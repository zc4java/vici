/**
 * 服务种类管理管理初始化
 */
var Sun_servicecategory = {
    id: "Sun_servicecategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Sun_servicecategory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '父编码', field: 'pid', visible: false, align: 'center', valign: 'middle'},
            {title: '当前分类的所有父分类编号', field: 'pids', visible: false, align: 'center', valign: 'middle'},
            {title: '分类名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '父类名称编码', field: 'pidname', visible: true, align: 'center', valign: 'middle'},
            {title: '排列顺序', field: 'num', visible: true, align: 'center', valign: 'middle'},
            {title: '有效标志', field: 'isvalid', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Sun_servicecategory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Sun_servicecategory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加服务种类管理
 */
Sun_servicecategory.openAddSun_servicecategory = function () {
    var index = layer.open({
        type: 2,
        title: '添加服务种类管理',
        area: ['850px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sun_servicecategory/sun_servicecategory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看服务种类管理详情
 */
Sun_servicecategory.openSun_servicecategoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '服务种类管理详情',
            area: ['850px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sun_servicecategory/sun_servicecategory_update/' + Sun_servicecategory.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除服务种类管理
 */
Sun_servicecategory.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sun_servicecategory/delete", function (data) {
            Feng.success("删除成功!");
            Sun_servicecategory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("sun_servicecategoryId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询服务种类管理列表
 */
Sun_servicecategory.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Sun_servicecategory.table.refresh({query: queryData});
};

/**
 * 动态加载服务类型
 *
 * @returns
 */
Sun_servicecategory.loadSelectbyid = function (key,name) {
	 $("#"+key).empty();
		var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/dictbyname/"+name, function(data){
			//$("#"+key).append("<option value=' '>请选择</option>"); 
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
 * 重置方法
 */
Sun_servicecategory.resetSearch = function () {
	debugger;
    $("#condition").val("");
  // Sun_servicecategory.search();
}

$(function () {
	Sun_servicecategory.loadSelectbyid("servicecategory","服务类型枚举");
    var defaultColunms = Sun_servicecategory.initColumn();
    var table = new BSTable(Sun_servicecategory.id, "/sun_servicecategory/list", defaultColunms);
    table.setPaginationType("client");
    Sun_servicecategory.table = table.init();
});
