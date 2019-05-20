package com.yt.tool.swt.base.widget.model;
import com.yt.tool.swt.base.widget.IYtWidgetFace;
/**
 * @author cr.wu
 *
 * 2015年9月18日
 * 
 * 简单的model数据
 */
public class SimpleWidgetModel implements IYtWidgetFace{
	/**
	 * 设置显示字段
	 * */
	private String show;
	/**
	 * 设置返回字段
	 * */
	private int result;
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = Integer.valueOf( result + "");
	}
	/**  **/
	public SimpleWidgetModel(String show, int result) {
		super();
		this.show = show;
		this.result = result;
	}
	
	/**  **/
	public SimpleWidgetModel() {
	}
	
	
}

