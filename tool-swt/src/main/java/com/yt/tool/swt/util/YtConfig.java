package com.yt.tool.swt.util;
import java.io.File;
import java.io.IOException;
import org.eclipse.jface.preference.PreferenceStore;
import com.yt.common.YtConstants;
import com.yt.utils.algorithm.encrypt.CharUtils;
public class YtConfig {
	
	public static final String CODE_CHAOREN_USER = "CODE_CHAOREN_USER";
	public static final String CODE_CHAOREN_PWD = "CODE_CHAOREN_PWD";
	
	public static final String CODE_UU_USER = "CODE_UU_USER";
	public static final String CODE_UU_PWD = "CODE_UU_PWD";
	
	public static final String CODE_LALA_USER = "CODE_LALA_USER";
	public static final String CODE_LALA_PWD = "CODE_LALA_PWD";
	
	
	private static PreferenceStore ps = null;
	static {
		if (ps == null)
			try {
				if(!new File(YtConstants.COMMON_INI).exists()){
					
					new File(YtConstants.COMMON_INI).createNewFile();
				}
				ps = new PreferenceStore(YtConstants.COMMON_INI);
			} catch (Exception e) {
				ps = new PreferenceStore();
			}
	}
	public static boolean getBoolean(String key) {
		try {
			ps.load();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return ps.getBoolean(key);
	}
	public static String getString(String key) {
		try {
			ps.load();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return deCode(ps.getString(key));
	}
	public static int getInteger(String key) {
		try {
			ps.load();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return ps.getInt(key);
	}
	public static void setValue(String key, String value) {
		ps.setValue(key, value);
		try {
			ps.save();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	/**
	 * @param code：加密方式 1:unicode 2:pwd
	 * */
	public static void setValue(String key,String value,int code){
		setValue(key,enCode(value,code));
	}
	public static void setValue(String key, boolean value) {
		ps.setValue(key, value);
		try {
			ps.save();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public static void setValue(String key, int value) {
		ps.setValue(key, value);
		try {
			ps.save();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	//如果要改变辨别码，只能在后面加，就能保证程序运行正常，比如$$$$Unicode@@sssdaa
	private static final String CODE_UNICODE = "$$$$Unicode";
	private static final int UNICODE_TYPE = 1;
	
	private static final String CODE_PWD = "$$$$Pwd";
	private static final int PWD_TYPE = 2;
	
	/**处理需要加密的文字 */
	private static String enCode(String value,int code){
		switch(code){
		case UNICODE_TYPE:
			value = CharUtils.encodingUnicode(value)+CODE_UNICODE;
			break;
		case PWD_TYPE:
			value = CharUtils.encodingPwd(value)+CODE_PWD;
			break;
		}
		return value;
	}
	/**解密加密的文字*/
	private static String deCode(String value){
		if(value.contains(CODE_UNICODE)){
			value = value.substring(0, value.indexOf(CODE_UNICODE));
			value = CharUtils.decodeUnicode(value);
		}else if(value.contains(CODE_PWD)){
			value = value.substring(0, value.indexOf(CODE_PWD));
			value = CharUtils.decodingPwd(value);
		}
		return value;
	}
	
	
}
/*
 * Location: G:\myEclipseSpace\tbmgr\lib\t\ Qualified Name:
 * com.wintaobao.qqaccount.config.Config JD-Core Version: 0.6.0
 */

