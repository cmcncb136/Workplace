package com.heybuddy.safespace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityProductDetailBinding
import com.heybuddy.safespace.databinding.ActivitySubscribeListBinding
import com.heybuddy.safespace.product.ProductDetail
import com.heybuddy.safespace.subscribe.SubscribeInfo

class ProductDetailActivity: AppCompatActivity() {

    private lateinit var bind: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val adapter = ProductDetailListAdapter()


        adapter.addItem(ProductDetail("Hello"))
        adapter.addItem(ProductDetail("Hello"))
        adapter.addItem(ProductDetail("Hello"))
        adapter.addItem(ProductDetail("Hello"))
        adapter.addItem(ProductDetail("Hello"))
        adapter.addItem(ProductDetail("Hello"))
        adapter.addItem(ProductDetail("Hello"))

        //리스트 뷰 받아와서 넣기
        bind.ProductDetailListView.adapter = adapter
        setListViewHeightBaseOnChildren(bind.ProductDetailListView)

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
    val productDetail_: ArrayList<ProductDetail> = ArrayList()
    override fun getCount(): Int {
        return productDetail_.size
    }

    override fun getItem(position: Int): Any {
        return productDetail_[position]
    }

    override fun getItemId(position: Int): Long {
        return position as Long;
    }

    fun addItem(s: ProductDetail){
        productDetail_.add(s)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val s = productDetail_[position]


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_prodct_detail, parent, false)


        return view
    }

}
