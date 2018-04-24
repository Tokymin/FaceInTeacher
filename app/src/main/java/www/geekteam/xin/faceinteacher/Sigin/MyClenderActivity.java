package www.geekteam.xin.faceinteacher.Sigin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import www.geekteam.xin.faceinteacher.LoginActivity;
import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.getdata.DownloadAsyncTask;
import www.geekteam.xin.faceinteacher.getdata.DownloadAsyncTask4;
import www.geekteam.xin.faceinteacher.getdata.MyJsonUtils;
import www.geekteam.xin.faceinteacher.view.XListView;
import www.geekteam.xin.faceinteacher.view.XListView3;

public class MyClenderActivity extends Activity implements View.OnClickListener{
    private ViewPager viewPager;
    HashMap<Integer, MyClenderView> views = new HashMap<>();
    private LinkedList<MyClenderView> cache = new LinkedList();
    private TextView before, after;
    private TextView month;
    private Button find;
    private Calendar mCalenerDate;
    public static XListView3 mListView3;
    public static SimpleAdapter mAdapter3;//？？？
    public static List<Course> courseLists3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.drawable.backlog);
        toolbar.setTitleMarginStart(1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClenderActivity.this.finish();
            }
        });

        findView();
        handler = new Handler();
        initView();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        setTime(0);
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = viewPager.getCurrentItem();
                viewPager.setCurrentItem(item - 1);
            }
        });
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = viewPager.getCurrentItem();
                viewPager.setCurrentItem(item + 1);
            }
        });
    }

    private void findView() {
        find=(Button)findViewById(R.id.find);
        before =  findViewById(R.id.before);
        after = findViewById(R.id.after);
        month =  findViewById(R.id.month);
        viewPager =  findViewById(R.id.viewpager);
        mCalenerDate = Calendar.getInstance();
        mCalenerDate.setTime(new Date());
        mListView3 = (XListView3) findViewById(R.id.ListView_pick);//
        mListView3.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
//        handler3 = new Handler();
        courseLists3 = new ArrayList<Course>();
        find.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.find:
                Log.e("hahahha","::"+LoginActivity.tId+"::"+MyClenderView.currenttime);
                if(MyClenderView.currenttime==null){
                    Toast.makeText(this, "请选择时间", Toast.LENGTH_SHORT).show();
                }else{
                    String currentTime=MyClenderView.currenttime;
                    Log.e("hahahha","::"+LoginActivity.tId+"::"+currentTime);
                    new DownloadAsyncTask4(this,LoginActivity.tId,currentTime,courseLists3,handler).execute();
                }

        }

    }
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }

        ;
    };

    private void setTime(int number) {
        if (mCalenerDate != null) {
            Calendar calendar = (Calendar) mCalenerDate.clone();
            calendar.add(Calendar.MONTH, number);
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM");
            month.setText(simpleFormat.format(calendar.getTime()));
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void initView() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                int ss = position - Integer.MAX_VALUE / 2;
                Log.e("zjun", "ss:" + ss);
                MyClenderView view;
                if (views.size() > 0 && views.get(position) != null) {
                    view = views.get(position);
                } else {
                    view = new MyClenderView(container.getContext(), position - Integer.MAX_VALUE / 2);
                }
                container.addView(view);
                views.put(position, view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                cache.addLast((MyClenderView) object);
                views.remove(position);
                views.size();
                Log.e("zjun", "views个数：" + views.size());
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {
                int next = position - Integer.MAX_VALUE / 2;
                Log.e("zjun", "onPageSelected： " + next);
                setTime(next);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("zjun", "onPageScrollStateChanged");
            }
        });


    }


}

