package com.yt.tool.swt.base;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.yt.common.xxx;
import com.yt.tool.swt.ui.text.YtText;
import com.yt.tool.swt.util.YtColorUtil;
/**
 * @author cr.wu
 *
 * 2015年7月30日
 */
public class YtComposite extends Composite {
	protected YtComposite me;
	
	
	public YtComposite(Composite parent, int style) {
		super(parent, style);
		
		me = this;
		addPaint();
	}
	
	public YtComposite(Composite parent) {
		this(parent, 0);
	}
	
	/**
	 * 根据class获取子控件列表
	 * @param cls
	 * @return
	 */
	public List<Control> getChildByClass(Class<?> cls){
		List<Control> csList = new ArrayList<Control>();
		Control[] cs = getChildren();
		for(int i = 0 ; i < cs.length ; i++ ){
			if(cs[i].getClass().getSimpleName().equals(cls.getSimpleName()) ){
				csList.add(cs[i]);
			}
		}
		return csList;
	} 
	
	public List<Control> getChildByKey(String key){
		List<Control> csList = new ArrayList<Control>();
		Control[] cs = getChildren();
		for(int i = 0 ; i < cs.length ; i++ ){
			Object obj = cs[i].getData(key);
			if(obj != null) {
				csList.add(cs[i]);
			}
			
		}
		return csList;
	} 
	
	
	
	/**
	 * 创建一个label
	 * @param txt
	 * @return
	 */
	public Label createLabel(String txt) {
		return createLabel(txt,0);
	}
	
	/**
	 * 创建一个label
	 * @param txt
	 * @return
	 */
	public Label createLabel(String txt , int style) {
		Label label = new Label(this, style);
		label.setText(txt);
		return label;
	}
	
	
	/**
	 * @param w
	 * @param h 标准为18
	 * @return
	 */
	public YtText createText(int w , int h){
		YtText text = new YtText(this , SWT.BORDER	);
		GridData gd = new GridData(SWT.FILL,SWT.CENTER,false,false);
		gd.heightHint = h;
		gd.widthHint = w;
		text.setLayoutData(gd);
		
		return text;
	}
	
	/**
	 * 新建充满且不抢占的文本
	 * */
	public YtText createFillText(){
		YtText text = new YtText(this);
		GridData gd = new GridData(SWT.FILL,SWT.CENTER,false,false);
		
		text.setLayoutData(gd);
		return text;
	}
	
	/**
	 * 创建一个有边框的文本
	 * @return
	 */
	public YtText createText(){
		YtText text = new YtText(this , SWT.BORDER);
		
		return text;
	}
	
	/**
	 * 创建label和text
	 * @param labelText
	 * @return
	 */
	public YtText createTextAndLabel(String labelText){
		createLabel(labelText);
		return createText();
	}
	
	
	/**
	 * 输出
	 * @param text
	 */
	public void lll(Object text){
		xxx.log(me.getClass(),text+"");
	}
	
	/**
	 * 根据getData("name")获取相应的子控件 能获取的子控件，必须有名字
	 * 
	 * @param data
	 *            名字
	 * */
	public Control getChildByName(Object data) {
		
		return getChildByData("name",data);
	}
	
	/**
	 * 根据子控件层次获取子控件
	 * @param index
	 * @return
	 */
	public Control getChildIndex(int index){
		return getChildren()[index];
	}
	
	/**
	 * 获取第一个子控件
	 * @return
	 */
	public Control getFirstChild(){
		return getChildren()[0];
	}
	
	/**
	 * 添加绘图事件
	 */
	private void addPaint(){
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				
				afterInit();
				removePaintListener(this);
			}
		});
	}
	
	/**
	 * 绘图事件执行的界面，可用于复写，在此方法执行的时候，所有的控件都将被分配了bound。可以直接设置bound
	 */
	protected void afterInit() {
		
	}
	/**
	 * 获取子控件数量
	 * */
	public int getChildNum(){
		return getChildren().length;
	}
	
	
	/**
	 * 设置成gridlayout
	 */
	public void setGridLayout(){
		setLayout(new GridLayout());
	}
	
	/**
	 * 封装了setLayout(new GridLayout(grid , b));
	 * @param grid
	 * @param b
	 */
	public void setGridLayout(int grid , boolean b){
		setLayout(new GridLayout(grid , b));
	}
	
	/**
	 * 设置充满式布局
	 */
	public void setFillLayout(){
		setLayout(new FillLayout());
	}
	
	/**
	 * 设置griddata样式
	 * @param hor 横向是否充满
	 * @param ver 竖向是否充满
	 */
	public void setGd(boolean hor , boolean ver){
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, hor, ver);
		me.setLayoutData(gridData);
		
		
	}
	
	
	/**
	 * 根据子控件的属性值配对，获取子控件
	 * */
	public Control getChildByData(String dataName , Object dataValue){
		Control[] controls = this.getChildren();
		for (int i = 0; i < controls.length; i++) {
			if (dataValue.equals(controls[i].getData(dataName) )) {
				return controls[i];
			}
		}
		return null;
	}
	/**
	 * 设置子控件是否接收鼠标事件
	 * */
	public void setChildEnabled(boolean enabled) {
		Control[] controls = this.getChildren();
		for (int i = 0; i < controls.length; i++) {
			controls[i].setEnabled(enabled);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		setChildEnabled(enabled);
		super.setEnabled(enabled);
	}
	
	/**
	 * 设置子控件是否接收鼠标事件
	 * */
	public void setChildVisible(boolean enabled) {
		Control[] controls = this.getChildren();
		for (int i = 0; i < controls.length; i++) {
			controls[i].setVisible(enabled);
		}
	}
	/**
	 * 设置所有的按钮的选中状态
	 * */
	public void setChildBtnSelect(boolean select) {
		Control[] controls = this.getChildren();
		for (int i = 0; i < controls.length; i++) {
			if(controls[i] instanceof Button){
				Button btn = (Button) controls[i];
				btn.setSelection(select);
			}
		}
	}
	
	public void removeAllChild(){
		SwtVoid.removeAllChild(me);
	}
	
	/**
	 * 获取子控件实际的尺寸，设置为显示的尺寸
	 * */
	public void autoSize() {
		Point xy = getRealSize();
		setSize(xy);
	}
	/**
	 * 获取真实的尺寸，但有可能因为被同级控件重叠，导致尺寸减少，具体原因还不是很清楚。
	 * */
	public Point getRealSize(){
		Point xy = computeSize(SWT.DEFAULT, SWT.DEFAULT);
		return xy;
	}
	/**
	 * 设置gridlayout，且layout的数量是子控件的数量
	 * */
	public void setGridLayoutByChildren(){
		setGridLayoutByChildren( true);
	}
	/**
	 * 设置gridlayout，且layout的数量是子控件的数量,且宽度不会强制相同
	 * */
	public void setGridLayoutByChildren(boolean b){
		setLayout(new GridLayout(getChildren().length, b));
	}
	
	
	
	
}

