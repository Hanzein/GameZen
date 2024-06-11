package com.farhanadi.gamezen.ui.activity.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanadi.gamezen.data.GameRepository
import com.farhanadi.gamezen.data.ResultData
import com.farhanadi.gamezen.data.data_game.Game_Property
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: GameRepository) : ViewModel() {
    private val _appResult = MutableStateFlow<ResultData<List<Game_Property>>>(ResultData.Loading)
    val resultdata: StateFlow<ResultData<List<Game_Property>>> = _appResult

    fun getFavoriteGame() = viewModelScope.launch {
        try {
            repository.getFavoriteGame().collect { fishList ->
                _appResult.value = ResultData.Success(fishList)
            }
        } catch (e: Exception) {
            _appResult.value = ResultData.Error(e.message.toString())
        }
    }

    fun updateGame(id: Int, newState: Boolean) {
        viewModelScope.launch {
            repository.updateGame(id, newState)
            getFavoriteGame()
        }
    }
}
