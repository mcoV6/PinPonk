package com.example.android.pinponk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    static String player_1_name,player_2_name;
    // work out player name 2 from Main Activity
    static boolean service_player_1_start=false;
    static boolean service_player_2_start=false;

    private RadioGroup radioService;
    private Button start_Button;
    private LinearLayout linearLayout;

    private Context mContext;
    private Activity mActivity;
    private Intent gameCounterIntent;

    private RelativeLayout mRelativeLayout;
    private Button mButton;
    private ViewGroup container;

    private PopupWindow popupWindow;
    private Button start_popUp;
   // private PopupWindow popupWindow;

    private void initiate(){
        //Get the context
        mContext=getApplicationContext();

        // Get the activity
        mActivity = MainActivity.this;

        // Get the widgets reference from XML layout

        // find button
        start_Button=(Button) findViewById(R.id.start_button);


        // find out who has service
        radioService = (RadioGroup) findViewById(R.id.radio_group_service);

        //gamecounter activity
        gameCounterIntent = new Intent(mActivity, GameCounterActivity.class);

    }

    RadioGroup.OnCheckedChangeListener serviceListener=new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            View radioButton=radioGroup.findViewById(i);
            int index =radioGroup.indexOfChild(radioButton);

            if (index==0){
                service_player_1_start=true;
                service_player_2_start=false;
            }else if(index==1){
                service_player_1_start=false;
                service_player_2_start=true;
            }else{
            }
            Toast.makeText(mActivity,"player "+index,Toast.LENGTH_SHORT).show();
        }
    }
;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiate();

        radioService.setOnCheckedChangeListener(serviceListener);

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
                    startActivity(gameCounterIntent);

                }else {

                    //create pop up window
                    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    container = (ViewGroup) layoutInflater.inflate(R.layout.pop_selection,null);

                    popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                    RadioButton rb_1= container.findViewById(R.id.radio_p11);
                    RadioButton rb_2= container.findViewById(R.id.radio_p22);
                    rb_1.setText(player_1_name);
                    rb_2.setText(player_2_name);

                    start_popUp = container.findViewById(R.id.start_button_pop);
                    final RadioGroup radioService_popUp = container.findViewById(R.id.radio_group_service_pop);
                    radioService_popUp.setOnCheckedChangeListener(serviceListener);

                    linearLayout = (LinearLayout) findViewById(R.id.setting_screen);

                    //Toast.makeText(getApplicationContext(), "ahoj", Toast.LENGTH_SHORT).show();


                    View.OnClickListener popOnClickList=new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {

                            String service_player;
                            if(service_player_1_start){
                                service_player=player_1_name+" has a servise";
                                popupWindow.dismiss();
                                startActivity(gameCounterIntent);
                            }else if(service_player_2_start){
                                service_player=player_2_name+" has a servise";
                                popupWindow.dismiss();
                                startActivity(gameCounterIntent);
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


                    popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);


                }

            }
        });




    }
}
