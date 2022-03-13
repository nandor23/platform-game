import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameView extends JPanel implements KeyListener {
    private final GameModel model;


    public GameView(GameModel model) {
        this.model = model;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        model.getBackground().draw(g);
        model.getPlatform().draw(g);
        model.getPlayer().draw(g);
        if (model.isGamePaused()) {
            model.drawGamePausedImage(g);
        } else if (model.isGameEnd()) {
            model.drawGameEndImage(g);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 32) {

            //the animation must end when jumping
            if (model.getPlayer().isJumping()) {
                return;
            }
            model.getSound().playJumpSound();
            model.getPlayer().setIdle(false);
            model.getPlayer().setJumping(true);
            model.getPlayer().setPositionChanged(true);

        } else if (e.getKeyCode() == KeyEvent.VK_D) {

            if (!model.getPlayer().getRight() && !model.getPlayer().isJumping()) {
                model.getPlayer().setPositionChanged(true);
            }
            model.getPlayer().setIdle(false);
            model.getPlayer().setRight(true);

        } else if (e.getKeyCode() == 27) {
            if (model.isGamePaused()) {
                model.setGamePaused(false);
                model.getSound().startMusic();
            } else {
                model.getSound().stopMusic();
                model.setGamePaused(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_D) {
            model.getPlayer().setRight(false);
        }

        if (!model.getPlayer().isJumping()) {
            model.getPlayer().setIdle(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}