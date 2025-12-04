package kr.go.config.db;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@PropertySource("classpath:properties/db/${spring.profiles.active}/jdbc.properties")
@EnableTransactionManagement
@MapperScan(basePackages = "kr.go.mapper"
    //, sqlSessionFactoryRef = "innerDBSqlSessionFactory"
    , sqlSessionTemplateRef = "innerDBSqlSessionTemplate")
public class DBConfig {

  @Value("${inner.jdbc.driver.class}")
  private String driverClassName;
  @Value("${inner.jdbc.url}")
  private String url;
  @Value("${inner.jdbc.username}")
  private String username;
  @Value("${inner.jdbc.password}")
  private String password;


  @Bean(name = "innerDB")
  @Primary
  public DataSource innerDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  // 트랜잭션설정
  @Bean(name = "innerDBTransactionManager")
  @Primary
  public PlatformTransactionManager transactionManager(@Qualifier("innerDB")DataSource innerDataSource) {
    return new DataSourceTransactionManager(innerDataSource);
  }

  // SqlSession설정
  @Bean(name = "innerDBSqlSessionFactory")
  @Primary
  public SqlSessionFactory sqlSessionFactory(@Qualifier("innerDB")DataSource innerDataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

    factoryBean.setDataSource(innerDataSource);
    factoryBean.setTypeAliasesPackage("kr.go.vo");

    // 2. XML 매퍼 파일 위치 설정
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));

    SqlSessionFactory sessionFactory = factoryBean.getObject();
    sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    sessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

    return sessionFactory;
  }

  @Bean(name = "innerDBSqlSessionTemplate")
  @Primary
  public SqlSessionTemplate innerDBSqlSessionTemplate(@Qualifier("innerDBSqlSessionFactory") SqlSessionFactory innerDBSqlSessionFactory) {
    return new SqlSessionTemplate(innerDBSqlSessionFactory);
  }


}