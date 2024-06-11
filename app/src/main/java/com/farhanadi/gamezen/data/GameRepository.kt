package com.farhanadi.gamezen.data

import com.farhanadi.gamezen.data.data_game.Game_Data
import com.farhanadi.gamezen.data.data_game.Game_Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GameRepository private constructor(){
    private val dummyGame = mutableListOf<Game_Property>()

    init {
        initializeDummyGame()
    }

    private fun initializeDummyGame() {
        if (dummyGame.isEmpty()) {
            dummyGame.addAll(Game_Data.dummyGame)
        }
    }

    fun getGameById(gameId: Int): Game_Property {
        return dummyGame.first { it.id == gameId }
    }

    fun getFavoriteGame(): Flow<List<Game_Property>> {
        return flowOf(dummyGame.filter { it.isFavGame })
    }

    fun searchGame(query: String): Flow<List<Game_Property>> = flow {
        val filteredGame = dummyGame.filter { it.name.contains(query, ignoreCase = true) }
        emit(filteredGame)
    }


    fun updateGame(gameId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyGame.indexOfFirst { it.id == gameId }
        val result = if (index >= 0) {
            val game = dummyGame[index]
            dummyGame[index] = game.copy(isFavGame = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(): GameRepository =
            instance ?: synchronized(this) {
                instance ?: GameRepository().also { instance = it }
            }
    }

}