package com.example.android.pinponk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.android.pinponk.MainActivity.player_2_name;

public class GameCounterActivity extends AppCompatActivity {

    private static int score_player_1=0;
    private static int score_player_2=0;
    private boolean service_player_1;
    private boolean service_player_2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_counter);

        //set player name 1 to Game Counter Activity layout
        TextView player1Text=(TextView) findViewById(R.id.player1_name_GC);
        player1Text.setText(MainActivity.player_1_name);


        //set player name 1 to Game Counter Activity layout
        TextView player2Text=(TextView) findViewById(R.id.player2_name_GC);
        player2Text.setText(MainActivity.player_2_name);

        //find service players 1, 2
        TextView player1_service=(TextView) findViewById(R.id.player1_service_GC);
        TextView player2_service=(TextView) findViewById(R.id.player2_service_GC);

        if(MainActivity.service_player_1_start==true){
            player1_service.setText("Service");
            player2_service.setText("");
        }else{
            player1_service.setText("");
            player2_service.setText("Service");
        }


    }
}
