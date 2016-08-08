package net.idey.moverjsoup.models;

import net.idey.moverjsoup.adapters.SliderPagerAdapter;

import java.util.List;

/**
 * Created by yusuf.abdullaev on 7/20/2016.
 */
public interface ViewCondition {
    void showVideosList(SliderPagerAdapter mAdapter);
    void showVideosListWithoutTabs(List<Video> videoList);
    void showVideoListWithSearch();
    void setToolbarTitle(String title);
}
