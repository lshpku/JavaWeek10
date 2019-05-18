/**
 * @Author: Liang Shuhao
 */
import java.io.*;
import java.util.ArrayList;

/**
 * GameMap - Contains the map and the positions of objects.
 */
public class GameMap {

    private int routeLength; // segment number of the route
    private int routeRadius[]; // radii of each segment
    private int routeAngle[]; // angles of each segment

    private int passedSeg; // present segment of the route
    private double passedAngle; // present passed angle of the segment
    private double carSpeed; // speed of the car
    private double carX; // x coordinate of the car

    private final static double MAX_SPEED = 100.0;
    private final static double TIME_UNIT = 0.001;
    private final static double ACCE_FACTOR = 1.0;
    private final static double BREAK_FACTOR = 1.0;
    private final static double TURN_FACTOR = 1.0;
    private final static double ROAD_WIDTH = 5.0;
    private final static double CURVE_LIMIT = 10.0;

    /*
     * loadMap - Load map of the specified name.
     */
    public boolean loadMap(String mapFileName) {
        ArrayList<Integer> routeList = new ArrayList<>();
        try {
            BufferedReader reader
                    = new BufferedReader(new FileReader(mapFileName));
            String line;
            while ((line = reader.readLine()) != null) { // read the map file
                String lineSplit[] = line.split("\\s+"); // parse the content
                if (lineSplit.length != 2)
                    return false;
                int radius = Integer.decode(lineSplit[0]);
                int angle = Integer.decode(lineSplit[1]);
                if (Math.abs(radius) < ROAD_WIDTH && radius != 0) // check validity
                    return false;
                if (angle <= 0)
                    return false;
                routeList.add(radius);
                routeList.add(angle);
            }
        } catch (IOException | NumberFormatException e) {
            return false;
        }
        routeLength = routeList.size() / 2; // store the content
        routeRadius = new int[routeLength];
        routeAngle = new int[routeLength];
        for (int i = 0, j = 0; j < routeLength; j++) {
            routeRadius[j] = routeList.get(i++);
            routeAngle[j] = routeList.get(i++);
        }
        return true;
    }

    /*
     * initCar - Set the car to the default status.
     */
    public void initCar() {
        passedSeg = 0;
        passedAngle = 0;
        carSpeed = 0;
        carX = 0;
    }

    /*
     * carMoveAStep - Move the car within one time unit.
     *                Return true if it hasn't reached the terminal.
     */
    public boolean carMoveAStep() {
        if (routeRadius[passedSeg] != 0) { // if the road isn't straight
            double movedDist = carSpeed * TIME_UNIT;
            double newX = carX + movedDist * movedDist
                    / (routeRadius[passedSeg] + carX);
            if (newX > ROAD_WIDTH) { // if the car drives off
                newX = ROAD_WIDTH;
                carBreak();
            } else if (newX < -ROAD_WIDTH) {
                newX = -ROAD_WIDTH;
                carBreak();
            }
            carX = newX;
            passedAngle += movedDist / Math.abs(routeRadius[passedSeg] + carX)
                    / Math.PI * 180;
        } else { // if the road is straight
            passedAngle += carSpeed * TIME_UNIT;
        }
        if (passedAngle > routeAngle[passedSeg]) { // if finished a segment
            passedSeg++;
            passedAngle = 0;
        }
        if (passedSeg == routeLength) { // if finished the journey
            passedSeg = 0;
            return false;
        }
        return true;
    }

    public double getCarSpeed() {
        return carSpeed;
    }

    public void carAccelerate() {
        carSpeed += (MAX_SPEED - carSpeed) * ACCE_FACTOR * TIME_UNIT;
        if (carSpeed > MAX_SPEED)
            carSpeed = MAX_SPEED;
    }

    public void carBreak() {
        carSpeed -= (MAX_SPEED + carSpeed) * BREAK_FACTOR * TIME_UNIT;
        if (carSpeed < 0)
            carSpeed = 0;
    }

    public void carTurn(int factor) {
        carX += factor * TURN_FACTOR * carSpeed;
        if (carX > ROAD_WIDTH) { // if the car drives off
            carX = ROAD_WIDTH;
            carBreak();
        } else if (carX < -ROAD_WIDTH) {
            carX = -ROAD_WIDTH;
            carBreak();
        }
    }

    public double getCarX() {
        return carX;
    }

    public double getRoadAngle() {
        double posRate = passedAngle / routeRadius[passedSeg];
        if (posRate < CURVE_LIMIT)
            return 1 / routeRadius[passedSeg] * posRate
                    + 1 / routeRadius[(passedSeg - 1 + routeLength) % routeLength]
                    * (100.0 - posRate);
        else if (posRate > CURVE_LIMIT)
            return 0;
        return 1 / routeRadius[passedSeg];
    }
}
