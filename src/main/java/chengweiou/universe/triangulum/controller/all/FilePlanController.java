package chengweiou.universe.triangulum.controller.all;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.triangulum.base.upload.UploadConfig;
import chengweiou.universe.triangulum.model.FilePlan;
import chengweiou.universe.triangulum.service.FilePlanService;

@RestController
public class FilePlanController {
    @Autowired
    private FilePlanService service;
    @Autowired
    private UploadConfig config;
    @PostMapping("/file")
    public Rest<String> file(FilePlan e) throws ParamException {
        Valid.check("file", e.getFile().getSize()).is().positive();
        if (e.getCategory() != null) Valid.check("image.category", e.getCategory()).is().of(config.getCategoryList());
        service.save(e);
        return Rest.ok(e.path().getFrontend());
    }
}
