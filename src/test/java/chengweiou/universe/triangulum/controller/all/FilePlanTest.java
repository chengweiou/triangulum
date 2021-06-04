package chengweiou.universe.triangulum.controller.all;


import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.triangulum.base.config.ProjConfig;
import chengweiou.universe.triangulum.model.ImagePlan;
import chengweiou.universe.triangulum.service.ImagePlanService;

@SpringBootTest
@ActiveProfiles("test")
public class FilePlanTest {
	@Autowired
	private ImagePlanService imagePlanService;
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private ProjConfig config;

	@Test
	public void file() throws Exception {
		ImagePlan imagePlan = Builder.set("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=").set("nameWithoutType", "filepanservicetest").to(new ImagePlan());
		imagePlanService.save(imagePlan);
		Assertions.assertEquals(true, new File(config.getPath() + imagePlan.path().getFilepath() + imagePlan.path().getFile()).exists());

		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "aaa.txt", "text/plain", "test data".getBytes());
		String result = mvc.perform(MockMvcRequestBuilders.multipart("/file")
				.file(mockMultipartFile)
				.param("nameWithoutType", "filepan-controller-result")
			).andReturn().getResponse().getContentAsString();
		Rest<String> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(true, rest.getData().length() > 3);
		new File(config.getPath() + rest.getData().substring(0, rest.getData().indexOf("?"))).delete();
	}

	@Test
	public void fileFail() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/file")
				.param("nameWithoutType", "abc")
		).andReturn().getResponse().getContentAsString();
		Rest<String> rest = Rest.from(result);
		// todo 缺少file，没有异常？想办法改为param
		Assertions.assertEquals(BasicRestCode.FAIL, rest.getCode(), rest.getMessage());
	}

	@BeforeEach
	public void before() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
}
