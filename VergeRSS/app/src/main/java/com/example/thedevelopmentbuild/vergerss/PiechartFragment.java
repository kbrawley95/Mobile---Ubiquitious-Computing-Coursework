package com.example.thedevelopmentbuild.vergerss;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.background;
import static android.R.attr.data;
import static android.R.attr.description;

public class PiechartFragment extends Fragment {

    private static String TAG = "MainActivity";

    private float[] yData = {55f, 4f, 12f, 29f};
    private double[]yValues={19.16, 1.167, 4.0522,10.1};
    private String[] xData = {"Starbucks", "Costa" , "Hortons" , "Dunkin' Donuts"
            };
    PieChart pieChart;


    public PiechartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Define the view to be displayed
        View view = inflater.inflate(R.layout.fragment_piechart, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.background));

        Log.d(TAG, "onCreate: starting to create chart");

        //Reference piechart in xml layout
        pieChart = (PieChart)view.findViewById(R.id.pieChart1);

        //Generate inner  circle
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleAlpha(0);

        pieChart.setCenterText("Revenue(%)");
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setCenterTextSize(16);
        pieChart.setDescription(null);

        //Handles the population of chart data
        addDataSet();

        /*Handles the touch event for the chart slices (Displaying message info)*/
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Log.d(TAG,"onValueSelected: Value select from chart");
                Log.d(TAG,"onValueSelected: " + e.toString());
                Log.d(TAG,"onValueSelected: " + h.toString());

                int pos1=e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 18);


                for(int i=0; i<yData.length; i++){

                   if(yData[i] == Float.parseFloat(sales)){
                       pos1 = i;
                       break;
                    }
                }

                    String company = xData[pos1];
                    Toast.makeText(getContext(), "Store: " + company + "\n" +"$"
                             +yValues[pos1]+  " Billion", Toast
                            .LENGTH_SHORT)
                            .show();


            }

            @Override
            public void onNothingSelected() {

            }
        });

        return view;

    }

    /*Method tasked with the population of chart data, and assigning colours to each of the
    slices in the chart*/
    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //Create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Popularity stat");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.WHITE);



        //Add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //Add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.DEFAULT);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);

        //Create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
