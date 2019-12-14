/**
 *
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo;

import com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity.*;

import java.io.Serializable;
import java.util.List;


/**
 *<p>Title:ClinicalDocument</p>
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 上午9:18:51
 */

public class ClinicalDocument implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Version version;
	private Ehr ehr;
	private String title;
	private Org org;
	private Id id;
	private String effectiveTime;
	private RecordTarget recordTarget;
	private List<Component> component;
	private ContactPerson contactPerson;
	private DeptOfficer deptOfficer;
	private DeptOfficer directorDoctor;
	private DeptOfficer inChargeDoctor;
	private DeptOfficer residentDoctor;
	private DeptOfficer learningDoctor;
	private DeptOfficer graduateIntern;
	private DeptOfficer intern;
	private DeptOfficer cataloger;
	private DeptOfficer qcDoctor;
	private DeptOfficer qcNurse;
	private Author author;
	private Authenticator authenticator;
	private Participant participant;
	private InFulfillmentOf inFulfillmentOf;
	private String endDate;
	private String summary;
	private String doctor;
	private String healthGuide;
	private String memo;
	private String type;
	private RootDocument rootDocument;


	public ClinicalDocument(){

	}
	public ClinicalDocument(String title,String effectiveTime,Version version,
							Ehr ehr,Id id,Org org,RecordTarget recordTarget,
							List<Component> component,ContactPerson contactPerson,
							DeptOfficer deptOfficer,DeptOfficer directorDoctor,
							DeptOfficer inChargeDoctor,DeptOfficer residentDoctor,
							DeptOfficer learningDoctor,DeptOfficer graduateIntern,
							DeptOfficer intern,DeptOfficer cataloger,
							DeptOfficer qcDoctor,DeptOfficer qcNurse){
		this.title=title;
		this.effectiveTime=effectiveTime;
		this.version=version;
		this.ehr=ehr;
		this.id=id;
		this.org=org;
		this.recordTarget=recordTarget;
		this.component=component;
		this.contactPerson=contactPerson;
		this.deptOfficer=deptOfficer;
		this.directorDoctor=directorDoctor;
		this.inChargeDoctor=inChargeDoctor;
		this.residentDoctor=residentDoctor;
		this.learningDoctor=learningDoctor;
		this.graduateIntern=graduateIntern;
		this.intern=intern;
		this.cataloger=cataloger;
		this.qcDoctor=qcDoctor;
		this.qcNurse=qcNurse;

	}


	public RootDocument getRootDocument() {
		return rootDocument;
	}
	public void setRootDocument(RootDocument rootDocument) {
		this.rootDocument = rootDocument;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the effectiveTime
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * @param effectiveTime the effectiveTime to set
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
	/**
	 * @return the ehr
	 */
	public Ehr getEhr() {
		return ehr;
	}
	/**
	 * @param ehr the ehr to set
	 */
	public void setEhr(Ehr ehr) {
		this.ehr = ehr;
	}
	/**
	 * @return the id
	 */
	public Id getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}
	/**
	 * @return the org
	 */
	public Org getOrg() {
		return org;
	}
	/**
	 * @param org the org to set
	 */
	public void setOrg(Org org) {
		this.org = org;
	}
	/**
	 * @return the recordTarget
	 */
	public RecordTarget getRecordTarget() {
		return recordTarget;
	}
	/**
	 * @param recordTarget the recordTarget to set
	 */
	public void setRecordTarget(RecordTarget recordTarget) {
		this.recordTarget = recordTarget;
	}

	/**
	 * @return the component
	 */
	public List<Component> getComponent() {
		return component;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(List<Component> component) {
		this.component = component;
	}
	/**
	 * @return the contactPerson
	 */
	public ContactPerson getContactPerson() {
		return contactPerson;
	}
	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(ContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}
	/**
	 * @return the deptOfficer
	 */
	public DeptOfficer getDeptOfficer() {
		return deptOfficer;
	}
	/**
	 * @param deptOfficer the deptOfficer to set
	 */
	public void setDeptOfficer(DeptOfficer deptOfficer) {
		this.deptOfficer = deptOfficer;
	}
	/**
	 * @return the directorDoctor
	 */
	public DeptOfficer getDirectorDoctor() {
		return directorDoctor;
	}
	/**
	 * @param directorDoctor the directorDoctor to set
	 */
	public void setDirectorDoctor(DeptOfficer directorDoctor) {
		this.directorDoctor = directorDoctor;
	}
	/**
	 * @return the inChargeDoctor
	 */
	public DeptOfficer getInChargeDoctor() {
		return inChargeDoctor;
	}
	/**
	 * @param inChargeDoctor the inChargeDoctor to set
	 */
	public void setInChargeDoctor(DeptOfficer inChargeDoctor) {
		this.inChargeDoctor = inChargeDoctor;
	}
	/**
	 * @return the residentDoctor
	 */
	public DeptOfficer getResidentDoctor() {
		return residentDoctor;
	}
	/**
	 * @param residentDoctor the residentDoctor to set
	 */
	public void setResidentDoctor(DeptOfficer residentDoctor) {
		this.residentDoctor = residentDoctor;
	}
	/**
	 * @return the learningDoctor
	 */
	public DeptOfficer getLearningDoctor() {
		return learningDoctor;
	}
	/**
	 * @param learningDoctor the learningDoctor to set
	 */
	public void setLearningDoctor(DeptOfficer learningDoctor) {
		this.learningDoctor = learningDoctor;
	}
	/**
	 * @return the graduateIntern
	 */
	public DeptOfficer getGraduateIntern() {
		return graduateIntern;
	}
	/**
	 * @param graduateIntern the graduateIntern to set
	 */
	public void setGraduateIntern(DeptOfficer graduateIntern) {
		this.graduateIntern = graduateIntern;
	}
	/**
	 * @return the intern
	 */
	public DeptOfficer getIntern() {
		return intern;
	}
	/**
	 * @param intern the intern to set
	 */
	public void setIntern(DeptOfficer intern) {
		this.intern = intern;
	}
	/**
	 * @return the cataloger
	 */
	public DeptOfficer getCataloger() {
		return cataloger;
	}
	/**
	 * @param cataloger the cataloger to set
	 */
	public void setCataloger(DeptOfficer cataloger) {
		this.cataloger = cataloger;
	}
	/**
	 * @return the qcDoctor
	 */
	public DeptOfficer getQcDoctor() {
		return qcDoctor;
	}
	/**
	 * @param qcDoctor the qcDoctor to set
	 */
	public void setQcDoctor(DeptOfficer qcDoctor) {
		this.qcDoctor = qcDoctor;
	}
	/**
	 * @return the qcNurse
	 */
	public DeptOfficer getQcNurse() {
		return qcNurse;
	}
	/**
	 * @param qcNurse the qcNurse to set
	 */
	public void setQcNurse(DeptOfficer qcNurse) {
		this.qcNurse = qcNurse;
	}
	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}
	/**
	 * @return the authenticator
	 */
	public Authenticator getAuthenticator() {
		return authenticator;
	}
	/**
	 * @param authenticator the authenticator to set
	 */
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	/**
	 * @return the participant
	 */
	public Participant getParticipant() {
		return participant;
	}
	/**
	 * @param participant the participant to set
	 */
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	/**
	 * @return the inFulfillmentOf
	 */
	public InFulfillmentOf getInFulfillmentOf() {
		return inFulfillmentOf;
	}
	/**
	 * @param inFulfillmentOf the inFulfillmentOf to set
	 */
	public void setInFulfillmentOf(InFulfillmentOf inFulfillmentOf) {
		this.inFulfillmentOf = inFulfillmentOf;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the doctor
	 */
	public String getDoctor() {
		return doctor;
	}
	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	/**
	 * @return the healthGuide
	 */
	public String getHealthGuide() {
		return healthGuide;
	}
	/**
	 * @param healthGuide the healthGuide to set
	 */
	public void setHealthGuide(String healthGuide) {
		this.healthGuide = healthGuide;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
