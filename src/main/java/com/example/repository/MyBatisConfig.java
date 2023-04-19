package com.example.repository;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
// @org.mybatis.spring.annotation.MapperScanを付与し、Mapperインターフェースのスキャンを有効にします。
@MapperScan("com.example.repository")
public class MyBatisConfig {

    // データソースのBean定義をします。
    // 今回はSQLite3を使用するため、DBファイルのパスを指定します。
	public DataSource dataSource() {
	    return DataSourceBuilder.create()
	        .url("jdbc:postgresql://localhost:5432/data_processing")
	        .username("postgres")
	        .password("postgres")
	        .build();
	}

    // org.mybatis.spring.SqlSessionFactoryBeanをBean定義します。
    // これによりSqlSessionFactoryBeanを利用してSqlSessionFactoryが生成されます。
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        // データソースを設定する。MyBatisの処理の中でSQLを発行すると、
        // ここで指定したデータソースからコネクションが取得されます。
        sessionFactoryBean.setDataSource(dataSource());
        // MyBatis設定ファイルを指定します。
        // 今回はresources直下に設定ファイルを配置します。
        sessionFactoryBean.setConfigLocation(new ClassPathResource("myBatis-config.xml"));
        return sessionFactoryBean;
    }

    // トランザクションマネージャーのBeanを定義します。
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}