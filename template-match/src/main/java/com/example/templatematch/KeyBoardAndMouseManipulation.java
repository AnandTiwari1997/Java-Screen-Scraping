package com.example.templatematch;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class KeyBoardAndMouseManipulation {
	
	private Robot bot = null;
	
	public KeyBoardAndMouseManipulation() throws AWTException {
		bot = new Robot();
	}
	
	public void doubleClick(int x, int y) {
	    bot.mouseMove(x, y);
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void delay(int time) {
		bot.delay(time);
	}
	
	public void leftClick(int x, int y) {
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);		
	}
	
	public void rightClick(int x, int y) {
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);		
	}
	
	public void type(String key) {
		for (char c : key.toCharArray()) {
			int code = Keys.getKeyCode(c);
			bot.keyPress(code);
			bot.keyRelease(code);
		}
	}
	
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
	
	public void type(int x, int y, String keys) {
		this.leftClick(x, y);
		this.delay(1000);
		this.type(keys);
	}

}
