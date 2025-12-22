package kr.go.config.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "kr.go.hico.*.*.mapper"
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
    //DriverManagerDataSource dataSource = new DriverManagerDataSource();
    DataSource dataSource = new DataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setInitSQL("SET search_path TO public");
    //dataSource.setInitSQL("SET search_path TO hico, public");
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
    factoryBean.setTypeAliasesPackage("kr.go.hico.*.*.vo");

    // 2. XML 매퍼 파일 위치 설정
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/**/*.xml"));

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