package adeyds.noes.wisbejo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.model.Galeri;
import adeyds.noes.wisbejo.model.Pantai;
import adeyds.noes.wisbejo.view.ImageViewer;

import static adeyds.noes.wisbejo.util.AppVar.PATH_IMAGE_GALERI;
import static adeyds.noes.wisbejo.view.ImageViewer.NAMA_IMAGE_DETAIL;
import static adeyds.noes.wisbejo.view.ImageViewer.PATH_IMAGE_DETAIL;

public class GaleriAdapter extends RecyclerView.Adapter<GaleriAdapter.HolderGaleri> {
    private ArrayList<Galeri> arrayList = new ArrayList<>();
    private Context context;

    public GaleriAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Galeri> items) {
        arrayList = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HolderGaleri onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_galeri, parent, false);
        return new HolderGaleri(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderGaleri holder, int position) {
        Galeri galeri = getItem(position);
        final String galeriPath = galeri.getPath();
        final String galeriNama = galeri.getNama();

        Glide.with(context)
                .load(PATH_IMAGE_GALERI+galeriPath)
                .apply(new RequestOptions()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher))
                .into(holder.imgGaleri);
        Log.e("item galeri", PATH_IMAGE_GALERI+galeri.getPath());
        holder.imgGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ImageViewer.class);
                intent.putExtra(PATH_IMAGE_DETAIL,PATH_IMAGE_GALERI+galeriPath);
                intent.putExtra(NAMA_IMAGE_DETAIL,galeriNama);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private Galeri getItem(int position) {
        return arrayList.get(position);

    }

    class HolderGaleri extends RecyclerView.ViewHolder {
        private ImageView imgGaleri;

        public HolderGaleri(View itemView) {
            super(itemView);
            imgGaleri = itemView.findViewById(R.id.img_galeri);
        }
    }
}
