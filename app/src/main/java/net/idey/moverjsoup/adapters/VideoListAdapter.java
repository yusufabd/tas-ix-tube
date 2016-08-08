package net.idey.moverjsoup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.idey.moverjsoup.R;
import net.idey.moverjsoup.models.Video;

import java.util.List;

/**
 * Created by yusuf.abdullaev on 7/19/2016.
 */
public class VideoListAdapter extends BaseAdapter{

    Context mContext;
    List<Video> mVideoList;
    LayoutInflater mInflater;

    public VideoListAdapter(Context context, List<Video> videoList) {
        this.mContext = context;
        this.mVideoList = videoList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mVideoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Video video = mVideoList.get(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_video, null);
            holder = new ViewHolder();

            holder.textViewTitle = (TextView)convertView.findViewById(R.id.text_view_title);
            holder.textViewViews = (TextView)convertView.findViewById(R.id.text_view_views);
            holder.textViewOwner = (TextView)convertView.findViewById(R.id.text_view_owner);
            holder.textViewLength = (TextView)convertView.findViewById(R.id.text_view_length);
            holder.imageViewThumb = (ImageView)convertView.findViewById(R.id.image_view_thumb);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textViewTitle.setText(video.getTitle());
        holder.textViewViews.setText(video.getViews());
        holder.textViewOwner.setText(video.getOwner());
        holder.textViewLength.setText(video.getLength());

        Picasso.with(mContext).load(video.getImage_url()).into(holder.imageViewThumb);

        return convertView;
    }

    static class ViewHolder{
        TextView textViewTitle, textViewViews, textViewOwner, textViewLength;
        ImageView imageViewThumb;
    }
}
