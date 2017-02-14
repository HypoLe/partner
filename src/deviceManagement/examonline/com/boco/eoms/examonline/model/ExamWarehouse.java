package com.boco.eoms.examonline.model;

public class ExamWarehouse implements java.io.Serializable {
  private String subId;
  private String importId;
  private String title;
  private String options;
  private String result;
  private int difficulty;      //难度(初级1 中级2)
  private int issueType;       //试题类型(练习1 考试2  目前只有考试2)
  private int contype;         //题目类型(单选题1 多选题2 判断题3 简答题4 论述题5)
  private int value;           //分值
  private String specialty;       //专业
  private int manufacturer;    //厂商
  private String comment;
  private String image;
  private int deleted;
  private String difficultyName;
  private String specialtyName;
  private String manufacturerName;
  private String typeSelId;//该字段不入库只用于储存生成试卷时产生的typesel
  
  
  public String getTypeSelId() {
	return typeSelId;
}

public void setTypeSelId(String typeSelId) {
	this.typeSelId = typeSelId;
}

public int getDifficulty() {
    return difficulty;
  }

  public String getSpecialty() {
    return specialty;
  }

  public String getResult() {
    return result;
  }

  public int getIssueType() {
    return issueType;
  }

  public String getOptions() {
    return options;
  }

  public String getComment() {
    return comment;
  }
  public int getManufacturer() {
    return manufacturer;
  }

  public String getTitle() {
    return title;
  }

  public String getImage() {
    return image;
  }

  public int getValue() {
    return value;
  }

  public String getImportId() {
    return importId;
  }

  public void setSubId(String subId) {
    this.subId = subId;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public void setIssueType(int issueType) {
    this.issueType = issueType;
  }

  public void setOptions(String options) {
    this.options = options;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  public void setManufacturer(int manufacturer) {
    this.manufacturer = manufacturer;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setImportId(String importId) {
    this.importId = importId;
  }

  public void setDeleted(int deleted) {
    this.deleted = deleted;
  }

  public void setDifficultyName(String difficultyName) {
    this.difficultyName = difficultyName;
  }

  public void setSpecialtyName(String specialtyName) {
    this.specialtyName = specialtyName;
  }

  public void setManufacturerName(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }

  public String getSubId() {
    return subId;
  }

  public int getDeleted() {
    return deleted;
  }

  public String getDifficultyName() {
    return difficultyName;
  }

  public String getSpecialtyName() {
    return specialtyName;
  }

  public String getManufacturerName() {
    return manufacturerName;
  }

 
  public ExamWarehouse() {
  }

public int getContype() {
	return contype;
}

public void setContype(int contype) {
	this.contype = contype;
}

}
