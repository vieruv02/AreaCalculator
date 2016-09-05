package com.example.vladislav.tutorial2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private LengthPicker mWidth;
    private LengthPicker mHeight;
    private TextView mArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWidth = (LengthPicker) findViewById(R.id.width);
        mHeight = (LengthPicker) findViewById(R.id.height);
        mArea = (TextView) findViewById(R.id.area);

        LengthPicker.onChangeListener listener = new LengthPicker.onChangeListener(){
            public void onChange(int length){
                updateArea();
            }
        };
        mWidth.setOnChangeListener(listener);
        mHeight.setOnChangeListener(listener); 
    }

    private void updateArea() {
        int area = mWidth.getNumInches() * mHeight.getNumInches();
        mArea.setText(area + " sq in");
    }

    //Makes area 0 as a default
    @Override
    protected void onResume() {
        super.onResume();
        updateArea();
    }
}
