package com.store.service.app;

import com.store.api.model.Product.LoopVO;
import com.store.api.model.Product.ProductVO;
import com.store.api.model.Product.TypeVO;
import com.store.model.LoopinfoModel;
import com.store.model.ResultData;
import com.store.model.VegetableModel;
import com.store.model.VegetabletypeModel;
import com.store.service.LoopinfoService;
import com.store.service.VegetableService;
import com.store.service.VegetabletypeService;
import com.store.utils.ConstantVariable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppIndexService {

    private static final Logger logger = Logger.getLogger(AppIndexService.class);

    @Autowired
    private VegetabletypeService vegetabletypeService;

    @Autowired
    private VegetableService vegetableService;

    @Autowired
    private LoopinfoService loopinfoService;

    public String getProductList(VegetableModel vmodel) {
        try {
            List<VegetableModel> productList = vegetableService.getList(vmodel);
            return ResultData.toSuccessString(productList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

    public String getProductListToApp(VegetableModel vmodel) {
        try {
            List<VegetableModel> productList = vegetableService.getList(vmodel);
            List<ProductVO> voList = new ArrayList<>();
            for (VegetableModel model : productList) {
                ProductVO vo = new ProductVO(model);
                voList.add(vo);
            }
            return ResultData.toSuccessString(voList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

    public List<VegetabletypeModel> getTypeList(VegetabletypeModel typeModel) {
        return vegetabletypeService.getList(typeModel);
    }

    public List<TypeVO> getTypeListToApp(VegetabletypeModel model) {
        List<VegetabletypeModel> sourceList = this.getTypeList(model);
        List<TypeVO> targetList = new ArrayList<>();
        for (VegetabletypeModel typeModel : sourceList) {
            TypeVO vo = new TypeVO((typeModel));
            targetList.add(vo);
        }
        return targetList;
    }

    public String getIndex() {
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("typeList", getTypeList());
            returnMap.put("loopList", getLoopListToApp(getLoopList(ConstantVariable.LOOP_INDEX)));
            return ResultData.toSuccessDataObj(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResultData.toErrorString();
        }
    }

    public List<LoopVO> getTypeLoop() {
        return getLoopListToApp(getLoopList(ConstantVariable.LOOP_TYPE));
    }

    private List<TypeVO> getTypeList() {
        VegetabletypeModel typeModel = new VegetabletypeModel();
        VegetableModel model = new VegetableModel();
        model.setValid(ConstantVariable.VALID_ENABLE);
        List<VegetabletypeModel> typeList = vegetabletypeService.getList(typeModel);
        List<VegetableModel> vegetableList = vegetableService.getListByTypeTop(model);
        for (VegetabletypeModel tmodel : typeList) {
            List<VegetableModel> productList = tmodel.getProductList();
            if (CollectionUtils.isEmpty(productList)) {
                productList = new ArrayList<>();
                tmodel.setProductList(productList);
            }
            Iterator<VegetableModel> it = vegetableList.iterator();
            while (it.hasNext()) {
                VegetableModel vegetable = it.next();
                if (vegetable.getTypeid().equals(tmodel.getVtid())) {
                    productList.add(vegetable);
                    it.remove();
                }
            }
        }
        return getTypeListToVO(typeList);
    }

    private List<TypeVO> getTypeListToVO(List<VegetabletypeModel> typeList) {
        List<TypeVO> voList = new ArrayList<>();
        for (VegetabletypeModel vegetabletypeModel : typeList) {
            TypeVO typeVO = new TypeVO(vegetabletypeModel);
            voList.add(typeVO);
        }
        return voList;
    }

    private List<LoopinfoModel> getLoopList(String loopIndex) {
        LoopinfoModel loopinfoModel = new LoopinfoModel();
        loopinfoModel.setPosition(loopIndex);
        List<LoopinfoModel> loopList = loopinfoService.getList(loopinfoModel);
        Collections.sort(loopList, new Comparator<LoopinfoModel>() {
            public int compare(LoopinfoModel afterModel, LoopinfoModel nextModel) {
                if (afterModel.getLoopseq() > nextModel.getLoopseq()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return loopList;
    }

    private List<LoopVO> getLoopListToApp(List<LoopinfoModel> loopList) {
        List<LoopVO> voList = new ArrayList<>();
        for (LoopinfoModel loop : loopList) {
            LoopVO vo = new LoopVO(loop);
            voList.add(vo);
        }
        return voList;
    }
}