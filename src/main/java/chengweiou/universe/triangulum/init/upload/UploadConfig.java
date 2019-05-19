package chengweiou.universe.triangulum.init.upload;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String path;
    private List<String> categoryList;

    @Override
    public String toString() {
        return "Config{" +
                "path='" + path + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }
}