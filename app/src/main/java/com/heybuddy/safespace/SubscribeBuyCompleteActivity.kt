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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivitySubscribeBuyCompleteBinding.inflate(layoutInflater)
        setContentView(bind.root);

        bind.nextPaymentdateDetail.text = intent.getStringExtra("nextPayment")

        //결재 완료 후 Main 이동 구현
        bind.gotoMainBtn.setOnClickListener {
            startActivity(Intent(this, SubscribeListActivity::class.java))
            finish()
        }
    }
}