package com.szuse.bkguidance;

import com.szuse.bkguidance.config.UploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({UploadProperties.class})
@SpringBootApplication(scanBasePackages = {"com.szuse.bkguidance"})
public class SzuseBkguidanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SzuseBkguidanceApplication.class, args);
    }
}
