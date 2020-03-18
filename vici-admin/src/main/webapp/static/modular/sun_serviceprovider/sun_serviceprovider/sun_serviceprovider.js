/**
 * 服务商信息管理管理初始化
 */
var Sun_serviceprovider = {
    id: "Sun_serviceproviderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Sun_serviceprovider.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '服务商名称', field: 'serviceprovidername', visible: true, align: 'center', valign: 'middle'},
            {title: '商务模式', field: 'commercialmodel', visible: false, align: 'center', valign: 'middle'},
            {title: '商务模式名称', field: 'commercialmodelname', visible: true, align: 'center', valign: 'middle'},
            {title: '星级', field: 'starlevel', visible: false, align: 'center', valign: 'middle'},
            {title: '星级名称', field: 'starlevelname', visible: true, align: 'center', valign: 'middle'},
            {title: '组织类型', field: 'organizationtype', visible: true, align: 'center', valign: 'middle'},
            {title: '销售模式', field: 'salestype', visible: true, align: 'center', valign: 'middle'},
            {title: '洽谈进度', field: 'contactprogress', visible: true, align: 'center', valign: 'middle'},
           {title: '联系人', field: 'contactperson', visible: true, align: 'center', valign: 'middle'},
           {title: '职务', field: 'duty', visible: true, align: 'center', valign: 'middle'},
            {title: '联系电话', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
           {title: '固定电话', field: 'telephone', visible: true, align: 'center', valign: 'middle'},
          {title: '邮箱', field: 'email', visible: false, align: 'center', valign: 'middle'},
           {title: '联系地址', field: 'contactaddress', visible: false, align: 'center', valign: 'middle'},
            {title: '服务内容', field: 'servicecontent', visible: false, align: 'center', valign: 'middle'},
            {title: '管理年费', field: 'managementfee', visible: false, align: 'center', valign: 'middle'},
            {title: '展柜年费', field: 'showcasefee', visible: false, align: 'center', valign: 'middle'},
            {title: '线上分成', field: 'onlineshare', visible: false, align: 'center', valign: 'middle'},
            {title: '线下分成', field: 'offlineshare', visible: false, align: 'center', valign: 'middle'},
            {title: '协议日期', field: 'agreementdate', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
            	if(typeof value!= "undefined"){

          		   return"<span title ='"+value+"'>"+value.substring(0, 11)+"</span>";
            	}
            	return value;
  		  }},
            {title: '签约日期', field: 'contractdate', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
            	if(typeof value!= "undefined"){

           		   return"<span title ='"+value+"'>"+value.substring(0, 11)+"</span>";
             	}
             	return value;}}
    ];
};

/**
 * 检查是否选中
 */
Sun_serviceprovider.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Sun_serviceprovider.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加服务商信息管理
 */
Sun_serviceprovider.openAddSun_serviceprovider = function () {
    var index = layer.open({
        type: 2,
        title: '添加服务商信息管理',
        area: ['850px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sun_serviceprovider/sun_serviceprovider_add'
    });
    this.layerIndex = index;
  
	//　window.location.href= Feng.ctxPath + '/sun_serviceprovider/sun_serviceprovider_add';
};
/**
 * 查询产品界面
 */
Sun_serviceprovider.queryproduct = function () {
	  if (this.check()) {
	        var index = layer.open({
	            type: 2,
	            title: '服务商信息管理详情',
	            area: ['850px', '420px'], //宽高
	            fix: false, //不固定
	            maxmin: true,
	            content: Feng.ctxPath +  '/sun_product?id='+ Sun_serviceprovider.seItem.id
	        });
	        this.layerIndex = index;
	    };
//	if (this.check()) {
//      //window.location.href= Feng.ctxPath + '/sun_product?id='+ Sun_serviceprovider.seItem.id;
//		debugger;
//		menuItem(Feng.ctxPath + '/sun_product?id='+ Sun_serviceprovider.seItem.id,"16","产品管理");
//		
//      //window.open(Feng.ctxPath + '/sun_product?id='+ Sun_serviceprovider.seItem.id,window);
//	}
};

/**
 * 打开查看服务商信息管理详情
 */
Sun_serviceprovider.openSun_serviceproviderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '服务商信息管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sun_serviceprovider/sun_serviceprovider_update/' + Sun_serviceprovider.seItem.id
        });
        this.layerIndex = index;
    };

};

/**
 * 删除服务商信息管理
 */
Sun_serviceprovider.delete = function () {
    if (this.check()) {
    	  var operation = function(){
		        var ajax = new $ax(Feng.ctxPath + "/sun_serviceprovider/delete", function (data) {
		            Feng.success("删除成功!");
		            Sun_serviceprovider.table.refresh();
		        }, function (data) {
		            Feng.error("删除失败!" + data.responseJSON.message + "!");
		        });
		        ajax.set("sun_serviceproviderId",Sun_serviceprovider.seItem.id);
		        ajax.start();
		    }
    	  Feng.confirm("是否刪除该服务商?", operation);
          };

         
};

/**
 * 查询服务商信息管理列表
 */
Sun_serviceprovider.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Sun_serviceprovider.table.refresh({query: queryData});
};
/**
 * 重置方法
 */
Sun_serviceprovider.resetSearch = function () {
	debugger;
    $("#condition").val("");
    //Sun_serviceprovider.search();
}
$(function () {
	 init();
    var defaultColunms = Sun_serviceprovider.initColumn();
    var table = new BSTable(Sun_serviceprovider.id, "/sun_serviceprovider/list", defaultColunms);
    table.setPaginationType("client");
    //table。serpagination(true);
//    pagination: true,  
//    pageSize: 20,  
//    pageNumber:1,  
//    pageList: [10, 20, 50, 100, 200, 500],  sidePagination:'client',  
    Sun_serviceprovider.table = table.init();
});

function init() {

    var BootstrapTable = $.fn.bootstrapTable.Constructor;
    BootstrapTable.prototype.onSort = function (event) {
 
        var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(),
            $this_ = this.$header.find('th').eq($this.index()),
            sortName = this.header.sortNames[$this.index()];

        this.$header.add(this.$header_).find('span.order').remove();

        if (this.options.sortName === $this.data('field')) {
            this.options.sortOrder = this.options.sortOrder === 'asc' ? 'desc' : 'asc';
        } else {
            this.options.sortName = sortName || $this.data('field');
            this.options.sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
        }
        this.trigger('sort', this.options.sortName, this.options.sortOrder);

        $this.add($this_).data('order', this.options.sortOrder);

        // Assign the correct sortable arrow
        this.getCaret();

        if (this.options.sidePagination === 'server') {
            this.initServer(this.options.silentSort);
            return;
        }

        this.initSort();
        this.initBody();
    };
    BootstrapTable.prototype.getCaret = function () {
        var that = this;

        $.each(this.$header.find('th'), function (i, th) {
            var sortName = that.header.sortNames[i];
            $(th).find('.sortable').removeClass('desc asc').addClass((sortName || $(th).data('field')) === that.options.sortName ? that.options.sortOrder : 'both');
        });
    };
}


