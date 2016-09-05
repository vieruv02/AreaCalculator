/////////////////////////////////////////////////////////////
//SHOWING HOW WE CAN SWITCH FROM ACTIVITY TO A CUSTOM VIEW!!!!!!!!!!!
/////////////////////////////////////////////////////////////

package com.example.vladislav.tutorial2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Vladislav on 6/15/2016.
 */
public class LengthPicker extends LinearLayout {
    private static final String KEY_SUPER_STATE = "superState";
    private static final String KEY_NUM_INCHES = "numInches";
    private View mPlusButton;
    private TextView mTextView;
    private View mMinusButton;

    //Keeps track on how many times you clicked on minus and plus
    private int mNumInches = 0;
    private onChangeListener mListener = null;

    //1st constructor used in JAVA
    public LengthPicker(Context context) {
        super(context);
        init();
    }

    //2nd constructor used in XML
    public LengthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.length_picker, this);

        //Use it to find two buttons and text
        mPlusButton = findViewById(R.id.plus_button);
        mTextView = (TextView)findViewById(R.id.text);
        mMinusButton = findViewById(R.id.minus_button);

        updateControls();

        //Now we have defined our LengthPicker
        View.OnClickListener listener = new View.OnClickListener(){
            public void onClick(View v){
                switch (v.getId()){
                    case R.id.plus_button:
                        mNumInches++;
                        if(mListener != null){
                            mListener.onChange(mNumInches);
                        }
                        updateControls();
                        break;
                    case R.id.minus_button:
                        if(mNumInches >0){
                            mNumInches--;
                            if(mListener != null){
                                mListener.onChange(mNumInches);
                            }
                            updateControls();
                        }
                        break;
                }
            }
        };
        mPlusButton.setOnClickListener(listener);
        mMinusButton.setOnClickListener(listener);

        setOrientation(LinearLayout.HORIZONTAL);
    }

    //Makes sure that when use reverse the screen position data remains the same
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_NUM_INCHES, mNumInches);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mNumInches = bundle.getInt(KEY_NUM_INCHES);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
        }
        else{
            super.onRestoreInstanceState(state);
        }
        updateControls();
    }

    private void updateControls(){
        int feet = mNumInches /12;
        int inches = mNumInches%12;

        String text = String.format("%d' %d\"", feet, inches);
        if(feet == 0){
            text = String.format("%d\"", inches);
        }
        else{
            if(inches == 0){
                text = String.format("%d'", feet);
            }
        }
        mTextView.setText(text);
        mMinusButton.setEnabled(mNumInches > 0);
    }

    public int getNumInches(){
        return mNumInches;
    }

    public void setOnChangeListener(onChangeListener listener){
        mListener = listener;
    }

    public interface onChangeListener{
        public void onChange(int length);
    }
}
