package adeyds.noes.wisbejo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import adeyds.noes.wisbejo.R;

import static adeyds.noes.wisbejo.util.AppVar.NAMA_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.PATH_IMAGE_GALERI;

public class ImageViewer extends AppCompatActivity {
private ImageView imgDetail;
private TextView tvSample;
public static final String PATH_IMAGE_DETAIL = "PATH_IMAGE";
public static final String NAMA_IMAGE_DETAIL = "NAMA_IMAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imgDetail = findViewById(R.id.img_detail);
        tvSample = findViewById(R.id.tv_sample_nama);

        tvSample.setText(getIntent().getStringExtra(NAMA_IMAGE_DETAIL));
        Glide.with(ImageViewer.this)
                .load(getIntent().getStringExtra(PATH_IMAGE_DETAIL))
                .apply(new RequestOptions()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher))
                .into(imgDetail);
    }
}
