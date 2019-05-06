package ru.egor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(value = "ru.egor")
@PropertySource("classpath:database.properties")
@ImportResource("classpath:security-context.xml")
@EnableTransactionManagement
public class TestConfig {
    @Value("${database.username}")
    String userName;

    @Value("${database.password}")
    String userPass;

}
