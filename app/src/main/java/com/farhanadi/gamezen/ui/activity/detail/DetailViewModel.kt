package com.farhanadi.gamezen.ui.activity.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanadi.gamezen.data.GameRepository
import com.farhanadi.gamezen.data.ResultData
import com.farhanadi.gamezen.data.data_game.Game_Property
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GameRepository) : ViewModel() {
    private val _resultdata = MutableStateFlow<ResultData<Game_Property>>(ResultData.Loading)
    val resultdata: StateFlow<ResultData<Game_Property>> get() = _resultdata

    fun getGamebyId(id: Int) = viewModelScope.launch {
        _resultdata.value = ResultData.Loading
        _resultdata.value = ResultData.Success(repository.getGameById(id))
    }

    fun updateGame(id: Int, newState: Boolean) = viewModelScope.launch {
        if (repository.updateGame(id, !newState).first()) {
            getGamebyId(id)
        }
    }
}
