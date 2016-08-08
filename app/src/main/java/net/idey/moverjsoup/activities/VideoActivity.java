package net.idey.moverjsoup.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import net.idey.moverjsoup.R;
import net.idey.moverjsoup.utils.VideoLoader;

public class VideoActivity extends AppCompatActivity{

    private ImageView imageViewDownload, imageViewShare;
    private VideoView mVideoPlayer;
    private LinearLayout layoutVideoHeader;
    private TextView textViewVideoTitle;
    private String mId, mTitle, mLength, mViews, mOwner, mUrl;
    private int mCode = 0;
    private VideoLoader mLoader;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        assert progressBar != null;
        progressBar.setProgress(15);
        mId = getIntent().getStringExtra("id");
        mTitle = getIntent().getStringExtra("title");
        mLength = getIntent().getStringExtra("length");
        mViews = getIntent().getStringExtra("views");
        mOwner = getIntent().getStringExtra("owner");

        mUrl = "http://v.mover.uz/" + mId + "_m.mp4";



        textViewVideoTitle = (TextView)findViewById(R.id.text_view_video_title);
        layoutVideoHeader.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                layoutVideoHeader.setVisibility(visibility);
            }
        });
        textViewVideoTitle.setText(mTitle);
        mVideoPlayer = (VideoView) findViewById(R.id.video_player);
        mVideoPlayer.setMediaController(new CustomMediaController(this));
        mVideoPlayer.setVideoURI(Uri.parse(mUrl));
        mVideoPlayer.start();
        mVideoPlayer.setOnCompletionListener(new OnVideoComplete());

        mVideoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });

        mVideoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });
    }

    public void shareVideo(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "http://mover.uz/watch/" + mId;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Скачайте Tas-Ix Tube, чтобы просматривать ролики с Mover.uz на телефоне!");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void finishActivity(View view) {
        finish();
    }

    private class OnVideoComplete implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public class CustomMediaController extends MediaController {
        public CustomMediaController(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomMediaController(Context context, boolean useFastForward) {
            super(context, useFastForward);
        }

        public CustomMediaController(Context context) {
            super(context, true);
        }

        public boolean dispatchKeyEvent(KeyEvent event)
        {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                ((Activity) getContext()).finish();

            return super.dispatchKeyEvent(event);
        }
    }

    public void downloadVideo(View view) {
        mVideoPlayer.pause();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1222);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mLoader = new VideoLoader();
                    mLoader.downloadFromUrl(getApplicationContext(), mUrl, mTitle);
                    mVideoPlayer.resume();
                }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(getApplicationContext(), getString(R.string.no_wes_permission), Toast.LENGTH_LONG).show();
                    mVideoPlayer.resume();
                }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayer.pause();
    }
    @SuppressLint("NewApi")
    private int getSoftButtonsBarHeight() {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }
}
