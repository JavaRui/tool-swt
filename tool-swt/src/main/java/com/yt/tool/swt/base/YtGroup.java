package com.yt.tool.swt.base;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
/**
 * @author cr.wu
 *
 * 2015年10月8日
 * 
 * 使用该类的时候，要注意，parent已经被修改成了parentGroup
 * 该类只能做个简单的容器，如果涉及比较复杂的变化，可能会有问题
 */
public class YtGroup extends YtComposite{
	private Group parentGroup;
	
	/**  **/
	public YtGroup(Composite parent, int style) {
		super(createParent(parent), style);
		parentGroup = (Group) this.getParent();
	}
	/**  **/
	public YtGroup(Composite parent) {
		this(createParent(parent) , 0);
	}
	/**
	 * 根据parent生成一个group作为真实的parent
	 * */
	private static Group createParent(Composite parent){
		Group group = new Group(parent, 0);
		group.setLayout(new FillLayout());
		return group;
	}
	
	public void setText(String txt){
		parentGroup.setText(txt);
	}
	
	@Override
	public void setLayoutData(Object layoutData) {
		parentGroup.setLayoutData(layoutData);
	}
	
	
}

