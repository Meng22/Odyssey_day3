package com.example.odyssey_day3

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api1_Interface {
    @GET("/api/playerGameStatus")
    fun gameStatus(): Call<PlayerResponse>

    @POST("/api/enterGame")
    fun enterGame(@Body enterRequest: EnterRequest): Call<EnterResponse>

    @GET("/api/playerGameAnswer")
    fun bomb(
        @Query( "playerId" ) playerId:Int
    ): Call<BombResponse>

}