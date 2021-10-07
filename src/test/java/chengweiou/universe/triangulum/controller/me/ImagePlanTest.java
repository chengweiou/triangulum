package chengweiou.universe.triangulum.controller.me;


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
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.blackhole.util.GsonUtil;
import chengweiou.universe.triangulum.base.config.ProjConfig;
import chengweiou.universe.triangulum.base.converter.Account;
import chengweiou.universe.triangulum.base.converter.Person;

@SpringBootTest
@ActiveProfiles("test")
public class ImagePlanTest {
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private ProjConfig config;
	private Account loginAccount;

	@Test
	public void image() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/me/image")
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
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
	public void imageWithCategory() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/me/image")
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
				.param("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
				.param("nameWithoutType", "abc").param("category", "pet")
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
		// base64 missing
		String result = mvc.perform(MockMvcRequestBuilders.post("/me/image").header("loginAccount", GsonUtil.create().toJson(loginAccount))
				.param("nameWithoutType", "abc").param("w", "100")
		).andReturn().getResponse().getContentAsString();
		Rest<String> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.PARAM, rest.getCode());
		// base64 without base64 start
		result = mvc.perform(MockMvcRequestBuilders.post("/me/image").header("loginAccount", GsonUtil.create().toJson(loginAccount))
		.param("base64", "/9j/4QG2RXhpZgAATU0AKgAAAAgABwEQAAIAAAAaAAAAYgEAAAQA")
				.param("nameWithoutType", "abc").param("w", "100")
		).andReturn().getResponse().getContentAsString();
		rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.UNAUTH, rest.getCode(), rest.getMessage());
		// category out of list
		result = mvc.perform(MockMvcRequestBuilders.post("/me/image").header("loginAccount", GsonUtil.create().toJson(loginAccount))
				.param("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
				.param("nameWithoutType", "abc").param("category", "abcd").param("w", "100")
		).andReturn().getResponse().getContentAsString();
		rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.PARAM, rest.getCode());
	}

	@BeforeEach
	public void before() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		loginAccount = Builder.set("person", Builder.set("id", 10L).to(new Person()))
				.set("extra", "aaa")
				.to(new Account());
	}
}
