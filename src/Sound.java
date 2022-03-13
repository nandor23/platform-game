import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip jump, music;

    public Sound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("jungle/sound/music.wav"));
            music = AudioSystem.getClip();
            music.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        if (music != null) {
            music.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopMusic() {
        music.stop();
    }

    public void startMusic() {
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playJumpSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("jungle/sound/jump.wav"));
            jump = AudioSystem.getClip();
            jump.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        jump.start();
    }
}
