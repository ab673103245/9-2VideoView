package qianfeng.a9_2videoview;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private VideoView vv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VideoView:播放视频，其实是MediaPlayer播放声音，SurfaceView绘制图像，但图像可在子线程更新UI
        vv = ((VideoView) findViewById(R.id.videoView));
        iv = ((ImageView) findViewById(R.id.iv));
        String filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "123.mp4").getAbsolutePath();
        Uri videoURI = Uri.parse(filePath);
        vv.setVideoURI(videoURI);

        // 如果是本地视频的话，就可以在本地获取某一帧的图像，作为视频的bitmap显示(就是预加载的图片)
        Bitmap bitmap = getVideoThumbnail(filePath);

        MediaController controller = new MediaController(this);
        // 使用系统提供的播放控制栏
        vv.setMediaController(controller);

        iv.setImageBitmap(bitmap);

    }

    private Bitmap getVideoThumbnail(String filePath) {

        MediaMetadataRetriever retriever = null;

        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(filePath);
            Bitmap frameAtTime = retriever.getFrameAtTime();
            return frameAtTime;

        } finally {
            retriever.release();
        }

    }


    public void start(View view) {
        // 点击播放视频
        iv.setVisibility(View.GONE);
        vv.start();
    }
}
