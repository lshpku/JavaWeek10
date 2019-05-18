
/**
 * GameControl - Control the back logic of the game.
 */
public class GameControl {
    
    private GameMap gameMap;
    
    GameControl() {
        gameMap = new GameMap();
        gameMap.loadMap("src/map.cfg");
        gameMap.initCar();
    }
    
    public static void main(String[] args) {
        
    }
    
}
