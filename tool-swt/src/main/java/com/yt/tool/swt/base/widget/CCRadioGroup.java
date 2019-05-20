package com.yt.tool.swt.base.widget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import com.yt.tool.swt.base.YtWidget;
import com.yt.tool.swt.base.YtComposite;
import com.yt.tool.swt.util.LayoutUtil;
import com.yt.utils.notify.INBack;
/**
 * @author cr.wu
 *
 * 2015年8月29日
 */
public class CCRadioGroup extends YtWidget{
	private ScrolledComposite scrolledComposite ;
	private YtComposite btnComposite;
	private CCRadioGroup me;
	private Button currentBtn;
	private boolean autoSelect = true;
	/**每次点击时，会把一个对象返回给back，看情况设置*/
	private INBack objBack;
	
	public CCRadioGroup(Composite parent) {
		super(parent);
		me = this;
		scrolledComposite = new ScrolledComposite(me, SWT.V_SCROLL);
		btnComposite = new YtComposite(scrolledComposite);
		btnComposite.setGridLayout();
		scrolledComposite.setContent(btnComposite);
	}
	@Override
	public int getSelectIndex() {
		Control[] controls = btnComposite.getChildren();
		for(int i = 0 ; i < controls.length ;i++){
			Button btn = (Button)controls[i];
			if(btn.getSelection()){
				return i;
			}
		}
		return -1;
	}
	@Override
	public void setSelectIndex(int index) {
		Control[] controls = btnComposite.getChildren();
		if(index >= controls.length){
			lll("越界了。。。。。设置的index为：     "+index+"       当前的数组长度为:        "+controls.length);
			return ;
		}
		
		for(Control control : controls){
			Button btn = (Button)control;
			btn.setSelection(false);
		}
		
		Button btn = (Button)controls[index];
		btn.setSelection(true);
		btn.notifyListeners(SWT.Selection, null);
	}
	@Override
	protected void addShow(IYtWidgetFace object) {
		super.addShow(object);
		
		String name = getShowByObject(object);
		Object result = getResultByObject(object);
		
		Button btn = new Button(btnComposite, SWT.RADIO);
		btn.setText(name);
		btn.setData("obj",object);
		btn.setData("result", result);
		btn.setLayoutData(LayoutUtil.createFillGridNoVer(1));
		btn.addSelectionListener(new SelectionAdapter() {
			
			/***将radio放入group之后，每点击一次，都会触发两次事件
			 * 一次：之前被选择的按钮，会触发
			 * 二次：此次被选择的按钮，会触发
			 * */
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				Button btn = ((Button)e.getSource());
				if(currentBtn == btn){
					return;
				}
				currentBtn = btn;
				if(!btn.getSelection()){
					return ;
				}
				setBtnSelect(btn);
			}
		});
		if(autoSelect){
			setBtnSelect(btn);
		}
		btnComposite.setSize(700,btnComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		btnComposite.layout();
		scrolledComposite.layout();
		
	}
	
	/**
	 * 设置选择的按钮
	 * @param btn
	 */
	public void setBtnSelect(Button btn){
		for(Control control : btnComposite.getChildren()){
			Button c = (Button)control;
			c.setSelection(false);
		}
		btn.setSelection(true);
		me.layout();
		btnComposite.layout();
		currentBtn = btn;
		if(resultBack != null){
			resultBack.callBack(btn.getData("result"));
		}
		if(objBack != null){
			objBack.callBack(btn.getData("obj"));
		}
	}
	
	
	@Override
	protected void add(String name) {
		
		
		
	}
	/**
	 * 删除了名字为Obj的button，并刷新显示
	 * @param obj
	 */
	public void removeChildByName(Object obj){
		Control control = btnComposite.getChildByData("result",obj);
		control.dispose();
		
		if(btnComposite.getChildNum() != 0){
			setBtnSelect((Button)btnComposite.getFirstChild());
		}else{
			currentBtn = null;
		}
		
		layout();
	}
	
	/**
	 * 删除当前选择按钮
	 */
	public void removeSelect(){
		if(btnComposite.getChildNum() == 0){
			return ;
		}
		
		Button selectButton = null;
		for(Control control : btnComposite.getChildren()){
			Button c = (Button)control;
			if(c.getSelection()){
				selectButton = c;
				break;
			}
		}
		if(selectButton != null){
			removeShowAtNameSb(selectButton.getText());
			removeChildByName(selectButton.getData("result"));
		}
	}
	
	public INBack getObjBack() {
		return objBack;
	}
	public void setObjBack(INBack objBack) {
		this.objBack = objBack;
	}
	public boolean isAutoSelect() {
		return autoSelect;
	}
	public void setAutoSelect(boolean autoSelect) {
		this.autoSelect = autoSelect;
	}
	@Override
	protected void replaceItem(int index, String nexTxt) {
		Control control = btnComposite.getChildren()[index];
		Button btn = (Button)control;
		btn.setText(nexTxt);
		
	}
	@Override
	protected void clearChildItem() {
		
		for(Control control : btnComposite.getChildren()){
			control.dispose();
		}
		
	}
}

