package com.jctampere.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //red = 0 yellow = 1
    int activePlayer = 0;
    int winner = 2;

    //gameStatus: 2 means unplayed
    int[] gameStatus={2,2,2,2,2,2,2,2,2};
    int[][] winningPostions ={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int gamerunning = 1;
    int filled=0;




    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int pos = Integer.parseInt(counter.getTag().toString());

        if (gameStatus[pos] == 2 && gamerunning==1) {
            counter.setTranslationY(-1000f);
            filled++;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                gameStatus[pos] = 0;
                activePlayer = 1;

            } else {
                counter.setImageResource(R.drawable.yellow);
                gameStatus[pos] = 1;
                activePlayer = 0;

            }
            counter.animate().translationYBy(1000f).rotation(30f).setDuration(300);
        }else if(gameStatus[pos] !=2 && gamerunning==1){
            Toast.makeText(getApplicationContext(),"Can't drop in this slot", Toast.LENGTH_LONG).show();
        }

        //check if anyone has won
        for(int[] winningPostion : winningPostions){
            if(gameStatus[winningPostion[0]]==gameStatus[winningPostion[1]] &&
                    gameStatus[winningPostion[1]]==gameStatus[winningPostion[2]] &&
                     gameStatus[winningPostion[0]]!=2){
                     gamerunning = 0;
                     winner = gameStatus[winningPostion[0]];
                     String w="";
                     if(winner == 0)
                         w ="Red has won";
                     if(winner ==1)
                         w="Yellow has won";
                     TextView winningTextView = (TextView)findViewById(R.id.textViewWinner);
                     winningTextView.setText(w);
                     LinearLayout resultLayout = (LinearLayout)findViewById(R.id.playagainLayout);
                     resultLayout.setVisibility(View.VISIBLE);
                     break;
            }
        }

        //check if all fields filled
       if(filled>=9){
           TextView winningTextView = (TextView)findViewById(R.id.textViewWinner);
           winningTextView.setText("Game Draw");
           LinearLayout resultLayout = (LinearLayout)findViewById(R.id.playagainLayout);
           resultLayout.setVisibility(View.VISIBLE);
       }


    }


   public void playAgain(View view){
       activePlayer = 0;
       winner = 2;
       gamerunning = 1;
       filled=0;
       for (int i=0; i<gameStatus.length; i++)
           gameStatus[i] = 2;
       LinearLayout resultLayout = (LinearLayout)findViewById(R.id.playagainLayout);
       resultLayout.setVisibility(View.INVISIBLE);

       GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
       for (int i=0; i<gridLayout.getChildCount();i++){
           ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
       }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
