package com.boco.eoms.examonline.model;
import java.sql.Timestamp;

/**
 * 试题导入详细信息
 * @author Administrator
 *
 */
public class ExamInfo {
  private String importId;
  private String importUser;
  private Timestamp importTime;
  private int difficulty;
  private int manufacturer;
  private int deleted;
  private String specialty = "0";
  private int auditing = 0;
  private String specialtyName = "";
  private String statusName = "";
  private String comment = "";
  private int contype;
  private String subSpecialty;   //专业子类型（如果specialty是无线专业，子类型就分为TD、GSM）
  
public ExamInfo() {
  }

  public String getImportUser() {
    return importUser;
  }

  public Timestamp getImportTime() {
    return importTime;
  }

  public void setImportId(String importId) {
    this.importId = importId;
  }

  public void setImportUser(String importUser) {
    this.importUser = importUser;
  }

  public void setImportTime(Timestamp importTime) {
    this.importTime = importTime;
  }

  public void setDeleted(int deleted) {
    this.deleted = deleted;
  }

  public void setAuditing(int auditing) {
    this.auditing = auditing;
  }

  public void setSpecialtyName(String specialtyName) {
    this.specialtyName = specialtyName;
  }

  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getImportId() {
    return importId;
  }

  public int getDeleted() {
    return deleted;
  }

  public int getAuditing() {
    return auditing;
  }

  public String getSpecialtyName() {
    return specialtyName;
  }

  public String getSpecialty() {
    return specialty;
  }

  public String getStatusName() {
    return statusName;
  }

  public String getComment() {
    return comment;
  }

public int getDifficulty() {
	return difficulty;
}

public void setDifficulty(int difficulty) {
	this.difficulty = difficulty;
}

public int getManufacturer() {
	return manufacturer;
}

public void setManufacturer(int manufacturer) {
	this.manufacturer = manufacturer;
}

public int getContype() {
	return contype;
}

public void setContype(int contype) {
	this.contype = contype;
}

public String getSubSpecialty() {
	return subSpecialty;
}

public void setSubSpecialty(String subSpecialty) {
	this.subSpecialty = subSpecialty;
}

}
