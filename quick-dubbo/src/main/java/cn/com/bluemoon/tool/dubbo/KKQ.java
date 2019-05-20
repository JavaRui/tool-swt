package cn.com.bluemoon.tool.dubbo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yt.common.xxx;
import com.yt.utils.file.ReadTxt;
import com.yt.utils.file.YtFileUtils;
import com.yt.utils.file.save.AutoSave;

public class KKQ {
	
	public static void main(String[] args) {
		
		String path = "D:\\code_space\\eclipse_space\\com.yt.utils";
		List<File> showFile = YtFileUtils.showFile(new ArrayList<File>(), path);
		xxx.show(showFile);
		showFile.forEach((file)->{
			
			if(!file.isFile()) {
				return ;
			}
			String filePath = file.getAbsolutePath();
			System.out.println(file.getAbsolutePath());
			String content = ReadTxt.getContentAsString(file.getAbsolutePath());
			AutoSave.write(content, filePath,true, "UTF-8");
		});
		
	}
	

}
