package com.yt.tool.swt.ui.text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import com.yt.tool.swt.calendar.Calendar;
import com.yt.tool.swt.calendar.ICalendarSub;
import com.yt.tool.swt.util.reflect.ISwtModelChange;
/**
 * @author cr.wu
 *
 * 2015年9月14日
 * 
 * 日历文本
 */
public class YtCalendarText extends YtText implements ISwtModelChange{
	private String format;
	
	public YtCalendarText(Composite parent){
		this(parent, SWT.BORDER);
	}
	
	public YtCalendarText(Composite parent, int style) {
		super(parent, style);
		GridData gd = new GridData(SWT.FILL,SWT.FILL,false,false);
		
		setLayoutData(gd);
		addListener();
	}
	private void addListener(){
		addListener(SWT.MouseUp, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				final Calendar calendar = (Calendar)Calendar.getSub(getShell());
				ICalendarSub calendarSub = new ICalendarSub() {
					
					@Override
					public void setDate(String date) {
						setText(date);
					}
					
					@Override
					public Point getDtLocation() {
						Point p = toDisplay(0,0);
						p.y = p.y+getSize().y;
						return p;
					}
				};
				calendar.createContent(calendarSub);
			}
		});
	}
	
	@Override
	public void setText(String string) {
		if(string.indexOf("T") != -1){
			string = string.split("T")[0];
		}
		super.setText(string);
	}
	
	
	@Override
	protected void checkSubclass() {
		
	}
	public String getFormat() {
		return format;
	}
	/**
	 * 设置日期格式
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
//
//	@Override
//	public void modelToSwt(Object obj) {
//	}
//
//	@Override
//	public Object swtToModel() {
//		return null;
//	}
	@Override
	public void modelToSwt(Object obj) {
		setText(obj+"");
	}
	@Override
	public Object swtToModel() {
		return getText();
	}
	
}

