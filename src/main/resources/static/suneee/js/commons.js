function getBrand(){
    if($("input").hasClass("brand_se_entry")){

        var test = $(".brand_se_entry").bigAutocomplete({
            width:'auto',
            id:['brandid','opcode','brandname'],
            highlight: true,
            ajax:{
                url: 'http://test.vr.weilian.cn:40884/goodsRestApi/getGoodsListToBrand',
                type : "GET",

                success: function(data){
                    var result = eval(data);
                    var Str = result;
                    var datas = [];
                    for (var i = 0; i < Str.data.length; i++) {
                        datas[i] = [];
                        console.info(Str.data[i]);
                        datas[i].push(Str.data[i].brandid);
                        datas[i].push(Str.data[i].opcode);
                        datas[i].push(Str.data[i].brandname);
                    }
                    test.setData(datas,true); // 设置显示的内容，并更新
                    test.setTitle(['品牌ID','拼音码','品牌名称']); // 设置标题
                },
                error: function (msg) {
                    alert("查询品牌接口异常")
                }
            }
        });

    }

}