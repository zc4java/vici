package generator;

import generator.config.ViciGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 *
 * @author cobona
 * @Date 2017/5/21 12:38
 */
public class ViciCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        ViciGeneratorConfig viciGeneratorConfig = new ViciGeneratorConfig();
        viciGeneratorConfig.doMpGeneration();

        /**
         * vici的生成器:
         *      vici的代码生成器可以生成controller,html页面,页面对应的js
         */
        //viciGeneratorConfig.doViciGeneration();
    }

}