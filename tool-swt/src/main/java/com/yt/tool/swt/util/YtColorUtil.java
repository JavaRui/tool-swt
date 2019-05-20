package com.yt.tool.swt.util;
import java.util.HashMap;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
public class YtColorUtil {
	public static Color whiteColor = new Color(null, 245, 245, 245);
	public static Color buleColor = new Color(null,10,166,227);
	public static Color regColor = new Color(null,227,20,20);
	public static Color blackColor = new Color(null, 0, 0, 0);
	
//	public static Color bgColor = new Color(null,192,192,192);
//	public static Color bgColor = new Color(null,255,255,255);
	public static Color bgColor = new Color(null,233,233,233);
	
	public static void addColor(CTabFolder comp){
//		comp.setBackground(whiteColor);
		comp.setSelectionBackground(new Color[] { new Color(null, 208,
		 222, 232), new Color(null, 217, 230, 245),
		 new Color(null, 124, 183, 241) }, new int[] { 25, 25 }, true);
	}
	
	public static void addColor(Composite comp){
		comp.setBackground(whiteColor);
	}
	
	
	
}

