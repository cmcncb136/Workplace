package com.heybuddy.safespace.basic_component

import android.widget.ListView

class AdapterSetting {
     companion object {
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
}