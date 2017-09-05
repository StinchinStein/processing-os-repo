package ca.bjr.scripts;

public interface Script {
	  boolean canExecute();
	  boolean executeInit(Object... objects);
	  boolean executeFunction(String functionName, Object... objects);
	  boolean reload();
}