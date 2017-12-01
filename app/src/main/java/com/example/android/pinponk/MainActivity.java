package com.example.android.pinponk;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    static String player_1_name,player_2_name;
    // work out player name 2 from Main Activity
    static boolean service_player_1_start=false;
    static boolean service_player_2_start=false;

    private RadioGroup radioService;
    private Button start_Button;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout linearLayout;
;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find out who has service
        radioService = (RadioGroup) findViewById(R.id.radio_group_service);
        radioService.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                View radioButton=radioGroup.findViewById(i);
                int index =radioGroup.indexOfChild(radioButton);
                Toast.makeText(getApplicationContext(),"player "+index,Toast.LENGTH_SHORT).show();
                if (index==0){
                    service_player_1_start=true;
                    service_player_2_start=false;
                }else if(index==1){
                    service_player_1_start=false;
                    service_player_2_start=true;
                }else{
                }
            }
        });

            // find button
        start_Button=(Button) findViewById(R.id.start_button);

        start_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gain player name 1
                EditText player1EText = (EditText) findViewById(R.id.player1_input);
                player_1_name = player1EText.getText().toString();
                if(player_1_name.length()==0){player_1_name="player 1";}

                // gain player name 2 from Main Activity
                EditText player2EText = (EditText) findViewById(R.id.player2_input);
                player_2_name = player2EText.getText().toString();
                if(player_2_name.length()==0){player_2_name="player 2";}


                // Create a new intent to open the {@link GameCounter}

                //check if service was chossen
                if(service_player_1_start ||service_player_2_start) {

                    // Start the new activity
                    Intent gameCounterIntent = new Intent(MainActivity.this, GameCounterActivity.class);
                    startActivity(gameCounterIntent);

                }else{

                   // RadioButton p1_pop=(RadioButton) findViewById(R.id.radio_p11);
                    //RadioButton p2_pop=(RadioButton) findViewById(R.id.radio_p22);

                    //load create pop up window
                    layoutInflater=(LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container=(ViewGroup) layoutInflater.inflate(R.layout.pop_selection,null);

                    popupWindow=new PopupWindow(container,400,400,true);
                    linearLayout=(LinearLayout)findViewById(R.id.setting_screen);

                    popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,1000,600);

                    container.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            popupWindow.dismiss();
                            return true;
                        }
                });

                    //find start button in popup window
                    Button start_popUp=(Button) findViewById(R.id.start_button_pop);
                    start_popUp.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // find out who has service
                            //Toast.makeText(getApplicationContext(),"ahoj",Toast.LENGTH_SHORT).show();
                            //popupWindow.dismiss();
//                          // RadioGroup radioServicePopup = (RadioGroup) findViewById(R.id.radio_group_service_pop);
//                            radioServicePopup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
//                                @Override
//                                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
//                                    View radioServicePopup=radioGroup.findViewById(i);
//                                    int index =radioGroup.indexOfChild(radioServicePopup);
//
//                                    if (index==0){
//                                        service_player_1_start=true;
//                                        service_player_2_start=false;
//                                    }else if(index==1){
//                                        service_player_1_start=false;
//                                        service_player_2_start=true;
//                                    }else{
//                                    }
//                                }
//                            });

                        }
                    });


                }
                //Intent gameCounterIntent = new Intent(MainActivity.this, GameCounterActivity.class);
                //startActivity(gameCounterIntent);
            }
        });




    }
}
