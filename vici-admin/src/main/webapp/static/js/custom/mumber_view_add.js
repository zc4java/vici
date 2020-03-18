/**
 * 初始化产品管理详情对话框
 */
var Sun_productInfoDlg = {
    sun_productInfoData : {},
    editor: null
};

/**
 * 清除数据
 */
Sun_productInfoDlg.clearData = function() {
    this.sun_productInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Sun_productInfoDlg.set = function(key, val) {
    this.sun_productInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Sun_productInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
Sun_productInfoDlg.close = function() {
    parent.layer.close(window.parent.Sun_product.layerIndex);
}

/**
 * 收集数据
 */
Sun_productInfoDlg.collectData = function() {
    this.sun_productInfoData['remark'] =htmlspecialchars_decode(Sun_productInfoDlg.editor.txt.html());
    this
    .set('id')
    .set('productname')
    .set('servicecategory')
    .set('productcategory')
    .set('producttype')
    .set('productunit')
    .set('marketprice')
    .set('costprice')
    .set('saleprice')
    .set('providerid')
    ;
}

/**
 * 提交添加
 */
Sun_productInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sun_product/add", function(data){
        Feng.success("添加成功!");
        window.parent.Sun_product.table.refresh();
        Sun_productInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sun_productInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
Sun_productInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sun_product/update", function(data){
        Feng.success("修改成功!");
        window.parent.Sun_product.table.refresh();
        Sun_productInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sun_productInfoData);
    ajax.start();
}

/**
 * 替换转义字符
 */
function htmlspecialchars_decode(str){           

    str = str.replace(/&amp;/g, '&'); 
    str = str.replace(/& lt;/g, '<');
    str = str.replace(/& gt;/g, '>');
    str = str.replace(/&quot;/g, "''");  
    str = str.replace(/&#039;/g, "'");  
    return str;  
}

//由于data类型存在“等特殊字符，val方法无法识别，所以再去后台获取一次。
function getbase64(id){      
	 //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
    editor.create();
	debugger;
	 //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sun_product/detail/"+id, function(data){
    	debugger;
    	editor.txt.html(data.remark);
        Sun_productInfoDlg.editor = editor;
    },function(data){
    	debugger;
    	return "";
    });
    ajax.start();
    
}

/**
 * 动态服务商信息
 *
 * @returns
 */
Sun_productInfoDlg.loadProductbyid = function () {
		var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/providernameid", function(data){
		$.each(data,function(idx,item){     
		   //输出
		   //alert(item.num+"哈哈"+item.name);
		$("#providerid").append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
/**
 * 按照服务分类级别进行加载
 *
 * @returns
 */
Sun_productInfoDlg.loadSelectbylevel = function (key,level) {
	 $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/sun_product/categorybylevel/"+level, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
/**
 * 按照pid进行加载
 *
 * @returns
 */
Sun_productInfoDlg.loadSelectBypids = function (key,pids) {
	   $("#"+key).empty();
	    $("#"+key).append("<option value=''>请选择</option>");
		var ajax = new $ax(Feng.ctxPath + "/sun_product/categorybypids/"+pids, function(data){
		$.each(data,function(idx,item){     
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
Sun_productInfoDlg.getSelectOption =function(key){
	 debugger;
		var op ="";
	$("#"+key+" option").each(function() {   
	//var arr=$(key).each(function() {  
		debugger;
	
		if (op==""){
			op = $(this).val();  
		}else{
			op = op+","+$(this).val();  
		}
		
	});  
	return op;
}

Sun_productInfoDlg.changeservicecategory=function(obj){
	   debugger;
	   Sun_productInfoDlg.loadSelectBypids("productcategory",obj.value);
	   Sun_productInfoDlg.loadSelectBypids("producttype",Sun_productInfoDlg.getSelectOption("productcategory"));
	}
Sun_productInfoDlg.changeproductcategory=function(obj){
	   Sun_productInfoDlg.loadSelectBypids("producttype",obj.value);
}

layui.use('form', function(){
	  var form = layui.form;
	  form.on('select(servicecategory)', function (data) {
		  debugger;
		  Sun_productInfoDlg.loadSelectBypids("productcategory",data.value);
		  Sun_productInfoDlg.loadSelectBypids("producttype",Sun_product.getSelectOption("productcategory"));
		   form.render('select');
		});
	  form.on('select(productcategory)', function (data) {
		  Sun_productInfoDlg.loadSelectBypids("producttype",data.value);
		  form.render('select');
		});

	  //各种基于事件的操作，下面会有进一步介绍
	});

function init() {
	debugger;
  
	
 
}
function collectdate(data) {
	debugger;
	//alert(data.field("productname"));
}
$(function() {

	
//	Sun_productInfoDlg.loadSelectbylevel("servicecategory","1");
//	Sun_productInfoDlg.loadSelectbylevel("productcategory","2");
//	Sun_productInfoDlg.loadSelectbylevel("producttype","3");
//	
//	Sun_productInfoDlg.loadProductbyid();
//	 if (Sun_productInfoDlg.get('id') != "") {
//         getbase64($("#id").val());
//         $("#servicecategory").val( Sun_productInfoDlg.get('servicecategoryhid'));
//         if(Sun_productInfoDlg.get('servicecategoryhid')!=""){
//         Sun_productInfoDlg.loadSelectBypids("productcategory",Sun_productInfoDlg.get('servicecategoryhid'));
//         }
//         $("#productcategory").val( Sun_productInfoDlg.get('productcategoryhid'));
//         if(Sun_productInfoDlg.get('productcategoryhid')!=""){
//             Sun_productInfoDlg.loadSelectBypids("producttype",Sun_productInfoDlg.get('productcategoryhid'));
//             }
//         $("#producttype").val( Sun_productInfoDlg.get('producttypehid'));
//     }
//	 else
//	 {
//	    var E = window.wangEditor;
//	    var editor = new E('#editor');
//	    editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
//	    editor.create();
//		 Sun_productInfoDlg.editor = editor;
//	 };
	
});
