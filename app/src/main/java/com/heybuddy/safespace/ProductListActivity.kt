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


        val moveAdapter = ProductsListAdapter()
        val bookAdapter = ProductsListAdapter()
        val musicAdapter = ProductsListAdapter()

        //Movie에 추가
        moveAdapter.addItem(ProductInfo("Hello"))
        moveAdapter.addItem(ProductInfo("Hello"))

        //book에 추가
        bookAdapter.addItem(ProductInfo("Hello"))
        bookAdapter.addItem(ProductInfo("Hello"))
        bookAdapter.addItem(ProductInfo("Hello"))
        bookAdapter.addItem(ProductInfo("Hello"))

        //music에 추가
        musicAdapter.addItem(ProductInfo("Hello"))
        musicAdapter.addItem(ProductInfo("Hello"))
        musicAdapter.addItem(ProductInfo("Hello"))


        bind.productsMovie.adapter = bookAdapter
        setListViewHeightBaseOnChildren(bind.productsBook)

        bind.productsBook.adapter = moveAdapter
        setListViewHeightBaseOnChildren(bind.productsMovie)

        bind.productsMusic.adapter = musicAdapter
        setListViewHeightBaseOnChildren(bind.productsMusic)
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