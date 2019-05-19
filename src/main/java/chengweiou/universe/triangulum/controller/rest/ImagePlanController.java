package chengweiou.universe.triangulum.controller.rest;


import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.triangulum.model.ImagePlan;
import chengweiou.universe.triangulum.service.ImagePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImagePlanController {
    @Autowired
    private ImagePlanService service;
    @PostMapping("/image")
    public Rest<String> image(ImagePlan e) throws ParamException {
        Valid.check("image.base64", e.getBase64()).is().lengthIn(10, -1).include("data:image/", ";base64,");
        service.save(e);
        return Rest.ok(e.path().getFrontend());
    }
}
