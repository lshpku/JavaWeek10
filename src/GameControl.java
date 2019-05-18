/**
 * @auther: Liang Shuhao
 */
import javax.swing.JFrame;

/**
 * GameControl - Control the back logic of the game.
 */
public class GameControl extends Thread {

    private final static int CYCLE_TIME = 25;

    private GameMap map;
    private ArrowKeyListrner keyboard;
    private JFrame graph;

    GameControl() {
        map = new GameMap();
        map.loadMap("src/map.cfg");
        map.initCar();
        keyboard = new ArrowKeyListrner();
    }

    public static void main(String[] args) {
        GameControl game = new GameControl();
        game.start();
    }

    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();

            if (keyboard.upIsPressed() && !keyboard.downIsPressed())
                map.carAccelerate();
            else if (keyboard.downIsPressed() && !keyboard.upIsPressed())
                map.carBreak();
            if (keyboard.leftIsPressed() && !keyboard.rightIsPressed())
                map.carTurn(true);
            else if (keyboard.rightIsPressed() && !keyboard.leftIsPressed())
                map.carTurn(false);

            if (map.carMoveAStep() == false)
                System.out.println("Game Over!");
            System.out.println(map.getCarX() + " @" + map.getCarSpeed());

            try { // sleep for a cycle
                int deltaTime = (int) (System.currentTimeMillis() - startTime);
                if (deltaTime < CYCLE_TIME)
                    sleep(CYCLE_TIME - deltaTime);
            } catch (InterruptedException ex) {
            }
        }
    }
}
