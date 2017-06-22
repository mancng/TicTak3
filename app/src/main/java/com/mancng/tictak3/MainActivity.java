package com.mancng.tictak3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = light_blue, 1 = pink

    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 means unplayed

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int turnsTaken = 0;

    TextView winnerMessage;


    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.light_blue);

                activePlayer = 1;

                turnsTaken++;

                Log.i("TurnNumber", "Turn: " + turnsTaken);

            } else {

                counter.setImageResource(R.drawable.pink);

                activePlayer = 0;

                turnsTaken++;
                Log.i("TurnNumber", "Turn: " + turnsTaken);
            }

            counter.animate().translationYBy(1000f).setDuration(300);
            Log.i("Counter tag", "Counter is: " + gameState[tappedCounter]);


            for (int[] winningPos : winningPositions) {

                if (gameState[winningPos[0]] == gameState[winningPos[1]] &&
                        gameState[winningPos[1]] == gameState[winningPos[2]] &&
                        gameState[winningPos[0]] != 2) {

                    Log.i("Score", "The winning position is " + gameState[winningPos[0]]);

                    // Someone has won!

                    gameIsActive = false;

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                    layout.setVisibility(View.VISIBLE);

                    TextView winnerMessage = (TextView) findViewById(R.id.txtWinnerMessage);

                    if (gameState[winningPos[0]] == 1) {

                        winnerMessage.setText("Pink has won!");

                    } else {

                        winnerMessage.setText("Baby Blue has won!");
                    }

                } else {

                    boolean gameIsOver = true;

                    for (int counterState : gameState) {

                        if (counterState == 2) gameIsOver = false;
                    }

                    if (gameIsOver) {

                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);

                        TextView winnerMessage = (TextView) findViewById(R.id.txtWinnerMessage);

                        winnerMessage.setText("Play again!");

                    }
                }
            }
        }
    }

    public void resetGame(View view) {

        gameIsActive = true;

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

        layout.setVisibility((View.INVISIBLE));

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
