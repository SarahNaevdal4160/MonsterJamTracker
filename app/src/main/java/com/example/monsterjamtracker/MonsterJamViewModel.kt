package com.example.monsterjamtracker

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MonsterJamViewModel : ViewModel() {

    private val _trucks = mutableStateListOf<MonsterTruck>()
    val trucks: List<MonsterTruck> = _trucks

    fun addTruck(name: String, series: String) {
        if (name.isBlank()) return
        _trucks.add(
            MonsterTruck(
                name = name.trim(),
                series = series
            )
        )
    }

    fun toggleCollected(id: String) {
        val index = _trucks.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = _trucks[index]
            _trucks[index] = current.copy(isCollected = !current.isCollected)
        }
    }

    fun editTruck(id: String, newName: String, newSeries: String) {
        if (newName.isBlank()) return
        val index = _trucks.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = _trucks[index]
            _trucks[index] = current.copy(
                name = newName.trim(),
                series = newSeries
            )
        }
    }

    fun deleteTruck(id: String) {
        _trucks.removeAll { it.id == id }
    }
}
