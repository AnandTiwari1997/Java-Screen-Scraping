package com.example.templatematch;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.apache.log4j.Logger;
import org.bytedeco.javacpp.opencv_core.Rect;

public class TestTemplateMatch {

	private static final Logger LOGGER = Logger.getLogger(TestTemplateMatch.class);
	private static final double RECORDING_WIDTH = 1360;
	private static final double RECORDING_HEIGHT = 750;
	private static final String TEMPLATE_FILENAME = "src/main/resources/template3.png";

	/**
	 * Main Method.
	 * @param args arguments.
	 */
	public static void main(String[] args) {
		
		Rect rect = null;
		MatchTemplate matchTemplate = new MatchTemplate();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		if (screenSize.getWidth() == RECORDING_WIDTH && screenSize.getHeight() == RECORDING_HEIGHT) {
			rect = matchTemplate.sameScaleTemplateMatch(TEMPLATE_FILENAME);
		} else if(screenSize.getWidth() < RECORDING_WIDTH && screenSize.getHeight() < RECORDING_HEIGHT) {
			rect = matchTemplate.multiScaleTemplateMatchDown(TEMPLATE_FILENAME);
		} else {
			rect = matchTemplate.multiScaleTemplateMatchUp(TEMPLATE_FILENAME);
		}
		
		if ( rect != null) {
			int x = (rect.x() + rect.x() + rect.width()) / 2;
			int y = (rect.y() + rect.y() + rect.height()) / 2;
			
			KeyBoardAndMouseManipulation bot;
			try {
				bot = new KeyBoardAndMouseManipulation();
				bot.rightClick(x, y);
			} catch (AWTException e) {
				LOGGER.error(" Unable to click due to " + e);
			}
		} else {
			LOGGER.error("Unable to find Coordinates.");
		}
 
	}

}
