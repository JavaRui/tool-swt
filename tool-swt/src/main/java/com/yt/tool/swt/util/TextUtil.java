package com.yt.tool.swt.util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.yt.tool.swt.ui.text.YtVerfityText;
public class TextUtil {
	
	public static final String PRICE_REG = "0123456789.";
	public static final String COUNT_REG = "0123456789";
	
	/**
	 * 设置文本为纯数字文本，小数点
	 * */
	public static void setOnlyNum(Text text) {
		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String reg = PRICE_REG;
				String s = e.text + "";
				e.doit = check(s , reg);
			}
		});
	}
	
	/**
	 * 判断是否在reg的范围内
	 * @param s
	 * @param reg
	 * @return
	 */
	public static boolean check(String s , String reg){
		if(reg.length() == 0 || reg == null){
			return true;
		}
		
		for(int i = 0;i < s.length();i++){
			boolean b = (reg.indexOf(s.charAt(i)) >= 0);
			if(!b){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 设置文本最大和最少数字
	 * */
	public static void setNumMinMax(final Text text, final int min,final int max) {
		text.setText(min+"");
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text t = (Text) e.widget;
				t.getSelectionText();
				if (t.getText().length() == 0){
					t.setText(min+"");
					return;
				}
				try{
					if (Integer.valueOf(t.getText()) > max) {
						t.setText(max + "");
					}else if(Integer.valueOf(t.getText()) <min){
						t.setText(min + "");
					}
					t.setSelection(t.getText().length(), t.getText().length());
					
				}catch(NumberFormatException ex){
					t.setText(max + "");
					t.setSelection(t.getText().length(), t.getText().length());
				}
			}
		});
	}
	/**
	 * 设置文本长度
	 * */
	public static void setLimit(final Text text , final int len) {
		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				
				String s = text.getText() + e.text;
				if(s.length() > len){
					e.doit = false;
				}
				
			}
		});
	}
	
	/**
	 * 初始化标题
	 * 
	 * @param txt
	 */
	public static Label initTitle(Composite parentComposite  , String txt) {
		Label titleLabel = new Label( parentComposite , SWT.BORDER | SWT.CENTER);
		titleLabel.setText(txt);
		titleLabel.setBackground(YtColorUtil.blackColor);
		titleLabel.setForeground(YtColorUtil.whiteColor);
		titleLabel.setFont(YtFontUtil.getFontBold(16));
		GridData gd = LayoutUtil.createFillGridNoVer(1);
		gd.heightHint = 30;
		titleLabel.setLayoutData(gd);
		return titleLabel;
	}
	
	
	/**
	 * 创建一个有验证的文本
	 */
	public static YtVerfityText createVerfityText(Composite parent , int w , int h){
		
		YtVerfityText text = new YtVerfityText(parent);
		
		GridData gd = new GridData(SWT.CENTER,SWT.CENTER,false,false);
		gd.heightHint = h;
		gd.widthHint = w;
		text.setLayoutData(gd);
		
		return text;
		
	}
	
	
}

