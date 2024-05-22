package com.heybuddy.safespace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityLoadingBinding

class LoadingActivity:AppCompatActivity() {
    private lateinit var bind: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

}