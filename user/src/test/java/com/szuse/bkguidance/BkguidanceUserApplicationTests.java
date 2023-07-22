package com.szuse.bkguidance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.szuse.event.config.RabbitConfig;
import com.szuse.user.config.UploadConfig;
import com.szuse.user.config.UploadProperties;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({RabbitConfig.class})
@Log4j2
@SpringBootTest
class BkguidanceUserApplicationTests {

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

    @Test
    public void rabbitMQSetupTest(
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost,
            @Autowired ConnectionFactory connectionFactory) {
        Assertions.assertAll(
                () -> assertEquals(port, connectionFactory.getPort()),
                () -> assertEquals(username, connectionFactory.getUsername()),
                () -> assertEquals(virtualHost,connectionFactory.getVirtualHost()));
    }
}
