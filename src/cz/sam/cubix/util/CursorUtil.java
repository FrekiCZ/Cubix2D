package cz.sam.cubix.util;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class CursorUtil {
	
	public static void setDefaultOSCursor() {
		try {
			Mouse.setNativeCursor(null);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setBlancCursor() {
		try {
			Mouse.setNativeCursor(new org.lwjgl.input.Cursor(32, 32, 0, 30, 1, GLUtil.getMouseCursor(), null));
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
