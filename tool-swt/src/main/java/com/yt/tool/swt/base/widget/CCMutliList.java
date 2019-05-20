package com.yt.tool.swt.base.widget;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import com.yt.utils.algorithm.encrypt.CharUtils;
/**
 * @author cr.wu
 *
 * 2015年8月29日
 */
public class CCMutliList extends CCList implements IYtMutilWidget{
	/**
	 * @param parent
	 */
	public CCMutliList(Composite parent) {
		super(parent ,  SWT.V_SCROLL|SWT.BORDER |SWT.MULTI);
		
	}
	@Override
	public int getSelectIndex() {
		return list.getSelectionIndex();
	}
	
	/**
	 * 获取结果字段的数组
	 * @return
	 */
	public List<?> getResults(){
		int[] ints = getSelectIndices();
		List resultList = new ArrayList();
		for(int i = 0 ; i < ints.length ; i++){
			IYtWidgetFace object = getDataList().get(ints[i]);
			Object result = getResultByObject(object);
			resultList.add(result);
		}
		
		return resultList;
		
	}
	
	/**
	 * 获取被选中的对象数组
	 */
	public List<IYtWidgetFace> getResultObjects(){
		int[] ints = getSelectIndices();
		List<IYtWidgetFace> resultList = new ArrayList<IYtWidgetFace>();
		for(int i = 0 ; i < ints.length ; i++){
			IYtWidgetFace object = getDataList().get(ints[i]);
			resultList.add(object);
		}
		
		return resultList;
		
	}
	
	
	public int[] getSelectIndices(){
		return list.getSelectionIndices();
	}
	
	/**
	 * 根据show数组，设置选择的条目
	 * @param showString
	 */
	public void setSelectByShow(String[] showString){
		int[] ints = new int[showString.length];
		for(int i = 0 ; i < showString.length ;i++){
			int index = getIndexByShow(showString[i]);
			ints[i] = index;
		}
		list.setSelection(ints);
	}
	
	/**
	 * 根据result数组，设置选择的条目
	 * @param showString
	 */
	public void setSelectByResult(Object[] resultString){
		int[] ints = new int[resultString.length];
		for(int i = 0 ; i < resultString.length ;i++){
			int index = getIndexByResult(resultString[i]);
			ints[i] = index;
		}
		list.setSelection(ints);
	}
	
	
	public void setSelect(int[] ints){
		list.setSelection(ints);
	}
	
	
	
	@Override
	public void setSelectIndex(int index) {
		list.setSelection(index);
	}
	@Override
	protected void add(String txt) {
		list.add(txt);
	}
	public void flushSelectText(String txt){
		int index = list.getSelectionIndex();
		IYtWidgetFace selectObj = getDataList().get(index);
		try {
			selectObj.setShow(txt);
			String oldTxt = list.getItem(index);
			list.remove(index);
			list.add(txt, index);
			replaceNameAtNameSb(oldTxt , txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String getResultString() {
		int[] ints = getSelectIndices();
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < ints.length ; i++){
			IYtWidgetFace object = getDataList().get(ints[i]);
			Object result = getResultByObject(object);
			sb.append(result+",");
		}
		return CharUtils.delEndChar(sb.toString());
	}
	
	
}

