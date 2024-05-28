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
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.basic_component.Login
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityProductPaymentBinding
import com.heybuddy.safespace.service.SubscribeInformationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ProductBuyActivity: AppCompatActivity() {

    private lateinit var productPayment_backIcon: ImageButton
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

    private lateinit var retrofit: Retrofit
    private lateinit var subscribeInformationService: SubscribeInformationService
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityProductPaymentBinding.inflate(layoutInflater)
        setContentView(bind.root)

        if(auth.uid == null){
            startActivity(Intent(this@ProductBuyActivity, LoginActivity::class.java))
            finish()
            return;
        }

        retrofit = RetrofitSetting.getRetrofit()
        subscribeInformationService = retrofit.create(SubscribeInformationService::class.java)

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
            val productId = intent.getStringExtra("productId")

            if(productId == null){
                finish()
                return@setOnClickListener;
            }

            subscribeInformationService.addSubscribeInfo(auth.uid!!, productId!!)
                .enqueue(object: Callback<Boolean>{
                    override fun onResponse(p0: Call<Boolean>, body: Response<Boolean>) {
                        if(body.body() == null || !body.body()!!){
                            Toast.makeText(this@ProductBuyActivity, "구매 실패했습니다.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this@ProductBuyActivity, "구매 성공했습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@ProductBuyActivity, SubscribeListActivity::class.java))
                            finish()
                        }
                    }

                    override fun onFailure(p0: Call<Boolean>, p1: Throwable) {
                        Toast.makeText(this@ProductBuyActivity, p1.message, Toast.LENGTH_LONG).show()
                    }

                })
        }
    }
}