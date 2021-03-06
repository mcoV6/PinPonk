package com.example.android.pinponk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.example.android.pinponk.MainActivity.player_1_name;
import static com.example.android.pinponk.MainActivity.player_2_name;
import static com.example.android.pinponk.MainActivity.service_player_1_start;
import static com.example.android.pinponk.MainActivity.service_player_2_start;

public class GameCounterActivity extends AppCompatActivity {

    private int score_player_1=0;
    private int score_player_2=0;
    private boolean service_player_1_start;
    private boolean service_player_1;
    boolean player1_ser;
    boolean player2_ser;
    private Context gContext;
    private Activity gActivity;
    private LinearLayout pl_1;
    private LinearLayout pl_2;
    private TextView undoTxt;

    private TextView pl_1_score;
    private TextView pl_2_score;
    private TextView pl_1_setTextView;
    private TextView pl_2_setTextView;
    private ViewGroup pl_1_service;
    private ViewGroup pl_2_service;


    private int pl_1_set=0;
    private int pl_2_set=0;

    private int scoreToWin=11;
    private int pointsToChangeService=5;


    private int totalScore;
    List<RoundScore> scoreList;


    private void initiate(){
        //Get the context
        gContext=getApplicationContext();

        // Get the activity
        gActivity = GameCounterActivity.this;

        //set player name 1 to Game Counter Activity layout
        TextView player1Text=(TextView) findViewById(R.id.player1_name_GC);
        player1Text.setText(player_1_name);

        //set player name 1 to Game Counter Activity layout
        TextView player2Text=(TextView) findViewById(R.id.player2_name_GC);
        player2Text.setText(MainActivity.player_2_name);

        //find service players 1, 2
        pl_1_service=(ViewGroup) findViewById(R.id.player1_service_GC);
        pl_2_service= (ViewGroup) findViewById(R.id.player2_service_GC);



        //find service players 1, 2
        pl_1_setTextView=(TextView) findViewById(R.id.player1_set_GC);
        pl_2_setTextView=(TextView) findViewById(R.id.player2_set_GC);
        pl_1_setTextView.setText(""+pl_1_set);
        pl_2_setTextView.setText(""+pl_2_set);


        pl_1=(LinearLayout) findViewById(R.id.player1_Layout_GC);
        pl_2=(LinearLayout) findViewById(R.id.player2_Layout_GC);

        pl_1_score= (TextView) findViewById(R.id.player1_score_GC);
        pl_1_score.setText(""+score_player_1);

        pl_2_score= (TextView) findViewById(R.id.player2_score_GC);
        pl_2_score.setText(""+score_player_2);

        undoTxt=(TextView) findViewById(R.id.undo_GC);
        undoTxt.setOnClickListener(undoListener);

        service_player_1_start=player_1_startWithService();
        service_player_1=service_player_1_start;

        showService(service_player_1_start);

        Toast.makeText(gContext,"do loop again",Toast.LENGTH_SHORT).show();


    }

    private boolean player_1_hasService(int tScore){

        if(score_player_1>(scoreToWin-2) && score_player_2>(scoreToWin-2)){
            //This is service for thigh break
            if(service_player_1_start){
                if(((tScore)%(2*2))<2){
                    return true;
                }else{
                    return false;
                }
            }else{
                if(((tScore)%(2*2))>=2){
                    return false;
                }else {
                    return true;
                }
            }

        }else{
            //this is normal game
            if(service_player_1_start){

                if(((tScore)%(2*pointsToChangeService))<pointsToChangeService){
                    return true;
                }else{
                    return false;
                }
            }else{

                if(((tScore)%(2*pointsToChangeService))>=pointsToChangeService){
                    return true;
                }else {
                    return false;
                }
            }
        }


    }

    private boolean player_1_startWithService(){
        if(MainActivity.service_player_1_start){
            if((pl_1_set+pl_2_set)%2==0) return true;
            return false;
        }else{
            if((pl_1_set+pl_2_set)%2==0) return false;
            return true;
        }


    }

    private void showService(boolean player_1_service){
        if(player_1_service){
            pl_1_service.setVisibility(View.VISIBLE);
            pl_2_service.setVisibility(View.GONE);
        }else{
            pl_2_service.setVisibility(View.VISIBLE);
            pl_1_service.setVisibility(View.GONE);
        }
    }

    private void changeService(){
        service_player_1_start=(!service_player_1_start);
        showService(service_player_1_start);
    }

    private void upDateSet(){
        pl_1_setTextView.setText(""+pl_1_set);
        pl_2_setTextView.setText(""+pl_2_set);
        winMatch();
    }

    private void winMatch(){
        //check if somebody has won the match
        if(pl_1_set==2)showWinner(player_1_name);
        if(pl_2_set==2)showWinner(player_2_name);
        return;
    }

    private void showWinner(String winner){

        //create pop up window
        LayoutInflater layoutInflater = (LayoutInflater) gContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container_GC = (ViewGroup) layoutInflater.inflate(R.layout.pop_winner, null);
        final PopupWindow popupWindow = new PopupWindow(container_GC, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // find textView in pop_winner.xml to show winner
        TextView winnerTextView=(TextView) container_GC.findViewById(R.id.winner_message);
        //set text
        winnerTextView.setText(winner + " has won match");

        //find buttons rematch and new game
        Button rematchButton=(Button)container_GC.findViewById(R.id.rematch_button_pop);
        Button newmatchButton=(Button) container_GC.findViewById(R.id.newmatch_button_pop);

        //create onclick listener
        rematchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                rematch();
                popupWindow.dismiss();
            }
        });

        newmatchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                newMatch();
                popupWindow.dismiss();

            }
        });


        ViewGroup linearLayout=(ViewGroup) findViewById(R.id.game_counter_layout);


        popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.null));



    }

    private void winSet(){
        // this function says who won set and set all parameters for new set
        //normal end
        if(totalScore<(2*(scoreToWin-1))){
            if(score_player_1==scoreToWin) {
                // player 1 won set
                pl_1_set++;
                upDateSet();
                resetCounter();
                //service_player_1_start=player_1_startWithService();
                changeService();
                //Toast.makeText(gContext,"set = "+pl_1_set+" : "+pl_2_set,Toast.LENGTH_SHORT).show();
                return;
            }else if(score_player_2==scoreToWin){
                // player 2 won set
                pl_2_set++;
                upDateSet();
                resetCounter();
                //service_player_1_start=player_1_startWithService();
                changeService();
                //Toast.makeText(gContext,"set = "+pl_1_set+" : "+pl_2_set,Toast.LENGTH_SHORT).show();
                return;
            }else{
                return;
            }
        }else{
            if(score_player_1>(score_player_2+1)){
                    pl_1_set++;
                    upDateSet();
                    resetCounter();
                    //service_player_1_start=player_1_startWithService();
                    changeService();
                    //Toast.makeText(gContext,"set = "+pl_1_set+" : "+pl_2_set,Toast.LENGTH_SHORT).show();
                    return;
            }else if(score_player_2>(score_player_1+1)){
                    pl_2_set++;
                    upDateSet();
                    resetCounter();
                    //service_player_1_start=player_1_startWithService();
                    changeService();
                    //Toast.makeText(gContext,"set = "+pl_1_set+" : "+pl_2_set,Toast.LENGTH_SHORT).show();
                    return;
            }else{
                return;
            }

        }
    }

    private void rematch(){
        resetCounter();
        pl_1_set=0;
        pl_2_set=0;
        pl_1_setTextView.setText(""+pl_1_set);
        pl_2_setTextView.setText(""+pl_2_set);
        showServicePopup();
    }

    private void resetCounter(){
        scoreList.clear();
        score_player_1=0;
        score_player_2=0;
        pl_1_score.setText(""+(score_player_1));
        pl_2_score.setText(""+(score_player_2));

    }

    private void newMatch(){
        rematch();
        Intent mainActivity=new Intent(gContext,MainActivity.class);
        startActivity(mainActivity);
    }



    private void showServicePopup(){


        //create pop up window
        LayoutInflater layoutInflater = (LayoutInflater) gContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container_RM = (ViewGroup) layoutInflater.inflate(R.layout.pop_selection, null);
        final PopupWindow popupRematch = new PopupWindow(container_RM, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        RadioButton rb_1= container_RM.findViewById(R.id.radio_p11);
        RadioButton rb_2= container_RM.findViewById(R.id.radio_p22);
        rb_1.setText(player_1_name);
        rb_2.setText(player_2_name);

        Button start_popUp = container_RM.findViewById(R.id.start_button_pop);
        final RadioGroup radioService_popUp_RM = container_RM.findViewById(R.id.radio_group_service_pop);

        RadioGroup.OnCheckedChangeListener serviceListener;serviceListener=new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                View radioButton=radioGroup.findViewById(i);
                int index =radioGroup.indexOfChild(radioButton);

                if (index==0){
                    MainActivity.service_player_1_start=true;
                    MainActivity.service_player_2_start=false;
                    player1_ser=true;
                    player2_ser=false;
                }else if(index==1){
                    MainActivity.service_player_1_start=false;
                    MainActivity.service_player_2_start=true;
                    player1_ser=true;
                    player2_ser=false;
                }else{
                }
                Toast.makeText(gActivity,"player "+index,Toast.LENGTH_SHORT).show();
            }
        };

        radioService_popUp_RM.setOnCheckedChangeListener(serviceListener);

        LinearLayout linearLayout_RM = (LinearLayout) findViewById(R.id.game_counter_layout);

        popupRematch.showAtLocation(linearLayout_RM, Gravity.CENTER, 0, 0);
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.null));
        radioService_popUp_RM.setOnCheckedChangeListener(serviceListener);

        //linearLayout = (LinearLayout) findViewById(R.id.setting_screen);
        //Toast.makeText(getApplicationContext(), "ahoj", Toast.LENGTH_SHORT).show();

        View.OnClickListener popOnClickList=new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String service_player="";
                if(player1_ser){
                    popupRematch.dismiss();
                    startActivity(new Intent(gActivity, GameCounterActivity.class));
                }else if(player2_ser){
                    service_player=player_2_name+" has a servise";
                    popupRematch.dismiss();
                    startActivity(new Intent(gActivity, GameCounterActivity.class));
                }else{
                    service_player="Please choose one of them";
                }
                Toast.makeText(getApplicationContext(),service_player,Toast.LENGTH_SHORT).show();
                //popupWindow.dismiss();
            }
        };

        start_popUp.setOnClickListener(popOnClickList);
        //popupWindow.setOutsideTouchable(false);
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.clear,R.style.pla));




    }

    View.OnClickListener score =new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String buttonName;
            boolean addToList=false;
            if(view.getId()==findViewById(R.id.player1_Layout_GC).getId()){
                buttonName="player 1";
                //TextView pl_1_score= (TextView) findViewById(R.id.player1_score_GC);

                pl_1_score.setText(""+(++score_player_1));
                totalScore=score_player_1+score_player_2;
                //Toast.makeText(gContext,"service player 1 = "+service_player_1,Toast.LENGTH_SHORT).show();
                service_player_1=player_1_hasService(totalScore);
                addToList=scoreList.add(new RoundScore(score_player_1,score_player_2,service_player_1));
                showService(service_player_1);
                winSet();

            }else if(view.getId()==findViewById(R.id.player2_Layout_GC).getId()){
                buttonName="player 2";

                pl_2_score.setText(""+(++score_player_2));
                totalScore=score_player_1+score_player_2;
               //Toast.makeText(gContext,"service player 1 = "+service_player_1,Toast.LENGTH_SHORT).show();
                service_player_1=player_1_hasService(totalScore);
                addToList=scoreList.add(new RoundScore(score_player_1,score_player_2,service_player_1));
                showService(service_player_1);
                winSet();

            }else{
                buttonName="No identification ";
            }
            //total score

            //buttonName=view.getResources().getResourceEntryName(view.getId());
            //Toast.makeText(gContext,"total scored points = "+(score_player_1+score_player_2),Toast.LENGTH_SHORT).show();
            //Toast.makeText(gContext, (R.id.player1_service_GC),Toast.LENGTH_SHORT).show();
           // Toast.makeText(gContext,"add to list= "+addToList+ " index "+scoreList.size(),Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener undoListener= new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int lastRound=scoreList.size()-1;
            int roundBefore=lastRound-1;
            if(lastRound<0)
            {   Toast.makeText(gContext,"Match not started",Toast.LENGTH_SHORT).show();
                return;
            }else if(lastRound==0){
                Toast.makeText(gContext,"The first round",Toast.LENGTH_SHORT).show();
             resetCounter();
            }else{
                scoreList.remove(lastRound);
                //scoreList.size();
                score_player_1=scoreList.get((roundBefore)).getScore1();
                score_player_2=scoreList.get((roundBefore)).getScore2();
                pl_1_score.setText(""+(score_player_1));
                pl_2_score.setText(""+(score_player_2));
                showService(scoreList.get((roundBefore)).p1_hasService());
                //Toast.makeText(gContext,""+lastRound+" round, score=> "+scoreList.get(roundBefore).getScore1()
                        //+": "+scoreList.get(roundBefore).getScore2(),Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_counter);
        // starting initiation
        RadioGroup.OnCheckedChangeListener serviceListener=new RadioGroup.OnCheckedChangeListener(){
            boolean player1_ser;
            boolean player2_ser;
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                View radioButton=radioGroup.findViewById(i);
                int index =radioGroup.indexOfChild(radioButton);

                if (index==0){
                    MainActivity.service_player_1_start=true;
                    MainActivity.service_player_2_start=false;
                    player1_ser=true;
                    player2_ser=false;
                }else if(index==1){
                    MainActivity.service_player_1_start=false;
                    MainActivity.service_player_2_start=true;
                    player1_ser=true;
                    player2_ser=false;
                }else{
                }
                Toast.makeText(gActivity,"player "+index,Toast.LENGTH_SHORT).show();
            }
        };
        initiate();
        scoreList=new ArrayList<RoundScore>();

        pl_1.setOnClickListener(score);
        pl_2.setOnClickListener(score);

    }
}
