package chengweiou.universe.triangulum.service;


import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.triangulum.init.upload.UploadConfig;
import chengweiou.universe.triangulum.model.ImagePlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

@SpringBootTest
@ActiveProfiles("test")
public class ImagePlanTest {
	@Autowired
	private ImagePlanService service;
	@Autowired
	private UploadConfig config;

	@Test
	public void saveDelete() {
		ImagePlan e = Builder.set("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=").to(new ImagePlan());
		service.save(e);
		Assertions.assertEquals(true, new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).exists());
		new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).delete();
	}

	@Test
	public void saveByName() {
		ImagePlan e = Builder.set("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
				.set("nameWithoutType", "aaa")
				.to(new ImagePlan());
		service.save(e);
		Assertions.assertEquals(true, e.path().getFrontend().contains("/aaa.gif"));
		Assertions.assertEquals(true, new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).exists());
		Assertions.assertEquals(true, !new File(config.getPath() + e.path().getFilepathBig() + e.path().getFile()).exists());
		new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).delete();
	}

	@Test
	public void saveByCompress() {
		ImagePlan e = Builder.set("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
				.set("w", 50)
				.to(new ImagePlan());
		service.save(e);
		Assertions.assertEquals(true, new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).exists());
		Assertions.assertEquals(true, new File(config.getPath() + e.path().getFilepathBig() + e.path().getFile()).exists());
		new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).delete();
		new File(config.getPath() + e.path().getFilepathBig() + e.path().getFile()).delete();
	}
	@Test
	public void saveBySingle() {
		ImagePlan e = Builder.set("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
				.set("w", 100)
				.set("single", true)
				.to(new ImagePlan());
		service.save(e);
		Assertions.assertEquals(true, new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).exists());
		Assertions.assertEquals(true, !new File(config.getPath() + e.path().getFilepathBig() + e.path().getFile()).exists());
		new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).delete();
	}
}
