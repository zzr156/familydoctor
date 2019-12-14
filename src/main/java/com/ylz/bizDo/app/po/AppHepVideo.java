package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * HepVideo Entity 健康教育宣传视频
 *
 * @author dws 2018-11-14
 * 
 */
@Entity
@Table(name = "APP_HEP_VIDEO")
public class AppHepVideo extends BasePO {

	/**
	 * fields
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "VIDEO_NAME", length = 50)
	private String videoName; // 视频名称
	@Column(name = "VIDEO_TYPE", length = 20)
	private String videoType; // 视频类别
	@Column(name = "VIDEO_STATE", length = 10)
	private String videoState; // 视频状态
	@Column(name = "VIDEO_ADDRESS", length = 200)
	private String videoAddress;//视频地址
	@Column(name = "VIDEO_CONTENT", length = 100)
	private String videoContent; // 文本/图片内容
	// Constructors

	/** default constructor */
	public AppHepVideo() {

	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getVideoState() {
		return videoState;
	}

	public void setVideoState(String videoState) {
		this.videoState = videoState;
	}

	public String getVideoAddress() {
		return videoAddress;
	}

	public void setVideoAddress(String videoAddress) {
		this.videoAddress = videoAddress;
	}

	public String getVideoContent() {
		return videoContent;
	}

	public void setVideoContent(String videoContent) {
		this.videoContent = videoContent;
	}

	public String getStrVideoState() throws Exception{
		if(StringUtils.isNotBlank(this.getVideoState())){
			SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE,this.getVideoState());
			if(value != null) {
				return value.getCodeTitle();
			}
		}
		return "";
	}
}
