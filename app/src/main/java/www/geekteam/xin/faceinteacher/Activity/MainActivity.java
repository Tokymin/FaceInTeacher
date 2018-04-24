package www.geekteam.xin.faceinteacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import www.geekteam.xin.faceinteacher.R;

import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.Http.DownloadAsyncTask;
import www.geekteam.xin.faceinteacher.Http.MyJsonUtils;
import www.geekteam.xin.faceinteacher.view.XListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPaper;
    private List<View> dots;
    private int currentItem;
    private int oldPosition = 0;
    private List<ImageView> images;
    private ScheduledExecutorService scheduledExecutorService;
    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };
    private String[]  titles = new String[]{
            "FaceIn",
            "人脸识别",
            "签到app",
            "一款基于人脸识别的签到app",
            "FaceIn"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private List<Course> CourseList = new ArrayList<>();
    public static MainActivity activity;
    private Button bt_start,bt_history,bt_lookdetail,bt_more;
    private ImageView iv_start,iv_history,iv_lookdetail,iv_more;
    public static XListView mListView;
    private TextClock textClock;
    public static SimpleAdapter mAdapter;//？？？
    public static List<Course> courseLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        activity = this;
        title.setText(titles[0]);
        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                oldPosition = position;
                currentItem = position;
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        mListView = (XListView) findViewById(R.id.ListView_today);//
        mListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        handler = new Handler();
        courseLists = new ArrayList<Course>();
        new DownloadAsyncTask(this, LoginActivity.tId, handler, courseLists, new MyJsonUtils()).execute();

    }
    private void findview() {
        textClock= findViewById(R.id.textClock);
        textClock.setFormat24Hour("yyyy-MM-dd");
        title = (TextView)findViewById(R.id.titles);
        mViewPaper = (ViewPager) findViewById(R.id.vp);
        //开始点到
        bt_start=(Button) findViewById(R.id.bt_diandao);
        bt_history=(Button)findViewById(R.id.bt_histroy);
        bt_lookdetail=(Button)findViewById(R.id.bt_lookdetail);
        bt_more=(Button)findViewById(R.id.bt_more);

        iv_start=(ImageView)findViewById(R.id.iv_diandao);
        iv_history=(ImageView)findViewById(R.id.iv_history);
        iv_lookdetail=(ImageView)findViewById(R.id.iv_lookdetail);
        iv_more=(ImageView)findViewById(R.id.iv_more);
//显示的小点
        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        bt_start.setOnClickListener(this);
        bt_history.setOnClickListener(this);
        bt_lookdetail.setOnClickListener(this);
        bt_more.setOnClickListener(this);
        iv_start.setOnClickListener(this);
        iv_history.setOnClickListener(this);
        iv_lookdetail.setOnClickListener(this);
        iv_more.setOnClickListener(this);
    }
    /**
     * 自定义Adapter
     *
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {

            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(images.get(position));
            return images.get(position);
        }

    }
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }

        ;
    };
    public  Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_diandao:
            case R.id.iv_diandao:
                Intent i1 = new Intent(MainActivity.this, DiandaoActivity.class);
                startActivity(i1);
                break;
            case R.id.bt_histroy:
            case R.id.iv_history:
                Intent i2 = new Intent(MainActivity.this, MyClenderActivity.class);
                startActivity(i2);
                break;
            case R.id.bt_lookdetail:
            case R.id.iv_lookdetail:
                Intent i3 = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(i3);
                break;
            case R.id.bt_more:
            case R.id.iv_more:
                Toast.makeText(this, "更多功能尚在开发中...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        if(scheduledExecutorService!=null){
            scheduledExecutorService.shutdown();
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ViewPageTask(), 2, 2, TimeUnit.SECONDS);
    }


    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }



}
