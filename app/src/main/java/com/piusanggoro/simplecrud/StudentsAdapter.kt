package com.piusanggoro.simplecrud

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.student_list.view.*

class StudentsAdapter(private val context: Context, private val arrayList: ArrayList<StudentsModel>) : RecyclerView.Adapter<StudentsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.student_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.lbNimList.text = arrayList?.get(position)?.nim
        holder.view.lbNameList.text = "Nama : "+arrayList?.get(position)?.name
        holder.view.lbAddressList.text = "Alamat : "+arrayList?.get(position)?.address
        holder.view.lbGenderList.text = "Gender : "+arrayList?.get(position)?.gender

        holder.view.cvList.setOnClickListener {
            val i = Intent(context,StudentCrudActivity::class.java)
            i.putExtra("editmode","1")
            i.putExtra("nim",arrayList?.get(position)?.nim)
            i.putExtra("name",arrayList?.get(position)?.name)
            i.putExtra("address",arrayList?.get(position)?.address)
            i.putExtra("gender",arrayList?.get(position)?.gender)
            context.startActivity(i)
        }
    }

    inner class Holder(val view:View) : RecyclerView.ViewHolder(view)
}