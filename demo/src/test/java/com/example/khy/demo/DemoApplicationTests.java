package com.example.khy.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	/*@Autowired
	private DocumentConverter documentConverter;

	@Test
	public void test() throws Exception {
		String sourceFile = "E:\\test\\输出.docx";
		String destFile = "E:\\aaaa\\aaaa\\pdfdest.pdf";
//        int i = WordToPdf.office2PDF(sourceFile, destFile);
//        System.out.println(i);
		InputStream inputStream = new FileInputStream(sourceFile);
		OutputStream outputStream = new FileOutputStream(destFile);
		documentConverter
				.convert(inputStream)
				.as(DefaultDocumentFormatRegistry.DOCX)
				.to(outputStream)
				.as(DefaultDocumentFormatRegistry.PDF)
				.execute();
		//JodConverter.convert(sourceFile).to(destFile).execute();

	}*/
}
