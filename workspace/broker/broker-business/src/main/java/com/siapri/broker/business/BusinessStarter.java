package com.siapri.broker.business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.esotericsoftware.yamlbeans.YamlReader;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BusinessStarter {
	
	@Bean
	public Config loadConfig() throws FileNotFoundException, IOException{
		Config config  = null;
			try(Reader fileReader = new InputStreamReader(BusinessStarter.class.getClassLoader().getResourceAsStream("config.yml"))){
			final YamlReader yamReader = new YamlReader(fileReader);
			config = yamReader.read(Config.class);
			yamReader.close();
		}
		return config;
	}
	
	public static void main(final String[] args) throws Exception{
		SpringApplication.run(BusinessStarter.class, args);
	}
}
