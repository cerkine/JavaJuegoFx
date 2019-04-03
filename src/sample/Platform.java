package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform {
    private Image image;
    private double posX, posY, velX,velY, width, height;

    public Platform() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.velX = this.velY = 5.0f;
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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


    public void changeDir(String direction) {
        if (direction.equals("left")) {
            posX -= velX;
        } else if (direction.equals("right")) {
            posX += velX;
        }else if (direction.equals("down")) {
            posY += velY;
        }else if (direction.equals("up")) {
            posY -= velY;
        }

    }

    public void setInitialValue(double x, double y) {
        posX = x;
        posY = y;
    }
}