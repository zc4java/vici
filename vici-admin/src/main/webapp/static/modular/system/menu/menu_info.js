/**
 * 菜单详情对话框
 */
var MenuInfoDlg = {
    menuInfoData: {},
    ztreeInstance: null,
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '菜单名称不能为空'
                }
            }
        },
        code: {
            validators: {
                notEmpty: {
                    message: '菜单编号不能为空'
                }
            }
        },
        pcodeName: {
            validators: {
                notEmpty: {
                    message: '父菜单不能为空'
                }
            }
        },
        url: {
            validators: {
                notEmpty: {
                    message: '请求地址不能为空'
                }
            }
        },
        num: {
            validators: {
                notEmpty: {
                    message: '序号不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
MenuInfoDlg.clearData = function () {
    this.menuInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MenuInfoDlg.set = function (key, val) {
    this.menuInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MenuInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MenuInfoDlg.close = function () {
    parent.layer.close(window.parent.Menu.layerIndex);
}

/**
 * 收集数据
 */
MenuInfoDlg.collectData = function () {
    this.set('id').set('name').set('code').set('pcode').set('url').set('componentname').set('objectname').set('num').set('levels').set('icon').set("ismenu").set("isadmin").set("isshortcut").set("shortcutname");
}

/**
 * 验证数据是否为空
 */
MenuInfoDlg.validate = function () {
    $('#menuInfoForm').data("bootstrapValidator").resetForm();
    $('#menuInfoForm').bootstrapValidator('validate');
    return $("#menuInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加用户
 */
MenuInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/menu/add", function (data) {
    	debugger;
        Feng.success("添加成功!");
        window.parent.Menu.table.refresh();
        MenuInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    this.menuInfoData.objectname=this.menuInfoData.objectname.replace(/'/g, '"');
    ajax.set(this.menuInfoData);
    ajax.start();
}
/**
 * 提交添加所有用户
 */
MenuInfoDlg.addallSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/menu/addall", function (data) {
        Feng.success("添加成功!");
        window.parent.Menu.table.refresh();
        MenuInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.menuInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MenuInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/menu/edit", function (data) {
    	debugger;
        Feng.success("修改成功!");
        window.parent.Menu.table.refresh();
        MenuInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    this.menuInfoData.objectname=this.menuInfoData.objectname.replace(/'/g, '"');
    ajax.set(this.menuInfoData);
    ajax.start();
}

/**
 * 点击父级编号input框时
 */
MenuInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#pcodeName").attr("value", MenuInfoDlg.ztreeInstance.getSelectedVal());
    $("#pcode").attr("value", treeNode.id);
};


/**
 * 显示父级菜单选择的树
 */
MenuInfoDlg.showMenuSelectTree = function () {
    Feng.showInputTree("pcodeName", "pcodeTreeDiv", 15, 34);
};

$(function () {
    Feng.initValidator("menuInfoForm", MenuInfoDlg.validateFields);

    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(MenuInfoDlg.onClickDept);
    ztree.init();
    MenuInfoDlg.ztreeInstance = ztree;

    //初始化是否是菜单
    if($("#ismenuValue").val() == undefined){
        $("#ismenu").val(1);
    }else{
        $("#ismenu").val($("#ismenuValue").val());
    }
    //初始化是否是后台菜单
    if($("#isadminValue").val() == undefined){
        $("#isadmin").val(0);
    }else{
        $("#isadmin").val($("#isadminValue").val());
    }
    //初始化是否是快捷方式
    if($("#isshortcutValue").val() == undefined){
    	$("#isshortcut").val(0);
    }else{
    	$("#isshortcut").val($("#isshortcutValue").val());
    }
});
