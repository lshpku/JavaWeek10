/*
 * GraphBoard - Graph board for car driving game.
 */
public interface GraphBoard {
    
    public void loadResource(String cfgFilePath);
    
    
    public void drawBackground(int status);
    
    public void drawRoad(int angle);
    
    public void drawBuilding(int type, int distance, int x);
    
    public void drawCar(int x);
}
