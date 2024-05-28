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
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityProductPaymentBinding
import com.heybuddy.safespace.dto.ProductDto
import com.heybuddy.safespace.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ProductBuyActivity: AppCompatActivity() {

    private lateinit var productPayment_backIcon: ImageView
    private lateinit var chooseCompany: TextView
    
    private lateinit var paymentProduct: TextView

    private lateinit var paymentCap: TextView
    
    private lateinit var paymentMonth: TextView
    
    private lateinit var paymentPrice: TextView
    private lateinit var gotoPayment: Button

    private lateinit var retrofit: Retrofit
    private lateinit var product: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityProductPaymentBinding.inflate(layoutInflater)
        setContentView(bind.root)

        //layout에서 받음
        productPayment_backIcon = bind.productPaymentBackIcon
        chooseCompany = bind.chooseCompany

        paymentProduct = bind.paymentProduct

        paymentCap = bind.paymentCap

        paymentMonth = bind.paymentMonth

        paymentPrice = bind.paymentPrice


        //뒤로 가기 구현
        productPayment_backIcon.setOnClickListener {
            Toast.makeText(this, "상품 상세 페이지로 이동", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProductDetailActivity::class.java))
        }

        //다음 페이지 이동 구현
        bind.gotoPayment.setOnClickListener {
            Toast.makeText(this, "go Main Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SubscribeBuyCompleteActivity::class.java))
        }


        //통신? 전 activity에서 값 받아옴
        retrofit = RetrofitSetting.getRetrofit()
        val productService: ProductService = retrofit.create(ProductService::class.java)
        val productId = this.intent.getStringExtra("productId")?:"a";

        val call = productService.findAllProduct()

        //TODO: 통신 받아와서 넣어주려고  80줄에서 오류
/*

        call.enqueue(object: Callback<ProductDto> {
            //받아옴
            override fun onResponse(call: Call<ProductDto>, body : Response<ProductDto>) {
                //받아온 상품 id가 null -> 지난 activity로 돌려보내줌
                if(body.body() == null) {
                    Toast.makeText(this@ProductBuyActivity, "존재하지 않는 제공자입니다.", Toast.LENGTH_SHORT).show();
                    startActivity(Intent(this@ProductBuyActivity, ProductListActivity::class.java))
                    finish()
                    return
                }

                val product = body.body()!!

                // UI를 상품 세부 정보로 업데이트
                chooseCompany.text = product.productName
                paymentProduct.text = product.productName
                paymentCap.text = product.capacity.toString()
                paymentMonth.text = product.month.toString()
                paymentPrice.text = product.price.toString()


            }
            //받아오지 못함
            override fun onFailure(call: Call<ProductDto>, p1: Throwable) {
                Toast.makeText(this@ProductBuyActivity, p1.message, Toast.LENGTH_LONG).show();
            }
        })

*/

        
        //결제시 상품아이디 같이 넘김
        gotoPayment.setOnClickListener {
            val intent = Intent(this, ProductBuyActivity::class.java)
            intent.putExtra("productId", productId);

            this.startActivity(intent)
        }
        

    }
}