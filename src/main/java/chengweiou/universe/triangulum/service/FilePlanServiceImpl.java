package chengweiou.universe.triangulum.service;


import chengweiou.universe.blackhole.util.LogUtil;
import chengweiou.universe.triangulum.base.upload.UploadConfig;
import chengweiou.universe.triangulum.model.FilePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilePlanServiceImpl implements FilePlanService {
    @Autowired
    private UploadConfig config;

    @Override
    public void save(FilePlan e) {
        e.fillNotRequire();
        String filename = config.getPath() + e.path().getFilepath() + e.path().getFile();
        String filepath = filename.substring(0, filename.lastIndexOf("/"));
        try {
            Files.createDirectories(Path.of(filepath));
        } catch (IOException ex) {
            LogUtil.e("file dirs create fail, url: " + filepath, ex);
        }
        try {
            Files.createFile(Paths.get(filename));
            e.getFile().transferTo(new File(filename));
        } catch (IOException ex) {
            LogUtil.e("file create fail, url: " + filename, ex);
        }
    }
}
