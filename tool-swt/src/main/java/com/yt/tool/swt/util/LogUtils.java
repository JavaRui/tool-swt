package com.yt.tool.swt.util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import com.yt.common.xxx;
import com.yt.tool.swt.base.SwtVoid;
import com.yt.tool.swt.ui.text.YtText;
import com.yt.utils.algorithm.DateUtil;
import com.yt.utils.log.LogFace;
public class LogUtils implements LogFace{
	private static LogUtils inst;
	
	/**  **/
	private LogUtils() {
		
	}
	
	public static LogUtils getInst(){
		if(inst == null){
			inst = new LogUtils();
		}
		return inst;
	}
	
	
	private static Text view;
	private static boolean isShow = true;
	public static YtText createView(Composite parent){
		YtText infoText= new YtText(parent,  SWT.BORDER | SWT.WRAP|SWT.MULTI|SWT.V_SCROLL|SWT.READ_ONLY);
		setView(infoText);
		infoText.setKBSelectAll();
		return infoText;
	}
	
	/**
	 * 创建清除按钮
	 * @param parent
	 * @return
	 */
	public static Button createClearBtn(Composite parent){
		Button btn = new Button(parent, SWT.PUSH);
		btn.setText("清除记录");
		btn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				clearLog();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		return btn;
	}
	
	public static void setView(Text t) {
		view = t;
	}
	
	
	/**
	 * 清除记录
	 */
	public static void clearLog(){
		SwtVoid.delayAsy(1, new Runnable() {
			@Override
			public void run() {
				view.setText("");
			}
		});
	}
	
	/**
	 * 添加显示字段,添加日期时间
	 * @param type
	 * @param txt
	 * @return
	 */
	public static String doTxt(String type, String txt) {
		return DateUtil.getNewDate(DateUtil.HMS1) + "  " + type + "  " + txt;
	}
	/**
	 * 添加显示字段
	 * */
	public static void showText(final String t) {
		SwtVoid.delayAsy(1, new Runnable() {
			@Override
			public void run() {
				view.append(t+"\n");
			}
		});
	}
	/**
	 * 添加分隔符
	 * */
	public static void addSP() {
		showText("----------------------------------------------------------");
	}
	/**
	 * 设置显示，或不显示
	 * */
	public static boolean setShow() {
		isShow = !isShow;
		return isShow;
	}
	public final static String TYPE_系统 = "系统";
	public final static String TYPE_任务 = "任务";
	public final static String TYPE_UU云 = "UU云";
	public final static String TYPE_超人打码 = "超人打码";
	public final static String TYPE_啦啦打码 = "拉拉打码";
	public final static String TYPE_打码 = "打码平台";
	/**颜色*/
	public final static String COLOR_SEP = "#$#$#$";
	public final static String COLOR_红色 = COLOR_SEP+"红色";
	
	public final static String TYPE_宽带 = "宽带";
	@Override
	public void log(String type, Object txt) {
		
		if(view == null){
			return;
		}
		if (!isShow) {
			return;
		}
		final String t = doTxt(type, txt.toString());
		if(Display.getCurrent() == null){
			showText(t);
		}else{
			view.append(t+"\n");
		}
		
	}
	@Override
	public void log(Class<?> clsName, Object log) {
	}
	@Override
	public void le(String clsName, Object show) {
	}
	@Override
	public void le(Class<?> clsName, Object show) {
	}
	
	
}

