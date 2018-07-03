package adeyds.noes.wisbejo.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.fragment.GaleriFragment;

import static adeyds.noes.wisbejo.util.AppVar.COVER_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.DESK_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.HTM_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.ID_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.KONTAK_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.LOKASI_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.NAMA_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.NOT_FROM_GALER;
import static adeyds.noes.wisbejo.util.AppVar.PATH_IMAGE_GALERI;
import static adeyds.noes.wisbejo.util.AppVar.TAG_GALERI;
import static adeyds.noes.wisbejo.util.AppVar.TO_DETAIL;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fabGaler;
    private ImageView imgExpanded;
    private TextView dtlJudul, dtlDesk, dtlHtm, dtlLok, dtlKontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fabGaler = findViewById(R.id.fabGaler);


        castView();
        setView();

        fabGaler.setOnClickListener(this);
        dtlKontak.setOnClickListener(this);

    }

    private void setView() {
        dtlJudul.setText(getIntent().getStringExtra(NAMA_EXTRA_DETAIL));
        dtlDesk.setText(getIntent().getStringExtra(DESK_EXTRA_DETAIL));
        dtlHtm.setText(getIntent().getStringExtra(HTM_EXTRA_DETAIL));
        dtlKontak.setText(getIntent().getStringExtra(KONTAK_EXTRA_DETAIL));
        dtlLok.setText(getIntent().getStringExtra(LOKASI_EXTRA_DETAIL));
        Glide.with(getApplicationContext())
                .load(PATH_IMAGE_GALERI + getIntent().getStringExtra(COVER_EXTRA_DETAIL))
                .apply(new RequestOptions()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher))
                .into(imgExpanded);

    }

    private void castView() {
        imgExpanded = findViewById(R.id.expandedImage);
        dtlJudul = findViewById(R.id.dtl_judul);
        dtlDesk = findViewById(R.id.dtl_desk);
        dtlHtm = findViewById(R.id.dtl_htm);
        dtlLok = findViewById(R.id.dtl_lok);
        dtlKontak = findViewById(R.id.dtl_kontak);
        //dtlDesk.setTextSize(200);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabGaler) {
            setResult(Integer.parseInt(getIntent().getStringExtra(ID_EXTRA_DETAIL)));
            finish();
        }
        if (v.getId() == R.id.dtl_kontak) {

            String uri = "tel:" + dtlKontak.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(NOT_FROM_GALER);
    }
}
