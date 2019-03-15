package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pilota {
    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Pilota() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.velX = 5.0f;
        this.velY = 1.0f;
        this.dirX = 1;
        this.dirY = 1;
    }

    public Pilota(double x, double y) {
        this.posX = x;
        this.posY = y;
        this.velX = 4.0f;
        this.velY = 4.0f;
        this.dirX = 1;
        this.dirY = 1;
    }



    public void move() {
//        if(dirX == 1) {
//            posX += velX;
//            if(posX>=400-width) dirX = (-1)*dirX;
//        }else {
//            posX -= velX;
//            if(posX<=0) dirX = (-1)*dirX;
//        }
//        if(dirY == 1){
//            posY += velY;
//            if(posY>=500-height) dirY = (-1)*dirY;
//        }
//        else {
//            posY -= velY;
//            if(posY<=0) dirY = (-1)*dirY;
//        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
        //System.out.println(posX + ":" + posY);
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

    public boolean isClicked(Point2D p) {
        if(getBoundary().contains(p)) return true;
        else return false;
    }

    public void changeDir(String direction) {
        if (direction.equals("left")){
            posX -= velX;
        }
        else if (direction.equals("right")){
            posX += velX;
        }
//        double t = Math.random();
//        if(0.33 > t) dirX = dirX*(-1);
//        if(0.33 < t && 0.66 > t) dirY = dirY*(-1);
//        if(0.66 < t) {
//            dirY = dirY*(-1);
//            dirX = dirX*(-1);
//        }

    }

    public void setDirection(String direction) {
        switch (direction) {
            case "RIGHT": dirX = 1;  break;
            case "LEFT": dirX= -1; break;
            case "DOWN": dirY = 1; break;
            case "UP": dirY = -1;break;
        }
    }
}