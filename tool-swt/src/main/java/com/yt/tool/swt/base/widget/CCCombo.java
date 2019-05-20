package com.yt.tool.swt.base.widget;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import com.yt.tool.swt.base.YtWidget;
import com.yt.tool.swt.base.widget.model.SimpleWidgetModel;
/**
 * @author cr.wu
 *
 * 2015年8月29日
 */
public class CCCombo extends YtWidget{
	private Combo combo;
	/**
	 * 封装了combo,自动判断，不会重复触发
	 * */
	public CCCombo(Composite parent ) {
		super(parent);
		combo = new Combo(this,SWT.READ_ONLY|SWT.BORDER | SWT.V_SCROLL);
		combo.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if(getResult() == null){
					return ;
				}
				
				lll("当前选择：          "+getResult());
				if(resultBack != null){
					resultBack.callBack(getResult());
				}
				
			}
		});
		
	}
	@Override
	public int getSelectIndex() {
		return combo.getSelectionIndex();
	}
	@Override
	public void setSelectIndex(int index) {
		combo.select(index);
	}
	@Override
	protected void add(String txt) {
		combo.add(txt);
	}
	@Override
	protected void replaceItem(int index, String nexTxt) {
		combo.remove(index);
		combo.add(nexTxt, index);
	}
	@Override
	protected void clearChildItem() {
		combo.removeAll();
	}
	
	/**
	 * 添加是否两个选项卡
	 */
	public void addTrueFalse(){
		addSimple(new String[]{"是","否"});
		
	}
	
	/**
	 * 添加两个选择的文本
	 * @param texts 长度为2的显示字段
	 */
	public void addSimple(String[] texts){
		SimpleWidgetModel model1 = new SimpleWidgetModel();
		model1.setShow(texts[0]);
		model1.setResult(1);
		
		SimpleWidgetModel model2 = new SimpleWidgetModel();
		model2.setShow(texts[1]);
		model2.setResult(0);
		
		List<IYtWidgetFace> list = new ArrayList<IYtWidgetFace>();
		list.add(model1);
		list.add(model2);
		setData(list);
		setSelectIndex(0);
	}
	public Combo getCombo() {
		return combo;
	}
	
	
}

