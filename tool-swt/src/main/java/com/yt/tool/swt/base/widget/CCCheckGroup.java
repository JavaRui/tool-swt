package com.yt.tool.swt.base.widget;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import com.yt.tool.swt.base.YtWidget;
import com.yt.utils.algorithm.encrypt.CharUtils;
/**
 * @author cr.wu
 *
 * 2015年8月31日
 */
public class CCCheckGroup extends YtWidget implements IYtMutilWidget{
	private Button fillBtn;
	
	protected List<Button> btns;
	
	public CCCheckGroup(Composite parent) {
		super(parent);
		btns = new ArrayList<Button>();
		addFillBtn();
	}
	/**
	 *@deprecated 在多选的控件中，此方法不能只能返回第一个选中的按钮的index
	 * 如果要获取所有的选中状态，请使用getSelectIndices
	 */
	@Override
	public int getSelectIndex() {
		for(int i = 0 ; i < btns.size() ; i ++){
			if(btns.get(i).getSelection()){
				return i;
			}
		}
		return -1;
	}
	@Override
	public void addList(List lists) {
		super.addList(lists);
//		setChildBtnSelect(false);
	}
	
	/**
	 * 获取选中下标的数组
	 * @return
	 */
	public Integer[] getSelectIndices(){
		List<Integer> intList = new ArrayList<Integer>();
		for(int i = 0 ; i < btns.size() ; i ++){
			if(btns.get(i).getSelection()){
				intList.add(i);
			}
		}
		Integer[] t = new Integer[intList.size()];
		return (Integer[]) intList.toArray(t);
	}
	
	@Override
	public void setSelectIndex(int index) {
		btns.get(index).setSelection(true);
	}
	/**
	 * 添加全选按钮
	 */
	public void addFillBtn(){
		fillBtn = new Button(this, SWT.CHECK);
		fillBtn.setText("全选");
		fillBtn.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
					setChildBtnSelect(true);
			}
			
		});
	}
	
	public void removeFillBtn(){
		fillBtn.dispose();
		fillBtn = null;
	}
	
	@Override
	public void addShow(IYtWidgetFace object) {
		super.addShow(object);
		Button btn = new Button(this, SWT.CHECK);
		btn.setText(getShowByObject(object));
		btn.setData(object);
		btn.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				int selectNum = 0;
				for(int i = 0 ; i < btns.size() ; i++){
					if(btns.get(i).getSelection()){
						selectNum++;
					}
				}
				if(selectNum == btns.size()){
					fillBtn.setSelection(true);
				}
			}
			
		});
		btns.add(btn);
		
	}
	
	@Override
	protected void add(String txt) {
	}
	
	@Override
	public List<?> getResults() {
		List<IYtWidgetFace> objects = (List<IYtWidgetFace>) getResultObjects();
		List<Object> resultList = new ArrayList<Object>(objects.size());
		for(int i = 0 ; i < objects.size() ; i++){
			
			IYtWidgetFace tObject = objects.get(i);
			resultList.add(getResultByObject(tObject));
			
		}
		return resultList;
	}
	
	@Override
	public List<IYtWidgetFace> getResultObjects() {
		List<IYtWidgetFace> objects = new ArrayList<IYtWidgetFace>();
		for(int i = 0 ; i < btns.size() ; i++){
			if(btns.get(i).getSelection()){
				
				IYtWidgetFace tObject = (IYtWidgetFace) btns.get(i).getData();
				objects.add(tObject);
			}
		}
		return objects;
	}
	@Override
	public void setSelectByShow(String[] showString) {
		setChildBtnSelect(false);
		for(int i = 0 ; i < showString.length ;i++){
			int index = getIndexByShow(showString[i]);
			setSelectIndex(index);
		}
		
	}
	@Override
	public void setSelectByResult(Object[] resultString) {
		setChildBtnSelect(false);
		for(int i = 0 ; i < resultString.length ;i++){
			int index = getIndexByResult(resultString[i]);
			setSelectIndex(index);
		}
		
	}
	
	public void checkFull(){
		for(Button btn : btns){
			if(btn.getEnabled()&&!btn.getSelection()){
				fillBtn.setSelection(false);
				return ;
			}
		}
		fillBtn.setSelection(true);
	}
	@Override
	protected void replaceItem(int index, String nexTxt) {
		btns.get(index).setText(nexTxt);
	}
	@Override
	protected void clearChildItem() {
		for(Button btn : btns){
			btn.dispose();
		}
		btns.clear();
	}
	@Override
	public String getResultString() {
		List<IYtWidgetFace> objects = (List<IYtWidgetFace>) getResultObjects();
		StringBuilder sbBuilder = new StringBuilder();
		List<Object> resultList = new ArrayList<Object>(objects.size());
		for(int i = 0 ; i < objects.size() ; i++){
			
			IYtWidgetFace tObject = objects.get(i);
			resultList.add(getResultByObject(tObject));
			sbBuilder.append(getResultByObject(tObject)+",");
			
		}
		return CharUtils.delEndChar(sbBuilder.toString());
	}
	
}

