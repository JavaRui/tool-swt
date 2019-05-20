package com.yt.tool.swt.base.widget;
import java.util.List;
/**
 * @author cr.wu
 *
 * 2015年8月31日
 */
public interface IYtMutilWidget {
	
	
	/**
	 * 获取被选择的result数据
	 * 可以返回数组，获取list
	 * */
	public List<?> getResults();
	/**
	 * 返回字符串形式的结果，用特殊符号隔开
	 * */
	public String getResultString();
	
	/**
	 * 获取被选择的对象
	 * 可以返回数组，list
	 * */
	public List<IYtWidgetFace> getResultObjects();
	
	/**
	 * 根据show数组，设置选择的条目
	 * @param showString
	 */
	public void setSelectByShow(String[] showString);
	
	/**
	 * 根据show数组，设置选择的条目
	 * @param showString
	 */
	public void setSelectByResult(Object[] resultString);
	
	
}

