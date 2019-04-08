package com.example.templatematch;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * KeyBoardAndMouseManipulation class used for keyboard and mouse interactions.
 * @author Anand Tiwari
 *
 */
public class KeyBoardAndMouseManipulation {
	
	private Robot bot = null;
	
	public KeyBoardAndMouseManipulation() throws AWTException {
		bot = new Robot();
	}
	
	/**
	 * Method for double clicking at the passed points.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void doubleClick(int x, int y) {
	    bot.mouseMove(x, y);
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	/**
	 * Method for putting delay between actions.
	 * @param time time of the delay will be applied.
	 */
	public void delay(int time) {
		bot.delay(time);
	}
	
	/**
	 * Method for left clicking at given point.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void leftClick(int x, int y) {
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);		
	}
	
	/**
	 * Method for right clicking at the passed points.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void rightClick(int x, int y) {
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);		
	}
	
	/**
	 * Method for typing the key at focused point.
	 * @param key key to type.
	 */
	public void type(String key) {
		for (char c : key.toCharArray()) {
			int code = Keys.getKeyCode(c);
			bot.keyPress(code);
			bot.keyRelease(code);
		}
	}
	
	/**
	 * Method for typing key and modifier simultaneously.
	 * @param key key to type.
	 * @param modifier modifier to type.
	 */
	public void type(String key, String modifier) {
		int i = Keys.getKeyCode(modifier);
		bot.keyPress(i);
		for (char c : key.toCharArray()) {
			int j = Keys.getKeyCode(c);
			bot.keyPress(j);
			bot.keyRelease(j);
		}
		bot.keyRelease(i);
	}
	
	/**
	 * Method for typing a string at given location / coordinates.
	 * @param x x-coordinate.
	 * @param y y-coordinate.
	 * @param keys key to type.
	 */
	public void type(int x, int y, String keys) {
		this.leftClick(x, y);
		this.delay(1000);
		this.type(keys);
	}

}
