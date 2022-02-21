package com.proyecto1.proyecto1_federicomoreira

// Definir la clase Platillo con los atributos nombre, precio, reting y foto //
class Platillo(nombre:String, precio:Double, rating:Float, foto:Int) {

    // Definir las variables para almacenar los valores que contendran los atributos //
    var nombre = ""
    var precio = 0.0
    var rating = 0.0F
    var foto = 0

    // Inicializar los atributos //
    init{
        this.nombre = nombre
        this.precio = precio
        this.rating = rating
        this.foto = foto
    }
}