package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pelota {
    private Image image;
    private String punto;
    private double posX, posY, velX, velY, width, height;
    private int[] puntos;
    private int dirX, dirY;
    private boolean eliminar;

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

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
        punto = "gris";
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

        }else {
            posX -= velX;
        }
        if(dirY == 1){
            posY += velY;
        }
        else {
            posY -= velY;
        }

        if(posX>=600) calcularPunto("verde");
        if(posX<=-image.getWidth()) calcularPunto("rojo");
        if(posY>=600)calcularPunto("azul");
        if(posY<=-image.getWidth())calcularPunto("amarillo");


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


    public void changeDir(String color) {
        double i = (Math.random()*10)%2;

        System.out.println(i);
        switch (color){
            case "azul":
                velY = -velY *i;
                break;
            case "verde":
                velX = -velX *i;
                break;
            case "amarillo":
                velY = -velY *i;
                break;
            case "rojo":
                velX = -velX *i;
                break;
        }
        double limitelento = 1.5;
        if (velY < limitelento && velY> -limitelento){
            if (velY < 0){
                velY = -limitelento;
            }else{
                velY = limitelento;
            }
        }
        if (velX < limitelento && velX> -limitelento){
            if (velX < 0){
                velX = -limitelento;
            }else{
                velX = limitelento;
            }
        }

        int limit = 5;
        if (velY < -limit || velY> limit){
            if (velY < 0){
                velY = -limit;
            }else{
                velY = limit;
            }
        }
        if (velX < -limit || velX> limit){
            if (velX < 0){
                velX = -limit;
            }else{
                velX = limit;
            }
        }

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

    public void calcularPunto(String recibe){
        switch (punto){
            case "azul":
                if (!recibe.equals("azul")) {
                    puntos[0]++;
                    punto = "gris";
                }
                break;
            case "verde":
                if (!recibe.equals("verde")) {
                    puntos[1]++;
                    punto = "gris";
                }
                break;
            case "rojo":
                if (!recibe.equals("rojo")) {
                    puntos[2]++;
                    punto = "gris";
                }
                break;
            case "amarillo":
                if (!recibe.equals("amarillo")) {
                    puntos[3]++;
                    punto = "gris";
                }
                break;
            case "gris":
                switch (recibe){
                    case "azul":
                        puntos[0]--;
                        punto = "gris";
                        break;
                    case "verde":
                        puntos[1]--;
                        punto = "gris";
                        break;
                    case "rojo":
                        puntos[2]--;
                        punto = "gris";
                        break;
                    case "amarillo":
                        puntos[3]--;
                        punto = "gris";
                        break;
                }
                break;
        }
        eliminar = true;
    }

    public void setInicio(double x, double y) {
        posX = x;
        posY = y;
    }
}