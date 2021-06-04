package chengweiou.universe.triangulum.controller.all;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.triangulum.base.config.ProjConfig;
import chengweiou.universe.triangulum.model.ImagePlan;
import chengweiou.universe.triangulum.service.ImagePlanService;

@RestController
public class ImagePlanController {
    @Autowired
    private ImagePlanService service;
    @Autowired
    private ProjConfig config;
    @PostMapping("/image")
    public Rest<String> image(ImagePlan e) throws ParamException {
        // todo 强制设置owner，或者api才能自行设置
        Valid.check("image.base64", e.getBase64()).is().lengthIn(10, -1).include("data:image/", ";base64,");
        if (e.getCategory() != null) Valid.check("image.category", e.getCategory()).is().of(config.getCategoryList());
        service.save(e);
        return Rest.ok(e.path().getFrontend());
    }
}
