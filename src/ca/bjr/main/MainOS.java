package ca.bjr.main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.luaj.vm2.Varargs;

import ca.bjr.scripts.LuaScript;
import processing.core.PApplet;
import processing.core.PImage;

public class MainOS extends PApplet {
	
	public Varargs lastResults;
	public static boolean mouseClicked = false;
	private int mCT = 0;
	public ArrayList<LuaScript> scripts = new ArrayList<LuaScript>();
	
	public static int dt;
	public static ArrayList<Window> windows = new ArrayList<Window>();
	public void settings() {
		size(800, 470, JAVA2D);
	}
	public void setup() {
		colorMode(RGB);

		loadLuaScripts();
		
		frameRate(144);
	    ///initBlur();
	}
	
	public void loadLuaScripts() {
		try {
			for(File f : new File("res/mods/").listFiles()) {
				//System.out.println("Found " + f.getName() + ", loading it...");
				
				scripts.add(new LuaScript(this, f.getName()));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		hookMethod("initMethod");
	}

	public void draw() {
		if(mousePressed) { mCT++; } else { mCT=0; }
		if(mCT==1) {mouseClicked=true;} else {mouseClicked=false;}
		//loadPixels();

		long allocated = (Runtime.getRuntime().totalMemory() / 1024) / 1024;
		long max = (Runtime.getRuntime().maxMemory() / 1024) / 1024;
		long free = (Runtime.getRuntime().freeMemory() / 1024) / 1024;
		
		surface.setTitle((allocated-free) + "mb / " + allocated + "mb / " + max + "mb");
		

		hookMethod("updateMethod");
		hookMethod("drawMethod");
		
		dt++;
		
	}
	
	public void keyPressed() {
		if(keyCode == KeyEvent.VK_R) {
			/*for(LuaScript ext : scripts) {
				boolean reloaded = ext.reload();
				if(reloaded) {
					System.out.println("Reloaded Lua Script: " + ext.scriptFileName + "!");
				} else {
					System.out.println("Lua Script: " + ext.scriptFileName + " Failed To Reload!");
				}
			}*/
		}
	}
	public static ArrayList<Window> ddf(ArrayList<Window> array, int idB){
	    Window old = array.get(idB);
	    array.remove(idB);
	    array.add(old);
	    return array;
	};
	
	public boolean mouseInArea(int x, int y, int w, int h) {
		if(mouseX >= x && mouseY >= y && mouseX <= x+w && mouseY <= y+h) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		PApplet.main("ca.bjr.main.MainOS");
	}
	
	//LUA METHODS
	public Window createWindow(int x, int y, String title) {
		Window win = new Window(x, y, title);
		return win;
	}

	public void commitWindow(Window w) {
		windows.add(w);
	}
	
	public Window getWindow(int index) {
		return windows.get(index);
	}
	

	public void hookMethod(String string, Object... objects) {
		for(LuaScript ext : scripts) {
			ext.executeFunction(string, objects);
		}
	}
	
	public void fastSmallBlur(PImage a, PImage b){ //a=src, b=dest img
		  int pa[]=a.pixels;
		  int pb[]=b.pixels;
		  int h=a.height;
		  int w=a.width;
		  final int mask=(0xFF&(0xFF<<2))*0x01010101;
		  for(int y=1;y<h-1;y++){ //edge pixels ignored
		    int rowStart=y*w  +1;
		    int rowEnd  =y*w+w-1;
		    for(int i=rowStart;i<rowEnd;i++){
		      pb[i]=(
		        ( (pa[i-w]&mask) // sum of neighbours only, center pixel ignored
		         +(pa[i+w]&mask)
		         +(pa[i-1]&mask)
		         +(pa[i+1]&mask)
		        )>>2)
		        |0xFF000000 //alpha -> opaque
		        ;
		    }
		  }
	}
}
