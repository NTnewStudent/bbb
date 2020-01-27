package com.example.calculatorapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.calculatorapplication.Model.Lens;
import com.example.calculatorapplication.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LensListActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ListView list;
    private FloatingActionButton fab;
    SimpleAdapter adapter;
    Context context;
    LensManager manager;
    List<Map<String,String>> mapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Depth of Field Calculator");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        list = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        manager=LensManager.getInstance();
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));

        for (Lens lens: manager) {
            Map<String,String> once = new HashMap<>();
            once.put("data",lens.getMake()+" "+lens.getFocalLength()+"mm  F"+lens.getMaximumAperture());
            mapList.add(once);
        }

        adapter= new SimpleAdapter(context,mapList,R.layout.list_item,new String[]{"data"},new int[]{R.id.camera});
        //here is the list item click listen
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lens lens = manager.getLens(position);
                Intent intent = new Intent(context,CalculateActivity.class);
                intent.putExtra("make",lens.getMake());
                intent.putExtra("aperture",lens.getMaximumAperture());
                intent.putExtra("length",lens.getFocalLength());
                startActivity(intent);
            }
        });

        list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivityForResult(makeIntentForNewLens(context),200);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode ==200){
            Lens lens = new Lens();
            lens.setMaximumAperture(data.getDoubleExtra("aperture",0.0));
            lens.setMake(data.getStringExtra("make"));
            lens.setFocalLength(data.getIntExtra("length",0));
            Map<String,String> once = new HashMap<>();
            once.put("data",lens.getMake()+" "+lens.getFocalLength()+"mm  F"+lens.getMaximumAperture());
            mapList.add(once);
            manager.add(lens);
            adapter.notifyDataSetChanged();
        }
    }

    public static Intent makeIntentForNewLens(Context context){
        return new Intent(context,AddActivity.class);
    }
}
