import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {
    private final BufferedImage[] idlePos, runPos;
    private BufferedImage image;
    private final BufferedImage[] jumpPos;
    private int playerY, score;
    private final int playerX, speed;
    private boolean idle, right, jumping, positionChanged;
    private int counter, sprite, bombX, bombY;
    private final Platform platform;


    private BufferedImage[] readImages(int size, String filePath) {
        BufferedImage[] tmp = new BufferedImage[size];
        for (int i = 0; i < size; ++i) {
            try {
                tmp[i] = ImageIO.read(new File(filePath + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }

    public Player(Platform platform) {
        this.platform = platform;
        idlePos = readImages(12, "jungle/character/idle/");
        runPos = readImages(8, "jungle/character/run/");
        jumpPos = readImages(1, "jungle/character/jump/");

        score = 0;
        counter = 0;
        sprite = 0;
        idle = true;
        right = false;
        jumping = false;
        positionChanged = false;

        playerX = 100;
        playerY = 328;
        speed = 5;

        platform.updatePosition(0);
    }


    public void updatePosition() {

        if (positionChanged) {
            positionChanged = false;
            counter = 0;
            sprite = 0;
        }

        if (idle) {
            updateIdle();
        } else if (jumping) {
            updateJumping();
        } else if (right) {
            updateRight();
        }
    }

    private void updateIdle() {
        if (counter > 4) {
            if (sprite == 11)
                sprite = 0;
            else
                sprite++;
            counter = 0;
        }
        counter++;
        image = idlePos[sprite];
    }

    private void updateJumping() {
        bombX = platform.getObstacleX();
        bombY = platform.getCameraY();

        if (counter < 30) {
            if (right) {
                platform.setCameraX(platform.getCameraX() - speed);
                platform.updatePosition(speed);
                score++;
            }
            playerY = playerY - speed;
        } else if (counter < 55) {
            if (right) {
                platform.setCameraX(platform.getCameraX() - speed);
                platform.updatePosition(speed);
                score++;
            }
            playerY = playerY + speed + 1;
        } else {
            jumping = false;
            if (!right)
                idle = true;
        }
        counter++;
        image = jumpPos[sprite];
    }

    private void updateRight() {
        if (counter > 5) {
            if (sprite == 7)
                sprite = 0;
            else
                sprite++;
            counter = 0;
        }
        counter++;
        score++;
        platform.setCameraX(platform.getCameraX() - speed);
        platform.updatePosition(speed);

        image = runPos[sprite];
    }


    //checks if the character collides with any bomb
    public boolean collides() {
        bombX = platform.getObstacleX();
        bombY = platform.getObstacleY();

        if (bombX >= 20 && bombX <= playerX - 20 && playerY >= bombY + 50)          //collides while running
            return true;
        return bombX >= 20 && bombX <= playerX - 30 && playerY >= bombY;            //collides while jumping
    }

    public void setPositionChanged(boolean positionChanged) {
        this.positionChanged = positionChanged;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean getRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public String getCharacterState() {
        return score + " " + counter + " " + sprite + " " + idle + " " + right + " " + jumping + " " + positionChanged;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void draw(Graphics g) {
        g.drawImage(image, playerX, playerY, null);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(score), 650, 40);
    }
}
