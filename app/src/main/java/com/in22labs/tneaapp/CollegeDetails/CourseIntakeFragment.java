package com.in22labs.tneaapp.CollegeDetails;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.app.MyApplication;

import java.io.IOException;
import java.util.ArrayList;


public class CourseIntakeFragment extends Fragment {
    ArrayList<BarEntry> Intake;

    ArrayList<String> Course, IntakeString;
    ArrayList<Float> IntakeFloat;
    ArrayList<Integer> IntakeInt;
    DatabaseHelper db;
    String CollegeCode;
    public BarChart bar;
    RelativeLayout mainLayout, pie;
    Float[] ArrCourCount;
    public PieChart mChart;
    ConnectionDetector cd;

    public static final int[] PASTEL_COLORS = {
            Color.rgb(66, 165, 245), Color.rgb(77, 182, 172), Color.rgb(79, 195, 247),
            Color.rgb(76, 175, 80), Color.rgb(216, 27, 96)
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.updates_layout,null);

      }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        cd=new ConnectionDetector(getActivity());
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            } else {
                AdView mAdView = (AdView) getActivity().findViewById(R.id.adViewCourseIntake);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                mAdView.setVisibility(View.VISIBLE);
            }
        }
//        setRetainInstance(true);
        try {
            db=new DatabaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CollegeCode= getArguments().getString("clgcode");
        db.clg_code=CollegeCode;
        IntakeString=db.getInTake();
        IntakeFloat = new ArrayList<>();
        IntakeInt = new ArrayList<>();
        for(int i=0;i<IntakeString.size();i++){
            IntakeFloat.add(i, Float.valueOf(IntakeString.get(i).toString()));
            IntakeInt.add(i, Integer.parseInt(IntakeString.get(i).toString()));
//            Toast.makeText(getActivity(),IntakeFloat.get(i).toString(), Toast.LENGTH_SHORT).show();
        }

        ArrCourCount = new Float[IntakeFloat.size()];
        ArrCourCount= IntakeFloat.toArray(ArrCourCount);

      Intake = new ArrayList<>();
        for(int j=0; j<IntakeFloat.size(); j++){
            Intake.add(new BarEntry(IntakeFloat.get(j), j));
        }
        mainLayout=(RelativeLayout)getActivity().findViewById(R.id.main_layout);
        pie=(RelativeLayout)getActivity().findViewById(R.id.pie_chart);
//
        BarDataSet dataset = new BarDataSet(Intake, "# No Of Seats");
        Course=db.getCourseInTake();
//
        bar = new BarChart(getActivity());
//
        ArrayList<Integer> color=new ArrayList<Integer>();

//		for( int c: ColorTemplate.COLORFUL_COLORS)
//		color.add(c);

//		for( int c: ColorTemplate.JOYFUL_COLORS)
//		color.add(c);
//
//        for( int c: ColorTemplate.VORDIPLOM_COLORS)
//            color.add(c);//fixed color
//
		for( int c: PASTEL_COLORS)
		color.add(c);
//
//        for( int c: ColorTemplate.LIBERTY_COLORS)
//            color.add(c);

        color.add(ColorTemplate.getHoloBlue());
        dataset.setColors(color);
        BarData data = new BarData(Course, dataset);
        bar.setData(data);

        bar.setDescription("Course Intakes");
        bar.setDescriptionColor(Color.rgb(0, 0, 0));

        mainLayout.addView(bar, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        mainLayout.getRotation();


        mChart = new PieChart(getActivity());


       //mChart.setUsePercentValues(false);

        mChart.setDescription("Course Intakes");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(R.color.tranred);
        mChart.setHoleRadius(60);
        //mChart.setTransparentCircleRadius(90);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Float f = e.getVal();
                int i = f.intValue();
//                Toast.makeText(getActivity(),
//                        Course.get(e.getXIndex()) + " = " + i , Toast.LENGTH_SHORT).show();
                String temp = Course.get(e.getXIndex()).toString();
                String CourseName = db.getRankCourse(temp);
                Snackbar.make(pie,CourseName+"  "+" "+i, Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        // customize legends
//        Legend l = mChart.getLegend();
//        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        pie.addView(mChart, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));


    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < IntakeInt.size(); i++)
            yVals1.add(new Entry(IntakeInt.get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < Course.size(); i++)
            xVals.add(Course.get(i));

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Course Code");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(yVals1.size());
        dataSet.setDrawValues(true);
        ArrayList<Integer> colors=new ArrayList<Integer>();
        for (int c : PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
//        data.setValueFormatter(new YourValueFormatter());
//        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();

        mChart.setDrawSliceText(true);
        mChart.animateXY(1500, 1500);
    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("TNEA College Intake");
    }
}
