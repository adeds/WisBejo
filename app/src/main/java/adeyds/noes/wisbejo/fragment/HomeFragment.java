package adeyds.noes.wisbejo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.adapter.PantaiAdapter;
import adeyds.noes.wisbejo.model.Pantai;
import adeyds.noes.wisbejo.prefs.SharedPref;

import static adeyds.noes.wisbejo.util.AppVar.ADDRESS_LOC;
import static adeyds.noes.wisbejo.util.AppVar.TO_DETAIL;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

private RecyclerView rvPantai;
    private ArrayList<Pantai> data;
    private PantaiAdapter pantaiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPantai = view.findViewById(R.id.rv_pantai);
        pantaiAdapter = new PantaiAdapter(getContext());
        swipeRefreshLayout = view.findViewById(R.id.swiper);
        rvPantai.setAdapter(pantaiAdapter);
        rvPantai.setLayoutManager(new GridLayoutManager(getContext(), 2));
        getPantai();
        swipeRefreshLayout.setColorScheme(R.color.me,
                R.color.ji, R.color.ku,
                R.color.hi, R.color.bi,
                R.color.bibi, R.color.ni,
                R.color.ung);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getPantai() {
        AndroidNetworking
                .get("http://"+ADDRESS_LOC+"/PHP/Pantai_API/procedural/view_data_pantai.php")
                .setTag("HOME")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("respon home", response.length()+"");
                            JSONArray list = response;
                            data= new ArrayList<>();
                           // JSONArray list = new JSONArray(response.toString());
                            Log.e("Home", list.length()+"");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject newsObj = list.getJSONObject(i);

                                Pantai pantai = new Pantai(newsObj);
                                data.add(pantai);

                            }
                            pantaiAdapter.setData(data);
                            swipeRefreshLayout.setRefreshing(false);


                            }catch (Exception e){
                            e.printStackTrace();
                            Log.e("errur", "catch");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    Log.e("errur", "ANError "+anError);
                    }
                });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getActivity().getApplicationContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==TO_DETAIL){
            Log.e("FROM DETAIL", "HERE");
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               getPantai();
            }
        }, 2000);
    }
}
