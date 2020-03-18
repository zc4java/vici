/**
 * 
 */
//空的init方法阻止页面报错，在用户自定义后会被覆盖
function init(){
	
}
//123
function func_empty(){
}

//数据提交前对数据进行整理
function collectdate(data){
	
}
//空的doButtonClick方法阻止页面报错，在用户自定义后会被覆盖
function  doButtonClick(bt,data){
	//layer.msg('你点击了'+bt);
		return;
}
//空的toobarDoing方法阻止页面报错，在用户自定义后会被覆盖
function toobarDoing(obj){}
function afterEditSaveSuccess(data){}
function layuiTableListRefresh(){
	$(".layui-laypage-btn").click();
}
function CurentTime(longtime,notime)  
{   
    var now = new Date(); 
    if(longtime!==undefined){
    	now = new Date(longtime); 
    }
      
    var year = now.getFullYear();       //年  
    var month = now.getMonth() + 1;     //月  
    var day = now.getDate();            //日  
      
    var hh = now.getHours();            //时  
    var mm = now.getMinutes();          //分  
    var ss = now.getSeconds();           //秒  
      
    var clock = year + "-";  
      
    if(month < 10)  
        clock += "0";  
      
    clock += month + "-";  
      
    if(day < 10)  
        clock += "0";  
    if(notime){
    	 clock += day;  
   	     return(clock);    
    }      
    clock += day + " ";  
    
    if(hh < 10)  
        clock += "0";  
          
    clock += hh + ":";  
    if (mm < 10) clock += '0';   
    clock += mm + ":";   
       
    if (ss < 10) clock += '0';   
    clock += ss;   
    return(clock);   
}  

//文件上传删除
function  deleteUpload(obj){
	var demoid=$(obj).attr("demoid");
	var pid=$(obj).attr("pid");
	$("#"+pid).val(0);
	$("#"+demoid).parent().hide();
}

//多图片上传点击放大，带删除按钮删除
function showMultipleImages(obj){
	var value=$(obj).attr("value");
	var fieldid=$(obj).attr("fieldid");
	var readonly=$("#"+fieldid).attr("readonly");
	if(readonly==='readonly'){
		showImages(value);
		return;
	}
	var image="<div style='text-align: center;'><img src='/generaltable/filedownload/"+value+"'/></div>";
	layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 1,
		  //skin: 'layui-layer-nobg', //没有背景色
		  area:['80%','80%'],
		  shadeClose: true,
		  content: image,
		  btn: ['删除', '关闭'],
		  yes: function(index1, layero){
			  layer.msg('确认删除图片', {
				  time: 0, //不自动关闭
				  btn: ['确认','取消'], //按钮
				  yes:function(index){
						var val=$("#"+fieldid).val();
						val=val.replace(",["+value+"]","")
					    $("#"+fieldid).val(val);
						$("img[value="+value+"]").remove();
						layer.close(index);
						layer.close(index1);
					}
				});
		   }
		  ,btn2: function(index, layero){
			  layer.close(index);
		  }
		});
}

//查看原图
function showImages(id){
	var image="<div style='text-align: center;'><img src='/generaltable/filedownload/"+id+"'/></div>";
	layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 1,
		  //skin: 'layui-layer-nobg', //没有背景色
		  area:['80%','80%'],
		  shadeClose: true,
		  content: image,
		});
}

//<input type="button"  title="popTitle"  onclick="printDivToPdf('divid',this)">
//打印或导出pdf 如果title="popTitle"为空则取当前王网页的title
function printDivToPdf(id,object) {
	var  title=$(object).attr("title");
	$(object).css('display','none');
    $("#"+id+"").printArea({popTitle:title});
    $(object).css('display','block');
}
//html标签转义editor使用
function HTMLDecode(text) { 
	var temp = document.createElement("div"); 
	temp.innerHTML = text; 
	var output = temp.innerText || temp.textContent; 
	temp = null; 
	return output; 
} 
