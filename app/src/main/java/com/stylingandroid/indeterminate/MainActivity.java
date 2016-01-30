package com.stylingandroid.indeterminate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.stylingandroid.indeterminate.drawable.IndeterminateDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        IndeterminateDrawable drawable = IndeterminateDrawable.newInstance(this);
        imageView.setImageDrawable(drawable);
        drawable.start();
    }
}
