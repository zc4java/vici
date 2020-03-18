package generator.config;

import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 默认的代码生成的配置
 *
 * @author cobona
 * @date 2017-10-28-下午8:27
 */
public class ViciGeneratorConfig extends AbstractGeneratorConfig {

    @Override
    protected void globalConfig() {
        globalConfig.setOutputDir("E:\\stsworkspace\\vici-parent\\vici-admin\\src\\main\\java");//写自己项目的绝对路径,注意具体到java目录
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setOpen(false);
        globalConfig.setAuthor("jinchm1");
    }

    @Override
    protected void dataSourceConfig() {
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("kbn123456");
         dataSourceConfig.setUrl("jdbc:mysql://47.93.32.243:3306/pinggu?characterEncoding=utf8");
         
    }

    @Override
    protected void strategyConfig() {
        //strategy.setTablePrefix(new String[]{"_"});// 此处可以修改为您的表前缀
        strategyConfig.setInclude(new String[]{"sqltable"});//这里限制需要生成的表,不写则是生成所有表
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
    }

    @Override
    protected void packageConfig() {
        packageConfig.setParent(null);
        packageConfig.setEntity("com.cobona.vici.common.persistence.model");
        packageConfig.setMapper("com.cobona.vici.common.persistence.dao");
        packageConfig.setXml("com.cobona.vici.common.persistence.dao.mapping");
    }

    @Override
    protected void contextConfig() {
    	
        contextConfig.setBizChName("sqltable管理");
        contextConfig.setBizEnName("sqltable");
        contextConfig.setModuleName("sqltable");
        //contextConfig.setProjectPath("/Users/cobona/work/ideaSpace/vici/vici-admin");//写自己项目的绝对路径
        contextConfig.setProjectPath("E:\\stsworkspace\\vici-parent\\vici-admin");//写自己项目的绝对路径
        //contextConfig.setEntityName("metadata");
        sqlConfig.setParentMenuName("元数据和视图管理");//这里写已有菜单的名称,当做父节点

        /**
         * mybatis-plus 生成器开关
         */
        contextConfig.setEntitySwitch(true);
        contextConfig.setDaoSwitch(true);
        contextConfig.setServiceSwitch(true);

        /**
         * vici 生成器开关
         */
        contextConfig.setControllerSwitch(true);
        contextConfig.setIndexPageSwitch(true);
        contextConfig.setAddPageSwitch(true);
        contextConfig.setEditPageSwitch(true);
        contextConfig.setJsSwitch(true);
        contextConfig.setInfoJsSwitch(true);
        contextConfig.setSqlSwitch(true);
    }
}
