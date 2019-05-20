/**
 * 
 */
package com.yt.tool.swt.util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
/**
 * @author cr.wu
 *
 * 2015年7月15日
 */
public class YtFontUtil {
	public static String FONT_STYLE = "微软雅黑";
	public static Font getFontNor(int height){
		return YtFontUtil.getFont(height, SWT.NORMAL);
	}
	public static Font getFontItalic(int height){
		return YtFontUtil.getFont(height, SWT.ITALIC);
	}
	public static Font getFontBold(int height){
		return YtFontUtil.getFont(height, SWT.BOLD);
	}
	public static Font getFont(int height , int style){
		return new Font(null, FONT_STYLE, height, style);
	}
	/**
	 * 根据文字获取文字的实际尺寸
	 * */
	public static Point getPxByText(String txt , Font font){
		GC gc = new GC(Display.getCurrent().getShells()[0]);
		gc.setFont(font);
		Point p = gc.textExtent(txt);
		gc.dispose();
		return p;
	}
	
	
}

