package com.example.basketservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered; // BU ÇOK ÖNEMLİ
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // WebMvcConfigurer implementasyonunu kullanmak yerine bu BEAN metodunu kullanın.
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        // CORS ayarlarınız
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // GET, POST, OPTIONS hepsi dahil

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // KRİTİK: Filtrenin sıralamasını en başa (en yüksek öncelik) ayarlayın.
        // Tüm custom filtrelerinizden (JWT filtreniz dahil) önce çalışmasını garanti eder.
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
}