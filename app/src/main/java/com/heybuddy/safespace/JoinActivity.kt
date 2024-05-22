package com.heybuddy.safespace

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityJoinBinding
import org.w3c.dom.Text

class JoinActivity: AppCompatActivity() {


    private lateinit var joinTitleTv: TextView
    private lateinit var idTv: TextView
    private lateinit var idEt: EditText
    private lateinit var idCheck: TextView

    private lateinit var passwdTv: TextView
    private lateinit var passwdEt: EditText
    private lateinit var joinNextBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(bind.root)
        joinTitleTv = bind.joinTitleTv
        //아이디
        idTv = bind.idTv
        idEt = bind.idEt
        idCheck = bind.idCheck

        //비밀번호
        passwdTv = bind.passwdTv
        passwdEt = bind.passwdEt

        //다음 버튼
        joinNextBtn = bind.joinNextBtn

        
        //Join Info 이동 구현
        bind.joinNextBtn.setOnClickListener {
            Toast.makeText(this, "go Join Info Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, JoinInfoActivity::class.java))
        }


    }
}