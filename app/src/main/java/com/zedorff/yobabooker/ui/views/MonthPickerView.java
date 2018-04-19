package com.zedorff.yobabooker.ui.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zedorff.yobabooker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthPickerView extends ViewPager {

    public final int DEFAULT_START_YEAR = 1900;
    public final int DEFAULT_END_YEAR = 2100;
    public final int MONTH_PER_YEAR = 12;

    private YearAdapter mAdapter;

    private MutableLiveData<Long> dateLiveData = new MutableLiveData<>();

    public MonthPickerView(@NonNull Context context) {
        super(context);
    }

    public MonthPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final ViewGroup.LayoutParams frame = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(frame);

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dateLiveData.setValue(mAdapter.getTimestamp(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAdapter = new YearAdapter(getContext());
        setAdapter(mAdapter);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (year >= DEFAULT_START_YEAR && year <= DEFAULT_END_YEAR) {
            setCurrentItem(year * MONTH_PER_YEAR - DEFAULT_START_YEAR * MONTH_PER_YEAR + month);
        } else {
            setCurrentItem(0);
        }
    }

    public LiveData<Long> getSelectedTimeInMillis() {
        return dateLiveData;
    }

    private class YearAdapter extends PagerAdapter {

        private final LayoutInflater inflater;
        private int minMonth;
        private int count;
        private Calendar calendar = Calendar.getInstance();
        private SimpleDateFormat formatter = new SimpleDateFormat("MMMM/yyyy", Locale.getDefault());

        public YearAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            final int minMonth = DEFAULT_START_YEAR * 12;
            final int count = DEFAULT_END_YEAR * 12;

            if (this.minMonth != minMonth || this.count != count) {
                this.minMonth = minMonth;
                this.count = count;
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final TextView v;
            v = (TextView) inflater.inflate(R.layout.month_label_text_view, container, false);

            int year = (int) Math.floor((minMonth + position) / 12);
            int month = (minMonth + position) - year * 12;
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);

            v.setText(formatter.format(new Date(calendar.getTimeInMillis())));
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((TextView) object);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public long getTimestamp(int position) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, getYearForPosition(position));
            calendar.set(Calendar.MONTH, getMonthForPosition(position));
            return calendar.getTimeInMillis();
        }

        public int getMonthForPosition(int position) {
            return (minMonth + position) - getYearForPosition(position) * MONTH_PER_YEAR;
        }

        public int getYearForPosition(int position) {
            return (int) Math.floor((minMonth + position) / MONTH_PER_YEAR);
        }
    }
}
