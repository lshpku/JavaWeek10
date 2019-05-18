/*
 * GraphBoard - Graph board for car driving game.
 */
public interface GraphBoard {
    
    /*
     * loadResource - Load all graph resources that are configured
     *                in the cfg file.
     */
    public void loadResource(String cfgFilePath);
    
    /*
     * drawBackground - Draw background of the specified status.
     */
    public void drawBackground(int status);
    
    /*
     * drawRoad - Draw road of the specified angle.
     */
    public void drawRoad(int angle);
    
    /*
     * drawBuilding - Draw building of the specified type, at the
     *                x coordinate distance away.
     */
    public void drawBuilding(int type, int distance, int x);
    
    /*
     * drawCar - Draw car at the x coordinate.
     */
    public void drawCar(int x);
}
