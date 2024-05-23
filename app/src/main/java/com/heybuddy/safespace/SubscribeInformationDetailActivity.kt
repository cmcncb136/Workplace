package com.heybuddy.safespace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivitySubscribeInformationDetailBinding

class SubscribeInformationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivitySubscribeInformationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySubscribeInformationDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}