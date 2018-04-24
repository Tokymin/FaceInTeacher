package www.geekteam.xin.faceinteacher.getdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.Sigin.CourseListActivity;
import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.view.XListView;
import www.geekteam.xin.faceinteacher.view.XListView2;

import static www.geekteam.xin.faceinteacher.MainActivity.mAdapter;
import static www.geekteam.xin.faceinteacher.MainActivity.mListView;
import static www.geekteam.xin.faceinteacher.Sigin.CourseListActivity.mListView2;


public class DownloadAsyncTask2 extends AsyncTask<String, Void, byte[]> implements XListView2.IXListViewListener {
    @SuppressLint("StaticFieldLeak")
    Context context;
    Boolean flag = false;
    List<Course> list1;
    MyJsonUtils utils; //解析json数据的工具类
    //	MyAdapter adapter; //自定义Adapter
    String postdatas;//提交信息
    private static String jsonStr;
    Handler handler;
    private CheckBox checkBox;
    private ArrayList<HashMap<String, Object>> dlist;
    private static ArrayList<HashMap<String, Object>> alllist;
    String time;
    String chooseid;
    String url = "http://geek-team.xin:8081/schedule/getTeacherScheduleByDay";//url
    public DownloadAsyncTask2(Context context, String commitdatas, Handler handler1, List<Course> list, MyJsonUtils utils) {
        super();
        this.context = context;
        this.list1 = list;
        this.utils = utils;
        this.postdatas = commitdatas;
        this.handler = handler1;

    }
    @Override
    protected byte[] doInBackground(String... params) {
        Log.e("HomeFr", "????3" + postdatas);
        if (postdatas != null) {
            url = url + "?tId=" + postdatas;
            return HttpUtils.getBytesByUrl(url);
        }
        return null;
    }

    @Override
    protected void onPostExecute(byte[] result) {
        super.onPostExecute(result);
        if (result != null) {
            jsonStr = new String(result, 0, result.length);
            Log.e("HomeFr", "有没有获取到json????3" + jsonStr);
            List<Course> list2 = (List<Course>) utils.parseJson(jsonStr);
            list1.addAll(list2);
            try {
                mAdapter = new SimpleAdapter(context, getData(),
                        R.layout.listitem_xuanke, new String[]{"cChooseId","time","jieci",
                        "today_name", "today_addr"}, new int[]{R.id.chooseid,R.id.secret,R.id.today_jieci2,
                        R.id.today_name2, R.id.today_addr2});
                mListView2.setAdapter(mAdapter);
                mListView2.setXListViewListener(this);
                mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        checkBox=view.findViewById(R.id.xuanke_radio1);
                        checkBox.setChecked(true);
                        TextView tv_jieci=(TextView) view.findViewById(R.id.secret);
                        TextView tv_chooseid=(TextView) view.findViewById(R.id.chooseid);

//                        TextView tv_jieci=(TextView) view.findViewById(R.id.today_jieci2);
                         time=tv_jieci.getText().toString();//总的天数
                         chooseid=tv_chooseid.getText().toString();
                        new CourseListActivity().getdata(chooseid,time);
                        Log.e("ITSSEcret",":::"+time+":::"+chooseid);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "获取数据失败！请稍后重试...", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<HashMap<String, Object>> getData() throws JSONException {
        JSONArray array = new JSONArray(jsonStr);
        dlist = new ArrayList<HashMap<String, Object>>();
        if (array.length()==0) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("jieci", "今天");
            map.put("today_name", '\n'+"没有课哦");
            map.put("today_addr", " ");
            dlist.add(map);
        }
        Log.e("HomeFrnight", "什么鬼1::" + array.length());

        Comparator<HashMap<String, Object>> comparator = new Comparator<HashMap<String, Object>>(){
            @Override
            public int compare(HashMap<String, Object> s1, HashMap<String, Object> s2) {
                    return (int)(s1.get("j"))-(int)s2.get("j");
            }
        };
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj2 = array.optJSONObject(i);
            String sum = obj2.optString("time");
            int tianshu = Integer.valueOf(sum.substring(2, 3));//天数
            Log.e("HomeFrnight", "什么鬼2::" + tianshu);
            int jieci = Integer.valueOf(sum.substring(3,4));//节次
            Log.e("HomeFrnight", "什么鬼3::" + getDayOfWeek());
            Log.e("HomeFrnight", "不知道是否" + 1);
            String coursename = obj2.optString("cName");
            Log.e("HomeFrnight", "不知道是否" + coursename);
            String classroomname = obj2.optString("cAddress");
            Log.e("HomeFrnight", "不知道是否" + coursename);
            String teacher = obj2.optString("t_name");
            String coursemark = obj2.optString("cCredit");
            String ChooseId = obj2.optString("cChooseId");
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("cChooseId",ChooseId);
            map.put("time",sum);
            map.put("j",jieci);
            map.put("jieci", "第" + jieci + "-" + (jieci + 1) + "节");
            map.put("today_name", coursename);
            map.put("today_addr", classroomname);
            dlist.add(map);
        }

        Log.e("wwwrrrrrrr", ":::" + dlist);
        Collections.sort(dlist,comparator);
        return dlist;
    }
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {//停止刷新
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    getData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

}


