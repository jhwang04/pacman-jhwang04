package jhwang04.pacman;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;

public class Button {
	private int x, y, width, height, textSize;
	private boolean isHovered, isPressed;
	private Color primaryColor, secondaryColor, textColor, borderColor;
	private String text;
	
	public Button(int x, int y, int width, int height, String text, int textSize, Color primaryColor, Color secondaryColor, Color textColor, Color borderColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.textSize = textSize;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.textColor = textColor;
		this.borderColor = borderColor;
	}
	
	public void draw(PApplet p) {
		p.pushMatrix();
		p.pushStyle();
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		
		Color fillColor;
		if(isHovered == true)
			fillColor = secondaryColor;
		else
			fillColor = primaryColor;
		

		p.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
		p.stroke(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
		p.strokeWeight(4);
		
		if(isPressed == true) {
			p.translate((float) (x + width/20.0), (float) (y + height/20.0));
			p.rect(0, 0, width - (width/10.0f), height - (height/10.0f), 10);
			//float textSize = (float) (((width - (width/10.0f)) /** 0.75 */) / text.length());
			p.textSize(textSize);
			p.popMatrix();
			p.fill(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
			if(textSize > 40)
				p.text(text, x + width/2, y + height/2 -10/*+ textSize*3/4*/);
			else
				p.text(text, x + width/2, y + height/2 -6/*+ textSize*3/4*/);
		} else {
			p.translate(x, y);
			p.rect(0, 0, width, height, 10);
			//float textSize = (float) ((width /* * 0.75*/ ) / text.length());
			p.textSize(textSize);
			p.popMatrix();
			p.fill(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
			if(textSize > 40)
				p.text(text, x + width/2, y + height/2 -10/*+ textSize*3/4*/);
			else
				p.text(text, x + width/2, y + height/2 -6/*+ textSize*3/4*/);
		}
		
		
		p.popStyle();
	}
	
	public void act(int mouseX, int mouseY, boolean mousePressed) {
		if(mouseX > x && mouseX < (x + width) && mouseY > y && mouseY < (y + height))
			isHovered = true;
		else
			isHovered = false;
		
		if(isHovered == true && mousePressed == true)
			isPressed = true;
		else
			isPressed = false;
		
	}
	
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int w) {
		width = w;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public boolean getIsHovered() {
		return isHovered;
	}
	
	public boolean getIsPressed() {
		return isPressed;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
