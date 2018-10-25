package com.example.lenovo.ranking;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class StartActivity extends AppCompatActivity {
    private Button login_btn;
    private  Button visitor_btn;
    private Random r;


    /**
     * 登陆函数
     * @param username 用户名
     * @param password 密码
     * @return 登陆成功，返回true，失败，返回false
     */
    protected Boolean login(String username, String password)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/user_info/login";
        try {
            URL url = new URL(req_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置POST请求
            connection.setRequestMethod("POST");
            // 设置可向服务器输出
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 打开连接
            connection.connect();

            // 打开连接后，向服务端写要提交的参数
            // 参数格式：“name=asdasdas&age=123123”
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("username=")
                    .append(username)
                    .append("&")
                    .append("password=")
                    .append(password);
            // 获取向服务器写数据的输出流
            connection.getOutputStream()
                    .write(stringBuilder.toString().getBytes());

            // 提交数据后，获取来自服务器的json数据
            if (connection.getResponseCode() == 200)
            {
                BufferedReader br = null;
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String json = "";
                String line = "";

                while ((line = br.readLine()) != null) {
                    json += line.trim();
                }
                // 解析
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.get("code").equals("True"))
                    return true;
                else
                    return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        login_btn = (Button)findViewById(R.id.login);
        visitor_btn = (Button)findViewById(R.id.s_visitor);


        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Intent intent = new Intent(StartActivity.this, BindingActivity.class);
//                startActivity(intent);

                String username = ((TextView)findViewById(R.id.username)).getText().toString();
                String pwd = ((TextView)findViewById(R.id.pwd)).getText().toString();

                if (login(username, pwd))
                {
                    GlobalData.setUsername(username);
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    alert_msg("Wrong username/password");
                }
            }
        });


        visitor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void alert_msg(String msg) {
        new  AlertDialog.Builder(this)
                .setTitle("Log in failed!" )
                .setMessage(msg)
                .setPositiveButton("OK!" ,  null )
                .show();
    }

}
