package com.example.serpiente;

import android.graphics.Bitmap;

public class Tablero {
    private Bitmap BM;
    private int x,y,width,height;

    public Tablero(Bitmap BM, int x, int y, int width, int height) {
        this.BM = BM;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Bitmap getBM() {
        return BM;
    }

    public void setBM(Bitmap BM) {
        this.BM = BM;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
