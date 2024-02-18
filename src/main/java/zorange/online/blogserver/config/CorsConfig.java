package zorange.online.blogserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
    //当前跨域最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        //初始化cors配置对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //配置跨域规则
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许携带cookie
        corsConfiguration.addAllowedOriginPattern("*");
        //允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        //允许所有请求方法
        corsConfiguration.addAllowedMethod("*");
        //配置跨域请求的域名
        corsConfiguration.setMaxAge(MAX_AGE);
        //添加映射路径，拦截一切请求
        source.registerCorsConfiguration("/**", corsConfiguration);
        //返回corsFilter实例，参数：cors配置源对象
        return new CorsFilter(source);
    }
}
