package com.yt.tool.swt.base.widget;
/**
 * @author cr.wu
 *
 * 2015年9月10日
 * 
 * 规定了show和result字段的获取
 * 
 */
public interface IYtWidgetFace {
	/**获取显示字段*/
	public String getShow();
	/**获取结果字段*/
	public Object getResult();
	
	/**设置显示字段*/
	public void setShow(String obj);
	/**设置结果字段*/
	public void setResult(Object obj);
	
}

