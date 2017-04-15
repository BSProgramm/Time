package ru.ca.time;

import android.app.TimePickerDialog;
import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ArrayList<Excersice> list = new ArrayList<Excersice>();
    TimePickerDialog timePicker;
    TextView tvTime, tvTimeLast;
    CountDownTimer cdt;
    RecyclerView rv;
    Button refresh;
    int min,timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        //setSupportActionBar(toolbar);

        min = 60;
        tvTime = (TextView) findViewById(R.id.tvTimeText);
        tvTimeLast = (TextView) findViewById(R.id.tvTimeLast);
        refresh = (Button) findViewById(R.id.refresh);
        tvTime.setText(getResources().getString(R.string.TimeText) + " " + min/60 + ":0" + min % 60);
        tvTimeLast.setVisibility(View.GONE);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        generateList();

        timePicker = new TimePickerDialog(this,R.style.tp, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                min = i*60 + i1;
                generateList();
            }
        }, 0, 0, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateList();
            }
        });
    }

    private void generateList(){
        tvTimeLast.setVisibility(View.VISIBLE);
        if (String.valueOf(min%60).length() == 1){
            tvTime.setText(getResources().getString(R.string.TimeText) + " " + min/60 + ":0" + min % 60);
        }
        else{
            tvTime.setText(getResources().getString(R.string.TimeText) + " " + min/60 + ":" + min % 60);
        }
        list.clear();
        int i = 0;
        int cycle = 20;
        int nowTime = min;
        int excTime = 0;
        int resTime = new Date().getHours()*60 + new Date().getMinutes();
        String exc = "";
        String result = "";
        while (nowTime != 0 && nowTime > 10 && i < cycle){
            exc = generateExcercise(true);
            if (!result.contains(exc)) {
                excTime = Integer.parseInt(exc.substring(exc.indexOf(':') + 1, exc.indexOf(':', exc.indexOf(':') + 1)));
                if (excTime <= nowTime) {
                    String name = exc.substring(0, exc.indexOf(':'));
                    String desc = exc.substring(exc.lastIndexOf(':')+1);
                    list.add(new Excersice(name, resTime, desc));
                    result += exc + "\n";
                    nowTime -= excTime;
                    resTime += excTime;
                }
            }
            i++;
        }
        if ((nowTime != 0 && nowTime <= 10)||(i >= cycle)){
            exc = generateExcercise(false);
            excTime = nowTime;
            result += exc.replaceAll("@", String.valueOf(nowTime));
            String name = exc.substring(0, exc.indexOf(':'));
            String desc = exc.substring(exc.lastIndexOf(':')+1);
            list.add(new Excersice(name, resTime, desc));
            resTime += excTime;
        }
        rv.setAdapter(new RVadapter(list));
        startTimer(min);
    }

    private String generateExcercise(boolean mode){
        //true-norm
        //false-other
        if (mode){
            int category = 1;
            int r = new Random().nextInt(3);
            switch (r){
                case 0: category = R.array.film;
                    break;
                case 1: category = R.array.books;
                    break;
                case 2: category = R.array.music;
                    break;
            }

            /*int min = 0;
            int max = 0;
            for (int i = 0; i < listComponent.length; i++){
                String exc = listComponent [i];
                int excTime = Integer.parseInt(exc.substring(exc.indexOf(':') + 1, exc.indexOf(':', exc.indexOf(':') + 1)));
                if (nowTime / 2 < excTime){
                    min = i;
                }
                if (nowTime > excTime){
                    min = i;
                }
            }*/

            String[] listComponent = getResources().getStringArray(category);
            return listComponent[new Random().nextInt(listComponent.length)];
        }
        else{
            String[] listComponent = getResources().getStringArray(R.array.other);
            return listComponent[new Random().nextInt(listComponent.length)];
        }
    }

    private void startTimer(int min){
        if (String.valueOf(min%60).length() == 1){
            tvTimeLast.setText("Времени осталось "
                    + String.valueOf(min/60) +":0"
                    + String.valueOf(min % 60));
        }
        else {
            tvTimeLast.setText("Времени осталось "
                    + String.valueOf(min/60) +":"
                    + String.valueOf(min % 60));
        }

        if (cdt != null){
            cdt.cancel();
        }
        cdt = new CountDownTimer(min*60*1000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                if (String.valueOf(minutes).length() == 1){
                    tvTimeLast.setText("Времени осталось " + hours + ":0" + minutes);
                }
                else {
                    tvTimeLast.setText("Времени осталось " + hours + ":" + minutes);
                }
            }

            public void onFinish() {
                tvTimeLast.setText("Время вышло");
            }
        };
        cdt.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
