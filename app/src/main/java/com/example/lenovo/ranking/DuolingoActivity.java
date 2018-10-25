package com.example.lenovo.ranking;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DuolingoActivity extends AppCompatActivity {
    private Button btn;

    protected JSONObject link_duolingo(String username, String duo_name, String duo_pwd)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/user_info/LinkDuolingo";
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
                    .append("duo_username=")
                    .append(duo_name).append("&").append("duo_password=").append(duo_pwd);
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
                return jsonObject;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duolingo);
        btn = (Button)findViewById(R.id.btn_done);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String username = GlobalData.getUsername();
                String duo_name = ((TextView)findViewById(R.id.app_username)).getText().toString();
                String duo_pwd = ((TextView)findViewById(R.id.app_password)).getText().toString();

                try
                {
                    JSONObject link_json = link_duolingo(username, duo_name, duo_pwd);
                    if (link_json.get("code").equals("True"))
                    {
                        Intent intent = new Intent(DuolingoActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        alert_msg("Binding duolingo failed", link_json.get("msg").toString());
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }


//                Intent intent = new Intent(DuolingoActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    protected void alert_msg(String title, String msg) {
        new  AlertDialog.Builder(this)
                .setTitle("Sign up Failed!" )
                .setMessage(msg)
                .setPositiveButton("OK!" ,  null )
                .show();
    }
}
