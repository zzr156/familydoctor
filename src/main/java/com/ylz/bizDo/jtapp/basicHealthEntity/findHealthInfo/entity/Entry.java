/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity;

import java.io.Serializable;
import java.util.List;


/**
 *<p>Title:Entry</p> 
 *<p>Description:</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-28 下午2:29:37
 */

public class Entry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String onsetTime;
	private String treatTime;
	private String diagnosisDate;
	private Reg reg;
	private Sec sec;
	private Dept dept;
	private Doctor doctor;
	private Symptom symptom;
	private Diagnosis diagnosis;
	private String id;
	private Performer performer;
	private String classCode;
	private SpecimenDeterminer specimenDeterminer;
	private Risk risk;
	private Quantity quantity;
	private RejectReason rejectReason;
	private PlayingDevice playingDevice;
	private Residence residence;
	private In in;
	private Move move;
	private Out out;
	private Cause cause;
	private IllStatus illStatus;
	private InfectiousStatus infectiousStatus;
	private InDiagnosis inDiagnosis;
	private OutDiagnosis outDiagnosis;
	private String deathTime;
	private Value causeOfDeath;
	private InTime inTime;
	private InTime outTime;
	private InDept inDept;
	private InDept outDept;
	private InDept moveDept;
	private String inHospitalDay;
	private Value payStyle;
	private String insuranceNo;
	private Value inCondition;
	private Value inClass;
	private Value moveIn;
	private Value outClass;
	private Value clinicDiagnosis;
	private InTime lastDiagnosisTime;
	private OutDiagnosisMain outDiagnosisMain;
	private String hospitalInfection;
	private String complication;
	private Value isAbnormal;
	private String patientCondition;
	private String examCause;
	private String description;
	private Order order;
	private Value quality;
	private PathologyDiagnosis pathologyDiagnosis;
	private Value poisoningDiagnosis;
	private Value HbsAg;
	private Value HCV_Ab;
	private Value HIV_Ab;
	private DiagnosisComparing diagnosisComparing;
	private String saveTimes;
	private String saveSuccessTimes;
	private Operation operation;
	private Fee fee;
	private Value autopsy;
	private Value bloodType;
	private Value bloodRh;
	private Value transfusionReaction;
	private Value infusionReaction;
	private RedBloodCell redBloodCell;
	private RedBloodCell platelet;
	private RedBloodCell plasma;
	private RedBloodCell wholeBlood;
	private RedBloodCell otherBlood;
	private Value followStatus;
	private String followInterval;
	private Value teachingExample;
	private Value researchExample;
	private Value firstOperation;
	private Value firstTreatment;
	private Value firstExam;
	private Value firstDiagnosis;
	private Value infectious;
	private Value infectiousType;
	private Value infectiousReported;
	private String allergyDrugs;
	private Allergy allergy;
	private GeneralCondition generalCondition;
	private Organ organ;
	private BodyExam bodyExam;
	private GeneralPeople generalPeople;
	private SpecialPeople specialPeople;
	
	
	
	
	
	
	
	
	
	
	
	
	/*@XmlElementWrapper*/
	/*@OneToMany(cascade = CascadeType.ALL)*/
    /*@XmlElements(value = { @XmlElement(name = "group", type = Group.class) })*/
		private List<Group> group;
 /*   @XmlElements(value={ @XmlElement(name="observation",type=Observation.class)})*/
    private List<Observation> observation;
	/*@XmlElement
	private List<Allergy> allergy;
	@XmlElement
	private List<Operation> operations;
	@XmlElement*/
	private OutDiagnosisOther outDiagnosisOther;
	public Entry(){}
	
	public Entry(String onsetTime,String treatTime,String diagnosisDate,
			Reg reg,Sec sec,Dept dept,Doctor doctor,Symptom symptom,Diagnosis diagnosis){
		this.onsetTime=onsetTime;
		this.treatTime=treatTime;
		this.diagnosisDate=diagnosisDate;
		this.reg=reg;
		this.sec=sec;
		this.dept=dept;
		this.doctor=doctor;
		this.symptom=symptom;
		this.diagnosis=diagnosis;
		
	}
	public Entry(Performer performer,PlayingDevice playingDevice,
			Value clinicDiagnosis,String patientCondition,String examCause,
			String description,Value isAbnormal){
		this.performer=performer;
		this.playingDevice=playingDevice;
		this.clinicDiagnosis=clinicDiagnosis;
		this.patientCondition=patientCondition;
		this.examCause=examCause;
		this.description=description;
		this.isAbnormal=isAbnormal;
	}
	public Entry(String onsetTime,String treatTime,String diagnosisDate,Reg reg,Sec sec,Dept dept,
			Doctor doctor,Symptom symptom,Diagnosis diagnosis,String id,Performer performer,String classCode,
			SpecimenDeterminer specimenDeterminer,Risk risk,Quantity quantity,RejectReason rejectReason,
			PlayingDevice playingDevice,Residence residence,In in,Move move,
			Out out,Cause cause,IllStatus illStatus,InfectiousStatus infectiousStatus,
			InDiagnosis inDiagnosis,OutDiagnosis outDiagnosis,String deathTime,
			Value causeOfDeath,InTime inTime,InTime outTime,InDept inDept,
			InDept outDept,InDept moveDept,String inHospitalDay,Value payStyle,String insuranceNo,
			Value inCondition,Value inClass,Value moveIn,Value outClass,Value clinicDiagnosis,
			InTime lastDiagnosisTime,OutDiagnosisMain outDiagnosisMain,
			String hospitalInfection,String complication,Value isAbnormal
			){
		this.onsetTime=onsetTime;
		this.treatTime=treatTime;
		this.diagnosisDate=diagnosisDate;
		this.reg=reg;
		this.sec=sec;
		this.dept=dept;
		this.doctor=doctor;
		this.symptom=symptom;
		this.diagnosis=diagnosis;
		this.id=id;
		this.performer=performer;
		this.classCode=classCode;
		this.specimenDeterminer=specimenDeterminer;
		this.risk=risk;
		this.quantity=quantity;
		this.rejectReason=rejectReason;
		this.playingDevice=playingDevice;
		this.residence=residence;
		this.in=in;
		this.move=move;
		this.out=out;
		this.cause=cause;
		this.illStatus=illStatus;
		this.infectiousStatus=infectiousStatus;
		this.inDiagnosis=inDiagnosis;
		this.outDiagnosis=outDiagnosis;
		this.deathTime=deathTime;
		this.causeOfDeath=causeOfDeath;
		this.inTime=inTime;
		this.outTime=outTime;
		this.inDept=inDept;
		this.outDept=outDept;
		this.moveDept=moveDept;
		this.inHospitalDay=inHospitalDay;
		this.payStyle=payStyle;
		this.insuranceNo=insuranceNo;
		this.inCondition=inCondition;
		this.inClass=inClass;
		this.moveIn=moveIn;
		this.outClass=outClass;
		this.clinicDiagnosis=clinicDiagnosis;
		this.lastDiagnosisTime=lastDiagnosisTime;
		this.outDiagnosisMain=outDiagnosisMain;
		this.hospitalInfection=hospitalInfection;
		this.complication=complication;
		this.isAbnormal=isAbnormal;
	}
	
	
	
	
	
	
	/**
	 * @return the onsetTime
	 */
	public String getOnsetTime() {
		return onsetTime;
	}
	/**
	 * @param onsetTime the onsetTime to set
	 */
	public void setOnsetTime(String onsetTime) {
		this.onsetTime = onsetTime;
	}
	/**
	 * @return the treatTime
	 */
	public String getTreatTime() {
		return treatTime;
	}
	/**
	 * @param treatTime the treatTime to set
	 */
	public void setTreatTime(String treatTime) {
		this.treatTime = treatTime;
	}
	/**
	 * @return the diagnosisDate
	 */
	public String getDiagnosisDate() {
		return diagnosisDate;
	}
	/**
	 * @param diagnosisDate the diagnosisDate to set
	 */
	public void setDiagnosisDate(String diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}
	/**
	 * @return the reg
	 */
	public Reg getReg() {
		return reg;
	}
	/**
	 * @param reg the reg to set
	 */
	public void setReg(Reg reg) {
		this.reg = reg;
	}
	/**
	 * @return the sec
	 */
	public Sec getSec() {
		return sec;
	}
	/**
	 * @param sec the sec to set
	 */
	public void setSec(Sec sec) {
		this.sec = sec;
	}
	/**
	 * @return the dept
	 */
	public Dept getDept() {
		return dept;
	}
	/**
	 * @param dept the dept to set
	 */
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	/**
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}
	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	/**
	 * @return the symptom
	 */
	public Symptom getSymptom() {
		return symptom;
	}
	/**
	 * @param symptom the symptom to set
	 */
	public void setSymptom(Symptom symptom) {
		this.symptom = symptom;
	}
	/**
	 * @return the diagnosis
	 */
	public Diagnosis getDiagnosis() {
		return diagnosis;
	}
	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}
	 
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the performer
	 */
	public Performer getPerformer() {
		return performer;
	}
	/**
	 * @param performer the performer to set
	 */
	public void setPerformer(Performer performer) {
		this.performer = performer;
	}
	/**
	 * @return the classCode
	 */
	public String getClassCode() {
		return classCode;
	}
	/**
	 * @param classCode the classCode to set
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	/**
	 * @return the specimenDeterminer
	 */
	public SpecimenDeterminer getSpecimenDeterminer() {
		return specimenDeterminer;
	}
	/**
	 * @param specimenDeterminer the specimenDeterminer to set
	 */
	public void setSpecimenDeterminer(SpecimenDeterminer specimenDeterminer) {
		this.specimenDeterminer = specimenDeterminer;
	}
	/**
	 * @return the risk
	 */
	public Risk getRisk() {
		return risk;
	}
	/**
	 * @param risk the risk to set
	 */
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	/**
	 * @return the quantity
	 */
	public Quantity getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the rejectReason
	 */
	public RejectReason getRejectReason() {
		return rejectReason;
	}
	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(RejectReason rejectReason) {
		this.rejectReason = rejectReason;
	}
	/**
	 * @return the playingDevice
	 */
	public PlayingDevice getPlayingDevice() {
		return playingDevice;
	}
	/**
	 * @param playingDevice the playingDevice to set
	 */
	public void setPlayingDevice(PlayingDevice playingDevice) {
		this.playingDevice = playingDevice;
	}
	 
	/**
	 * @return the residence
	 */
	public Residence getResidence() {
		return residence;
	}
	/**
	 * @param residence the residence to set
	 */
	public void setResidence(Residence residence) {
		this.residence = residence;
	}
	/**
	 * @return the in
	 */
	public In getIn() {
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(In in) {
		this.in = in;
	}
	
	/**
	 * @return the move
	 */
	public Move getMove() {
		return move;
	}

	/**
	 * @param move the move to set
	 */
	public void setMove(Move move) {
		this.move = move;
	}

	/**
	 * @return the out
	 */
	public Out getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(Out out) {
		this.out = out;
	}
	/**
	 * @return the cause
	 */
	public Cause getCause() {
		return cause;
	}
	/**
	 * @param cause the cause to set
	 */
	public void setCause(Cause cause) {
		this.cause = cause;
	}
	/**
	 * @return the illStatus
	 */
	public IllStatus getIllStatus() {
		return illStatus;
	}
	/**
	 * @param illStatus the illStatus to set
	 */
	public void setIllStatus(IllStatus illStatus) {
		this.illStatus = illStatus;
	}
	/**
	 * @return the infectiousStatus
	 */
	public InfectiousStatus getInfectiousStatus() {
		return infectiousStatus;
	}
	/**
	 * @param infectiousStatus the infectiousStatus to set
	 */
	public void setInfectiousStatus(InfectiousStatus infectiousStatus) {
		this.infectiousStatus = infectiousStatus;
	}
	/**
	 * @return the inDiagnosis
	 */
	public InDiagnosis getInDiagnosis() {
		return inDiagnosis;
	}
	/**
	 * @param inDiagnosis the inDiagnosis to set
	 */
	public void setInDiagnosis(InDiagnosis inDiagnosis) {
		this.inDiagnosis = inDiagnosis;
	}
	/**
	 * @return the outDiagnosis
	 */
	public OutDiagnosis getOutDiagnosis() {
		return outDiagnosis;
	}
	/**
	 * @param outDiagnosis the outDiagnosis to set
	 */
	public void setOutDiagnosis(OutDiagnosis outDiagnosis) {
		this.outDiagnosis = outDiagnosis;
	}
	/**
	 * @return the deathTime
	 */
	public String getDeathTime() {
		return deathTime;
	}
	/**
	 * @param deathTime the deathTime to set
	 */
	public void setDeathTime(String deathTime) {
		this.deathTime = deathTime;
	}
	/**
	 * @return the causeOfDeath
	 */
	public Value getCauseOfDeath() {
		return causeOfDeath;
	}
	/**
	 * @param causeOfDeath the causeOfDeath to set
	 */
	public void setCauseOfDeath(Value causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}
	/**
	 * @return the inTime
	 */
	public InTime getInTime() {
		return inTime;
	}
	/**
	 * @param inTime the inTime to set
	 */
	public void setInTime(InTime inTime) {
		this.inTime = inTime;
	}
	/**
	 * @return the outTime
	 */
	public InTime getOutTime() {
		return outTime;
	}
	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(InTime outTime) {
		this.outTime = outTime;
	}
	/**
	 * @return the inDept
	 */
	public InDept getInDept() {
		return inDept;
	}
	/**
	 * @param inDept the inDept to set
	 */
	public void setInDept(InDept inDept) {
		this.inDept = inDept;
	}
	/**
	 * @return the outDept
	 */
	public InDept getOutDept() {
		return outDept;
	}
	/**
	 * @param outDept the outDept to set
	 */
	public void setOutDept(InDept outDept) {
		this.outDept = outDept;
	}
	/**
	 * @return the moveDept
	 */
	public InDept getMoveDept() {
		return moveDept;
	}
	/**
	 * @param moveDept the moveDept to set
	 */
	public void setMoveDept(InDept moveDept) {
		this.moveDept = moveDept;
	}
	/**
	 * @return the inHospitalDay
	 */
	public String getInHospitalDay() {
		return inHospitalDay;
	}
	/**
	 * @param inHospitalDay the inHospitalDay to set
	 */
	public void setInHospitalDay(String inHospitalDay) {
		this.inHospitalDay = inHospitalDay;
	}
	/**
	 * @return the payStyle
	 */
	public Value getPayStyle() {
		return payStyle;
	}
	/**
	 * @param payStyle the payStyle to set
	 */
	public void setPayStyle(Value payStyle) {
		this.payStyle = payStyle;
	}
	/**
	 * @return the insuranceNo
	 */
	public String getInsuranceNo() {
		return insuranceNo;
	}
	/**
	 * @param insuranceNo the insuranceNo to set
	 */
	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}
	/**
	 * @return the inCondition
	 */
	public Value getInCondition() {
		return inCondition;
	}
	/**
	 * @param inCondition the inCondition to set
	 */
	public void setInCondition(Value inCondition) {
		this.inCondition = inCondition;
	}
	/**
	 * @return the inClass
	 */
	public Value getInClass() {
		return inClass;
	}
	/**
	 * @param inClass the inClass to set
	 */
	public void setInClass(Value inClass) {
		this.inClass = inClass;
	}
	/**
	 * @return the moveIn
	 */
	public Value getMoveIn() {
		return moveIn;
	}
	/**
	 * @param moveIn the moveIn to set
	 */
	public void setMoveIn(Value moveIn) {
		this.moveIn = moveIn;
	}
	/**
	 * @return the outClass
	 */
	public Value getOutClass() {
		return outClass;
	}
	/**
	 * @param outClass the outClass to set
	 */
	public void setOutClass(Value outClass) {
		this.outClass = outClass;
	}
	/**
	 * @return the clinicDiagnosis
	 */
	
	/**
	 * @return the lastDiagnosisTime
	 */
	public InTime getLastDiagnosisTime() {
		return lastDiagnosisTime;
	}
	 

	/**
	 * @return the clinicDiagnosis
	 */
	public Value getClinicDiagnosis() {
		return clinicDiagnosis;
	}

	/**
	 * @param clinicDiagnosis the clinicDiagnosis to set
	 */
	public void setClinicDiagnosis(Value clinicDiagnosis) {
		this.clinicDiagnosis = clinicDiagnosis;
	}

	/**
	 * @param lastDiagnosisTime the lastDiagnosisTime to set
	 */
	public void setLastDiagnosisTime(InTime lastDiagnosisTime) {
		this.lastDiagnosisTime = lastDiagnosisTime;
	}
	/**
	 * @return the outDiagnosisMain
	 */
	public OutDiagnosisMain getOutDiagnosisMain() {
		return outDiagnosisMain;
	}
	/**
	 * @param outDiagnosisMain the outDiagnosisMain to set
	 */
	public void setOutDiagnosisMain(OutDiagnosisMain outDiagnosisMain) {
		this.outDiagnosisMain = outDiagnosisMain;
	}

	/**
	 * @return the hospitalInfection
	 */
	public String getHospitalInfection() {
		return hospitalInfection;
	}
	/**
	 * @param hospitalInfection the hospitalInfection to set
	 */
	public void setHospitalInfection(String hospitalInfection) {
		this.hospitalInfection = hospitalInfection;
	}
	/**
	 * @return the complication
	 */
	public String getComplication() {
		return complication;
	}
	/**
	 * @param complication the complication to set
	 */
	public void setComplication(String complication) {
		this.complication = complication;
	}

	/**
	 * @return the group
	 */
	public List<Group> getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(List<Group> group) {
		this.group = group;
	}

	/**
	 * @return the observation
	 */
	public List<Observation> getObservation() {
		return observation;
	}

	/**
	 * @param observation the observation to set
	 */
	public void setObservation(List<Observation> observation) {
		this.observation = observation;
	}

	/**
	 * @return the isAbnormal
	 */
	public Value getIsAbnormal() {
		return isAbnormal;
	}

	/**
	 * @param isAbnormal the isAbnormal to set
	 */
	public void setIsAbnormal(Value isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	/**
	 * @return the patientCondition
	 */
	public String getPatientCondition() {
		return patientCondition;
	}

	/**
	 * @param patientCondition the patientCondition to set
	 */
	public void setPatientCondition(String patientCondition) {
		this.patientCondition = patientCondition;
	}

	/**
	 * @return the examCause
	 */
	public String getExamCause() {
		return examCause;
	}

	/**
	 * @param examCause the examCause to set
	 */
	public void setExamCause(String examCause) {
		this.examCause = examCause;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the quality
	 */
	public Value getQuality() {
		return quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(Value quality) {
		this.quality = quality;
	}

	
	/**
	 * @return the pathologyDiagnosis
	 */
	public PathologyDiagnosis getPathologyDiagnosis() {
		return pathologyDiagnosis;
	}

	/**
	 * @param pathologyDiagnosis the pathologyDiagnosis to set
	 */
	public void setPathologyDiagnosis(PathologyDiagnosis pathologyDiagnosis) {
		this.pathologyDiagnosis = pathologyDiagnosis;
	}

	/**
	 * @return the poisoningDiagnosis
	 */
	public Value getPoisoningDiagnosis() {
		return poisoningDiagnosis;
	}

	/**
	 * @param poisoningDiagnosis the poisoningDiagnosis to set
	 */
	public void setPoisoningDiagnosis(Value poisoningDiagnosis) {
		this.poisoningDiagnosis = poisoningDiagnosis;
	}

	/**
	 * @return the hbsAg
	 */
	public Value getHbsAg() {
		return HbsAg;
	}

	/**
	 * @param hbsAg the hbsAg to set
	 */
	public void setHbsAg(Value hbsAg) {
		HbsAg = hbsAg;
	}

	/**
	 * @return the hCV_Ab
	 */
	public Value getHCV_Ab() {
		return HCV_Ab;
	}

	/**
	 * @param hCV_Ab the hCV_Ab to set
	 */
	public void setHCV_Ab(Value hCV_Ab) {
		HCV_Ab = hCV_Ab;
	}

	/**
	 * @return the hIV_Ab
	 */
	public Value getHIV_Ab() {
		return HIV_Ab;
	}

	/**
	 * @param hIV_Ab the hIV_Ab to set
	 */
	public void setHIV_Ab(Value hIV_Ab) {
		HIV_Ab = hIV_Ab;
	}

	/**
	 * @return the diagnosisComparing
	 */
	public DiagnosisComparing getDiagnosisComparing() {
		return diagnosisComparing;
	}

	/**
	 * @param diagnosisComparing the diagnosisComparing to set
	 */
	public void setDiagnosisComparing(DiagnosisComparing diagnosisComparing) {
		this.diagnosisComparing = diagnosisComparing;
	}

	/**
	 * @return the saveTimes
	 */
	public String getSaveTimes() {
		return saveTimes;
	}

	/**
	 * @param saveTimes the saveTimes to set
	 */
	public void setSaveTimes(String saveTimes) {
		this.saveTimes = saveTimes;
	}

	/**
	 * @return the saveSuccessTimes
	 */
	public String getSaveSuccessTimes() {
		return saveSuccessTimes;
	}

	/**
	 * @param saveSuccessTimes the saveSuccessTimes to set
	 */
	public void setSaveSuccessTimes(String saveSuccessTimes) {
		this.saveSuccessTimes = saveSuccessTimes;
	}

	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * @return the fee
	 */
	public Fee getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(Fee fee) {
		this.fee = fee;
	}

	/**
	 * @return the autopsy
	 */
	public Value getAutopsy() {
		return autopsy;
	}

	/**
	 * @param autopsy the autopsy to set
	 */
	public void setAutopsy(Value autopsy) {
		this.autopsy = autopsy;
	}

	/**
	 * @return the bloodType
	 */
	public Value getBloodType() {
		return bloodType;
	}

	/**
	 * @param bloodType the bloodType to set
	 */
	public void setBloodType(Value bloodType) {
		this.bloodType = bloodType;
	}

	/**
	 * @return the bloodRh
	 */
	public Value getBloodRh() {
		return bloodRh;
	}

	/**
	 * @param bloodRh the bloodRh to set
	 */
	public void setBloodRh(Value bloodRh) {
		this.bloodRh = bloodRh;
	}

	/**
	 * @return the transfusionReaction
	 */
	public Value getTransfusionReaction() {
		return transfusionReaction;
	}

	/**
	 * @param transfusionReaction the transfusionReaction to set
	 */
	public void setTransfusionReaction(Value transfusionReaction) {
		this.transfusionReaction = transfusionReaction;
	}

	/**
	 * @return the infusionReaction
	 */
	public Value getInfusionReaction() {
		return infusionReaction;
	}

	/**
	 * @param infusionReaction the infusionReaction to set
	 */
	public void setInfusionReaction(Value infusionReaction) {
		this.infusionReaction = infusionReaction;
	}

	/**
	 * @return the redBloodCell
	 */
	public RedBloodCell getRedBloodCell() {
		return redBloodCell;
	}

	/**
	 * @param redBloodCell the redBloodCell to set
	 */
	public void setRedBloodCell(RedBloodCell redBloodCell) {
		this.redBloodCell = redBloodCell;
	}

	/**
	 * @return the platelet
	 */
	public RedBloodCell getPlatelet() {
		return platelet;
	}

	/**
	 * @param platelet the platelet to set
	 */
	public void setPlatelet(RedBloodCell platelet) {
		this.platelet = platelet;
	}

	/**
	 * @return the plasma
	 */
	public RedBloodCell getPlasma() {
		return plasma;
	}

	/**
	 * @param plasma the plasma to set
	 */
	public void setPlasma(RedBloodCell plasma) {
		this.plasma = plasma;
	}

	/**
	 * @return the wholeBlood
	 */
	public RedBloodCell getWholeBlood() {
		return wholeBlood;
	}

	/**
	 * @param wholeBlood the wholeBlood to set
	 */
	public void setWholeBlood(RedBloodCell wholeBlood) {
		this.wholeBlood = wholeBlood;
	}

	/**
	 * @return the otherBlood
	 */
	public RedBloodCell getOtherBlood() {
		return otherBlood;
	}

	/**
	 * @param otherBlood the otherBlood to set
	 */
	public void setOtherBlood(RedBloodCell otherBlood) {
		this.otherBlood = otherBlood;
	}

	/**
	 * @return the followStatus
	 */
	public Value getFollowStatus() {
		return followStatus;
	}

	/**
	 * @param followStatus the followStatus to set
	 */
	public void setFollowStatus(Value followStatus) {
		this.followStatus = followStatus;
	}

	/**
	 * @return the followInterval
	 */
	public String getFollowInterval() {
		return followInterval;
	}

	/**
	 * @param followInterval the followInterval to set
	 */
	public void setFollowInterval(String followInterval) {
		this.followInterval = followInterval;
	}

	/**
	 * @return the teachingExample
	 */
	public Value getTeachingExample() {
		return teachingExample;
	}

	/**
	 * @param teachingExample the teachingExample to set
	 */
	public void setTeachingExample(Value teachingExample) {
		this.teachingExample = teachingExample;
	}

	/**
	 * @return the researchExample
	 */
	public Value getResearchExample() {
		return researchExample;
	}

	/**
	 * @param researchExample the researchExample to set
	 */
	public void setResearchExample(Value researchExample) {
		this.researchExample = researchExample;
	}

	/**
	 * @return the firstOperation
	 */
	public Value getFirstOperation() {
		return firstOperation;
	}

	/**
	 * @param firstOperation the firstOperation to set
	 */
	public void setFirstOperation(Value firstOperation) {
		this.firstOperation = firstOperation;
	}

	/**
	 * @return the firstTreatment
	 */
	public Value getFirstTreatment() {
		return firstTreatment;
	}

	/**
	 * @param firstTreatment the firstTreatment to set
	 */
	public void setFirstTreatment(Value firstTreatment) {
		this.firstTreatment = firstTreatment;
	}

	/**
	 * @return the firstExam
	 */
	public Value getFirstExam() {
		return firstExam;
	}

	/**
	 * @param firstExam the firstExam to set
	 */
	public void setFirstExam(Value firstExam) {
		this.firstExam = firstExam;
	}

	/**
	 * @return the firstDiagnosis
	 */
	public Value getFirstDiagnosis() {
		return firstDiagnosis;
	}

	/**
	 * @param firstDiagnosis the firstDiagnosis to set
	 */
	public void setFirstDiagnosis(Value firstDiagnosis) {
		this.firstDiagnosis = firstDiagnosis;
	}

	/**
	 * @return the infectious
	 */
	public Value getInfectious() {
		return infectious;
	}

	/**
	 * @param infectious the infectious to set
	 */
	public void setInfectious(Value infectious) {
		this.infectious = infectious;
	}

	/**
	 * @return the infectiousType
	 */
	public Value getInfectiousType() {
		return infectiousType;
	}

	/**
	 * @param infectiousType the infectiousType to set
	 */
	public void setInfectiousType(Value infectiousType) {
		this.infectiousType = infectiousType;
	}

	/**
	 * @return the infectiousReported
	 */
	public Value getInfectiousReported() {
		return infectiousReported;
	}

	/**
	 * @param infectiousReported the infectiousReported to set
	 */
	public void setInfectiousReported(Value infectiousReported) {
		this.infectiousReported = infectiousReported;
	}

	/**
	 * @return the allergyDrugs
	 */
	public String getAllergyDrugs() {
		return allergyDrugs;
	}

	/**
	 * @param allergyDrugs the allergyDrugs to set
	 */
	public void setAllergyDrugs(String allergyDrugs) {
		this.allergyDrugs = allergyDrugs;
	}

	 
	/**
	 * @return the outDiagnosisOther
	 */
	public OutDiagnosisOther getOutDiagnosisOther() {
		return outDiagnosisOther;
	}

	/**
	 * @param outDiagnosisOther the outDiagnosisOther to set
	 */
	public void setOutDiagnosisOther(OutDiagnosisOther outDiagnosisOther) {
		this.outDiagnosisOther = outDiagnosisOther;
	}

	/**
	 * @return the allergy
	 */
	public Allergy getAllergy() {
		return allergy;
	}

	/**
	 * @param allergy the allergy to set
	 */
	public void setAllergy(Allergy allergy) {
		this.allergy = allergy;
	}

	/**
	 * @return the generalCondition
	 */
	public GeneralCondition getGeneralCondition() {
		return generalCondition;
	}

	/**
	 * @param generalCondition the generalCondition to set
	 */
	public void setGeneralCondition(GeneralCondition generalCondition) {
		this.generalCondition = generalCondition;
	}

	/**
	 * @return the organ
	 */
	public Organ getOrgan() {
		return organ;
	}

	/**
	 * @param organ the organ to set
	 */
	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	/**
	 * @return the bodyExam
	 */
	public BodyExam getBodyExam() {
		return bodyExam;
	}

	/**
	 * @param bodyExam the bodyExam to set
	 */
	public void setBodyExam(BodyExam bodyExam) {
		this.bodyExam = bodyExam;
	}

	/**
	 * @return the generalPeople
	 */
	public GeneralPeople getGeneralPeople() {
		return generalPeople;
	}

	/**
	 * @param generalPeople the generalPeople to set
	 */
	public void setGeneralPeople(GeneralPeople generalPeople) {
		this.generalPeople = generalPeople;
	}

	/**
	 * @return the specialPeople
	 */
	public SpecialPeople getSpecialPeople() {
		return specialPeople;
	}

	/**
	 * @param specialPeople the specialPeople to set
	 */
	public void setSpecialPeople(SpecialPeople specialPeople) {
		this.specialPeople = specialPeople;
	}

 
	
	
	

}
