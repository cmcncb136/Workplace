package com.heybuddy.safespace

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityLoadingBinding

class LoadingActivity:AppCompatActivity() {
    private lateinit var bind: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val handler = Handler()

        handler.postDelayed({
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, 1500)
    }

}