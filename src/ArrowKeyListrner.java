/**
 * @author Liang Shuhao
 */
import java.awt.event.*;

/**
 * ArrowKeyListener - Listener for arrow keys.
 */
public class ArrowKeyListrner implements KeyListener {

    private boolean up, left, right, down;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    public boolean upIsPressed() {
        return up;
    }
    public boolean downIsPressed() {
        return down;
    }
    public boolean leftIsPressed() {
        return left;
    }
    public boolean rightIsPressed() {
        return right;
    }
}
