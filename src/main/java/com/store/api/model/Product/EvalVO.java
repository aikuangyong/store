package com.store.api.model.Product;

import com.store.model.OrderevalModel;
import com.store.utils.DateUtil;

public class EvalVO {

    private String icon;
    private String nickname;
    private String score;
    private String evalcontent;
    private String evaltime;

    public EvalVO(OrderevalModel model) {
        this.icon = model.getUsericon();
        this.nickname = model.getNickname();
        this.evalcontent = model.getEvalcontent();
        if (null != model.getEvaltime()) {
            this.evaltime = DateUtil.sdfTime.format(model.getEvaltime());
        } else {
            this.evaltime = null;
        }
        this.score = model.getEvalscore();
    }

    public EvalVO() {
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEvalcontent() {
        return evalcontent;
    }

    public void setEvalcontent(String evalcontent) {
        this.evalcontent = evalcontent;
    }

    public String getEvaltime() {
        return evaltime;
    }

    public void setEvaltime(String evaltime) {
        this.evaltime = evaltime;
    }
}