import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Platform {
    private BufferedImage ground;
    private final BufferedImage[] bomb;
    private int counter, sprite, cameraX, cameraY, startIndex, endIndex;
    private final int obstacleY;
    private int[] obstacles;

    public Platform() {
        counter = 0;
        sprite = 0;
        cameraX = 0;
        cameraY = 320;
        obstacleY = 270;
        bomb = new BufferedImage[10];

        try {
            ground = ImageIO.read(new File("jungle/tileset/ground.png"));
            for (int i = 0; i < 10; ++i) {
                bomb[i] = ImageIO.read(new File("jungle/tileset/bomb/" + i + ".png"));
            }


            BufferedReader reader = new BufferedReader(new FileReader("jungle/obstacles.txt"));
            obstacles = reader.lines()
                    .mapToInt(Integer::parseInt).toArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //the nearest bomb's X coordinate
    public int getObstacleX() {
        return obstacles[startIndex];
    }

    //the nearest bomb's Y coordinate
    public int getObstacleY() {
        return obstacleY;
    }

    private void updateCurrent() {
        while (startIndex < obstacles.length && obstacles[startIndex] < -120) {
            startIndex++;
        }
        endIndex = startIndex;
        while (endIndex < obstacles.length && obstacles[endIndex] < 1000) {
            endIndex++;
        }

        if (startIndex == obstacles.length) {
            startIndex--;
            endIndex--;
        }
    }

    //pushes the bombs left
    public void updatePosition(int speed) {
        updateCurrent();

        for (int i = startIndex; i < obstacles.length; ++i) {
            obstacles[i] = obstacles[i] - speed;
        }

    }

    //updates the bomb's animation (changes picture)
    public void updateBombAnimation() {
        if (counter > 4) {
            if (sprite == 9)
                sprite = 0;
            else
                sprite++;
            counter = 0;
        }
        counter++;
    }

    //draws the obstacles to the current position
    public void draw(Graphics g) {
        int j = -1 * cameraX / 320;
        for (int i = j; i < j + 4; ++i) {
            g.drawImage(ground, i * 319 + cameraX, 385, null);
        }

        for (int i = startIndex; i < endIndex; ++i) {
            g.drawImage(bomb[sprite], obstacles[i], obstacleY, null);
        }

    }

    public int getCameraX() {
        return cameraX;
    }

    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraY(int cameraY) {
        this.cameraY = cameraY;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int[] getPlatformState() {
        int[] elem = new int[obstacles.length + 4];
        elem[0] = cameraX;
        elem[1] = cameraY;
        elem[2] = startIndex;
        elem[3] = endIndex;
        System.arraycopy(obstacles, 0, elem, 4, obstacles.length);
        return elem;
    }

    public void updatePlatformState(int[] obstacles) {
        this.obstacles = obstacles;
    }

}
