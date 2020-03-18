/**
 * 初始化元数据管理详情对话框
 */
var MetadataInfoDlg = {
    metadataInfoData : {}
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
    .set('templet')
    .set('templetscript')
    .set('minwidth')
    .set('selecttype')
    .set('defaultvalue')
    ;
}

/**
 * 提交添加
 */
MetadataInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadata/add", function(data){
        Feng.success("添加成功!");
        //window.parent.Metadata.table.refresh();
        window.parent.Metadata.refresh();
      //  MetadataInfoDlg.close(); 
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.metadataInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MetadataInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/metadata/update", function(data){
        Feng.success("修改成功!");
        window.parent.Metadata.table.refresh();
        MetadataInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    debugger;
    ajax.set(this.metadataInfoData);
    ajax.start();
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
	   $("#"+key).append("<option value='bigint'>bigint</option>"); 
	   $("#"+key).append("<option value='numeric'>numeric</option>"); 
	   $("#"+key).append("<option value='double'>double</option>"); 
	   $("#"+key).append("<option value='decimal'>decimal</option>");
	   $("#"+key).append("<option value='date'>date</option>"); 
	   $("#"+key).append("<option value='time'>time</option>"); 
	   $("#"+key).append("<option value='datetime'>datetime</option>"); 
	   $("#"+key).append("<option value='char'>char</option>");
	   $("#"+key).append("<option value='varchar'>varchar</option>"); 
	   $("#"+key).append("<option value='text'>text</option>"); 
	   $("#"+key).append("<option value='longtext'>longtext</option>"); 
	   if(defultvalue!=""){
			 $("#"+key).val(defultvalue);
		   }
	  
	}
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
	   $("#"+key).append("<option value='selects'>selects</option>"); 
	   $("#"+key).append("<option value='switch'>switch</option>"); 
	   $("#"+key).append("<option value='textarea'>textarea</option>"); 
       $("#"+key).append("<option value='tree'>tree</option>");
       $("#"+key).append("<option value='rate'>rate</option>");
       $("#"+key).append("<option value='tags'>tags</option>"); 
       $("#"+key).append("<option value='images'>images</option>"); 
       $("#"+key).append("<option value='multipleImages'>multipleImages</option>"); 
       $("#"+key).append("<option value='file'>file</option>"); 
       $("#"+key).append("<option value='date'>date</option>"); 
       $("#"+key).append("<option value='geojson'>geojson</option>"); 
       
	   if(defultvalue!=""){
			 $("#"+key).val(defultvalue);
		   }
	  
	}

//加载selecttype二级代码
MetadataInfoDlg.loadDictSelecttype = function (key,defultvalue) {
	   $("#"+key).empty();
	   $("#"+key).append("<option value='eq'>eq</option>"); 
	   $("#"+key).append("<option value='uneq'>uneq</option>"); 
	   $("#"+key).append("<option value='gt'>gt</option>"); 
	   $("#"+key).append("<option value='lt'>lt</option>"); 
	   $("#"+key).append("<option value='egt'>egt</option>"); 
	   $("#"+key).append("<option value='elt'>elt</option>"); 
	   $("#"+key).append("<option value='gtandlt'>gtandlt</option>"); 
	   $("#"+key).append("<option value='egtandelt'>egtandelt</option>"); 
	   $("#"+key).append("<option value='unegtandelt'>unegtandelt</option>"); 
       $("#"+key).append("<option value='ungtandlt'>ungtandlt</option>");
       $("#"+key).append("<option value='like'>like</option>");
       
	   if(defultvalue!=""){
			 $("#"+key).val(defultvalue);
		   }
	  
	}
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
MetadataInfoDlg.refresh = function () {
	MetadataInfoDlg.loadDictFiledtype("fieldtype","");
	MetadataInfoDlg.loadDictInputtype("inputtype","");
	MetadataInfoDlg.loadDictSelecttype("selecttype","");
	MetadataInfoDlg.loadFathernode("fathernode"," ");
	//MetadataInfoDlg.loadDict("isdelcascade","");
	MetadataInfoDlg.loadDict("isnullable","0");
	MetadataInfoDlg.loadDict("isenum","0");
	MetadataInfoDlg.loadDict("ismajorkey","0");
	MetadataInfoDlg.loadDict("isnullable","0");
	MetadataInfoDlg.loadDict("display","0");
	MetadataInfoDlg.loadDict("update","0");
	MetadataInfoDlg.loadDict("add","0");
	MetadataInfoDlg.loadDict("isselect","0");
	MetadataInfoDlg.loadDict("issort","0");
	MetadataInfoDlg.loadDict("imp","0");
	MetadataInfoDlg.loadDict("exp","0");
	MetadataInfoDlg.loadDict("readonly","0");
	MetadataInfoDlg.loadDict("isdelcascade","0");
	//MetadataInfoDlg.loadDict("clickevent","");
	//MetadataInfoDlg.loadDict("templet","");
	//MetadataInfoDlg.loadDict("templetscript","");
	//MetadataInfoDlg.loadDict("minwidth","");

	
    $("#fieldsort").val("0");
	
	 if (MetadataInfoDlg.get('id') != "") {
		    MetadataInfoDlg.loadDictFiledtype("fieldtype", MetadataInfoDlg.get('fieldtypehid'));
			MetadataInfoDlg.loadDictInputtype("inputtype", MetadataInfoDlg.get('inputtypehid'));
			MetadataInfoDlg.loadDictSelecttype("selecttype", MetadataInfoDlg.get('selecttypehid'));
			MetadataInfoDlg.loadDict("isnullable",MetadataInfoDlg.get('isnullablehid'));
			MetadataInfoDlg.loadDict("isenum",MetadataInfoDlg.get('isenumhid'));
			MetadataInfoDlg.loadDict("ismajorkey",MetadataInfoDlg.get('ismajorkeyhid'));
			MetadataInfoDlg.loadDict("isnullable",MetadataInfoDlg.get('isnullablehid'));
			MetadataInfoDlg.loadDict("display",MetadataInfoDlg.get('displayhid'));
			MetadataInfoDlg.loadDict("update",MetadataInfoDlg.get('updatehid'));
			MetadataInfoDlg.loadDict("add",MetadataInfoDlg.get('addhid'));
			MetadataInfoDlg.loadDict("isselect",MetadataInfoDlg.get('isselecthid'));
			MetadataInfoDlg.loadDict("isselect",MetadataInfoDlg.get('issort'));
			MetadataInfoDlg.loadDict("imp",MetadataInfoDlg.get('imphid'));
			MetadataInfoDlg.loadDict("exp",MetadataInfoDlg.get('exphid'));
			MetadataInfoDlg.loadDict("readonly",MetadataInfoDlg.get('readonlyhid'));
			//MetadataInfoDlg.loadDict("clickevent",MetadataInfoDlg.get('clickevent'));
			//MetadataInfoDlg.loadDict("templet",MetadataInfoDlg.get('templet'));
			//MetadataInfoDlg.loadDict("templetscript",MetadataInfoDlg.get('templetscript'));
			//MetadataInfoDlg.loadDict("minwidth",MetadataInfoDlg.get('minwidth'));
			MetadataInfoDlg.loadFathernode("fathernode"," ");
		    $("#fathernode").val($("#fathernodehid").val());
	 }
};
$(function() {
	MetadataInfoDlg.refresh();
});
