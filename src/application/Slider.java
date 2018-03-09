package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Slider {
	
	private Line end1, end2, scale, tick;
	private double x, y, width, height, amount;
	private String orientation;
	
	public Slider(double x, double y, double width, double height, String orientation) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.orientation = orientation;
		amount = .5;
		if (orientation.equals("vertical")) {
			end1 = new Line(x, y + height, x + width, y + height);
			end2 = new Line(x, y, x + width, y);
			scale = new Line(x + width/2, y, x + width/2, y + height);
			tick = new Line(x, y + tickDist(), x + width, y + tickDist());
		} else if (orientation.equals("horizontal")) {
			end1 = new Line(x, y, x, y + height);
			end2 = new Line(x + width, y, x + width, y + height);
			scale = new Line(x, y + height/2, x + width, y + height/2);
			tick = new Line(x + tickDist(), y, x + tickDist(), y + height);
		}
		setParams(end1);
		setParams(end2);
		setParams(scale);
		setParams(tick);
	}
	
	/**
	 * Streamlines all line objects to same basic specifications.
	 * @param line
	 */
	public void setParams(Line line) {
		line.setStroke(Color.BLACK);
		line.setStrokeWidth(2);
	}
	
	/**
	 * Calculates how far the tick should be.
	 * @return distance from first end
	 */
	public double tickDist() {
		if (orientation.equals("up")) {
			return height*(1-amount);
		} else {
			return width*amount;
		}
	}
	
	/**
	 * Returns Line objects that compose the slider. Use when adding to Pane.
	 * @return Group of Line objects.
	 */
	public Group components() {
		Group g = new Group();
		g.getChildren().addAll(end1, end2, scale, tick);
		return g;
	}
	
	/**
	 * 
	 * @return amount tick indicates on slider.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * Checks if mouse is in range of the slider.
	 * @param mouseX
	 * @param mouseY
	 * @return if mouse is in slider
	 */
	
	public boolean mouseInSlider(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
	
	/**
	 * Adjusts tick in response to user modification.
	 * @param mouseX
	 * @param mouseY
	 * @param mousePressed
	 */
	public void draw(int mouseX, int mouseY, boolean mousePressed) {
		if (mouseInSlider(mouseX, mouseY) && mousePressed) {
			amount = (mouseX-x)/width;
			tick.setLayoutX(width*(amount-0.5));
		}
	}
}
