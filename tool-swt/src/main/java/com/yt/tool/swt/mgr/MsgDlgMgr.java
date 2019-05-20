package com.yt.tool.swt.mgr;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import com.yt.common.xxx;
import com.yt.tool.swt.base.SwtVoid;
public class MsgDlgMgr {
	public  static String TITLE = "系统提示";
	
	public static void setTitle(String t){
		TITLE = t;
	}
	
	/**
	 * return:
	 * SWT.OK
	 * SWT.CANCEL
	 * */
	public static int showMessbox(String desc){
		return showMessbox(MsgDlgMgr.TITLE,desc,0);
	}
	/**
	 * 二选一框的提示信息
	 * 返回 SWT.OK / SWT.CANCEL
	 * */
	public static int showMessbox(final String title,final String desc,int style){
		
		if(style == 0){
			style = SWT.OK|SWT.CANCEL;
		}else{
			style =  SWT.OK;
		}
		MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), style); 
		messageBox.setMessage(desc);
		messageBox.setText( title);
		
		return messageBox.open() ;// messageBox.open() == SWT.OK, messageBox.open() == SWT.CANCEL;
	}
	/**
	 * 显示提示信息
	 * */
	public static void showInfo(final String title,final String desc){
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), title, desc);
		
	}
	
	/**
	 * 显示自定义的dialog
	 * @param title 提示标题
	 * @param desc 提示内容
	 * @param btnTxt 按钮的文本
	 * @return
	 */
	public static int showCustomDialog(String title,String desc,String[] btnTxt){
		MessageDialog d = new MessageDialog(Display.getCurrent().getActiveShell(),title,
				null,desc,
				MessageDialog.INFORMATION,
				btnTxt,
				1) ;
		int i = d.open();
		return i;
	}
	
	/**
	 * 显示错误信息
	 * @param title
	 * @param desc
	 */
	public static void showError(final String title,final String desc){
		MessageDialog.openError(null, title, desc);
	}
	/**
	 * 显示错误信息，标题为软件标题
	 * */
	public static void showError(String desc){
		showError( MsgDlgMgr.TITLE, desc);
	}
	/**
	 * return value	正常输入
	 * 	""==>用户点击了确定按钮，但没输入
	 * 	null==>用户点击了取消按钮
	 * */
	public static String showInput(String desc){
		
		return showInput(desc , "");
		
	}
	
	/**
	 * return value	正常输入
	 * 	""==>用户点击了确定按钮，但没输入
	 * 	null==>用户点击了取消按钮
	 * */
	public static String showInput(String desc , String value){
		
		InputDialog inputDialog = new InputDialog(null,desc, desc, value, null);
		
		int r = inputDialog.open();
		if(r == Window.OK){
			xxx.log(MsgDlgMgr.class,"ok");
			value = inputDialog.getValue();
		}else {
			xxx.log(MsgDlgMgr.class,"no");
			return null;
		}
		return value;
		
	}
	
}

