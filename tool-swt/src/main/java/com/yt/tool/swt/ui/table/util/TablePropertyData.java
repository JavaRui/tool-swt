package com.yt.tool.swt.ui.table.util;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/***
 * 1.通过model的属性，排列出，属性的顺序，且用|隔开
 * 如GoodsBean : column_index = goodsId|goodName|stardard
 * 
 * 2.设置表头的文字，此时文字应该与属性对应，且用|隔开
 * 如GoodsBean : column_name = 商品id|商品名称|商品规格
 * 
 * 3.设置可编辑的表头index，对应文字的下标
 * 如在GoodsBean中，我想设置 商品名称和商品规格可以编辑，GoodsBean : can_modify = 1|2
 * 
 * 4.调用PropertyData.put方法，把GoodsBean.class对应以上的设置
 * PropertyData.put(GoodsBean.class,"订单id|合同号|供应商|总金额", "inOrderId|no|supName|allMoney","1|2");
 * 
 * 5.设置tableViewer为默认排序，可编辑，且具有自适应宽度，多行等功能
 * TableUtil.setCommon(tableViewer, GoodsBean.class);
 * 
 * 特殊扩展:每个特殊扩展，都需要用$隔开
 * 宽度 ：写法{w=xx}
 * 扩展显示：写法{extend=xx$}，当有这个标记的时候，table的显示，将去提取这个class的xx方法
 * 
 * */
public class TablePropertyData {
	/**表头对应的属性*/
	public static final String column_index = "column_index";
	/**表头显示文字*/
	public static final String column_name = "column_name";
	/**能被修改的字段*/
	public static final String can_modify = "can_modify";
	/**是否排序,可以不填，默认为true*/
	public static final String is_sort = "is_sort";
	
	public static final String split_sym = "\\|";
	/**
	 * class与map文件的对应关系
	 * classPath:map
	 * 如：
	 * class pers.cr.swtDev.table.testModular.goods.GoodsBean : map
	 * */
	private static Map<String, Map<String, String>> hm = new HashMap<String, Map<String,String>>();
	/**
	 * 添加对应的map
	 * @param classPath
	 * @param map
	 */
	public static void put(String classPath, Map<String, String> map) {
		hm.put(classPath, map);
	}
	
	/**
	 * 添加对应的map
	 * @param classObj
	 * @param columnName 表头名字用|隔开
	 * @param columnIndex 属性排列
	 */
	public static void put(Class<?> classObj , String columnName , String columnIndex){
		hm.put(classObj.toString(), doStringToMap(columnName, columnIndex));
	}
	
	/**
	 * 添加对应的map，且有可编辑字段
	 * @param classObj 
	 * @param columnName 表头名字用|隔开
	 * @param columnIndex 属性排列
	 * @param canModify 可编辑字段
	 */
	public static void put(Class<?> classObj , String columnName , String columnIndex , String canModify){
		put(classObj , columnName , columnIndex);
		hm.get(classObj.toString()).put(can_modify, canModify);
	}
	
	/**
	 * 根据classObj查找map对象
	 * @param classObj
	 * @return
	 */
	public static Map<String,String> get(Class<?> classObj) {
		if(hm.get(classObj.toString()) == null){
			new RuntimeException("class：   "+classObj+"   no found");
		}
		return hm.get(classObj.toString());
	}
	
	
	
	/**
	 * 将name字符串，和index转成map形式
	 * @param column_name
	 * @param column_index
	 * @return
	 */
	private static Map<String , String> doStringToMap(String column_name , String column_index){
		Map<String , String> map = new HashMap<String, String>(); 
		map.put(TablePropertyData.column_name, column_name);
		map.put(TablePropertyData.column_index, column_index);
		String[] names = column_name.split(split_sym);
		String[] indexs = column_index.split(split_sym);
		//为了使用方便查找
		for(int i = 0 ; i < names.length ;i++){
			//columnIndex  :  field
			map.put(i+"", indexs[i]);
			//name  :  field
			map.put(names[i], indexs[i]);
			//field+index  :  columnIndex
			map.put(indexs[i] + "_index", i+"");
			//field+name : name;
			map.put(indexs[i] + "_name", names[i]);
		}
		return map;
	}
	
	
	
}

