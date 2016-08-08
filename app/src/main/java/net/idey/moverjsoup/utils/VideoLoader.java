package net.idey.moverjsoup.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by yusuf.abdullaev on 7/15/2016.
 */
public class VideoLoader {

    public static final String TAG = "myLogTag";
    private final int TIMEOUT_CONNECTION = 5000;// 5sec
    private final int TIMEOUT_SOCKET = 300000;// 30sec

    public void downloadFromUrl(Context context, String url, String fileName) { // this is
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));

// This put the download in the same Download dir the browser uses
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

// When downloading music and videos they will be listed in the player
// (Seems to be available since Honeycomb only)
        r.allowScanningByMediaScanner();

// Notify user when download is completed
// (Seems to be available since Honeycomb only)
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

// Start download
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }
}
