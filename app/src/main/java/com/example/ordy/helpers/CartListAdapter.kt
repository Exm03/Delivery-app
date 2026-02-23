package com.example.ordy.helpers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.ordy.FoodPage
import com.example.ordy.Models.Cart
import com.example.ordy.Models.Category
import com.example.ordy.Models.Users
import com.example.ordy.R
import com.example.ordy.SignIn.Companion.setDefults
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartListAdapter (private val mContext: Context,
                       private val mResource: Int,
                       private val cartList: List<Cart?>) : ArrayAdapter<Cart>(mContext, mResource, cartList)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mainView  = LayoutInflater.from(mContext).inflate(mResource, parent, false)

        val cart = cartList[position]

        val productName = mainView.findViewById<TextView>(R.id.productName)
        val productAmount = mainView.findViewById<TextView>(R.id.productAmount)

        productAmount.text = cart?.amount.toString()

        val database =  FirebaseDatabase.getInstance("https://ordy-28323-default-rtdb.europe-west1.firebasedatabase.app/")
        val table  = database.getReference("Category")

        table.child((cart?.categoryID.toString())).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val category = snapshot.getValue(Category::class.java)
                productName.text = category?.name
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

        return mainView
    }

}