package adeyds.noes.wisbejo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import adeyds.noes.wisbejo.view.DetailActivity;
import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.model.Pantai;

import static adeyds.noes.wisbejo.util.AppVar.COVER_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.DESK_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.HTM_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.ID_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.KONTAK_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.LOKASI_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.NAMA_EXTRA_DETAIL;
import static adeyds.noes.wisbejo.util.AppVar.PATH_IMAGE_GALERI;
import static adeyds.noes.wisbejo.util.AppVar.TO_DETAIL;

public class PantaiAdapter extends RecyclerView.Adapter<PantaiAdapter.HolderPantai> {
    private ArrayList<Pantai> arrayList = new ArrayList<>();
    private Context context;

    public PantaiAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public HolderPantai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantai, parent, false);
        return new HolderPantai(view);
    }

    public void setData(ArrayList<Pantai> items) {
        arrayList = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPantai holder, int position) {
        final Pantai pantai = getItem(position);
        holder.tvPantai.setText(pantai.getNama());

        holder.containerPantai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra(NAMA_EXTRA_DETAIL, pantai.getNama());
                intent.putExtra(ID_EXTRA_DETAIL, pantai.getId_pantai());
                intent.putExtra(LOKASI_EXTRA_DETAIL, pantai.getLokasi());
                intent.putExtra(HTM_EXTRA_DETAIL, pantai.getHtm());
                intent.putExtra(COVER_EXTRA_DETAIL, pantai.getCover());
                intent.putExtra(KONTAK_EXTRA_DETAIL, pantai.getKontak());
                intent.putExtra(DESK_EXTRA_DETAIL, pantai.getDeskripsi());

                ((Activity) context).startActivityForResult(intent,TO_DETAIL);
            }
        });
        Glide.with(context)
                .load(PATH_IMAGE_GALERI+pantai.getCover())
                .apply(new RequestOptions()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher))
                .into(holder.imgPantai);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
//
private Pantai getItem(int position) {
    return arrayList.get(position);

}


    class HolderPantai extends RecyclerView.ViewHolder {
        private ImageView imgPantai;
        private CardView containerPantai;
        private TextView tvPantai;




        public HolderPantai(View itemView) {
            super(itemView);

            imgPantai = itemView.findViewById(R.id.img_pantai);
            tvPantai = itemView.findViewById(R.id.tv_pantai);
            containerPantai = itemView.findViewById(R.id.container_pantai);
//            containerPantai.setOnClickListener(new CustomClick(getPosition(), new CustomClick.OnItemClickCallback() {
//                @Override
//                public void onItemClicked(View view, int position) {
//                    final Pantai pantai = getItem(position);
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    view.getContext().startActivity(intent);
//                }
//            }));


        }
    }
}
