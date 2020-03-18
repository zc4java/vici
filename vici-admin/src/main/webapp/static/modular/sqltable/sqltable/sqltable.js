/**
 * sqltable管理管理初始化
 */
var Sqltable = {
    id: "SqltableTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 初始化表格的列
 */
Sqltable.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '使用标识', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '语句名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '语句类型', field: 'type', visible: true, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){
            		  if(value==1){
            			  return '单数据查询'}
            		  if(value==2){
            			  return '数组查询'}
            		  if(value==3){
            			  return '更新语句'}
            		  if(value==4){
            			  return '插入语句'}
            		  if(value==5){
            			  return '删除语句'}
            		  ;}},
            {title: 'sql语句', field: 'sql', visible: true, align: 'center', valign: 'middle'},
            {title: '参数', field: 'params', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Sqltable.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Sqltable.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加sqltable管理
 */
Sqltable.openAddSqltable = function () {
    var index = layer.open({
        type: 2,
        title: '添加sqltable管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sqltable/sqltable_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看sqltable管理详情
 */
Sqltable.openSqltableDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'sqltable管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sqltable/sqltable_update/' + Sqltable.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除sqltable管理
 */
Sqltable.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sqltable/delete", function (data) {
            Feng.success("删除成功!");
            Sqltable.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("sqltableId",this.seItem.id);
        ajax.start();
    }
};


Sqltable.loadType = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''></option>");
	   $("#"+key).append("<option value='1'>单数据查询</option>");
	   $("#"+key).append("<option value='2'>数组查询</option>");
	   $("#"+key).append("<option value='3'>更新语句</option>");
	   $("#"+key).append("<option value='4'>插入语句</option>"); 
	   $("#"+key).append("<option value='5'>删除语句</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}

/**
 * 查询sqltable管理列表
 */
Sqltable.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
//    queryData['type'] = $("#type").val();
    queryData['id'] = $("#id").val();
    Sqltable.table.refresh({query: queryData});
};


$(function () {
	Sqltable.loadType("type"," ");
    var defaultColunms = Sqltable.initColumn();
    var table = new BSTable(Sqltable.id, "/sqltable/list", defaultColunms);
    table.setPaginationType("client");
    table.setDefaultList(10,50,100);
	table.setDefultNumber(10);
    Sqltable.table = table.init();
});
