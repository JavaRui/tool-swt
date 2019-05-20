package com.yt.tool.swt.util.reflect;
import java.lang.reflect.Field;
import java.util.Date;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import com.yt.common.xxx;
import com.yt.utils.algorithm.DateUtil;
import com.yt.utils.reflect.ReflectUtil;
/**
 * @author cr.wu
 * 
 *         2015年8月25日
 *         
 *  处理swt和model的转换
 */
public class SwtModelChange {
	public static final int MODEL_TO_SWT = 1;
	public static final int SWT_TO_MODEL = 2;
	
	public static void modelTo(Object model, Object swt) {
		modelSwtChange(model , swt , MODEL_TO_SWT);
	}
	
	public static void swtTo(Object model, Object swt) {
		modelSwtChange(model , swt , SWT_TO_MODEL);
	}
	
	/**
	 * 数据类型和swt文本的相互转换
	 * 
	 * @param model
	 * @param swt
	 * @param type
	 *            MODEL_TO_SWT:model转swt 
	 *            SWT_TO_MODEL：swt转model
	 */
	public static void modelSwtChange(Object model, Object swt, int type) {
		if(model == null || swt == null){
			xxx.log(ReflectUtil.class, "不能实现转换其中:   model:   "+model+" ,  swt:   " + swt);
			return ;
		}
		Class<?> swtClass = swt.getClass();
		Field[] swtFields = swtClass.getDeclaredFields();
		Field[] tdFields = model.getClass().getDeclaredFields();
		for (Field tdF : tdFields) {
			// xxx.log(ReflectGetValue.class,tdF.getName());
			Field swtCurrentF = null;
			try {
				// 查到属性，tdF.getName()+"Text"，是否存在swtFields中
				for (Field swtF : swtFields) {
					if (swtF.getName().equals("$" + tdF.getName() + "Text") || swtF.getName().equals("$" + tdF.getName() + "Button")
							||swtF.getName().equals("$" + tdF.getName() + "Change")||swtF.getName().equals("$" + tdF.getName() + "Combo")
							) {
						swtCurrentF = swtF;
//						xxx.log(ReflectUtil.class, "找到了一个swt的属性:    " + swtCurrentF.getName());
						break;
					}
				}
				if (swtCurrentF == null) {
//					xxx.log(ReflectUtil.class, "swt的属性:    " + tdF.getName()+"  不能找到");
					continue;
				}
				swtCurrentF.setAccessible(true);// 设置可访问
				tdF.setAccessible(true);
				if (type == MODEL_TO_SWT) {
					SwtModelChange.modelToSwt(swtCurrentF, swt, tdF, model);
				} else {
					SwtModelChange.swtToModel(swtCurrentF, swt, tdF, model);
				}
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * model转swt
	 * @param swtF swt的属性
	 * @param swt swt的示例
	 * @param tdF model的属性
	 * @param model model的实例
	 * @throws Exception
	 */
	public static void modelToSwt(Field swtF, Object swt, Field tdF, Object model) throws Exception {
		swtF.setAccessible(true);// 设置可访问
		tdF.setAccessible(true);
		if (swtF.get(swt) instanceof Text) {
			Text tt = (Text) swtF.get(swt);
			if (tt == null) {
				return;
			}
			if (tdF.getType().getSimpleName().equals("Date")) {
				tt.setText(DateUtil.getFormatDate((Date) tdF.get(model)) + "");
			} else {
				tt.setText(tdF.get(model) + "");
			}
		} else if (swtF.getType().getSimpleName().equals("Combo")) {
			Combo tt = (Combo) swtF.get(swt);
			if (tt == null) {
				return;
			}
			tt.setText(tdF.get(model) + "");
		} else if (swtF.getType().getSimpleName().equals("Button")) {
			Button tt = (Button) swtF.get(swt);
			if (tt == null) {
				return;
			}
			boolean b = false;
			if("1".equals(tdF.get(model) + "")){
				b = true;
			}else if("0".equals(tdF.get(model) + "")){
				b = false;
			}else{
				b = Boolean.valueOf(tdF.get(model) + "");
			}
			tt.setSelection(b);
		}else if(swtF.get(swt) instanceof ISwtModelChange){
			ISwtModelChange change = (ISwtModelChange) swtF.get(swt);
			change.modelToSwt(tdF.get(model));
			
		}
	}
	/**
	 * swt转model
	 * @param swtF
	 * @param swt
	 * @param tdF
	 * @param model
	 * @throws Exception
	 */
	public static void swtToModel(Field swtF, Object swt, Field tdF, Object model) throws Exception {
		swtF.setAccessible(true);// 设置可访问
		if (Text.class.isAssignableFrom(swtF.getType())) {
//			if (swtF.getType().getSimpleName().equals("Text")||swtF.getType().getSimpleName().equals("YtText")||swtF.getType().getSimpleName().equals("YtVerfityText")) {
			Text tt = (Text) swtF.get(swt);
			if (tt == null) {
				ReflectUtil.setValue(model, tdF, "");
				return;
			}
			ReflectUtil.setValue(model, tdF, tt.getText());
		}
		else if (swtF.getType().getSimpleName().equals("Combo")) {
			Combo tt = (Combo) swtF.get(swt);
			if (tt == null) {
				ReflectUtil.setValue(model, tdF, "");
				return;
			}
			ReflectUtil.setValue(model, tdF, tt.getText());
		} else if (swtF.getType().getSimpleName().equals("Button")) {
			Button tt = (Button) swtF.get(swt);
			if (tt == null) {
				ReflectUtil.setValue(model, tdF, false);
				return;
			}
			ReflectUtil.setValue(model, tdF, tt.getSelection());
		}else if(swtF.get(swt) instanceof ISwtModelChange){
			ISwtModelChange change = (ISwtModelChange) swtF.get(swt);
			ReflectUtil.setValue(model, tdF, change.swtToModel());
		}
	}
}

