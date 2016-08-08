package net.idey.moverjsoup.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import net.idey.moverjsoup.R;

/**
 * Created by yusuf.abdullaev on 7/22/2016.
 */
public class VideoController extends MediaController{

    Context mContext;
    ImageButton mCollapseButton;
    boolean isFullscreen;
    WindowManager mWindowManager;
    Window mWindow;

    public VideoController(Context context, boolean vvState) {
        super(context);
        mContext = context;
        isFullscreen = vvState;
        mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);

    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        FrameLayout.LayoutParams frameParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        frameParams.gravity = Gravity.RIGHT|Gravity.TOP;

        View v = makeVideoView(view);
        addView(v, frameParams);
    }

    private View makeVideoView(final View videoView) {
        mCollapseButton = new ImageButton(mContext);
        mCollapseButton.setImageResource(R.drawable.collapse);

        mCollapseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullscreen){
                    DisplayMetrics metrics = new DisplayMetrics();
                    mWindowManager.getDefaultDisplay().getMetrics(metrics);
                    android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoView.getLayoutParams();
                    params.width =  (int) (300*metrics.density);
                    params.height = (int) (250*metrics.density);
                    params.leftMargin = 30;
                    videoView.setLayoutParams(params);
                }else{
                    DisplayMetrics metrics = new DisplayMetrics();
                    mWindowManager.getDefaultDisplay().getMetrics(metrics);
                    android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoView.getLayoutParams();
                    params.width =  metrics.widthPixels;
                    params.height = metrics.heightPixels;
                    params.leftMargin = 0;
                    videoView.setLayoutParams(params);
                }
            }
        });


        return mCollapseButton;
    }

}
