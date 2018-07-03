package adeyds.noes.wisbejo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import adeyds.noes.wisbejo.R;

import static adeyds.noes.wisbejo.util.AppVar.PATH_IMAGE_GALERI;

public class ImageViewer extends AppCompatActivity {
private ImageView imgDetail;
public static final String PATH_IMAGE_DETAIL = "PATHHHH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imgDetail = findViewById(R.id.img_detail);
        Glide.with(ImageViewer.this)
                .load(getIntent().getStringExtra(PATH_IMAGE_DETAIL))
                .apply(new RequestOptions()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher))
                .into(imgDetail);
    }
}
