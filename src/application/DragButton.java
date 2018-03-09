package application;

public class DragButton extends Button {
	
	private boolean engaged = false;
	private int pmouseX, pmouseY;
	private double xi, yi, lxi, lyi;
	
	public DragButton(double x, double y, double width, double height) {
		super(x, y, width, height);
		xi = x;
		yi = y;
		lxi = 0;
		lyi = 0;
	}
	
	public void draw(int mouseX, int mouseY, boolean mousePressed, boolean mouseReleased) {
		super.draw(mouseX, mouseY, mousePressed);
		// engage/disengage
		if (mouseInButton(mouseX, mouseY) && mousePressed) {
			// establish initial mouse position
			if (!engaged) {
				pmouseX = mouseX;
				pmouseY = mouseY;
			}
			engaged = true;
		} else if (mouseReleased) {
			// save initial positions for next drag
			xi = getX();
			yi = getY();
			lxi = getButton().getLayoutX();
			lyi = getButton().getLayoutY();
			engaged = false;
		}
		// drag
		if (engaged) {
			// change in location
			double xDiff = mouseX - pmouseX;
			double yDiff = mouseY - pmouseY;
			// adjust layout values
			getButton().setLayoutX(lxi + xDiff);
			getButton().setLayoutY(lyi + yDiff);
			// real button position
			setX(mouseX - (pmouseX - xi));
			setY(mouseY - (pmouseY - yi));
			// adjust text location
			if (getText() != null) {
				setTextLoc(lxi, lyi, mouseX - pmouseX, mouseY - pmouseY);
			}
		}
	}
	
}
