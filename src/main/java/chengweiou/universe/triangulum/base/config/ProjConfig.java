package chengweiou.universe.triangulum.base.config;


import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "proj")
@Component
@Data
public class ProjConfig {
    private String path;
    private List<String> categoryList;
}
