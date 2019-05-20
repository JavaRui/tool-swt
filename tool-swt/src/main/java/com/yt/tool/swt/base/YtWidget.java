package com.yt.tool.swt.base;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import com.yt.common.xxx;
import com.yt.tool.swt.base.widget.IYtWidgetFace;
import com.yt.tool.swt.util.reflect.ISwtModelChange;
import com.yt.utils.notify.INBack;
/**
 * @author cr.wu
 *
 * 2015年8月17日
 */
public abstract class YtWidget extends YtComposite implements ISwtModelChange{
	private List<IYtWidgetFace> dataList = new ArrayList<IYtWidgetFace>();
	
	/**是否不能重复 
	 * true 不能（默认不能）
	 * false 能
	 * */
	private boolean singlon = true;
	
	protected INBack resultBack;
	
	private YtWidget me;
	protected StringBuilder nameSb = new StringBuilder("");
	
	/**
	 * 是否自动选择，当自动选择的时候，
	 * 删除时，会自动跳到第一个，且会触发inback方法
	 * 新增时，会自动跳到新增的那个，且会触发inback方法
	 * 而当，手动时，删除和新增都需要手动设置，但inback方法依然
	 * */
	private boolean autoSelect = true;
	public YtWidget(Composite parent) {
		super(parent);
		me = this;
		setLayout(new FillLayout());
	}
	/**
	 * 设置显示列表
	 * @param objs 数据列表
	 * @param showFieldName 需要显示的属性
	 * @param resultFieldName 返回的属性
	 */
	public void setData(IYtWidgetFace[] objs ){
		dataList = new ArrayList(objs.length);
		for(IYtWidgetFace object : objs){
			dataList.add(object);
		}
		setData(dataList );
		
	}
	
	
	/**
	 * 设置显示列表
	 */
	public void setData(List lists ){
		clearAll();
		addList(lists);
	}
	
	
	/**
	 * 添加显示列表
	 * @param lists
	 */
	public void addList(List<IYtWidgetFace> lists){
		for(IYtWidgetFace obj : lists){
			addObj(obj);
		}
		if(lists.size() == 0){
			return;
		}
//		setSelectIndex(0);
	}
	
	/**
	 * 添加显示列表
	 * @param dataList
	 */
	public void addObjs(IYtWidgetFace[] objs){
		for(IYtWidgetFace obj : objs){
			addObj(obj);
		}
//		setSelectIndex(0);
	}
	
	/**
	 * 添加对象
	 * @param object
	 */
	public void addObj(IYtWidgetFace object){
		if(singlon){
			String name = getShowByObject(object)+"";
			if(nameSb.toString().indexOf(name) != -1){
				xxx.log(this , "此处有重复添加的项，已拦截");
				return;
			}
		}
		
		
		dataList.add(object);
		addShow(object);
	}
	
	/**
	 * 添加显示
	 * @param object
	 */
	protected void addShow(IYtWidgetFace object){
		String name = getShowByObject(object)+"";
		nameSb.append(name+xxx.SP);
		add(name);
	}
	/**
	 * 获取显示的数据，通过该对象
	 * */
	public String getShowByObject(IYtWidgetFace object){
		if(object == null){
			return null;
		}
		return object.getShow();
	}
	/**
	 * 获取结果数据，通过该对象
	 * */
	public Object getResultByObject(IYtWidgetFace object){
		if(object == null){
			return null;
		}
		return object.getResult();
	}
	
	/**
	 * 设置选择，根据show
	 * @param showString
	 */
	public void setSelectByShow(String showString){
		
		int index = getIndexByShow(showString);
		if(index == -1){
			return;
		}
		setSelectIndex(index);
		
	}
	
	/**
	 * 设置选择根据result
	 * @param resultObj
	 */
	public void setSelectByResult(Object resultObj){
		for(int i = 0 ; i < dataList.size() ; i++){
			IYtWidgetFace object = dataList.get(i);
			Object name = getResultByObject(object);
			if(name.equals(resultObj)||name == resultObj){
				setSelectIndex(i);
				break;
			}
		}
	}
	/**
	 * 返回结果，如果是多选，将用|隔开
	 * 
	 * @return
	 */
	public Object getResult(){
		Object result =  getResultByObject( getSelectObj());
		return result;
	}
	
	/**
	 * 获取显示文字
	 * @return
	 */
	public String getShow() {
		String show =  getShowByObject( getSelectObj())+"";
		return show;
	}
	
	/**
	 * 获取一个被选择的对象
	 * @return
	 */
	public IYtWidgetFace getSelectObj(){
		if(dataList.size() == 0){
			return null;
		}
		int index = getSelectIndex();
		IYtWidgetFace object = dataList.get(index);
		return object;
	}
	
	/**
	 * 根据现实字段，获取index
	 * @param show
	 * @return
	 */
	public int getIndexByShow(String show) {
		for(int i = 0 ; i < dataList.size() ; i++){
			IYtWidgetFace object = dataList.get(i);
			Object showName = getShowByObject(object)+"";
			if(showName.equals(show)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 根据结果字段，获取index
	 * @param result
	 * @return
	 */
	public int getIndexByResult(Object result) {
		for(int i = 0 ; i < dataList.size() ; i++){
			IYtWidgetFace object = dataList.get(i);
			Object reslutName = getResultByObject(object);
			if(reslutName.equals(result)){
				return i;
			}
		}
		return -1;
	}
	/**
	 * 移除选择的item，子类实现
	 * */
	public void removeSelect(){}
	
	/**返回当前选择的index，不同的控件，有不同的方法，所以子类必须继承*/
	public abstract int getSelectIndex() ;
	/**设置选择，不同的控件，有不同的方法，所以子类必须继承*/
	public abstract void setSelectIndex(int index) ;
	/**添加显示,同上*/
	protected abstract void add(String txt);
	
	/**清除自控件的显示列表*/
	protected abstract void clearChildItem();
	
	/**
	 * 清除所有，包括数据，item
	 * */
	public void clearAll(){
		dataList = new ArrayList<IYtWidgetFace>();
		nameSb = new StringBuilder();
		clearChildItem();
		
	}
	
	/**
	 * 移除一个显示项
	 * 清除了名字，且会删除对象
	 * @param oldName
	 */
	public void removeShowAtNameSb(String oldName){
		replaceNameAtNameSb(oldName, "");
		
		for(int i = 0 ; i < dataList.size() ; i++){
			String oldTxt = getShowByObject(dataList.get(i));
			if(oldTxt.equals(oldName)){
				dataList.remove(i);
				break;
			}
		}
		
	}
	
	protected void replaceNameAtNameSb(String oldName , String newName){
		nameSb = new StringBuilder(nameSb.toString().replace(oldName+xxx.SP, newName));
	}
	
	/**
	 * 判断名字是否存在
	 * @param name
	 * 
	 * @return bool
	 * 	false：不存在
	 * 	true：存在
	 * 
	 */
	public boolean checkName(String name){
		if(nameSb.toString().indexOf(name) == -1){
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 * 刷新被选择的子控件的文本
	 * @param newTxt
	 */
	public void flushSelectText(String newTxt){
		int index = getSelectIndex();
		IYtWidgetFace selectObj = getDataList().get(index);
		try {
			selectObj.setShow(newTxt);
			String oldTxt = getShowByObject(selectObj);
			replaceItem(index , newTxt);
			
			replaceNameAtNameSb(oldTxt , newTxt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**替换child的显示*/
	protected abstract void replaceItem(int index , String nexTxt);
	public boolean isSinglon() {
		return singlon;
	}
	public void setSinglon(boolean singlon) {
		this.singlon = singlon;
	}
	public List<IYtWidgetFace> getDataList() {
		return dataList;
	}
	public void setDataList(List lists) {
		this.dataList = lists;
	}
	@Override
	public void modelToSwt(Object obj) {
		setSelectByResult(obj);
	}
	@Override
	public Object swtToModel() {
		return getResult();
	}
	
	
	
	public String getNameString(){
		return nameSb.toString();
	}
	public INBack getResultBack() {
		return resultBack;
	}
	public void setResultBack(INBack resultBack) {
		this.resultBack = resultBack;
	}
	
}

