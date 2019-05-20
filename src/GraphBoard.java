//package PKUDriver;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/*
 * GraphBoard - Graph board for car driving game.
 */
public class GraphBoard extends JFrame {
	public Graphics graph;

	/**
	 * getImage - Load an image according to url.
	 * 
	 * @param path Path to the image file.
	 * @return The content of the image file.
	 */
	private Image getImage(String path) {
		URL url = GraphBoard.class.getClassLoader().getResource(path);
		BufferedImage image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	private ArrayList<Image> bgList = new ArrayList<Image>();
	private ArrayList<Image> bdList = new ArrayList<Image>();
	private ArrayList<Image> carList = new ArrayList<Image>();

	/**
	 * loadResource - Load all graph resources that are configured in the cfg file.
	 */
	private void loadResource(String cfgFilePath) throws IOException, InterruptedException {
		int bgAll = 8;
		int bdAll = 3;
		int carAll = 5;
		BufferedReader reader = new BufferedReader(new FileReader(cfgFilePath));
		String line;
		for (int i = 0; i < bgAll; i++) {
			line = reader.readLine();
			Image img = getImage(line);
			bgList.add(img);
		}
		for (int i = 0; i < bdAll; i++) {
			line = reader.readLine();
			Image img = getImage(line);
			bdList.add(img);
		}
		for (int i = 0; i < carAll; i++) {
			line = reader.readLine();
			Image img = getImage(line);
			Image smallImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			carList.add(smallImg);
		}
	}

	/**
	 * drawRoad - Draw road of the specified angle.
	 */
	public int routeIndex = 0;

	public void drawRoad(int type) {
		routeIndex = type;
	}

	/**
	 * drawBuilding - Draw building of the specified type, at the x coordinate
	 * distance away.
	 */
	private ArrayList<Building> buildings = new ArrayList<Building>();

	public void drawBuilding(ArrayList<Building> buildings) {
		this.buildings = buildings;
	}

	/**
	 * drawCar - Draw car at the x coordinate.
	 */
	public int carIndex = 3;

	public void drawCar(int x) {
		carPos = x;
	}

	public void drawCar(int x, int idx) {
		carPos = x;
		carIndex = idx;
	}
    @Override
	public void paint(Graphics g) {
		g.drawImage(bgList.get(routeIndex), 0, 0, this);
		g.drawImage(carList.get(carIndex), carPos, 450, this);
		for (Building b : buildings) {
			g.drawImage(bdList.get(b.buildingType), b.x, b.y, this);
		}

	}

	public int carPos = 550;
	public static int midX = 550; // to put a car in the center

	public GraphBoard() throws IOException, InterruptedException {
		setSize(1200, 680);
		setVisible(true);
		graph = this.getGraphics();
		loadResource("car.cfg");
		graph.drawImage(bgList.get(routeIndex), 0, 0, this);
		graph.drawImage(carList.get(carIndex), carPos, 450, this);
	}
}