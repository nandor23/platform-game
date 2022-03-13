import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GameMenuBar extends JMenuBar implements ActionListener {
    private final JMenu menu;
    private final JMenuItem save, load;
    private final GameModel model;

    public GameMenuBar(GameModel model) {
        this.model = model;
        menu = new JMenu("Settings");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        menu.add(save);
        menu.add(load);
        this.add(menu);

        save.addActionListener(this);
        load.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //saves game's state to file
        if (e.getSource() == save) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter fileWriter = new FileWriter(chooser.getSelectedFile());
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(model.getPlayer().getCharacterState());

                    int[] elem = model.getPlatform().getPlatformState();
                    bufferedWriter.newLine();
                    bufferedWriter.write(elem[0] + " " + elem[1] + " " + elem[2] + " " + elem[3]);
                    bufferedWriter.newLine();
                    bufferedWriter.write(String.valueOf(elem.length - 4));
                    for (int i = 4; i < elem.length; ++i) {
                        bufferedWriter.newLine();
                        bufferedWriter.write(String.valueOf(elem[i]));
                    }

                    bufferedWriter.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == load) {
            JFileChooser chooser = new JFileChooser();

            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    FileReader fileReader = new FileReader(chooser.getSelectedFile());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String[] elem = bufferedReader.readLine().split(" ");
                    model.getPlayer().setScore(Integer.parseInt(elem[0]));
                    model.getPlayer().setCounter(Integer.parseInt(elem[1]));
                    model.getPlayer().setSprite(Integer.parseInt(elem[2]));
                    model.getPlayer().setIdle(Boolean.parseBoolean(elem[3]));
                    model.getPlayer().setRight(Boolean.parseBoolean(elem[4]));
                    model.getPlayer().setJumping(Boolean.parseBoolean(elem[5]));
                    model.getPlayer().setPositionChanged(Boolean.parseBoolean(elem[6]));

                    elem = bufferedReader.readLine().split(" ");
                    model.getPlatform().setCameraX(Integer.parseInt(elem[0]));
                    model.getPlatform().setCameraY(Integer.parseInt(elem[1]));
                    model.getPlatform().setStartIndex(Integer.parseInt(elem[2]));
                    model.getPlatform().setEndIndex(Integer.parseInt(elem[3]));

                    int length = Integer.parseInt(bufferedReader.readLine());
                    int[] obstacles = new int[length];
                    for (int i = 0; i < length; ++i) {
                        obstacles[i] = Integer.parseInt(bufferedReader.readLine());
                    }
                    model.getPlatform().updatePlatformState(obstacles);
                    bufferedReader.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }
    }
}