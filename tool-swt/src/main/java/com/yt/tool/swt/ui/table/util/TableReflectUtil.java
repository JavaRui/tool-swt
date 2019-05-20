package com.yt.tool.swt.ui.table.util;
import java.util.Date;
import java.util.Map;
import com.yt.utils.algorithm.DateUtil;
import com.yt.utils.reflect.ReflectUtil;
/**
 * @author cr.wu
 *
 * 2015-8-2
 */
public class TableReflectUtil {
	
	/**
	 * 获取属性中的值，通过表头的index序号
	 * @param element
	 * @param columnIndex
	 * @return
	 */
	public static Object getValueByColumn(Object element  , int columnIndex){
		try {
			String fieldName = getFieldNameByColumuIndex(element , columnIndex);
			if(fieldName == null){
				return "field no found by index:  "+columnIndex;
				
			}
			Object result = TableReflectUtil.getValueByFieldName(element,fieldName);
			
			//处理时间，因为会包含T，要替换成空格" "
			//inDate : "2015-09-02T16:14:19"
			if((result!=null)&&(fieldName.contains("Date") || fieldName.contains("date"))){
				if(result.toString().startsWith("201")&&result.toString().contains("-") && result.toString().contains(":")){
					result = result.toString().replace("T", " ");
				}
			}
			
			if(result!=null){
				if(result.getClass().getSimpleName().equals("Date")){
					return DateUtil.getFormatDate((Date)result);
				}
				if((result+"").equals("true")){
					return "是";
				}else if((result+"").equals("false")){
					return "否";
				}
				if(fieldName.contains("State") || fieldName.contains("state") || fieldName.contains("status")){
					if((result+"").equals("1")){
						return "是";
					}else if((result+"").equals("0")){
						return "否";
					}
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "field no found by index:  "+columnIndex;
	}
	
	
	
	
	/**
	 * 获取属性对应的get方法
	 * @param fieldName
	 * @return
	 */
	public static String getMethodByField(String fieldName){
		return ReflectUtil.pareGetName(fieldName);
	}
	
	/**
	 * 通过方法名字获取返回值
	 * @param eo
	 * @param getMethodName
	 * @return
	 * @throws Exception
	 */
	public static Object getValueByMethodName(Object eo , String getMethodName) throws Exception{
		return ReflectUtil.getValue(eo, getMethodName);
	}
	/**
	 * 获取属性名字，通过columuIndex
	 * @param bean
	 * @param value
	 * @return
	 */
	public static String getFieldNameByColumuIndex(Object bean , int columuIndex){
		Map<String, String> p = TablePropertyData.get(bean.getClass());
		return p.get(columuIndex+"")+"";
	} 
	
	/**
	 * 获取属性名字，通过ColumuName
	 * @param bean
	 * @param columuName
	 * @return
	 */
	public static String getFieldNameByColumuName(Object bean , String columuName){
		Map<String, String> p = TablePropertyData.get(bean.getClass());
		return p.get(columuName)+"";
	}
	
	/**
	 * 通过属性名获取columnIndex
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public static int getColumnNameByFieldName(Object bean ,String fieldName){
		Map<String, String> p = TablePropertyData.get(bean.getClass());
		return Integer.valueOf(p.get(fieldName+"_index")+"");
	}
	
	/**
	 * 通过头名名获取columnIndex
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public static int getColumnNameByColumnName(Object bean ,String fieldName){
		Map<String, String> p = TablePropertyData.get(bean.getClass());
		return Integer.valueOf(p.get(fieldName+"_name")+"");
	}
	
	/**
	 * 获取属性值，通过映射属性
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public static Object getValueByFieldName(Object bean , String fieldName){
		return ReflectUtil.getValueByFieldName(bean, fieldName);
	}
	
	
}

