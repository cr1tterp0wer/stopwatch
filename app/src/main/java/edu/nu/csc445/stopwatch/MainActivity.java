package edu.nu.csc445.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    final Timer mTimer = new Timer();
    int mMilliSeconds = 0;
    ButtonState mBtnState = ButtonState.START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate called");
        TextView text1 = findViewById(R.id.text1);
        Button btn1 = findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String btnInfo = btn1.getText().toString();
              Log.d(TAG, btnInfo);

              switch (mBtnState) {
                  case START:
                      mBtnState = ButtonState.STOP;
                      btn1.setText(getString(R.string.btn_stop));
                      break;
                  case STOP:
                      mBtnState = ButtonState.RESET;
                      btn1.setText(getString(R.string.btn_reset));
                      mMilliSeconds = 0;
                      break;
                  default:
                      mBtnState = ButtonState.START;
                      text1.setText(getString(R.string.sw_initial_text));
                      btn1.setText(getString(R.string.btn_start));
                      break;
              }
          }
      });

        // Let's set up the timer
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mBtnState == ButtonState.STOP) {
                            mMilliSeconds++;
                            int seconds = mMilliSeconds/1000;
                            int dispHours = seconds/3600;
                            int dispMins = (seconds%3600)/60;
                            int dispSecs = seconds%60;
                            int dispMillis = mMilliSeconds%1000; // 000 -> 999
                            // hh:mm:ss.mmm
                            String stopWatchInfo = String.format("%02d:%02d:%02d.%03d",
                                                            dispHours,
                                                            dispMins,
                                                            dispSecs,
                                                            dispMillis);
                            text1.setText(stopWatchInfo);
                        }
                    }
                });
            }
        }, 0, 1);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart called");
//    }
}