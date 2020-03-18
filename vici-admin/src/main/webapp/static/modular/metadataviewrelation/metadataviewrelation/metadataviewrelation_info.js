/**
 * 初始化视图关系管理详情对话框
 */
var MetadataviewrelationInfoDlg = {
    metadataviewrelationInfoData : {},
    zTreeInstance : null
};

/**
 * 清除数据
 */
MetadataviewrelationInfoDlg.clearData = function() {
    this.metadataviewrelationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MetadataviewrelationInfoDlg.set = function(key, val) {
    this.metadataviewrelationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MetadataviewrelationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MetadataviewrelationInfoDlg.close = function() {
    parent.layer.close(window.parent.Metadataviewrelation.layerIndex);
}

/**
 * 收集数据
 */
MetadataviewrelationInfoDlg.collectData = function() {
    this
    .set('id')
    .set('viewname')
    .set('relation')
    .set('addafter')
    .set('mastertable')
    .set('deletetables')
    .set('addview')
    .set('editview')
    .set('listview')
    .set('orderfield')
    .set('detailview')
    .set('rowclickevent')
    .set('toolbar')
    .set('toolbarscript')
    .set('selectplus')
    .set('toolbarwidth')
    ;
}

/**
 * 提交添加
 */
MetadataviewrelationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataviewrelation/add", function(data){
        Feng.success("添加成功!");
        window.parent.Metadataviewrelation.table.refresh();
        MetadataviewrelationInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.metadataviewrelationInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MetadataviewrelationInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataviewrelation/update", function(data){
        Feng.success("修改成功!");
        window.parent.Metadataviewrelation.table.refresh();
        MetadataviewrelationInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.metadataviewrelationInfoData);
    ajax.start();
}
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
MetadataviewrelationInfoDlg.loadSelectByViewname = function (key,viewname) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataview/viewnameByid/"+viewname, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
MetadataviewrelationInfoDlg.loadSelectByViewnamedef = function (key,viewname,defaultvalue) {
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
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
MetadataviewrelationInfoDlg.loadSelectTablenameByViewname = function (key,viewname) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataviewrelation/tablenameByViewname/"+viewname, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}

MetadataviewrelationInfoDlg.loadFieldByTablename = function (key,tablename) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataview/fieldByTablename/"+tablename, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
MetadataviewrelationInfoDlg.changeviewname=function(obj){
	debugger;
	$("#viewname").val(obj.selectedOptions["0"].innerText);
	if (obj.value != "") {
		//MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("mastertable",obj.selectedOptions["0"].innerText);
		MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("mastertable",obj.selectedOptions["0"].innerText);
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("relation_left_table",obj.selectedOptions["0"].innerText);
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("relation_right_table",obj.selectedOptions["0"].innerText);
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("addafter_table",obj.selectedOptions["0"].innerText);
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("addafter_right_table",obj.selectedOptions["0"].innerText);
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("addafter_where_table",obj.selectedOptions["0"].innerText);
		MetadataviewrelationInfoDlg.loadtables("ztreeDeletetables",obj.selectedOptions["0"].innerText);
		 MetadataviewrelationInfoDlg.loadSelecttree("deletetables","ztreeDeletetables");
		}else{
			$("#viewnamecn").val("");
			 $("#mastertable").val("");
			 $("#mastertable").val("deletetables");
			 
		}

	
	}
MetadataviewrelationInfoDlg.showTableSelectTree = function () {
	debugger;
    //Feng.showInputTree("tableids", "tableidsContent");
	// $("#mastertable").val("");
    Feng.showInputTreeNohide("deletetables", "deletetablesContent","ztreeDeletetables");
  // $("#mastertable").attr("value", MetadataviewrelationInfoDlg.zTreeInstance.getSelectedVal());
}
/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
//MetadataviewrelationInfoDlg.onClickIds = function(e, treeId, treeNode) {
//	debugger;
//    $("#mastertable").attr("value", MetadataviewrelationInfoDlg.zTreeInstance.getSelectedVal());
//   // $("#pid").attr("value", treeNode.id);
//}
MetadataviewrelationInfoDlg.loadtables = function (key,viewname) {
	debugger;
	var setting = {
            check: {
                enable: true,
                chkboxType: { "Y": "ps", "N": "ps" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
      // var ztreeTableids = new $ZTree("ztreeTableids", "/sun_servicecategory/servicecategorytreeList"+tableid);
	   var ztreeTableids = new $ZTree(key, "/metadataviewrelation/getTablenameTreeByViewname/"+viewname);
	  // ztreeTableids.bindOnClick(MetadataviewInfoDlg.onClickIds);
       ztreeTableids.setSettings(setting);
       ztreeTableids.init();
	  
	}
/**
 * 动态树的信息
 *
 * @returns
 */
MetadataviewrelationInfoDlg.loadSelecttree = function (inputId,ztreeid) {
				 
	  // var idsName =Feng.zTreeCheckedNodesName(ztreeid);
	     $("#"+inputId).attr("value", "");
      // $("#"+inputId).attr("value", idsName);
	     $("#"+inputId).attr("value", ztreeid);
	 }
MetadataviewrelationInfoDlg.loadDict = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''>请选择</option>");
	   $("#"+key).append("<option value='0'>否</option>"); 
	   $("#"+key).append("<option value='1'>是</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}
MetadataviewrelationInfoDlg.loadRelationRel = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value='and'>and</option>"); 
	   $("#"+key).append("<option value='or'>or</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}

MetadataviewrelationInfoDlg.loadRelationSign = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value='='>=</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}
MetadataviewrelationInfoDlg.changeReltaionLeftTable=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewrelationInfoDlg.loadFieldByTablename("relation_left_col",obj.value+"");
		}else{
			$("#relation_left_col").val("");
		}

	
	}

MetadataviewrelationInfoDlg.changeReltaionRightTable=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewrelationInfoDlg.loadFieldByTablename("relation_right_col",obj.value+"");
		}else{
			$("#relation_right_col").val("");
		}

	
	}
MetadataviewrelationInfoDlg.changeAddafterTable=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewrelationInfoDlg.loadFieldByTablename("addafter_left_col",obj.value+"");
		MetadataviewrelationInfoDlg.loadFieldByTablename("addafter_where_left_col",obj.value+"");
		}else{
			$("#addafter_left_col").val("");
			$("#addafter_where_left_col").val("");
			
		}

	
	}

MetadataviewrelationInfoDlg.changeAddafterRightTable=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewrelationInfoDlg.loadFieldByTablename("addafter_right_col",obj.value+"");
		}else{
			$("#addafter_right_col").val("");
		}

	
	}
MetadataviewrelationInfoDlg.changeAddafterWhereTable=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewrelationInfoDlg.loadFieldByTablename("addafter_where_right_col",obj.value+"");
		}else{
			$("#addafter_where_right_col").val("");
		}

	
	}
MetadataviewrelationInfoDlg.addrelation=function(obj){
	debugger;
   // var str;
  var str=$("#relation").val();
  str=str.trim();
    str += $("#relation_rel").val() +" "+$("#relation_left_table").val()+"."+$("#relation_left_col").find("option:selected").text()+
    " "+$("#relation_sign").val()+"  "+$("#relation_right_table").val()+"."+$("#relation_right_col").find("option:selected").text()+" ";
	//$("#relation").append(str);
    $("#relation").val(str);
	
	}
MetadataviewrelationInfoDlg.addafter=function(obj){
	debugger;
	var str=$("#addafter").val();
	str   =   str.replace(/^\s+|\s+$/g,"");
	 str += "update " +$("#addafter_table").val()+" set "+$("#addafter_left_col").find("option:selected").text()+
	    " =  #{"+$("#addafter_right_table").val()+"."+$("#addafter_right_col").find("option:selected").text()+"}"+" where "+$("#addafter_where_left_col").find("option:selected").text()+"= #{"+ $("#addafter_where_table").val()+"."+$("#addafter_where_right_col").find("option:selected").text()+"} ; ";
	$("#addafter").val(str);
	//$("#addafter").append(str);
	
	}

$(function() {
	 if (MetadataviewrelationInfoDlg.get('id') != "") {
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("mastertable",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("relation_left_table",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("relation_right_table",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("addafter_table",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("addafter_right_table",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadSelectTablenameByViewname("addafter_where_table",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadRelationRel("relation_rel"," ");
		 MetadataviewrelationInfoDlg.loadDict("toolbar",$("#toolbarhid").val());
		 MetadataviewrelationInfoDlg.loadRelationSign("relation_sign"," ");
		 MetadataviewrelationInfoDlg.loadSelectByViewnamedef("viewnameselect"," ",$("#viewname").val());
		 MetadataviewrelationInfoDlg.loadtables("ztreeDeletetables",$("#viewname").val())
		 $("#deletetables").attr("value",$("#deletetableshid").val());
		 //$("#mastertable").val("aa");
		 $("#mastertable").val($("#mastertablehid").val());
		// MetadataviewrelationInfoDlg.loadSelecttree("deletetables","ztreeDeletetables");
	 }else{
		 MetadataviewrelationInfoDlg.loadSelectByViewname("viewnameselect"," ");
		 MetadataviewrelationInfoDlg.loadRelationRel("relation_rel"," ");
		 MetadataviewrelationInfoDlg.loadRelationSign("relation_sign"," ");
		 MetadataviewrelationInfoDlg.loadDict("toolbar","0");
		 MetadataviewrelationInfoDlg.loadSelecttree("toolbarwidth","200");
	
	 }
});
