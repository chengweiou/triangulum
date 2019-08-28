package chengweiou.universe.triangulum.model;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;

@Data
public class FilePlan implements NotNullObj, Serializable {
    // file
    private MultipartFile file;

    /**
     * path: avoid mess upload folder
     *  1. free save
     *      /upload/2019-03-02/20190302sicl.png
     *  2. belong to person.id=6
     *      /upload/person/6/2019-03-02/20190302sicl.png
     *  3. belong to person.id=6 and category book
     *      /upload/person/6/book/20190302sicl.png
     *  4. no owner, but in a category
     *      /upload/book/20190302sicl.png
     */
    private String owner;
    private String category;
    private String nameWithoutType;

    private Path path;
    public Path path() {
        if (path != null) return path;
        String type = file.getOriginalFilename().substring(file.getName().lastIndexOf(".")+1);
        String file = nameWithoutType + "." + type;
        String filepath = "/upload" + (!owner.isBlank() ? ("/person/" + owner) : "" ) + "/" + category + "/";
        String frontend = filepath + nameWithoutType + "." + type + "?" + file.length();
        path = new Path(filepath, file, frontend);
        return path;
    }

// todo add file to base then to file
//    private String base64Value;
//    public String getBase64Value() {
//        base64Value = base64Value != null ? base64Value : base64.substring(base64.indexOf(";base64,") + ";base64,".length());
//        return base64Value;
//    }
    public void fillNotRequire() {
        owner = owner != null ? owner : "";
        category = category != null ? category : LocalDate.now(ZoneId.of("UTC")).toString();
        nameWithoutType = nameWithoutType != null ? nameWithoutType : LocalDate.now(ZoneId.of("UTC")).toString() + RandomStringUtils.randomAlphabetic(6);

    }
    public static final FilePlan NULL = new Null();
    public static class Null extends FilePlan implements NullObj {
    }

    @Getter
    @ToString
    public static class Path {
        private String frontend;
        private String filepath;
        private String file;
        public Path(String filepath, String filename, String frontendUrl) {
            this.filepath = filepath;
            this.file = filename;
            this.frontend = frontendUrl;
        }
    }
}
