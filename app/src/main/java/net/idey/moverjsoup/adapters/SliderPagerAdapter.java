package net.idey.moverjsoup.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.idey.moverjsoup.fragment.VideosFragment;
import net.idey.moverjsoup.models.CategoryTypes;
import net.idey.moverjsoup.models.SliderTabs;
import net.idey.moverjsoup.models.Video;
import net.idey.moverjsoup.utils.HtmlParser;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by yusuf.abdullaev on 7/20/2016.
 */
public class SliderPagerAdapter extends FragmentStatePagerAdapter{

    Context mContext;
    CategoryTypes mType;
    SliderTabs[] mTabs;
    Document mDoc;
    HtmlParser mParser;

    public SliderPagerAdapter(FragmentManager fm, Context context, CategoryTypes type, SliderTabs[] tabs, Document doc) {
        super(fm);
        this.mContext = context;
        this.mType = type;
        this.mTabs = tabs;
        this.mDoc = doc;
        this.mParser = new HtmlParser();
    }

    @Override
    public Fragment getItem(int position) {
        List<Video> mVideoList = mParser.getVideosFromHtml(mDoc, mType, mTabs[position]);
        return VideosFragment.newInstance(mVideoList);
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (mTabs[position]){
            case RECOMMENDED:
                return "  Рекомендованные  ";
            case TOP3:
                return "  ТОП 3 дня  ";
            case NEW:
                return "  Новое  ";
        }
        return "";
    }
}
