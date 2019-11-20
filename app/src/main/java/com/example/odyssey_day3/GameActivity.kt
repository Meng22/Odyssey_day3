package com.example.odyssey_day3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GameActivity : AppCompatActivity() {
    private val desertAdapter = PlayerAdapter()
    private val forestAdapter = PlayerAdapter()
    private val desertList = arrayListOf<String>()
    private val forestList = arrayListOf<String>()
    private var playerId = 1
    lateinit var renewtimer: Timer
    lateinit var bombtimer: Timer

    var isrruning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val mName = intent.getStringExtra("name")
        val mPosition = intent.getIntExtra("position", 1)
        val mTime = intent.getIntExtra("time", 1574237816)
        renewtimer = Timer()

        println("=============$mName $mPosition $mTime")


        entry(mTime, mName, mPosition)

//        bombtimer.schedule(object : TimerTask(){
//            override fun run() {
//                bomb()
//            }
//        }, 0, 5000)


    }

    override fun onResume() {
        super.onResume()
        renewtimer.schedule(object : TimerTask(){
            override fun run() {
                renew()
            }
        }, 0, 10000)
    }

    override fun onPause() {
        super.onPause()
        renewtimer.cancel()
    }


    fun entry(time: Int, name: String, position: Int){
        API1.apiInterface.enterGame(EnterRequest(time, name, position)).enqueue(object : Callback<EnterResponse> {
            override fun onFailure(call: Call<EnterResponse>, t: Throwable) {
            }
            override fun onResponse(call: Call<EnterResponse>, response: Response<EnterResponse>) {
                if (response.isSuccessful){
                    val responsebody = response.body()
                    playerId = responsebody!!.playerId
                    val players = responsebody!!.players
                    val players_1 = players.filter { it.position == 1 }
                    val players_2 = players.filter { it.position == 2 }

                    for (i in 0 until players_1.size){
                        desertList.add(players_1[i].name)
                    }

                    for (i in 0 until players_2.size){
                        forestList.add(players_2[i].name)
                    }
                    println("=================$desertList")
                    println("=================$forestList")

                    rv_desert.layoutManager = StaggeredGridLayoutManager(2,1)
                    rv_desert.adapter = desertAdapter
                    desertAdapter.update(desertList)

                    rv_forest.layoutManager = StaggeredGridLayoutManager(2,1)
                    rv_forest.adapter = forestAdapter
                    forestAdapter.update(forestList)

                }
            }
        })
    }
    fun renew(){
        if(isrruning ) return

        isrruning = true
        API1.apiInterface.gameStatus().enqueue(object: Callback<PlayerResponse> {
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                isrruning = false
            }

            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                isrruning = false
                if (response.code() == 200) {
                    val responsebody = response.body()
                    val players = responsebody!!.players
                    val players_1 = players.filter { it.position == 1 }
                    val players_2 = players.filter { it.position == 2 }

                    desertList.clear()
                    for (i in 0 until players_1.size) {
                        desertList.add(players_1[i].name)
                    }

                    forestList.clear()
                    for (i in 0 until players_2.size) {
                        forestList.add(players_2[i].name)
                    }
                    println("=================$desertList")
                    println("=================$forestList")

                    rv_desert.layoutManager = StaggeredGridLayoutManager(2,1)
                    rv_desert.adapter = desertAdapter
                    desertAdapter.update(desertList)

                    rv_forest.layoutManager = StaggeredGridLayoutManager(2,1)
                    rv_forest.adapter = forestAdapter
                    forestAdapter.update(forestList)


                }
            }
        })

    }

    fun bomb(){
        API1.apiInterface.bomb(playerId).enqueue(object : Callback<BombResponse>{
            override fun onFailure(call: Call<BombResponse>, t: Throwable) {

            }
            override fun onResponse(call: Call<BombResponse>, response: Response<BombResponse>) {
                if (response.code() == 200){
                    val responsebody = response.body()
                    println("============$responsebody")
                    val result = responsebody!!.isWinner
                    val id = responsebody!!.playerId
//                    if (result == true && id == 1){
//                        bomb_forest.visibility
//                        renewtimer.cancel()
//                        bombtimer.cancel()
//                    }else if (result == true && id == 2){
//                        bomb_desert.visibility
//                        renewtimer.cancel()
//                        bombtimer.cancel()
//                    }else if (result == false && id == 1){
//                        bomb_desert.visibility
//                        renewtimer.cancel()
//                        bombtimer.cancel()
//                    }else if (result == false && id == 1){
//                        bomb_forest.visibility
//                        renewtimer.cancel()
//                        bombtimer.cancel()
//                    }
                }
            }
        })
    }
}
