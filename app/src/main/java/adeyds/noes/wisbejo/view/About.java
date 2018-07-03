package adeyds.noes.wisbejo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import adeyds.noes.wisbejo.R;

public class About extends AppCompatActivity {
public static final String EXTRA_ABT = "ABOUT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra(EXTRA_ABT).equals("US"))
            setContentView(R.layout.about_us );
        else  setContentView(R.layout.activity_about);
    }
}
