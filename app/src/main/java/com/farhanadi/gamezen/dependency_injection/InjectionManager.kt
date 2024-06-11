package com.farhanadi.gamezen.dependency_injection

import com.farhanadi.gamezen.data.GameRepository

object InjectionManager {
    fun provideRepository(): GameRepository {
        return GameRepository.getInstance()
    }
}