package org.mhacks.zss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.mhacks.zss.model.Portfolio;

import java.util.ArrayList;


public class CustomizationPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page3, container, false);

        v.findViewById(R.id.calculate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });

        ((SeekBar) v.findViewById(R.id.risk)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) getActivity().findViewById(R.id.value_text)).setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar) v.findViewById(R.id.value)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) getActivity().findViewById(R.id.value_text)).setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return v;
    }

    private void retrieveData() {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final LoadingDialogFragment newFragment = new LoadingDialogFragment();
        newFragment.setCancelable(false);
        newFragment.show(fragmentManager, "dialog");

        StringRequest request = Network.getPortfolio(LoginActivity.customerId,
                ((SeekBar) getActivity().findViewById(R.id.risk)).getProgress(),
                ((SeekBar) getActivity().findViewById(R.id.value)).getProgress(),
                new Response.Listener<Portfolio>() {
                    @Override
                    public void onResponse(Portfolio response) {
                        Intent intent = new Intent(CustomizationPageFragment.this.getActivity(),
                                PortfolioActivity.class);
                        intent.putExtra("portfolio", response);
                        startActivity(intent);
                        CustomizationPageFragment.this.getActivity().finish();
                    }
                });

        Network.getRequestQueue(getActivity().getApplicationContext()).add(request);
        Network.getRequestQueue(getActivity().getApplicationContext()).start();
    }

    public static class LoadingDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.loading_data_dialog, null))
                    .setCancelable(false);

            return builder.create();
        }
    }

    public void signout(View view) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

