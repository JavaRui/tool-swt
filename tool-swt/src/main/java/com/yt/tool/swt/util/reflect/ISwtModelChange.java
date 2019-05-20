package com.yt.tool.swt.util.reflect;
/**
 * @author cr.wu
 *
 * 2015年8月25日
 */
public interface ISwtModelChange {
	/**
	 * model转swt
	 * */
	public void modelToSwt(Object obj);
	
	/**
	 * 返回一个swt的数据model
	 * @return
	 */
	public Object swtToModel();
	
}

