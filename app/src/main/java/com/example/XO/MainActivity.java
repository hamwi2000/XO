package com.example.XO;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView score1, score2, PlayerStatus;
    private Button [] buttons= new Button[9];
    private Button resetGame;

    private int player1ScoreCount, player2ScoreCount, rountCount;

    boolean activePlayer;

// p1=>0
// p2=>1
// empty =>2

    int[] gameState= {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions= {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {3,4,5}, {2,5,8}, // coloumns
            {0,4,8}, {2,4,6} // corss
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score1= (TextView) findViewById(R.id.score1);
        score2= (TextView) findViewById(R.id.score2);
        PlayerStatus=(TextView) findViewById(R.id.PlayerStatus);

        resetGame= (Button) findViewById(R.id.resetGame);

        for(int i=0; i< buttons.length; i++){
            String buttonID= "btn_"+i;
            int resourceID= getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i]= (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount= 0;
        player1ScoreCount= 0;
        player2ScoreCount= 0;
        activePlayer= true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID= v.getResources().getResourceEntryName(v.getId()); // btn_2
        int gameStatePointer= Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length())); // 2

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer]= 0;
        } else {
            ((Button) v).setText("0");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer]= 1;
        }
        rountCount++;

        if (checkWinner()){
            if(activePlayer){
                player1ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player 1 won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                player2ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player 2 won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        } else if (rountCount == 9){
            playAgain();
            Toast.makeText(this, "no winner!", Toast.LENGTH_SHORT).show();


        } else {
            activePlayer= !activePlayer;
        }
        if(player1ScoreCount>player2ScoreCount){
            PlayerStatus.setText("Player 1 is winning!");
        } else if(player1ScoreCount<player2ScoreCount) {
            PlayerStatus.setText("Player 2 is winning!");
        } else {
            PlayerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                player1ScoreCount= 0;
                player2ScoreCount= 0;
                PlayerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner(){
        boolean winnerResult= false;

        for (int[] winningPosition: winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2){
                winnerResult= true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        score1.setText(Integer.toString(player1ScoreCount));
        score2.setText(Integer.toString(player2ScoreCount));
    }
    public void playAgain(){
        rountCount= 0;
        activePlayer= true;

        for(int i= 0; i<buttons.length; i++){
            gameState[i]= 2;
            buttons[i].setText("");
        }
    }
}