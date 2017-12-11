package edu.sjsu.cmpe275.project.trainMgmt.util;

import java.util.Properties;
import java.lang.Object;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence-${envTarget:mysql}.properties" })
@ComponentScan({ "edu.sjsu.cmpe275.project.trainMgmt" })
@EnableJpaRepositories(basePackages = "edu.sjsu.cmpe275.project.trainMgmt.dao")
public class PersistenceConfig {
//TODO

	}
