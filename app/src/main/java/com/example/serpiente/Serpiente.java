package com.example.serpiente;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;

import java.util.ArrayList;

public class Serpiente {

    private android.os.Handler handler;
    //Estos objetos booleanos determinan si el usuario mueve la serpiente en alguna dirección
    private boolean move_right, move_left, move_up, move_bottom;
    //En ingles para abreviar los nombres que representan las partes de la serpiente en sus posibles posiciones
    private Bitmap bm, bm_head_up, bm_head_down, bm_head_left, bm_head_right, bm_body_vertical
            , bm_body_horizontal, bm_body_top_right, bm_body_top_left, bm_body_bottom_right
            , bm_body_bottom_left, bm_tail_right, bm_tail_left, bm_tail_up, bm_tail_down;
    private int x, y, lenght;
    private ArrayList<CuerpoSerpiente> arrParteSerpiente = new ArrayList<>();

    //El construcor serpiente contiene todas las posibles formas de la serpiente
    //la posicion en x esta enumerada de izquierda a derecha en la imagen sneiki.png
    //para obtener las posibles formas de la serpiente
    //El constructor crea la serpiente con que se inicia el juego
    //en su posición inicial dentro del tablero
    //y tambien moviendose inicialmente hacia la derecha
    public Serpiente(Bitmap bm, int x, int y, int lenght) {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.lenght = lenght;
        bm_body_bottom_left = Bitmap.createBitmap(bm,0,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_body_bottom_right = Bitmap.createBitmap(bm,Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_body_horizontal = Bitmap.createBitmap(bm,2*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_body_top_left = Bitmap.createBitmap(bm,3*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_body_top_right = Bitmap.createBitmap(bm,4*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_body_vertical = Bitmap.createBitmap(bm,5*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_head_down = Bitmap.createBitmap(bm,6*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_head_left = Bitmap.createBitmap(bm,7*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_head_right = Bitmap.createBitmap(bm,8*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_head_up = Bitmap.createBitmap(bm,9*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_tail_up = Bitmap.createBitmap(bm,10*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_tail_right = Bitmap.createBitmap(bm,11*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_tail_left = Bitmap.createBitmap(bm,12*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        bm_tail_down = Bitmap.createBitmap(bm,13*Interfaz.TamañoMapa,0,Interfaz.TamañoMapa,Interfaz.TamañoMapa);
        arrParteSerpiente.add(new CuerpoSerpiente(bm_head_right,x,y));

        for(int i=1;i<lenght-1;i++){
            arrParteSerpiente.add(new CuerpoSerpiente(bm_body_horizontal,arrParteSerpiente.get(i-1).getX() -Interfaz.TamañoMapa,y));
        }
        arrParteSerpiente.add(new CuerpoSerpiente(bm_tail_right,arrParteSerpiente.get(lenght-2).getX() -Interfaz.TamañoMapa,y));
        setMove_right(true);
    }

    public void update() {
        for (int i = lenght - 1; i > 0; i--) {
            arrParteSerpiente.get(i).setX(arrParteSerpiente.get(i - 1).getX());
            arrParteSerpiente.get(i).setY(arrParteSerpiente.get(i - 1).getY());
        }
        if (move_right) {
            arrParteSerpiente.get(0).setX(arrParteSerpiente.get(0).getX() + Interfaz.TamañoMapa);
            arrParteSerpiente.get(0).setBm(bm_head_right);
        } else if (move_left) {
            arrParteSerpiente.get(0).setX(arrParteSerpiente.get(0).getX() - Interfaz.TamañoMapa);
            arrParteSerpiente.get(0).setBm(bm_head_left);
        } else if (move_up) {
            arrParteSerpiente.get(0).setY(arrParteSerpiente.get(0).getY() - Interfaz.TamañoMapa);
            arrParteSerpiente.get(0).setBm(bm_head_up);
        } else if (move_bottom) {
            arrParteSerpiente.get(0).setY(arrParteSerpiente.get(0).getY() + Interfaz.TamañoMapa);
            arrParteSerpiente.get(0).setBm(bm_head_down);
        }
        for (int i = 1; i < lenght - 1; i++) {
            if (arrParteSerpiente.get(i).getCHizquierda().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHabajo().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    || arrParteSerpiente.get(i).getCHizquierda().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHabajo().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())) {
                arrParteSerpiente.get(i).setBm(bm_body_bottom_left);
            } else if (arrParteSerpiente.get(i).getCHderecha().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHabajo().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    || arrParteSerpiente.get(i).getCHderecha().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHabajo().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())) {
                arrParteSerpiente.get(i).setBm(bm_body_bottom_right);
            } else if (arrParteSerpiente.get(i).getCHizquierda().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHarriba().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    || arrParteSerpiente.get(i).getCHizquierda().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHarriba().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())) {
                arrParteSerpiente.get(i).setBm(bm_body_top_left);
            } else if (arrParteSerpiente.get(i).getCHderecha().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHarriba().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    || arrParteSerpiente.get(i).getCHderecha().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHarriba().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())) {
                arrParteSerpiente.get(i).setBm(bm_body_top_right);
            } else if (arrParteSerpiente.get(i).getCHarriba().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHabajo().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    || arrParteSerpiente.get(i).getCHarriba().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHabajo().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())) {
                arrParteSerpiente.get(i).setBm(bm_body_vertical);
            } else if (arrParteSerpiente.get(i).getCHizquierda().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHderecha().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    || arrParteSerpiente.get(i).getCHizquierda().intersect(arrParteSerpiente.get(i - 1).getCHcuerpo())
                    && arrParteSerpiente.get(i).getCHderecha().intersect(arrParteSerpiente.get(i + 1).getCHcuerpo())) {
                arrParteSerpiente.get(i).setBm(bm_body_horizontal);
            }
        }
        if (arrParteSerpiente.get(lenght - 1).getCHderecha().intersect(arrParteSerpiente.get(lenght - 2).getCHcuerpo())) {
            arrParteSerpiente.get(lenght - 1).setBm(bm_tail_right);
        } else if (arrParteSerpiente.get(lenght - 1).getCHizquierda().intersect(arrParteSerpiente.get(lenght - 2).getCHcuerpo())) {
            arrParteSerpiente.get(lenght - 1).setBm(bm_tail_left);
        } else if (arrParteSerpiente.get(lenght - 1).getCHarriba().intersect(arrParteSerpiente.get(lenght - 2).getCHcuerpo())) {
            arrParteSerpiente.get(lenght - 1).setBm(bm_tail_up);
        }else if (arrParteSerpiente.get(lenght - 1).getCHabajo().intersect(arrParteSerpiente.get(lenght - 2).getCHcuerpo())) {
            arrParteSerpiente.get(lenght - 1).setBm(bm_tail_down);
        }
    }

    public void draw(Canvas canvas){
        for(int i=0;i<lenght;i++){
            canvas.drawBitmap(arrParteSerpiente.get(i).getBm(),arrParteSerpiente.get(i).getX(),arrParteSerpiente.get(i).getY(), null);
        }

    }

    //Funciones para establecer y obtener las formas en que se mueve la serpiente
    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm_head_up() {
        return bm_head_up;
    }

    public void setBm_head_up(Bitmap bm_head_up) {
        this.bm_head_up = bm_head_up;
    }

    public Bitmap getBm_head_down() {
        return bm_head_down;
    }

    public void setBm_head_down(Bitmap bm_head_down) {
        this.bm_head_down = bm_head_down;
    }

    public Bitmap getBm_head_left() {
        return bm_head_left;
    }

    public void setBm_head_left(Bitmap bm_head_left) {
        this.bm_head_left = bm_head_left;
    }

    public Bitmap getBm_head_right() {
        return bm_head_right;
    }

    public void setBm_head_right(Bitmap bm_head_right) {
        this.bm_head_right = bm_head_right;
    }

    public Bitmap getBm_body_vertical() {
        return bm_body_vertical;
    }

    public void setBm_body_vertical(Bitmap bm_body_vertical) {
        this.bm_body_vertical = bm_body_vertical;
    }

    public Bitmap getBm_body_horizontal() {
        return bm_body_horizontal;
    }

    public void setBm_body_horizontal(Bitmap bm_body_horizontal) {
        this.bm_body_horizontal = bm_body_horizontal;
    }

    public Bitmap getBm_body_top_right() {
        return bm_body_top_right;
    }

    public void setBm_body_top_right(Bitmap bm_body_top_right) {
        this.bm_body_top_right = bm_body_top_right;
    }

    public Bitmap getBm_body_top_left() {
        return bm_body_top_left;
    }

    public void setBm_body_top_left(Bitmap bm_body_top_left) {
        this.bm_body_top_left = bm_body_top_left;
    }

    public Bitmap getBm_body_bottom_right() {
        return bm_body_bottom_right;
    }

    public void setBm_body_bottom_right(Bitmap bm_body_bottom_right) {
        this.bm_body_bottom_right = bm_body_bottom_right;
    }

    public Bitmap getBm_body_bottom_left() {
        return bm_body_bottom_left;
    }

    public void setBm_body_bottom_left(Bitmap bm_body_bottom_left) {
        this.bm_body_bottom_left = bm_body_bottom_left;
    }

    public Bitmap getBm_tail_right() {
        return bm_tail_right;
    }

    public void setBm_tail_right(Bitmap bm_tail_right) {
        this.bm_tail_right = bm_tail_right;
    }

    public Bitmap getBm_tail_left() {
        return bm_tail_left;
    }

    public void setBm_tail_left(Bitmap bm_tail_left) {
        this.bm_tail_left = bm_tail_left;
    }

    public Bitmap getBm_tail_up() {
        return bm_tail_up;
    }

    public void setBm_tail_up(Bitmap bm_tail_up) {
        this.bm_tail_up = bm_tail_up;
    }

    public Bitmap getBm_tail_down() {
        return bm_tail_down;
    }

    public void setBm_tail_down(Bitmap bm_tail_down) {
        this.bm_tail_down = bm_tail_down;
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

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public ArrayList<CuerpoSerpiente> getArrParteSerpiente() {
        return arrParteSerpiente;
    }

    public void setArrParteSerpiente(ArrayList<CuerpoSerpiente> arrParteSerpiente) {
        this.arrParteSerpiente = arrParteSerpiente;
    }
    // Estas funciones determinan directamente si el usuario esta efectuando movimientos a la serpiente

    public boolean isMove_right() {
        return move_right;
    }

    public void setMove_right(boolean move_right) {
        SinAccion();
        this.move_right = move_right;
    }

    public boolean isMove_left() {
        return move_left;
    }

    public void setMove_left(boolean move_left) {
        SinAccion();
        this.move_left = move_left;
    }

    public boolean isMove_up() {
        return move_up;
    }

    public void setMove_up(boolean move_up) {
        SinAccion();
        this.move_up = move_up;
    }

    public boolean isMove_bottom() {
        return move_bottom;
    }

    public void setMove_bottom(boolean move_bottom) {
        SinAccion();
        this.move_bottom = move_bottom;
    }
    public void SinAccion(){
        this.move_bottom =false;
        this.move_left =  false;
        this.move_right = false;
        this.move_up   =  false;
    }

    public void Comer() {
        CuerpoSerpiente cuerpoSerpiente = this.arrParteSerpiente.get(lenght-1);
        this.lenght ++;
        if(cuerpoSerpiente.getBm() == bm_tail_right){
            this.arrParteSerpiente.add(new CuerpoSerpiente(bm_tail_right, cuerpoSerpiente.getX() -Interfaz.TamañoMapa,cuerpoSerpiente.getY()));
        }else if(cuerpoSerpiente.getBm() == bm_tail_left){
            this.arrParteSerpiente.add(new CuerpoSerpiente(bm_tail_left, cuerpoSerpiente.getX() -Interfaz.TamañoMapa,cuerpoSerpiente.getY()));
        }else if(cuerpoSerpiente.getBm() == bm_tail_up){
            this.arrParteSerpiente.add(new CuerpoSerpiente(bm_tail_up, cuerpoSerpiente.getX() -Interfaz.TamañoMapa,cuerpoSerpiente.getY()));
        }else if(cuerpoSerpiente.getBm() == bm_tail_down){
            this.arrParteSerpiente.add(new CuerpoSerpiente(bm_tail_down, cuerpoSerpiente.getX() -Interfaz.TamañoMapa,cuerpoSerpiente.getY()));
        }
        if((this.arrParteSerpiente.get(0)).getBm() == bm_head_left){
            this.arrParteSerpiente.add(new CuerpoSerpiente(bm_tail_right, cuerpoSerpiente.getX() -Interfaz.TamañoMapa,cuerpoSerpiente.getY()));
        }
    }
}
