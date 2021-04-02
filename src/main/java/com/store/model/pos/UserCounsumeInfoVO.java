package com.store.model.pos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserCounsumeInfoVO {

    private String cardNo;
    private String cashPayRate;
    private String point;
    private List<CounsemeDetailVO> detailList;
}
