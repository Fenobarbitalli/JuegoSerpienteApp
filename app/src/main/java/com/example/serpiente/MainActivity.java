package com.example.serpiente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static ImageView img_swipe;
    public static Dialog dialogScore;
    private Interfaz gv;
    public static TextView txt_score, txt_best_score, txt_dialog_score, txt_dialog_best_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constantes.SCREEN_WIDTH = dm.widthPixels;
        Constantes.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);
        img_swipe = findViewById(R.id.img_swipe);
        gv = findViewById(R.id.gv);
        txt_score = findViewById(R.id.Puntaje);
        txt_best_score = findViewById(R.id.MayorPuntaje);
        dialogScore();
    }

    private void dialogScore() {
        Vibrator vibrador = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrador.vibrate(500);
        int bestScore = 0;
        SharedPreferences sp = this.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if (sp != null) {
            bestScore = sp.getInt("bestscore", 0);
        }
        MainActivity.txt_best_score.setText(bestScore + "");
        dialogScore = new Dialog(this);
        dialogScore.setContentView(R.layout.dialog_start);
        txt_dialog_score = dialogScore.findViewById(R.id.txt_dialog_score);
        txt_dialog_best_score = dialogScore.findViewById(R.id.txt_dialog_best_score);
        txt_dialog_best_score.setText(bestScore + "");
        dialogScore.setCanceledOnTouchOutside(false);
        RelativeLayout rl_start = dialogScore.findViewById(R.id.rl_start);
        rl_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_swipe.setVisibility(View.VISIBLE);
                gv.reset();
                dialogScore.dismiss();
                Vibrator vibrador = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrador.vibrate(500);
            }
        });
        dialogScore.show();
    }

}