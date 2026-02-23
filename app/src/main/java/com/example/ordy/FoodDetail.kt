package com.example.ordy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ordy.Models.Cart
import com.example.ordy.Models.Category
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.ordy.Models.Food
import com.example.ordy.helpers.JSONHelper


class FoodDetail : AppCompatActivity() {
    companion object  {
        var ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_food_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainPhoto = findViewById<ImageView>(R.id.mainPhoto)
        val foodMainName = findViewById<TextView>(R.id.foodMainName)
        val price = findViewById<TextView>(R.id.foodPrice)
        val fullName = findViewById<TextView>(R.id.foodFullName)

        val database =  FirebaseDatabase.getInstance("https://ordy-28323-default-rtdb.europe-west1.firebasedatabase.app/")
        val table  = database.getReference("Category")


        table.child(ID.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val category =snapshot.getValue(Category::class.java)

                foodMainName.text = category?.name

                val id = when (category?.image) {
                    "burger" -> R.drawable.burger
                    "pizza" -> R.drawable.pizza
                    "fish" -> R.drawable.fish
                    "chicken" -> R.drawable.chicken
                    "meat" -> R.drawable.meat
                    "sandwich" ->R.drawable.sandwich
                    "soup" -> R.drawable.soup
                    else -> R.drawable.vegan
                }

                mainPhoto.setImageResource(id)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FoodDetail, "No internet connection", Toast.LENGTH_SHORT).show()

            }


        })

        val tableFood  = database.getReference("Food")
        tableFood.child(ID.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val food = snapshot.getValue(Food::class.java)
                price.text = food?.price + "$"
                fullName.text = food?.full_text

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FoodDetail, "No internet connection", Toast.LENGTH_SHORT).show()

            }


        })

        val btnGoToCart = findViewById<Button>(R.id.btnGoToCart)
        btnGoToCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))

        }

    }
    public fun btnAddToCart(view: View) {
        var cartList: MutableList<Cart?>? = JSONHelper.importFromJSON(this)?.toMutableList()

        if (cartList == null) {
            cartList = ArrayList()
            cartList.add(Cart(ID, 1))
        }else {
            var isFound  = false
            for (el in cartList) {
                if (el?.categoryID == ID) {
                    el.amount++
                    isFound = true
                }
            }

            if (!isFound) {
                cartList.add(Cart(ID, 1))
            }


        }

        JSONHelper.expotrToJSON(this, cartList)
        Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show()
        val btnCart = view as Button
        btnCart.text = "Added"
    }
}