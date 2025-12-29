package kr.go.config;

import kr.go.config.db.DBConfig;
import kr.go.config.db.ExtDBConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
        basePackages = {"kr.go.hico.*.*.service", "kr.go.exception", "kr.go.common"}
)
@Import({
        DBConfig.class
        , ExtDBConfig.class
        , PropertyConfig.class
        , SecurityConfig.class
        , QuartzConfig.class
})
public class ContextConfig {

}
