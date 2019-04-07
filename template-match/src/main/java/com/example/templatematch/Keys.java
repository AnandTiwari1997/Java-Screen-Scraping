package com.example.templatematch;

import java.awt.event.KeyEvent;

public class Keys {

	public static int getKeyCode(char key) {

		int i = 0;
		switch (key) {
		case 'a':
			i = KeyEvent.VK_A;
			break;
		case 'b':
			i = KeyEvent.VK_B;
			break;
		case 'c':
			i = KeyEvent.VK_C;
			break;
		case 'd':
			i = KeyEvent.VK_D;
			break;
		case 'e':
			i = KeyEvent.VK_E;
			break;
		case 'f':
			i = KeyEvent.VK_F;
			break;
		case 'g':
			i = KeyEvent.VK_G;
			break;
		case 'h':
			i = KeyEvent.VK_H;
			break;
		case 'i':
			i = KeyEvent.VK_I;
			break;
		case 'j':
			i = KeyEvent.VK_J;
			break;
		case 'k':
			i = KeyEvent.VK_K;
			break;
		case 'l':
			i = KeyEvent.VK_L;
			break;
		case 'm':
			i = KeyEvent.VK_M;
			break;
		case 'n':
			i = KeyEvent.VK_N;
			break;
		case 'o':
			i = KeyEvent.VK_O;
			break;
		case 'p':
			i = KeyEvent.VK_P;
			break;
		case 'q':
			i = KeyEvent.VK_Q;
			break;
		case 'r':
			i = KeyEvent.VK_R;
			break;
		case 's':
			i = KeyEvent.VK_S;
			break;
		case 't':
			i = KeyEvent.VK_T;
			break;
		case 'u':
			i = KeyEvent.VK_U;
			break;
		case 'v':
			i = KeyEvent.VK_V;
			break;
		case 'w':
			i = KeyEvent.VK_W;
			break;
		case 'x':
			i = KeyEvent.VK_X;
			break;
		case 'y':
			i = KeyEvent.VK_Y;
			break;
		case 'z':
			i = KeyEvent.VK_Z;
			break;

		default:
			break;
		}

		return i;
	}
	
	public static int getKeyCode(String modifier) {
		
		int i = 0;
		
		switch (modifier) {
		case "win":
			i = KeyEvent.VK_WINDOWS;
			break;
		case "shift":
			i = KeyEvent.VK_SHIFT;
			break;
		case "ctrl":
			i = KeyEvent.VK_CONTROL;
			break;
		case "alt":
			i = KeyEvent.VK_ALT;
			break;

		default:
			break;
		}
		
		return i;
	}

}
