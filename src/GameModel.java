import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameModel {
    private final Player player;
    private final Background background;
    private final Platform platform;
    private boolean gamePaused, gameEnd;
    private BufferedImage paused, end;
    private final Sound sound;

    public GameModel() {
        platform = new Platform();
        player = new Player(platform);
        background = new Background();
        sound = new Sound();
        gamePaused = false;
        gameEnd = false;

        try {
            paused = ImageIO.read(new File("jungle/paused.png"));
            end = ImageIO.read(new File("jungle/game over.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Player getPlayer() {
        return player;
    }

    public Background getBackground() {
        return background;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Sound getSound() {
        return sound;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void drawGamePausedImage(Graphics g) {
        g.drawImage(paused, 280, 185, null);
    }

    public void drawGameEndImage(Graphics g) {
        g.drawImage(end, 270, 160, null);
    }
}
