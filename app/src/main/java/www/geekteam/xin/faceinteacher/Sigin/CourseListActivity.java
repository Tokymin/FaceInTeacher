package www.geekteam.xin.faceinteacher.Sigin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import www.geekteam.xin.faceinteacher.ListViewAdapter;
import www.geekteam.xin.faceinteacher.LoginActivity;
import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.getdata.DownloadAsyncTask;
import www.geekteam.xin.faceinteacher.getdata.DownloadAsyncTask2;
import www.geekteam.xin.faceinteacher.getdata.DownloadAsyncTask3;
import www.geekteam.xin.faceinteacher.getdata.MyJsonUtils;

import www.geekteam.xin.faceinteacher.view.XListView2;

/**
 * Created by PC on 2018/4/20.
 */

public class CourseListActivity extends Activity implements View.OnClickListener{
    TextView startTime;
    TextView endTime;
    CustomDatePicker customDatePicker1;
    CustomDatePicker customDatePicker2;
    Context mContext;
    Button yes;
    public static XListView2 mListView2;
    public static SimpleAdapter mAdapter;//？？？
    public static List<Course> courseLists;
    static String time;
    static String chooseid;
    String starttime;
    String endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diandaoview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.backlog);
        toolbar.setTitleMarginStart(1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListActivity.this.finish();
            }
        });
        findView();
        initDatePicker();
        mListView2= (XListView2) findViewById(R.id.ListView_diandao);//
        handler = new Handler();
        courseLists = new ArrayList<Course>();

        new DownloadAsyncTask2(this, LoginActivity.tId, handler, courseLists, new MyJsonUtils()).execute();


    }
    public void getdata(String chooseid, String time){
        this.chooseid=chooseid;
        this.time=time;
    }
    private void findView() {
        startTime=findViewById(R.id.startTime);
        endTime=findViewById(R.id.endTime);
        yes=findViewById(R.id.yes);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        yes.setOnClickListener(this);
        mContext=this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTime:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(startTime.getText().toString());
                break;
            case  R.id.endTime:

                customDatePicker1.show(endTime.getText().toString());
                break;

            case R.id.yes:
                starttime=startTime.getText().toString();
                endtime=endTime.getText().toString();
                Log.e("TONIGHT",":::"+starttime);
                Log.e("TONIGHT",":::"+endtime);
                Log.e("TONIGHT",":::"+time);
                Log.e("TONIGHT",":::"+chooseid);
                new DownloadAsyncTask3(CourseListActivity.this,chooseid,time,starttime,endtime).execute();
                Toast.makeText(CourseListActivity.this,"点到成功",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }

        ;
    };
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
            endTime.setText(now);
            startTime.setText(now);
        customDatePicker1 = new CustomDatePicker(mContext, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的结束时间
                endTime.setText(time);
            }
        }, "2010-01-01 00:00", "2025-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(true); // 显示时和分
        customDatePicker1.setIsLoop(true); // 允许循环滚动


        customDatePicker2 = new CustomDatePicker(mContext, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                startTime.setText(time);
            }
        }, "2010-01-01 00:00", "2025-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动


    }
}
