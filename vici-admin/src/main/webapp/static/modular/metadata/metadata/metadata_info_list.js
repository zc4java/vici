/**
 * 初始化元数据管理详情对话框
 */
var MetadataInfoDlg = {
    metadataInfoData : {},
    id: "MetadataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    insertrow: {},
    layerIndex: -1
};

/**
 * 清除数据
 */
MetadataInfoDlg.clearData = function() {
    this.metadataInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MetadataInfoDlg.set = function(key, val) {
    this.metadataInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MetadataInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MetadataInfoDlg.close = function() {
    parent.layer.close(window.parent.Metadata.layerIndex);
}

/**
 * 收集数据
 */
MetadataInfoDlg.collectData = function() {
    this
    .set('id')
    .set('tablename')
    .set('tablenamecn')
    .set('field')
    .set('fieldtype')
    .set('fieldlength')
    .set('decimalpoint')
    .set('isnullable')
    .set('title')
    .set('isdelcascade')
    .set('fathernode')
    .set('ismajorkey')
    .set('isvalid')
    .set('updatetime')
    .set('operator')
    .set('operatnum')
    .set('display')
    .set('update')
    .set('add')
    .set('verifyrole')
    .set('isselect')
    .set('dict')
    .set('inputtype')
    .set('onclick')
    .set('ondblclick')
    .set('onfocus')
    .set('onblur')
    .set('fieldsort')
    .set('issort')
    .set('imp')
    .set('exp')
    .set('readonly')
    .set('clickevent')
    .set('minwidth')
    .set('selecttype')
    .set('defaultvalue')

    ;
}

/**
 * 新增
 */
MetadataInfoDlg.addSave = function() {

	if ($("#tablename").val() == ""||$("#tablenamecn").val() == ""||$("#tablename").val() == null||$("#tablenamecn").val() == null) {
		Feng.info("请填写表头");
		return false;
	}
	this.clearData();
	this.collectData();
	for (var i = 0; i < $("#" + MetadataInfoDlg.id).bootstrapTable('getData').length; i++) {// 修改表名表中文名
		$("#" + MetadataInfoDlg.id).bootstrapTable('getData')[i].tablename = $("#tablename").val();
		$("#" + MetadataInfoDlg.id).bootstrapTable('getData')[i].tablenamecn = $("#tablenamecn").val();
	}
	var allTableDatastr = JSON.stringify($("#MetadataTable").bootstrapTable('getData'));
	allTableDatastr = Feng.htmlspecialchars_decode(allTableDatastr);
	// 提交信息
	if (this.check()) {

		var ajax = new $ax(Feng.ctxPath + "/metadata/addList", function(data) {
			Feng.success("保存成功!");
			$("#tablename").attr("readonly", "readonly")
			$("#tablenamecn").attr("readonly", "readonly")
			//$("[name='refresh']").eq(0).click();

			window.parent.Metadata.table.refresh();
			window.parent.Metadata.refresh();
		}, function(data) {

			Feng.error(data.responseJSON.message + "!");
		});

		ajax.set(this.metadataInfoData);
		ajax.set("allTableDatastr", allTableDatastr);
		ajax.start();
	}

}


/**
 * 新增并关闭
 */
MetadataInfoDlg.addSubmit = function() {

	this.clearData();
	this.collectData();
	// var allTableData=$("#MetadataTable").bootstrapTable('getData')
	//    
	// $.each(allTableData,function (n, value) {
	// if(value.metadataid==$("#metadataid").val()){
	// Feng.info("字段已经存在");
	// }
	// });
	if ($("#tablename").val() == ""||$("#tablenamecn").val() == ""||$("#tablename").val() == null||$("#tablenamecn").val() == null) {
		Feng.info("请填写表头");
		return false;
	}

	var allTableDatastr = JSON.stringify($("#MetadataTable").bootstrapTable('getData'));
	allTableDatastr = Feng.htmlspecialchars_decode(allTableDatastr);
	debugger;
	// 提交信息
	if (this.check()) {
		var ajax = new $ax(Feng.ctxPath + "/metadata/addList", function(data) {
			Feng.success("添加成功!");
			window.parent.Metadata.table.refresh();
			window.parent.Metadata.refresh();
			MetadataInfoDlg.close();
		}, function(data) {
			Feng.error(data.responseJSON.message + "!");
		});

		ajax.set(this.metadataInfoData);
		ajax.set("allTableDatastr", allTableDatastr);
		// ajax.setData("allTableDatastr : "+allTableDatastr);
		ajax.start();
	}
}


/**
 * 修改保存 与添加操作一致所以调用addList方法
 */
MetadataInfoDlg.saveData = function() {

	this.clearData();
	this.collectData();
	/*
	 * for(var i=0;i<$("#"+MetadataInfoDlg.id).bootstrapTable('getData').length;i++){//修改表名表中文名
	 * $("#"+MetadataInfoDlg.id).bootstrapTable('getData')[i].tablename=$("#tablename").val();
	 * $("#"+MetadataInfoDlg.id).bootstrapTable('getData')[i].tablenamecn=$("#tablenamecn").val(); }
	 */
	if ($("#tablename").val() == ""||$("#tablenamecn").val() == ""||$("#tablename").val() == null||$("#tablenamecn").val() == null) {
		Feng.info("请填写表头");
		return false;
	}
	var allTableDatastr = JSON.stringify($("#" + MetadataInfoDlg.id).bootstrapTable('getData'));
	allTableDatastr = Feng.htmlspecialchars_decode(allTableDatastr);
	if (this.check()) {
		// 提交信息
		var ajax = new $ax(Feng.ctxPath + "/metadata/addList", function(data) {
			Feng.success("保存成功!");
			//$("[name='refresh']").eq(0).click();
			window.parent.Metadata.table.refresh();
		}, function(data) {

			Feng.error(data.responseJSON.message + "!");
		});
		ajax.set("allTableDatastr", allTableDatastr);
		ajax.start();
	}
}
/**
 * 提交修改
 */
MetadataInfoDlg.editSubmit = function() {

	this.clearData();
	this.collectData();
	var allTableDatastr = JSON.stringify($("#" + MetadataInfoDlg.id).bootstrapTable('getData'));
	allTableDatastr = Feng.htmlspecialchars_decode(allTableDatastr);
	if ($("#tablename").val() == ""||$("#tablenamecn").val() == ""||$("#tablename").val() == null||$("#tablenamecn").val() == null) {
		Feng.info("请填写表头");
		return false;
	}
	if (this.check()) {
		// 提交信息
		var ajax = new $ax(Feng.ctxPath + "/metadata/addList", function(data) {
			Feng.success("修改成功!");
			window.parent.Metadata.table.refresh();
			MetadataInfoDlg.close();
		}, function(data) {
			Feng.error(data.responseJSON.message + "!");
		});

		ajax.set("allTableDatastr", allTableDatastr);
		ajax.start();
	}
}
MetadataInfoDlg.loadDict = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''>请选择</option>");
	   $("#"+key).append("<option value='0'>否</option>"); 
	   $("#"+key).append("<option value='1'>是</option>"); 
	   if(defultvalue!=""){
		 $("#"+key).val(defultvalue);
	   }
	  
	}
MetadataInfoDlg.loadDictFiledtype = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value='int'>int</option>");
	   $("#"+key).append("<option value='varchar'>varchar</option>"); 
	   $("#"+key).append("<option value='numeric'>numeric</option>"); 
	   $("#"+key).append("<option value='date'>date</option>"); 
	   if(defultvalue!=""){
			 $("#"+key).val(defultvalue);
		   }
	  
	}
/*
//加载inputtype二级代码
MetadataInfoDlg.loadDictInputtype = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value=''>无</option>");
	   $("#"+key).append("<option value='checkbox'>checkbox</option>"); 
	   $("#"+key).append("<option value='checkbox1'>checkbox1</option>"); 
	   $("#"+key).append("<option value='editor'>editor</option>"); 
	   $("#"+key).append("<option value='input'>input</option>"); 
	   $("#"+key).append("<option value='radio'>radio</option>"); 
	   $("#"+key).append("<option value='select'>select</option>"); 
	   $("#"+key).append("<option value='switch'>switch</option>"); 
	   $("#"+key).append("<option value='textarea'>textarea</option>"); 
       $("#"+key).append("<option value='tree'>tree</option>"); 
       $("#"+key).append("<option value='rate'>rate</option>"); 
       $("#"+key).append("<option value='evaluate'>rrrrr</option>"); 
       
	   if(defultvalue!=""){
			 $("#"+key).val(defultvalue);
		   }
	  
	}
	*/
MetadataInfoDlg.loadFathernode = function (key,tablename) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
	    debugger;
		var ajax = new $ax(Feng.ctxPath + "/metadata/getfathernodeBytablename/"+tablename, function(data){
			debugger;
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}

MetadataInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '数据库表名', field: 'tablename', visible: false, align: 'center', valign: 'middle',editable: {
                type: 'text',
                title: '数据库表名',
                mode: "popup",  
                emptytext: "空"
            }},
            {title: '数据库表中文含义', field: 'tablenamecn', visible: false, align: 'center', valign: 'middle',editable: {
            	 type: 'text',
                 title: '数据库表中文含义',
                 mode: "popup",  
                 emptytext: "空"
            }},
            {title: '注释', field: 'title', visible: true, align: 'center', valign: 'middle',
            	editable: {
                  	 type: 'text',
                 	 disabled: false,  
                   showbuttons: false,
                    title: '数据库字段注释',
                    mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                    emptytext: "请录入"  
               }
            },
        
        
        {title: '输入类型', field: 'inputtype', visible: true, align: 'center', valign: 'middle',
        	editable: {
              	 type: 'select',
              	 disabled: false,  
                showbuttons: false,
                title: '输入类型',
                mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                emptytext: "请录入",  
                source:[{value:"checkbox",text:"checkbox"},{value:"checkbox1",text:"checkbox1"},
                	{value:"editor",text:"editor"},{value:"input",text:"input"},
                	{value:"radio",text:"radio"},{value:"select",text:"select"},
                	{value:"selects",text:"selects"},{value:"switch",text:"switch"},
                	{value:"textarea",text:"textarea"},{value:"tree",text:"tree"},{value:"rate",text:"rate"},
                	{value:"images",text:"images"},{value:"multipleImages",text:"multipleImages"}
                	,{value:"file",text:"file"},{value:"tags",text:"tags"},{value:"date",text:"date"}
                	,{value:"geojson",text:"geojson"}
                	]  
           }},
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
        	    {title: '查询', field: 'isselect', visible: true, align: 'center', valign: 'middle',
            	  editable: {
                      type: 'select',
                      title: '是否可查询',
                      disabled: false,  
      	           	 showbuttons: false,
      	           	 mode: "inline",  
                      emptytext: "请选择",  
                      source:[{value:"0",text:"否"},{value:"1",text:"是"}]
            	  }},
            	  
                  {title: '列表排序', field: 'issort', visible: true, align: 'center', valign: 'middle',
              	  editable: {
                        type: 'select',
                        title: '开启列表排序',
                        disabled: false,  
        	           	 showbuttons: false,
        	           	 mode: "inline",  
                        emptytext: "请选择",  
                        source:[{value:"0",text:"否"},{value:"1",text:"是"}]
              	  }},
              	  {title: '    字         典                  ', field: 'dict', visible: true, align: 'center', valign: 'middle',
                    	editable: {
                          	 type: 'text',
                          	 disabled: false,  
                            showbuttons: false,
                            title: '字段长度',
                            mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                            emptytext: "请录入"  
                       }},
                       {title: '校验规则', field: 'verifyrole', visible: true, align: 'center', valign: 'middle',
                         	editable: {
                            	 type: 'text',
                            	 disabled: false,  
                              showbuttons: false,
                              title: '校验规则',
                              mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                              emptytext: "请录入"  
                         }},
                   	  {title: '数据库字段名', field: 'field', visible: true, align: 'center', valign: 'middle',editable: {
                      	 type: 'text',
                          title: '数据库字段名',
                          disabled: false,  
                          showbuttons: false,
                          mode: "inline",  
                          emptytext: "请录入"  
                     }},
                     {title: '数据库字段类型', field: 'fieldtype', visible: true, align: 'center', valign: 'middle',editable: {
                         type: 'select',
                     	 title: '数据库字段类型',
         	             disabled: false,  
         	           	 showbuttons: false,
         	           	 mode: "inline",  
                         emptytext: "请选择",  
                         source:[{value:"int",text:"int"},{value:"bigint",text:"bigint"},
                         	{value:"numeric",text:"numeric"},{value:"double",text:"double"},{value:"decimal",text:"decimal"},
                         	{value:"date",text:"date"},{value:"time",text:"time"},{value:"datetime",text:"datetime"},
                         	{value:"char",text:"char"},{value:"varchar",text:"varchar"},
                         	{value:"text",text:"text"},{value:"longtext",text:"longtext"}],
                     }},
                     {title: '长度', field: 'fieldlength', visible: true, align: 'center', valign: 'middle',editable: {
                       	 type: 'text',
                       	 disabled: false,  
                         showbuttons: false,
                         title: '字段长度',
                         mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                         emptytext: "请录入",
                         validate: function(v){
                        	 return isNumber(v)
                         }
                    }},
                        {title: '小数点', field: 'decimalpoint', visible: true, align: 'center', valign: 'middle',
                    	editable: {
                       	 type: 'text',
                       	 disabled: false,  
                         showbuttons: false,
                         title: '小数点',
                         mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                         emptytext: "空",
                         validate: function(v){
                        	 return isNumber(v)
                         }
                    }},
                    {title: '字段默认值', field: 'defaultvalue', visible: true, align: 'center', valign: 'middle',
                    	editable: {
                          	 type: 'text',
                          	 disabled: false,  
                            showbuttons: false,
                            title: '字段默认值',
                            mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                            emptytext: "请录入"  
                       }},
            {title: '可空', field: 'isnullable', visible: true, align: 'center', valign: 'middle',
            	  editable: {
                      type: 'select',
                      title: '是否可以为空',
                     disabled: false,  
   	            	 showbuttons: false,
     	           	 mode: "inline",  
                     emptytext: "请选择",  
                      source:[{value:"1",text:"否"},{value:"0",text:"是"}]
                  },},
        
            {title: '级联删除', field: 'isdelcascade', visible: true, align: 'center', valign: 'middle',
            	  editable: {
                      type: 'select',
                      title: '是否级联删除',
                     disabled: false,  
   	            	 showbuttons: false,
     	           	 mode: "inline",  
                     emptytext: "请选择",    
                     source:[{value:"0",text:"否"},{value:"1",text:"是"}]
                  }},
            {title: '   级联删除的父节点       ', field: 'fathernode', visible: true, align: 'center', valign: 'middle',
            		  editable: {
                          type: 'select',
                          title: '级联删除的父节点',
                          disabled: false,  
          	           	 showbuttons: false,
          	           	 mode: "inline",  
                          emptytext: "请选择",  
                          source: function() {//动态获取数据
                              var result = [];
                          	var ajax = new $ax(Feng.ctxPath + "/metadata/getfathernodeBytablename/"+" ", function(data){
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
            {title: '主键', field: 'ismajorkey', visible: true, align: 'center', valign: 'middle',
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
                	   {title: 'excel导入', field: 'imp', visible: true, align: 'center', valign: 'middle',
                      	editable: {
                            	 type: 'select',
                            	 disabled: false,  
                              showbuttons: false,
                              title: 'excel导入',
                              mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                              emptytext: "请选择",  
                              source:[{value:"0",text:"否"},{value:"1",text:"是"}]  
                         }},
                      {title: 'excel导出', field: 'exp', visible: true, align: 'center', valign: 'middle',
                      	editable: {
                            	 type: 'select',
                            	 disabled: false,  
                              showbuttons: false,
                              title: 'excel导出',
                              mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                              emptytext: "请选择",  
                              source:[{value:"0",text:"否"},{value:"1",text:"是"}]
                         }},
            
            {title: '有效标志', field: 'isvalid', visible: false, align: 'center', valign: 'middle',
            	  formatter:function(value,row,index){if(value==0){return '否'};if(value==1){return '是'};}},
            {title: '更新时间', field: 'updatetime', visible: false, align: 'center', valign: 'middle'},
            {title: '更新人', field: 'operator', visible: false, align: 'center', valign: 'middle'},
            {title: '操作号', field: 'operatnum', visible: false, align: 'center', valign: 'middle'},
            {title: '操作序号', field: 'operatnumbefore', visible: false, align: 'center', valign: 'middle'},
           
          
         
            {title: '单击方法', field: 'onclick', visible: true, align: 'center', valign: 'middle',
            	editable: {
                  	 type: 'text',
                  	 disabled: false,  
                    showbuttons: false,
                    title: '鼠标单击执行方法',
                    mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                    emptytext: "请录入"  
               }},
            {title: '双击方法', field: 'ondblclick', visible: true, align: 'center', valign: 'middle',
            	editable: {
                  	 type: 'text',
                  	 disabled: false,  
                    showbuttons: false,
                    title: '鼠标双击执行方法',
                    mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                    emptytext: "请录入"  
               }},
            {title: '获得焦点', field: 'onfocus', visible: true, align: 'center', valign: 'middle',
            	editable: {
                  	 type: 'text',
                  	 disabled: false,  
                    showbuttons: false,
                    title: '当元素获得焦点时执行方法',
                    mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                    emptytext: "请录入"  
               }},

                  {title: '失去焦点', field: 'onblur', visible: true, align: 'center', valign: 'middle',
                  	editable: {
                        	 type: 'text',
                        	 disabled: false,  
                          showbuttons: false,
                          title: '元素失去焦点时执行方法',
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
                               {title: '查询类型', field: 'selecttype', visible: true, align: 'center', valign: 'middle',
                               	editable: {
                                     	 type: 'select',
                                     	 disabled: false,  
                                       showbuttons: false,
                                       title: '查询类型',
                                       mode: "inline",  //编辑框的模式：支持popup和inline两种模式，默认是popup
                                       emptytext: "请xuanze"  ,
                                       source:[{value:"eq",text:"eq"},{value:"uneq",text:"uneq"},{value:"gt",text:"gt"}
    ,{value:"lt",text:"lt"},{value:"egt",text:"egt"},{value:"elt",text:"elt"},{value:"gtandlt",text:"gtandlt"}
    ,{value:"egtandelt",text:"egtandelt"},{value:"unegtandelt",text:"unegtandelt"},{value:"ungtandlt",text:"ungtandlt"},{value:"like",text:"like"}]
                                  }},
            
            
    ];
};

/**
 * 检查可以提交
 */
MetadataInfoDlg.check = function() {
	debugger;
	var allTableData = $('#' + MetadataInfoDlg.id).bootstrapTable('getData');
	var isExists = false
	if (allTableData.length > 0) {
	} else {
		Feng.info("必须要有数据");
		return false;
	}
	
	  $.each(allTableData,function (n, value) {
		  if(value.ismajorkey=='1'){
			 isExists =true
		  }
	  });
	if(isExists){
		return true;
	}else{ 
		
		 Feng.info("必须存在主键");
		 return false;
	}	
	
	
	
};


/**
 * 点击添加元数据管理
 */
MetadataInfoDlg.AddMetadata = function () {
	debugger;
	var allTableData = $("#MetadataTable").bootstrapTable('getData');
	var allTableDataone = $("#MetadataTable").bootstrapTable('getData')[0];
	
	 if($("#tablename").val()==""||$("#tablenamecn").val()==""||$("#tablename").val() == null||$("#tablenamecn").val() == null){
		   Feng.info("请填写表头");
		  return false;
	 }
	 var indexall=$('#MetadataTable').bootstrapTable('getOptions').totalRows;
	 var rowdata;
	 if(indexall ==0){
		 rowdata= {tablename:$("#tablename").val(),
				    tablenamecn:$("#tablenamecn").val(),
					add:"1",
					decimalpoint:"0",
					dict:"",
					display:"1",
					exp:"1",
					field:"id",
					fieldlength:"12",
					fieldtype:"int",
					fieldsort:"0",
					imp:"1",
					inputtype:"input",
					isdelcascade:"0",
					ismajorkey:"1",
					isnullable:"1",
					isselect:"0",
					issort:"0",
					onblur:"",
					minwidth:"80",
					selecttype:"eq",
					onclick:"",
					ondblclick:"",
					onfocus:"",
					readonly:"0",
					selectItem:false,
					title:"ID",
					update:"1",
					id:"",
					verifyrole:"",
					clickevent:"",
					defaultvalue:"",
				};
	 }else{//其他栏
	  rowdata= {tablename:$("#tablename").val(),
		    tablenamecn:$("#tablenamecn").val(),
			add:"1",
			decimalpoint:"0",
			dict:"",
			display:"1",
			exp:"1",
			field:"",
			fieldlength:"",
			fieldsort:"0",
			imp:"1",
			inputtype:"input",
			isdelcascade:"0",
			ismajorkey:"0",
			isnullable:"0",
			isselect:"0",
			issort:"0",
			onblur:"",
			minwidth:"80",
			selecttype:"eq",
			onclick:"",
			ondblclick:"",
			onfocus:"",
			readonly:"0",
			selectItem:false,
			title:"",
			update:"1",
			id:"",
			verifyrole:"",
			clickevent:"",
			defaultvalue:"",
		};}

		
//	 if(indexall>0){
//		 $('#MetadataTable').bootstrapTable('getOptions').data.splice(indexall-1, 1,allTableDataone);
//	 }
	 
	
	 MetadataInfoDlg.insertrow['tablename']= $("#tablename").val();
	 MetadataInfoDlg.insertrow['tablenamecn']= $("#tablenamecn").val();
//	 MetadataInfoDlg.insertrow['index']= $('#MetadataTable').bootstrapTable('getOptions').totalRows;
//	 $('#MetadataTable').bootstrapTable('append',MetadataInfoDlg.insertrow)
	$('#MetadataTable').bootstrapTable('insertRow', {
     	index:indexall,
     	row:rowdata});
	 $('#MetadataTable').bootstrapTable('getOptions').data.splice(indexall,1,rowdata);
	 $('#MetadataTable').bootstrapTable('load', $("#MetadataTable").bootstrapTable('getData'));
};
MetadataInfoDlg.DelMetadata = function() {
	// selected[0].selectItem
	var selected = $('#' + MetadataInfoDlg.id).bootstrapTable('getSelections');
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
			url : '/metadata/list/init/' + selected[0].tablename,
			data : {},
			async : false,
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (selected[0].field == data[i].field) {
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
			$("#" + MetadataInfoDlg.id).bootstrapTable('remove', {
				field : 'selectItem',
				values : [ true ]
			});
			return true;
		}
	}

};
MetadataInfoDlg.DelMetadataEdit = function() {
	// selected[0].selectItem
	var selected = $('#' + MetadataInfoDlg.id).bootstrapTable('getSelections');

	if (selected[0].id != "") {
		Feng.info("此模块不允许删除已经存在的记录！");
		return false;
	}

	if (selected.length == 0) {
		Feng.info("请先选中表格中的某一记录！");
		return false;
	} else {
		/*
		 * if(selected.id!=""){ Feng.info("已存在字段不能在此功能删除！"); return false;
		 * }else{
		 */
		var swit = false;
		$.ajax({
			type : "get",
			url : '/metadata/list/init/' + selected[0].tablename,
			data : {},
			async : false,
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (selected[0].field == data[i].field) {
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
			$("#" + MetadataInfoDlg.id).bootstrapTable('remove', {
				field : 'selectItem',
				values : [ true ]
			});
			return true;
		}
	}

};
MetadataInfoDlg.GetAllMetadata = function () {
	var allTableData = $("#MetadataTable").bootstrapTable('getData');
	alert(allTableData);
};

$("#MetadataTable")
.on('click-row.bs.table', function (e, row, ele,field) {
	var  index= ele.data('index');
	if(row['ismajorkey']=='1'){
	  row['isnullable']='1';
	}
//	  Feng.info("请填写表头"+row['ismajorkey']);
//	  Feng.info("请填写表头2"+row['isnullable']);
//	 row['field2'] = row['field']; 
//	 row['fieldtype2'] = row['fieldtype']; 
	 $('#MetadataTable').bootstrapTable('getOptions').data.splice(index,1,row);
//	 $('#MetadataTable').bootstrapTable("load", $('#MetadataTable').bootstrapTable('getOptions').data)
	 
//    $("#eventInfo").text('点击行事件 当前商品名：'+ row.tablename );
})
$(function() {
	//MetadataInfoDlg.refresh();
	var defaultColunms = MetadataInfoDlg.initColumn();
	 if (MetadataInfoDlg.get('id') != "") {
		 var table = new BSTable(MetadataInfoDlg.id, "/metadata/list/init/"+MetadataInfoDlg.get('tablenamehid'), defaultColunms);
		 table.setPaginationType("client");
		 table.setDefultNumber('All');
		 table.setDefaultList(10,'All');
		 MetadataInfoDlg.table = table.init();

		  $("#tablename").val($("#tablenamehid").val());
		  $("#tablenamecn").val($("#tablenamecnhid").val());
		 
	 }else{
		   var table = new BSTable(MetadataInfoDlg.id, "/metadata/list/init/"+" ", defaultColunms);
		   table.setPaginationType("client");
		   //table.setFixedColumns("true","3");
		   table.setDefultNumber('All');
		   table.setDefaultList(10,'All');
		    MetadataInfoDlg.table = table.init();
	 }

	  
});
function isNumber(v){
        if (isNaN(v)){return '必须是数字';}
        if (parseInt(v) < 0) {return '必须为正整数';}
        if(Math.floor(v)!=v){return '必须为整数';}
}
