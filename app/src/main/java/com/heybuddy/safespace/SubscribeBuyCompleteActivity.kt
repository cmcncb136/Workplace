package com.heybuddy.safespace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityLoginBinding
import com.heybuddy.safespace.databinding.ActivitySubscribeBuyCompleteBinding

class SubscribeBuyCompleteActivity: AppCompatActivity() {

    private lateinit var completeMsg: TextView
    private lateinit var paymentCompleteImg: ImageView

    private lateinit var nextPaymentdate: TextView
    private lateinit var nextPaymentdateDetail: TextView

    private lateinit var gotoMainBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivitySubscribeBuyCompleteBinding.inflate(layoutInflater)
        setContentView(bind.root);

        //결재 완료 후 Main 이동 구현
        bind.gotoMainBtn.setOnClickListener {
            Toast.makeText(this, "go Main Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}