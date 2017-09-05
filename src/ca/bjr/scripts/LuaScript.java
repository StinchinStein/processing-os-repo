package ca.bjr.scripts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import ca.bjr.main.MainOS;

public class LuaScript implements Script {
	 
    private Globals globals = JsePlatform.standardGlobals();
    private LuaValue chunk;
 
    private boolean scriptFileExists;
    
    public String scriptFileName;
    
    public Varargs lastResults;
 
    public LuaScript(MainOS main, String scriptFileName) {
		LuaValue javaEngine = CoerceJavaToLua.coerce(main);
    	globals.set("javaEngine", javaEngine);
    	this.scriptFileName = scriptFileName;
    	this.scriptFileExists = false;
    	this.load(scriptFileName);
    }
    
    public boolean load(String scriptFileName) {
    	this.scriptFileName = scriptFileName;
 
        try {
        	chunk = globals.loadfile("res/mods/" + scriptFileName);
    		this.scriptFileExists = true;
        } catch (Exception e) {
        	e.printStackTrace();
    		this.scriptFileExists = false;
    		return false;
    	}
        
        chunk.call();
        
        return true;
    }
    
    public boolean reload() {
    	return this.load(this.scriptFileName);
    }
 
    public boolean canExecute() {
        return scriptFileExists;
    }
 
    public boolean executeInit(Object... objects) {
    	return executeFunction("init", objects);
    }
    
    public boolean executeFunction(String functionName, Object... objects) {
    	return executeFunctionParamsAsArray(functionName, objects);
    }
    
    public boolean executeFunctionParamsAsArray(String functionName, Object[] objects) {
        if (!canExecute()) {
            return false;
        }
 
        LuaValue luaFunction = globals.get(functionName);
        
        if (luaFunction.isfunction()) {
        	LuaValue[] parameters = new LuaValue[objects.length];
        	
        	int i = 0;
        	for (Object object : objects) {
        		parameters[i] = CoerceJavaToLua.coerce(object);
        		i++;
        	}
        	try {
        		lastResults = luaFunction.invoke(parameters);
        	} catch (LuaError e) {
        		return false;
        	}
        	return true;
        }
        return false;
    }
 
    public void registerJavaFunction(TwoArgFunction javaFunction) {
    	globals.load(javaFunction);
    }
}