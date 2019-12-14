package com.ylz.packcommon.common.comEnum;

import java.util.ArrayList;
import java.util.List;

public enum CdPaperWorkTableType {

	/**
	 * 条形码
	 */
	onecode("onecode","条形码") ,
    /**
     * 艾滋病 
     */
    aids("sources_aids","艾滋病 ") ,
    /**
     * 结核病
     */
    tuberculosis("sources_tuberculosis","结核病");
    private String id;
    private String value;
    private CdPaperWorkTableType(String id,String value){
        this.id = id;
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public String getId() {
        return id;
    }

    public static List<EnumVo> getValuesList(){
	    List<EnumVo> ls=new ArrayList<EnumVo>();
	    for(CdPaperWorkTableType w:CdPaperWorkTableType.values()){
	        EnumVo v=new EnumVo();
	        v.setId(w.getId());
	        v.setValue(w.getValue());
	        ls.add(v);
	    }
	    return ls;
	}
	
	public static EnumVo getIdFindValue(String id){
	    EnumVo v = new EnumVo();
	    for(CdPaperWorkTableType w:CdPaperWorkTableType.values()){
	        if(w.getId().equals(id)) {
	            v.setId(w.getId());
	            v.setValue(w.getValue());
	            break;
	        }
	    }
	    return v;
	}
}
