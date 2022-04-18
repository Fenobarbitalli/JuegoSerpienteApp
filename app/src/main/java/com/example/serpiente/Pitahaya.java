package com.example.serpiente;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pitahaya{

    private Bitmap Bm;
    private int x,y;
    private Rect r;

    public Pitahaya(Bitmap bm, int x, int y) {
        Bm = bm;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBm() {
        return Bm;
    }

    public void setBm(Bitmap bm) {
        Bm = bm;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getR() {
        return new Rect(this.x,this.y,this.x+Interfaz.TamañoMapa,this.y+Interfaz.TamañoMapa);
    }

    public void setR(Rect r) {
        this.r = r;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(Bm,x,y, null);
    }
    public void reset(int nx, int ny){
        this.x = nx;
        this.y = ny;

    }
}
