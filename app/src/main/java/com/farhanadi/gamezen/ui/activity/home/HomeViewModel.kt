package com.farhanadi.gamezen.ui.activity.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanadi.gamezen.data.GameRepository
import com.farhanadi.gamezen.data.ResultData
import com.farhanadi.gamezen.data.data_game.Game_Property
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: GameRepository) : ViewModel() {
    private val _appResult = MutableStateFlow<ResultData<List<Game_Property>>>(ResultData.Loading)
    val resultdata: StateFlow<ResultData<List<Game_Property>>> get() = _appResult

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun searchGame(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        try {
            repository.searchGame(_query.value)
                .collect { result ->
                    _appResult.value = ResultData.Success(result)
                }
        } catch (e: Exception) {
            _appResult.value = ResultData.Error(e.message.toString())
        }
    }

    fun updateGame(id: Int, newState: Boolean) = viewModelScope.launch {
        try {
            repository.updateGame(id, newState)
                .collect { isUpdated ->
                    if (isUpdated) searchGame(_query.value)
                }
        } catch (e: Exception) {
            _appResult.value = ResultData.Error(e.message.toString())
        }
    }
}
