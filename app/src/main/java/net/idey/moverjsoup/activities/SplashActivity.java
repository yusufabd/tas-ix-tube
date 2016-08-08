package net.idey.moverjsoup.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import net.idey.moverjsoup.R;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RelativeLayout layout;
    ConnectivityManager conMgr;
    NetworkInfo activeNetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        layout = (RelativeLayout)findViewById(R.id.rel_lay);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        progressBar.setProgress(50);
        progressBar.setVisibility(View.VISIBLE);
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(layout, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.turn_internet), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 1222);
                        }
                    });
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(layout, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.turn_internet), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 1222);
                        }
                    });
            snackbar.show();
        }
    }
}
