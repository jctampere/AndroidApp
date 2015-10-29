package com.jctampere.braintraining;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //int Min = 0;
    //int Max = 50;
    int result,total, rightsum;
    ArrayList<Integer> answers;
    int locationOfCorrectAnswer, userSelectedLocation;


    TextView calTextView, timeTextView, infoTextView, summaryTextView;

    Button button0, button1,button2, button3, goButton, restartButton;

    CountDownTimer countDownTimer;


    public void findViews(){
        calTextView = (TextView)findViewById(R.id.calTextView);

        button0 = (Button)findViewById(R.id.resultButton0);
        button1 = (Button)findViewById(R.id.resultButton1);
        button2 = (Button)findViewById(R.id.resultButton2);
        button3 = (Button)findViewById(R.id.resultButton3);
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        summaryTextView = (TextView) findViewById(R.id.summaryTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        goButton = (Button) findViewById(R.id.goButton);
        restartButton = (Button) findViewById(R.id.restartButton);

    }
    public void setViewsInvisible(){
        goButton.setVisibility(View.VISIBLE);

        calTextView.setVisibility(View.GONE);
        button0.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);
        summaryTextView.setVisibility(View.GONE);
        timeTextView.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
    }

    public void setViewsVisible(){
        goButton.setVisibility(View.GONE);


        calTextView.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        infoTextView.setVisibility(View.VISIBLE);
        summaryTextView.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);

        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

    }

    public void initialCal(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        /* Another way of rand generation
        a = Min+(int)(Math.random()*((Max-Min)+1));
        b = Min+(int)(Math.random()*((Max-Min)+1));
        */
        result = a+b;

        calTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        answers = new ArrayList<Integer>();
        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        for(int i=0; i<4; ++i){
            if(i==locationOfCorrectAnswer){
                answers.add(result);

            }else{
                incorrectAnswer=rand.nextInt(41);
                while(incorrectAnswer == (result)){
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }


        button0.setText(Integer.toString(answers.get(0)));

        button1.setText(Integer.toString(answers.get(1)));

        button2.setText(Integer.toString(answers.get(2)));

        button3.setText(Integer.toString(answers.get(3)));

    }

    public void selectResult(View view){
           userSelectedLocation = Integer.parseInt((String)view.getTag());

           total = total+1;


           if(userSelectedLocation == locationOfCorrectAnswer ){
               infoTextView.setText("Correct");
               rightsum = rightsum + 1;
           }else{
               infoTextView.setText("Wrong");
           }
           if((rightsum > 9) || (total>9)){
               summaryTextView.setTextSize(20);
           }
           summaryTextView.setText(Integer.toString(rightsum)+"/"+Integer.toString(total));
           initialCal();
    }


    public void startGame(View view){
        setViewsVisible();
        initialCal();
        total = 0;
        rightsum = 0;
        summaryTextView.setText(Integer.toString(rightsum)+"/"+Integer.toString(total));
        countDownTimer = new CountDownTimer(30*1000+200, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timeTextView.setText(Integer.toString((int)(long)(millisUntilFinished/1000))+"s");
            }

            @Override
            public void onFinish() {

                timeTextView.setText("0s");

                infoTextView.setText("Time out, your score "+Integer.toString(rightsum)+"/"+Integer.toString(total));
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                restartButton.setVisibility(View.VISIBLE);


            }


        }.start();


    }

    public void startNew(View view){
        setViewsInvisible();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setViewsInvisible();




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
