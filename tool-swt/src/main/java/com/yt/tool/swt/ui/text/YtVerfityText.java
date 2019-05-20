package com.yt.tool.swt.ui.text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import com.yt.tool.swt.util.TextUtil;
/**
 * @author cr.wu
 *
 * 2015年9月26日
 * 
 * 默认是带有小数点的数量文本
 * 如果只需要数字，可以调用setOnlyInt
 */
public class YtVerfityText extends YtText{
	private String verString = TextUtil.PRICE_REG;
	
	/**  **/
	public YtVerfityText(Composite parent, int style) {
		super(parent, style);
		addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent e) {
				String text = e.text;
				e.doit = TextUtil.check(text , verString);
			}
		});
		if(verString.length() >0){
			setText(verString.charAt(0)+"");
		}
	}
	/**  **/
	public YtVerfityText(Composite parent) {
		this(parent , SWT.BORDER);
		
	}
	
	/** 设置只能数字 */
	public void setOnlyInt(){
		verString = TextUtil.COUNT_REG;
	}
	/** 设置小数点和数字  */
	public void setOnlyDouble(){
		verString = TextUtil.PRICE_REG;
	}
	
	/** 设置不同验证的文本 */
	public void setVerString(String newVerString){
		verString = newVerString;
	}
	
	
	
	
}

