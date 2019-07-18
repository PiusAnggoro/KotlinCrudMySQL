package com.piusanggoro.simplecrud

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.jacksonandroidnetworking.JacksonParserFactory

class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<StudentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.setParserFactory(JacksonParserFactory())

        supportActionBar?.title = "Data Mahasiswa"

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        mFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, StudentCrudActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }

    private fun loadAllStudents() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {
                        arrayList.clear()
                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0) {
                            loading.dismiss()
                            Toast.makeText(applicationContext, "Student data is empty, Add the data first", Toast.LENGTH_SHORT).show()
                        }

                        for (i in 0 until jsonArray?.length()!!) {
                            val jsonObject = jsonArray?.optJSONObject(i)

                            arrayList.add(StudentsModel(jsonObject.getString("nim"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("address"),
                                    jsonObject.getString("gender")))

                            if (jsonArray?.length() - 1 == i) {
                                loading.dismiss()
                                val adapter = StudentsAdapter(applicationContext, arrayList)
                                adapter.notifyDataSetChanged()
                                mRecyclerView.adapter = adapter
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.e("ONERROR", anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
