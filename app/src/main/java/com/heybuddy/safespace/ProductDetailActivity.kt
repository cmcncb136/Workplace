package com.heybuddy.safespace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.heybuddy.safespace.basic_component.AdapterSetting
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityProductDetailBinding
import com.heybuddy.safespace.databinding.ActivitySubscribeListBinding
import com.heybuddy.safespace.dto.ProductDto
import com.heybuddy.safespace.product.ProductDetail
import com.heybuddy.safespace.service.ProductService
import com.heybuddy.safespace.subscribe.SubscribeInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductDetailActivity: AppCompatActivity() {

    private lateinit var bind: ActivityProductDetailBinding
    private lateinit var retrofit: Retrofit
    private lateinit var back_icon_detail: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        //retrofit에 retrofit 받아옴 (service 객체에서 받아옴)
        retrofit = RetrofitSetting.getRetrofit()
        val productService: ProductService = retrofit.create(ProductService::class.java)

        val providerId = this.intent.getStringExtra("providerId")?:"a";

        val adapter = ProductDetailListAdapter()

        back_icon_detail = bind.backIconDetail

        //리스트 뷰 받아와서 넣기
        bind.ProductDetailListView.adapter = adapter
        AdapterSetting.setListViewHeightBaseOnChildren(bind.ProductDetailListView)

        //call에 content product로 providerId를 가져와서 뿌려줌 (현재 전체 정보를 뿌려줌)
        //val call = productService.findByProviderIdProduct(providerId)
        val call = productService.findAllProduct()

        //통신
        call.enqueue(object: Callback<List<ProductDto>>{
            //받아옴
            override fun onResponse(p0: Call<List<ProductDto>>, body : Response<List<ProductDto>>) {
                //받아온 상품 id가 null -> 지난 activity로 돌려보내줌
                if(body.body() == null) {
                    Toast.makeText(this@ProductDetailActivity, "존재하지 않는 제공자입니다.", Toast.LENGTH_SHORT).show();
                    startActivity(Intent(this@ProductDetailActivity, ProductListActivity::class.java))
                    finish()
                    return
                }

                val products = body.body()!!

                for(p in products){
                    adapter.addItem(p)
                }

               adapter.notifyDataSetChanged()
            }
            //받아오지 못함
            override fun onFailure(p0: Call<List<ProductDto>>, p1: Throwable) {
                Toast.makeText(this@ProductDetailActivity, p1.message, Toast.LENGTH_LONG).show();
            }
        })


        //뒤로 가기 버튼 선택 시 뒤로 가기
        back_icon_detail.setOnClickListener {
            Toast.makeText(this, "콘텐츠 제공업체 선택 페이지로 이동합니다.", Toast.LENGTH_LONG).show();
            val i = Intent(this, ProductListActivity::class.java)
            startActivity(i)
        }

    }

}

class ProductDetailListAdapter: BaseAdapter(){
    val products: ArrayList<ProductDto> = ArrayList()
    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(position: Int): Any {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return position as Long;
    }

    fun addItem(p: ProductDto){
        products.add(p)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val p = products[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_prodct_detail, parent, false)

        //상품명, IMG, 수용 인원, 금액
        //findViewById<TextView>(R.id.content_type).text = p.companyname.toString()

        Glide.with(view)
            .load(RetrofitSetting.URL+ p.imgPath)
            .into(view.findViewById(R.id.company_icon))

        view.findViewById<TextView>(R.id.product_cap).text = p.capacity.toString()
        view.findViewById<TextView>(R.id.product_price).text = p.price.toString()

        //페이지 이동
        view.setOnClickListener {
            val intent = Intent(context, ProductBuyActivity::class.java)
            intent.putExtra("productId", p.productId);

            context.startActivity(intent)
        }


        return view
    }

}
