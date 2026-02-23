package com.example.ordy.Models

class Cart (var categoryID: Int, var amount: Int){

    override fun toString(): String {
        return "$categoryID: $amount"

    }

}