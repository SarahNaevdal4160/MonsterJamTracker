package com.example.monsterjamtracker

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import org.json.JSONArray
import org.json.JSONObject

class MonsterJamViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("monster_jam_prefs", Context.MODE_PRIVATE)
    private val _trucks = mutableStateListOf<MonsterTruck>()
    val trucks: List<MonsterTruck> = _trucks

    init {
        loadTrucks()
    }
    fun addTruck(name: String, series: String) {
        if (name.isBlank()) return
        _trucks.add(
            MonsterTruck(
                name = name.trim(),
                series = series
            )
        )
        saveTrucks()
    }

    fun toggleCollected(id: String) {
        val index = _trucks.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = _trucks[index]
            _trucks[index] = current.copy(isCollected = !current.isCollected)
            saveTrucks()
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
            saveTrucks()
        }
    }

    fun deleteTruck(id: String) {
        _trucks.removeAll { it.id == id }
        saveTrucks()
    }
    private fun saveTrucks() {
        val jsonArray = JSONArray()
        _trucks.forEach { truck ->
            val obj = JSONObject().apply {
                put("id", truck.id)
                put("name", truck.name)
                put("series", truck.series)
                put("isCollected", truck.isCollected)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString("saved_trucks", jsonArray.toString()).commit()
    }

    // Läser in sparade bilar när appen startas
    private fun loadTrucks() {
        val jsonString = prefs.getString("saved_trucks", null) ?: return
        val jsonArray = JSONArray(jsonString)
        _trucks.clear()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            _trucks.add(
                MonsterTruck(
                    id = obj.getString("id"),
                    name = obj.getString("name"),
                    series = obj.getString("series"),
                    isCollected = obj.getBoolean("isCollected")
                )
            )
        }
    }
}

