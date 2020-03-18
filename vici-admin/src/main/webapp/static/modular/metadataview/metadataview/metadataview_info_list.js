/**
 * 初始化视图管理详情对话框
 */
var MetadataviewInfoDlg = {
    metadataviewInfoData : {},
    zTreeInstance : null,
    id: "MetadataViewTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    insertrow: {},
    layerIndex: -1
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
    .set('metadataid')
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
    .set('minwidth')
    .set('clickevent')
    .set('defaultvalue')
    ;
}
/**
 * 检查可以提交
 */
MetadataviewInfoDlg.check = function () {
	  var allTableData = $('#'+MetadataviewInfoDlg.id).bootstrapTable('getData');
		var isExists =false
		  $.each(allTableData,function (n, value) {
			  if(value.viewid=='1'){
				 isExists =true
			  }
		  });
		if(isExists){
			return true;
		}else{ 
			
			 Feng.info("必须存在一个主id");
			 return false;
		}	
};

/**
 * 新增-保存
 */
MetadataviewInfoDlg.addSave = function() {

    this.clearData();
    this.collectData();

    var allTableDatastr = JSON.stringify($("#"+MetadataviewInfoDlg.id).bootstrapTable('getData'));
    if (this.check()) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataview/update_list", function(data){
        Feng.success("保存成功!");
        //$("[name='refresh']").eq(0).click();
        $("#viewname").attr("readonly","readonly")
        $("#viewnamecn").attr("readonly","readonly")
        window.parent.Metadataview.table.refresh();
    },function(data){
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("allTableDatastr",allTableDatastr);
    ajax.start();
    }
}

/**
 * 新增-提交
 */
MetadataviewInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    var allTableDatastr = JSON.stringify($("#"+MetadataviewInfoDlg.id).bootstrapTable('getData'));
    if (this.check()) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataview/update_list", function(data){
        Feng.success("添加成功!");
        window.parent.Metadataview.table.refresh();
        MetadataviewInfoDlg.close();
        window.parent.Metadataview.refresh();
    },function(data){
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("allTableDatastr",allTableDatastr);
    ajax.start();
    }
}

/**
 * 修改-保存
 */
MetadataviewInfoDlg.saveData = function() {

    this.clearData();
    this.collectData();
    var allTableDatastr = JSON.stringify($("#"+MetadataviewInfoDlg.id).bootstrapTable('getData'));
    debugger;
    if (this.check()) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataview/update_list", function(data){
        Feng.success("保存成功!");
       // $("[name='refresh']").eq(0).click();
        window.parent.Metadataview.refresh();
        window.parent.Metadataview.table.refresh();
    },function(data){
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("allTableDatastr",allTableDatastr);
    ajax.start();
    }
}

/**
 * 修改-提交
 */
MetadataviewInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    var allTableDatastr = JSON.stringify($("#"+MetadataviewInfoDlg.id).bootstrapTable('getData'));
    debugger;
    if (this.check()) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadataview/update_list", function(data){
        Feng.success("修改成功!");
        window.parent.Metadataview.table.refresh();
        MetadataviewInfoDlg.close();
    },function(data){
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set("allTableDatastr",allTableDatastr);
    ajax.start();
    }
}
/**
 * 按照viewname进行加载视图列表
 *
 * @returns
 */
MetadataviewInfoDlg.loadSelectByViewname = function (key,viewname) {
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
MetadataviewInfoDlg.loadtablenameBytableid = function (key,tableid) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/metadataview/tablenameBytableid/"+tableid, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
MetadataviewInfoDlg.loadFieldByTablename = function (key,tablename) {
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
MetadataviewInfoDlg.loadtitleByid = function (key,tableid) {
	debugger;
	   $("#"+key).empty();
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
MetadataviewInfoDlg.loadtableids = function (key,tablename) {
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
	   var ztreeTableids = new $ZTree(key, "/metadataview/getTableIdListByTablename/"+tablename);
	  // ztreeTableids.bindOnClick(MetadataviewInfoDlg.onClickIds);
       ztreeTableids.setSettings(setting);
       ztreeTableids.init();
	  
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
		MetadataviewInfoDlg.loadFieldByTablename("metadataid",obj.selectedOptions["0"].innerText);
		MetadataviewInfoDlg.loadtableids("ztreeMetadataidids",obj.selectedOptions["0"].innerText)
		}else{
			$("#tableid").val("");
		}
}

MetadataviewInfoDlg.changetableid=function(obj){
	debugger;
	if (obj.value != "") {
		MetadataviewInfoDlg.loadtitleByid("fieldtitle",obj.value+"");
		}else{
			$("#fieldtitle").val("");
		}

	
	}
MetadataviewInfoDlg.showTableidsSelectTree = function () {
	debugger;
    Feng.showInputTreeNohide("metadataids", "metadataidsContent","ztreeMetadataidids");
}
/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MetadataviewInfoDlg.onClickIds = function(e, treeId, treeNode) {
	debugger;
    $("#metadataids").attr("value", MetadataviewInfoDlg.zTreeInstance.getSelectedVal());

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

MetadataviewInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '视图名', field: 'viewname', visible: false, align: 'center', valign: 'middle'},
            {title: '视图名中文', field: 'viewnamecn', visible: false, align: 'center', valign: 'middle'},
            {title: '重写字段', field: 'replacecolumn', visible: false, align: 'center', valign: 'middle'},
            {title: '数据库表字段', field: 'metadataid', visible: true, align: 'center', valign: 'middle',
            	editable: {
                    type: 'select',
                    title: '级联删除的父节点',
                    disabled: true,  
    	           	showbuttons: false,
    	           	 mode: "inline",  
                    emptytext: "请选择",  
                    source: function() {//动态获取数据
                        var result = [];
                    	var ajax = new $ax(Feng.ctxPath + "/metadataview/fieldByTablename/"+" ", function(data){
              			debugger;
              		$.each(data,function(idx,item){     
              		   result.push({
                             value : item.num,
                             text : item.name
                         });
              		})
              		},function(data){
              		Feng.error("获取二级代码失败!" + data.responseJSON.message + "!");
              		});
              		ajax.start();
                        return result;}
                }},
            {title: '表名', field: 'tablename', visible: true, align: 'center', valign: 'middle'},
            {title: '字段名', field: 'field', visible: false, align: 'center', valign: 'middle'},
            {title: '注释', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '页面顺序', field: 'fieldsort', visible: true, align: 'center', valign: 'middle',
            	  editable: {
            	 	 type: 'text',
               	 disabled: false,  
                 showbuttons: false,
                 title: '字段页面显示顺序',
                 mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                 emptytext: "请录入",
                 validate: function(v){
                	    if (isNaN(v)){return '必须是数字';}
                 }
          	  }},
            {title: '列表展示', field: 'display', visible: true, align: 'center', valign: 'middle',
          	  editable: {
                  type: 'select',
                  title: '是否在列表展示',
                  disabled: false,  
  	           	 showbuttons: false,
  	           	 mode: "inline",  
                  emptytext: "请选择",  
                  source:[{value:"0",text:"否"},{value:"1",text:"是"}]
        	  }},
            {title: '更新展示', field: 'update', visible: true, align: 'center', valign: 'middle',
          	  editable: {
                  type: 'select',
                  title: '是否在更新页面展示',
                  disabled: false,  
  	           	 showbuttons: false,
  	           	 mode: "inline",  
                  emptytext: "请选择",  
                  source:[{value:"0",text:"否"},{value:"1",text:"是"}]
        	  }},
            {title: '添加展示', field: 'add', visible: true, align: 'center', valign: 'middle',
          	  editable: {
                  type: 'select',
                  title: '是否在添加页面展示',
                  disabled: false,  
  	           	 showbuttons: false,
  	           	 mode: "inline",  
                  emptytext: "请选择",  
                  source:[{value:"0",text:"否"},{value:"1",text:"是"}]
        	  }},
          
            {title: '有效标志', field: 'isvalid', visible: false, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updatetime', visible: false, align: 'center', valign: 'middle'},
            {title: '更新人', field: 'operator', visible: false, align: 'center', valign: 'middle'},
            {title: '操作号', field: 'operatnum', visible: false, align: 'center', valign: 'middle'},
            {title: '操作序号', field: 'operatnumbefore', visible: false, align: 'center', valign: 'middle'},
         
            {title: '列表排序', field: 'issort', visible: true, align: 'center', valign: 'middle',
          	  editable: {
                  type: 'select',
                  title: '是否是主键',
                  disabled: false,  
  	           	 showbuttons: false,
  	           	 mode: "inline",  
                  emptytext: "请选择",  
                  source:[{value:"0",text:"否"},{value:"1",text:"是"}]
        	  }},
          
            {title: '只读', field: 'readonly', visible: true, align: 'center', valign: 'middle',
          	  editable: {
                  type: 'select',
                  title: '是否只读',
                  disabled: false,  
  	           	 showbuttons: false,
  	           	 mode: "inline",  
                  emptytext: "请选择",  
                  source:[{value:"0",text:"否"},{value:"1",text:"是"}]
        	  }},
            {title: '主id', field: 'viewid', visible: true, align: 'center', valign: 'middle',
          	  editable: {
                  type: 'select',
                  title: '是否为主id',
                  disabled: false,  
  	           	 showbuttons: false,
  	           	 mode: "inline",  
                  emptytext: "请选择",  
                  source:[{value:"0",text:"否"},{value:"1",text:"是"}]
        	  }},
        	  {title: '查询', field: 'isselect', visible: true, align: 'center', valign: 'middle',
              	  editable: {
                      type: 'select',
                      title: '是否为查询条件',
                      disabled: false,  
      	           	 showbuttons: false,
      	           	 mode: "inline",  
                      emptytext: "请选择",  
                      source:[{value:"0",text:"否"},{value:"1",text:"是"}]
            	  }},
                  {title: '默认值', field: 'defaultvalue', visible: true, align: 'center', valign: 'middle',
               	   editable: {
               		   type: 'text',
               		   disabled: false,  
               		   showbuttons: false,
               		   title: '默认值',
               		   mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
               		   emptytext: "请录入"  
               	   }},
            	  {title: 'excel导入', field: 'imp', visible: true, align: 'center', valign: 'middle',
                  	  editable: {
                          type: 'select',
                          title: 'excel导入',
                          disabled: false,  
          	           	 showbuttons: false,
          	           	 mode: "inline",  
                          emptytext: "请选择",  
                          source:[{value:"0",text:"否"},{value:"1",text:"是"}]
                	  }},
                    {title: 'excel导出', field: 'exp', visible: true, align: 'center', valign: 'middle',
                    	  editable: {
                              type: 'select',
                              title: 'excel导出',
                              disabled: false,  
              	           	 showbuttons: false,
              	           	 mode: "inline",  
                              emptytext: "请选择",  
                              source:[{value:"0",text:"否"},{value:"1",text:"是"}]
                    	  }},
                {title: '单击方法', field: 'onclick', visible: true, align: 'center', valign: 'middle',
                	editable: {
                     	 type: 'text',
                     	 disabled: false,  
                       showbuttons: false,
                       title: '字段页面显示顺序',
                       mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                       emptytext: "请录入"  
                  }},
                {title: '双击方法', field: 'ondblclick', visible: true, align: 'center', valign: 'middle',
                	editable: {
                     	 type: 'text',
                     	 disabled: false,  
                       showbuttons: false,
                       title: '字段页面显示顺序',
                       mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                       emptytext: "请录入"  
                  }},
                {title: '获得焦点', field: 'onfocus', visible: true, align: 'center', valign: 'middle',
                	editable: {
                     	 type: 'text',
                     	 disabled: false,  
                       showbuttons: false,
                       title: '字段页面显示顺序',
                       mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                       emptytext: "请录入"  
                  }},
                {title: '失去焦点', field: 'onblur', visible: true, align: 'center', valign: 'middle',
                	editable: {
                     	 type: 'text',
                     	 disabled: false,  
                       showbuttons: false,
                       title: '字段页面显示顺序',
                       mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                       emptytext: "请录入"  
                  }},
                {title: '单元格点击事件', field: 'clickevent', visible: true, align: 'center', valign: 'middle',
                	editable: {
                      	 type: 'text',
                      	 disabled: false,  
                        showbuttons: false,
                        title: '单元格点击事件',
                        mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                        emptytext: "请录入"  
                   }},

                {title: '单元格最小宽度', field: 'minwidth', visible: true, align: 'center', valign: 'middle',
                	editable: {
                      	 type: 'text',
                      	 disabled: false,  
                        showbuttons: false,
                        title: '单元格宽度',
                        mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                        emptytext: "请录入",
                        validate: function(v){
                       	 return isNumber(v)
                        }
                   }},

    ];
};
MetadataviewInfoDlg.AddMetadata = function () {
	debugger;
	var allTableData = $('#'+MetadataviewInfoDlg.id).bootstrapTable('getData');
	var allTableDataone = $('#'+MetadataviewInfoDlg.id).bootstrapTable('getData')[0];
	var isExists =false
	  $.each(allTableData,function (n, value) {
		  if(value.metadataid==$("#metadataid").val()){
			  Feng.info("字段已经存在");
			  isExists =true
			 
		  }
	  });
	if(isExists){
		 return false;
	}	
	
	 if($("#viewname").val()==""||$("#viewname").val()==null||$("#viewnamecn").val()==""||$("#viewnamecn").val==null){
		   Feng.info("请填写表头");
		  return false;
	 }
	 if($("#metadataid").val()==null||$("#metadataid").val()==""){
		   Feng.info("请选择列");
		  return false;
	 }
	 var indexall=$('#'+MetadataviewInfoDlg.id).bootstrapTable('getOptions').totalRows
	 var rowdata= {viewname:$("#viewname").val(),
		  viewnamecn:$("#viewnamecn").val(),
		    metadataid:$("#metadataid").val(),
		    tablename:$("#tablename")[0].selectedOptions["0"].innerText,
		    title:$("#fieldtitle").val(),
		    selectItem:false,
			decimalpoint:"",
			display:"1",
			update:"1",
			add:"1",
			isselect:"0",
			onclick:"",
			ondblclick:"",
			defaultvalue:"",
			onfocus:"",
			onblur:"",
			fieldsort:"0",
			issort:"0",
			imp:"1",
			exp:"1",
			readonly:"0",
			id:"",
			viewid:"0",
			clickevent:"",
			minwidth:"80"
		};
		
//	 if(indexall>0){
//		 $('#MetadataTable').bootstrapTable('getOptions').data.splice(indexall-1, 1,allTableDataone);
//	 }
//	 MetadataInfoDlg.insertrow['index']= $('#MetadataTable').bootstrapTable('getOptions').totalRows;
//	 $('#MetadataTable').bootstrapTable('append',MetadataInfoDlg.insertrow)
	$('#'+MetadataviewInfoDlg.id).bootstrapTable('insertRow', {
     	index:indexall,
     	row:rowdata});
	 $('#'+MetadataviewInfoDlg.id).bootstrapTable('getOptions').data.splice(indexall,1,rowdata);
	// $('#MetadataTable').bootstrapTable('load', $("#MetadataTable").bootstrapTable('getData'));
};
MetadataviewInfoDlg.DelMetadata = function() {
	debugger;
	// selected[0].selectItem
	var selected = $('#' + MetadataviewInfoDlg.id).bootstrapTable('getSelections');
	if (selected[0].id != "") {
		Feng.info("此模块不允许删除已经存在的记录！");
		return false;
	}
	if (selected.length == 0) {
		Feng.info("请先选中表格中的某一记录！");
		return false;
	} else {
		var swit = false;
		$.ajax({
			type : "get",
			url : '/metadataview/list/init/' + selected[0].viewname,
			data : {},
			async : false,
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (selected[0].metadataid == data[i].metadataid) {
						swit = true;
						return;
					}
				}
			},
			error : function(data) {
				layer.msg('数据出错，请检查数据库');
			}
		});
		if (swit) {
			Feng.info("此模块不允许删除已经存在的记录！");
			return false;
		} else {

			$("#" + MetadataviewInfoDlg.id).bootstrapTable('remove', {
				field : 'selectItem',
				values : [ true ]
			});
			return true;
		}
	}

};
$("#MetadataviewTable")
.on('click-row.bs.table', function (e, row, ele,field) {
	debugger;
	var  index= ele.data('index');

	 $('#MetadataviewTable').bootstrapTable('getOptions').data.splice(index,1,row);

})

$(function() {
	  debugger;
		var defaultColunms = MetadataviewInfoDlg.initColumn();
	 
		 if (MetadataviewInfoDlg.get('id') != "") {
				debugger;
			 var table = new BSTable(MetadataviewInfoDlg.id, "/metadataview/list/init/"+MetadataviewInfoDlg.get('viewnamehid'), defaultColunms);
			 table.setPaginationType("client");
			 table.setDefaultList(10,'All');
			 table.setDefultNumber('All');
			// table.setFixedColumns("true","3");
			  $("#viewname").val($("#viewnamehid").val());
			  $("#viewnamecn").val($("#viewnamecnhid").val());
             MetadataviewInfoDlg.loadtablenameBytableid("tablename"," ");
             MetadataviewInfoDlg.table = table.init();
		 }else{
			  var table = new BSTable(MetadataviewInfoDlg.id, "/metadataview/list/init/"+" ", defaultColunms);
			   table.setPaginationType("client");
			  // table.setFixedColumns("true","3");
			   table.setDefaultList(10,'All');
			   table.setDefultNumber('All');
			   MetadataviewInfoDlg.table = table.init();
			   MetadataviewInfoDlg.loadtablenameBytableid("tablename"," ");
		 }
    
});
function isNumber(v){
    if (isNaN(v)){return '必须是数字';}
    if (parseInt(v) < 0) {return '必须为正整数';}
    if(Math.floor(v)!=v){return '必须为整数';}
}