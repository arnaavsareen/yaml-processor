package com.example.yamlprocessor;

import com.example.yamlprocessor.model.ConnectionParameters;
import com.example.yamlprocessor.service.YamlProcessorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class YamlProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(YamlProcessorApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ApplicationContext ctx) {
        return args -> {
            YamlProcessorService yamlProcessorService = ctx.getBean(YamlProcessorService.class);
            String inputFilePath = "src/main/resources/VMwareExporterBeforeProcessing.yaml";
            String outputFilePath = "src/main/resources/VMwareExporterAfterProcessing.yaml";
            String finalOutputFilePath = "src/main/resources/VMwareExporterFinalProcessing.yaml";

            try {
                Map<String, ConnectionParameters> data = yamlProcessorService.readYamlFile(inputFilePath);

                ConnectionParameters newParams = new ConnectionParameters();
                newParams.setVsphere_host("127.0.0.1:8443");
                newParams.setVsphere_user("auser@vsphere.local");
                newParams.setVsphere_password("Heywhat$down88&");
                newParams.setIgnore_ssl(true);
                newParams.setSpecs_size(5000);
                newParams.setFetch_custom_attributes(true);
                newParams.setFetch_tags(false);
                newParams.setFetch_alarms(false);
                Map<String, Boolean> collectOnly = new LinkedHashMap<>();
                collectOnly.put("vms", true);
                collectOnly.put("vmguests", true);
                collectOnly.put("datastores", true);
                collectOnly.put("hosts", true);
                collectOnly.put("snapshots", true);
                newParams.setCollect_only(collectOnly);

                System.out.println("Creating entry");
                yamlProcessorService.createEntry(data, "12345", newParams);

                System.out.println("Writing to file after creation");
                yamlProcessorService.writeYamlFile(outputFilePath, data);

                Map<String, ConnectionParameters> newData = yamlProcessorService.readYamlFile(outputFilePath);

                ConnectionParameters updatedParams = new ConnectionParameters();
                updatedParams.setVsphere_host("192.168.1.1:8443");
                updatedParams.setVsphere_user("updateduser@vsphere.local");
                updatedParams.setVsphere_password("NewPassword123");
                updatedParams.setIgnore_ssl(true);
                updatedParams.setSpecs_size(5000);
                updatedParams.setFetch_custom_attributes(true);
                updatedParams.setFetch_tags(false);
                updatedParams.setFetch_alarms(false);
                updatedParams.setCollect_only(collectOnly);

                System.out.println("Updating entry.");
                yamlProcessorService.updateEntry(newData, "1234", updatedParams);

                System.out.println("Writing to file after update.");
                yamlProcessorService.writeYamlFile(finalOutputFilePath, newData);

                Map<String, ConnectionParameters> finalData = yamlProcessorService.readYamlFile(finalOutputFilePath);

                System.out.println("Deleting entry.");
                yamlProcessorService.deleteEntry(finalData, "12345");

                System.out.println("Writing to file after deletion.");
                yamlProcessorService.writeYamlFile(finalOutputFilePath, finalData);

                System.out.println("YAML processing has been completed successfully.");
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("There was an error processing YAML files: " + e.getMessage());
            }
        };
    }
}
