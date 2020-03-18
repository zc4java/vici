/**
 * 基于JQuery的UMD封装写法 - JQ插件
 * 整理：Violet_Ice紫冰 <violetice@aliyun.com>
 * Date：2018-06-24 18:54:58
 */
(function(root,factroy){
    typeof root.layui === 'object' && layui.define ? layui.define(['jquery'],function(mods){mods('umd2',factroy(layui.jquery))}) :
    factroy(root.$);
}(this,function($){
    $.fn.umd2 = function(text){
        this.html(text);
        return this;
    };

    $.umd2 = function(val){
        console.log(val);
    };

    return {mod:'umd2',v:'1.0.0'};
}));