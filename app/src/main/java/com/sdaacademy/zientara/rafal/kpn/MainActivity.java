package com.sdaacademy.zientara.rafal.kpn;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Drawable cutDrawable;
    private Drawable paperDrawable;
    private Drawable stoneDrawable;

    private ImageView myAction;
    private ImageView computerAction;
    private Action myActionEnum;
    private Action computerActionEnum;
    private Random random;
    private int numberOfComputerWin = 0;
    private int numberOfUserWin = 0;

    public static final int tableWin[][] = {{0, -1, 1}, {1, 0, -1}, {-1, 1, 0}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();


        cutDrawable = ContextCompat.getDrawable(this, R.drawable.ic_content_cut_black_24dp);
        paperDrawable = ContextCompat.getDrawable(this, R.drawable.ic_content_copy_black_24dp);
        stoneDrawable = ContextCompat.getDrawable(this, R.drawable.ic_brightness_1_black_24dp);


        myAction = (ImageView) findViewById(R.id.myActionImage);
        computerAction = (ImageView) findViewById(R.id.computerActionImage);
    }

    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.cutButton:
                myAction.setImageDrawable(cutDrawable);
                myActionEnum = Action.CUT;
                break;
            case R.id.paperButton:
                myAction.setImageDrawable(paperDrawable);
                myActionEnum = Action.PAPER;
                break;
            case R.id.stoneButton:
                myAction.setImageDrawable(stoneDrawable);
                myActionEnum = Action.STONE;
                break;
        }

        computerActionEnum = getRandomAction();
        switch (computerActionEnum) {
            case STONE:
                computerAction.setImageDrawable(stoneDrawable);
                break;
            case PAPER:
                computerAction.setImageDrawable(paperDrawable);
                break;
            case CUT:
                computerAction.setImageDrawable(cutDrawable);
        }
        TextView textView= (TextView) findViewById(R.id.textView3);
        game(textView);


    }

    public void game(TextView textView) {
            String winnerText = whoWinRound(myActionEnum, computerActionEnum);
            Toast toast = Toast.makeText(this, winnerText, Toast.LENGTH_LONG);
            toast.show();
            if (numberOfComputerWin == 3 || numberOfUserWin == 3) {

                textView.setText(whoWinGame());
                showDialog(whoWinGame());
            }



    }

    private void showDialog(String mesage){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .create();
        alertDialog.setTitle("kto wygrał");
        String negativeMes="nie gram";
        String positiveMes="gram";
        alertDialog.setMessage(mesage);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveMes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numberOfComputerWin = 0;
                numberOfUserWin = 0;
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeMes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alertDialog.show();
    }

    public String whoWinGame() {
        String text = "kto wygral?";
        if (numberOfUserWin == 3) {
            text = "user win";
        }
        if (numberOfComputerWin == 3) {
            text = "comp win";
        }


        return text;
    }


    public String whoWinRound(Action myActionEnum, Action computerActionEnum) {

        TextView textView1= (TextView) findViewById(R.id.userPoint);
        TextView textView2= (TextView) findViewById(R.id.compPoint);
        String winnerText = "nie rozpoczęta gra";
        switch (tableWin[myActionEnum.value][computerActionEnum.value]) {
            case 0:
                winnerText = "remis";
                break;
            case 1:
                winnerText = "wygral uzytkownik";
                numberOfUserWin++;
                break;
            case -1:
                winnerText = "wygral komp";
                numberOfComputerWin++;
                break;
        }
    

        return winnerText;
    }

    private Action getRandomAction() {
        int randomAction = random.nextInt(3);
        if (randomAction == 0)
            return Action.STONE;
        if (randomAction == 1)
            return Action.PAPER;
        return Action.CUT;
    }

    enum Action {
        STONE(0),
        PAPER(1),
        CUT(2);

        private int value;

        Action(int value) {
            this.value = value;
        }

    }

}
