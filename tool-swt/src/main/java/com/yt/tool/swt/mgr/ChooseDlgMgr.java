package com.yt.tool.swt.mgr;
import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;
import com.yt.common.YtConstants;
import com.yt.common.xxx;
public class ChooseDlgMgr {
	
	private static Shell shell(){
		Display dis = Display.getCurrent();
		return dis.getActiveShell();
	}
	
	private static String userPath = YtConstants.USER_DIR;
	
	public static void main(String[] args) {
		Display dis = new Display();
		openFile(userPath + "a.txt");
	}
	
	/**
	 * 选择文件
	 * @param path
	 * @param fileter 过滤器
	 * **/
	public static String openFile(String path,String[] filter){
		// 创建一个打开对话框，样式设置为SWT.OPEN，其他也可以是SWT.SAVE、SWT.MULTI
		FileDialog dialog = new FileDialog(shell(), SWT.OPEN);
		// 设置打开默认的路径
		dialog.setFilterPath(path);
		// 设置所打开文件的扩展名
		dialog.setFilterExtensions(filter);
		// 设置显示到下拉框中的扩展名的名称
		dialog.setFilterNames(filter);
		// 打开窗口，返回用户所选的文件目录
		String file = dialog.open();
		if(file == null)return null;
		boolean add = true;
		for(String p : filter){
			if(file.contains(p.substring(1, p.length()))||p.contains("*.*")){
				add = false;
				break;
			}
		}
		if(add){
			file += filter[0].substring(1, filter[0].length());
		}
		xxx.log(ChooseDlgMgr.class,"选择的文件path:       "+file);
		return file;
	}
	/**
	 * 选择文件
	 * @param path
	 * **/
	public static String openFile(String path){
		String[] filter = new String[] { "*.txt", "*.*" };
		return openFile(path,filter);
	}
	/**
	 * 选择文件
	 * @param fileter 过滤器
	 * **/
	public static String openFile(String[] filter){
		String path = userPath;
		return openFile(path,filter);
	}
	
	/**
	 * 选择文件(带默认参数)
	 * **/
	public static String openFile(){
		String path = userPath;
		String[] filter = new String[] { "*.txt", "*.*" };
		return openFile(path,filter);
	}
	/**
	 * 选择目录
	 * @param path
	 * */
	public static String openDir(String path){
		DirectoryDialog dialog = new DirectoryDialog(shell());
		// 设置显示在窗口上方的提示信息
		dialog.setMessage("请选择所要操作的文件夹");
		// 设置对话框的标题
		dialog.setText("选择文件目录");
		// 设置打开时默认的文件目录
		dialog.setFilterPath(path);
		// 打开窗口，返回用户所选的文件目录
		String saveFile = dialog.open();
		if (saveFile != null) {
			// 创建一个File对象
			File directory = new File(saveFile);
			xxx.log(ChooseDlgMgr.class,directory.getPath());
		}
		return saveFile;
	}
	/**
	 * 选择目录(带默认参数)
	 * */
	public static String openDir(){
		String path = userPath;
		return openDir(path);
	}
	/**
	 * 打开颜色对话框
	 * */
	public static RGB openColor(){
		// 创建一个颜色对话框
		ColorDialog dialog = new ColorDialog(shell());
		// 设置默认选中的颜色
		dialog.setRGB(new RGB(255, 255, 128));
		// 打开对话框，将选中的颜色返回给rgb对象
		RGB rgb = dialog.open();
		if(rgb == null){
			return null;
		}
		xxx.log(ChooseDlgMgr.class,rgb.red + ","+rgb.green+","+rgb.blue);
		xxx.log(ChooseDlgMgr.class,rgb.red + "   "+rgb.green+"   "+rgb.blue);
//		if (rgb != null) {
//			xxx.log(ChooseDlgMgr.class,rgb);
//			// 创建颜色对象
//			Color color = new Color(display, rgb);
//			// 在使用完颜色对象后，释放资源
//			color.dispose();
//
//		}
		return rgb;
	}
	/**
	 * 打开字体对话框
	 * */
	public static FontData openFont(){
		// 创建一个字体对话框
		FontDialog dialog = new FontDialog(shell());
		// 设置默认选中的颜色
		dialog.setRGB(new RGB(255, 255, 128));
		// 打开对话框，将选中的字体返回给fontData对象
		FontData fontData = dialog.open();
//		if (fontData != null) {
//			xxx.log(ChooseDlgMgr.class,fontData);
//			// 创建颜色对象
//			Font font = new Font(display, fontData);
//			// 在使用完字体对象后，释放资源
//			font.dispose();
//		}
		return fontData;
	}
	/**
	 * 打印机对话框
	 * */
	public static PrinterData openPrint(){
		// 创建一个打印对话框
		PrintDialog dialog = new PrintDialog(shell());
		// 打开对话框，将选中的字体返回给fontData对象
		PrinterData printData = dialog.open();
//		if (printData != null) {
//			// 创建打印对象
//			Printer printer = new Printer(printData);
//			// 在使用打印对象后，释放资源
//			printer.dispose();
//		}
		return printData;
	}
	
	
}

