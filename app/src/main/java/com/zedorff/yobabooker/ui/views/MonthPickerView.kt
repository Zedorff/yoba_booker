package com.zedorff.yobabooker.ui.views

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.v4.util.Pair
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zedorff.yobabooker.R

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MonthPickerView : ViewPager {

    private val DEFAULT_START_YEAR = 1900
    private val DEFAULT_END_YEAR = 2100
    private val MONTH_PER_YEAR = 12

    private var mAdapter: YearAdapter? = null

    private val dateLiveData = MutableLiveData<Long>()

    val selectedTimeInMillis: LiveData<Long>
        get() = dateLiveData

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onFinishInflate() {
        super.onFinishInflate()
        val frame = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams = frame

        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                dateLiveData.value = mAdapter!!.getTimestamp(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        mAdapter = YearAdapter(context)
        adapter = mAdapter
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        currentItem = if (year in DEFAULT_START_YEAR..DEFAULT_END_YEAR) {
            year * MONTH_PER_YEAR - DEFAULT_START_YEAR * MONTH_PER_YEAR + month
        } else {
            0
        }
    }

    private inner class YearAdapter(context: Context) : PagerAdapter() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var minMonth: Int = 0
        private var count: Int = 0
        private val calendar = Calendar.getInstance()
        private val formatter = SimpleDateFormat("MMMM/yyyy", Locale.getDefault())

        init {
            val minMonth = DEFAULT_START_YEAR * 12
            val count = DEFAULT_END_YEAR * 12

            if (this.minMonth != minMonth || this.count != count) {
                this.minMonth = minMonth
                this.count = count
            }
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val v: TextView = inflater.inflate(R.layout.month_label_text_view, container, false) as TextView

            val year = Math.floor(((minMonth + position) / 12).toDouble()).toInt()
            val month = minMonth + position - year * 12
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)

            v.text = formatter.format(Date(calendar.timeInMillis))
            container.addView(v)
            return v
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as TextView)
        }

        override fun getCount(): Int {
            return count
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        fun getTimestamp(position: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, getYearForPosition(position))
            calendar.set(Calendar.MONTH, getMonthForPosition(position))
            return calendar.timeInMillis
        }

        fun getMonthForPosition(position: Int): Int {
            return minMonth + position - getYearForPosition(position) * MONTH_PER_YEAR
        }

        fun getYearForPosition(position: Int): Int {
            return Math.floor(((minMonth + position) / MONTH_PER_YEAR).toDouble()).toInt()
        }
    }
}
