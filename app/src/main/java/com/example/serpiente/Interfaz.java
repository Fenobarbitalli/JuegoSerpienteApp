package com.example.serpiente;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class Interfaz extends View {
   //Los objetos del juego (casillas,serpiente ..etc)
    private Bitmap a,b, bmSerpiente, bmPitaya1,bmPitaya2;
    public static int TamañoMapa= 75*Constantes.SCREEN_WIDTH/1080;
    private ArrayList<Tablero> tablero = new ArrayList<>();
    private Serpiente serpiente;
    private boolean move=false;
    private float mx, my;
    private android.os.Handler handler;
    public static boolean EstadoJuego = false;
    public static int Puntaje = 0, MayorPuntaje = 0;
    private Context context;
    private int SonidoComer, SonidoMorir;
    private float Volumen;
    private boolean SonidoCargado;
    private SoundPool soundPool;
    private Runnable r;
    private Pitahaya pitahaya1;
    private Pitahaya pitahaya2;
    private MainActivity vibracion;

    public Interfaz(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SharedPreferences sp = context.getSharedPreferences("gamesetting",Context.MODE_PRIVATE);

        if(sp != null){
            MayorPuntaje = sp.getInt("mayorpuntaje",0);
        }
        this.context = context;
        a = BitmapFactory.decodeResource(this.getResources(), R.drawable.a);
        a = Bitmap.createScaledBitmap(a, TamañoMapa, TamañoMapa, true);
        b = BitmapFactory.decodeResource(this.getResources(), R.drawable.b);
        b = Bitmap.createScaledBitmap(b, TamañoMapa, TamañoMapa, true);

        bmSerpiente = BitmapFactory.decodeResource(this.getResources(), R.drawable.sneiki);
        bmSerpiente = Bitmap.createScaledBitmap(bmSerpiente, 14 * TamañoMapa, TamañoMapa, true);

        bmPitaya1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.pitayaa);
        bmPitaya1 = Bitmap.createScaledBitmap(bmPitaya1,  TamañoMapa, TamañoMapa, true);
        bmPitaya2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.pitaya);
        bmPitaya2 = Bitmap.createScaledBitmap(bmPitaya2,  TamañoMapa, TamañoMapa, true);

        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 12; j++) {
                if ((i + j) % 2 == 0) {
                    tablero.add(new Tablero(a, j * TamañoMapa + Constantes.SCREEN_WIDTH / 2 - (6 * TamañoMapa), i * TamañoMapa + 100 * Constantes.SCREEN_HEIGHT / 1920,
                            TamañoMapa, TamañoMapa));
                } else {
                    tablero.add(new Tablero(b, j * TamañoMapa + Constantes.SCREEN_WIDTH / 2 - (6 * TamañoMapa), i * TamañoMapa + 100 * Constantes.SCREEN_HEIGHT / 1920,
                            TamañoMapa, TamañoMapa));
                }
            }
        }
        serpiente = new Serpiente(bmSerpiente, tablero.get(126).getX(), tablero.get(126).getY(), 4);
        pitahaya1 = new Pitahaya(bmPitaya1,tablero.get(PitahayaAleatoria()[0]).getX(), tablero.get(PitahayaAleatoria()[1]).getY());
        pitahaya2 = new Pitahaya(bmPitaya2,tablero.get(PitahayaAleatoria()[0]).getX(), tablero.get(PitahayaAleatoria()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        if(Build.VERSION.SDK_INT>=21){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        }else{
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                SonidoCargado = true;
            }
        });
        SonidoComer = this.soundPool.load(context, R.raw.eat,1);
        SonidoMorir = this.soundPool.load(context,R.raw.die, 1);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int Accion = event.getActionMasked();
        switch (Accion) {
            case MotionEvent.ACTION_MOVE: {
                if (move == false) {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                } else if (mx - event.getX() > 100 * Constantes.SCREEN_WIDTH / 1080 && !serpiente.isMove_right()) {
                    mx = event.getX();
                    my = event.getY();
                    serpiente.setMove_left(true);
                    EstadoJuego = true;
                    MainActivity.img_swipe.setVisibility(INVISIBLE);
                } else if (event.getX() - mx > 100 * Constantes.SCREEN_WIDTH / 1080 && !serpiente.isMove_left()) {
                    mx = event.getX();
                    my = event.getY();
                    serpiente.setMove_right(true);
                    EstadoJuego = true;
                    MainActivity.img_swipe.setVisibility(INVISIBLE);
                } else if (my - event.getY() > 100 * Constantes.SCREEN_WIDTH / 1080 && !serpiente.isMove_bottom()) {
                    mx = event.getX();
                    my = event.getY();
                    serpiente.setMove_up(true);
                    EstadoJuego = true;
                    MainActivity.img_swipe.setVisibility(INVISIBLE);
                  //  vibracion.VibrarInicio();
                } else if (event.getY() - my > 100 * Constantes.SCREEN_WIDTH / 1080 && !serpiente.isMove_up()) {
                    mx = event.getX();
                    my = event.getY();
                    serpiente.setMove_bottom(true);
                    EstadoJuego = true;
                    MainActivity.img_swipe.setVisibility(INVISIBLE);
                //    vibracion.VibrarInicio();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
            return true;
    }



    @Override
    public void draw(Canvas canvas) {
        int time = 300;
        super.draw(canvas);
        canvas.drawColor(0xFF1A6100);
        for(int i=0;i<tablero.size();i++){
            canvas.drawBitmap(tablero.get(i).getBM(), tablero.get(i).getX(),tablero.get(i).getY(),null);
        }
        serpiente.draw(canvas);
        pitahaya1.draw(canvas);
        //Si come una pitahaya
        if(serpiente.getArrParteSerpiente().get(0).getCHcuerpo().intersect(pitahaya1.getR())){
            if(SonidoCargado){
                int streamID = this.soundPool.play(this.SonidoComer, (float) 0.5,(float) 0.5,1,0,1f);
            }
            PitahayaAleatoria();
            pitahaya1.reset(tablero.get(PitahayaAleatoria()[0]).getX(), tablero.get(PitahayaAleatoria()[1]).getY());
            serpiente.Comer();
            Puntaje++;
            MainActivity.txt_score.setText(Puntaje+"");
        }
        if( Puntaje <=5){
            time= 300;
        }else if(Puntaje <10 && Puntaje> 5)
            time= 200;
        else if(Puntaje>=10 && Puntaje <15)
            time = 150;
        else if (Puntaje>=15 && Puntaje <30)
            time = 100;
        else if(Puntaje>=30)
            time=90;

        if(Puntaje > MayorPuntaje){
            MayorPuntaje = Puntaje;
            SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("bestscore", MayorPuntaje);
            editor.apply();
            MainActivity.txt_best_score.setText(MayorPuntaje+"");
        }
        handler.postDelayed(r,time);
        if(EstadoJuego) {
            serpiente.update();
            if (serpiente.getArrParteSerpiente().get(0).getX() < this.tablero.get(0).getX()
                    || serpiente.getArrParteSerpiente().get(0).getY() < this.tablero.get(0).getY()
                    || serpiente.getArrParteSerpiente().get(0).getY() + TamañoMapa > this.tablero.get(this.tablero.size() - 1).getY() + TamañoMapa
                    || serpiente.getArrParteSerpiente().get(0).getX() + TamañoMapa > this.tablero.get(this.tablero.size() - 1).getX() + TamañoMapa) {
                gameOver();
            }
            for (int i = 1; i < serpiente.getArrParteSerpiente().size(); i++) {
                if (serpiente.getArrParteSerpiente().get(0).getCHcuerpo().intersect(serpiente.getArrParteSerpiente().get(i).getCHcuerpo())) {
                    gameOver();
                }
            }
        }

    }


    public int[] PitahayaAleatoria() {
        int[] xy = new int[2];
        Random PosicionAleatoria = new Random();
        xy[0] = PosicionAleatoria.nextInt(tablero.size() - 1);
        xy[1] = PosicionAleatoria.nextInt(tablero.size() - 1);
        Rect rect = new Rect(tablero.get(xy[0]).getX(), tablero.get(xy[1]).getY(), tablero.get(xy[0]).getX() + TamañoMapa, tablero.get(xy[1]).getY() + TamañoMapa);
        Boolean check = true;
        while (check) {
            check = false;

            for (int i = 0; i < serpiente.getArrParteSerpiente().size(); i++) {
                if (rect.intersect(serpiente.getArrParteSerpiente().get(i).getCHcuerpo())) {
                    check = true;
                    xy[0] = PosicionAleatoria.nextInt(tablero.size() - 1);
                    xy[1] = PosicionAleatoria.nextInt(tablero.size() - 1);
                    rect = new Rect(tablero.get(xy[0]).getX(), tablero.get(xy[1]).getY(), tablero.get(xy[0]).getX() + TamañoMapa, tablero.get(xy[1]).getY() + TamañoMapa);
                }
            }
        }
    return xy;
    }
    private void gameOver() {
        serpiente.setMove_right(false);
        serpiente.setMove_bottom(false);
        serpiente.setMove_up(false);
        serpiente.setMove_left(false);
        EstadoJuego = false;
        MainActivity.dialogScore.show();
        MainActivity.txt_dialog_best_score.setText(MayorPuntaje+"");
        MainActivity.txt_dialog_score.setText(Puntaje+"");
        if(SonidoCargado){
            int streamId = this.soundPool.play(this.SonidoMorir, (float) 0.5, (float) 0.5, 1, 0, 1f);
        }
    }

    public void reset(){

        for(int i= 0;i< 25; i++){
            for(int j = 0; j<13 ;j++){

                if((j+i)%2 == 0) {
                    tablero.add(new Tablero(a, j * a.getWidth() + Constantes.SCREEN_WIDTH / 2 - 6 * a.getWidth(), i * a.getHeight() + 50 * Constantes.SCREEN_HEIGHT / 1920, a.getWidth(), a.getHeight()));
                }else{
                tablero.add(new Tablero(b,j*b.getWidth()+ Constantes.SCREEN_WIDTH/2 - 6*b.getWidth(),i*b.getHeight()+50*Constantes.SCREEN_HEIGHT/1920, b.getWidth(),b.getHeight() ));
            }}
        }
        serpiente = new Serpiente(bmSerpiente,tablero.get(126).getX(),tablero.get(126).getY(), 4);
        pitahaya1 = new Pitahaya(bmPitaya1, tablero.get(PitahayaAleatoria()[0]).getX(), tablero.get(PitahayaAleatoria()[1]).getY());
        MainActivity.txt_score.setText("0");
        Puntaje = 0;
    }
}
