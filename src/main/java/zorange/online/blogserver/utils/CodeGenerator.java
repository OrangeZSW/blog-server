package zorange.online.blogserver.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;


/**
 * 代码生成器
 * Created by macro on 2018/4/26.
 * Modified by zorange on 2020/4/26.
 * @Author zorange
 * @Date 2020/4/26 22:40
 * @Version 1.0
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }
    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8",
                        "root",
                        "421232")
                .globalConfig(builder -> {
                    builder.author("zorange")
                            .fileOverride()
                            .outputDir("C:\\Users\\orange\\Desktop\\毕业设计选题\\博客系统\\Blog-Server\\src\\main\\java")
                            .enableSwagger();
                })
                .packageConfig(builder -> {
                    builder.parent("zorange.online.blogserver") // 设置父包名
                            .moduleName(null)//模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\orange\\Desktop\\毕业设计选题\\博客系统\\Blog-Server\\src\\main\\resources\\mapper")); // 设置mapper.xml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();  // 为实体类添加lombok注解
                    builder.controllerBuilder().enableRestStyle(); // 为控制器添加@RestController注解
                    builder.mapperBuilder().enableMapperAnnotation().build(); // 为mapper添加@Mapper注解
                    builder.addInclude("dict")
                            .addTablePrefix("_t");
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 指定模板引擎，默认是VelocityTemplateEngine ，需要引入相关引擎依赖
                .execute();
    }
}
