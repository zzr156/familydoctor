package com.ylz.bizDo.jtapp.basicHealthEntity.BirthCertificate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <p>
 * Title: Fy_csyxzm
 * </p>
 * <p>
 * Description:出生医学证明表
 * </p>
 * <p>
 * Copyright:Copyright(c)2010
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * </p>
 * @hibernate.class table="Fy_csyxzm"
 * @author
 * @version 1.0
 */
 /*
 	<mapping resource="com/start/si/maternal_child_health/commons/entity/Fy_csyxzm.hbm.xml" />
 */
 public class Fy_csyxzm implements Serializable
 {
 	 	/**
 	 * 默认构造函数
 	 */
 	public Fy_csyxzm(){}
 				private String idno;
				private  String cszmid; //出生医学证明内码
				private  String mqkh00; //母亲卡号
				private  String qfdw00; //签发单位
				private  String ffrq00; //发放日期
				private  String cszmbh; //出生医学证明编号
				private  String bybh00; //病案编号
				private  String ysxm00; //婴儿姓名
				private  String xb0000; //性别
				private  String csrq00; //出生日期
				private  String csyz00; //出生孕周
				private  String tz0000; //出生体重
				private  String sc0000; //出生身长
				private  String jkzk00; //健康状况（1、良好2、一般3、差）
				private  String jsz000; //接生者
				private  String csdshe; //出生地：省
				private  String csdshi; //出生地：市
				private  String csdxia; //出生地：县(区)
				private  String csdzhe; //出生地：镇(乡)
				private  String csdcun; //出生地：村(居)
				private  String csdmph; //出生地：门牌号
				private  String gsdshe; //地址统计归属（户口）：省
				private  String gsdshi; //地址统计归属（户口）：市
				private  String gsdxia; //地址统计归属（户口）：县(区)
				private  String gsdzhe; //地址统计归属（户口）：镇(乡)
				private  String gsdcun; //地址统计归属（户口）：村(居)
				private  String gsdmph; //地址统计归属（户口）：门牌号
				private  String mqbh00; //母亲编号
				private  String mqsfz0; //母亲身份证
				private  String mqnl00; //母亲年龄
				private  String mqgj00; //母亲国籍
				private  String mqmz00; //母亲民族
				private  String fqbh00; //父亲编号
				private  String fqsfz0; //父亲身份证
				private  String fqnl00; //父亲年龄
				private  String fqgj00; //父亲国籍
				private  String fqmz00; //父亲民族
				private  String csddfl; //出生地点分类（1、医院2、妇幼保健院3、家庭4、其他）
				private  String csdd00; //出生地点
				private  String jsjg00; //接生机构
				private  String dhhm00; //电话号码
				private  String syzh00; //生育证号
				private  String tc0000; //胎次
				private  String qfzqm0; //签发者签名
				private  String qfqk00; //签发情况（1、已发2、未发）
				private  String yxzmlx; //医学证明类型（1、首次签发2、换发3、补发）
				private  String zfbz00; //作废标志（0作废）
				private  String tsqk00; //特殊情况（可能有下拉）
				private  String bz0000; //备注
				private  String kh0000; //卡号
				private  String dyzt00; //打印状态（是否打印，打印次数）不允许打印为-1允许为0,1,2……
				private  String jkdah0; //居民健康档案号
				private  String zhbjid; //最后编辑者id
				private  String zhbjz0; //最后编辑者
				private  String jdrq00; //最后操作日期
				private  String famshe; //家庭地址：省
				private  String famshi; //家庭地址：市
				private  String famxia; //家庭地址：县(区)
				private  String famzhe; //家庭地址：镇(乡)
				private  String famcun; //家庭地址：村(居)
				private  String fammph; //家庭地址：门牌号
				private  String lzrnam; //领证人姓名
				private  String lzrgx0; //领证人关系
				private  String lzrlb0; //领证人证件类别
				private  String lzrsfz; //领证人证件号
				private  String yyid00; //医院id
				private String wsxxdz;//省外详细地址
				private String scxtbh;//手册系统编号
				private String sfdq00;//是否单亲
				private String csyzt0;//出生孕周(天)
				private String sflq00;//是否领取
				private String fqzjlx;//证据类型 1身份证 2护照 3 军官证
				private String mqzjlx;//证据类型 1身份证 2护照 3 军官证
				private String lzrq00;//领证日期
				private String fmxtbh;//分娩记录系统编号
				private String mqpic0;//母亲身份证照片
				private String fqpic0;//父亲身份证照片
				private String sfyxxg;//是否允许修改(通过换发页面进入编辑页面)
				private String zyh000;//住院号
				private String wts000;//委托书(0.是，1否)
				private String jsz001;//接生者2
				private String jsz002;//接生者3
				private String mqhkd0;//母亲户口地
				private String fqhkd0;//父亲户口地
				private String dyrq00;//打印日期
				private String dyinfo;//打印信息
				private String byzjmq;//母亲备用证件号
				private String byzjfq;//父亲备用证件号
				private String bdcs00;//补打次数
				private String jszmc0;
				private String jszmc1;
				private String jszmc2;
				private  String fqsfzl; //父亲身份证类别
				private  String mqsfzl; //母亲身份证类别


				private String cyrq00;		//出院日期

				private String scqflx;			//首次签发类型，0-本年度首次签发，1-非本年度首次签发
				private String wlzcf0;			//2012年9月30日以前未领取证件产妇：0-否，1-是

				private String zhbjsj;//最后编辑时间

				private String scbz00;//上传标志，1代表已上传
				private String sfyxzm;//是否有效证明（1有效  0无效）
				private String  zfsj00;//作废时间

				private String csqhdm ;
				private String bah000 ;
				private String xzqhdm ;
				private String zzjgdm ;
				private String jglbdm ;
				private String mqqhdm ;
				private String lxdh00 ;
				private String xgtemp ;

				private String yztxz0;  //孕周修正（1、+；2、-）
				private String sfyz00; //是否通过省平台接口验证,为空时表示未验证
				private String sffz00; //1、正常；2废证

				private String gpyzp1; //高拍仪照片1
				private String gpyzp2; //高拍仪照片2
				private String gpyzp3; //高拍仪照片3

				private  String hfyylx; //换发原因类型（1、签发单位2、信息相符3、信息不符4、其它）
				private  String bfyyqt;//补发原因其他
				private  String bhfyy0; //补发原因(1、打印或填写错误2、遗失3、其它)



				public String getIdno() {
					return idno;
				}

				public void setIdno(String idno) {
					this.idno = idno;
				}

				public String getHfyylx() {
					return hfyylx;
				}

				public void setHfyylx(String hfyylx) {
					this.hfyylx = hfyylx;
				}

				public String getBfyyqt() {
					return bfyyqt;
				}

				public void setBfyyqt(String bfyyqt) {
					this.bfyyqt = bfyyqt;
				}

				public String getBhfyy0() {
					return bhfyy0;
				}

				public void setBhfyy0(String bhfyy0) {
					this.bhfyy0 = bhfyy0;
				}

	 public void setFfrq00(String ffrq00) {
		 this.ffrq00 = ffrq00;
	 }

	 public void setXb0000(String xb0000) {
		 this.xb0000 = xb0000;
	 }

	 public void setCsrq00(String csrq00) {
		 this.csrq00 = csrq00;
	 }

	 public void setCsyz00(String csyz00) {
		 this.csyz00 = csyz00;
	 }

	 public void setTz0000(String tz0000) {
		 this.tz0000 = tz0000;
	 }

	 public void setSc0000(String sc0000) {
		 this.sc0000 = sc0000;
	 }

	 public void setMqnl00(String mqnl00) {
		 this.mqnl00 = mqnl00;
	 }

	 public void setFqnl00(String fqnl00) {
		 this.fqnl00 = fqnl00;
	 }

	 public void setTc0000(String tc0000) {
		 this.tc0000 = tc0000;
	 }

	 public void setJdrq00(String jdrq00) {
		 this.jdrq00 = jdrq00;
	 }

	 public void setCsyzt0(String csyzt0) {
		 this.csyzt0 = csyzt0;
	 }

	 public void setLzrq00(String lzrq00) {
		 this.lzrq00 = lzrq00;
	 }

	 public void setMqpic0(String mqpic0) {
		 this.mqpic0 = mqpic0;
	 }

	 public String getFqpic0() {
		 return fqpic0;
	 }

	 public void setFqpic0(String fqpic0) {
		 this.fqpic0 = fqpic0;
	 }

	 public void setDyrq00(String dyrq00) {
		 this.dyrq00 = dyrq00;
	 }

	 public void setCyrq00(String cyrq00) {
		 this.cyrq00 = cyrq00;
	 }

	 public void setZhbjsj(String zhbjsj) {
		 this.zhbjsj = zhbjsj;
	 }

	 public void setZfsj00(String zfsj00) {
		 this.zfsj00 = zfsj00;
	 }

	 public String getGpyzp1() {
		 return gpyzp1;
	 }

	 public void setGpyzp1(String gpyzp1) {
		 this.gpyzp1 = gpyzp1;
	 }

	 public String getGpyzp2() {
		 return gpyzp2;
	 }

	 public void setGpyzp2(String gpyzp2) {
		 this.gpyzp2 = gpyzp2;
	 }

	 public String getGpyzp3() {
		 return gpyzp3;
	 }

	 public void setGpyzp3(String gpyzp3) {
		 this.gpyzp3 = gpyzp3;
	 }

	 public String getSffz00() {
					return sffz00;
				}

				public void setSffz00(String sffz00) {
					this.sffz00 = sffz00;
				}

				public String getSfyz00() {
					return sfyz00;
				}

				public void setSfyz00(String sfyz00) {
					this.sfyz00 = sfyz00;
				}

				public String getYztxz0() {
					return yztxz0;
				}

				public void setYztxz0(String yztxz0) {
					this.yztxz0 = yztxz0;
				}

				public String getXgtemp() {
					return xgtemp;
				}

				public void setXgtemp(String xgtemp) {
					this.xgtemp = xgtemp;
				}


				public String getSfyxzm() {
					return sfyxzm;
				}

				public void setSfyxzm(String sfyxzm) {
					this.sfyxzm = sfyxzm;
				}

				public String getScbz00() {
					return scbz00;
				}

				public void setScbz00(String scbz00) {
					this.scbz00 = scbz00;
				}

				public String getScqflx() {
					return scqflx;
				}

				public void setScqflx(String scqflx) {
					this.scqflx = scqflx;
				}

				public String getWlzcf0() {
					return wlzcf0;
				}

				public void setWlzcf0(String wlzcf0) {
					this.wlzcf0 = wlzcf0;
				}



				public String getFqsfzl() {
					return fqsfzl;
				}

				public void setFqsfzl(String fqsfzl) {
					this.fqsfzl = fqsfzl;
				}

				public String getMqsfzl() {
					return mqsfzl;
				}

				public void setMqsfzl(String mqsfzl) {
					this.mqsfzl = mqsfzl;
				}

				public String getJszmc0() {
					return jszmc0;
				}

				public void setJszmc0(String jszmc0) {
					this.jszmc0 = jszmc0;
				}

				public String getJszmc1() {
					return jszmc1;
				}

				public void setJszmc1(String jszmc1) {
					this.jszmc1 = jszmc1;
				}

				public String getJszmc2() {
					return jszmc2;
				}

				public void setJszmc2(String jszmc2) {
					this.jszmc2 = jszmc2;
				}

				public String getBdcs00() {
					return bdcs00;
				}

				public void setBdcs00(String bdcs00) {
					this.bdcs00 = bdcs00;
				}

				public String getByzjmq() {
					return byzjmq;
				}

				public void setByzjmq(String byzjmq) {
					this.byzjmq = byzjmq;
				}

				public String getByzjfq() {
					return byzjfq;
				}

				public void setByzjfq(String byzjfq) {
					this.byzjfq = byzjfq;
				}

				public String getDyinfo() {
					return dyinfo;
				}

				public void setDyinfo(String dyinfo) {
					this.dyinfo = dyinfo;
				}


				public String getMqhkd0() {
					return mqhkd0;
				}

				public void setMqhkd0(String mqhkd0) {
					this.mqhkd0 = mqhkd0;
				}

				public String getFqhkd0() {
					return fqhkd0;
				}

				public void setFqhkd0(String fqhkd0) {
					this.fqhkd0 = fqhkd0;
				}

				public String getJsz001() {
					return jsz001;
				}

				public void setJsz001(String jsz001) {
					this.jsz001 = jsz001;
				}

				public String getJsz002() {
					return jsz002;
				}

				public void setJsz002(String jsz002) {
					this.jsz002 = jsz002;
				}

				public String getSfyxxg() {
					return sfyxxg;
				}

				public void setSfyxxg(String sfyxxg) {
					this.sfyxxg = sfyxxg;
				}


				public String getFmxtbh() {
					return fmxtbh;
				}

				public void setFmxtbh(String fmxtbh) {
					this.fmxtbh = fmxtbh;
				}


				public String getFqzjlx() {
					return fqzjlx;
				}

				public void setFqzjlx(String fqzjlx) {
					this.fqzjlx = fqzjlx;
				}

				public String getMqzjlx() {
					return mqzjlx;
				}

				public void setMqzjlx(String mqzjlx) {
					this.mqzjlx = mqzjlx;
				}

				public String getSflq00() {
					return sflq00;
				}

				public void setSflq00(String sflq00) {
					this.sflq00 = sflq00;
				}


				public String getSfdq00() {
					return sfdq00;
				}

				public void setSfdq00(String sfdq00) {
					this.sfdq00 = sfdq00;
				}

				public String getScxtbh() {
					return scxtbh;
				}

				public void setScxtbh(String scxtbh) {
					this.scxtbh = scxtbh;
				}


					public String getWsxxdz() {
					return wsxxdz;
				}
				public void setWsxxdz(String wsxxdz) {
					this.wsxxdz = wsxxdz;
				}
					/**
			 * 出生医学证明内码
			 * @hibernate.property column="cszmid"
			 * @return cszmid
			 */
			public  String getCszmid() {
			return this.cszmid;
			}
				/**
			 * 母亲卡号
			 * @hibernate.property column="mqkh00"
			 * @return mqkh00
			 */
			public  String getMqkh00() {
			return this.mqkh00;
			}
				/**
			 * 签发单位
			 * @hibernate.property column="qfdw00"
			 * @return qfdw00
			 */
			public  String getQfdw00() {
			return this.qfdw00;
			}
				/**
			 * 发放日期
			 * @hibernate.property column="ffrq00"
			 * @return ffrq00
			 */
				/**
			 * 出生医学证明编号
			 * @hibernate.property column="cszmbh"
			 * @return cszmbh
			 */
			public  String getCszmbh() {
			return this.cszmbh;
			}
				/**
			 * 病案编号
			 * @hibernate.property column="bybh00"
			 * @return bybh00
			 */
			public  String getBybh00() {
			return this.bybh00;
			}
				/**
			 * 婴儿姓名
			 * @hibernate.property column="ysxm00"
			 * @return ysxm00
			 */
			public  String getYsxm00() {
			return this.ysxm00;
			}
				/**
			 * 性别
			 * @hibernate.property column="xb0000"
			 * @return xb0000
			 */
				/**
			 * 出生日期
			 * @hibernate.property column="csrq00"
			 * @return csrq00
			 */
				/**
			 * 出生孕周
			 * @hibernate.property column="csyz00"
			 * @return csyz00
			 */
				/**
			 * 出生体重
			 * @hibernate.property column="tz0000"
			 * @return tz0000
			 */
				/**
			 * 出生身长
			 * @hibernate.property column="sc0000"
			 * @return sc0000
			 */
				/**
			 * 健康状况（1、良好2、一般3、差）
			 * @hibernate.property column="jkzk00"
			 * @return jkzk00
			 */
			public  String getJkzk00() {
			return this.jkzk00;
			}
				/**
			 * 接生者
			 * @hibernate.property column="jsz000"
			 * @return jsz000
			 */
			public  String getJsz000() {
			return this.jsz000;
			}
				/**
			 * 出生地：省
			 * @hibernate.property column="csdshe"
			 * @return csdshe
			 */
			public  String getCsdshe() {
			return this.csdshe;
			}
				/**
			 * 出生地：市
			 * @hibernate.property column="csdshi"
			 * @return csdshi
			 */
			public  String getCsdshi() {
			return this.csdshi;
			}
				/**
			 * 出生地：县(区)
			 * @hibernate.property column="csdxia"
			 * @return csdxia
			 */
			public  String getCsdxia() {
			return this.csdxia;
			}
				/**
			 * 出生地：镇(乡)
			 * @hibernate.property column="csdzhe"
			 * @return csdzhe
			 */
			public  String getCsdzhe() {
			return this.csdzhe;
			}
				/**
			 * 出生地：村(居)
			 * @hibernate.property column="csdcun"
			 * @return csdcun
			 */
			public  String getCsdcun() {
			return this.csdcun;
			}
				/**
			 * 出生地：门牌号
			 * @hibernate.property column="csdmph"
			 * @return csdmph
			 */
			public  String getCsdmph() {
			return this.csdmph;
			}
				/**
			 * 地址统计归属（户口）：省
			 * @hibernate.property column="gsdshe"
			 * @return gsdshe
			 */
			public  String getGsdshe() {
			return this.gsdshe;
			}
				/**
			 * 地址统计归属（户口）：市
			 * @hibernate.property column="gsdshi"
			 * @return gsdshi
			 */
			public  String getGsdshi() {
			return this.gsdshi;
			}
				/**
			 * 地址统计归属（户口）：县(区)
			 * @hibernate.property column="gsdxia"
			 * @return gsdxia
			 */
			public  String getGsdxia() {
			return this.gsdxia;
			}
				/**
			 * 地址统计归属（户口）：镇(乡)
			 * @hibernate.property column="gsdzhe"
			 * @return gsdzhe
			 */
			public  String getGsdzhe() {
			return this.gsdzhe;
			}
				/**
			 * 地址统计归属（户口）：村(居)
			 * @hibernate.property column="gsdcun"
			 * @return gsdcun
			 */
			public  String getGsdcun() {
			return this.gsdcun;
			}
				/**
			 * 地址统计归属（户口）：门牌号
			 * @hibernate.property column="gsdmph"
			 * @return gsdmph
			 */
			public  String getGsdmph() {
			return this.gsdmph;
			}
				/**
			 * 母亲编号
			 * @hibernate.property column="mqbh00"
			 * @return mqbh00
			 */
			public  String getMqbh00() {
			return this.mqbh00;
			}
				/**
			 * 母亲身份证
			 * @hibernate.property column="mqsfz0"
			 * @return mqsfz0
			 */
			public  String getMqsfz0() {
			return this.mqsfz0;
			}
				/**
			 * 母亲年龄
			 * @hibernate.property column="mqnl00"
			 * @return mqnl00
			 */
				/**
			 * 母亲国籍
			 * @hibernate.property column="mqgj00"
			 * @return mqgj00
			 */
			public  String getMqgj00() {
			return this.mqgj00;
			}
				/**
			 * 母亲民族
			 * @hibernate.property column="mqmz00"
			 * @return mqmz00
			 */
			public  String getMqmz00() {
			return this.mqmz00;
			}
				/**
			 * 父亲编号
			 * @hibernate.property column="fqbh00"
			 * @return fqbh00
			 */
			public  String getFqbh00() {
			return this.fqbh00;
			}
				/**
			 * 父亲身份证
			 * @hibernate.property column="fqsfz0"
			 * @return fqsfz0
			 */
			public  String getFqsfz0() {
			return this.fqsfz0;
			}
				/**
			 * 父亲年龄
			 * @hibernate.property column="fqnl00"
			 * @return fqnl00
			 */
				/**
			 * 父亲国籍
			 * @hibernate.property column="fqgj00"
			 * @return fqgj00
			 */
			public  String getFqgj00() {
			return this.fqgj00;
			}
				/**
			 * 父亲民族
			 * @hibernate.property column="fqmz00"
			 * @return fqmz00
			 */
			public  String getFqmz00() {
			return this.fqmz00;
			}
				/**
			 * 出生地点分类（1、医院2、妇幼保健院3、家庭4、其他）
			 * @hibernate.property column="csddfl"
			 * @return csddfl
			 */
			public  String getCsddfl() {
			return this.csddfl;
			}
				/**
			 * 出生地点
			 * @hibernate.property column="csdd00"
			 * @return csdd00
			 */
			public  String getCsdd00() {
			return this.csdd00;
			}
				/**
			 * 接生机构
			 * @hibernate.property column="jsjg00"
			 * @return jsjg00
			 */
			public  String getJsjg00() {
			return this.jsjg00;
			}
				/**
			 * 电话号码
			 * @hibernate.property column="dhhm00"
			 * @return dhhm00
			 */
			public  String getDhhm00() {
			return this.dhhm00;
			}
				/**
			 * 生育证号
			 * @hibernate.property column="syzh00"
			 * @return syzh00
			 */
			public  String getSyzh00() {
			return this.syzh00;
			}
				/**
			 * 胎次
			 * @hibernate.property column="tc0000"
			 * @return tc0000
			 */
				/**
			 * 签发者签名
			 * @hibernate.property column="qfzqm0"
			 * @return qfzqm0
			 */
			public  String getQfzqm0() {
			return this.qfzqm0;
			}
				/**
			 * 签发情况（1、已发2、未发）
			 * @hibernate.property column="qfqk00"
			 * @return qfqk00
			 */
			public  String getQfqk00() {
			return this.qfqk00;
			}
				/**
			 * 医学证明类型（1、首次签发2、换发3、补发）
			 * @hibernate.property column="yxzmlx"
			 * @return yxzmlx
			 */
			public  String getYxzmlx() {
			return this.yxzmlx;
			}
				/**
			 * 作废标志（0作废）
			 * @hibernate.property column="zfbz00"
			 * @return zfbz00
			 */
			public  String getZfbz00() {
			return this.zfbz00;
			}
				/**
			 * 特殊情况（可能有下拉）
			 * @hibernate.property column="tsqk00"
			 * @return tsqk00
			 */
			public  String getTsqk00() {
			return this.tsqk00;
			}
				/**
			 * 备注
			 * @hibernate.property column="bz0000"
			 * @return bz0000
			 */
			public  String getBz0000() {
			return this.bz0000;
			}
				/**
			 * 卡号
			 * @hibernate.property column="kh0000"
			 * @return kh0000
			 */
			public  String getKh0000() {
			return this.kh0000;
			}
				/**
			 * 打印状态（是否打印，打印次数）不允许打印为-1允许为0,1,2……
			 * @hibernate.property column="dyzt00"
			 * @return dyzt00
			 */
			public  String getDyzt00() {
			return this.dyzt00;
			}
				/**
			 * 居民健康档案号
			 * @hibernate.property column="jkdah0"
			 * @return jkdah0
			 */
			public  String getJkdah0() {
			return this.jkdah0;
			}
				/**
			 * 最后编辑者id
			 * @hibernate.property column="zhbjid"
			 * @return zhbjid
			 */
			public  String getZhbjid() {
			return this.zhbjid;
			}
				/**
			 * 最后编辑者
			 * @hibernate.property column="zhbjz0"
			 * @return zhbjz0
			 */
			public  String getZhbjz0() {
			return this.zhbjz0;
			}
				/**
			 * 最后操作日期
			 * @hibernate.property column="jdrq00"
			 * @return jdrq00
			 */
				/**
			 * 家庭地址：省
			 * @hibernate.property column="famshe"
			 * @return famshe
			 */
			public  String getFamshe() {
			return this.famshe;
			}
				/**
			 * 家庭地址：市
			 * @hibernate.property column="famshi"
			 * @return famshi
			 */
			public  String getFamshi() {
			return this.famshi;
			}
				/**
			 * 家庭地址：县(区)
			 * @hibernate.property column="famxia"
			 * @return famxia
			 */
			public  String getFamxia() {
			return this.famxia;
			}
				/**
			 * 家庭地址：镇(乡)
			 * @hibernate.property column="famzhe"
			 * @return famzhe
			 */
			public  String getFamzhe() {
			return this.famzhe;
			}
				/**
			 * 家庭地址：村(居)
			 * @hibernate.property column="famcun"
			 * @return famcun
			 */
			public  String getFamcun() {
			return this.famcun;
			}
				/**
			 * 家庭地址：门牌号
			 * @hibernate.property column="fammph"
			 * @return fammph
			 */
			public  String getFammph() {
			return this.fammph;
			}
				/**
			 * 领证人姓名
			 * @hibernate.property column="lzrnam"
			 * @return lzrnam
			 */
			public  String getLzrnam() {
			return this.lzrnam;
			}
				/**
			 * 领证人关系
			 * @hibernate.property column="lzrgx0"
			 * @return lzrgx0
			 */
			public  String getLzrgx0() {
			return this.lzrgx0;
			}
				/**
			 * 领证人证件类别
			 * @hibernate.property column="lzrlb0"
			 * @return lzrlb0
			 */
			public  String getLzrlb0() {
			return this.lzrlb0;
			}
				/**
			 * 领证人证件号
			 * @hibernate.property column="lzrsfz"
			 * @return lzrsfz
			 */
			public  String getLzrsfz() {
			return this.lzrsfz;
			}
				/**
			 * 医院id
			 * @hibernate.property column="yyid00"
			 * @return yyid00
			 */
			public  String getYyid00() {
			return this.yyid00;
			}

					/**
		 *	@param cszmid 出生医学证明内码
			 */
			public  void setCszmid(String cszmid)
			{
				this.cszmid=cszmid;
			}
				/**
		 *	@param mqkh00 母亲卡号
			 */
			public  void setMqkh00(String mqkh00)
			{
				this.mqkh00=mqkh00;
			}
				/**
		 *	@param qfdw00 签发单位
			 */
			public  void setQfdw00(String qfdw00)
			{
				this.qfdw00=qfdw00;
			}
				/**
		 *	@param ffrq00 发放日期
			 */
				/**
		 *	@param cszmbh 出生医学证明编号
			 */
			public  void setCszmbh(String cszmbh)
			{
				this.cszmbh=cszmbh;
			}
				/**
		 *	@param bybh00 病案编号
			 */
			public  void setBybh00(String bybh00)
			{
				this.bybh00=bybh00;
			}
				/**
		 *	@param ysxm00 婴儿姓名
			 */
			public  void setYsxm00(String ysxm00)
			{
				this.ysxm00=ysxm00;
			}
				/**
		 *	@param xb0000 性别
			 */
				/**
		 *	@param csrq00 出生日期
			 */
				/**
		 *	@param csyz00 出生孕周
			 */
				/**
		 *	@param tz0000 出生体重
			 */
				/**
		 *	@param sc0000 出生身长
			 */
				/**
		 *	@param jkzk00 健康状况（1、良好2、一般3、差）
			 */
			public  void setJkzk00(String jkzk00)
			{
				this.jkzk00=jkzk00;
			}
				/**
		 *	@param jsz000 接生者
			 */
			public  void setJsz000(String jsz000)
			{
				this.jsz000=jsz000;
			}
				/**
		 *	@param csdshe 出生地：省
			 */
			public  void setCsdshe(String csdshe)
			{
				this.csdshe=csdshe;
			}
				/**
		 *	@param csdshi 出生地：市
			 */
			public  void setCsdshi(String csdshi)
			{
				this.csdshi=csdshi;
			}
				/**
		 *	@param csdxia 出生地：县(区)
			 */
			public  void setCsdxia(String csdxia)
			{
				this.csdxia=csdxia;
			}
				/**
		 *	@param csdzhe 出生地：镇(乡)
			 */
			public  void setCsdzhe(String csdzhe)
			{
				this.csdzhe=csdzhe;
			}
				/**
		 *	@param csdcun 出生地：村(居)
			 */
			public  void setCsdcun(String csdcun)
			{
				this.csdcun=csdcun;
			}
				/**
		 *	@param csdmph 出生地：门牌号
			 */
			public  void setCsdmph(String csdmph)
			{
				this.csdmph=csdmph;
			}
				/**
		 *	@param gsdshe 地址统计归属（户口）：省
			 */
			public  void setGsdshe(String gsdshe)
			{
				this.gsdshe=gsdshe;
			}
				/**
		 *	@param gsdshi 地址统计归属（户口）：市
			 */
			public  void setGsdshi(String gsdshi)
			{
				this.gsdshi=gsdshi;
			}
				/**
		 *	@param gsdxia 地址统计归属（户口）：县(区)
			 */
			public  void setGsdxia(String gsdxia)
			{
				this.gsdxia=gsdxia;
			}
				/**
		 *	@param gsdzhe 地址统计归属（户口）：镇(乡)
			 */
			public  void setGsdzhe(String gsdzhe)
			{
				this.gsdzhe=gsdzhe;
			}
				/**
		 *	@param gsdcun 地址统计归属（户口）：村(居)
			 */
			public  void setGsdcun(String gsdcun)
			{
				this.gsdcun=gsdcun;
			}
				/**
		 *	@param gsdmph 地址统计归属（户口）：门牌号
			 */
			public  void setGsdmph(String gsdmph)
			{
				this.gsdmph=gsdmph;
			}
				/**
		 *	@param mqbh00 母亲编号
			 */
			public  void setMqbh00(String mqbh00)
			{
				this.mqbh00=mqbh00;
			}
				/**
		 *	@param mqsfz0 母亲身份证
			 */
			public  void setMqsfz0(String mqsfz0)
			{
				this.mqsfz0=mqsfz0;
			}
				/**
		 *	@param mqnl00 母亲年龄
			 */
				/**
		 *	@param mqgj00 母亲国籍
			 */
			public  void setMqgj00(String mqgj00)
			{
				this.mqgj00=mqgj00;
			}
				/**
		 *	@param mqmz00 母亲民族
			 */
			public  void setMqmz00(String mqmz00)
			{
				this.mqmz00=mqmz00;
			}
				/**
		 *	@param fqbh00 父亲编号
			 */
			public  void setFqbh00(String fqbh00)
			{
				this.fqbh00=fqbh00;
			}
				/**
		 *	@param fqsfz0 父亲身份证
			 */
			public  void setFqsfz0(String fqsfz0)
			{
				this.fqsfz0=fqsfz0;
			}
				/**
		 *	@param fqnl00 父亲年龄
			 */
				/**
		 *	@param fqgj00 父亲国籍
			 */
			public  void setFqgj00(String fqgj00)
			{
				this.fqgj00=fqgj00;
			}
				/**
		 *	@param fqmz00 父亲民族
			 */
			public  void setFqmz00(String fqmz00)
			{
				this.fqmz00=fqmz00;
			}
				/**
		 *	@param csddfl 出生地点分类（1、医院2、妇幼保健院3、家庭4、其他）
			 */
			public  void setCsddfl(String csddfl)
			{
				this.csddfl=csddfl;
			}
				/**
		 *	@param csdd00 出生地点
			 */
			public  void setCsdd00(String csdd00)
			{
				this.csdd00=csdd00;
			}
				/**
		 *	@param jsjg00 接生机构
			 */
			public  void setJsjg00(String jsjg00)
			{
				this.jsjg00=jsjg00;
			}
				/**
		 *	@param dhhm00 电话号码
			 */
			public  void setDhhm00(String dhhm00)
			{
				this.dhhm00=dhhm00;
			}
				/**
		 *	@param syzh00 生育证号
			 */
			public  void setSyzh00(String syzh00)
			{
				this.syzh00=syzh00;
			}
				/**
		 *	@param tc0000 胎次
			 */
				/**
		 *	@param qfzqm0 签发者签名
			 */
			public  void setQfzqm0(String qfzqm0)
			{
				this.qfzqm0=qfzqm0;
			}
				/**
		 *	@param qfqk00 签发情况（1、已发2、未发）
			 */
			public  void setQfqk00(String qfqk00)
			{
				this.qfqk00=qfqk00;
			}
				/**
		 *	@param yxzmlx 医学证明类型（1、首次签发2、换发3、补发）
			 */
			public  void setYxzmlx(String yxzmlx)
			{
				this.yxzmlx=yxzmlx;
			}
				/**
		 *	@param zfbz00 作废标志（0作废）
			 */
			public  void setZfbz00(String zfbz00)
			{
				this.zfbz00=zfbz00;
			}
				/**
		 *	@param tsqk00 特殊情况（可能有下拉）
			 */
			public  void setTsqk00(String tsqk00)
			{
				this.tsqk00=tsqk00;
			}
				/**
		 *	@param bz0000 备注
			 */
			public  void setBz0000(String bz0000)
			{
				this.bz0000=bz0000;
			}
				/**
		 *	@param kh0000 卡号
			 */
			public  void setKh0000(String kh0000)
			{
				this.kh0000=kh0000;
			}
				/**
		 *	@param dyzt00 打印状态（是否打印，打印次数）不允许打印为-1允许为0,1,2……
			 */
			public  void setDyzt00(String dyzt00)
			{
				this.dyzt00=dyzt00;
			}
				/**
		 *	@param jkdah0 居民健康档案号
			 */
			public  void setJkdah0(String jkdah0)
			{
				this.jkdah0=jkdah0;
			}
				/**
		 *	@param zhbjid 最后编辑者id
			 */
			public  void setZhbjid(String zhbjid)
			{
				this.zhbjid=zhbjid;
			}
				/**
		 *	@param zhbjz0 最后编辑者
			 */
			public  void setZhbjz0(String zhbjz0)
			{
				this.zhbjz0=zhbjz0;
			}
				/**
		 *	@param jdrq00 最后操作日期
			 */
				/**
		 *	@param famshe 家庭地址：省
			 */
			public  void setFamshe(String famshe)
			{
				this.famshe=famshe;
			}
				/**
		 *	@param famshi 家庭地址：市
			 */
			public  void setFamshi(String famshi)
			{
				this.famshi=famshi;
			}
				/**
		 *	@param famxia 家庭地址：县(区)
			 */
			public  void setFamxia(String famxia)
			{
				this.famxia=famxia;
			}
				/**
		 *	@param famzhe 家庭地址：镇(乡)
			 */
			public  void setFamzhe(String famzhe)
			{
				this.famzhe=famzhe;
			}
				/**
		 *	@param famcun 家庭地址：村(居)
			 */
			public  void setFamcun(String famcun)
			{
				this.famcun=famcun;
			}
				/**
		 *	@param fammph 家庭地址：门牌号
			 */
			public  void setFammph(String fammph)
			{
				this.fammph=fammph;
			}
				/**
		 *	@param lzrnam 领证人姓名
			 */
			public  void setLzrnam(String lzrnam)
			{
				this.lzrnam=lzrnam;
			}
				/**
		 *	@param lzrgx0 领证人关系
			 */
			public  void setLzrgx0(String lzrgx0)
			{
				this.lzrgx0=lzrgx0;
			}
				/**
		 *	@param lzrlb0 领证人证件类别
			 */
			public  void setLzrlb0(String lzrlb0)
			{
				this.lzrlb0=lzrlb0;
			}
				/**
		 *	@param lzrsfz 领证人证件号
			 */
			public  void setLzrsfz(String lzrsfz)
			{
				this.lzrsfz=lzrsfz;
			}
				/**
		 *	@param yyid00 医院id
			 */
			public  void setYyid00(String yyid00)
			{
				this.yyid00=yyid00;
			}

		public String toString()
    	{
        	        		return (new ToStringBuilder(this))
        		        		.append("cszmid", getCszmid())
        		        		.toString();
        	    	}

		public int hashCode()	{
    	    	return (new HashCodeBuilder(17, 37))
	    		    		.append(getCszmid())
	    	    	.toHashCode();
    		}

		public boolean equals(Object o) {
		    if ( !(o instanceof Fy_csyxzm) ) {
        		return false;
    		}
		    if(o==this) {
                return true;
            }
    		Fy_csyxzm me=(Fy_csyxzm)o;
	    		        return new EqualsBuilder()
	        	        		.append(getCszmid(),me.getCszmid())
	                	.isEquals();
    	}

		public void setWts000(String wts000) {
			this.wts000 = wts000;
		}

		public String getWts000() {
			return wts000;
		}
		public void setZyh000(String zyh000) {
			this.zyh000 = zyh000;
		}

		public String getZyh000() {
			return zyh000;
		}

		public String getCsqhdm() {
			return csqhdm;
		}

		public void setCsqhdm(String csqhdm) {
			this.csqhdm = csqhdm;
		}

		public String getBah000() {
			return bah000;
		}

		public void setBah000(String bah000) {
			this.bah000 = bah000;
		}

		public String getXzqhdm() {
			return xzqhdm;
		}

		public void setXzqhdm(String xzqhdm) {
			this.xzqhdm = xzqhdm;
		}

		public String getZzjgdm() {
			return zzjgdm;
		}

		public void setZzjgdm(String zzjgdm) {
			this.zzjgdm = zzjgdm;
		}

		public String getJglbdm() {
			return jglbdm;
		}

		public void setJglbdm(String jglbdm) {
			this.jglbdm = jglbdm;
		}

		public String getMqqhdm() {
			return mqqhdm;
		}

		public void setMqqhdm(String mqqhdm) {
			this.mqqhdm = mqqhdm;
		}

		public String getLxdh00() {
			return lxdh00;
		}

		public void setLxdh00(String lxdh00) {
			this.lxdh00 = lxdh00;
		}

	 public String getFfrq00() {
		 return ffrq00;
	 }

	 public String getXb0000() {
		 return xb0000;
	 }

	 public String getCsrq00() {
		 return csrq00;
	 }

	 public String getCsyz00() {
		 return csyz00;
	 }

	 public String getTz0000() {
		 return tz0000;
	 }

	 public String getSc0000() {
		 return sc0000;
	 }

	 public String getMqnl00() {
		 return mqnl00;
	 }

	 public String getFqnl00() {
		 return fqnl00;
	 }

	 public String getTc0000() {
		 return tc0000;
	 }

	 public String getJdrq00() {
		 return jdrq00;
	 }

	 public String getCsyzt0() {
		 return csyzt0;
	 }

	 public String getLzrq00() {
		 return lzrq00;
	 }

	 public String getMqpic0() {
		 return mqpic0;
	 }

	 public String getDyrq00() {
		 return dyrq00;
	 }

	 public String getCyrq00() {
		 return cyrq00;
	 }

	 public String getZhbjsj() {
		 return zhbjsj;
	 }

	 public String getZfsj00() {
		 return zfsj00;
	 }


 }
