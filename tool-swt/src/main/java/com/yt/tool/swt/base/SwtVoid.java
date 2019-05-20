package com.yt.tool.swt.base;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.yt.common.xxx;
import com.yt.utils.notify.INBack;
import com.yt.utils.pc.SysUtil;
public class SwtVoid {
	/**
	 * ui同步线程更新，会造成阻塞
	 * */
	public static void delaySyn(final int delay, final Runnable runnable) {
		if (delay == 0) {
			Display.getDefault().syncExec(runnable);
			return;
		}
		new Thread(new Runnable() {
			// 开辟新线程
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
					// e1.printStackTrace();
				}
				Display.getDefault().syncExec(runnable);
			}
		}).start();
	}
	/**
	 * ui异步线程更新，不会阻塞,该方法会等待调用此方法的方法体的功能全部运行完成之后，才会执行
	 * */
	public static void delayAsy(final int delay, final Runnable runnable) {
		if (delay == 0) {
			Display.getDefault().asyncExec(runnable);
			return;
		}
		new Thread(new Runnable() {
			// 开辟新线程
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e1) {
					// e1.printStackTrace();
				}
				Display.getDefault().asyncExec(runnable);
			}
		}).start();
	}
	/** 将内容放到剪切板 */
	public static void addCopy(String content) {
		if (content.length() == 0) {
			xxx.log(SwtVoid.class, "复制的长度为0，返回");
			return;
		}
		Clipboard clipboard = new Clipboard(Display.getCurrent());
		String plainText = content;
		String rtfText = "{\\rtf1\\b " + content + "}";
		TextTransfer textTransfer = TextTransfer.getInstance();
		RTFTransfer rftTransfer = RTFTransfer.getInstance();
		clipboard.setContents(new String[] { plainText, rtfText }, new Transfer[] { textTransfer, rftTransfer });
		clipboard.dispose();
	}
	/**
	 * 添加文件拽入功能
	 * 路径会传入INBack中
	 * @param comp
	 * @param iSimpleCallBack 
	 */
	public static void addDrop(Composite comp, final INBack iSimpleCallBack) {
		// 1.注册控件为DropTarget
		DropTarget dropTarget = new DropTarget(comp, DND.DROP_NONE); // dropCom为你的控件
		// 2.添加transfer并制定支持拖拽的类型为file
		Transfer[] transfer = new Transfer[] { FileTransfer.getInstance() };
		dropTarget.setTransfer(transfer);
		// 3.给控件添加监听
		dropTarget.addDropListener(new DropTargetAdapter() {
			// 4.获取文件的绝对路径
			// 在监听中找到下面方法并添加代码
			public void drop(DropTargetEvent arg0) {
				String[] files = (String[]) arg0.data;
				for (int i = 0; i < files.length; i++) {// 有可能用户拖动了多个文件
					xxx.log(SwtVoid.class, files[i]);
				}
				if (files.length == 0)
					return;
				iSimpleCallBack.callBack(files);
			}
		}); // 自动补全监听器即可
	}
	/**
	 * 获取显示器分辨率
	 * */
	public static int[] getPcWH() {
		int w = Display.getCurrent().getClientArea().width;
		int h = Display.getCurrent().getClientArea().height;
		return new int[] { w, h };
	}
	// /**
	// * 截图，并且加载loading条，返回img
	// * */
	// public static Image cutAddLoading(int x , int y , int w , int h){
	// Image loading = ImageFoctory.getImg(ImagePath.LOADING );
	//
	// //1.先从屏幕截取要截取的控件的区域
	// Image imageNew = cut(new Rectangle(x, y, w, h));
	// //3.把loading和上面的image组合在一起
	// ImgAddImg img = new ImgAddImg(imageNew, loading);
	// imageNew = img.createImage().getImage();
	//
	// return imageNew;
	// }
	/**
	 * 截图，传入区域
	 * @param r
	 * @return
	 */
	public static Image cut(Rectangle r) {
		if (r.width == 0 || r.height == 0) {
			xxx.log(SwtVoid.class + "", "截图时候，尺寸问错。。。。。。。。。。。。。 ");
			return null;
		}
		// 1.先从屏幕截取要截取的控件的区域
		Image imageNew = new Image(null, r.width, r.height);
		GC gc = new GC(Display.getDefault());
		gc.copyArea(imageNew, r.x, r.y);
		gc.dispose();
		return imageNew;
	}
	/**
	 * 截图，传入控件
	 * @param control
	 * @return
	 */
	public static Image cut(Control control) {
		Point p = control.getSize();
		// Image image = ImageFoctory.getImg(ImagePath.LOADING_BG , p.x ,p.y);
		Image image = SwtVoid.cut(new Rectangle(control.toDisplay(0, 0).x, control.toDisplay(0, 0).y, p.x, p.y));
		return image;
	}
	/**
	 * 设置透明度0-255
	 * 
	 * @param image
	 * @param alpha
	 *            0-255
	 * @return
	 */
	public static Image setAlpha(Image image, int alpha) {
		Image imageNew = new Image(null, image.getBounds().width, image.getBounds().height);
		GC gc2 = new GC(imageNew);
		gc2.setAlpha(alpha);
		gc2.drawImage(image, 0, 0);
		gc2.dispose();
		return imageNew;
	}
	/**
	 * 释放所有子控件
	 * @param child
	 */
	public static void disposeAll(Control child) {
		if (((Composite) child) != null) {
			Control[] cs = ((Composite) child).getChildren();
			child.dispose();
			for (Control c : cs) {
				if (child instanceof SashForm) {
					c.dispose();
				}
				if (!c.isDisposed()) {
					disposeAll(c);
					xxx.log(SwtVoid.class, child + "     的子控件   " + c + "        释放啦。。。。。。。");
				}
			}
		}
	}
	/**
	 * 删除所有子控件
	 * 
	 * @param composite
	 */
	public static void removeAllChild(Composite composite) {
		for (; composite.getChildren().length > 0;) {
			composite.getChildren()[0].dispose();
		}
		composite.layout();
	}
	/**
	 * 创建swt容器
	 * @param inBack
	 */
	public static void createSwt(ShellBack inBack) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 500);
		shell.setText("软通工具集.project");
		shell.setLayout(new FillLayout());
		inBack.callBack(shell);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		SysUtil.closeSys();
	}
}

