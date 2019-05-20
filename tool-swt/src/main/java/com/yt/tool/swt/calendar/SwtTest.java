package com.yt.tool.swt.calendar;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.yt.common.xxx;
/**
 * @author cr.wu
 *
 * 2015??7??è21??
 */
public class SwtTest {
	protected Shell shell;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SwtTest window = new SwtTest();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		final Calendar calendar = (Calendar)Calendar.getSub(shell);
		ICalendarSub calendarSub = new ICalendarSub() {
			
			@Override
			public void setDate(String date) {
				xxx.log(this,date);
				
			}
			
			@Override
			public Point getDtLocation() {
				return new Point(0,0);
			}
		};
		calendar.createContent(calendarSub);
		
	}
}

