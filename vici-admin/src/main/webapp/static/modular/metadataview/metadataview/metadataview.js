/**
 * 视图管理管理初始化
 */
var Metadataview = {
    id: "MetadataviewTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Metadataview.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '视图名', field: 'viewname', visible: true, align: 'center', valign: 'middle'},
            {title: '视图名中文', field: 'viewnamecn', visible: true, align: 'center', valign: 'middle'},
            {title: '重写字段', field: 'replacecolumn', visible: false, align: 'center', valign: 'middle'},
            {title: '数据库表id', field: 'metadataid', visible: false, align: 'center', valign: 'middle'},
            {title: '数据库表名称', field: 'tablename', visible: true, align: 'center', valign: 'middle'},
            {title: '字段名', field: 'field', visible: true, align: 'center', valign: 'middle'},
            {title: '注释', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '是否在列表展示', field: 'display', visible: true, align: 'center', valign: 'middle',
            formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '是否在更新页面展示', field: 'update', visible: true, align: 'center', valign: 'middle',
                formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '是否在添加页面展示', field: 'add', visible: true, align: 'center', valign: 'middle',
                    formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '是否为查询条件', field: 'isselect', visible: true, align: 'center', valign: 'middle',
                        formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '鼠标单击执行方法', field: 'onclick', visible: true, align: 'center', valign: 'middle'},
            {title: '鼠标双击执行方法', field: 'ondblclick', visible: true, align: 'center', valign: 'middle'},
            {title: '当元素获得焦点时执行方法', field: 'onfocus', visible: true, align: 'center', valign: 'middle'},
            {title: '元素失去焦点时执行方法', field: 'onblur', visible: true, align: 'center', valign: 'middle'},
            {title: '有效标志', field: 'isvalid', visible: false, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updatetime', visible: false, align: 'center', valign: 'middle'},
            {title: '更新人', field: 'operator', visible: false, align: 'center', valign: 'middle'},
            {title: '操作号', field: 'operatnum', visible: false, align: 'center', valign: 'middle'},
            {title: '操作序号', field: 'operatnumbefore', visible: false, align: 'center', valign: 'middle'},
            {title: '字段页面显示顺序', field: 'fieldsort', visible: false, align: 'center', valign: 'middle',
            formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '开启列表排序', field: 'issort', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: 'excel导入', field: 'imp', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: 'excel导出', field: 'exp', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '是否只读', field: 'readonly', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
             {title: '是否是主id', field: 'viewid', visible: false, align: 'center', valign: 'middle',
                	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}}
    ];
};

/**
 * 检查是否选中
 */
Metadataview.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Metadataview.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加视图管理
 */
Metadataview.openAddMetadataview = function () {
    var index = layer.open({
        type: 2,
        title: '添加视图管理',
        area: ['850px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/metadataview/metadataview_add'
    });
    this.layerIndex = index;
    layer.full(index);
};
/**
 * 点击添加视图管理
 */
Metadataview.openAddMetadataviewlist = function () {
	debugger;
    var index = layer.open({
        type: 2,
        title: '添加视图管理',
        area: ['850px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/metadataview/metadataview_add_list'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看视图管理详情
 */
Metadataview.openMetadataviewDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '视图管理详情',
            area: ['850px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/metadataview/metadataview_update/' + Metadataview.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};
Metadataview.openMetadataviewDetaillist = function () {
    if (this.check()) {
    	
        var index = layer.open({
            type: 2,
            title: '视图管理详情',
            area: ['850px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/metadataview/metadataview_update_list/' + Metadataview.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除视图管理
 */
Metadataview.delete = function () {
	debugger;
    if (this.check()) {
    	  var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/metadataview/delete", function (data) {
            Feng.success("删除成功!");
            Metadataview.table.refresh();
            Metadataview.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("metadataviewId",Metadataview.seItem.id);
        ajax.start();
    	  }
    	  Feng.confirm("是否确认刪除?", operation);
    }
};

/**
 * 查询视图管理列表
 */
Metadataview.search = function () {
	debugger;
	var options=$("#viewname option:selected"); 
    var queryData = {};
    //= $("#viewname").selectedOptions["0"].innerText;
    queryData['viewname'] = options.text();
    queryData['field'] =$("#field").val();
    queryData['tablename'] =$("#tablename").val();
    Metadataview.table.refresh({query: queryData});
};
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
Metadataview.loadSelectByViewname = function (key,viewname) {
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
/**
 * 查询元数据管理列表
 */
Metadataview.refresh = function () {
	Metadataview.loadSelectByViewname("viewname"," ");
};
$(function () {
    Metadataview.loadSelectByViewname("viewname"," ");
    var defaultColunms = Metadataview.initColumn();
    var table = new BSTable(Metadataview.id, "/metadataview/list", defaultColunms);
    table.setPaginationType("client");
    table.setDefultNumber("10");
    table.setDefaultList(10,50,100);
    Metadataview.table = table.init();
});
