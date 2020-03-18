/**
 * 产品管理管理初始化
 */
var Sun_product = {
    id: "Sun_productTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Sun_product.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '产品名称', field: 'productname', visible: true, align: 'center', valign: 'middle'},
            {title: '服务类型', field: 'servicecategory', visible: false, align: 'center', valign: 'middle'},
            {title: '服务类型', field: 'servicecategoryname', visible: true, align: 'center', valign: 'middle'},
            {title: '产品分类', field: 'productcategory', visible: false, align: 'center', valign: 'middle'},
            {title: '产品分类', field: 'productcategoryname', visible: true, align: 'center', valign: 'middle'},
            {title: '产品类型', field: 'producttype', visible: false, align: 'center', valign: 'middle'},
            {title: '产品类型', field: 'producttypename', visible: true, align: 'center', valign: 'middle'},
            {title: '商品单位', field: 'productunit', visible: true, align: 'center', valign: 'middle'},
            {title: '市场价', field: 'marketprice', visible: true, align: 'center', valign: 'middle'},
            {title: '成本价', field: 'costprice', visible: true, align: 'center', valign: 'middle'},
            {title: '售价', field: 'saleprice', visible: true, align: 'center', valign: 'middle'},
            {title: '说明', field: 'remark', visible: false, align: 'center', valign: 'middle'},
            {title: '有效标志', field: 'isvalid', visible: false, align: 'center', valign: 'middle'},
            {title: '服务商主键', field: 'providerid', visible: false, align: 'center', valign: 'middle'},
            {title: '服务商名称', field: 'providername', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Sun_product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Sun_product.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加产品管理
 */
Sun_product.openAddSun_product = function () {
    var index = layer.open({
        type: 2,
        title: '添加产品管理',
        area: ['850px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sun_product/sun_product_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看产品管理详情
 */
Sun_product.openSun_productDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '产品管理详情',
            area: ['850px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sun_product/sun_product_update/' + Sun_product.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除产品管理
 */
Sun_product.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sun_product/delete", function (data) {
            Feng.success("删除成功!");
            Sun_product.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("sun_productId",this.seItem.id);
        ajax.start();
    }
};
/**
 * 动态服务商信息
 *
 * @returns
 */
Sun_product.loadProductbyid = function () {
		var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/providernameid", function(data){
		$.each(data,function(idx,item){     
		   //输出
		   //alert(item.num+"哈哈"+item.name);
		$("#providerid").append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}

/**
 * 查询产品管理列表
 */
Sun_product.search = function () {
    var queryData = {};
    //servicecategory  productcategory producttype productname providername
    queryData['servicecategory'] = $("#servicecategory").val();
    queryData['productcategory'] = $("#productcategory").val();
    queryData['producttype'] = $("#producttype").val();
    queryData['productname'] = $("#productname").val();
    queryData['providerid'] = $("#providerid").val();
    queryData['id'] = "";
    Sun_product.table.refresh({query: queryData});
};

/**
 * 查询产品byid
 */
Sun_product.searchbyid = function (id) {
    var queryData = {};
    ///queryData['condition'] = $("#condition").val();
    queryData['id'] = id;
    Sun_product.table.refresh({query: queryData});
};


/**
 * 动态服务商信息
 *
 * @returns
 */
Sun_product.loadProvidernamebyid = function (key) {
	debugger;

	 $("#"+key).append("<option value=' '>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/providernameid", function(data){
		$.each(data,function(idx,item){     
		   //输出
		   //alert(item.num+"哈哈"+item.name);
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
/**
 * 按照服务分类级别进行加载
 *
 * @returns
 */
Sun_product.loadSelectbylevel = function (key,level) {
	 $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/sun_product/categorybylevel/"+level, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
/**
 * 按照pid进行加载
 *
 * @returns
 */
Sun_product.loadSelectBypids = function (key,pids) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/sun_product/categorybypids/"+pids, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}

Sun_product.getSelectOption =function(key){
		var op ="";
	$("#"+key+" option").each(function() {   
	//var arr=$(key).each(function() {  
		     debugger;
			 if($(this).val()!=""){
							if (op==""){
								op = $(this).val();  
							}else{
								op = op+","+$(this).val();  
							}
			 }
		
	});  
	return op;
}

/**
 * 重置方法
 */
Sun_product.resetSearch = function () {
	debugger;
    $("#servicecategory").val(" ");
    $("#productcategory").val(" ");
    $("#producttype").val(" ");
    $("#productname").val("");
    $("#providerid").val(" ");
  
    //Sun_product.search();
}

/**
 * 联动方法
 */
Sun_product.changeservicecategory=function(obj){
	   debugger;
	   Sun_product.loadSelectBypids("productcategory",obj.value);
	   Sun_product.loadSelectBypids("producttype",Sun_product.getSelectOption("productcategory"));
	}
Sun_product.changeproductcategory=function(obj){
	Sun_product.loadSelectBypids("producttype",obj.value);
}
$(function () {
	Sun_product.loadSelectbylevel("servicecategory","1");
	Sun_product.loadSelectbylevel("productcategory","2");
	Sun_product.loadSelectbylevel("producttype","3");
	debugger;
	Sun_product.loadProvidernamebyid("providerid");
	
	var Ohref=window.location.href;
	var arrhref=Ohref.split("?id=");
	var Controllerurl= "/sun_product/list";
	

	
    var defaultColunms = Sun_product.initColumn();
    var table = new BSTable(Sun_product.id, Controllerurl, defaultColunms);
    table.setPaginationType("client");
    Sun_product.table = table.init();
    if(typeof(arrhref[1])!='undefined'){
    	Sun_product.searchbyid(arrhref[1]);
	}
    
});
