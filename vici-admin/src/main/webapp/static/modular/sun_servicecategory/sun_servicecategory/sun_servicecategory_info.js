/**
 * 初始化服务种类管理详情对话框
 */
var Sun_servicecategoryInfoDlg = {
    sun_servicecategoryInfoData : {},
    zTreeInstance : null
};

/**
 * 清除数据
 */
Sun_servicecategoryInfoDlg.clearData = function() {
    this.sun_servicecategoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Sun_servicecategoryInfoDlg.set = function(key, val) {
    this.sun_servicecategoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Sun_servicecategoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
Sun_servicecategoryInfoDlg.close = function() {
    parent.layer.close(window.parent.Sun_servicecategory.layerIndex);
}

/**
 * 收集数据
 */
Sun_servicecategoryInfoDlg.collectData = function() {
    this
    .set('id')
    .set('pid')
    .set('pids')
    .set('name')
    .set('num')
    ;
}

/**
 * 提交添加
 */
Sun_servicecategoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sun_servicecategory/add", function(data){
        Feng.success("添加成功!");
        window.parent.Sun_servicecategory.table.refresh();
        Sun_servicecategoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sun_servicecategoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
Sun_servicecategoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sun_servicecategory/update", function(data){
        Feng.success("修改成功!");
        window.parent.Sun_servicecategory.table.refresh();
        Sun_servicecategoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sun_servicecategoryInfoData);
    ajax.start();
}
$("#servicecategory").change(function(){
	  alert("servicecategory on change");
	});
/**
 * 动态加载服务类型
 *
 * @returns
 */
Sun_servicecategoryInfoDlg.showCategorySelectTree = function () {
    Feng.showInputTree("serviceCategoryService", "serviceCategoryServiceContent");

}
/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
Sun_servicecategoryInfoDlg.onClickDept = function(e, treeId, treeNode) {
	debugger;
    $("#serviceCategoryService").attr("value", Sun_servicecategoryInfoDlg.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
}

$(function() {
	
	//Sun_servicecategoryInfoDlg.loadSelectbyid("servicecategory","服务类型枚举");
	if (Sun_servicecategoryInfoDlg.get('id') != "") {
	var ztreeCecategory = new $ZTree("ztreeCecategory", "/sun_servicecategory/servicecategoryTreeListByProvideridId/"+Sun_servicecategoryInfoDlg.get('id'));
    ztreeCecategory.bindOnClick(Sun_servicecategoryInfoDlg.onClickDept);
	ztreeCecategory.init();
	}else{
    var ztreeCecategory = new $ZTree("ztreeCecategory", "/sun_servicecategory/servicecategorytreeList");
    ztreeCecategory.bindOnClick(Sun_servicecategoryInfoDlg.onClickDept);
    ztreeCecategory.init();
   
	}
	
	 Sun_servicecategoryInfoDlg.zTreeInstance = ztreeCecategory;
    
    
});
