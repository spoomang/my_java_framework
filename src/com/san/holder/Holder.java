package com.san.holder;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.san.annotations.Controller;
import com.san.annotations.Function;
import com.san.mapping.Mapping;

/**
 * @author sandeeppoomanglaath
 *
 */

public class Holder {
	private static HashMap<String, Mapping> pathToMapping = new HashMap<String, Mapping>();
	private static List<String> srcFiles = new ArrayList<String>();
	private static File file;
	private static Holder holderInstance = new Holder();
	
	private Holder() {
		file = new File("src");
	}
	
	public static Holder getHolderInstance() {
		return holderInstance;
	}
	
	public void scan() {
		addFiles(file, "");
		populatePathToMapping();
	}
	
	private void populatePathToMapping() {
		for (String srcFile : srcFiles) {
			String javaFile = srcFile.substring(0, srcFile.indexOf(".java"));
			
			try {
				Class classInfo = Class.forName(javaFile);
				Controller controller = (Controller)classInfo.getAnnotation(Controller.class);
				
				if(controller != null) {
					String controllerPath = controller.value();

					for (Method method : classInfo.getMethods()) {
						Function methodInfo = method.getAnnotation(Function.class);
						if(methodInfo != null) {
							String methodPath = methodInfo.value();
							
							Constructor constructor = classInfo.getConstructor();
							Object object = constructor.newInstance(); 
							
							pathToMapping.put("/"+controllerPath+"/"+methodPath, new Mapping(method, object));
						}
					}
				}
				
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found Exception");
			} catch (NoSuchMethodException e) {
				System.out.println("No Such Method Exception");
			} catch (SecurityException e) {
				System.out.println("Security Exception");
			} catch (Exception e) {
				System.out.println("InstantiationException ");
			}
		}
	}
	
	public List<String> getFileList() {
		return srcFiles;
	}
	
	public HashMap<String, Mapping> getMapping() {
		return pathToMapping;
	}
	
	static void addFiles(File file, String indent) {
		
		if(file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f: list) {
				addFiles(f, (indent.isBlank()?"":indent+".") + f.getName());
			}
		} else {
			srcFiles.add(indent);
		}
	}
}