package com.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class DeliveryDemoApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        // Check if RestTemplate bean is present
        try {
            RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
            System.out.println("RestTemplate bean found: " + restTemplate);
        } catch (Exception e) {
            System.out.println("RestTemplate bean not found.");
        }
    }
}
