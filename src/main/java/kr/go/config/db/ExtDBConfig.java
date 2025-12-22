package kr.go.config.db;

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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "kr.go.hico.*.*.externalMapper"
    , sqlSessionTemplateRef = "externalDBSqlSessionTemplate")
public class ExtDBConfig {

  @Value("${external.jdbc.driver.class}")
  private String externalDriverClassName;
  @Value("${external.jdbc.url}")
  private String externalUrl;
  @Value("${external.jdbc.username}")
  private String externalUsername;
  @Value("${external.jdbc.password}")
  private String externalPassword;

  @Bean(name = "externalDB")
  public DataSource externalDataSource() {
    //DriverManagerDataSource dataSource = new DriverManagerDataSource();
    DataSource dataSource = new DataSource();
    dataSource.setDriverClassName(externalDriverClassName);
    dataSource.setUrl(externalUrl);
    dataSource.setUsername(externalUsername);
    dataSource.setPassword(externalPassword);
    dataSource.setInitSQL("SET search_path TO public");
    //dataSource.setInitSQL("SET search_path TO hico_ext, public");
    return dataSource;
  }

  // 트랜잭션설정
  @Bean(name = "externalDBTransactionManager")
  public PlatformTransactionManager externalTransactionManager(
      @Qualifier("externalDB") DataSource externalDataSource) {
    return new DataSourceTransactionManager(externalDataSource);
  }

  // SqlSession설정
  @Bean(name = "externalDBSqlSessionFactory")
  public SqlSessionFactory externalSqlSessionFactory(
      @Qualifier("externalDB") DataSource externalDataSource) throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

    factoryBean.setDataSource(externalDataSource);
    factoryBean.setTypeAliasesPackage("kr.go.hico.*.*.vo");

    // 2. XML 매퍼 파일 위치 설정
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factoryBean.setMapperLocations(resolver.getResources("classpath:/externalMapper/**/**/*.xml"));

    SqlSessionFactory externalSessionFactory = factoryBean.getObject();
    externalSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    externalSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

    return externalSessionFactory;
  }

  @Bean(name = "externalDBSqlSessionTemplate")
  public SqlSessionTemplate externalDBSqlSessionTemplate(
      @Qualifier("externalDBSqlSessionFactory") SqlSessionFactory externalDBSqlSessionFactory) {
    return new SqlSessionTemplate(externalDBSqlSessionFactory);
  }
}
