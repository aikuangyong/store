package com.store.model;

public class RegularDate extends PageModel{

    private String dateinfo;
    private String dateseq;
    private String weeknum;
    private String coditionDay;

    public String getCoditionDay() {
        return coditionDay;
    }

    public void setCoditionDay(String coditionDay) {
        this.coditionDay = coditionDay;
    }

    public String getDateinfo() {
        return dateinfo;
    }

    public void setDateinfo(String dateinfo) {
        this.dateinfo = dateinfo;
    }

    public String getDateseq() {
        return dateseq;
    }

    public void setDateseq(String dateseq) {
        this.dateseq = dateseq;
    }

    public String getWeeknum() {
        return weeknum;
    }

    public void setWeeknum(String weeknum) {
        this.weeknum = weeknum;
    }
}