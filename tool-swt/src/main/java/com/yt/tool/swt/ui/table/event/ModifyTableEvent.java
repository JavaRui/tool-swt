package com.yt.tool.swt.ui.table.event;
import org.eclipse.swt.widgets.Event;
/**
 * @author cr.wu
 *
 * 2015年8月6日
 */
public class ModifyTableEvent extends Event{
	public String fieldName;
	
	public Object afterValue;
	
	
	public Object element;
	public int index;
	@Override
	public String toString() {
		return "MofityTableEvent [fieldName=" + fieldName + ", afterValue=" + afterValue + ", element=" + element + ", index=" + index + "]";
	}
	
	
	
	
}

