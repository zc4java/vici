/**
 * 初始化sqltable管理详情对话框
 */
var SqltableInfoDlg = {
    sqltableInfoData : {}
};

/**
 * 清除数据
 */
SqltableInfoDlg.clearData = function() {
    this.sqltableInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SqltableInfoDlg.set = function(key, val) {
    this.sqltableInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SqltableInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SqltableInfoDlg.close = function() {
    parent.layer.close(window.parent.Sqltable.layerIndex);
}

/**
 * 收集数据
 */
SqltableInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('type')
    .set('sql')
    .set('params')
    ;
}

/**
 * 提交添加
 */
SqltableInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sqltable/add", function(data){
        Feng.success("添加成功!");
        window.parent.Sqltable.table.refresh();
        SqltableInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sqltableInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SqltableInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sqltable/update", function(data){
        Feng.success("修改成功!");
        window.parent.Sqltable.table.refresh();
        SqltableInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sqltableInfoData);
    ajax.start();
}

SqltableInfoDlg.loadType = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value='1'>单数据查询</option>");
	   $("#"+key).append("<option value='2'>数组查询</option>");
	   $("#"+key).append("<option value='3'>更新语句</option>");
	   $("#"+key).append("<option value='4'>插入语句</option>"); 
	   $("#"+key).append("<option value='5'>删除语句</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}



$(function() {
	
	SqltableInfoDlg.loadType("type",$("#typeView").val());
});
