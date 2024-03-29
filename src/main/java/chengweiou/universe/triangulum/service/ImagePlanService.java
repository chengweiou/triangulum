package chengweiou.universe.triangulum.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.triangulum.base.config.ProjConfig;
import chengweiou.universe.triangulum.model.ImagePlan;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class ImagePlanService {
    @Autowired
    private ProjConfig config;

    public void save(ImagePlan e) {
        e.fillNotRequire();
        boolean compress = e.getW() > 0;
        String filename = config.getPath() + (compress ? e.path().getFilepathBig() : e.path().getFilepath()) + e.path().getFile();
        String filepath = filename.substring(0, filename.lastIndexOf("/"));
        try {
            Files.createDirectories(Path.of(filepath));
        } catch (IOException ex) {
            log.error("file dirs create fail, url: " + filepath, ex);
        }
        try (FileOutputStream out = new FileOutputStream(filename)) {
            byte[] imageByteArray = Base64.getDecoder().decode(e.getBase64Value());
            out.write(imageByteArray);
        } catch (IOException ex) {
            log.error("file create fail, url: " + filename, ex);
        }
        if (compress) {
            try {
                Thumbnails.of(new File(filename)).size(e.getW(), e.getW()).toFile(config.getPath() + e.path().getFilepath() + e.path().getFile());
            } catch (IOException ex) {
                log.error("file compress create fail, url: " + e.path().getFilepath() + e.path().getFile(), ex);
            }
            if (e.getSingle()) {
                new File(filename).delete();
            }
        }
    }
}
