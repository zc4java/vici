/**
 * 无前置类UMD封装写法 - 原生js
 * 整理：Violet_Ice紫冰 <violetice@aliyun.com>
 * Date：2018-06-24 17:19:01
 */
(function(root,factroy){
    // root = this = window
    // factroy = function(){}
    typeof root.layui === 'object' && layui.define ? layui.define(function(mods){mods('umd1',factroy(layui))}) :
    root.umd1 = factroy();
}( this,function(layui){
    'use strict';

    // 在这里可以加载css
    layui.link(layui.cache.base+'vlice/umd1/umd1.css');

    var test = '私有变量：无前置类UMD封装写法 - 原生js';
    var func = function(){
        return '私有函数：无前置类UMD封装写法 - 原生js';
    };

    var umd1 = {
        test:'公有变量：无前置类UMD封装写法 - 原生js',
        func:function(){
            console.log([test,func(),this.test]);
            return '公有函数：无前置类UMD封装写法 - 原生js';
        }
    };

    return umd1;
} ));