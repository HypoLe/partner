package com.boco.eoms.infmanage.controller;

import org.apache.struts.action.ActionForm;

public class TawInfSortForm
    extends ActionForm {
    // ���������
    private int infSortId;

    // �����������
    private String infSortName;

    public TawInfSortForm() {

    }

    public int getInfSortId() {
        return infSortId;
    }

    public void setInfSortId(int infSortId) {
        this.infSortId = infSortId;
    }

    public String getInfSortName() {
        return infSortName;
    }

    public void setInfSortName(String infSortName) {
        this.infSortName = infSortName;
    }

}