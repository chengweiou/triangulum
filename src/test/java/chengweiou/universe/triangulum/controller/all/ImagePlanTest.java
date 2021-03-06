package chengweiou.universe.triangulum.controller.all;


import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.triangulum.base.config.ProjConfig;

@SpringBootTest
@ActiveProfiles("test")
public class ImagePlanTest {
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private ProjConfig config;

	@Test
	public void image() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/image")
				.param("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
				.param("nameWithoutType", "abc").param("w", "100")
			).andReturn().getResponse().getContentAsString();
		Rest<String> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(true, rest.getData().length() > 3);
		new File(config.getPath() + rest.getData().substring(0, rest.getData().indexOf("?"))).delete();
		String big = rest.getData().substring(0, rest.getData().lastIndexOf("/"))
				+ "/big" + rest.getData().substring(rest.getData().lastIndexOf("/"), rest.getData().indexOf("?"));
		new File(config.getPath() + big).delete();
	}

	@Test
	public void imageFail() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/image")
				.param("nameWithoutType", "abc").param("w", "100")
		).andReturn().getResponse().getContentAsString();
		Rest<String> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.PARAM, rest.getCode());
	}

	@BeforeEach
	public void before() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
}
