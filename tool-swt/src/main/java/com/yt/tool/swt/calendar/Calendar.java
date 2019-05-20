package com.yt.tool.swt.calendar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
/**
 * @author cr.wu
 *
 * 2015??7??è21??
 */
public class Calendar extends Composite{
	private Calendar me;
	private DateTime dateTime;
	private ICalendarSub sub;
	private Calendar(Shell parent ) {
		super(parent, SWT.BORDER);
		me = this;
		
	}
	/**
	 * 创建容器，添加到shell去
	 * */
	public void createContent(ICalendarSub sub1){
		this.sub = sub1;
		setLayout(new FillLayout());
		getShell().setLocation(sub.getDtLocation());
		dateTime = new DateTime(me, SWT.CALENDAR);
		dateTime.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sub.setDate(formatDt());
				getShell().dispose();
			}
		});
		
		dateTime.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				sub.setDate(formatDt());
//				me.setVisible(false);
				getShell().dispose();
			}
		});
		layout();
		getShell().open();
	}
	private String formatDt() {
		String month = (dateTime.getMonth()+1)+"";
		if((dateTime.getMonth()+1)<10){
			month = "0"+month;
		}
		String day = dateTime.getDay()+"";
		if(dateTime.getDay() < 10){
			day = "0"+day;
		}
		return dateTime.getYear() + "-" + month + "-" + day;
	}
	
	/**
	 * 创建日历的静态方法，不能直接使用new
	 * @param parent
	 * @return
	 */
	public static Composite getSub(Shell parent ){
		Shell shell = new Shell(parent , SWT.NO_TRIM);
		shell.setLayout(new FillLayout());
		shell.setSize(217, 163);
		Calendar sl = new Calendar(shell);
		return sl;
	}
}

