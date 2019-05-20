package com.yt.tool.swt.ui.table.ui;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import com.yt.common.xxx;
import com.yt.tool.swt.ui.table.util.TableUtil;
import com.yt.utils.notify.INBack;
import com.yt.utils.reflect.ReflectUtil;
/**
 * @author cr.wu
 *
 * *****************************
 * TableProviderConfig.addData();
 * 
 * List list = new ArrayList<InOrderDesc>();
		for(int i = 0 ; i < 10 ; i++){
			InOrderDesc gb = new InOrderDesc();
			gb.setInOrderId(i);
			gb.setNo(r.getEnRandom(5, 10));
			gb.setSupName(r.getChRandom(1, 3));
			gb.setAllMoney(Float.valueOf(r.getNumFixed(4))/100);
			list.add(gb);
		}
		
		YtCheckBoxTable tvBase = YtCheckBoxTable.newCheckBox(shell, InOrderDesc.class);
		tvBase.setEoList(list);
	*****************************
 * 
 * 
 */
public class YtCheckBoxTable extends CheckboxTableViewer{
	
	protected List<Object> eoList;
	private YtCheckBoxTable checkBoxTable;
	private Class<?> paramBaseClass;
	private INBack selectBack;
	
	protected YtCheckBoxTable(Table parent , Class<?> paramBase ) {
		 
	    super(parent);
	    checkBoxTable = this;
	    eoList = new ArrayList<Object>();
	    TableUtil.setCommon(this, paramBase);
	    setInput(eoList);
	    paramBaseClass = paramBase;
	    parent.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Table table = getTable();
				TableItem items[] = table.getSelection();
				boolean flag = true;
				for(TableItem item : items){
					item.setChecked(!item.getChecked());
					if(item.getChecked() && flag){
						if(selectBack == null){
							continue;
						}
						flag = false;
						int index = table.getSelectionIndex();
						Object obj = YtCheckBoxTable.this.getElementAt(index);
						xxx.log(YtCheckBoxTable.class, obj);
						selectBack.callBack(obj);
					}
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	   
	}
	/**
	 * 带有复选框的YtCheckBoxTable
	 * 
	 * */
	public static YtCheckBoxTable newCheckBox(Composite parent , Class<?> paramBase){
		Table table = new Table(parent, SWT.FULL_SELECTION|SWT.CHECK|SWT.MULTI);
		return new YtCheckBoxTable(table, paramBase);
	}
	/**
	 * 普通的tableViewer
	 * 
	 * */
	public static YtCheckBoxTable newSimpleTable(Composite parent , Class<?> paramBase){
		Table table = new Table(parent, SWT.FULL_SELECTION|SWT.MULTI);
		return new YtCheckBoxTable(table, paramBase);
	}
	
	/**
	 * 复写了add方法，使调用此方法的时候，都会调用添加到eoList去
	 */
	public void add(Object obj){
		if(obj == null){
			return ;
		}
		add((new Object[] { obj }));
	}
	/**
	 * 复写了add方法，使调用此方法的时候，都会调用添加到eoList去
	 */
	public void add(Object[] obj){
		if(obj == null){
			return ;
		}
		for(int i = 0 ; i < obj.length ;i ++){
			eoList.add(obj[i]);
		}
		super.add(obj);
	}
	
	/**
	 * 删除复选框选中的对象
	 * */
	public void delCheck(){
		Object[] objs = getCheckedElements();
		if(objs == null  || objs.length == 0){
			return ;
		}
		for(Object obj : objs){
			eoList.remove(obj);
		}
		this.remove(objs);
		
	}
	/**
	 * 删除选择的对象
	 * */
	public void delSelect(){
		TableItem[] items = getTable().getSelection();
		for( int i = 0 ; i<items.length ; i++ ){
			Object data = items[i].getData();
			eoList.remove(data);
			remove(data);
		}
		
	}
	
	/**
	 * 清除所有元素
	 * */
	public void clear(){
		eoList.clear();
		checkBoxTable.getTable().removeAll();
		checkBoxTable.setInput(null);
		checkBoxTable.setInput(eoList);
		
	}
	/***
	 * 获取复选框所选的对象
	 * 
	 * @return
	 */
	public List<?> getCheck(){
		Object[] objs = checkBoxTable.getCheckedElements();
		List<Object> list = new ArrayList<Object>(objs.length);
		for(int i = 0 ; i < objs.length ; i++){
			list.add(objs[i]);
		}
		return list;
	}
	/**
	 * 获取阴影所选的对象
	 * */
	public List<?> getSelect(){
		List<Object> list = new ArrayList<Object>();
		TableItem[] items = getTable().getSelection();
		for( int i = 0 ; i<items.length ; i++ ){
			Object data = items[i].getData();
			list.add(data);
		}
		return list;
	}
	
	
	/**
	 * 通过fieldName的值，若与tag,相等替换checkBoxTable的某对象
	 * 即判断list中所有的对象的fieldName对应的属性值，与tag比较，如果相同，替换
	 * 
	 * @param tag 标志的值 
	 * @param okObj 要替换的对象
	 * @param fieldName 要替换的fieldName
	 */
	public void replaceObjByField(Object tag , Object newObj , String fieldName){
		if(newObj == null){
			return ;
		}
		List list = eoList;
		for(int i = 0 ; i < list.size() ; i++){
			Object tObj = list.get(i);
			Object r = ReflectUtil.getValueByFieldName(tObj, fieldName);
			if(r.equals(tag)){
				list.remove(tObj);
				list.add(i, newObj);
				setInput(list);
//				replace(newObj , i);
				break;
				
			}
		}
		
	}
	
	
	
	/**
	 * 不排序，当数据量太多时，排序会很低效
	 * return ViewerSorter;
	 * */
	public ViewerSorter removeSort(){
		ViewerSorter sorter = getSorter();
		setSorter(null);
		return sorter;
	}
	
	/**
	 * 删除可编辑功能
	 * return ICellModifier;
	 */
	public ICellModifier removeCellModify(){
		ICellModifier cellModify =  getCellModifier();
		setCellModifier(null);
		return cellModify;
	}
	
	public List<Object> getEoList() {
		return eoList;
	}
	
	public <T> void setEoList(List<T> eoList) {
		this.eoList = (List<Object>) eoList;
		checkBoxTable.setInput(eoList);
	}
	
	public <T> void addEoList(List<T> tempList){
		this.eoList.addAll(tempList);
		checkBoxTable.setInput(eoList);
	}
	
	
	
	
	public void setLayoutData(Object layoutData){
		getTable().setLayoutData(layoutData);
	}
	public INBack getSelectBack() {
		return selectBack;
	}
	public void setSelectBack(INBack selectBack) {
		this.selectBack = selectBack;
	}
	
	
}

