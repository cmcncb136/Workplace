package com.heybuddy.safespace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityProductPaymentBinding


class ProductBuyActivity: AppCompatActivity() {

    private lateinit var productPayment_backIcon: ImageView
    private lateinit var chooseCompany: TextView

    private lateinit var subscribeProduct: TextView
    private lateinit var paymentProduct: TextView

    private lateinit var productCap: TextView
    private lateinit var paymentCap: TextView

    private lateinit var productMonth: TextView
    private lateinit var paymentMonth: TextView

    private lateinit var productPrice: TextView
    private lateinit var paymentPrice: TextView

    private lateinit var gotoPayment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityProductPaymentBinding.inflate(layoutInflater)
        setContentView(bind.root)

        //이동
        productPayment_backIcon = bind.productPaymentBackIcon
        chooseCompany = bind.chooseCompany

        subscribeProduct = bind.subscribeProduct
        paymentProduct = bind.paymentProduct

        productCap = bind.productCap
        paymentCap = bind.paymentCap

        productMonth = bind.productMonth
        paymentMonth = bind.paymentMonth

        productPrice = bind.productPrice
        paymentPrice = bind.paymentPrice

        chooseCompany = bind.chooseCompany

        /*
        //뒤로 가기 구현
        bind.productPayment_backIcon.setOnClickListener {
            Toast.makeText(this, "go Join Info Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, JoinInfoActivity::class.java))
        }
      */

        // 이동 구현
        bind.gotoPayment.setOnClickListener {
            Toast.makeText(this, "go Main Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}