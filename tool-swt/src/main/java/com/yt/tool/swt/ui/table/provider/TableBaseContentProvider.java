package com.yt.tool.swt.ui.table.provider;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
/**
 * @author cr.wu
 *
 * 2015-8-2
 */
public class TableBaseContentProvider implements IStructuredContentProvider{
	@Override
	public void dispose() {
		
	}
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return ((List)inputElement).toArray();
	}
	
}

