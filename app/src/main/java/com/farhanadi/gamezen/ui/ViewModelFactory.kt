package com.farhanadi.gamezen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farhanadi.gamezen.data.GameRepository
import com.farhanadi.gamezen.ui.activity.detail.DetailViewModel
import com.farhanadi.gamezen.ui.activity.favorite.FavoriteViewModel
import com.farhanadi.gamezen.ui.activity.home.HomeViewModel

class ViewModelFactory(private val repository: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}