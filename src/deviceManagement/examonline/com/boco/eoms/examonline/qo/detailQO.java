package com.boco.eoms.examonline.qo;

public class detailQO {
  public detailQO() {
  }
  String title = "";
  String options = "";
  String answer = "";
  int right = 0;
  String image = "";
  String submitTime = "";
  Integer contype;   //题型
  Integer mark;   //得分
  String qa; //问答题回答

  public Integer getMark() {
	return mark;
}

public void setMark(Integer mark) {
	this.mark = mark;
}

public String getQa() {
	return qa;
}

public void setQa(String qa) {
	this.qa = qa;
}

public Integer getContype() {
	return contype;
}

public void setContype(Integer contype) {
	this.contype = contype;
}

public String getOptions() {
    return options;
  }

  public String getAnswer() {
    return answer;
  }

  public String getTitle() {
    return title;
  }

  public void setRight(int right) {
    this.right = right;
  }

  public void setOptions(String options) {
    this.options = options;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setSubmitTime(String submitTime) {
    this.submitTime = submitTime;
  }

  public int getRight() {
    return right;
  }

  public String getImage() {
    return image;
  }

  public String getSubmitTime() {
    return submitTime;
  }

}
