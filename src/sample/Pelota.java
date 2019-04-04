package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pelota {
    private Image image;
    private String punto;
    private double posX, posY, velX, velY, width, height;
    private int[] puntos;
    private int dirX, dirY;
    private boolean eliminar;

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Image getImage() {
        return image;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }

    public int[] getPuntos() {
        return puntos;
    }

    public Pelota() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.velX = 2.0f;
        this.velY = 2.0f;
        this.dirX = 1;
        this.dirY = 1;
        puntos = new int[4];
        for (int i = 0; i < puntos.length; i++) {
            puntos[i] = 0;
        }
        punto = "";
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void move() {
        if(dirX == 1) {
            posX += velX;
            if(posX>=600) calcularPunto();

        }else {
            posX -= velX;
            if(posX<=0) calcularPunto();
        }
        if(dirY == 1){
            posY += velY;
            if(posY>=600)calcularPunto();
        }
        else {
            posY -= velY;
            if(posY<=0)calcularPunto();
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void clear(GraphicsContext gc) {
        gc.clearRect(posX,posY, width, height);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(posX,posY,width,height);
    }


    public void changeDir() {
        double t = Math.random();
        if(0.33 > t) dirX = dirX*(-1);
        if(0.33 < t && 0.66 > t) dirY = dirY*(-1);
        if(0.66 < t) {
            dirY = dirY*(-1);
            dirX = dirX*(-1);
        }

    }

    public void calcularPunto(){
        switch (punto){
            case "azul":
                puntos[0]++;
                punto = "";
                break;
            case "verde":
                puntos[1]++;
                punto = "";
                break;
            case "rojo":
                puntos[2]++;
                punto = "";
                break;
            case "amarillo":
                puntos[3]++;
                punto = "";
                break;
        }
        eliminar = true;
    }

    public void setInicio(double x, double y) {
        posX = x;
        posY = y;
    }
}