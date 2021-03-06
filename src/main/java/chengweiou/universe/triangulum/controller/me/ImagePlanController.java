package chengweiou.universe.triangulum.controller.me;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.triangulum.base.config.ProjConfig;
import chengweiou.universe.triangulum.base.converter.Account;
import chengweiou.universe.triangulum.model.ImagePlan;
import chengweiou.universe.triangulum.service.ImagePlanService;

@RestController("meImagePlanController")
@RequestMapping("me")
public class ImagePlanController {
    @Autowired
    private ImagePlanService service;
    @Autowired
    private ProjConfig config;

    @PostMapping("/image")
    public Rest<String> image(ImagePlan e, @RequestHeader("loginAccount") Account loginAccount) throws ParamException {
        Valid.check("loginAccount.person", loginAccount.getPerson()).isNotNull();
        Valid.check("loginAccount.person.id", loginAccount.getPerson().getId()).is().positive();
        Valid.check("image.base64", e.getBase64()).is().lengthIn(10, -1).include("data:image/", ";base64,");
        if (e.getCategory() != null) Valid.check("image.category", e.getCategory()).is().of(config.getCategoryList());
        e.setOwner(loginAccount.getPerson().getId().toString());
        service.save(e);
        return Rest.ok(e.path().getFrontend());
    }
}
