package com.example.ordy

import com.example.ordy.Models.Category
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class FoodListAdapter(private val mContext: Context, private val mResource: Int, private val categorys: List<Category>) : ArrayAdapter<Category>(mContext, mResource, categorys) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mainView  = LayoutInflater.from(mContext).inflate(mResource, parent, false)

        val category = categorys[position]

        val foodName = mainView.findViewById<TextView>(R.id.foodMainName)
        val foodImage = mainView.findViewById<ImageView>(R.id.mainPhoto)

        foodName.text = category.name
        val id = when (category.image) {
            "burger" -> R.drawable.burger
            "pizza" -> R.drawable.pizza
            "fish" -> R.drawable.fish
            "chicken" -> R.drawable.chicken
            "meat" -> R.drawable.meat
            "sandwich" ->R.drawable.sandwich
            "soup" -> R.drawable.soup
            else -> R.drawable.vegan
        }

        foodImage.setImageResource(id)

        foodImage.setOnClickListener {
            FoodDetail.ID = position + 1
            mContext.startActivity(Intent(mContext, FoodDetail::class.java))

        }

        return mainView
    }
}