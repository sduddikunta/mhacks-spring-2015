package org.mhacks.zss;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.mhacks.zss.model.Portfolio;


public class LoginActivity extends ActionBarActivity {

    public static String customerId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }

    private void retrieveData() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final LoadingDialogFragment newFragment = new LoadingDialogFragment();
        newFragment.setCancelable(false);
        newFragment.show(fragmentManager, "dialog");

        StringRequest request = Network.getPortfolio(((EditText) findViewById(R.id.customer_id)).getText().toString(),
                new Response.Listener<Portfolio>() {
                    @Override
                    public void onResponse(Portfolio response) {
                        Intent intent = new Intent(LoginActivity.this, PortfolioActivity.class);
                        intent.putExtra("portfolio", response);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                });

        customerId = ((EditText) findViewById(R.id.customer_id)).getText().toString();

        Network.getRequestQueue(getApplicationContext()).add(request);
        Network.getRequestQueue(getApplicationContext()).start();
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

    }
}