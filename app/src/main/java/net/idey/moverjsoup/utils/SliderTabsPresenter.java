package net.idey.moverjsoup.utils;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import net.idey.moverjsoup.R;
import net.idey.moverjsoup.adapters.SliderPagerAdapter;
import net.idey.moverjsoup.models.Category;
import net.idey.moverjsoup.models.CategoryTypes;
import net.idey.moverjsoup.models.IdConstants;
import net.idey.moverjsoup.models.SliderTabs;
import net.idey.moverjsoup.models.Video;
import net.idey.moverjsoup.models.ViewCondition;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by yusuf.abdullaev on 7/20/2016.
 */
public class SliderTabsPresenter implements IdConstants {

    Context mContext;
    ViewCondition mCondition;
    FragmentManager fm;
    HtmlParser mParser;

    public SliderTabsPresenter(Context mContext, ViewCondition mCondition, FragmentManager fm) {
        this.mContext = mContext;
        this.mCondition = mCondition;
        this.fm = fm;
        this.mParser = new HtmlParser();
    }

    public void init(){
        showVideosList(new Category(CategoryTypes.MAIN, mContext.getString(R.string.main), MAIN));
    }

    private void showVideosList(Category mCategory){

        Document document = null;

        try {
            document = Jsoup.connect(mCategory.getUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mCategory.getType()== CategoryTypes.LATEST){
            List<Video> mVideoList = mParser.getVideosFromHtml(document, mCategory.getType(), SliderTabs.TOP3);
            mCondition.showVideosListWithoutTabs(mVideoList);
        }else if(mCategory.getType()==CategoryTypes.SEARCH){
            mCondition.showVideoListWithSearch();
        }
        else{
            SliderTabs[] tabs = {SliderTabs.RECOMMENDED, SliderTabs.TOP3, SliderTabs.NEW};
            SliderPagerAdapter adapter = new SliderPagerAdapter(fm, mContext, mCategory.getType(), tabs, document);
            mCondition.showVideosList(adapter);
            mCondition.setToolbarTitle(mCategory.getTitle());
        }
    }

    public void onNavigationItemSelected(MenuItem item){
        mCondition.setToolbarTitle(item.getTitle().toString());
        Category category = Category.newInstance(mContext, item.getItemId());
        showVideosList(category);
    }
}
