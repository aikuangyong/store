package com.store.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.store.dao.SysmenuMapper;
import com.store.dao.SysmenuroleMapper;
import com.store.model.SysmenuModel;
import com.store.model.ResultData;
import com.store.model.SysmenuroleModel;
import com.store.model.SysroleModel;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("sysmenuService")
public class SysmenuService {

    @Autowired
    private SysmenuMapper sysmenuMapper;

    @Autowired
    private SysmenuroleMapper sysmrMapper;

    @Autowired
    private ExcelUtil<SysmenuModel> excelUtil;

    public XSSFWorkbook export(SysmenuModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SysmenuModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    public ResultData getData(SysmenuModel model) {
        ResultData returnData = new ResultData();
        try {
            returnData.setDataList(this.getList(model));
            int dataCount = this.getCount(model);
            returnData.setPageCount(dataCount, model.getPageSize());
            returnData.setDataCount(dataCount);
            returnData.setState(ConstantVariable.SUCCESS);
            returnData.setPageNumber(model.getPageNumber());
        } catch (Exception e) {
            returnData.setState(ConstantVariable.ERROR);
            returnData.setMsg(e.getMessage());
        }
        return returnData;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static List<SysmenuModel> buildByRecursive(List<SysmenuModel> treeNodes) {
        List<SysmenuModel> trees = new ArrayList<SysmenuModel>();
        for (SysmenuModel treeNode : treeNodes) {
            if ("0".equals(treeNode.getParentid())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static SysmenuModel findChildren(SysmenuModel treeNode, List<SysmenuModel> treeNodes) {
        for (SysmenuModel it : treeNodes) {
            if (treeNode.getMenuid().equals(it.getParentid())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<SysmenuModel>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


    public List<SysmenuModel> getMenuByRole(SysmenuModel model) {
        List<SysmenuModel> dataList = sysmenuMapper.getMenuByRole(model);
        List<SysmenuModel> returnList = buildByRecursive(dataList);
        for (SysmenuModel menu : returnList) {
            if (!"0".equals(menu.getParentid())) {
                returnList.remove(menu);
            }
        }
        return buildByRecursive(returnList);
    }

    public List<SysmenuModel> getList(SysmenuModel model) {
        List<SysmenuModel> dataList = sysmenuMapper.getList(model);
        List<SysmenuModel> returnList = buildByRecursive(dataList);
        for (SysmenuModel menu : returnList) {
            if (!"0".equals(menu.getParentid())) {
                returnList.remove(menu);
            }
        }
        return buildByRecursive(returnList);
    }

    public int getCount(SysmenuModel model) {
        return sysmenuMapper.getCount(model);
    }

    public SysmenuModel insert(SysmenuModel model) {
        sysmenuMapper.insert(model);
        return model;
    }

    public SysmenuModel update(SysmenuModel model) {
        sysmenuMapper.update(model);
        return model;
    }

    public List<SysmenuModel> batchUpdate(List<SysmenuModel> modelList) {
        for (SysmenuModel model : modelList) {
            sysmenuMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SysmenuModel model) {
        sysmenuMapper.disableOrEnable(model);
    }

    public void delete(SysmenuModel model) {
        sysmenuMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        SysmenuModel model = new SysmenuModel();
        model.setIdList(idList);
        sysmenuMapper.delete(model);
        return ResultData.toSuccessString();
    }

}