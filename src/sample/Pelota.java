package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pelota {
    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Image getImage() {
        return image;
    }

    public Pelota() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.velX = 2.0f;
        this.velY = 2.0f;
        this.dirX = 1;
        this.dirY = 1;
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
            //if(posX>=400-width) dirX = (-1)*dirX;
        }else {
            posX -= velX;
            //if(posX<=0) dirX = (-1)*dirX;
        }
        if(dirY == 1){
            posY += velY;
            //if(posY>=500-height) dirY = (-1)*dirY;
        }
        else {
            posY -= velY;
            //if(posY<=0) dirY = (-1)*dirY;
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

//    public void setDirection(String direction) {
//        switch (direction) {
//            case "RIGHT": dirX = 1;  break;
//            case "LEFT": dirX= -1; break;
//            case "DOWN": dirY = 1; break;
//            case "UP": dirY = -1;break;
//        }
//    }
}