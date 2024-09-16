package com.jpc.chapter18

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context, private val data: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    // 用于得到我们自定义的ViewHolder。在ListView中，我们也会定义ViewHolder来承载视图中的元素。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        // 根据不同的viewType返回不同的ViewHolder
        return if(viewType == ITEM_TYPE_SECTION){
            SectionViewHolder(inflater.inflate(R.layout.item_section_list, parent, false))
        }else{
            ItemViewHolder(inflater.inflate(R.layout.item_string_list, parent, false))
        }
    }
    // 用于获取列表总共的item数。
    override fun getItemCount(): Int {
        return data.size
    }
    // 用于将指定位置的数据和视图绑定起来。
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionViewHolder){
            holder.tvSection.text = "第${position/10}组"
        }else if (holder is ItemViewHolder){
            // 绑定数据
            holder.tvString.text = data[position]
            holder.tvString.setOnClickListener {
                Toast.makeText(context, "click string: $position", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // 通过getItemViewType函数来返回每个position所对应的类型
    override fun getItemViewType(position: Int): Int {
        return if (position % 10 == 0)
            ITEM_TYPE_SECTION
        else
            ITEM_TYPE_ITEM
    }

    // ViewHolder的主要作用就是将XML中的控件以变量的形式保存起来，方便我们后面的数据绑定。
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvString: TextView = view.findViewById(R.id.tv_string)
    }

    inner class SectionViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvSection: TextView = view.findViewById(R.id.tv_section)
    }

    companion object{
        const val ITEM_TYPE_SECTION = 0
        const val ITEM_TYPE_ITEM = 1
    }
}