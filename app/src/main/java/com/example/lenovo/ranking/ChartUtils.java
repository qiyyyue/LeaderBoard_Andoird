package com.example.lenovo.ranking;

import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.util.TimeUtils;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART_INSIDE;

public class ChartUtils {
    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart chart) {
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("No Data...");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(true);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(true);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(true);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setGridColor(Color.parseColor("#306ab04c"));
        // 设置x轴数据偏移量
        xAxis.setYOffset(-12);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(true);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(20);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        Matrix matrix = new Matrix();
//        x轴缩放1.5倍
//        matrix.postScale(1.5f, 1f);
        matrix.postScale(1f, 1f);
//        在图表动画显示之前进行缩放
        chart.getViewPortHandler().refresh(matrix, chart, false);
//         x轴执行动画
//        chart.animateX(2000);
        chart.animateXY(2000, 2000);
//        chart.invalidate();
        return chart;
    }
    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values,List<Entry> values1) {
        LineDataSet lineDataSet,lineDataSet1;
        if(values1!=null){
            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
                lineDataSet.setValues(values);
                lineDataSet1 = (LineDataSet) chart.getData().getDataSetByIndex(1);
                lineDataSet1.setValues(values1);

                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                lineDataSet = new LineDataSet(values, "My Score");
                lineDataSet.setColor(Color.parseColor("#6ab04c"));
                lineDataSet.setMode(LineDataSet.Mode.LINEAR);
                lineDataSet.setDrawCircles(true);
                lineDataSet.setCircleColor(Color.parseColor("#6ab04c"));
                lineDataSet.setDrawValues(true);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawFilled(true);
                lineDataSet.setFillColor(Color.parseColor("#306ab04c"));

                lineDataSet1 = new LineDataSet(values1, "Average");
                lineDataSet1.setColor(Color.parseColor("#f9ca24"));
                lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
                lineDataSet1.setDrawCircles(true);
                lineDataSet1.setCircleColor(Color.parseColor("#f9ca24"));
                lineDataSet1.setDrawValues(true);
                lineDataSet1.setHighlightEnabled(true);
                lineDataSet1.setDrawFilled(true);
                lineDataSet1.setFillColor(Color.parseColor("#30f9ca24"));


                LineData data = new LineData();
                data.addDataSet(lineDataSet);
                data.addDataSet(lineDataSet1);

                chart.setData(data);
                chart.invalidate();
            }
        }
        else{
            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
                lineDataSet.setValues(values);

                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                lineDataSet = new LineDataSet(values, "My Rank");
                lineDataSet.setColor(Color.parseColor("#6ab04c"));
                lineDataSet.setMode(LineDataSet.Mode.LINEAR);
                lineDataSet.setDrawCircles(true);
                lineDataSet.setCircleColor(Color.parseColor("#6ab04c"));
                lineDataSet.setDrawValues(true);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setDrawFilled(true);
                lineDataSet.setFillColor(Color.parseColor("#306ab04c"));


                LineData data = new LineData();
                data.addDataSet(lineDataSet);

                chart.setData(data);
                chart.invalidate();
            }
        }
    }
    /**
     * 更新图表
     *
     * @param chart     图表
     * @param values    数据
     * @param valueType 数据类型
     */
    public static void notifyDataSetChanged(LineChart chart, List<Entry> values,List<Entry> values1,
                                            final int valueType) {
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValuesProcess(valueType)[(int) value];
            }
        });

        chart.invalidate();
        setChartData(chart,values,values1);
    }
    /**
     * x轴数据处理
     *
     * @param valueType 数据类型
     * @return x轴数据
     */
    private static String[] xValuesProcess(int valueType) {
//        String[] week = {"Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat."};
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        if (valueType == 0) { // weekly

//            String[] weekValues = new String[7];
//            Calendar calendar = Calendar.getInstance();
//            int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
//
//            for (int i = 6; i >= 0; i--) {
//                weekValues[i] = week[currentWeek - 1];
//                if (currentWeek == 1) {
//                    currentWeek = 7;
//                } else {
//                    currentWeek -= 1;
//                }
//            }
//            return weekValues;

            Date today = new Date();

            String[] weekValues = new String[7];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(calendar.DATE,-7);
            for (int i = 0; i <7; i++) {
                calendar.add(calendar.DATE,+1);
                weekValues[i] = sdf.format(calendar.getTime());
            }
            return weekValues;

        } else if (valueType == 1) { // month-30

//            获得当月天数
//            Calendar a = Calendar.getInstance();
//            a.set(Calendar.DATE, 1);
//            a.roll(Calendar.DATE, -1);
//            int maxDate = a.get(Calendar.DATE);
            Date today = new Date();

            String[] monthValues = new String[30];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(calendar.DATE,-30);
            for (int i = 0; i <30; i++) {
                calendar.add(calendar.DATE,+1);
                monthValues[i] = sdf.format(calendar.getTime());
            }
            return monthValues;

        } else if (valueType == 2) { // year
            Date today = new Date();

            String[] yearValues = new String[365];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(calendar.DATE,-365);
            for (int i = 0; i <365; i++) {
                calendar.add(calendar.DATE,+1);
                yearValues[i] = sdf.format(calendar.getTime());
            }
            return yearValues;
        }
        return new String[]{};
    }

}
