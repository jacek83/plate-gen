package org.jack.plategen.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jack.plategen.date.DateUtils;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

// registration plates for the input might be generated from: http://nummernschild.heisnbrg.net/fe/
@Setter
@Getter
@PropertySource("classpath:config.properties")
public class DocGen {

	private static final BigInteger MARGIN_10MM = BigInteger.valueOf(569L);

	private static final Logger LOG = LoggerFactory.getLogger(DocGen.class);

	private FileWalker fileWalker;

	private DocPdfConverter docPdfConverter;

	private ImageGenerator imgGenerator;

	@Value("${input.graphics.path}")
	private String graphicsPath;
//
	@Value("${output.word.path}")
	private String outputWordPath;

	@Value("${write.mode}")
	private String writeMode;

	public void createDoc() throws Exception {
		// Blank Document
		XWPFDocument document = new XWPFDocument();

		addMarginsToDoc(document);

		// Write the Document in file system
		String fileNamePrefix = "plates" + "_" + DateUtils.getCurrentTs();
		String outputDocFileName = fileNamePrefix + ".docx";
		FileOutputStream out = new FileOutputStream(outputWordPath + "/" + outputDocFileName);
		// create Paragraph
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();

		Set<File> images = fileWalker.walk(graphicsPath);

		imgGenerator.generateImagesIntoDoc(images, run);

		document.write(out);

		// TODO JC pdf cannot be created due to some missing sections of the word file.
		// more analysis needed to solve the problem
		// docPdfConverter.convertToPdf(document, fileNamePrefix);

		out.close();
		document.close();
		LOG.info(outputDocFileName + " has been created");
	}

	private void addMarginsToDoc(XWPFDocument document) {
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		CTPageMar pageMar = sectPr.addNewPgMar();
		pageMar.setLeft(MARGIN_10MM);
		pageMar.setTop(MARGIN_10MM);
		pageMar.setRight(MARGIN_10MM);
		pageMar.setBottom(MARGIN_10MM);
	}
}
