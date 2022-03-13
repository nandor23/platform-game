import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {
    private final BufferedImage[] background;

    public Background() {
        background = new BufferedImage[5];
        try {
            for (int i = 0; i < 5; ++i) {
                background[i] = ImageIO.read(new File("jungle/background/" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < 5; ++i) {
            g.drawImage(background[i], 0, 0, null);
        }
    }
}
