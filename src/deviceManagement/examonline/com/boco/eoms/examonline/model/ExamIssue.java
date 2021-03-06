package com.boco.eoms.examonline.model;

/**
 * Examissue generated by MyEclipse Persistence Tools
 */

public class ExamIssue {

	// Fields

	private String subId;
	private String issueid;
	private String importId;
	private String title;
	private String options;
	private String result;
	private Integer difficulty;
	private Integer issueType;
	private Integer value;
	private String specialty;
	private Integer manufacturer;
	private String comment;
	private String image;
	private Integer deleted;
	private Integer contype;

	// Constructors

	/** default constructor */
	public ExamIssue() {
	}

	/** minimal constructor */
	public ExamIssue(String issueid, String importId, String title,
			String options, String result, Integer difficulty,
			Integer issueType, String specialty, Integer manufacturer) {
		this.issueid = issueid;
		this.importId = importId;
		this.title = title;
		this.options = options;
		this.result = result;
		this.difficulty = difficulty;
		this.issueType = issueType;
		this.specialty = specialty;
		this.manufacturer = manufacturer;
	}

	/** full constructor */
	public ExamIssue(String issueid, String importId, String title,
			String options, String result, Integer difficulty,
			Integer issueType, Integer value, String specialty,
			Integer manufacturer, String comment, String image,
			Integer deleted, Integer contype) {
		this.issueid = issueid;
		this.importId = importId;
		this.title = title;
		this.options = options;
		this.result = result;
		this.difficulty = difficulty;
		this.issueType = issueType;
		this.value = value;
		this.specialty = specialty;
		this.manufacturer = manufacturer;
		this.comment = comment;
		this.image = image;
		this.deleted = deleted;
		this.contype = contype;
	}

	// Property accessors

	public String getSubId() {
		return this.subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getIssueid() {
		return this.issueid;
	}

	public void setIssueid(String issueid) {
		this.issueid = issueid;
	}

	public String getImportId() {
		return this.importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return this.options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getIssueType() {
		return this.issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Integer getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getContype() {
		return this.contype;
	}

	public void setContype(Integer contype) {
		this.contype = contype;
	}

}