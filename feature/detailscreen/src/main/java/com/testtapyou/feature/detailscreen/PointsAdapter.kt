package com.testtapyou.feature.detailscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testtapyou.data.network.model.Point
import com.testtapyou.detailscreen.R

class PointsAdapter : RecyclerView.Adapter<ViewHolder>() {
    val items = mutableListOf<Point>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val point = items[holder.adapterPosition]
        holder.bind(point)
    }

    fun setData(points: List<Point>) {
        items.clear()
        items.addAll(points)
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textX = view.findViewById<TextView>(R.id.x)
    private val textY = view.findViewById<TextView>(R.id.y)

    fun bind(point: Point) {
        textX.text = point.x.toString()
        textY.text = point.y.toString()
    }
}

