package com.piusanggoro.simplecrud

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.student_list.view.*

class StudentsAdapter(private val context: Context, private val arrayList: ArrayList<StudentsModel>) : RecyclerView.Adapter<StudentsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.student_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val students = arrayList[position]
        holder.nim.text = students.nim
        holder.nama.text = "Nama : "+students.name
        holder.address.text = "Alamat : "+students.address
        holder.gender.text = "Gender : "+students.gender

        holder.list.setOnClickListener {
            val i = Intent(context,StudentCrudActivity::class.java)
            i.putExtra("editmode","1")
            i.putExtra("nim",students.nim)
            i.putExtra("name",students.name)
            i.putExtra("address",students.address)
            i.putExtra("gender",students.gender)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    inner class Holder(val view:View) : RecyclerView.ViewHolder(view){
        var nim: TextView = view.findViewById(R.id.lbNimList)
        var nama: TextView = view.findViewById(R.id.lbNameList)
        var address: TextView = view.findViewById(R.id.lbAddressList)
        var gender: TextView = view.findViewById(R.id.lbGenderList)
        var list: CardView = view.findViewById(R.id.cvList)
    }
}