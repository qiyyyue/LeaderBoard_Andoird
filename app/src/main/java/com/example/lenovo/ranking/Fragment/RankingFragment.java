package com.example.lenovo.ranking.Fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lenovo.ranking.ChartUtils;
import com.example.lenovo.ranking.GlobalData;
import com.example.lenovo.ranking.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RankingFragment extends Fragment {
    public RankingFragment() {
        // Required empty public constructor
    }
    private RecyclerView mRecyclerViewAll;

    private Spinner spTime,spApp;
    private LineChart mLineChar1,mLineChar2;
    private LineData lineData;
    private XAxis xAxis;
    //////////////////////////////////////////////////////////////////////////////////////////
    private String[] listTime = {"latest week","latest month","latest year"};
    private String[] listApp = {"Duolingo","All"};
    //////////////////////////////////////////////////////////////////////////////////////////
    private ArrayAdapter<String> timeAdapter;
    private ArrayAdapter<String> appAdapter;

    /**
     * 获取用户历史分数
     * @param username 用户名
     * @param period 时间长 Weekly, Monthly
     * @return 返回一定周期的历史分数
     */
    protected JSONArray get_history_points(String username, String period)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/leader_board/GetHistoryPoint";
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
            stringBuilder.append("username=").append(GlobalData.getUsername())
                         .append("&period=").append(period);
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
                {
//                    System.out.println((jsonObject.get("leader_board_list")).getClass());
//                    System.out.println("ttttttttttttttttttttt" + jsonObject.get("point"));
                    System.out.println("------------class:   " + (jsonObject.get("history_point_list")).getClass());
                    return (JSONArray) jsonObject.get("history_point_list");
                }
                else
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }


    /**
     * 获取历史排名
     * @param username 用户名
     * @param period 周期
     * @return
     */
    protected JSONArray get_history_ranks(String username, String period)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/leader_board/GetHistoryRank";
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
            stringBuilder.append("username=").append(GlobalData.getUsername())
                    .append("&period=").append(period);
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
                {
//                    System.out.println((jsonObject.get("leader_board_list")).getClass());
//                    System.out.println("ttttttttttttttttttttt" + jsonObject.get("point"));
                    System.out.println("------------class:   " + (jsonObject.get("history_rank_list")).getClass());
                    return (JSONArray) jsonObject.get("history_rank_list");
                }
                else
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSpinner(view);

        mLineChar1=(LineChart)view.findViewById(R.id.mLineChar1);
        ChartUtils.initChart(mLineChar1);
        mLineChar2=(LineChart)view.findViewById(R.id.mLineChar2);
        ChartUtils.initChart(mLineChar2);
        //valueType:
        //0 week
        //1 month
        //2 year
        ChartUtils.notifyDataSetChanged(mLineChar1, getMyData(),getAverageData(),0);
        ChartUtils.notifyDataSetChanged(mLineChar2, get_history_rank_data(),null,0);

    }

/////////////////////////////////////////////////////////////
//填充折线图的数据
    private List<Entry> getMyData()
    {
        JSONArray point_list = get_history_points(GlobalData.getUsername(), "Weekly");

        List<Entry> entries = new ArrayList<Entry>();
        try
        {
            for(int i=0;i<7;i++)
            {
                int tmp_point = (int)(point_list.get(i));
//                System.out.println(tmp_point);
                entries.add(new Entry(i, tmp_point));
            }
                //entries.add(new Entry(i, Float.parseFloat(point_list.getJSONObject(i).toString())));
        }
        catch(Exception e)
        {
            System.out.println("erro111111111111" + e.toString());
        }
        return entries;
    }
    private List<Entry> getAverageData(){
        JSONArray rank_list = get_history_ranks(GlobalData.getUsername(), "Weekly");

        List<Entry> entries1 = new ArrayList<Entry>();
        try {
            for(int i=0;i<7;i++)
                entries1.add(new Entry(i,i+1));
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return entries1;
    }

    private List<Entry> get_history_rank_data()
    {
        JSONArray rank_list = get_history_ranks(GlobalData.getUsername(), "Weekly");

        List<Entry> entries1 = new ArrayList<Entry>();
        try {
            for(int i=0;i<7;i++)
            {
                int tmp_rank = (int)(rank_list.get(i));
//                System.out.println(tmp_rank);
                entries1.add(new Entry(i, tmp_rank));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return entries1;
    }
////////////////////////////////////////////////////////////////
    private void setSpinner(View view_){
        final View view=view_;
        spTime = (Spinner)view.findViewById(R.id.sp_time);
        spApp = (Spinner)view.findViewById(R.id.sp_app);

        timeAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1, listTime);
        spTime.setAdapter(timeAdapter);
        spTime.setSelection(0,true);

        appAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1, listApp);
        spApp.setAdapter(appAdapter);
        spApp.setSelection(0,true);

    }



}
