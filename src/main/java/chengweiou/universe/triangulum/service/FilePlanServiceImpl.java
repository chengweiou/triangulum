package chengweiou.universe.triangulum.service;


import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.util.LogUtil;
import chengweiou.universe.triangulum.base.config.ProjConfig;
import chengweiou.universe.triangulum.model.FilePlan;

@Service
public class FilePlanServiceImpl implements FilePlanService {
    @Autowired
    private ProjConfig config;

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
            if (Files.notExists(Paths.get(filename))) Files.createFile(Paths.get(filename));
            e.getFile().transferTo(Paths.get(filename));
        } catch (FileAlreadyExistsException ex) {
        } catch (IOException ex) {
            LogUtil.e("file create fail, url: " + filename, ex);
        }
    }
}
