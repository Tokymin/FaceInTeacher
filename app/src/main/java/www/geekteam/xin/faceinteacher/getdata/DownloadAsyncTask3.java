package www.geekteam.xin.faceinteacher.getdata;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Course;
import www.geekteam.xin.faceinteacher.view.XListView;

import static www.geekteam.xin.faceinteacher.MainActivity.mAdapter;
import static www.geekteam.xin.faceinteacher.MainActivity.mListView;


public class DownloadAsyncTask3 extends AsyncTask<String, Void, Boolean> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    Boolean flag = false;
    List<Course> list1;
    //	MyAdapter adapter; //自定义Adapter
    private static String jsonStr;
    String chooseid;
    String time;
    String starttime;
    String endtime;
    private ArrayList<HashMap<String, Object>> dlist;
    private static ArrayList<HashMap<String, Object>> alllist;
    String url = "http://geek-team.xin:8081/signPeriod/set";//url

    public DownloadAsyncTask3(Context context, String chooseid, String time, String starttime, String endtime) {
        super();
        this.context = context;
        this.chooseid = chooseid;
        this.endtime = endtime;
        this.starttime = starttime;
        this.time = time;
    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        url = url + "?course=" + chooseid + "&courseTime=" + time + "&startTime=" + starttime + "&endTime=" + endtime;
        Log.e("今晚好", "::" + url);
        //String result = byteArrayToStr(HttpUtils.getBytesByUrl("http://geek-team.xin:8081/signPeriod/set?course=(2017-2018-2)-1201600-050102-1&courseTime=43&startTime=2018-04-23 12:55&endTime=2018-04-24 00:00"));
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        //formBody.add("username","zhangsan");//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url("http://geek-team.xin:8081/signPeriod/set" +
                        "?course=(2017-2018-2)-1201600-050102-1&courseTime=43&startTime=2018-04-23 12:55&endTime=2018-04-24 00:00")
                .post(formBody.build())//传递请求体
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            Log.e("HomeFr", "????5" + response.body().string());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) try {
            throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }



           /*
            Log.e("HomeFr", "????4");
            url1 = new URL();
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "**");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.connect();
            Log.e("HomeFr", "????5" + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                Log.e("HomeFr", "????5");
                byte[] buffer = new byte[1024];
                int len = 0;
                baos = new ByteArrayOutputStream();
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                is.close();
                baos.close();
            } else {
                Log.e("HomeFr", "????6");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/
        return Boolean.TRUE;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            Toast.makeText(context, "获取数据失败！请稍后重试...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "获取数据失败！请稍后重试...", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }
}


