package com.example.lenovo.ranking.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lenovo.ranking.GlobalData;
import com.example.lenovo.ranking.MainActivity;
import com.example.lenovo.ranking.R;
import com.example.lenovo.ranking.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView mRecyclerView;
    private ArrayList<String> lRank = new ArrayList<>();
//    private ArrayList<Integer> lPic= new ArrayList<>();
    private ArrayList<String> lStatus= new ArrayList<>();
    private ArrayList<String> lUser = new ArrayList<>();
    private ArrayList<String> lScore = new ArrayList<>();
    private Spinner spContinent;
    private Spinner spCountry;
    private Spinner spCity;


    //////////////////////////////////////////////////////////////////////////////////////////
    private String[] listCountinent = {"All","Europe"};
    private String[][] listCountry = {{"All"},{"All", "United Kingdom"}};
    private String[][][] listCity = {{{"All"}},{{"All"},{"All","Aberdeen"}}};
    //////////////////////////////////////////////////////////////////////////////////////////

    private ArrayAdapter<String> continentAdapter;
    private ArrayAdapter<String> countryAdapter;
    private ArrayAdapter<String> cityAdapter;
    static int cityPosition=0;

    /**
     * 获取排行榜信息
     * @param continent
     * @param country 国家
     * @param city 城市
     * @param period 时间长度, Weekly, Monthly, All
     * @return 返回JSONArray, [{'username':-,'point':-,'rank':-},]
     */
    protected JSONArray get_leader_board(String continent, String country, String city, String period)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/home/GetLeaderBoard";
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
            stringBuilder.append("continent=").append(continent)
                    .append("&country=").append(country)
                    .append("&city=").append(city)
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
                    System.out.println((jsonObject.get("leader_board_list")).getClass());
                    return (JSONArray) jsonObject.get("leader_board_list");
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
     * 获得当前分数排名
     * @param username 用户名
     * @return 返回用户当前分数排名
     */
    protected int get_rank(String username)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/home/GetRank";
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
            stringBuilder.append("username=").append(GlobalData.getUsername());
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
                    return (int)jsonObject.get("rank");
                }
                else
                    return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return -1;
    }

    /**
     * 获取分数
     * @param username 用户名
     * @return 返回用户分数
     */
    protected int get_point(String username)
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        String req_url = "http://10.0.2.2:5000/home/GetPoint";
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
            stringBuilder.append("username=").append(GlobalData.getUsername());
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
                    return (int)jsonObject.get("point");
                }
                else
                    return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        //初始化用户当前排名
//        int current_rank = get_rank(GlobalData.getUsername());
//        ((TextView)(view.findViewById(R.id.tv_myRank))).setText(current_rank);
//
//        //初始化用户当前分数
//        int current_point = get_point(GlobalData.getUsername());
//        ((TextView)(view.findViewById(R.id.tv_myPoints))).setText(current_point);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSpinner(view);

        //
//        view.findViewById(R.id.tv_myRank);
        //初始化用户当前排名
        int current_rank = get_rank(GlobalData.getUsername());
        TextView rank_view = view.findViewById(R.id.tv_myRank);
        rank_view.setText("" + current_rank);

        //初始化用户当前分数
        int current_point = get_point(GlobalData.getUsername());
        TextView point_view = view.findViewById(R.id.tv_myPoints);
        point_view.setText("" + current_point);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //////////////////////////////////////////////////////////
        String continent = "Europe";
        String country = "UK";
        String city = "Aberdeen";
        String period = "Weekly";

        JSONArray rank_list = get_leader_board(continent, country, city, period);
        try
        {
            String[] str = { "+", "-"};
            for (int i = 0; i < rank_list.length(); i++)
            {
                JSONObject rank_item = rank_list.getJSONObject(i);
                lRank.add("" + rank_item.get("rank"));
                lUser.add("" + rank_item.get("username"));
                lScore.add("" + rank_item.get("point"));
                int random = (int) ( Math.random () * 2 );
                lStatus.add(str[random]+(i+2));
//                System.out.println("" + rank_item.get("rank") + "\t" + rank_item.get("username") + "\t" +rank_item.get("point"));
            }
        }
        catch (Exception e)
        {
//            System.out.println("wrong!!!!!!!!!!!!!!");
            System.out.println(e.toString());
        }

//        String[] str = { "+", "-"};
//        for (int i = 1; i < 31; i++) {
//            lRank.add(String.valueOf(i));
//        }
////        for (int i = 0; i < 30; i++) {
////            lPic.add(R.mipmap.ic_launcher);
////        }
//        for (int i = 0; i < 30; i++) {
//            lUser.add("user_"+i);
//        }
//        for (int i = 0; i < 30; i++) {
//            int random = (int) ( Math.random () * 2 );
//            lStatus.add(str[random]+(i+2));
//        }
//        for (int i = 30; i > 0; i--) {
//            lScore.add(String.valueOf(i*13));
//        }
        ///////////////////////////////////////////////////////////
//        mRecyclerView.setAdapter(new RecyclerViewAdapter(view.getContext(),lRank,lPic,lUser,lScore));
//        System.out.println("setAdapter");
        mRecyclerView.setAdapter(new RecyclerViewAdapter(view.getContext(),lRank,lStatus,lUser,lScore));




    }
    private void setSpinner(View view_){
        final View view=view_;
        spCountry = (Spinner)view.findViewById(R.id.spin_country);
        spCity = (Spinner)view.findViewById(R.id.spin_city);
        spContinent = (Spinner)view.findViewById(R.id.spin_continent);

        continentAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, listCountinent);
        spContinent.setAdapter(continentAdapter);
        spContinent.setSelection(1,true);

        countryAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, listCountry[1]);
        spCountry.setAdapter(countryAdapter);
        spCountry.setSelection(1,true);

        cityAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, listCity[1][1]);
        spCity.setAdapter(cityAdapter);
        spCity.setSelection(1, true);

        spContinent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                countryAdapter = new ArrayAdapter<String>(
                        view.getContext(), android.R.layout.simple_spinner_item, listCountry[position]);
                spCountry.setAdapter(countryAdapter);
                cityPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }

        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3)
            {
                cityAdapter = new ArrayAdapter<String>(view.getContext(),
                        android.R.layout.simple_spinner_item, listCity[cityPosition][position]);
                spCity.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }
}
