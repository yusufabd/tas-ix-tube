package net.idey.moverjsoup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.idey.moverjsoup.R;
import net.idey.moverjsoup.models.Video;
import net.idey.moverjsoup.activities.VideoActivity;
import net.idey.moverjsoup.adapters.VideoListAdapter;

import java.util.List;

/**
 * Created by yusuf.abdullaev on 7/17/2016.
 */
public class VideosFragment extends Fragment{

    public static final String ARG_CATEGORY_NUMBER = "arg_category_number";

    private List<Video> mVideoList;
    private VideoListAdapter mAdapter;
    private ListView mListViewVideos;

    public VideosFragment() {
    }

    public static VideosFragment newInstance(List<Video> videoList) {
        VideosFragment fragment = new VideosFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        fragment.setVideoList(videoList);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos, container, false);

        mAdapter = new VideoListAdapter(getActivity(), mVideoList);
        mListViewVideos = (ListView)rootView.findViewById(R.id.list_view_videos);
        if (mVideoList!=null){
        mListViewVideos.setAdapter(mAdapter);
        }
        mListViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = mVideoList.get(position);

                Intent mIntent = new Intent(getActivity(), VideoActivity.class);
                mIntent.putExtra("id", video.getId());
                mIntent.putExtra("title", video.getTitle());
                mIntent.putExtra("owner", video.getOwner());
                mIntent.putExtra("views", video.getViews());
                mIntent.putExtra("length", video.getLength());
                startActivity(mIntent);
            }
        });

        return rootView;
    }

    public void setVideoList(List<Video> videoList) {
        this.mVideoList = videoList;
    }
}
