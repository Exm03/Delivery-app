package com.example.ordy

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ordy.Models.Cart
import com.example.ordy.Models.Order
import com.example.ordy.Models.Users
import com.example.ordy.SignIn.Companion.setDefults
import com.example.ordy.helpers.CartListAdapter
import com.example.ordy.helpers.JSONHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.shopping_cart)
        val cartList = JSONHelper.importFromJSON(this)

        if (cartList == null) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_LONG).show()
        }else{
            val arrayAdapter = CartListAdapter(this, R.layout.cart_item, cartList)
            listView.adapter = arrayAdapter
            Toast.makeText(this, "Cart is not empty", Toast.LENGTH_LONG).show()
        }

        val database =  FirebaseDatabase.getInstance("https://ordy-28323-default-rtdb.europe-west1.firebasedatabase.app/")
        val table  = database.getReference("Order")

        val btnMakeOrder = findViewById<Button>(R.id.btnMakeOrder)
        btnMakeOrder.setOnClickListener {
            table.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val json = JSONHelper.importFromJSON(this@CartActivity)

                    if (json ==null){
                        Toast.makeText(this@CartActivity, "Cart is empty", Toast.LENGTH_SHORT).show()
                        return
                    }

                    val phone = SignIn.getDefults("UserPhone", this@CartActivity)
                    val name = SignIn.getDefults("Username", this@CartActivity)

                    val order = Order(json.toString(), phone, name)

                    val dateFormat = SimpleDateFormat("dd-MM HH:mm")
                    val currentData = dateFormat.format(Date())

                    table.child(currentData).setValue(order).addOnSuccessListener {
                        Toast.makeText(this@CartActivity, "Order is made", Toast.LENGTH_SHORT).show()

                        val dataList: List<Cart?> = ArrayList()
                        JSONHelper.expotrToJSON(this@CartActivity, dataList)
                        val arrayAdapter = CartListAdapter(this@CartActivity, R.layout.cart_item, dataList)
                        listView.adapter = arrayAdapter
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "No internet connection", Toast.LENGTH_SHORT).show()

                }


            })
        }

    }
}