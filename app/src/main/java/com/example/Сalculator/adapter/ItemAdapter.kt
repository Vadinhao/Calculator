package com.example.Сalculator.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.Сalculator.Calculator
import com.example.Сalculator.R
import com.example.Сalculator.databinding.ActivityMainBinding
import com.example.Сalculator.model.ButtonData

class ItemAdapter(
        private val context: Context,
        private val dataset: List<ButtonData>,
        private val listener: OnItemClickListener,
        private val calculator: Calculator,
        private val binding: ActivityMainBinding
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {
        val buttonView: Button = view.findViewById(R.id.button)
        //initListener
        init {
            buttonView.setOnClickListener(this)
        }
        //onClickSetFun
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, calculator, context, binding)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, calculator: Calculator, context: Context, binding: ActivityMainBinding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: ButtonData = dataset[position]
        holder.buttonView.text = context.resources.getString(item.stringResourceId)
        //buttonStyle
        if (holder.buttonView.text == context.getString(R.string.ac) ||
                holder.buttonView.text == context.getString(R.string.ch) ||
                holder.buttonView.text == context.getString(R.string.percentage)) {
            holder.buttonView.setBackgroundResource(R.drawable.roundedbutton_white_gray)
            holder.buttonView.setTextColor(Color.parseColor((context.getString(R.color.black))))
        }
        if (holder.buttonView.text == context.getString(R.string.division) ||
                holder.buttonView.text == context.getString(R.string.multiplication) ||
                holder.buttonView.text == context.getString(R.string.subtraction) ||
                holder.buttonView.text == context.getString(R.string.addition) ||
                holder.buttonView.text == context.getString(R.string.equality))
            holder.buttonView.setBackgroundResource(R.drawable.roundedbutton_orange)
        if (holder.buttonView.text == context.getString(R.string._0))
            holder.buttonView.setBackgroundResource(R.drawable.roundedbutton_dark_gray_left_piece)
        if (holder.buttonView.text == context.getString(R.string.empty))
            holder.buttonView.setBackgroundResource(R.drawable.roundedbutton_dark_gray_right_piece)
        //buttonStyle
    }

    override fun getItemCount() = dataset.size
}
