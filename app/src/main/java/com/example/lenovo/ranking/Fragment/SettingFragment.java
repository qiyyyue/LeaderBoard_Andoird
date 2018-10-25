package com.example.lenovo.ranking.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.lenovo.ranking.BindingActivity;
import com.example.lenovo.ranking.DuolingoActivity;
import com.example.lenovo.ranking.R;
public class SettingFragment extends Fragment {

    public SettingFragment() {
        // Required empty public constructor
    }
    private Spinner spCountry;
    private Spinner spCity;
    private Spinner spClass;
    //////////////////////////////////////////////////////////////////////////////////////////
    private String[] country = {"England"};
    private String[][] citys = {{"Aberdeen", "Edinburgh", "Liverpool", "London"}};
    private String[][][] classes = {{{"class_1","class_2"},{"class_a"},{"class_x","class_y"},{"class_i","class_j","class_k"}}};
    //////////////////////////////////////////////////////////////////////////////////////////
    private ArrayAdapter<String> countryAdapter;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> classAdapter;
    static int countryPosition=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSpinner(view);
    }


    private void setSpinner(View view_){
        final View view=view_;
        spCountry = (Spinner)view.findViewById(R.id.s_country);
        spCity = (Spinner)view.findViewById(R.id.s_city);
        spClass = (Spinner)view.findViewById(R.id.s_class);

        countryAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, country);
        spCountry.setAdapter(countryAdapter);
        spCountry.setSelection(0,true);

        cityAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, citys[0]);
        spCity.setAdapter(cityAdapter);
        spCity.setSelection(0,true);

        classAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, classes[0][0]);
        spClass.setAdapter(classAdapter);
        spClass.setSelection(0, true);

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                cityAdapter = new ArrayAdapter<String>(
                        view.getContext(), android.R.layout.simple_spinner_item, citys[position]);
                spCity.setAdapter(cityAdapter);
                countryPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }

        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3)
            {
                classAdapter = new ArrayAdapter<String>(view.getContext(),
                        android.R.layout.simple_spinner_item, classes[countryPosition][position]);
                spClass.setAdapter(classAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }
}