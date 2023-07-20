package com.szuse.bkguidance.config;

import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

@Import({UploadConfig.class})
public class UploadProperties {

    public static String BASIC_PATH;
    public static String DATASET_PATH;

    public UploadProperties(UploadConfig uploadConfig) {
        if (StringUtils.hasText(uploadConfig.getAbsoluteBasicPath())) {
            BASIC_PATH = uploadConfig.getAbsoluteBasicPath();
            DATASET_PATH = BASIC_PATH + uploadConfig.getDatasetSubPath();
        } else if (StringUtils.hasText(uploadConfig.getRelativeBasicPath())) {
            BASIC_PATH = uploadConfig.getRelativeBasicPath();
            DATASET_PATH = BASIC_PATH + uploadConfig.getDatasetSubPath();
        }
    }

}
