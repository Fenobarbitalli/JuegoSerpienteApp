package com.example.serpiente;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class CuerpoSerpiente {
    //Movimiento a través del mapa
    private Bitmap Bm;
    //movimiento en plano de dos dimensiones
    private int x,y;
    //Control de acciones sobre el mapa en cualquier dirección
    private Rect CHcuerpo,CHarriba,CHabajo,CHizquierda,CHderecha;

    public CuerpoSerpiente(Bitmap bm, int x, int y) {
        Bm = bm;
        this.x = x;
        this.y = y;
    }

    //Funciones Get en Variables Rect (CH) retornan la posicion
    // de la cabeza en "x","y" y sobre el mapa establecido (tablero)
    //En español CH(dirección-CHoqueDireción

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

    public Rect getCHcuerpo() {
        return new Rect(this.x, this.y,this.x+Interfaz.TamañoMapa,this.y+Interfaz.TamañoMapa);
    }

    public void setCHcuerpo(Rect CHcuerpo) {
        this.CHcuerpo = CHcuerpo;
    }

    public Rect getCHarriba() {
        return new Rect(this.x, this.y-10*Constantes.SCREEN_HEIGHT/1920 ,this.x+Interfaz.TamañoMapa,this.y);
    }

    public void setCHarriba(Rect CHarriba) {
        this.CHarriba = CHarriba;
    }

    public Rect getCHabajo() {
        return new Rect(this.x, this.y+Interfaz.TamañoMapa,this.x+Interfaz.TamañoMapa,this.y+Interfaz.TamañoMapa+10*Constantes.SCREEN_HEIGHT/1920);
    }

    public void setCHabajo(Rect CHabajo) {
        this.CHabajo = CHabajo;
    }

    public Rect getCHizquierda() {
        return new Rect(this.x-10*Constantes.SCREEN_WIDTH/1000, this.y, this.x,this.y+Interfaz.TamañoMapa);
    }

    public void setCHizquierda(Rect CHizquierda) {
        this.CHizquierda = CHizquierda;
    }

    public Rect getCHderecha() {
        return new Rect(this.x+Interfaz.TamañoMapa, this.y,this.x+Interfaz.TamañoMapa+10*Constantes.SCREEN_WIDTH/1000,this.y+Interfaz.TamañoMapa);
    }

    public void setCHderecha(Rect CHderecha) {
        this.CHderecha = CHderecha;
    }
}
