package cn.com.bluemoon.tool.dubbo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yt.common.xxx;
import com.yt.tool.swt.base.ShellBack;
import com.yt.tool.swt.base.SwtVoid;
import com.yt.tool.swt.base.YtComposite;
import com.yt.tool.swt.ui.text.YtText;
import com.yt.tool.swt.util.LayoutUtil;
import com.yt.utils.file.YtFileUtils;

public class QuickCreateDubboMain extends YtComposite{
	
	private YtText groudIdText ;
	private YtText parentText ;
	private YtText artifactIdText;
	private String SP_KEY="sp_key";
	private String IS_KEY = "is_key";
	
	private ProAndConComposite provider;
	private ProAndConComposite consumer;
	private YtComposite propertiesComp;
	
	public static enum PRO_CON {
		provider,consumer
	};
	
	
	
	public QuickCreateDubboMain(Composite parent) {
		super(parent);
		
		setGridLayout();
		
		Group globalGroup = new Group(this, SWT.BORDER_DASH);
		globalGroup.setText("全局设置");
		globalGroup.setLayout(new GridLayout(2, false));
		globalGroup.setLayoutData(LayoutUtil.createFillGridNoVer(1));
		
		createLabel(globalGroup,"groudId");
		groudIdText = new YtText(globalGroup);
		groudIdText.setGdFill(true, false);
		
		
		createLabel(globalGroup,"artifactId");
		artifactIdText = new YtText(globalGroup);
		artifactIdText.setGdFill(true, false);
		artifactIdText.setFocus();
		try {
			createDefaultText();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		
		
		Group providerGroup = new Group(this, SWT.BORDER_DASH);
		providerGroup.setText("provider设置");
		providerGroup.setLayout(new FillLayout());
		providerGroup.setLayoutData(LayoutUtil.createFillGridNoVer(1));
		
		provider = new ProAndConComposite(providerGroup, PRO_CON.provider);
//		provider.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		Group consumerGroup = new Group(this, SWT.BORDER_DASH);
		consumerGroup.setText("consumer设置");
		consumerGroup.setLayout(new FillLayout());
		consumerGroup.setLayoutData(LayoutUtil.createFillGridNoVer(1));
		consumer = new ProAndConComposite(consumerGroup, PRO_CON.consumer);
//		consumer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));

		ModifyListener modifyListener = new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				ReplaceKey key = createReplaceKeys();
				
				provider.change(key);
				consumer.change(key);
			}
		};
		groudIdText.addModifyListener(modifyListener);
		artifactIdText.addModifyListener(modifyListener);
		
		Button btn = new Button(this, SWT.PUSH);
		btn.setText("生成");
		btn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				ReplaceKey createReplaceKeys = createReplaceKeys();
				
				try {
					
					String pom = xxx.U+"res"+File.separatorChar+"pom.xml";
					String projectStr = xxx.U+"output"+File.separatorChar+artifactIdText.getText()+File.separatorChar+"pom.xml";
					YtFileUtils.copyFile(pom, projectStr,true);
					ProAndConComposite.replaceKeyAtJavaFile(new File(projectStr),createReplaceKeys);
					
					provider.createFile(createReplaceKeys);
					consumer.createFile(createReplaceKeys);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
	}
	
	private void createLabel(Group globalGroup, String string) {
		Label label = new Label(globalGroup, 0);
		label.setText(string);
		
	}

	private void createDefaultText() throws FileNotFoundException, IOException {
		
		Group group = new Group(this,SWT.BORDER_DASH);
		group.setLayoutData(LayoutUtil.createFillGridNoVer(1));
		group.setLayout(new FillLayout());
		
		propertiesComp = new YtComposite(group);
		propertiesComp.setGridLayout(2, false);
		
		Properties p = new Properties();
		p.load(new FileInputStream(xxx.U+"res"+File.separatorChar+"default.properties"));
		p.forEach((k,v)->{
			propertiesComp.createLabel(k+"");
			parentText = new YtText(propertiesComp);
			parentText.setText(p.getProperty(k+""));
			parentText.setGdFill(true, false);
			parentText.setData(IS_KEY, "yes");
			parentText.setData(SP_KEY, k+"");
		});
		group.setText("properties设置");
		
		groudIdText.setText(p.getProperty("groupId"));
		
		
	}


	private ReplaceKey createReplaceKeys() {
		
		Map<String,String> keyMap = new HashMap<>();
		
		List<Control> childByKey = propertiesComp.getChildByKey(SP_KEY);
		childByKey.forEach(control->{
			Text t = (Text) control;
			Object data2 = control.getData(SP_KEY);
			keyMap.put(data2+"", t.getText().trim());
		});
		
		ReplaceKey key = new ReplaceKey();
		key.artifactId = artifactIdText.getText();
		key.groupId = groudIdText.getText();
		key.name = key.artifactId;
		key.artifactParent = parentText.getText();
		key.keyMap = keyMap;
		key.trim();
		return key;
	}
	
	

	public static void main(String[] args) {
		SwtVoid.createSwt(new ShellBack() {
			
			@Override
			public void callBack(Shell shell) {
				shell.setSize(500, 650);
				shell.setText("快速生成dubbo模块小工具");
				new QuickCreateDubboMain(shell);
				
			}
		});
		
	}

}
