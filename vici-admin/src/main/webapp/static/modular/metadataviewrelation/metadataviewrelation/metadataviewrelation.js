/**
 * 视图关系管理管理初始化
 */
var Metadataviewrelation = {
    id: "MetadataviewrelationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Metadataviewrelation.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '视图名', field: 'viewname', visible: true, align: 'center', valign: 'middle'},
            {title: '视图中的表关联', field: 'relation', visible: true, align: 'center', valign: 'middle'},
            {title: '更新后执行语句', field: 'addafter', visible: true, align: 'center', valign: 'middle'},
            {title: '主表，唯一id所在到表', field: 'mastertable', visible: true, align: 'center', valign: 'middle'},
            {title: '删除操作表名', field: 'deletetables', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Metadataviewrelation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Metadataviewrelation.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加视图关系管理
 */
Metadataviewrelation.openAddMetadataviewrelation = function () {
    var index = layer.open({
        type: 2,
        title: '添加视图关系管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/metadataviewrelation/metadataviewrelation_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看视图关系管理详情
 */
Metadataviewrelation.openMetadataviewrelationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '视图关系管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/metadataviewrelation/metadataviewrelation_update/' + Metadataviewrelation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除视图关系管理
 */
Metadataviewrelation.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/metadataviewrelation/delete", function (data) {
            Feng.success("删除成功!");
            Metadataviewrelation.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("metadataviewrelationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询视图关系管理列表
 */
Metadataviewrelation.search = function () {
	var options=$("#viewname option:selected"); 
    var queryData = {};
    queryData['viewname'] = options.text();
    Metadataviewrelation.table.refresh({query: queryData});
};
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
Metadataviewrelation.loadSelectByViewname = function (key,viewname) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''></option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataview/viewnameByid/"+viewname, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
$(function () {
	Metadataviewrelation.loadSelectByViewname("viewname"," ");
    var defaultColunms = Metadataviewrelation.initColumn();
    var table = new BSTable(Metadataviewrelation.id, "/metadataviewrelation/list", defaultColunms);
    table.setPaginationType("client");
    table.setDefaultList(10,50,100);
	table.setDefultNumber(10);
    Metadataviewrelation.table = table.init();
});
