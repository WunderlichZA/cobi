package com.cobi.cobi;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.VersionAdapter;
import model.AndroidVersion;
import util.DividerItemDecoration;
import viewmodel.VersionsViewModel;

import com.cobi.cobi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "version";
    public static final String TAG = MainActivity.class.getSimpleName();

    // views
    Toolbar toolbar;

    private View parentView;
    private List<AndroidVersion> androidVersions = new ArrayList<>();
    private VersionAdapter adapter;

    VersionsViewModel versionsViewModel;

    ActivityMainBinding mainBinding;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        versionsViewModel = ViewModelProviders.of(this).get(VersionsViewModel.class);

        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerView.hasFixedSize();
        mainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this));

        // Set Toolbar
        setSupportActionBar(toolbar);
        // download data
        makeHttpCall();
    }

    public void makeHttpCall(){
        versionsViewModel.getListLiveData().observe(this, androidVersions ->{
                adapter = new VersionAdapter(androidVersions, R.layout.list_item, this);
                mainBinding.recyclerView.setAdapter(adapter);
        });
    }
}
