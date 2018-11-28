package com.example.michael.proiectunitbv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class choose_timeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_timeline);


        //STATUS BAR TRANSPARENT
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //recycle view cu adapter

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        List<item> mList = new ArrayList<>();
        mList.add(new item(R.drawable.logo_at,"AT", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_c,"CONSTRUCTII", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_d,"DREPT", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_dpm,"DPM", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_econ,"ECON", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_edsm,"SPORT", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_esc,"IESC", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_inf,"MATEINFO", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_ingmat,"SIM", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_l,"IL", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_lit,"LITERE", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_m,"MECANICA", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_med,"MEDICINA", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_mzc,"MUZICA", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_psiho,"PSIHOEDU", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_silv,"SILVIC", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_socio,"SOCIO", R.drawable.unitbv2, 3));
        mList.add(new item(R.drawable.logo_tmi,"ITMI", R.drawable.unitbv2, 3));

        Adapter adapter = new Adapter(this, mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}
