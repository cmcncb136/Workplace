package com.heybuddy.safespace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivitySubscribeInformationDetailBinding
import com.heybuddy.safespace.dto.SubscribeInformationDto
import com.heybuddy.safespace.service.SubscribeInformationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SubscribeInformationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivitySubscribeInformationDetailBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var retrofit: Retrofit = RetrofitSetting.getRetrofit()
    private lateinit var subscribeInformationService: SubscribeInformationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySubscribeInformationDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        subscribeInformationService = retrofit.create(SubscribeInformationService::class.java)

        val id = intent.getIntExtra("subscribeId", 0)
        if(id == 0){
            Toast.makeText(this, "올바르지 않은 접급입니다..", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        Toast.makeText(this@SubscribeInformationDetailActivity, "find id : " + id.toString(), Toast.LENGTH_LONG).show()
        subscribeInformationService.findSubscribeInfo(id).enqueue(
            object: Callback<SubscribeInformationDto>{
                override fun onResponse(p0: Call<SubscribeInformationDto>, body: Response<SubscribeInformationDto>) {
                    if(body.body() == null){
                        Toast.makeText(this@SubscribeInformationDetailActivity, "상품 정보가 없습니다.", Toast.LENGTH_LONG).show()
                        finish()
                        return
                    }

                    val subscribeInformation = body.body()!!
                    bind.capacityTv.text = subscribeInformation.product.capacity.toString() + "명"
                    bind.categoryTv.text = subscribeInformation.product.category.categoryName
                    bind.productNameTv.text = subscribeInformation.product.productName
                    bind.providerNameTv.text = subscribeInformation.provider.name
                    bind.paymentDateTv.text = subscribeInformation.paymentDate
                    bind.nextPaymentDateTv.text = subscribeInformation.nextPaymentDate
                    bind.startDateTv.text = subscribeInformation.startDate
                    bind.endDateTv.text = subscribeInformation.endDate
                }

                override fun onFailure(p0: Call<SubscribeInformationDto>, p1: Throwable) {
                    Toast.makeText(this@SubscribeInformationDetailActivity, p1.message, Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}