package com.example.odyssey_day3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_lobby.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer

class LobbyActivity : AppCompatActivity() {
    private val players_list = arrayListOf<String>()
    private val lobbyAdapter = PlayerAdapter()
    var isrruning = false
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        val name = intent.getStringExtra("name")
        val positionList = arrayOf("荒野沙漠","魔法森林")
        tv_player.setText("玩家姓名：$name")
        timer = Timer()


        //取得系統時間
        val mCalendar = Calendar.getInstance();
        val timeInMill = mCalendar.timeInMillis
        val time = (timeInMill/1000).toInt()
        println("===========================$time")

        val mAnimation = AnimationUtils.loadAnimation(this, R.anim.show_anim)
        player_image.startAnimation(mAnimation)
        tv_player.startAnimation(mAnimation)

        //刷新畫面
//        timer.schedule(object : TimerTask(){
//            override fun run() {
//                gameStatus()
//            }
//        }, 5000, 500)

        btn_play.setOnClickListener {
            var position = 0
            AlertDialog.Builder(this)
                .setTitle("請選擇遊戲背景？")
                .setSingleChoiceItems(positionList, 0) { _, i ->
                    position = i }
                .setNegativeButton("取消"){dialog,_ ->
                    dialog.cancel()
                }
                .setPositiveButton("確認") { _, _ ->
                //提醒
                    Toast.makeText(this, "你點的是" + positionList[position], Toast.LENGTH_SHORT).show()
                //發送加入遊戲api



                //跳轉畫面
                    val position = position+1
                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("time", time)
                    intent.putExtra("name", name)
                    intent.putExtra("position", position)
                    println("=============$name $position")
                    startActivityForResult(intent, 1)

                }.show()
        }
    }

    override fun onResume() {
        super.onResume()
        timer.schedule(object : TimerTask(){
            override fun run() {
                gameStatus()
            }
        }, 0, 5000)
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
    fun gameStatus(){
        if(isrruning ) return

        isrruning = true
        API1.apiInterface.gameStatus().enqueue(object: Callback<PlayerResponse>{
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                isrruning = false
            }
            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                isrruning = false
                if (response.code() == 200){
                    val responsebody = response.body()
                    println("================$responsebody")
                    val players = responsebody!!.players
                    players_list.clear()
                    for (i in 0 until players.size){
                        players_list.add(players[i].name)
                    }
                    println("===========$players_list")

                    rv_lobby.layoutManager = GridLayoutManager(this@LobbyActivity, 6)
                    rv_lobby.adapter = lobbyAdapter
                    lobbyAdapter.update(players_list)
                }
            }
        })
    }

}
