package www.geekteam.xin.faceinteacher.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.bean.Student;
import www.geekteam.xin.faceinteacher.getdata.DownloadAsyncTask5;
import www.geekteam.xin.faceinteacher.view.XListView;
import www.geekteam.xin.faceinteacher.view.XListView2;
import www.geekteam.xin.faceinteacher.view.XListView3;

public class DetailSignInActivity extends Activity {
    private Handler mHandler;
    public static XListView mListView;
    public static List<Student> studentLists;
    private ArrayList<HashMap<String, Object>> dlist;
    public static XListView mListView4;
    public static SimpleAdapter mAdapter4;//？？？
    String chooseid,time;
    private int which=0;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sign_in);
        chooseid=getIntent().getStringExtra("chooseid");
        time=getIntent().getStringExtra("time");
        studentLists = new ArrayList<Student>();

        Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
        toolbar3.setNavigationIcon(R.drawable.backlog);
        toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findview();
        setRadioGroupListener();
    }

    private void findview(){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        dlist = new ArrayList<HashMap<String, Object>>();
        mListView4 = (XListView)findViewById(R.id.ListView_student);// 这个listview是在这个layout里面
        mListView4.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        mHandler = new Handler();
    }
    public void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_yiqian:
                        new DownloadAsyncTask5(DetailSignInActivity.this,chooseid,time,studentLists,mHandler,which).execute();
                        break;
                    case R.id.rb_weiqian:
                        which=1;
                        new DownloadAsyncTask5(DetailSignInActivity.this,chooseid,time,studentLists,mHandler,which).execute();
                        break;
                }
            }
        });
    }

//    /** 初始化本地数据 */
//    String data[] = new String[] { "Toky","xiao"};
//    String data1[]=new String[] {"2013-3-1","2018-2-13"};
//


}
