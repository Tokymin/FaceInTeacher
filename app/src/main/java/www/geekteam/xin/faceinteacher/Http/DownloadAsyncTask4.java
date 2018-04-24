package www.geekteam.xin.faceinteacher.Http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import www.geekteam.xin.faceinteacher.Activity.DetailSignInActivity;
import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.view.XListView3;

import static www.geekteam.xin.faceinteacher.Activity.MyClenderActivity.mAdapter3;
import static www.geekteam.xin.faceinteacher.Activity.MyClenderActivity.mListView3;


public class DownloadAsyncTask4 extends AsyncTask<String, Void, byte[]> implements XListView3.IXListViewListener {
    @SuppressLint("StaticFieldLeak")
    Context context;
    Boolean flag = false;
    MyJsonUtils utils; //解析json数据的工具类
    List<Course> list1;
    //	MyAdapter adapter; //自定义Adapter
    private static String jsonStr;
    private String tid;
    private String today;
    private Handler handler3;
    private ArrayList<HashMap<String, Object>> dlist;
    private static ArrayList<HashMap<String, Object>> alllist;
    String url = "http://geek-team.xin:8081/schedule/getTeacherScheduleByOneDay";//url

    public DownloadAsyncTask4(Context context, String tId, String today,List<Course> list,Handler handler1) {
        super();
        this.context = context;
        this.tid = tId;
        this.today = today;
        this.list1=list;
        this.handler3=handler1;

    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    @Override
    protected byte[] doInBackground(String... params) {

        if ((tid != null) || (today != null)) {
            url = url + "?tId=" + tid + "&oneDay=" + today;
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
            utils = new MyJsonUtils();
            List<Course> list2 = (List<Course>) utils.parseJson(jsonStr);
            list1.addAll(list2);
            try {
                mAdapter3 = new SimpleAdapter(context, getData(),
                        R.layout.listitem_today, new String[]{"cChooseId", "time", "jieci",
                        "today_name", "today_addr"}, new int[]{R.id.chooseid1, R.id.secret1, R.id.today_jieci,
                        R.id.today_name, R.id.today_addr});
                mListView3.setAdapter(mAdapter3);
                mListView3.setXListViewListener(this);
                mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tv_jieci = (TextView) view.findViewById(R.id.secret1);
                        TextView tv_chooseid = (TextView) view.findViewById(R.id.chooseid1);
                        String time = tv_jieci.getText().toString();//总的天数
                        String chooseid = tv_chooseid.getText().toString();
//                        new DiandaoActivity().getdata(chooseid, time);
//                          new MyClenderActivity().getdata(chooseid,time);
                        Intent i1=new Intent(context, DetailSignInActivity.class);
                        i1.putExtra("chooseid",chooseid);
                        i1.putExtra("time",time);
                        context.startActivity(i1);
                        Log.e("ITSSEcret11", ":::" + time + ":::" + chooseid);
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
        Log.e("HomeFrnight", "什么鬼1::" + array.length());
        dlist = new ArrayList<HashMap<String, Object>>();
        if (array.length()==0) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("jieci", "今天");
            map.put("today_name", '\n'+"没有课哦");
            map.put("today_addr", " ");
            dlist.add(map);
        }
        Comparator<HashMap<String, Object>> comparator = new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> s1, HashMap<String, Object> s2) {
                return (int) (s1.get("j")) - (int) s2.get("j");
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
            map.put("cChooseId", ChooseId);
            map.put("time", sum);
            map.put("j", jieci);
            map.put("jieci", "第" + jieci + "-" + (jieci + 1) + "节");
            map.put("today_name", coursename);
            map.put("today_addr", classroomname);
            dlist.add(map);
        }

        Log.e("wwwrrrrrrr", ":::" + dlist);
        Collections.sort(dlist, comparator);
        return dlist;
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    @Override
    public void onRefresh() {
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListView3.setAdapter(mAdapter3);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {//停止刷新
        handler3.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    getData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter3.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        mListView3.stopRefresh();
        mListView3.stopLoadMore();
        mListView3.setRefreshTime("刚刚");
    }
}

