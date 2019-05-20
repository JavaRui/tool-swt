package com.yt.tool.swt.ui.table.provider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import com.yt.tool.swt.ui.table.util.TableReflectUtil;
/**
 * @author cr.wu
 *
 * 2015-8-2
 */
public class TableBaseLabelProvider implements ITableLabelProvider{
	
	@Override
	public void addListener(ILabelProviderListener listener) {
		
	}
	@Override
	public void dispose() {
		
	}
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}
	@Override
	public void removeListener(ILabelProviderListener listener) {
		
	}
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		
		
		return null;
	}
	
	@Override
	public String getColumnText(Object element, int columnIndex) {
		
		return TableReflectUtil.getValueByColumn(element, columnIndex)+"";
		
	}
}

