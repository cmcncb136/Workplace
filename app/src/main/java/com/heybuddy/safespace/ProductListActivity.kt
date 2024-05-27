package com.heybuddy.safespace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityProductsBinding
import com.heybuddy.safespace.dto.ProviderCategoryDto
import com.heybuddy.safespace.dto.ProviderDto
import com.heybuddy.safespace.product.ProductDetail
import com.heybuddy.safespace.product.ProductInfo
import com.heybuddy.safespace.service.ProviderCategoryService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductListActivity: AppCompatActivity() {

    private lateinit var bind: ActivityProductsBinding
    private lateinit var retrfoit: Retrofit
    private lateinit var providerCategoryService: ProviderCategoryService
    private val moveAdapter = ProductsListAdapter()
    private val bookAdapter = ProductsListAdapter()
    private val musicAdapter = ProductsListAdapter()
    private val listMap:HashMap<String, ListView> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        retrfoit = RetrofitSetting.getRetrofit()
        providerCategoryService = retrfoit.create(ProviderCategoryService::class.java)

        providerCategoryService.findProviderCategory()
            .enqueue(object: Callback<List<ProviderCategoryDto>>{
                override fun onResponse(p0: Call<List<ProviderCategoryDto>>, body: Response<List<ProviderCategoryDto>>) {
                    if(body.body() == null) return

                    val list:ArrayList<ProviderCategoryDto> = body.body() as ArrayList<ProviderCategoryDto>
                    Log.d("item", list.toString())

                    for(x in list){
                        Log.d("item", x.category.categoryName)
                        val adapter = listMap.get(x.category.categoryName)?.adapter as ProductsListAdapter?
                        adapter?.addItem(x.provider)
                        adapter?.notifyDataSetChanged()
                    }

                    for(v in listMap.values)
                        setListViewHeightBaseOnChildren(v)
                }

                override fun onFailure(p0: Call<List<ProviderCategoryDto>>, p1: Throwable) {
                }

            })


//
//        //Movie에 추가
//        moveAdapter.addItem(ProductInfo("Hello"))
//        moveAdapter.addItem(ProductInfo("Hello"))
//
//        //book에 추가
//        bookAdapter.addItem(ProductInfo("Hello"))
//        bookAdapter.addItem(ProductInfo("Hello"))
//        bookAdapter.addItem(ProductInfo("Hello"))
//        bookAdapter.addItem(ProductInfo("Hello"))
//
//        //music에 추가
//        musicAdapter.addItem(ProductInfo("Hello"))
//        musicAdapter.addItem(ProductInfo("Hello"))
//        musicAdapter.addItem(ProductInfo("Hello"))


        bind.productsMovie.adapter = bookAdapter
        setListViewHeightBaseOnChildren(bind.productsBook)

        bind.productsBook.adapter = moveAdapter
        setListViewHeightBaseOnChildren(bind.productsMovie)

        bind.productsMusic.adapter = musicAdapter
        setListViewHeightBaseOnChildren(bind.productsMusic)

        listMap.put("movie", bind.productsMovie)
        listMap.put("book", bind.productsBook)
        listMap.put("music", bind.productsMusic)
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


class ProductsListAdapter: BaseAdapter(){
    val providers: ArrayList<ProviderDto> = ArrayList()

    override fun getCount(): Int {
        return  providers.size
    }

    override fun getItem(position: Int): Any {
        return  providers[position]
    }

    override fun getItemId(position: Int): Long {
        return position as Long;
    }

    fun addItem(s: ProviderDto){
        providers.add(s)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val p = providers[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_products, parent, false)
        view.findViewById<TextView>(R.id.providerTv).text = p.name

        Glide.with(view) //이미지 서버로부터 가져오기
            .load(RetrofitSetting.URL +p.imgPath)
            .into(view.findViewById(R.id.providerIv))

        view.setOnClickListener { //페이지 이동
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("providerId", p.providerId);

            context.startActivity(intent)
        }
        return view
    }
}