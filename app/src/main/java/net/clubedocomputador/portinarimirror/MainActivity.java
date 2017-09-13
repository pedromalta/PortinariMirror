package net.clubedocomputador.portinarimirror;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private LinearLayout mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mContentView = (LinearLayout) findViewById(R.id.fullscreen_content);
        mContentView.setOnTouchListener(new View.OnTouchListener() { public boolean onTouch(View v, MotionEvent event) {  return true; } });

        for(BusTime time : BusTime.getSchedule(6)){
            TextView timeView = new TextView(this);
            timeView.setText(time.getTime());
            timeView.setGravity(Gravity.CENTER);
            if (time.isNext())
                timeView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.bus, 0, 0, 0);
            timeView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            mContentView.addView(timeView);
        }



    }



}
