package com.yt.tool.swt.ui.text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import com.yt.tool.swt.keyboard.KeyCode;
import com.yt.tool.swt.util.LayoutUtil;
import com.yt.tool.swt.util.YtColorUtil;
import com.yt.tool.swt.util.YtFontUtil;
/**
 * @author cr.wu
 *
 * 2015年9月16日
 */
public class YtText extends Text{
	public final int SELECT_ALL = 1;
	public int ytStyle = 0;
	
	
	public YtText(Composite parent, int style) {
		super(parent, style);
//		setBackground(YtColorUtil.whiteColor);
//		Font font = YtFontUtil.getFontNor(18);
//		setFont(font);
		this.addKeyListener(keyListener);
	}
	
	public YtText(Composite parent) {
		this(parent, SWT.BORDER  | SWT.WRAP | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		setKBSelectAll();
	}
	/**
	 * 键盘事件
	 * */
	private KeyListener keyListener = new KeyListener() {
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			boolean ctrl = false;
			boolean shift = false;
			if( ( arg0.stateMask & SWT.CTRL ) != 0 ){
				ctrl = true;
			}
			if( ( arg0.stateMask & SWT.SHIFT ) != 0 ){
				shift = true;
			}
			//全选
			if( ctrl && arg0.keyCode == KeyCode.KEY_A ){
				
				if( ( ytStyle & SELECT_ALL ) != 0 ){
					selectAll();
				}
			}
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
		}
	};
	
	@Override
	protected void checkSubclass() {
		
	}
	
	public void setGdSize( int w , int h ){
		GridData gd = (GridData)getLayoutData();
		if(gd == null){
			gd = new GridData();
		}
		gd.widthHint = w;
		gd.heightHint = h;
		setLayoutData(gd);
		getParent().layout();
	}
	/**
	 * 全选功能
	 * */
	public void setKBSelectAll(){
		ytStyle = ytStyle | SELECT_ALL;
	}
	
	public void setGdFill( boolean hor , boolean ver){
		GridData gd = new GridData(SWT.FILL, SWT.FILL, hor, ver);
		setLayoutData(gd);
	}
	
	
}

