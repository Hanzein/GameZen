package com.farhanadi.gamezen.data.data_game

data class Game_Property(
    val id: Int,
    val name: String,
    val genre: String,
    val image: Int,
    val description: String,
    val gametype: String,
    val publishers: String,
    val apprelease: String,
    val price: Double,
    val isFavGame: Boolean = false
)
