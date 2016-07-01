package com.example.andy.solenergi;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class TextItemsFragment extends Fragment {

    // Layout Views
    private TextView batteryVoltageView;
    private TextView solarPowerView;

    // variables
    private float solarVoltage;
    private float solarCurrent;
    private float solarPower;
    private float batteryVoltage;

    public TextItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_items, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        batteryVoltageView = (TextView) view.findViewById(R.id.battery_voltage_text);
        solarPowerView = (TextView) view.findViewById(R.id.solar_power_text);
    }
    public void updateText(JSONObject inputJson)
    {
        try {
            batteryVoltage = Float.parseFloat(inputJson.getString("batVoltage"));
            batteryVoltageView.setText(String.format("%.2f", batteryVoltage) + "V");

            solarVoltage = Float.parseFloat(inputJson.getString("voltage"));
            solarCurrent = Math.abs(Float.parseFloat(inputJson.getString("current")));
            solarPower = solarPower*solarCurrent;
            solarPowerView.setText((String.format("%.2f", solarPower) + "W"));

        }catch(JSONException e){}
    }
}
