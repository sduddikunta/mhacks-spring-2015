package org.mhacks.zss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.mhacks.zss.model.Portfolio;

import java.util.ArrayList;


public class FundsAllocationPageFragment extends Fragment {

    private PieChart mChart;

    private Portfolio mPortfolio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page1, container, false);

        mPortfolio = (Portfolio) getArguments().getSerializable("portfolio");

        mChart = (PieChart) v.findViewById(R.id.chart);

        mChart.setUsePercentValues(true);
        mChart.setCenterText("Funds\nAllocation");
        mChart.setCenterTextSize(15f);

        mChart.setValueTextSize(13f);

        mChart.setDrawLegend(false);
        mChart.setDescription("");

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(36f);
        mChart.setTransparentCircleRadius(40f);

        mChart.setData(generatePieData());

        ((TextView) v.findViewById(R.id.investments)).setText(String.format("$%,.2f", mPortfolio.getInvested()));
        ((TextView) v.findViewById(R.id.savings)).setText(String.format("$%,.2f", mPortfolio.getSavings()));

        return v;
    }

    private PieData generatePieData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry((float) (mPortfolio.getSavings() / mPortfolio.getTotalCash()), 0));
        yVals1.add(new Entry((float) (mPortfolio.getInvested() / mPortfolio.getTotalCash()), 1));

        ArrayList<String> xVals1 = new ArrayList<>();

        xVals1.add("Savings");
        xVals1.add("Market");

        PieDataSet dataset = new PieDataSet(yVals1, "Funds Breakdown");
        // dataset.setSliceSpace(3f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataset.setColors(colors);

        return new PieData(xVals1, dataset);
    }

    public void signout(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

