package com.example.android.pinponk;

/**
 * Created by Martin on 2. 12. 2017.
 */

class RoundScore {
    private int score1;
    private int score2;
    private boolean player_1_service=true;

    RoundScore(int score1, int score2){
        this.score1=score1;
        this.score2=score2;
    }

    RoundScore(int score1, int score2,boolean player_1_servic){
        this.score1=score1;
        this.score2=score2;
        this.player_1_service=player_1_servic;
    }

    public int getScore1(){
        return score1;
    }
    public int getScore2(){
        return score2;
    }
    public boolean hasScore_pl1() {return player_1_service;}

    public void setScore1(int score1){
        this.score1=score1;
    }
    public void setScore2(int score2){
        this.score2=score2;
    }
    public void setP1_HasService(boolean serviceForPlayer_1){ this.player_1_service=serviceForPlayer_1;}

}
