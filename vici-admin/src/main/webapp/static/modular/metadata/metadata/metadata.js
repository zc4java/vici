/**
 * 元数据管理管理初始化
 */
var Metadata = {
    id: "MetadataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Metadata.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一id', field: 'id', visible: false, align: 'center', valign: 'middle'},
//            {title: '数据库表名', field: 'tablename',visible: true, align: 'center', valign: 'middle',
//            	 editable: {
//                     type: 'text',
//                     title: '用户名',
//                     validate: function (v) {
//                         if (!v) return '用户名不能为空';
//                     }
//                 }},
             {title: '数据库表名', field: 'tablename',visible: true, align: 'center', valign: 'middle'},
            {title: '数据库表中文含义', field: 'tablenamecn', visible:true, align: 'center', valign: 'middle'},
            {title: '数据库字段名', field: 'field', visible: true, align: 'center', valign: 'middle'},
            {title: '数据库字段类型', field: 'fieldtype', visible: true, align: 'center', valign: 'middle'},
            {title: '字段长度', field: 'fieldlength', visible: true, align: 'center', valign: 'middle'},
            {title: '小数点', field: 'decimalpoint', visible: true, align: 'center', valign: 'middle'},
            {title: '是否可以为空', field: 'isnullable', visible: true, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==1){return '否'};if(value==0){return '是'};}},
            {title: '数据库字段注释', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '是否枚举类型', field: 'isdelcascade', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '父节点', field: 'fathernode', visible: false, align: 'center', valign: 'middle'},
            {title: '父节点名称', field: 'fathernodeName', visible: false, align: 'center', valign: 'middle'},
            {title: '是否是主键', field: 'ismajorkey', visible: true, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '有效标志', field: 'isvalid', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '更新时间', field: 'updatetime', visible: false, align: 'center', valign: 'middle'},
            {title: '更新人', field: 'operator', visible: false, align: 'center', valign: 'middle'},
            {title: '操作号', field: 'operatnum', visible: false, align: 'center', valign: 'middle'},
            {title: '操作序号', field: 'operatnumbefore', visible: false, align: 'center', valign: 'middle'},
            {title: '是否在列表展示', field: 'display', visible: true, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '是否在更新页面展示', field: 'update', visible: true, align: 'center', valign: 'middle',
          	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '是否在添加页面展示', field: 'add', visible: false, align: 'center', valign: 'middle',
          	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '校验规则', field: 'verifyrole', visible: false, align: 'center', valign: 'middle'},
            {title: '是否可查询', field: 'isselect', visible: true, align: 'center', valign: 'middle',
          	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '字典表pname或者查询sql', field: 'dict', visible: false, align: 'center', valign: 'middle'},
            {title: '输入类型', field: 'inputtype', visible: false, align: 'center', valign: 'middle'},
            {title: '鼠标单击执行方法', field: 'onclick', visible: false, align: 'center', valign: 'middle'},
            {title: '鼠标双击执行方法', field: 'ondblclick', visible: false, align: 'center', valign: 'middle'},
            {title: '当元素获得焦点时执行方法', field: 'onfocus', visible: false, align: 'center', valign: 'middle'},
            {title: '元素失去焦点时执行方法', field: 'onblur', visible: false, align: 'center', valign: 'middle'},
            {title: '字段页面显示顺序', field: 'fieldsort', visible: false, align: 'center', valign: 'middle'},
            {title: '开启列表排序', field: 'issort', visible: false, align: 'center', valign: 'middle',
          	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: 'excel导入', field: 'imp', visible: false, align: 'center', valign: 'middle'},
            {title: 'excel导出', field: 'exp', visible: false, align: 'center', valign: 'middle'},
            {title: '是否只读', field: 'readonly', visible: true, align: 'center', valign: 'middle',
          	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            
            
    ];
};

/**
 * 检查是否选中
 */
Metadata.check = function () {
	debugger;
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.success("请先选中表格中的某一记录！");
        return false;
    }else{
        Metadata.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加元数据管理
 */
Metadata.openAddMetadata = function () {
    var index = layer.open({
        type: 2,
        title: '添加元数据管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/metadata/metadata_add'
    });
    this.layerIndex = index;
};
/**
 * 点击添加元数据管理
 */
Metadata.openAddMetadatalist = function () {
    var index = layer.open({
        type: 2,
        title: '添加元数据管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/metadata/metadata_add_list'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看元数据管理详情
 */
Metadata.openMetadataDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '元数据管理详情',
            area: ['850px', '520px'], //宽高['850px', '520px']
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/metadata/metadata_update/' + Metadata.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};
/**
 * 打开查看元数据管理详情
 */
Metadata.openMetadataDetailList = function () {
	debugger;
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '元数据管理详情',
            area: ['850px', '520px'], //宽高['850px', '520px']
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/metadata/metadata_update_list/' + Metadata.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除元数据管理
 */
Metadata.delete = function () {
    if (this.check()) {
    	  var operation = function(){
        var ajax = new $ax(Feng.ctxPath+'metadata/delete', function (data) {
            Feng.success("删除成功!");
            Metadata.table.refresh();
            Metadata.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("metadataId",Metadata.seItem.id);
        ajax.start();
    	  }
    	  Feng.confirm("是否确认刪除?", operation);
    }
};

/**
 * 查询元数据管理列表
 */
Metadata.search = function () {
	var options=$("#tablename option:selected"); 
    var queryData = {};
    //= $("#viewname").selectedOptions["0"].innerText;
    queryData['tablename'] = options.text();
    queryData['field'] =$("#field").val();
   Metadata.table.refresh({query: queryData});
};
/**
 * 查询元数据管理列表
 */
Metadata.refresh = function () {
	Metadata.loadSelectByTbalename("tablename"," ")
};
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
Metadata.loadSelectByTbalename = function (key,tablename) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''></option>");
		var ajax = new $ax(Feng.ctxPath + "/metadata/tablenameByid/"+tablename, function(data){
			debugger;
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}



$(function () {
	Metadata.loadSelectByTbalename("tablename"," ")
    var defaultColunms = Metadata.initColumn();
    var table = new BSTable(Metadata.id, "/metadata/list", defaultColunms);
    table.setPaginationType("client");
    table.setDefultNumber("10");
	table.setDefaultList(10,50,100);
    Metadata.table = table.init();
});
