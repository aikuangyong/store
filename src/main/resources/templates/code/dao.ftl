package com.${scheml}.dao;

import com.${scheml}.model.${name?cap_first}Model;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("${name?uncap_first}Dao")
public interface ${name?cap_first}Mapper {

    public List<${name?cap_first}Model> getList(${name?cap_first}Model model);

    public int getCount(${name?cap_first}Model model);

    public void insert(${name?cap_first}Model model);

    public void update(${name?cap_first}Model model);

    public void disableOrEnable(${name?cap_first}Model model);

    public void delete(${name?cap_first}Model model);
}