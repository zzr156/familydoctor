package com.ylz.bizDo.flow.vo;

public class FlowStep {
	 	private int typeid;
	    private String typeName;
	    private String typeState;//0:不显示任何选择人员,1:只显示选择人员按钮,2:显示职位,3:显示角色
		public int getTypeid() {
			return typeid;
		}
		public void setTypeid(int typeid) {
			this.typeid = typeid;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public String getTypeState() {
			return typeState;
		}
		public void setTypeState(String typeState) {
			this.typeState = typeState;
		}
	    
	    
}
