package com.example.ordy.Models

class Users {

    var name: String? = null
    var pass: String? = null

    constructor()
    constructor(name: String, pass: String) {
        this.name = name
        this.pass = pass
    }
}


