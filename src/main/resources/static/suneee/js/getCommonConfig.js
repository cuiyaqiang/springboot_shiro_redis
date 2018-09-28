var baseStrUrl;        //base URL
var supplierURL;   		//供应商的URL
var goodsURL;     //商品的
var storageUrl;    //仓库
var wholesaleUrl;  //批发
var saleUrl;     //零售
var kitchenUrl;     //后厨URL
var marketUrl; //促销
var purchaseUrl;  //采购
var reportformUrl;//报表
var contractUrl; //合同
var enterpriseId; //企业
var uploadUrl;	  //上传图片url
var dictionaryUrl;
var tmsUrl;
var bksettlementUrl;//大宗 结算URL
var bksale;//大宗 销售URL

var iespGoodsUrl;
var iespDeclarationUrl;
var iespCargoUrl;
var iespIbaseUrl;
var iespDepotUrl;
var iespExpressUrl;
var iespToolsUrl;
var baseStrURL;
var systemUrl;
var yn_mall_url;
var product_scf_domain;
var enterpriseLevel;//企业级别
var enterpriseCode;//企业级别
var xp_url;

var domaindata;


function getModularPrefix(systemhost) {
    $.ajax({
        async: false,
        type: "GET",
        dataType: 'json',
        url: systemhost+"/user/getModularPrefix",
        error: function (data) {
//				layer.msg("获取api的url数据失败")
        },
        success: function (data) {
            systemUrl = data.systemUrl;
            baseStrUrl = data.baseUrl;//rest
            baseStrURL = data.baseURL;//tomcat
            supplierURL = data.supplierURL;
            goodsURL = data.goodsURL;
            storageUrl = data.storageUrl;
            wholesaleUrl = data.wholesaleUrl;
            saleUrl = data.saleUrl;
            kitchenUrl = data.kitchenUrl;
            marketUrl = data.marketUrl;
            purchaseUrl = data.purchaseUrl;
            reportformUrl = data.reportformUrl;
            contractUrl = data.contractUrl;
            uploadUrl = data.uploadUrl;
            enterpriseId = data.enterpriseId;
            dictionaryUrl = data.dictionaryUrl;
            tmsUrl = data.tmsUrl;
            bksettlementUrl = data.bksettlementUrl;
            bksale	= data.bksale;
            
            
            iespGoodsUrl = data.iespGoodsUrl;
            iespDeclarationUrl = data.iespDeclarationUrl;
            iespCargoUrl = data.iespCargoUrl;
            iespIbaseUrl = data.iespIbaseUrl;
            iespDepotUrl = data.iespDepotUrl;
            iespExpressUrl = data.iespExpressUrl;
            iespToolsUrl = data.iespToolsUrl;
            yn_mall_url = data.yn_mall_url;
            product_scf_domain=data.product_scf_domain;
            enterpriseLevel=data.enterpriseLevel;
            enterpriseCode=data.enterpriseCode;
            xp_url=data.xp_url;
            domaindata=data;
        }
    });
}