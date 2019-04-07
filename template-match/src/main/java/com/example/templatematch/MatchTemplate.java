package com.example.templatematch;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.indexer.FloatIndexer;

public class MatchTemplate {

	private static final Logger LOGGER = Logger.getLogger(MatchTemplate.class);

	public static Map<Float, Point> getPointsFromMatAboveThreshold(Mat m, float t) {
		Map<Float, Point> matches = new HashMap<>();
		FloatIndexer indexer = m.createIndexer();
		float previousT = t;
		for (int y = 0; y < m.rows(); y++) {
			for (int x = 0; x < m.cols(); x++) {
				if (indexer.get(y, x) >= previousT) {
					matches.put(indexer.get(y, x), new Point(x, y));
					previousT = indexer.get(y, x);
				}
			}
		}
		SortedMap<Float, Point> sortedMap = new TreeMap<Float, Point>(new Comparator<Float>() {
			@Override
			public int compare(Float o1, Float o2) {
				return o2.compareTo(o1);
			}

		});
		sortedMap.putAll(matches);
		return sortedMap;
	}
	
	public void click(String templateFile) {
		Rect rect = this.sameScaleTemplateMatch(templateFile);
	}
	
	public Rect sameScaleTemplateMatch(String templateFile) {
		
		String fileName = "C:\\Users\\Anand\\Pictures\\Screenshots\\Screenshot.png";

		
		Mat template = opencv_imgcodecs.imread(templateFile);
		Rect rect = new Rect();

		try {
			
			Robot robot = new Robot();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(screenSize));
			ImageIO.write(bufferedImage, "png", new File(fileName));

			Mat source = opencv_imgcodecs.imread(fileName);
			
			opencv_imgproc.cvtColor(source, source, opencv_imgproc.COLOR_BGR2GRAY);
			opencv_imgproc.cvtColor(template, template, opencv_imgproc.COLOR_BGR2GRAY);

			opencv_imgproc.Canny(source, source, 50, 200);
			opencv_imgproc.Canny(template, template, 50, 200);

			int matchMode = opencv_imgproc.TM_CCOEFF;
			Mat result = new Mat();

			opencv_imgproc.matchTemplate(source, template, result, matchMode);
			opencv_imgproc.threshold(result, result, 0.8, 1, opencv_imgproc.THRESH_TOZERO);
			float threshold = 10000000.0f;
			Map<Float, Point> matches = getPointsFromMatAboveThreshold(result, threshold);

			int occurance = 1;
			int i = 1;

			for (Entry<Float, Point> point : matches.entrySet()) {

				if (occurance == i) {
					LOGGER.info(" Map Value :: " + point.getKey() + " :: ( " + point.getValue().x() + ", "
							+ point.getValue().y() + ")");
					rect.x(point.getValue().x());
					rect.y(point.getValue().y());
					rect.width(template.cols());
					rect.height(template.rows());
				}
				i++;
			}
			opencv_imgproc.rectangle(source, rect, Scalar.WHITE, 2, 8, 0);

			opencv_imgcodecs.imwrite("source.png", source);
			

		} catch (Exception e) {
			if (e instanceof AWTException) {
				LOGGER.error(" Unable to click due to " + e);
			} else {
				LOGGER.error(" Unknown Exception Occured :: " + e);
			}
		} 
		return rect;
	}
	
	public Rect multiScaleTemplateMatchDown(String templateFile) {
		
		String fileName = "C:\\Users\\Anand\\Pictures\\Screenshots\\Screenshot.png";


		Rect rect = new Rect();
		Mat template = opencv_imgcodecs.imread(templateFile);

		try {
			
			opencv_imgproc.cvtColor(template, template, opencv_imgproc.COLOR_BGR2GRAY);
			opencv_imgproc.Canny(template, template, 50, 200);
			
			Robot robot = new Robot();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(screenSize));
			ImageIO.write(bufferedImage, "png", new File(fileName));
			
			Mat source = opencv_imgcodecs.imread(fileName);
			opencv_imgproc.cvtColor(source, source, opencv_imgproc.COLOR_BGR2GRAY);
			List<Object> found = new ArrayList<>();
			
			for (double i = 1.0; i > 0.1; i = i - 0.1) {
				Mat resized = new Mat();
				opencv_imgproc.resize(source, resized, new Size((int) (source.cols() * i), (int) (source.rows() * i)), 0, 0, opencv_imgproc.INTER_AREA);
				int r = source.cols() / resized.cols();
				
				if( resized.cols() < template.cols() && resized.rows() < template.rows() )
					break;

				Mat edge = new Mat();
				opencv_imgproc.Canny(resized, edge, 50, 200);
				
				int matchMode = opencv_imgproc.TM_CCOEFF;
				Mat result = new Mat();

				opencv_imgproc.matchTemplate(edge, template, result, matchMode);
				
				float threshold = 4000000.0f;
				Map<Float, Point> matches = getPointsFromMatAboveThreshold(result, threshold);
				
				Rect newRect = new Rect();
				int occurance = 1;
				int j = 1;
				Float maxVal = 0.0f;

				for (Entry<Float, Point> point : matches.entrySet()) {
					
					if (occurance == j) {
						maxVal = point.getKey();
						LOGGER.info(" Map Value :: " + point.getKey() + " :: ( " + point.getValue().x() + ", "
								+ point.getValue().y() + ")");
						newRect.x(point.getValue().x());
						newRect.y(point.getValue().y());
						newRect.width(template.cols());
						newRect.height(template.rows());
					}
					j++;
				}
				
				opencv_imgproc.rectangle(resized, newRect, Scalar.WHITE, 2, 8, 0);
				opencv_imgcodecs.imwrite("source_" + i + ".png", resized);
				
				if (found.isEmpty() || (Float) found.get(0) < maxVal) {
					found = new ArrayList<>();
					found.add(maxVal);
					found.add(newRect);
					found.add(r);
				}
				
			}

			rect = (Rect) found.get(1);
			int r = (int) found.get(2);
			
			LOGGER.info((Float)found.get(0));
			
			rect.x(rect.x() * r);
			rect.y(rect.y() * r);
			rect.width(template.cols() * r);
			rect.height(template.rows() * r);			

			opencv_imgproc.rectangle(source, rect, Scalar.WHITE, 2, 8, 0);

			opencv_imgcodecs.imwrite("source.png", source);
			

		} catch (Exception e) {
			if (e instanceof AWTException) {
				LOGGER.error(" Unable to click due to " + e);
			} else {
				LOGGER.error(" Unknown Exception Occured :: " + e);
			}
			e.printStackTrace();
		} 
		return rect;
	}

}
