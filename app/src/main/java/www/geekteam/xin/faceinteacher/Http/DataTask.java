package www.geekteam.xin.faceinteacher.Http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import www.geekteam.xin.faceinteacher.Activity.LoginActivity;

/**
 * 从服务器获取课程数据信息
 * Created by PC on 2018/4/18.
 */

public class DataTask extends AsyncTask<Void,Integer,Boolean> {
                Boolean result=false;
              public  static   String courseName[];
              public  static   String courseTime[];
              public  static   String courseRoom[];
    @Override
    protected Boolean doInBackground(Void... voids) {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL("");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print("tId=" + LoginActivity.tId);//给服务器发送参数教职工编号
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，返回的数据
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = in.readLine();
            JSONArray array=new JSONArray(line);
            int length= array.length();
            courseName=new String[length];
            courseRoom=new String[length];
            courseTime=new String[length];
            for(int i=0;i<length;i++){
                courseName[i]= array.getJSONObject(i).getString("name");
                courseTime[i]=array.getJSONObject(i).getString("time");
                courseRoom[i]=array.getJSONObject(i).getString("room");
            }
            for (int i=0;i<length;i++){
                Log.d("TAG","COURSE"+courseName[i]);
            }
            //获取服务器返回的Json数据，解析得到所需数据
            result=true;
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {

    }
}
