package www.geekteam.xin.faceinteacher.Sigin;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import www.geekteam.xin.faceinteacher.R;

/**
 * Created by PC on 2018/4/20.
 */

public class MyClenderView extends LinearLayout
{
    private boolean isScrol;
    private int windowWidth;
    Calendar mCalenerDate;
    private MyGridView gridview;
    private boolean isNowMonth;
    public static String currenttime;
    List<MyDateBean> cells;
    private ClenderAdapter adapter;

    public MyClenderView(Context context, int numner) {
        super(context);
        init(context, numner);
    }

    public MyClenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, 0);
    }

    private void init(Context context, int number) {//number为当前日期加上number个月的日期
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();
        initViews(context);
        mCalenerDate = Calendar.getInstance();
        mCalenerDate.setTime(new Date());
        mCalenerDate.add(Calendar.MONTH, number);
        if (number == 0) { //0代表当前日期
            isNowMonth = true;
        }
        startShowCalernder(context);
    }


    /**
     * 初始化gridview
     * @param context
     */
    private void initViews(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.calender_view, this);
        gridview = view.findViewById(R.id.gridview);
        gridview.IsScrol(new MyGridView.ScroListener() {
            @Override
            public void scrol(boolean bl) {
                isScrol = bl;
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isScrol) {
                    updateItemBackground(i);
                }
            }
        });
    }

    /**
     * 更改日期选中状态
     * @param position
     */
    private void updateItemBackground(int position) {
        Calendar calender = (Calendar) mCalenerDate.clone();
        Date date=cells.get(position).getDate();
        if(date.getYear()+1900==calender.get(Calendar.YEAR)&&date.getMonth()==calender.get(Calendar.MONTH)) { //日历上显示的月份，即可选日期
            for (int i = 0; i < gridview.getChildCount(); i++) {
                cells.get(i).setSelect(false);
            }
            cells.get(position).setSelect(true);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 初始化日历
     * @param context
     */
    private void startShowCalernder(Context context) {
        Calendar calender = (Calendar) mCalenerDate.clone();
        calender.set(Calendar.DAY_OF_MONTH, 1);   //日期置为当月的第一天；
        int maxdays = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
        // calender.get(Calendar.DAY_OF_WEEK)    获取当月的第一天是星期几；
        int beforeMonthDays = calender.get(Calendar.DAY_OF_WEEK) - 1;//留给上一个月的天数；
        cells = new ArrayList<>();
        calender.add(Calendar.DAY_OF_MONTH, -beforeMonthDays);  //从上个月的几号开始
        while (cells.size() < beforeMonthDays + maxdays) {   //上个月加本月的个数
            Date date = calender.getTime();
            MyDateBean myDateBean = new MyDateBean(false, date);
            cells.add(myDateBean);
            calender.add(Calendar.DAY_OF_MONTH, 1);  //往后加一天
        }
        //循环完毕日期多加了一天变为下个月的第一天。
        calender.add(Calendar.DAY_OF_MONTH, -1);  //当月的最后一天
        int afterMonthDays = 7 - calender.get(Calendar.DAY_OF_WEEK); //下一个月多少天
        while (cells.size() < beforeMonthDays + maxdays + afterMonthDays) {
            calender.add(Calendar.DAY_OF_MONTH, 1);
            Date date = calender.getTime();
            MyDateBean myDateBean = new MyDateBean(false, date);
            cells.add(myDateBean);
        }
        adapter= new ClenderAdapter(context, cells);
        gridview.setAdapter(adapter);

    }

    private class ClenderAdapter extends BaseAdapter {

        Context context;
        List<MyDateBean> cells;

        public ClenderAdapter(Context context, List<MyDateBean> cells) {
            this.context = context;
            this.cells = cells;
        }

        @Override
        public int getCount() {
            return cells.size();
        }

        @Override
        public Object getItem(int i) {
            return cells.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int positon, View view, ViewGroup viewGroup)
        {
            MyHoder holer;
            if (view == null) {
                holer = new MyHoder();
                view = LayoutInflater.from(context).inflate(R.layout.calender_text, null, false);
                holer.text = (TextView) view.findViewById(R.id.text);
                LayoutParams lp = (LayoutParams) holer.text.getLayoutParams();
                int padingSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
                lp.width = (windowWidth - 2 * padingSpace) / 10;
                lp.height = lp.width;
                holer.text.setLayoutParams(lp);
                view.setTag(holer);
            } else {
                holer = (MyHoder) view.getTag();
            }
            Calendar calender = (Calendar) mCalenerDate.clone();
            Date date = cells.get(positon).getDate();
            if (isNowMonth && date.getMonth() == calender.get(Calendar.MONTH) && date.getYear() + 1900 == calender.get(Calendar.YEAR) && date.getDate() == calender.get(Calendar.DAY_OF_MONTH)) {
                holer.text.setTextColor(getResources().getColor(R.color.white));
            } else if (date.getMonth() == calender.get(Calendar.MONTH) && date.getYear() + 1900 == calender.get(Calendar.YEAR)) { //当前月
                holer.text.setTextColor(getResources().getColor(R.color.black));
            } else {
                holer.text.setTextColor(getResources().getColor(R.color.gray));
            }
            if (isNowMonth && date.getMonth() == calender.get(Calendar.MONTH) && date.getYear() + 1900 == calender.get(Calendar.YEAR) && date.getDate() == calender.get(Calendar.DAY_OF_MONTH)) {
                holer.text.setBackgroundResource(R.drawable.circle);
            } else if (cells.get(positon).isSelect()) {

                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1=cells.get(positon).getDate();

                //显示你点击的日期
                Toast.makeText(context,"你选择了："+simpleFormat.format(date1),Toast.LENGTH_SHORT).show();
                currenttime=simpleFormat.format(date1);
                holer.text.setBackgroundResource(R.drawable.check_nor);
            } else {
                holer.text.setBackgroundResource(0);
            }
            int day = date.getDate();
            holer.text.setText(day + "");
            return view;
        }

        public class MyHoder
        {
            TextView text;
        }
    }
}
