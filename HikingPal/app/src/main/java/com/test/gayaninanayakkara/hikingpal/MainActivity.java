package com.test.gayaninanayakkara.hikingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;




public class MainActivity extends ActionBarActivity {

    TextView s1,s2,s3,start;
    Handler uiThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = (TextView) findViewById(R.id.tvStart1);
        s2 = (TextView) findViewById(R.id.tvStart2);
        s3 = (TextView) findViewById(R.id.tvStart3);
        start=(TextView)findViewById(R.id.tvName);

        Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        };
        thread.start();

        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        s1.setVisibility(View.VISIBLE);
                        s2.setVisibility(View.VISIBLE);
                        s3.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 5000);


        Thread thr=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(intent);
                }
            }
        };
        thr.start();

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
}
