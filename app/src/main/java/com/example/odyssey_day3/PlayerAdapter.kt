package com.example.odyssey_day3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayerAdapter: RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    val list : MutableList<String> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.player_itemview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(list[position])
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val player_name = view.findViewById<TextView>(R.id.tv_player)

        fun bing(name: String){
            player_name.setText(name)
        }

    }
    fun update(newList: MutableList<String>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}