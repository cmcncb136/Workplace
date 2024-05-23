package com.heybuddy.safespace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivityProductsBinding
import com.heybuddy.safespace.product.ProductInfo

class ProductListActivity: AppCompatActivity() {

    private lateinit var bind: ActivityProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val adapter = ProductsListAdapter()

        adapter.addItem(ProductInfo("Hello"))
        adapter.addItem(ProductInfo("Hello"))
        adapter.addItem(ProductInfo("Hello"))
        adapter.addItem(ProductInfo("Hello"))

        bind.productsBook.adapter = adapter
        setListViewHeightBaseOnChildren(bind.productsBook)

        bind.productsBook.adapter = adapter
        setListViewHeightBaseOnChildren(bind.productsMovie)
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
    val products: ArrayList<ProductInfo> = ArrayList()

    override fun getCount(): Int {
        return  products.size
    }

    override fun getItem(position: Int): Any {
        return  products[position]
    }

    override fun getItemId(position: Int): Long {
        return position as Long;
    }

    fun addItem(s: ProductInfo){
        products.add(s)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val s = products[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_products, parent, false)

        return view
    }
}