package chengweiou.universe.triangulum.service;


import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.triangulum.base.upload.UploadConfig;
import chengweiou.universe.triangulum.model.FilePlan;
import chengweiou.universe.triangulum.model.ImagePlan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
public class FilePlanTest {
	@Autowired
	private FilePlanService service;
	@Autowired
	private ImagePlanService imagePlanService;
	@Autowired
	private UploadConfig config;

	// todo new service to save audio, file
	@Test
	public void saveDelete() throws IOException {
		ImagePlan imagePlan = Builder.set("base64", "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=").set("nameWithoutType", "filepanservicetest").to(new ImagePlan());
		imagePlanService.save(imagePlan);
		Assertions.assertEquals(true, new File(config.getPath() + imagePlan.path().getFilepath() + imagePlan.path().getFile()).exists());

		MultipartFile multipartFile = new MockMultipartFile(
				"file", "filepanservicetest.gif", "", new FileInputStream(new File(config.getPath() + imagePlan.path().getFilepath() + imagePlan.path().getFile())));
		FilePlan e = Builder.set("file", multipartFile).set("nameWithoutType", "filepanservicetest-result").to(new FilePlan());
		service.save(e);
		Assertions.assertEquals(true, new File(config.getPath() + e.path().getFilepath() + e.path().getFile()).exists());
	}

}
