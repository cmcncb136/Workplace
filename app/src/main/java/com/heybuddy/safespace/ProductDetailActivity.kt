package com.heybuddy.safespace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        retrofit = RetrofitSetting.getRetrofit()
        val productService: ProductService = retrofit.create(ProductService::class.java)

        val providerId = this.intent.getStringExtra("providerId")?:"a";

        val adapter = ProductDetailListAdapter()


        //리스트 뷰 받아와서 넣기
        bind.ProductDetailListView.adapter = adapter
        setListViewHeightBaseOnChildren(bind.ProductDetailListView)


        //val call = productService.findByProviderIdProduct(providerId)
        val call = productService.findAllProduct()

        call.enqueue(object: Callback<List<ProductDto>>{
            override fun onResponse(p0: Call<List<ProductDto>>, body : Response<List<ProductDto>>) {
                if(body.body() == null) {
                    Toast.makeText(this@ProductDetailActivity, "그런 제공자는 없습니다.", Toast.LENGTH_SHORT).show();
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

            override fun onFailure(p0: Call<List<ProductDto>>, p1: Throwable) {
                Toast.makeText(this@ProductDetailActivity, p1.message, Toast.LENGTH_LONG).show();
            }

        })


    }

    fun setListViewHeightBaseOnChildren(list: ListView){
        val adapter = list.adapter ?: return
        var totalHeight = 0
        for(i in 0 until adapter.count){
            val item = adapter.getView(i, null, list)
            item.measure(0, 0)
            totalHeight += item.measuredHeight
        }

        val params = list.layoutParams
        params.height = totalHeight + (list.dividerHeight * (list.count) - 1)
        list.layoutParams = params
        list.requestLayout()
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
        Log.d("itemList","value : " + position.toString())
        return 0;
    }

    fun addItem(p: ProductDto){
        products.add(p)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val p = products[position]


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_prodct_detail, parent, false)
        view.findViewById<TextView>(R.id.product_price).text = p.price.toString()
        view.findViewById<TextView>(R.id.product_cap).text = p.capacity.toString()

        Glide.with(view) //이미지 서버로부터 가져오기
            .load(RetrofitSetting.URL +p.imgPath)
            .into(view.findViewById(R.id.companyicon))

        view.setOnClickListener {
            val intent = Intent(context, ProductBuyActivity::class.java)
            intent.putExtra("productId", p.productId)
            context.startActivity(intent)
        }

        return view
    }
}
