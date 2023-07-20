package com.szuse.bkguidance.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "bkguidance.upload")
public class UploadConfig {

    private String relativeBasicPath;
    private String absoluteBasicPath;
    private String datasetSubPath;
}
