package adeyds.noes.wisbejo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import adeyds.noes.wisbejo.R;
import adeyds.noes.wisbejo.view.About;

import static adeyds.noes.wisbejo.view.About.EXTRA_ABT;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {

    private LinearLayout containerApps, containerUs;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerApps = view.findViewById(R.id.container_apps);
        containerUs = view.findViewById(R.id.container_us);
        containerUs.setOnClickListener(this);
        containerApps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(), About.class);
        if (v.getId() == R.id.container_us) {
            intent.putExtra(EXTRA_ABT, "US");
        }
        if (v.getId() == R.id.container_apps) {
            intent.putExtra(EXTRA_ABT, "APPS");
        }
        startActivity(intent);
    }
}
