package org.jack.plategen.generator;

import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_BMP;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_DIB;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_EMF;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_EPS;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_GIF;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_JPEG;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PICT;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_TIFF;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_WMF;
import static org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_WPG;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageGenerator {
	private static final Logger LOG = LoggerFactory.getLogger(ImageGenerator.class);

	private static final int EMU_PER_MM = Units.EMU_PER_CENTIMETER / 10;

	private ScaleConverter scaleConverter;

	@Value("${images.total_number: 4}")
	private int imagesTotalNumber;

	@Value("${images.generated.step: 0.5}")
	private double imagesGeneratedStep;

	@Value("${images.scaling.mode: HEIGHT}")
	private String imageScalingMode;

	@Value("${images.scaling.original.height}")
	private int originalHeight;

	@Value("${images.scaling.original.width}")
	private int originalWidth;

	public void generateImagesIntoDoc(Set<File> images, XWPFRun run) {
		try {
			for (File imgFile : images) {
				addImagesToWordDocument(imgFile, run);
			}
		} catch (InvalidFormatException | IOException e) {
			LOG.error("Adding images into doc file has failed " + e);
		}
	}

	private void addImagesToWordDocument(File imageFile, XWPFRun r) throws IOException, InvalidFormatException {
		BufferedImage buffImage = ImageIO.read(imageFile);
		// width and height are in pixels
		int width = buffImage.getWidth();
		int height = buffImage.getHeight();

		double scale = scaleConverter.getScale();

		// maintain width/height ratio of the image
		double imgWidthHeightRatio = width * 1.0 / height;
		int targetHeight = 0;
		int targetWidth = 0;

		// step for each next image
		int step = (int) (Double.valueOf(imagesGeneratedStep) * EMU_PER_MM);

		switch (ScalingMode.valueOf(imageScalingMode)) {
		case WIDTH:
			targetWidth = (int) (originalWidth * scale * EMU_PER_MM);
			targetHeight = (int) (targetWidth * (1.0d / imgWidthHeightRatio));
			break;
		default:
		case HEIGHT:
			targetHeight = (int) (originalHeight * scale * EMU_PER_MM);
			targetWidth = (int) (targetHeight * imgWidthHeightRatio);
			break;
		}

		addImages(imageFile, r, imgWidthHeightRatio, targetHeight, targetWidth, step);

		r.addBreak();
		r.addBreak();
	}

	/**
	 * Inserts the given image in imagesTotalNumber copies into the given XWPFRun of the word document. Each image differs by the given step value.
	 * @param imageFile
	 * @param r
	 * @param imgWidthHeightRatio
	 * @param targetHeight
	 * @param targetWidth
	 * @param step
	 */
	private void addImages(File imageFile, XWPFRun r, double imgWidthHeightRatio, int targetHeight, int targetWidth, int step)
			throws InvalidFormatException, IOException {
		String imgFile = imageFile.getName();
		int imgFormat = this.toIntImageFormat(imgFile);

		for (int i = -1; i < this.imagesTotalNumber - 1; ++i) {
			int wdth = (int) ((double) targetWidth + (double) (i * step) * imgWidthHeightRatio);
			int hght = targetHeight + i * step;
			r.addPicture(new FileInputStream(imageFile), imgFormat, imgFile, wdth, hght);
			r.setText("  ");
		}
	}

	/**
	 * Converts the image file extension to the int value expected by POI framework.
	 */
	private int toIntImageFormat(String imgFileName) {
		int format;
		if (imgFileName.endsWith(".emf")) {
			format = PICTURE_TYPE_EMF;
		} else if (imgFileName.endsWith(".wmf")) {
			format = PICTURE_TYPE_WMF;
		} else if (imgFileName.endsWith(".pict")) {
			format = PICTURE_TYPE_PICT;
		} else if (imgFileName.endsWith(".jpeg") || imgFileName.endsWith(".jpg")) {
			format = PICTURE_TYPE_JPEG;
		} else if (imgFileName.endsWith(".png")) {
			format = PICTURE_TYPE_PNG;
		} else if (imgFileName.endsWith(".dib")) {
			format = PICTURE_TYPE_DIB;
		} else if (imgFileName.endsWith(".gif")) {
			format = PICTURE_TYPE_GIF;
		} else if (imgFileName.endsWith(".tiff")) {
			format = PICTURE_TYPE_TIFF;
		} else if (imgFileName.endsWith(".eps")) {
			format = PICTURE_TYPE_EPS;
		} else if (imgFileName.endsWith(".bmp")) {
			format = PICTURE_TYPE_BMP;
		} else if (imgFileName.endsWith(".wpg")) {
			format = PICTURE_TYPE_WPG;
		} else {
			return 0;
		}
		return format;
	}

}
