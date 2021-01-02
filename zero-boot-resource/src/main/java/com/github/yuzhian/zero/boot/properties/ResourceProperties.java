package com.github.yuzhian.zero.boot.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * @author yuzhian
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "zero.resource")
public class ResourceProperties {
    @Getter
    private final Location location = new Location();

    @PostConstruct
    private void initLocation() throws IOException {
        File partsDir = new File(getLocation().getParts());
        File mergeDir = new File(getLocation().getMerge());
        File storeDir = new File(getLocation().getStore());
        if (!partsDir.exists() && !partsDir.mkdirs()) {
            throw new IOException("分片上传目录初始化失败");
        }
        if (!mergeDir.exists() && !mergeDir.mkdirs()) {
            throw new IOException("合并目录初始化失败");
        }
        if (!storeDir.exists() && !storeDir.mkdirs()) {
            throw new IOException("资源文件目录初始化失败");
        }
        if (log.isInfoEnabled())
            log.info("Resource location loaded, parts location:[{}], merge location:[{}], store location:[{}]",
                    partsDir.getAbsolutePath(), mergeDir.getAbsolutePath(), storeDir.getAbsolutePath());
    }

    @Getter
    @Setter
    public static class Location {
        /**
         * 分片目录
         */
        private String parts = ".resource" + File.separatorChar + "parts";

        /**
         * 合并目录
         */
        private String merge = ".resource" + File.separatorChar + "merge";

        /**
         * 资源目录
         */
        private String store = ".resource" + File.separatorChar + "store";
    }
}
