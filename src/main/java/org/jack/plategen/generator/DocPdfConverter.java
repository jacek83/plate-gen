package org.jack.plategen.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class DocPdfConverter {
	private static final Logger LOG = LoggerFactory.getLogger(DocPdfConverter.class);
	
	@Value("${output.pdf.path}")
	private String outputPdfPath;

	public void convertToPdf(XWPFDocument wordDocument, String docFileName) throws XWPFConverterException, IOException {
		String pdfFileName = this.outputPdfPath + "/" + docFileName + ".pdf";
		OutputStream out = new FileOutputStream(new File(pdfFileName));
		PdfOptions options = PdfOptions.create();
		PdfConverter.getInstance().convert(wordDocument, out, options);
		out.close();
		LOG.info("Plates generated into: " + pdfFileName);
	}
}