package chengweiou.universe.triangulum.model;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImagePlanTile extends ServiceEntity {
    // file
    private String base64;

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

    /**
     * spec
     *  1.auto: only origin image
     *  2.origin image with a small image
     *      w || h
     *  3.only small image
     *      (w || h) && single
     */
    private Integer w;
    private Boolean single;

    /**
     * 总片数，当前第几片
     */
    private Integer fullNum;
    private Integer num;
    private String hash;

    private Path path;
    public Path path() {
        if (path != null) return path;
        String type = base64.substring(base64.indexOf("data:image/") + "data:image/".length(), base64.indexOf(";base64,"));
        String file = nameWithoutType + "." + type;
        String filepath = "/upload" + (!owner.isBlank() ? ("/person/" + owner) : "" ) + "/" + category + "/";
        String frontend = filepath + nameWithoutType + "." + type + "?" + base64.length();
        path = new Path(filepath, file, frontend);
        return path;
    }


    private String base64Value;
    public String getBase64Value() {
        base64Value = base64Value != null ? base64Value : base64.substring(base64.indexOf(";base64,") + ";base64,".length());
        base64Value = base64Value.replaceAll(" ", "+");
        return base64Value;
    }
    public void fillNotRequire() {
        owner = owner != null ? owner : "";
        category = category != null ? category : LocalDate.now(ZoneId.of("UTC")).toString();
        nameWithoutType = nameWithoutType != null ? nameWithoutType : LocalDate.now(ZoneId.of("UTC")).toString() + RandomStringUtils.randomAlphabetic(6);
        w = w != null ? w : 0;
        single = single != null ? single : false;
    }
    public static final ImagePlan NULL = new Null();
    public static class Null extends ImagePlan implements NullObj {
    }

    @Getter
    @ToString
    public static class Path {
        private String frontend;
        private String filepath;
        private String filepathBig;
        private String file;
        public Path(String filepath, String filename, String frontendUrl) {
            this.filepath = filepath;
            this.filepathBig = filepath + "/big/";
            this.file = filename;
            this.frontend = frontendUrl;
        }
    }

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Dto extends DtoEntity {
        private String base64;
        private String owner;
        private String category;
        private String nameWithoutType;
        private Integer w;
        private Boolean single;
        private Integer fullNum;
        private Integer num;
        private String hash;
        private Path path;

        public ImagePlanTile toBean() {
            ImagePlanTile result = new ImagePlanTile();
            BeanUtils.copyProperties(this, result);
            return result;
        }
    }
}
