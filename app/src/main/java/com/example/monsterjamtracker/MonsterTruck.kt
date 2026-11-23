package com.example.monsterjamtracker

import java.util.UUID

data class MonsterTruck(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val series: String,
    val isCollected: Boolean = false
)
