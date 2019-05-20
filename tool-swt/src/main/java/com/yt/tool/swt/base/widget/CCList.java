package com.yt.tool.swt.base.widget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import com.yt.tool.swt.base.YtWidget;
import com.yt.utils.algorithm.ShowUtil;
/**
 * @author cr.wu
 *
 * 2015年8月29日
 */
public class CCList extends YtWidget{
	protected List list;
	/**
	 * style是设置list的样式，默认SWT.V_SCROLL|SWT.BORDER
	 * */
	public CCList(Composite parent ) {
		super(parent);
		list = new List(this,  SWT.V_SCROLL|SWT.BORDER );
		
	}
	
	public CCList(Composite parent , int style) {
		super(parent);
		list = new List(this,  style );
	}
	@Override
	public int getSelectIndex() {
		return list.getSelectionIndex();
	}
	@Override
	public void setSelectIndex(int index) {
		list.setSelection(index);
	}
	@Override
	protected void add(String txt) {
		list.add(txt);
	}
	
	public void removeSelect(){
		if(list.getSelectionIndices().length< 0){
			return;
		}
		int[] ints = list.getSelectionIndices();
		ShowUtil.show(ints);
		java.util.List<IYtWidgetFace> lllList = getDataList(); 
		ShowUtil.show(lllList);
		for(int i = ints.length -1; i >=0 ; i--){
			IYtWidgetFace object = lllList.get(ints[i]);
			lll(object);
			String name = getShowByObject(object);
			removeShowAtNameSb(name);
			lllList.remove(ints[i]);
		}
		
		list.remove(list.getSelectionIndices());
	}
	@Override
	protected void replaceItem(int index, String nexTxt) {
		
		list.remove(index);
		list.add(nexTxt, index);
		
	}
	@Override
	protected void clearChildItem() {
		list.removeAll();
	}
}

