package com.yt.tool.swt.calendar;
import org.eclipse.swt.graphics.Point;
/**
 * @author cr.wu
 *
 * 2015??7??è21??
 */
public interface ICalendarSub {
	/**设置日历的坐标*/
	public Point getDtLocation();
	/**返回时间**/
	public void setDate(String date);
}

