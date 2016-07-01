package com.example.andy.solenergi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class LiveBatteryFragment extends Fragment {

    private static final String TAG = "LiveBatteryFragment";

    private float batVoltage;

    // graphing variables
    LineChart lineChart;
    LineDataSet batterySet;
    LineData data;
    int count;

    public LiveBatteryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_live_battery_graph, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        count=0;

        lineChart = (LineChart) view.findViewById(R.id.chart);
        lineChart.setDescription("");
        lineChart.getLegend().setTextColor(Color.WHITE);

        data = new LineData();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setValueFormatter(new WattageYAxisValueFormatter());
        leftYAxis.setLabelCount(6, true);
        leftYAxis.setAxisMinValue(0);
        leftYAxis.setAxisMaxValue(5);
        leftYAxis.setTextColor(Color.WHITE);

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setValueFormatter(new VoltageAmperageYAxisValueFormatter());
        rightYAxis.setLabelCount(6, true);
        rightYAxis.setAxisMinValue(0);
        rightYAxis.setAxisMaxValue(5);
        rightYAxis.setTextColor(Color.WHITE);


        batterySet = new LineDataSet(null, "Battery Voltage");
        batterySet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        batterySet.setColor(Color.rgb(0, 255, 0));
        batterySet.setDrawCubic(true);
        batterySet.setDrawCircles(false);
        batterySet.setLineWidth(3);

        data.addDataSet(batterySet);
        lineChart.setData(data);
        ;

    }

    public void updateGraph(JSONObject inputJson)
    {
        try {
            batVoltage = Float.parseFloat(inputJson.getString("batVoltage"));

            data.addXValue(count + "");
            batterySet.addEntry(new Entry(batVoltage, count));
            count++;

            data.notifyDataChanged(); // let the data know a dataSet changed
            lineChart.notifyDataSetChanged(); // let the chart know it's data changed
            lineChart.invalidate(); // refresh


        }catch(Exception e){}
    }

    public class WattageYAxisValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;

        public WattageYAxisValueFormatter () {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            // write your logic here
            // access the YAxis object to get more information
            return mFormat.format(value) + " W"; // e.g. append a dollar-sign
        }
    }

    public class VoltageAmperageYAxisValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;

        public VoltageAmperageYAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            // write your logic here
            // access the YAxis object to get more information
            return mFormat.format(value) + " V,A"; // e.g. append a dollar-sign
        }
    }

}
