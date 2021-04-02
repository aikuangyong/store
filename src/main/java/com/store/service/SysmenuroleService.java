package com.store.service;

import com.store.dao.SysmenuroleMapper;
import com.store.model.SysmenuModel;
import com.store.model.SysmenuroleModel;
import com.store.model.ResultData;
import com.store.model.SysroleModel;
import com.store.utils.ConstantVariable;
import com.store.utils.ExcelUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@Service("sysmenuroleService")
public class SysmenuroleService {

    @Autowired
    private SysmenuroleMapper sysmenuroleMapper;

    @Autowired
    private ExcelUtil<SysmenuroleModel> excelUtil;

    public XSSFWorkbook export(SysmenuroleModel model) {
        model.setPageSize(Integer.MAX_VALUE);
        model.setPageNow(0);
        List<SysmenuroleModel> dataList = getList(model);
        return excelUtil.exportExcel(model, dataList);
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static List<SysmenuroleModel> buildByRecursive(List<SysmenuroleModel> treeNodes) {
        List<SysmenuroleModel> trees = new ArrayList<>();
        for (SysmenuroleModel treeNode : treeNodes) {
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
    public static SysmenuroleModel findChildren(SysmenuroleModel treeNode, List<SysmenuroleModel> treeNodes) {
        for (SysmenuroleModel it : treeNodes) {
            if (treeNode.getMenuid().equals(it.getParentid())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<SysmenuroleModel>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


    public List<SysmenuroleModel> getMenuByRole(SysroleModel model) {
        SysmenuroleModel mrModel = new SysmenuroleModel();
        mrModel.setRoleid(model.getRoleid());
        List<SysmenuroleModel> dataList = sysmenuroleMapper.getMenuByRole(mrModel);
        List<SysmenuroleModel> returnList = buildByRecursive(dataList);
        for (SysmenuroleModel menu : returnList) {
            if (!"0".equals(menu.getParentid())) {
                returnList.remove(menu);
            }
        }
        return buildByRecursive(returnList);
    }

    public Set<String> getPermsByRoleId(String roleId) {
        Set<String> perms = new HashSet<>(0);
        SysmenuroleModel mrModel = new SysmenuroleModel();
        mrModel.setRoleid(roleId);
        List<SysmenuroleModel> dataList = sysmenuroleMapper.getList(mrModel);
        dataList.forEach(sysmenuroleModel ->
                perms.add(sysmenuroleModel.getMenuname())
        );
        return perms;
    }

    public ResultData getData(SysmenuroleModel model) {
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

    public SysmenuroleModel getModelById(SysmenuroleModel model) {
        List<SysmenuroleModel> dataList = getList(model);
        if (dataList.size() > 0) {
            return dataList.get(0);
        }
        return null;
    }

    public List<SysmenuroleModel> getList(SysmenuroleModel model) {
        return sysmenuroleMapper.getList(model);
    }

    public int getCount(SysmenuroleModel model) {
        return sysmenuroleMapper.getCount(model);
    }

    public SysmenuroleModel insert(SysmenuroleModel model) {
        sysmenuroleMapper.insert(model);
        return model;
    }

    public SysmenuroleModel update(SysmenuroleModel model) {
        sysmenuroleMapper.update(model);
        return model;
    }

    public List<SysmenuroleModel> batchUpdate(List<SysmenuroleModel> modelList) {
        for (SysmenuroleModel model : modelList) {
            sysmenuroleMapper.update(model);
        }
        return modelList;
    }

    public void disableOrEnable(SysmenuroleModel model) {
        sysmenuroleMapper.disableOrEnable(model);
    }

    public void delete(SysmenuroleModel model) {
        List<String> idList = new ArrayList<>();
        idList.add(model.getRmid());
        model.setIdList(idList);
        sysmenuroleMapper.delete(model);
    }

    public String batchDelete(List<String> idList) {
        SysmenuroleModel model = new SysmenuroleModel();
        model.setIdList(idList);
        sysmenuroleMapper.delete(model);
        return ResultData.toSuccessString();
    }

}