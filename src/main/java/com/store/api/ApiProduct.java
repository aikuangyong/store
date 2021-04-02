package com.store.api;

import com.store.api.model.Product.*;
import com.store.model.*;
import com.store.service.OrderevalService;
import com.store.service.ShopcarService;
import com.store.service.VegetableService;
import com.store.service.app.AppIndexService;
import com.store.utils.ConstantVariable;
import com.store.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.FormParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Api(value = "菜单商品", tags = "菜单商品")
@RequestMapping("/api/product")
@Controller
public class ApiProduct {

    @Autowired
    private AppIndexService indexService;

    @Autowired
    private OrderevalService orderevalService;

    @Autowired
    private ShopcarService shopcarService;

    @Autowired
    private VegetableService vegetableService;

    @RequestMapping(value = "/getProductByPosNo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "vid", defaultValue = "1", value = "商品ID", required = false)})
    @ApiOperation(value = "按照商品ID获取详细信息", notes = "按照商品ID获取详细信息")
    public String getProductByPosNo(String posNo) {
        try {
            VegetableModel vegetableModel = new VegetableModel();
            vegetableModel.setPosno(posNo);
            vegetableModel = vegetableService.getModelById(vegetableModel);
            if (null == vegetableModel) {
                return ResultData.toErrorString("没有找到对应的商品");
            }
            return ResultData.toSuccessDataObj(new ProductVO(vegetableModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getProductById", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "vid", defaultValue = "1", value = "商品ID", required = false)})
    @ApiOperation(value = "按照商品ID获取详细信息", notes = "按照商品ID获取详细信息")
    public String getProductById(String vid) {
        try {
            VegetableModel vegetableModel = new VegetableModel();
            vegetableModel.setVid(vid);
            vegetableModel = vegetableService.getModelById(vegetableModel);
            return ResultData.toSuccessDataObj(new ProductVO(vegetableModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }


    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取首页信息", notes = "获取首页信息，包含轮播图,商品分类及每个分类下的六种推荐商品和该分类对应的轮播图;{\"dataCount\": 0,\"dataList\": null,\"dataObj\": {\"typeList\": [{\"bannersrc\": \"http://localhost/upload/20180816/20180816aecd75a250b560327c0a26ac29acb6a5.jpg\", //分类的图片\"productList\": [{ //产品列表\"content\": \"112\", //产品的描述\"detailcontent\": \"<p>1222</p>\", //产品的详情页\"imageList\": [{\"href\": \"http://localhost/upload/20180816/201808166aecf2cfeb135c0145fa8e888d400fdd.jpg\", //图片地址\"imageseq\": 9 //序号最小的就是最先传的,用做缩略图},{\"href\": \"http://localhost/upload/20180816/201808167ba2bb26f3f4ba8f55a940c7b4f392f3.jpg\",\"imageseq\": 10},{\"href\": \"http://localhost/upload/20180816/2018081684a8c1958941ca45e1a8924f7685790d.png\",\"imageseq\": 11}],\"isIndex\": null,\"posno\": \"32967\", // post编号\"price\": \"58\", //价格\"vegetablename\": \"菜品1-分类1\" //名称},{\"content\": \"1\",\"detailcontent\": \"<p>菜品2-分类1</p>\",\"imageList\": [{\"href\": \"20180817/20180817ef76f74b59cb3b42e817d44b7d0deb6d.jpg\"},{\"href\": \"20180817/20180817065b078da3db923d627e30e8aadbd6d1.jpg\"},{\"href\": \"20180817/20180817d856841f08e8e893f9e4628a13d0b18c.jpg\"}],\"isIndex\": null,\"posno\": \"80102\",\"price\": \"6506\",\"vegetablename\": \"菜品2-分类1\"},{\"content\": \"1\",\"detailcontent\": \"<p>菜品3-分类1</p>\",\"imageList\": [{\"href\": \"20180817/2018081779cafa5d65d3721b676e0805e612cd2a.jpg\"},{\"href\": \"20180817/2018081701e7b5f8ff5b02a47b7e2094f2a871aa.jpg\"},{\"href\": \"20180817/201808177be3aa186383fd3e54e0daf3ba343781.jpg\"}],\"isIndex\": null,\"posno\": \"64081\",\"price\": \"8347\",\"vegetablename\": \"菜品3-分类1\"},{\"content\": \"菜品4-分类1\",\"detailcontent\": \"<p>菜品4-分类1</p>\",\"imageList\": [{\"href\": \"20180817/201808177d9df164fcd672c340678c95221c92d0.jpg\"},{\"href\": \"20180817/2018081779847543bfe13c980d017ca883102c6c.jpg\"},{\"href\": \"20180817/20180817ff9085f4c0aef29469ddfa43fb668e09.jpg\"}],\"isIndex\": null,\"posno\": \"44759\",\"price\": \"6706\",\"vegetablename\": \"菜品4-分类1\"}],\"typesrc\": \"http://localhost/upload/20180816/201808160a4c2daac77fb2d2fa3f13efa08d9ba8.jpg\",\"vtid\": \"8dabd7e3-a15c-11e8-b6dd-00ff57a662b1\",\"vtname\": \"分类1\"},{\"bannersrc\": \"http://localhost/upload/20180816/2018081614145782d8dfe9a0e9bd57d5466b6b09.jpg\",\"productList\": [{\"content\": \"菜品5-分类1\",\"detailcontent\": \"<p>菜品5-分类1</p>\",\"imageList\": [{\"href\": \"20180817/201808172d3576d5140d11e2e2a6c5c2b41f31d0.jpg\"},{\"href\": \"20180817/201808174b5b945a15ec997ee37038fc92316ce6.png\"},{\"href\": \"20180817/20180817157184ae0e45a3f4ba46111e702c16b5.jpg\"}],\"isIndex\": null,\"posno\": \"29187\",\"price\": \"4444\",\"vegetablename\": \"菜品5-分类1\"},{\"content\": \"菜品5-分类2\",\"detailcontent\": \"<p>菜品5-分类2</p>\",\"imageList\": [{\"href\": \"20180817/201808176050cbda88df0bfafc0fb2b495458768.jpg\"},{\"href\": \"20180817/2018081761b9152dcef5a77f9b3a977b33114af5.jpg\"},{\"href\": \"20180817/20180817b86e16848a88fe7f8689e3ff30463c87.png\"}],\"isIndex\": null,\"posno\": \"33007\",\"price\": \"3827\",\"vegetablename\": \"菜品6-分类2\"},{\"content\": \"菜品5-分类2\",\"detailcontent\": \"<p>菜品5-分类2</p>\",\"imageList\": [{\"href\": \"20180817/20180817afc853becae153bcb1f979161dd00de3.jpg\"},{\"href\": \"20180817/20180817b544c8c39ac63c65e362801cde388297.jpg\"},{\"href\": \"20180817/20180817553fc25215736348ebdedd2a1a0c1604.jpg\"}],\"isIndex\": null,\"posno\": \"34978\",\"price\": \"9384\",\"vegetablename\": \"菜品5-分类2\"},{\"content\": \"菜品5-分类2\",\"detailcontent\": \"<p>菜品1-分类2</p>\",\"imageList\": [{\"href\": \"20180817/201808173867334242db90bd82ab0cd450ec4e50.jpg\"},{\"href\": \"20180817/201808171b58e0bcba26a468c79b98c144e57a2a.jpg\"},{\"href\": \"20180817/2018081711181aa443346c66bb0cae6a16e62418.jpg\"}],\"isIndex\": null,\"posno\": \"27152\",\"price\": \"995\",\"vegetablename\": \"菜品1-分类2\"}],\"typesrc\": \"http://localhost/upload/20180816/2018081634d661fb44d0c9d88141262bc520e4dd.jpg\",\"vtid\": \"9de3b8cc-a15c-11e8-b6dd-00ff57a662b1\",\"vtname\": \"分类2\"},{\"bannersrc\": \"http://localhost/upload/20180816/20180816725a01ff6f1783d5ea461c4bca416ae1.png\",\"productList\": [],\"typesrc\": \"http://localhost/upload/20180816/201808161a0037936106cbc584447afbe358ae30.jpg\",\"vtid\": \"23ee9ea9-a15d-11e8-b6dd-00ff57a662b1\",\"vtname\": \"分类3\"},{\"bannersrc\": \"http://localhost/upload/20180816/201808169fa6ea3e250058ad2a0dbe40889e1a1c.jpg\",\"productList\": [],\"typesrc\": \"http://localhost/upload/20180816/2018081663a1c6226a327a3e6a82047462ff1818.png\",\"vtid\": \"18e15286-a15f-11e8-b6dd-00ff57a662b1\",\"vtname\": \"分类4\"},{\"bannersrc\": \"http://localhost/upload/20180816/20180816f4003ca2b3e53ac25cc7e37bbccee886.jpg\",\"productList\": [],\"typesrc\": \"http://localhost/upload/20180816/201808163bad9641571280f9cee2e381888edf30.jpg\",\"vtid\": \"218f2637-a15f-11e8-b6dd-00ff57a662b1\",\"vtname\": \"分类5\"}],\"loopList\": [{\"loopcontent\": \"\", //轮播内容\"looptype\": \"1\", //轮播分类;轮播图类型1无内容2链接3富文本\"src\": \"http://localhost/upload/20180817/2018081744e2e4d6df259aa0ce0010ac88fb4a19.jpg\" //轮播图地址}]},\"msg\": null,\"pageCount\": 0,\"pageNumber\": 0,\"state\": \"SUCCESS\"}")
    public String getIndex() {
        return indexService.getIndex();
    }

    @RequestMapping(value = "/getTypeLoop", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取首页信息", notes = "")
    public String getTypeLoop() {
        try {
            List<LoopVO> loopList = indexService.getTypeLoop();
            return ResultData.toSuccessDataObj(loopList);
        } catch (Exception e) {
            return ResultData.toErrorString();
        }

    }


    @RequestMapping(value = "/getLoopList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "返回轮播图")
    public String getLoopList(String looptype) {
        LoopinfoModel loopinfoModel = new LoopinfoModel();
        loopinfoModel.setLooptype(looptype);
        return ResultData.toSuccessString();
    }

    @RequestMapping(value = "/getTypeList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有分类信息", notes = "获取所有分类信息")
    public String getType() {
        VegetabletypeModel typeModel = new VegetabletypeModel();
        typeModel.setPageNow(0);
        typeModel.setPageSize(Integer.MAX_VALUE);
        List<TypeVO> dataList = indexService.getTypeListToApp(typeModel);
        return ResultData.toSuccessString(dataList);
    }

    @RequestMapping(value = "/getProduct", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "typeid", defaultValue = "1", value = "分类ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "vegetablename", defaultValue = "", value = "商品名称", required = false)})
    @ApiOperation(value = "获取商品列表", notes = "按照分类id或者商品名称获取商品列表")
    public String getProductList(@RequestParam(defaultValue = "", required = false) String typeid,
                                 @RequestParam(defaultValue = "", required = false) String vegetablename) {
        VegetableModel vmodel = new VegetableModel();
        vmodel.setPageNow(0);
        vmodel.setPageSize(Integer.MAX_VALUE);
        if (StringUtils.isNotEmpty(typeid)) {
            vmodel.setTypeid(typeid);
        }
        if (StringUtils.isNotEmpty(vegetablename)) {
            vmodel.setVegetablename(vegetablename);
        }
        vmodel.setValid(ConstantVariable.PRODUCT_ONLINE);
        return indexService.getProductListToApp(vmodel);
    }

    @RequestMapping(value = "/getEvalByProduct", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "productid", defaultValue = "2ab858c0-a1e5-11e8-afd7-00ff57a662b1", value = "商品ID", required = false)})
    @ApiOperation(value = "获取评价列表", notes = "按照商品ID获取评价列表")
    public String getEvalByProduct(String productid) {
        if (StringUtils.isBlank(productid)) {
            return ResultData.toErrorString("商品信息不能为空");
        }
        OrderevalModel orderevalModel = new OrderevalModel();
        orderevalModel.setVegetable(productid);
        orderevalModel.setOrderColumn("createtime");
        orderevalModel.setOrderType("desc");
        List<EvalVO> dataList = orderevalService.getListToApp(orderevalModel);
        Integer count = dataList.size();
        Float score = 0f, avgScore = 0f;
        for (EvalVO vo : dataList) {
            score += Float.parseFloat(vo.getScore());
        }

        Map<String, Object> returnMap = new HashMap<>();
        if (count == 0 || score == 0) {
            returnMap.put("avgscore", "0.0");
        } else {
            avgScore = score / count;
            avgScore = null == avgScore ? 0 : avgScore;
            returnMap.put("avgscore", ConstantVariable.SCORE_FORMAT.format(avgScore));
        }
        return ResultData.toSuccessString(dataList, returnMap);
    }

    @RequestMapping(value = "/addProductToShopCar", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "productid", defaultValue = "1", value = "商品ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false)})
    @ApiOperation(value = "给购物车添加商品", notes = "给购物车添加商品")
    public String addProductToShopCar(String userid, String productid) {
        try {
            ShopcarModel shopcarModel = new ShopcarModel();
            shopcarModel.setUserid(userid);
            shopcarModel.setProductid(productid);
            shopcarService.add(shopcarModel);
            shopcarModel = new ShopcarModel();
            shopcarModel.setUserid(userid);
            shopcarModel.noPage();
            return getShopCarByUser(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/subProductToShopCar", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "productid", defaultValue = "1", value = "商品ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户ID", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "count", defaultValue = "1", value = "减少的个数", required = false)})
    @ApiOperation(value = "减少购物车的商品", notes = "减少购物车的商品")
    public String subProductToShopCar(String userid, String productid, @RequestParam(value = "count", defaultValue = "1") Integer count) {
        try {
            ShopcarModel shopcarModel = new ShopcarModel();
            shopcarModel.setUserid(userid);
            shopcarModel.setProductid(productid);
            if (null == count) {
                count = 1;
            }
            shopcarModel.setProductcount(count);
            boolean isSuccess = shopcarService.sub(shopcarModel);
            if (isSuccess) {
                shopcarModel = new ShopcarModel();
                shopcarModel.setUserid(userid);
                shopcarModel.noPage();
                return getShopCarByUser(userid);
            }
            return ResultData.toErrorString("商品不存在");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/getShopCarByUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "商品ID", required = false)})
    @ApiOperation(value = "按照用户ID获取购物车的商品", notes = "按照用户ID获取购物车的商品;enableList(有效的数据集合);disableList(无效的数据集合);")
    public String getShopCarByUser(String userid) {
        try {
            ShopcarModel shopcarModel = new ShopcarModel();
            shopcarModel.setUserid(userid);
            shopcarModel.noPage();
            List<ShopCarVO> disableList = new ArrayList<>();
            List<ShopCarVO> enableList = new ArrayList<>();
            List<ShopCarVO> allList = shopcarService.getListToApp(shopcarModel);
            for (ShopCarVO vo : allList) {
                if (ConstantVariable.VALID_DISABLE.equals(vo.getVaild())) {
                    disableList.add(vo);
                } else {
                    enableList.add(vo);
                }
            }
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("disableList", disableList);
            returnMap.put("enableList", enableList);
            return ResultData.toSuccessDataObj(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "userid", defaultValue = "1", value = "用户编号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "productids", defaultValue = "1,2,3,4", value = "商品编号", required = false)})
    @ApiOperation(value = "按照用户ID删除购物车的商品", notes = "按照用户ID删除购物车的商品,清除无效商品就传无效商品的productid就可以")
    public String deleteProduct(String userid, String productids) {
        try {
            ShopcarModel shopcarModel = new ShopcarModel();
            shopcarModel.setUserid(userid);
            List<String> idList = Arrays.asList(productids.split(","));
            shopcarModel.setIdList(idList);
            shopcarService.deleteByCondition(shopcarModel);
            return ResultData.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.toErrorString();
        }
    }
}