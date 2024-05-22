package com.heybuddy.safespace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.heybuddy.safespace.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()


        val btn = bind.loginGoBtn
        btn.setOnClickListener {
            Toast.makeText(this, "go Login page", Toast.LENGTH_SHORT).show()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        bind.mainGoBtn.setOnClickListener {
            Toast.makeText(this, "go Main page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SubscribeListActivity::class.java))
        }


        //이동 처리 (6개)
        //JOIN -> JOIN INFO -> MAIN으로 임시 구현
        bind.joinBtn.setOnClickListener {
            Toast.makeText(this, "go Join Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, JoinActivity::class.java))
        }

        bind.productsBtn.setOnClickListener {
            Toast.makeText(this, "go products Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProductListActivity::class.java))
        }

        bind.ProductDetailBtn.setOnClickListener {
            Toast.makeText(this, "go Product Detail Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProductDetailActivity::class.java))
        }

        bind.ProductBuyBtn.setOnClickListener {
            Toast.makeText(this, "go .Product Buy Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProductBuyActivity::class.java))
        }

        bind.BuyCompleteBtn.setOnClickListener {
            Toast.makeText(this, "go Buy Complete Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SubscribeBuyCompleteActivity::class.java))
        }



    }

}