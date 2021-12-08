package com.dicedefense.Game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicedefense.R
import com.dicedefense.databinding.ItemFieldBinding

class FieldAdaptor(private val diceList : ArrayList<Dice>) : RecyclerView.Adapter<FieldAdaptor.FieldViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(position : Int)
    }

    lateinit var itemClickListener : OnItemClickListener

    fun setOnItemClickListener(itemClickListener : OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class FieldViewHolder(private val binding : ItemFieldBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dice : Dice, position: Int) {
            when (dice.level) {
                1 -> {
                    when (dice.type) {
                        0 -> {
                            binding.IvField.setImageResource(R.drawable.dice_normal_1)
                        }
                        1 -> {
                            binding.IvField.setImageResource(R.drawable.dice_red_1)
                        }
                        2 -> {
                            binding.IvField.setImageResource(R.drawable.dice_blue_1)
                        }
                    }
                }
                2 -> {
                    when (dice.type) {
                        0 -> {
                            binding.IvField.setImageResource(R.drawable.dice_normal_2)
                        }
                        1 -> {
                            binding.IvField.setImageResource(R.drawable.dice_red_2)
                        }
                        2 -> {
                            binding.IvField.setImageResource(R.drawable.dice_blue_2)
                        }
                    }
                }
                3 -> {
                    when (dice.type) {
                        0 -> {
                            binding.IvField.setImageResource(R.drawable.dice_normal_3)
                        }
                        1 -> {
                            binding.IvField.setImageResource(R.drawable.dice_red_3)
                        }
                        2 -> {
                            binding.IvField.setImageResource(R.drawable.dice_blue_3)
                        }
                    }
                }
                4 -> {
                    when (dice.type) {
                        0 -> {
                            binding.IvField.setImageResource(R.drawable.dice_normal_4)
                        }
                        1 -> {
                            binding.IvField.setImageResource(R.drawable.dice_red_4)
                        }
                        2 -> {
                            binding.IvField.setImageResource(R.drawable.dice_blue_4)
                        }
                    }
                }
                5 -> {
                    when (dice.type) {
                        0 -> {
                            binding.IvField.setImageResource(R.drawable.dice_normal_5)
                        }
                        1 -> {
                            binding.IvField.setImageResource(R.drawable.dice_red_5)
                        }
                        2 -> {
                            binding.IvField.setImageResource(R.drawable.dice_blue_5)
                        }
                    }
                }
                6 -> {
                    when (dice.type) {
                        0 -> {
                            binding.IvField.setImageResource(R.drawable.dice_normal_6)
                        }
                        1 -> {
                            binding.IvField.setImageResource(R.drawable.dice_red_6)
                        }
                        2 -> {
                            binding.IvField.setImageResource(R.drawable.dice_blue_6)
                        }
                    }
                }
                else -> {
                    binding.IvField.setImageResource(R.drawable.dice_empty)
                }
            }

            binding.IvField.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val binding = ItemFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        holder.bind(diceList[position], position)
    }

    override fun getItemCount(): Int {
        return diceList.size
    }
}