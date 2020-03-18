/**
 * 初始化视图管理详情对话框
 */
var MetadataviewInfoDlg = {
    metadataviewInfoData : {}
};

/**
 * 清除数据
 */
MetadataviewInfoDlg.clearData = function() {
    this.metadataviewInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MetadataviewInfoDlg.set = function(key, val) {
    this.metadataviewInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MetadataviewInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MetadataviewInfoDlg.close = function() {
    parent.layer.close(window.parent.Metadataview.layerIndex);
}

/**
 * 收集数据
 */
MetadataviewInfoDlg.collectData = function() {
    this
    .set('id')
    .set('viewname')
    .set('viewnamecn')
    .set('replacecolumn')
    .set('tableid')
    .set('display')
    .set('update')
    .set('add')
    .set('isselect')
    .set('onclick')
    .set('ondblclick')
    .set('onfocus')
    .set('onblur')
    .set('isvalid')
    .set('updatetime')
    .set('operator')
    .set('operatnum')
    .set('fieldsort')
    .set('issort')
    .set('imp')
    .set('exp')
    .set('readonly')
    .set('viewid')
    .set('clickevent')
    .set('templet')
    .set('templetscript')
    .set('minwidth')
    .set('defaultvalue')
    ;
}

/**
 * 提交添加
 */
MetadataviewInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataview/add", function(data){
        Feng.success("添加成功!");
        window.parent.Metadataview.table.refresh();
        MetadataviewInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.metadataviewInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MetadataviewInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataview/update", function(data){
        Feng.success("修改成功!");
        window.parent.Metadataview.table.refresh();
        MetadataviewInfoDlg.close();
        window.parent.Metadataview.refresh();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.metadataviewInfoData);
    ajax.start();
}
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
MetadataviewInfoDlg.loadSelectByViewname = function (key,viewname,defaultvalue) {
	   $("#"+key).empty();
	    debugger;
	    var tempvlaue;
		var ajax = new $ax(Feng.ctxPath + "/metadataview/viewnameByid/"+viewname, function(data){
		$.each(data,function(idx,item){     
			if (item.name==defaultvalue){
				tempvlaue=item.num;
			}
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		if (defaultvalue!=""){
			 $("#"+key).val(tempvlaue);
		}
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}

MetadataviewInfoDlg.loadtablenameBytableid = function (key,tableid,defaultvalue) {
	   $("#"+key).empty();
	    var tempvlaue;
		var ajax = new $ax(Feng.ctxPath + "/metadataview/tablenameBytableid/"+tableid, function(data){
			
		$.each(data,function(idx,item){   
			if (item.name==defaultvalue){
				tempvlaue=item.num;
			}
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		if (defaultvalue!=""){
			 $("#"+key).val(tempvlaue);
		}
		
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
MetadataviewInfoDlg.loadFieldByTablename = function (key,tablename,defaultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataview/fieldByTablename/"+tablename, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		if (defaultvalue!=""){
            $("#"+key).val(defaultvalue);
		}
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
MetadataviewInfoDlg.loadviewnamecnByViewname = function (key,viewnameid) {
	debugger;
	   $("#"+key).empty();
		var ajax = new $ax(Feng.ctxPath + "/metadataview/viewnamecnByViewname/"+viewnameid, function(data){
			debugger;
			$.each(data,function(idx,item){     
				$("#"+key).val(item.name); 
				})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}

MetadataviewInfoDlg.loadtitleByid = function (key,tableid) {
	debugger;
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataview/titleByid/"+tableid, function(data){
			debugger;
			$.each(data,function(idx,item){     
				$("#"+key).val(item.name); 
				})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
MetadataviewInfoDlg.changeviewname=function(obj){
	debugger;
	$("#viewname").val(obj.selectedOptions["0"].innerText);
	if (obj.value != "") {
		MetadataviewInfoDlg.loadviewnamecnByViewname("viewnamecn",obj.value+"");
		}else{
			$("#viewnamecn").val("");
		}

	
	}


MetadataviewInfoDlg.changetablename=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewInfoDlg.loadFieldByTablename("tableid",obj.selectedOptions["0"].innerText,"");
		}else{
			$("#tableid").val("");
		}
	$("#tableid").val("");
	$("#fieldtitle").val("");
}

MetadataviewInfoDlg.changetableid=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewInfoDlg.loadtitleByid("fieldtitle",obj.value+"");
		}else{
			$("#fieldtitle").val("");
		}
    }
MetadataviewInfoDlg.loadDict = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''>请选择</option>");
	   $("#"+key).append("<option value='0'>否</option>"); 
	   $("#"+key).append("<option value='1'>是</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}
$(function() {
	debugger;
      MetadataviewInfoDlg.loadSelectByViewname("viewnameselect"," ",$("#viewname").val());
      MetadataviewInfoDlg.loadtablenameBytableid("tablename"," ",$("#tablenamehid").val());
      MetadataviewInfoDlg.loadFieldByTablename("metadataid",$("#tablenamehid").val(),$("#metadataidhid").val());
      MetadataviewInfoDlg.loadDict("display",$("#displayhid").val());
  	  MetadataviewInfoDlg.loadDict("update",$("#updatehid").val());
   	  MetadataviewInfoDlg.loadDict("add",$("#addhid").val());
  	  MetadataviewInfoDlg.loadDict("isselect",$("#isselecthid").val());
  	  MetadataviewInfoDlg.loadDict("issort",$("#issorthid").val());
  	  MetadataviewInfoDlg.loadDict("imp",$("#imphid").val());
  	  MetadataviewInfoDlg.loadDict("exp",$("#exphid").val());
  	  MetadataviewInfoDlg.loadDict("readonly",$("#readonlyhid").val());
      MetadataviewInfoDlg.loadDict("viewid",$("#viewidhid").val());
});
