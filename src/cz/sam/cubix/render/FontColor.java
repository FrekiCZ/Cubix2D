package cz.sam.cubix.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class FontColor {
	
	public static void setColor(int red, int green, int blue) {
		GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1);
	}
	
	public static void setColor(int red, int green, int blue, float alpha) {
		GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, alpha);
	}
	
	public static void setColor(Color color) {
		GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1);
	}
	
	public static Color getColor(int color) {
		int red = color >> 16 & 255;
        int blue = color >> 8 & 255;
        int green = color & 255;
        int alpha = color >> 24 & 255;
        return new Color(alpha, red, green, blue);
	}
	
	public static int getColor(int r, int g, int b) {
		int color = r << 16 | g << 8 | b;
		return color;
	}
	
	public static int getIntColorRGBA(int r, int g, int b, int alpha) {
		int color = r << 32 | g << 16 | b << 8 | alpha;
		return color;
	}
	
}
