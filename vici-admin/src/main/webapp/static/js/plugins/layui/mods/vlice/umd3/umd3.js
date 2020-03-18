/**
 * 基于layui的UMD封装写法 - 高级插件
 * 整理：Violet_Ice紫冰 <violetice@aliyun.com>
 * Date：2018-06-24 20:03:16
 */
(function(root,factroy){
    typeof root.layui === 'object' && layui.define ? layui.define(['layer'],function(mods){mods('umd3',factroy(layui.layer))}) :
    factroy(root.layer);
}(this,function(layer){
    // 引入css
    layui.link(layui.cache.base+'vlice/umd3/umd3.min.css');

    // 对layer进行扩展，方法和扩展JQ差不多
    // 最大化弹出
    layer.maxopen = function(option){
        var index = layer.open(Object.assign({
            success:function(el,index){
                layer.full(index);
            }
        },option));
        return index;
    };

    return {mod:'umd3',v:'1.0.0'};
}));