package com.yt.tool.swt.ui.table.util;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import com.yt.tool.swt.base.SwtVoid;
import com.yt.tool.swt.ui.table.provider.TableBaseContentProvider;
import com.yt.tool.swt.ui.table.provider.TableBaseLabelProvider;
import com.yt.tool.swt.ui.table.provider.TableBaseModify;
import com.yt.tool.swt.ui.table.provider.TableBaseTableSorter;
import com.yt.utils.algorithm.RegexUtils;
/**
 * @author cr.wu
 *
 * 2015-8-2
 */
public class TableUtil {
	
	/**
	 * 处理tableViewer，使之具有默认排序，多行，自适应宽度，可编辑功能
	 * @param tableViewer
	 * @param classObj tableViewer装载的数据类型
	 */
	public static void setCommon(final TableViewer tableViewer , Class<?> classObj){
		
		String[] names = TablePropertyData.get(classObj).get(TablePropertyData.column_name).toString().split("\\|");
		for(int i = 0;i<names.length;i++){
			TableColumn t = new TableColumn(tableViewer.getTable(),SWT.LEFT);
			//宽度判断
			String flag = RegexUtils.getField(names[i], "(?i)\\{w=(\\d+)\\}", 1);
			if(flag != null){
				
				int width = Integer.valueOf(flag);
				t.setWidth(width);
				String name = RegexUtils.getField(names[i], "(?i)(.*?)\\{", 1);
				t.setText(name);
			}else{
				t.setText(names[i]);
				t.setWidth(80);
			}
			t.setData(i);
			
			
			
			t.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e){
					if(tableViewer.getSorter() == null){
						return ;
					}
					int index = Integer.valueOf( e.widget.getData()+"");
					((TableBaseTableSorter)tableViewer.getSorter()).doSort(index);
					tableViewer.refresh();
					
				}
			});
		}
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
		doTable(tableViewer.getTable());
		
		tableViewer.setLabelProvider(new TableBaseLabelProvider());
		tableViewer.setContentProvider(new TableBaseContentProvider());
		tableViewer.setSorter(new TableBaseTableSorter());
		if(TablePropertyData.get(classObj).get(TablePropertyData.can_modify) == null){
			return ;
		}
		tableViewer.setCellModifier(new TableBaseModify(tableViewer, classObj));
	}
	
	/**
	 * 设置table具有多行，自适应宽度功能
	 * @param table
	 */
	public static void doTable(Table table) {
		Listener paintListener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				//最大列宽,多行，column maxWidth,multi
					case SWT.MeasureItem: {
						TableItem item = (TableItem) event.item;
						String text = getText(item, event.index);
						Point size = event.gc.textExtent(text);
						if (size.x > 300) {
							size.x = 300;
						}
						event.width = size.x;
						event.height = Math.max(event.height, size.y);
						break;
					}
					
					//设置被选中区域完整
					case SWT.PaintItem: {
						TableItem item = (TableItem) event.item;
						String text = getText(item, event.index);
						Point size = event.gc.textExtent(text);
						int offset2 = event.index == 0 ? Math.max(0, (event.height - size.y) / 2) : 0;
						event.gc.drawText(text, event.x, event.y + offset2, true);
						break;
					}
					case SWT.EraseItem: {
						event.detail &= ~SWT.FOREGROUND;
						break;
					}
				}
			}
			String getText(TableItem item, int column) {
				return item.getText(column);
			}
		};
		table.addListener(SWT.MeasureItem, paintListener);
		table.addListener(SWT.PaintItem, paintListener);
		table.addListener(SWT.EraseItem, paintListener);
	}
	
	/**
	 * 获取table中，某一列的值，用|隔开
	 * */
	public static String getTextAtIndex(Table table , int index){
		TableItem[] items = table.getItems();
		StringBuilder sb = new StringBuilder();
		for(TableItem item : items){
			sb.append(item.getText(index)+"|");
		}
		return sb.toString();
	}
	
}

