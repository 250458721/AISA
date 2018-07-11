package com.icbc.zoro.common;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//import com.github.pagehelper.PageHelper;
public class dbConfig {
	@Configuration
	@ConfigurationProperties(prefix = "spring.datasource")
	public class MyBatisConfiguration {
		
		private String url;
		private String driverClassName;
		private String username;
		private String password;


		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
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
		@Bean
		public PageHelper pageHelper() {
			System.out.println("MyBatisConfiguration.pageHelper()");
			PageHelper pageHelper = new PageHelper();
			Properties p = new Properties();
			p.setProperty("offsetAsPageNum", "true");
			p.setProperty("rowBoundsWithCount", "true");
			p.setProperty("reasonable", "true");
			pageHelper.setProperties(p);
			return pageHelper;
		}
		*/
		@Bean(name = "dataSource")
		public DriverManagerDataSource dataSource(){
			 DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		     driverManagerDataSource.setDriverClassName(driverClassName);
		     driverManagerDataSource.setUrl(url);
		     driverManagerDataSource.setUsername(username);
		     driverManagerDataSource.setPassword(password);
		     return driverManagerDataSource;
		}

	}
}
