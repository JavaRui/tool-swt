package com.yt.tool.swt.ui.table.provider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Item;
import com.yt.tool.swt.ui.table.event.ModifyTableEvent;
import com.yt.tool.swt.ui.table.face.ModifyFace;
import com.yt.tool.swt.ui.table.util.TablePropertyData;
import com.yt.tool.swt.ui.table.util.TableReflectUtil;
import com.yt.utils.reflect.ReflectUtil;
/**
 * @author cr.wu
 *
 * 2015-8-2
 */
public class TableBaseModify implements ICellModifier{
	private String[] names;
	private String[] canModify;
	private TableViewer tableViewer;
	
	/**table值被修改之后，抛出事件**/
	public static final int TABLE_MODIFY = 1001;
	
	
	/**
	 * @param tableViewer
	 * @param classObj
	 */
	public TableBaseModify(TableViewer tableViewer , Class<?> classObj){
		this.tableViewer = tableViewer;
		
		init(classObj);
	}
	
	private void init(Class<?> element){
		names = TablePropertyData.get(element).get(TablePropertyData.column_name).toString().split("\\|");
		tableViewer.setColumnProperties(names);
		//设置可编辑的columnIndex
		canModify = TablePropertyData.get(element).get(TablePropertyData.can_modify).toString().split("\\|");
		CellEditor[] editors = new CellEditor[names.length];
		
		//设置为文本编辑器
		for(String can : canModify){
			editors[Integer.valueOf(can)] = new TextCellEditor(tableViewer.getTable());
		}
		tableViewer.setCellEditors(editors);
	}
	
	@Override
	public void modify(Object element, String property, Object value) {
		if(element instanceof Item){
			element = ((Item)element).getData();
		}
		try {
			//通过文字，获取对应的属??
			String fieldName = TableReflectUtil.getFieldNameByColumuName(element, property);
			
			Object oldValue = ReflectUtil.getValueByFieldName(element, fieldName);
			
			if(oldValue.equals(value)){
				return ;
			}
			//设置输入的??
			ReflectUtil.setValueByFieldName(element, fieldName, value);
			
			int index = TableReflectUtil.getColumnNameByFieldName(element, fieldName);
			
			
			ModifyTableEvent event = new ModifyTableEvent();
			event.fieldName = fieldName;
			event.afterValue = value;
			event.element = element;
			event.index = index;
			
			if(element instanceof ModifyFace){
				((ModifyFace)element).modify(fieldName, value);
			}
			tableViewer.refresh();
			tableViewer.getTable().notifyListeners(TABLE_MODIFY, event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 编辑之前获取??
	 * */
	@Override
	public Object getValue(Object element, String property) {
		//通过文字，获取对应的属??
		String fieldName = TableReflectUtil.getFieldNameByColumuName(element, property);
		
		return TableReflectUtil.getValueByFieldName(element, fieldName)+"";
	}
	
	@Override
	public boolean canModify(Object element, String property) {
		
		return true;
	}
}

