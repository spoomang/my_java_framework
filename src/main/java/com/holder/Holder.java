package com.holder;

import com.annotations.Controller;
import com.annotations.Function;
import com.model.Mapping;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author sandeeppoomanglaath
 */
public class Holder {
    private Holder() {
        file = new File("build/classes/java/main");
    }

    private static Holder holder = new Holder();
    private static List<String> srcFiles = new ArrayList<>();
    private static HashMap<String, Mapping> pathToMapping = new HashMap<>();
    private static File file;

    public static Holder getInstance(){
        return holder;
    }

    public void scan() {
        addFiles(file,"");
        System.out.println(srcFiles);
        populatePathToMapping();
    }

    private void populatePathToMapping() {
        for (String srcFile : srcFiles) {
            String javaFile = srcFile.substring(0, srcFile.indexOf(".class"));
            try {
                Class classInfo = Class.forName(javaFile);
                Controller controller = (Controller)classInfo.getAnnotation(Controller.class);

                if (controller != null) {
                    String controllerPath = controller.value();
                    Constructor constructor = classInfo.getConstructor();
                    Object object = constructor.newInstance();

                    for (Method method : classInfo.getMethods()) {
                        Function methodInfo = method.getAnnotation(Function.class);
                        if(methodInfo != null) {
                            String methodPath = methodInfo.value();
                            pathToMapping.put("/"+controllerPath+"/"+methodPath, new Mapping(method, object));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception thrown");
            }
        }
    }

    private static void addFiles(File file, String indent){
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files){
                addFiles(f, (indent.isEmpty() ? "" : indent+".") + f.getName());
            }
        } else {
            srcFiles.add(indent);
        }
    }

    public List<String> getFileList() {
        return srcFiles;
    }

    public HashMap<String, Mapping> getMapping() {
        return pathToMapping;
    }
}
