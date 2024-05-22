package com.heybuddy.safespace

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.heybuddy.safespace.databinding.ActivitySubscribeListBinding
import com.heybuddy.safespace.subscribe.SubscribeInfo

class SubscribeListActivity: AppCompatActivity() {
    private lateinit var bind: ActivitySubscribeListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySubscribeListBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val adapter = SubscribesListAdapter()


        adapter.addItem(SubscribeInfo("Tving"))
        adapter.addItem(SubscribeInfo("밀리"))
        adapter.addItem(SubscribeInfo("지니"))
        adapter.addItem(SubscribeInfo("Hello"))
        adapter.addItem(SubscribeInfo("Hello"))
        adapter.addItem(SubscribeInfo("Hello"))
        adapter.addItem(SubscribeInfo("Hello"))






        bind.subscribeListView.adapter = adapter
        setListViewHeightBaseOnChildren(bind.subscribeListView)
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

class SubscribesListAdapter: BaseAdapter(){
    val subscribes: ArrayList<SubscribeInfo> = ArrayList()
    override fun getCount(): Int {
        return subscribes.size
    }

    override fun getItem(position: Int): Any {
        return subscribes[position]
    }

    override fun getItemId(position: Int): Long {
        return position as Long;
    }

    fun addItem(s: SubscribeInfo){
        subscribes.add(s)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val s = subscribes[position]


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_subscribe, parent, false)
        view.findViewById<TextView>(R.id.subscribe_nameTv).text = s.productName

        return view
    }

}