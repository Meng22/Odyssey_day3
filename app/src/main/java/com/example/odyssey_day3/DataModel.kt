package com.example.odyssey_day3

data class PlayerResponse(
    val endTime: String,
    val isEnd: Boolean,
    val players: List<PlayersDetail>
)

data class EnterRequest(
    val enterTime: Int,
    val name: String,
    val position: Int
)
data class EnterResponse(
    val endTime: String,
    val playerId: Int,
    val players: List<PlayInfo>,
    val yourName: String
)
data class PlayInfo(
    val name: String,
    val position: Int
)

data class PlayersDetail(
    val name: String,
    val playerId: Int,
    val position: Int
)
data class BombResponse(
    val answer: Int,
    val isWinner: Boolean,
    val playerId: Int,
    val yourName: String
)

