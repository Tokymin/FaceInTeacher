package www.geekteam.xin.faceinteacher.Http;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import www.geekteam.xin.faceinteacher.Activity.LoginActivity;
import www.geekteam.xin.faceinteacher.Activity.MainActivity;

/**
 * Created by PC on 2018/4/18.
 */

public class LoginTask extends AsyncTask<Void,Integer,Boolean> {
            String url="http://geek-team.xin:8081/LAR/tLogin";
            Boolean res=false;
    @Override
    protected Boolean doInBackground(Void...voids) {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn =realUrl.openConnection();
            Log.d("dddd","connconn");
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
            out.print("tId=" + LoginActivity.tId + "&" + "tPassword=" + LoginActivity.tPwd);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = in.readLine();
            JSONObject object1 = new JSONObject(line);
            String result1 = object1.getString("result");
            Log.e(":::返回result:::", "==" + result1);
            res=true;
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
        return res;
    }



    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            Toast.makeText(LoginActivity.activity,"登录成功", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(LoginActivity.activity,MainActivity.class);
           LoginActivity.activity.startActivity(intent);
        }else {
            Toast.makeText(LoginActivity.activity,"登录失败",Toast.LENGTH_LONG).show();
        }
    }
}
