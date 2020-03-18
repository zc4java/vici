/**
 * 初始化服务商信息管理详情对话框
 */
var Sun_serviceproviderInfoDlg = {
    sun_serviceproviderInfoData : {},
    deptZtree: null,
    pNameZtree: null,
    validateFields: {
    	serviceprovidername: {
            validators: {
                notEmpty: {
                    message: '服务商名称不能为空'
                }
    ,
                remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                    url: Feng.ctxPath + '/sun_serviceprovider/checkname',//验证地址
                    data:{serviceprovidername:function() {
                        return $('input[name="serviceprovidername"]').val() }
                    },
                    dataType:"json",
                    message: '服务商名已存在',//提示消息
                    delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                    type: 'POST'//请求方式
                }
            }
        },
        commercialmodel: {
            validators: {
                notEmpty: {
                    message: '商务模式不能为空'
                }
            }
        },
        starlevel: {
            validators: {
                notEmpty: {
                    message: '服务商星级不能为空'
                }
            }
        },
        duty: {
            validators: {
                notEmpty: {
                    message: '职务不能为空'
                }
            }
        },
        mobile: {
            validators: {
                notEmpty: {
                    message: '联系电话不能为空'
                }
            }
        },
        serviceCategoryService: {
            validators: {
                notEmpty: {
                    message: '服务类型不能为空'
                }
            }
        },
        telephone: {
            validators: {
                notEmpty: {
                    message: '固定电话不能为空'
                }
            }
        },
        email: {
            validators: {
                notEmpty: {
                    message: '邮箱不能为空'
                },
                emailAddress: {
                    message: '请输入正确的邮件地址如：123@qq.com'
                   }
            }
        },
        contactaddress: {
            validators: {
                notEmpty: {
                    message: '联系地址不能为空'
                }
            }
        },
        servicecontent: {
            validators: {
                notEmpty: {
                    message: '服务内容不能为空'
                }
            }
        },
        agreementdate: {
            validators: {
                notEmpty: {
                    message: '协议日期不能为空'
                }
            }
        },
        contractdate: {
            validators: {
                notEmpty: {
                    message: '签约日期不能为空'
                }
            }
        },
        contactperson: {
            validators: {
                notEmpty: {
                    message: '联系人不能为空'
                }
            }
        }
    },
    validateFieldsedit: {
    	serviceprovidername: {
            validators: {
                notEmpty: {
                    message: '服务商名称不能为空'
                }
    ,
                remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                    url: Feng.ctxPath + '/sun_serviceprovider/checknamebyid/'+$("#id").val(),//验证地址
                    data:{serviceprovidername:function() {
                        return $('input[name="serviceprovidername"]').val() }
                    },
                    dataType:"json",
                    message: '服务商名已存在',//提示消息
                    delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                    type: 'POST'//请求方式
                }
            }
        },
        commercialmodel: {
            validators: {
                notEmpty: {
                    message: '商务模式不能为空'
                }
            }
        },
        starlevel: {
            validators: {
                notEmpty: {
                    message: '服务商星级不能为空'
                }
            }
        },
        duty: {
            validators: {
                notEmpty: {
                    message: '职务不能为空'
                }
            }
        },
        mobile: {
            validators: {
                notEmpty: {
                    message: '联系电话不能为空'
                }
            }
        },
        serviceCategoryService: {
            validators: {
                notEmpty: {
                    message: '服务类型不能为空'
                }
            }
        },
        telephone: {
            validators: {
                notEmpty: {
                    message: '固定电话不能为空'
                }
            }
        },
        email: {
            validators: {
                notEmpty: {
                    message: '邮箱不能为空'
                },
                emailAddress: {
                    message: '请输入正确的邮件地址如：123@qq.com'
                   }
            }
        },
        contactaddress: {
            validators: {
                notEmpty: {
                    message: '联系地址不能为空'
                }
            }
        },
        servicecontent: {
            validators: {
                notEmpty: {
                    message: '服务内容不能为空'
                }
            }
        },
        agreementdate: {
            validators: {
                notEmpty: {
                    message: '协议日期不能为空'
                }
            }
        },
        contractdate: {
            validators: {
                notEmpty: {
                    message: '签约日期不能为空'
                }
            }
        },
        contactperson: {
            validators: {
                notEmpty: {
                    message: '联系人不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
Sun_serviceproviderInfoDlg.clearData = function() {
    this.sun_serviceproviderInfoData = {};
}



/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Sun_serviceproviderInfoDlg.set = function(key, val) {
    this.sun_serviceproviderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
Sun_serviceproviderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
Sun_serviceproviderInfoDlg.close = function() {
    parent.layer.close(window.parent.Sun_serviceprovider.layerIndex);
    //alert("1111");
    //window.history.back(-1);
}

/**
 * 收集数据
 */
Sun_serviceproviderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('serviceprovidername')
    .set('commercialmodel')
    .set('starlevel')
    .set('organizationtype')
    .set('salestype')
    .set('contactprogress')
    .set('contactperson')
    .set('duty')
    .set('mobile')
    .set('telephone')
    .set('email')
    .set('contactaddress')
    .set('servicecontent')
    .set('managementfee')
    .set('showcasefee')
    .set('onlineshare')
    .set('offlineshare')
    .set('agreementdate')
    .set('contractdate')
    ;
}
/**
 * 验证数据是否为空
 */
Sun_serviceproviderInfoDlg.validate = function () {
    $('#serviceproviderForm').data("bootstrapValidator").resetForm();
    $('#serviceproviderForm').bootstrapValidator('validate');
    return $("#serviceproviderForm").data('bootstrapValidator').isValid();  

};



/**
 * 提交添加
 */
Sun_serviceproviderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

   this.validate();

    	
    var idsCecategory = Feng.zTreeCheckedNodes("ztreeCecategory");

    var idsDept = Feng.zTreeCheckedNodes("ztreeDept");
    
    //提交信息
    setTimeout(function(){
    	//延迟判断返回值，应对异步验证
     if (!$("#serviceproviderForm").data('bootstrapValidator').isValid()) {
    	 Feng.error("校验失败，请检查!");
     return;
     }
    var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/add", function(data){
        Feng.success("添加成功!");
        window.parent.Sun_serviceprovider.table.refresh();
        Sun_serviceproviderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(Sun_serviceproviderInfoDlg.sun_serviceproviderInfoData);
    ajax.set("ids", idsCecategory);
    ajax.set("deptids", idsDept);
    ajax.start();
    },3000);
    //window.history.back(-1);
}

/**
 * 提交修改
 */
Sun_serviceproviderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    this.validate();
    var ids ="";
    //Cecategory+","+idsProduct+","+idsOther
    var idsCecategory = Feng.zTreeCheckedNodes("ztreeCecategoryByid");
    var idsProduct = Feng.zTreeCheckedNodes("ztreeProductByid");
    var idsOther = Feng.zTreeCheckedNodes("ztreeOtherByid");
    if(idsCecategory!=""){
    	ids=idsCecategory;
    	if(idsProduct!=""){
        	ids=ids+","+idsProduct;
        }
    	if(idsProduct!=""){
        	ids=ids+","+idsOther;
        }
    }else{
    	if(idsProduct!=""){
        	ids=idsProduct;
        	if(idsProduct!=""){
            	ids=ids+","+idsOther;
            }
        }else{
        	ids=idsOther;
        }
    }
    
    
    var idsDept = Feng.zTreeCheckedNodes("ztreeDeptByid");
    //提交信息
    setTimeout(function(){
    	//延迟判断返回值，应对异步验证
     if (!$("#serviceproviderForm").data('bootstrapValidator').isValid()) {
    	 Feng.error("校验失败，请检查!");
     return;
     }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/update", function(data){
        Feng.success("修改成功!");
        window.parent.Sun_serviceprovider.table.refresh();
        Sun_serviceproviderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(Sun_serviceproviderInfoDlg.sun_serviceproviderInfoData);
    ajax.set("ids", ids);
    ajax.set("deptids", idsDept);
    ajax.start();
    },3000);
}


Sun_serviceproviderInfoDlg.onDblClickDept = function (e, treeId, treeNode) {
    $("#deptName").attr("value", Sun_serviceproviderInfoDlg.deptZtree.getSelectedVal());
    //$("#deptid").attr("value", treeNode.id);
    $("#deptid").attr("value", "24");
    $("#deptContent").fadeOut("fast");
}
/**
 * 显示服务类型选择的树
 *
 * @returns
 */

Sun_serviceproviderInfoDlg.showCategorySelectTree = function () {
     Feng.showInputTreeNohide("serviceCategoryService", "serviceCategoryServiceContent","ztreeCecategory");

}
/**
 * 显示服务类型选择的树BYID
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showCategorySelectTreebyid = function () {
	 
	Feng.showInputTreeNohide("serviceCategoryService", "serviceCategoryServiceContent","ztreeCecategoryByid");
		//Feng.showInputTree("deptName", "deptContent");
	}
/**
 * 显示产品类型选择的树
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showProductSelectTree = function () {
	Feng.showInputTreeNohide("serviceProductService", "serviceProductServiceContent","ztreeProduct");
		//Feng.showInputTree("deptName", "deptContent");
	}
/**
 * 显示产品类型选择的树byid
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showProductSelectTreebyid = function () {
		 
    Feng.showInputTreeNohide("serviceProductService", "serviceProductServiceContent","ztreeProductByid");
			//Feng.showInputTree("deptName", "deptContent");
		}
/**
 * 显示其他类型选择的树
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showOtherSelectTree = function () {
    Feng.showInputTreeNohide("serviceOtherService", "serviceOtherServiceContent","ztreeOther");
			//Feng.showInputTree("deptName", "deptContent");
		}
/**
 * 显示其他类型选择的树byid
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showOtherSelectTreebyid = function () {
			 
	Feng.showInputTreeNohide("serviceOtherService", "serviceOtherServiceContent","ztreeOtherByid");
				//Feng.showInputTree("deptName", "deptContent");
	}
/**
 * 显示区域选择的树
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showDeptSelectTree = function () {
		Feng.showInputTreeNohide("serviceDeptService", "serviceDeptServiceContent","ztreeDept");
				//Feng.showInputTree("deptName", "deptContent");
	}
/**
 * 显示区域选择的树byid
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.showDeptSelectTreebyid = function () {
				 
	  Feng.showInputTreeNohide("serviceDeptService", "serviceDeptServiceContent","ztreeDeptByid");
					//Feng.showInputTree("deptName", "deptContent");
	}

/**
 * 动态加载二级代码
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.loadSelectbyid = function (key,name) {
			 //Feng.showInputTree("serviceDeptService", "serviceDeptServiceContent");
			//Feng.showInputTree("deptName", "deptContent");
		//$("#commercialmodel").empty();
		//$("#commercialmodel").append("<option value='"+0+"'>"+"请选择"+"</option>"); 
		//$("#"+key).empty();
		var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/dictbyname/"+name, function(data){
		$.each(data,function(idx,item){     
		   //输出
		   //alert(item.num+"哈哈"+item.name);
		$("#"+key).append("<option value='"+item.num+"'>"+item.name+"</option>"); 
		})
		},function(data){
		Feng.error("获取二级代码"+key+"失败!" + data.responseJSON.message + "!");
		});
		ajax.start();
	  
	}
/**
 * 动态树的信息
 *
 * @returns
 */
Sun_serviceproviderInfoDlg.loadSelecttree = function (inputId,ztreeid) {
				 
	  var idsName =Feng.zTreeCheckedNodesName(ztreeid);
	     $("#"+inputId).attr("value", "");
	     $("#"+inputId).attr("value", idsName);
	 
	}


$(function() {

	
	Sun_serviceproviderInfoDlg.loadSelectbyid("commercialmodel","商务模式");
	Sun_serviceproviderInfoDlg.loadSelectbyid("starlevel","服务商星级");

	  if (Sun_serviceproviderInfoDlg.get('id') != "") {
          Feng.initValidator("serviceproviderForm", Sun_serviceproviderInfoDlg.validateFieldsedit);
          $("#starlevel").val( Sun_serviceproviderInfoDlg.get('starlevelhid'));
          $("#commercialmodel").val( Sun_serviceproviderInfoDlg.get('commercialmodelhid'));
	  }else{
          Feng.initValidator("serviceproviderForm", Sun_serviceproviderInfoDlg.validateFields);
      }
	
	
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
	       var ztreeCecategory = new $ZTree("ztreeCecategory", "/sun_servicecategory/servicecategorytreeList");
	       ztreeCecategory.setSettings(setting);
	       ztreeCecategory.init();
	       

	       
	       var ztreeDept = new $ZTree("ztreeDept", "/sun_servicecategory/servicedepttreeList");
	       ztreeDept.setSettings(setting);
	       ztreeDept.init();
	        
	        if (Sun_serviceproviderInfoDlg.get('id') != "") {
	        var ztreeCecategoryByid = new $ZTree("ztreeCecategoryByid", "/sun_servicecategory/servicecategoryTreeListByProvideridId/"+Sun_serviceproviderInfoDlg.get('id'));
	        ztreeCecategoryByid.setSettings(setting);
	        ztreeCecategoryByid.init();
	        Sun_serviceproviderInfoDlg.loadSelecttree("serviceCategoryService","ztreeCecategoryByid");

	        
	        var ztreeDeptByid = new $ZTree("ztreeDeptByid", "/sun_servicecategory/servicedeptTreeListByProvideridId/"+Sun_serviceproviderInfoDlg.get('id'));
	        ztreeDeptByid.setSettings(setting);
	        ztreeDeptByid.init();
	        Sun_serviceproviderInfoDlg.loadSelecttree("serviceDeptService","ztreeDeptByid");
	        }
	      
//	 var deptTree = new $ZTree("deptTree", "/sun_servicecategory/servicecategorytreeList");
//	    deptTree.bindOnClick(Sun_serviceproviderInfoDlg.onClickDept);
//	    deptTree.bindOnDblClick(Sun_serviceproviderInfoDlg.onDblClickDept)
//	    deptTree.init();
	   // Sun_serviceproviderInfoDlg.deptZtree = ztree;
});
