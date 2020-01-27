package com.example.calculatorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculatorapplication.Model.Lens;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText make;
    private EditText focalLength;
    private EditText aperture;
    private Button save;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Lens Details");
    }

    private void initView() {
        make = (EditText) findViewById(R.id.make);
        focalLength = (EditText) findViewById(R.id.focalLength);
        aperture = (EditText) findViewById(R.id.aperture);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                submit();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String makeString = make.getText().toString().trim();
        if (TextUtils.isEmpty(makeString)) {
            Toast.makeText(this, "ex:Canon", Toast.LENGTH_SHORT).show();
            return;
        }

        String focalLengthString = focalLength.getText().toString().trim();
        if (TextUtils.isEmpty(focalLengthString)) {
            Toast.makeText(this, "ex:200 for 200mm", Toast.LENGTH_SHORT).show();
            return;
        }

        String apertureString = aperture.getText().toString().trim();
        if (TextUtils.isEmpty(apertureString)) {
            Toast.makeText(this, "ex:2.8 for F2.8", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        Intent intent = new Intent();
        Lens lens = new Lens();
        lens.setFocalLength(Integer.parseInt(focalLengthString));
        lens.setMake(makeString);
        lens.setMaximumAperture(Double.parseDouble(apertureString));
        intent.putExtra("make",lens.getMake());
        intent.putExtra("aperture",lens.getMaximumAperture());
        intent.putExtra("length",lens.getFocalLength());

        setResult(200,intent);
        finish();

    }
}
