package ca.bjr.main;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.WeakTable;

import ca.bjr.scripts.LuaScript;
import processing.core.PApplet;
import processing.core.PImage;

public class Window {
	
	public int x = 20, y = 20;
	public int w = 240, h = 140;
	public boolean dragging = false;
	public String title = "Window";
	public boolean enableAero = true;
	public int id;
	
	public Window(int x, int y, String title) {
		this.x = x;
		this.y = y;
		this.title = title;
	}
	
	
}
