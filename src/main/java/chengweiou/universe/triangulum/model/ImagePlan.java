package chengweiou.universe.triangulum.model;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;

public class ImagePlan implements NotNullObj, Serializable {
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

        public String getFrontend() {
            return frontend;
        }

        public String getFilepath() {
            return filepath;
        }

        public String getFilepathBig() {
            return filepathBig;
        }

        public String getFile() {
            return file;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "frontend='" + frontend + '\'' +
                    ", filepath='" + filepath + '\'' +
                    ", filepathBig='" + filepathBig + '\'' +
                    ", file='" + file + '\'' +
                    '}';
        }
    }
    public String getBase64() {
        return base64;
    }

    @Override
    public String toString() {
        return "ImagePlan{" +
                "base64='" + base64 + '\'' +
                ", owner='" + owner + '\'' +
                ", category='" + category + '\'' +
                ", nameWithoutType='" + nameWithoutType + '\'' +
                ", w=" + w +
                ", single=" + single +
                ", path=" + path +
                ", base64Value='" + base64Value + '\'' +
                '}';
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameWithoutType() {
        return nameWithoutType;
    }

    public void setNameWithoutType(String nameWithoutType) {
        this.nameWithoutType = nameWithoutType;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }
}
