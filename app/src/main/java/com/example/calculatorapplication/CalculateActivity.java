package com.example.calculatorapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatorapplication.Model.DepthFieldCalculator;
import com.example.calculatorapplication.Model.Lens;

import java.text.DecimalFormat;

public class CalculateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nearFocal;
    private TextView farFocal;
    private TextView dof;
    private TextView hyperfocalDistance;
    private TextView Detail;
    private EditText coc;
    private EditText subjectDistance;
    private EditText selectAperture;
    private Button ok;
    Context context;
    Lens lens=new Lens();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        context = this;
        Intent intent = getIntent();
        lens.setMaximumAperture(intent.getDoubleExtra("aperture",0.0));
        lens.setMake(intent.getStringExtra("make"));
        lens.setFocalLength(intent.getIntExtra("length",0));
        initView();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Calculate DoF");
    }

    private void initView() {
        nearFocal = (TextView) findViewById(R.id.nearFocal);
        farFocal = (TextView) findViewById(R.id.farFocal);
        dof = (TextView) findViewById(R.id.dof);
        hyperfocalDistance = (TextView) findViewById(R.id.hyperfocalDistance);
        Detail = (TextView) findViewById(R.id.Detail);
        coc = (EditText) findViewById(R.id.coc);
        subjectDistance = (EditText) findViewById(R.id.subjectDistance);
        selectAperture = (EditText) findViewById(R.id.selectAperture);
        ok = (Button) findViewById(R.id.ok);

        Detail.setText(lens.toString());

        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String cocString = coc.getText().toString().trim();
        if (TextUtils.isEmpty(cocString)) {
            Toast.makeText(this, "ex:0.029 for 0.029m", Toast.LENGTH_SHORT).show();
            return;
        }

        String subjectDistanceString = subjectDistance.getText().toString().trim();
        if (TextUtils.isEmpty(subjectDistanceString)) {
            Toast.makeText(this, "ex:1.5 for 1.5m", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectApertureString = selectAperture.getText().toString().trim();
        if (TextUtils.isEmpty(selectApertureString)) {
            Toast.makeText(this, "ex:2.8 for F2.8", Toast.LENGTH_SHORT).show();
            return;
        }

        double distance = Double.parseDouble(subjectDistanceString);
        double coc =Double.parseDouble(cocString);
        double selectAperture = Double.parseDouble(selectApertureString);


        // TODO validate success, do something
        DepthFieldCalculator depthFieldCalculator = new DepthFieldCalculator(lens,distance,selectAperture,coc);
        DecimalFormat format = new DecimalFormat("0.00");

        this.hyperfocalDistance.setText(format.format(depthFieldCalculator.getTransformHyperFocalDistance())+"m");
        this.farFocal.setText(format.format(depthFieldCalculator.getTransformFarFocal())+"m");
        this.nearFocal.setText(format.format(depthFieldCalculator.getTransformNearFocal())+"m");
        this.dof.setText(format.format(depthFieldCalculator.getTransformDofFocal())+"m");
    }
}
