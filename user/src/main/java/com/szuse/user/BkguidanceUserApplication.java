package com.szuse.user;

import com.szuse.user.config.UploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({UploadProperties.class})
@SpringBootApplication(scanBasePackages = {"com.szuse.bkguidance"})
public class BkguidanceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BkguidanceUserApplication.class, args);
    }
}
