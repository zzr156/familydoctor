package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 轮播图片
 * @author lyy
 *
 */
@Entity
@Table(name = "CD_CAROUSEL_IMG")
public class CdCarouselimg extends BasePO{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键

    @Column(name = "CJSJ")
    private Calendar cjsj;//创建时间
    @Column(name = "IMG_URL")
	private String imgUrl;//图片地址
    @Column(name = "PX")
	private String px;//排序

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getCjsj() {
        return cjsj;
    }

    public void setCjsj(Calendar cjsj) {
        this.cjsj = cjsj;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPx() {
        return px;
    }

    public void setPx(String px) {
        this.px = px;
    }

    public String getStrCjsj(){
        return ExtendDate.getYMD_h_m(this.getCjsj());
    }
}
