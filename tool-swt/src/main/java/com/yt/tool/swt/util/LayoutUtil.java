package com.yt.tool.swt.util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import com.yt.tool.swt.image.ImageFoctory;
import com.yt.utils.pc.SysUtil;
/**
 * 用于设置常用的layout功能，如果创建常用的gridData等等
 * @author cr.wu
 * @version 1.0.1
 * @category swt扩展功能模块
 * 
 * */
public class LayoutUtil {
	
	/** 设置控件居中
	 * @param c 任何控件
	 * */
	public static void setCenter(Control c){
		c.setLocation(Display.getCurrent().getClientArea().width / 2
				- c.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - c.getSize().y / 2);
	}
	/** 设置控件居中
	 * @param c 任何控件
	 * */
	public static void setCenterByRealSize(Control c){
		Point p = c.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		
		c.setLocation(Display.getCurrent().getActiveShell().getSize().x / 2
				- p.x / 2, Display.getCurrent()
				.getActiveShell().getSize().y / 2 - p.y / 2);
	}
	
	/** 设置控件居中
	 * @param c 任何控件
	 * */
	public static void setCenter(Composite parent , Composite child){
		Point lp = getCenterPoint(parent , child);
		child.setLocation(lp);
	}
	
	
	/**
	 * 设置控件居中，适用于shell中的shell
	 * 使用之前，请确认两个shell都存在size
	 * @param shell 如果传 null ，会自动转成，当前的活动shell
	 * @param targetShell 要设置坐标的shell
	 * */
	public static void setCenter(Shell shell , Shell targetShell){
		if(shell == null){
			shell = Display.getDefault().getActiveShell();
		}
		Point p = targetShell.getSize();
		Point pp = new Point(shell.getLocation().x + shell.getSize().x / 2 - p.x / 2, shell.getLocation().y + shell.getSize().y / 2 - p.y / 2);
		targetShell.setLocation(pp.x, pp.y);
	}
	
	public static Point getCenterPoint(Control mainControl , Control baseControl){
		Point p = baseControl.getSize();
		Point lp = new Point(mainControl.getSize().x / 2- p.x / 2, mainControl.getSize().y / 2 - p.y / 2);
		return lp;
	}
	
	
	/**
	 * 创建一个空的占领girdSpan的grid的label，用于撑开
	 * */
	public static void createEmptyLabel(Composite c,int gridSpan){
		new Label(c, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, gridSpan, 1));
	}
	/**
	 * 创建一个样式为充满，并自动抢占的griddata
	 * */
	public static GridData createFillGrid(){
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = gd.verticalAlignment = SWT.FILL;
		
		return gd;
	}
	/**
	 * 创建样式为充满，且全局占满，占有gridCount个数个griddata
	 * @param gridCount 水平方向的占用grid数量
	 * @return GridData
	 * */
	public static GridData createFillGrid(int gridCount){
		GridData gd = createFillGrid();
		gd.horizontalSpan = gridCount;
		return gd;
	}
	
	/**
	 * 创建样式为充满，且水平占满，占有gridCount个数个griddata
	 * @param gridCount 水平方向的占用grid数量
	 * @return GridData
	 * */
	public static GridData createFillGridNoFill(int gridCount){
		GridData gd = createFillGridNoVer(gridCount);
		return gd;
	}
	
	/**
	 * 创建样式为充满，且水平占满，占有gridCount个数个griddata
	 * @param gridCount 水平方向的占用grid数量
	 * @return GridData
	 * */
	public static GridData createFillGridNoVer(int gridCount){
		GridData gd = createFillGrid(gridCount);
		gd.grabExcessVerticalSpace = false;
		return gd;
	}
	
	/**
	 * 创建一个单独的toolItem，由composite包装住
	 * */
	public static ToolItem createToolItem(Composite parent,String imagePath){
		
		ToolBar toolBar = new ToolBar(parent, 8388608);
		
		ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
		toolItem.setImage(ImageFoctory.getImg(imagePath));
		
		return toolItem;
	}
	/**
	 * 为菜单添加分割线
	 * */
	public static void addSep(Menu menu){
		new MenuItem(menu, SWT.SEPARATOR);
	}
	/**
	 * 设置与本来相反的显示
	 * */
	public static void changeExclude(Control c){
		GridData gd = null;
		if(c.getLayoutData() == null){
			gd = new GridData();
		}else{
			gd = (GridData)c.getLayoutData();
		}
		gd.exclude = !gd.exclude;
		c.setLayoutData(gd);
		c.setVisible(!gd.exclude);
	}
	
	/**
	 * 设置显示方式
	 * */
	public static void setExclude(Control c , boolean b){
		GridData gd = null;
		if(c.getLayoutData() == null){
			gd = new GridData();
		}else{
			gd = (GridData)c.getLayoutData();
		}
		gd.exclude = !b;
		c.setLayoutData(gd);
		c.setVisible(b);
		c.getParent().layout();
	}
	
	/**
	 * 创建一个link且带有文字和链接
	 * @param composite
	 * @param net 链接地址
	 * @param text 提示文本
	 */
	public static void createLink(Composite composite , String net , String text){
		Link link = new Link(composite, 64);
		link.setText("<a href=\""+net+"/\">  "+text+"</a>");
		link.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.err.println(arg0.text);
				String url = arg0.text;
				SysUtil.openURL(url);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	
}

