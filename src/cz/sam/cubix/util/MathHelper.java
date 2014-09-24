package cz.sam.cubix.util;

public class MathHelper {
	
	public static int ceiling_double_int(double d) {
        int i = (int)d;
        return d > (double)i ? i + 1 : i;
    }
	
	public static long floor_double_long(double par0) {
		long i = (long)par0;
        return par0 < (double)i ? i - 1L : i;
	}
	
	public static final float sqrt_float(float par0) {
        return (float)Math.sqrt((double)par0);
    }

	public static int floor_double(double par0) {
        int var2 = (int)par0;
        return par0 < (double)var2 ? var2 - 1 : var2;
    }

	public static int bucketInt(int par0, int par1) {
		return par0 < 0 ? -((-par0 - 1) / par1) - 1 : par0 / par1;
	}
	
}
