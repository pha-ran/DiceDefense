package com.dicedefense.Game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicedefense.R
import com.dicedefense.databinding.ItemFieldBinding

class FieldAdaptor(private val diceList : ArrayList<Dice>) : RecyclerView.Adapter<FieldAdaptor.FieldViewHolder>() {

    inner class FieldViewHolder(private val binding : ItemFieldBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dice : Dice) {
            when (dice.level) {
                1 -> {
                    binding.IvField.setImageResource(R.drawable.dice_normal_1)
                }
                else -> {
                    binding.IvField.setImageResource(R.drawable.dice_empty)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val binding = ItemFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        holder.bind(diceList[position])
    }

    override fun getItemCount(): Int {
        return diceList.size
    }
}