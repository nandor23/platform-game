public class GameControl implements Runnable {
    private GameModel model;
    private GameView view;


    public GameControl(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {

        do {
            if (!model.isGamePaused()) {
                model.getPlayer().updatePosition();             //update the character's position and animation
                model.getPlatform().updateBombAnimation();      //updates the bomb's position
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            view.repaint();

        } while (!model.getPlayer().collides());

        model.setGameEnd(true);
        view.repaint();
        model.getSound().stopMusic();
    }
}
