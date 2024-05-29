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
import com.heybuddy.safespace.basic_component.DefaultFormat
import com.heybuddy.safespace.basic_component.Login
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityProductPaymentBinding
import com.heybuddy.safespace.dto.ProductDto
import com.heybuddy.safespace.service.ProductService
import com.heybuddy.safespace.service.SubscribeInformationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate
import java.util.regex.Pattern


class ProductBuyActivity: AppCompatActivity() {

    private lateinit var backIcon: ImageView
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

    private var retrofit: Retrofit = RetrofitSetting.getRetrofit()
    private var subscribeInformationService = retrofit.create(SubscribeInformationService::class.java)
    private var auth = FirebaseAuth.getInstance()
    private lateinit var product: ProductDto

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


        //이동
        backIcon = bind.productPaymentBackIcon
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
        gotoPayment = bind.gotoPayment

        gotoPayment.isClickable = false //데이터를 가져올 때까지 클릭 못하게 방지

        //뒤로 가기 구현
        backIcon.setOnClickListener {
            Toast.makeText(this, "상품 상세 페이지로 이동", Toast.LENGTH_SHORT).show()
            finish() //현재 페이지 종료
        }


        //통신? 전 activity에서 값 받아옴
        val productService: ProductService = retrofit.create(ProductService::class.java)
        val productId = this.intent.getStringExtra("productId")?:"a";
        var providerName = this.intent.getStringExtra("providerName") ?: "Netflix"
        chooseCompany.text = providerName


        //상품 아이디로 조회
        val call = productService.findProduct(productId)

        call.enqueue(object: Callback<ProductDto> {
            //받아옴
            override fun onResponse(call: Call<ProductDto>, body : Response<ProductDto>) {
                //받아온 상품 id가 null -> 전 activity로 돌려보내줌
                if(body.body() == null) {
                    Toast.makeText(this@ProductBuyActivity, "존재하지 않는 제공자입니다.", Toast.LENGTH_SHORT).show();
                    finish()
                    return
                }

                product = body.body()!!

                // UI를 상품 세부 정보로 업데이트
                paymentProduct.text = product.productName
                paymentCap.text = product.capacity.toString() + "명"
                paymentMonth.text = product.month.toString() + "개월"
                paymentPrice.text = DefaultFormat.defaultNumberFormat(product.price.toInt()) + "원"
                gotoPayment.isClickable = true;
            }
            //받아오지 못함
            override fun onFailure(call: Call<ProductDto>, p1: Throwable) {
                Toast.makeText(this@ProductBuyActivity, p1.message, Toast.LENGTH_LONG).show();
            }
        })



        //결제시 상품아이디 같이 넘김
        //다음 페이지 이동 구현
        gotoPayment.setOnClickListener {
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
                            var intent = Intent(this@ProductBuyActivity, SubscribeBuyCompleteActivity::class.java)
                            intent.putExtra("nextPayment",LocalDate.now().plusMonths(product.month.toLong()).toString())
                            startActivity(intent)
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