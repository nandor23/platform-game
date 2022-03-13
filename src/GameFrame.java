import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {

        this.setBounds(200, 100, 782, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameControl control = new GameControl(model, view);

        GameMenuBar menu = new GameMenuBar(model);
        this.add(view, BorderLayout.CENTER);
        this.add(menu, BorderLayout.NORTH);

        addKeyListener(view);                   //it will get the events from the view component

        Thread th = new Thread(control);
        th.start();

        this.setVisible(true);
    }
}
