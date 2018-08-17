package de.synoa.getting.started.helloworld.configurations;

import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataFormats {

    @Bean
    public CsvDataFormat createCSVDataFormat() {
        return new CsvDataFormat().setDelimiter(',').setSkipHeaderRecord(true).setUseMaps(true);
    }

}
