package com.test.gayaninanayakkara.hikingpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    double lon, lat;
    String name, desc, ele,longi,lati;
    TextView tvname, tvdesc, tvele, tvlon, tvlat;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bun = getIntent().getExtras();

        if (bun != null) {
            name = bun.getString("compTitle");
            desc = bun.getString("compDesc");
            ele = bun.getString("compEle");
            longi=bun.getString("compLong");
            lati=bun.getString("compLat");

        }



        tvname = (TextView) findViewById(R.id.tvName);
        tvname.setText(name);

        tvdesc = (TextView) findViewById(R.id.tvDescription);
        tvdesc.setText(desc);

        tvele = (TextView) findViewById(R.id.tvElevation);
        tvele.setText(ele);

        tvlon = (TextView) findViewById(R.id.tvLongitude);
        tvlon.setText(longi);

        tvlat = (TextView) findViewById(R.id.tvLatitude);
        tvlat.setText(lati);

        btn=(Button)findViewById(R.id.btnWeather);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,MiddleActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
