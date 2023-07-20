package com.szuse.bkguidance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.szuse.bkguidance.config.UploadConfig;
import com.szuse.bkguidance.config.UploadProperties;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class SzuseBkguidanceApplicationTests {

    @Test
    public void applicationInitializeTest(
            @Value("${bkguidance.upload.relativeBasicPath}") String relativeBasicPath,
            @Value("${bkguidance.upload.absoluteBasicPath}") String absoluteBasicPath,
            @Value("${bkguidance.upload.datasetSubPath}") String datasetSubPath) {
        var basicPath = UploadProperties.BASIC_PATH;
        var datasetPath = UploadProperties.DATASET_PATH;
        UploadProperties properties = new UploadProperties(
                new UploadConfig(relativeBasicPath, absoluteBasicPath, datasetSubPath));
        Assertions.assertAll(
                () -> assertEquals(basicPath, UploadProperties.BASIC_PATH),
                () -> assertEquals(datasetPath, UploadProperties.DATASET_PATH));
    }

}
