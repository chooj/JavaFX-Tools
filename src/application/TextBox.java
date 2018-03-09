package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBox {
	
	private Rectangle box;
	private int uIndex;
	private boolean active = true, primed = true;
	private Text text;
	private Line cursor;
	private double width, height, size;
	
	public TextBox(double x, double y, double width, double height) {
		this.width = width;
		this.height = height;
		box = new Rectangle(x, y, width, height);
		box.setFill(Color.WHITE);
		uIndex = 0;
		size = height/2;
		box.setStroke(Color.grayRgb(180));
		text = new Text(x + height/4, y + 3*height/4, "");
		text.setFill(Color.grayRgb(30));
		text.setFont(new Font("Verdana", size));
		cursor = new Line(text.getX() + 2, text.getY() - 11*size/10, text.getX() + 2, text.getY() + size/10);
	}
	
	public void boxStroke() {
		if (active) {
			box.setStroke(Color.rgb(70, 160, 255));
		} else {
			box.setStroke(Color.grayRgb(180));
		}
	}
	
	public double deltaCX() {
		Text subtext = new Text(string().substring(0, uIndex));
		subtext.setFont(new Font("Verdana", size));
		return subtext.getLayoutBounds().getWidth();
	}
	
	public void drawCursor() {
		if (active && System.currentTimeMillis() % 1000 < 500) {
			cursor.setLayoutX(deltaCX());
		} else {
			cursor.setLayoutX(Double.NEGATIVE_INFINITY);
		}
	}
	
	public String string() {
		return text.getText();
	}
	
	public boolean hasRoom(String key) {
		Text newText = new Text(string() + key);
		newText.setFont(new Font(size));
		return newText.getLayoutBounds().getWidth() < width - 3*height/4;
	}
	
	public void mouseActivate(int mouseX, int mouseY, boolean mousePressed) {
		if (mousePressed) {
			if (mouseX >= box.getX() && mouseX <= box.getX() + width
					&& mouseY >= box.getY() && mouseY <= box.getY() + height) {
				active = true;
				uIndex = string().length();
			} else {
				active = false;
			}
		}
	}
	
	public void editText(String key, boolean keyPressed, boolean keyReleased, boolean shiftDown) {
		if (active) {
			if (keyPressed && primed) {
				primed = false;
				if (key.length() == 1 && hasRoom(key)) {
					text.setText(string() + ((shiftDown) ? key : key.toLowerCase()));
					uIndex++;
				} else {
					switch (key) {
					case "SPACE": if (hasRoom(" ")) {
							text.setText(string() + " ");
							uIndex++;
						}
						break;
					case "RIGHT": if (uIndex < string().length()) {
							uIndex++;
						}
						break;
					case "LEFT": if (uIndex > 0) {
							uIndex--;
						}
						break;
					case "BACK_SPACE": if (uIndex > 0) {
							text.setText(string().substring(0, uIndex-1)
									+ string().substring(uIndex, string().length()));
							uIndex--;
						}
						break;
					case "ENTER": active = false;
						break;
					default: break;
					}
				}
			} else if (keyReleased) {
				primed = true;
			}
		}
	}
	
	public void draw(String key, boolean keyPressed, boolean keyReleased, boolean shiftDown,
			int mouseX, int mouseY, boolean mousePressed) {
		boxStroke();
		drawCursor();
		mouseActivate(mouseX, mouseY, mousePressed);
		editText(key, keyPressed, keyReleased, shiftDown);
	}
	
	public Group components() {
		Group g = new Group();
		g.getChildren().addAll(box, text, cursor);
		return g;
	}
	
}
