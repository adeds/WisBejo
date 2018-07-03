package adeyds.noes.wisbejo.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.print.PageRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.adapter.GaleriAdapter;
import adeyds.noes.wisbejo.adapter.PantaiAdapter;
import adeyds.noes.wisbejo.model.Galeri;
import adeyds.noes.wisbejo.model.Pantai;

import static adeyds.noes.wisbejo.util.AppVar.ADDRESS_LOC;
import static adeyds.noes.wisbejo.util.AppVar.FROM_GALER;
import static adeyds.noes.wisbejo.util.AppVar.GALER_EXTRA;
import static adeyds.noes.wisbejo.util.AppVar.GALER_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class GaleriFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rvGaleri;
    private GaleriAdapter galeriAdapter;
    private ArrayList<Galeri> arrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    public GaleriFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_galeri, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getActivity().getApplicationContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvGaleri = view.findViewById(R.id.rv_galeri);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        swipeRefreshLayout = view.findViewById(R.id.swiper);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvGaleri.setLayoutManager(layoutManager);
        rvGaleri.setHasFixedSize(true);

        galeriAdapter = new GaleriAdapter(getContext());
        rvGaleri.setAdapter(galeriAdapter);

        Log.e("Argumen", getArguments().getString(FROM_GALER));

        checkingYo();
        swipeRefreshLayout.setColorScheme(R.color.me,
                R.color.ji, R.color.ku,
                R.color.hi, R.color.bi,
                R.color.bibi, R.color.ni,
                R.color.ung);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void checkingYo() {
        if (getArguments().getString(FROM_GALER).equals(GALER_EXTRA)) {
            String key = getArguments().getString(GALER_KEY);
            getGaleriParam(key);

        } else {
            getGaleri();
        }
    }

    private void getGaleriParam(String i) {
        AndroidNetworking
                .post("http://"+ADDRESS_LOC+"/PHP/Pantai_API/procedural/view_image_param.php")
                .addBodyParameter("id_pantai", i)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray responseso) {
                        Log.e("respon galeri par", responseso + "");
                        try {
                            Log.e("respon galeri", responseso.length() + "");
                            JSONArray list = responseso;
                            arrayList = new ArrayList<>();
                            // JSONArray list = new JSONArray(response.toString());
                            Log.e("Galeri param", list.length() + "");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject Obj = list.getJSONObject(i);

                                Galeri galeri = new Galeri(Obj);
                                arrayList.add(galeri);
                            }
                            galeriAdapter.setData(arrayList);
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("errur", "catch");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("errur", "ANError " + anError);
                    }


                });
        Log.e("respon galeri parrr", "rune");
    }


    private void getGaleri() {
        AndroidNetworking
                .get("http://" + ADDRESS_LOC + "/PHP/Pantai_API/procedural/view_data_imageAll.php")
                .setTag("GALERI")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("respon galeri", response.length() + "");
                            JSONArray list = response;
                            arrayList = new ArrayList<>();
                            // JSONArray list = new JSONArray(response.toString());
                            Log.e("Galeri", list.length() + "");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject Obj = list.getJSONObject(i);

                                Galeri galeri = new Galeri(Obj);
                                arrayList.add(galeri);
                            }
                            galeriAdapter.setData(arrayList);
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("errur", "catch");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("errur", "ANError " + anError);
                    }
                });

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkingYo();
            }
        }, 2000);
    }
}
