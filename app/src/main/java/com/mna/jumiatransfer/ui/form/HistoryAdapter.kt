package com.mna.jumiatransfer.ui.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.ui.ItemClick

class HistoryAdapter(private val walletIds: ArrayList<String>, private val itemClick: ItemClick<String>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_wallet_id_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (walletIds.size < 5) {
            walletIds.size
        } else {
            5
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(walletIds.elementAt(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            val textVw = itemView.findViewById<TextView>(R.id.idTxtVw)
            textVw.text = item
            textVw.setOnClickListener { itemClick.onClick(walletIds.elementAt(adapterPosition)) }
        }
    }
}
