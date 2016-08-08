package net.idey.moverjsoup.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.idey.moverjsoup.R;
import net.idey.moverjsoup.adapters.SliderPagerAdapter;
import net.idey.moverjsoup.fragment.VideosFragment;
import net.idey.moverjsoup.models.CategoryTypes;
import net.idey.moverjsoup.models.SliderTabs;
import net.idey.moverjsoup.models.Video;
import net.idey.moverjsoup.models.ViewCondition;
import net.idey.moverjsoup.ui.SlidingTabLayout;
import net.idey.moverjsoup.utils.HtmlParser;
import net.idey.moverjsoup.utils.SliderTabsPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewCondition, View.OnClickListener {

    SlidingTabLayout tabs;
    ViewPager pager;
    FrameLayout container;
    LinearLayout layout_pager;
    EditText editTextSearch;
    ImageView imageViewSearch, imageViewLogo;
    RelativeLayout layout_search, layout_ac;
    TextView textViewDesc, textViewSite;


    private SliderTabsPresenter mPresenter;
    private HtmlParser mParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setupWindowAnimations();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        layout_ac = (RelativeLayout)headerView.findViewById(R.id.layout_ac);
        layout_ac.setOnClickListener(this);
        tabs = (SlidingTabLayout)findViewById(R.id.tabs);
        pager = (ViewPager)findViewById(R.id.pager);
        container = (FrameLayout)findViewById(R.id.frame_container);
        layout_pager = (LinearLayout)findViewById(R.id.layout_pager);
        layout_search = (RelativeLayout)findViewById(R.id.search_layout);

        editTextSearch = (EditText)findViewById(R.id.edit_text_search);
        imageViewSearch = (ImageView)findViewById(R.id.image_view_search);

        assert imageViewSearch != null;
        imageViewSearch.setOnClickListener(this);

        mPresenter = new SliderTabsPresenter(this, this, getSupportFragmentManager());
        mPresenter.init();

        mParser = new HtmlParser();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_view_search:
                String url = "http://mover.uz/search?val=";
                Document document = null;
                String query = editTextSearch.getText().toString();
                if (query.length()>0){
                    if (query.contains(" ")){
                        query = query.replaceAll(" ", "+");
                    }
                    url = url + query;
                    try {
                        document = Jsoup.connect(url).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<Video> videoList = mParser.getVideosFromHtml(document, CategoryTypes.SEARCH, SliderTabs.TOP3);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, VideosFragment.newInstance(videoList))
                            .commit();

                }
                break;
            case R.id.layout_ac:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://androidclub.uz"));
                startActivity(browserIntent);
                break;

        }}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void showVideosList(SliderPagerAdapter mAdapter){
        container.setVisibility(View.GONE);
        layout_search.setVisibility(View.GONE);
        layout_pager.setVisibility(View.VISIBLE);
        pager.setAdapter(mAdapter);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        tabs.setViewPager(pager);
    }

    public void showVideosListWithoutTabs(List<Video> videoList){
        layout_pager.setVisibility(View.GONE);
        layout_search.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, VideosFragment.newInstance(videoList))
                .commit();
    }

    @Override
    public void showVideoListWithSearch() {
        layout_pager.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        layout_search.setVisibility(View.VISIBLE);
    }

    public void setToolbarTitle(String title){
        setTitle(title);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mPresenter.onNavigationItemSelected(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
