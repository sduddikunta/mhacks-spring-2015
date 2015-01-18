package org.mhacks.zss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.github.mikephil.charting.utils.YLabels;

import org.mhacks.zss.model.Portfolio;
import org.mhacks.zss.model.Security;

import java.util.ArrayList;


public class PortfolioPageFragment extends Fragment {

    private LineChart mChart;

    private Portfolio mPortfolio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page2, container, false);

        mPortfolio = (Portfolio) getArguments().getSerializable("portfolio");

        mChart = (LineChart) v.findViewById(R.id.linechart);

        mChart.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                return String.format("$%,.0f", v);
            }
        });
        mChart.setDrawUnitsInChart(true);

        // if enabled, the chart will always start at zero on the y-axis
        mChart.setStartAtZero(false);

        // disable the drawing of values into the chart
        mChart.setDrawYValues(false);

        mChart.setDrawBorder(true);

        mChart.setBorderPositions(new BarLineChartBase.BorderPosition[] {
                BarLineChartBase.BorderPosition.BOTTOM
        });

        // no description text
        mChart.setDescription("");

        // // enable / disable grid lines
        mChart.setDrawVerticalGrid(false);
        // mChart.setDrawHorizontalGrid(false);
        //
        // // enable / disable grid background
        // mChart.setDrawGridBackground(false);
        //
        mChart.setDrawLegend(false);

        // enable value highlighting
        mChart.setHighlightEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(getResources().getColor(R.color.background_material_dark));

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);

        mChart.setValueTextColor(getResources().getColor(android.R.color.white));

        mChart.setDrawMarkerViews(false);

        YLabels y = mChart.getYLabels();
        y.setTextColor(Color.WHITE);
        y.setFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                return String.format("$%,.0f", v);
            }
        });

        // add data
        setData();

        mChart.animateX(2500);

        TableLayout table = (TableLayout) v.findViewById(R.id.table);

        table.removeAllViews();

        for (Security security : mPortfolio.getSecurities()) {
            View view = inflater.inflate(R.layout.portfolio_table_row, table, false);
            ((TextView) view.findViewById(R.id.table_company)).setText(security.getName());
            ((TextView) view.findViewById(R.id.table_symbol)).setText(security.getSymbol());
            ((TextView) view.findViewById(R.id.table_price)).setText(String.format("$%.2f",
                    security.getValue()));
            table.addView(view);
        }

        return v;
    }

    private void setData() {
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < mPortfolio.getComposites().size(); i++) {
            yVals1.add(new Entry((float) mPortfolio.getComposites().get(i).doubleValue(), i));
        }

        ArrayList<String> xVals1 = new ArrayList<>();

        for (int i = 0; i < mPortfolio.getComposites().size(); i++) {
            xVals1.add("");
        }

        LineDataSet dataset = new LineDataSet(yVals1, "Composite Portfolio");

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(android.R.color.holo_orange_dark));

        dataset.setColors(colors);

        dataset.setLineWidth(1.75f);
        dataset.setDrawCircles(false);

        mChart.setData(new LineData(xVals1, dataset));
    }

    public void signout(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

