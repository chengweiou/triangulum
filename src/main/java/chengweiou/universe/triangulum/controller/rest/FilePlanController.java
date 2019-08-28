package chengweiou.universe.triangulum.controller.rest;


import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.triangulum.model.FilePlan;
import chengweiou.universe.triangulum.service.FilePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilePlanController {
    @Autowired
    private FilePlanService service;
    @PostMapping("/file")
    public Rest<String> file(FilePlan e) throws ParamException {
        Valid.check("file", e.getFile().getSize()).is().positive();
        service.save(e);
        return Rest.ok(e.path().getFrontend());
    }
}
