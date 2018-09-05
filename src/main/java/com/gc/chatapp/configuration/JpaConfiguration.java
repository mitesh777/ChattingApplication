package com.gc.chatapp.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.gc.chatapp.dao", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class JpaConfiguration {

	@Autowired
	private Environment environment;

	@Value("${datasource.chatapp.maxPoolSize}")
	private int maxPoolSize;

	@Value("${datasource.chatapp.driverClassName}")
	private String driverClass;

	@Value("${spring.datasource.url}")
	private String databaseUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/*
	 * Populate SpringBoot DataSourceProperties object directly from
	 * application.properties properties of DataSourceProperties object].
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.chatapp")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean(destroyMethod = "")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.gc.chatapp.entities" });
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		return adapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.chatapp.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto",
				environment.getRequiredProperty("datasource.chatapp.hibernate.hbm2ddl.method"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.chatapp.hibernate.show_sql"));
		properties.put("hibernate.format_sql",
				environment.getRequiredProperty("datasource.chatapp.hibernate.format_sql"));
		if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.chatapp.defaultSchema"))) {
			properties.put("hibernate.default_schema",
					environment.getRequiredProperty("datasource.chatapp.defaultSchema"));
		}
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
