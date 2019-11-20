package com.example.odyssey_day3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_entry1.setOnClickListener {
            if (ed_user.text.isNullOrEmpty()){
                Toast.makeText(this, "請輸入玩家名稱", Toast.LENGTH_SHORT).show()
            }
            else{
                //切換遊戲大廳頁面
                val name = ed_user.text.toString()
                val intent = Intent(this, LobbyActivity::class.java)
                intent.putExtra("name", name)
                startActivityForResult(intent, 0)
            }
        }
    }
}
