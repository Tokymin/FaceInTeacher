package www.geekteam.xin.faceinteacher.Http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Student;
import www.geekteam.xin.faceinteacher.view.XListView;

import static www.geekteam.xin.faceinteacher.Activity.DetailSignInActivity.mAdapter4;
import static www.geekteam.xin.faceinteacher.Activity.DetailSignInActivity.mListView4;
import static www.geekteam.xin.faceinteacher.Activity.MyClenderActivity.mAdapter3;
import static www.geekteam.xin.faceinteacher.Activity.MyClenderActivity.mListView3;


public class DownloadAsyncTask5 extends AsyncTask<String, Void, byte[]> implements XListView.IXListViewListener {
    @SuppressLint("StaticFieldLeak")
    Context context;
    Boolean flag = false;
    MyJsonUtils2 utils; //解析json数据的工具类
    List<Student> list1;
    //	MyAdapter adapter; //自定义Adapter
    private static String jsonStr;
    private String chooseid;
    private String time;
    private Handler handler3;
    private int which;
    private ArrayList<HashMap<String, Object>> dlist;
    String url = "http://geek-team.xin:8081/signIn/getSignInfo";//url

    public DownloadAsyncTask5(Context context, String chooseid, String time, List<Student> list, Handler handler1, int which) {
        super();
        this.context = context;
        this.chooseid = chooseid;
        this.time = time;
        this.list1=list;
        this.handler3=handler1;
        this.which=which;
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

        if ((chooseid != null) || (time != null)) {
            url = url + "?course=" + chooseid + "&courseTime=" + time;
            Log.e("hahaha::","--"+url);
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
            utils = new MyJsonUtils2();
            List<Student> list2 = (List<Student>) utils.parseJson(jsonStr);
            list1.addAll(list2);
            try {
                mAdapter4= new SimpleAdapter(context, getData(),
                        R.layout.listitem_stu, new String[]{"stu_id", "stu_name", "sign_time",
                        }, new int[]{R.id.tv_stu_id, R.id.tv_stu_name, R.id.tv_sign_time,
                        });
                mListView4.setAdapter(mAdapter4);
                mListView4.setXListViewListener(this);
                mListView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

        if(which==1){//代表已签
            JSONObject jsonObject=new JSONObject(jsonStr);
            Pattern p=Pattern.compile("\\\\");
            Matcher m=p.matcher(jsonObject.getString("notSign"));
            JSONArray jsonArray=new JSONArray(m.replaceAll(""));
            Log.e("HomeFrnightoo1",m.replaceAll(""));
            Log.e("HomeFrnightoo", "什么鬼1::" + jsonArray.length());
            dlist = new ArrayList<HashMap<String, Object>>();
            if (jsonArray.length()==0) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("stu_id", " ");
                map.put("stu_name", '\n'+" ");
                map.put("sign_time", " ");
                dlist.add(map);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj2 = jsonArray.optJSONObject(i);
                Log.e("HomeFrnightoo", "不知道是否" + 1);
                String stu_id = obj2.optString("s_id");
                Log.e("HomeFrnightoo", "不知道是否" + stu_id);
                String stu_name = obj2.optString("s_name");
                Log.e("HomeFrnightoo", "不知道是否" + stu_name);
                String sign_time = obj2.optString("sign_time");
                LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("stu_id", stu_id);
                map.put("stu_name", stu_name);
                map.put("sign_time", sign_time);
                dlist.add(map);
            }
        }else if(which==0){
            JSONObject jsonObject=new JSONObject(jsonStr);
            Pattern p=Pattern.compile("\\\\");
            Matcher m=p.matcher(jsonObject.getString("hasSign"));
            JSONArray jsonArray=new JSONArray(m.replaceAll(""));
            Log.e("HomeFrnightoo2",m.replaceAll(""));
            Log.e("HomeFrnightoo", "什么鬼1::" + jsonArray.length());
            dlist = new ArrayList<HashMap<String, Object>>();
            if (jsonArray.length()==0) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("stu_id", " ");
                map.put("stu_name", '\n'+" ");
                dlist.add(map);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj2 = jsonArray.optJSONObject(i);
                Log.e("HomeFrnightoo", "不知道是否" + 1);
                String stu_id = obj2.optString("s_id");
                Log.e("HomeFrnightoo", "不知道是否" + stu_id);
                String stu_name = obj2.optString("s_name");
                Log.e("HomeFrnightoo", "不知道是否" + stu_name);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("stu_id", stu_id);
                map.put("stu_name", stu_name);
                dlist.add(map);
            }
        }


        Log.e("wwwrrrrrrr", ":::" + dlist);
//        Collections.sort(dlist, comparator);
        return dlist;
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
                mListView3.setAdapter(mAdapter4);
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

