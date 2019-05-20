package cn.com.bluemoon.tool.dubbo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.yt.common.xxx;
import com.yt.tool.swt.base.YtComposite;
import com.yt.tool.swt.ui.text.YtText;
import com.yt.utils.file.ReadTxt;
import com.yt.utils.file.YtFileUtils;
import com.yt.utils.file.save.AutoSave;

import cn.com.bluemoon.tool.dubbo.QuickCreateDubboMain.PRO_CON;

public class ProAndConComposite extends YtComposite{

	private YtText nameText ;
	private YtText artifactIdText;
	private PRO_CON proCon;
	private YtText rootText;
	private YtText packageText;
	private  ReplaceKey replaceKey;
	private YtText portText;
	private ReplaceKey createReplaceKeys;

	public ProAndConComposite(Composite parent ,  PRO_CON proCon) {
		super(parent);
		this.proCon = proCon;
		init();
	}

	private void init() {
		
		setGridLayout(2, false);
		
		createLabel("name_"+proCon);
		
		nameText = new YtText(this);
		nameText.setGdFill(true, false);
		
		createLabel("artifactId_"+proCon);
		artifactIdText = new YtText(this);
		artifactIdText.setGdFill(true, false);

		createLabel("root_"+proCon);
		rootText = new YtText(this);
		rootText.setGdFill(true, false);
		rootText.setEditable(false);
		
		createLabel("package_"+proCon);
		packageText = new YtText(this);
		packageText.setGdFill(true, false);
		
//		createLabel("server_port_"+proCon);
//		portText = new YtText(this);
//		portText.setGdFill(true, false);
		
	}

	
	/**
	 * 开始创建文件
	 * @param createReplaceKeys
	 * @throws IOException
	 */
	public void createFile(ReplaceKey createReplaceKeys) throws IOException {
		this.createReplaceKeys = createReplaceKeys;
		//到src/main/java的路径
		String rootJavaStr = xxx.U+"output"+File.separatorChar+createReplaceKeys.artifactId+File.separatorChar+rootText.getText();
		File rootJavaFolder = new File(rootJavaStr);
		rootJavaFolder.mkdirs();
//		rootFolder.createNewFile();
		//源文件的路径
		String res = xxx.U+"res"+File.separatorChar+""+proCon+File.separatorChar;
		
//		createReplaceKeys.keyMap.put("server.port_"+proCon,  portText.getText());
		
		
		copyPom(rootJavaFolder, res);
		copyResource( rootJavaFolder , res);
		copyJavaFile(rootJavaFolder, res);
	}
	
	/**
	 * 开始复制pom文件
	 * @param rootJavaFolder
	 * @param res
	 * @throws IOException
	 */
	private void copyPom(File rootJavaFolder , String res) throws IOException{
		File projectFolder = rootJavaFolder.getParentFile().getParentFile().getParentFile();
		String newPath = projectFolder.getAbsolutePath()+File.separatorChar+"pom.xml";
		YtFileUtils.copyFile(res+"pom.xml", newPath,true);
		replaceKeyAtJavaFile(new File(newPath),replaceKey);
	}

	/**
	 * 复制resource的文件夹和修改里面的关键字
	 * @param rootJavaFolder
	 * @param res
	 * @return
	 * @throws IOException
	 */
	private String copyResource(File rootJavaFolder , String res) throws IOException {
		String sourcePath = res+"resource"+File.separatorChar;
		File mainFile = rootJavaFolder.getParentFile();
		String resourcePath = mainFile.getAbsolutePath()+File.separatorChar+"resource"+File.separatorChar;
		YtFileUtils.copyDirectiory(sourcePath, resourcePath);
		
		List<File> showFile = YtFileUtils.showFile(new ArrayList<File>(), resourcePath);
		showFile.forEach(file->{
			
			replaceKeyAtJavaFile(file, createReplaceKeys);
			
		});
		
		
		return  resourcePath;
	}
	
	/**
	 * 复制java文件
	 * @param rootJavaFolder
	 * @param res
	 * @throws IOException
	 */
	private void copyJavaFile(File rootJavaFolder,String res) throws IOException {
		String javaFilePath = rootJavaFolder.getAbsolutePath()+File.separatorChar+packageText.getText();
		File javaFolder = new File(javaFilePath);
		javaFolder.mkdirs();
		YtFileUtils.copyDirectiory(res+proCon+File.separatorChar, javaFolder.getAbsolutePath());
		
		List<File> showFile = YtFileUtils.showFile(new ArrayList<File>(), javaFolder.getAbsolutePath());
		showFile.forEach(file->{
			String newFileName = file.getAbsolutePath().replaceAll("ArtifactId", YtFileUtils.upFirstName(replaceKey.artifactId));
			System.out.println("------  "+newFileName);
			file.renameTo(new File(newFileName));
			replaceKeyAtJavaFile(new File(newFileName),replaceKey);
		});
	}
	
	
	
	
	/**
	 * 修改文件中的关键字
	 * @param javaFile
	 * @param replaceKey
	 */
	public static  void replaceKeyAtJavaFile(File javaFile,ReplaceKey replaceKey) {
		if(!javaFile.isFile()) {
			return ;
		}
		
		String fileContent = ReadTxt.getContentAsString(javaFile.getAbsolutePath());
		fileContent = fileContent.replaceAll("\\$\\{groupId\\}", ""+replaceKey.groupId);
		fileContent = fileContent.replaceAll("\\$\\{com\\.groupId\\}", "com."+replaceKey.groupId);
		fileContent = fileContent.replaceAll("\\$\\{artifactId\\}", replaceKey.artifactId);
		fileContent = fileContent.replaceAll("\\$\\{name\\}", replaceKey.name);
		fileContent = fileContent.replaceAll("\\$\\{artifactId_parent\\}", replaceKey.artifactId);
		fileContent = fileContent.replaceAll("\\$\\{ArtifactId\\}", replaceKey.upArtifactId());
		fileContent = fileContent.replaceAll("\\$\\{artifactId_consumer\\}", replaceKey.artifactId+"-"+PRO_CON.consumer);
		fileContent = fileContent.replaceAll("\\$\\{artifactId_provider\\}", replaceKey.artifactId+"-"+PRO_CON.provider);
		
		for(Map.Entry<String, String> it : replaceKey.keyMap.entrySet()){
			System.out.println(it.getKey()+"="+it.getValue());
			fileContent = fileContent.replaceAll("\\$\\{"+it.getKey()+"\\}", it.getValue());
		}
		//"server.port_"+proCon

		AutoSave.write(fileContent, javaFile.getAbsolutePath()+"",true,"utf-8");
		
	}

	/**文字修改，引起的输入框变化
	 * @param key
	 */
	public void change(ReplaceKey key) {
		replaceKey = key;
		replaceKey.trim();
		nameText.setText(replaceKey.name+"-"+proCon);
		artifactIdText.setText(replaceKey.artifactId+"-"+proCon);
		
		String rootStr = nameText.getText()+File.separatorChar+"src"+File.separatorChar+"main"+File.separatorChar+"java"+File.separatorChar;
		rootText.setText(rootStr);
		
		String packageStr = "";
		
		packageStr = "com"+File.separatorChar+replaceKey.groupId+""+File.separatorChar+replaceKey.artifactId+""+File.separatorChar+proCon+File.separatorChar;
		packageText.setText(packageStr);
		
	}
	
	public String getArtifactId() {
		return artifactIdText.getText();
	}
	
	
	
	
}
