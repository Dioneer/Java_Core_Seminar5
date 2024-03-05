package Pegas;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Homework {
    private final static Map<String, String> pathsAndNames = new HashMap<>();
    private final static String STR = "/backup";
    //путь к корню
    private final static Path PATH = FileSystems.getDefault().getPath(".");

    public static void main(String[] args) throws IOException {
        //коллекция путей всех файлов
        collectAllPaths(new File(PATH +"/src"), pathsAndNames);
        //путь для бэкапа
        String backUpPath = mkDir(PATH+STR);
        //создание бэкапа
        mkBackUp(pathsAndNames, backUpPath);
        System.out.println(backUpPath);
    }
    public static String mkDir(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path;
    }
    public static  Map<String, String> collectAllPaths(File root, Map<String, String> arr){
        if(root.isDirectory()){
            File[] directoryFiles = root.listFiles();
            if(directoryFiles!=null){
                for(File file : directoryFiles){
                    if(file.isDirectory()){
                        collectAllPaths(file, arr);
                    }else{
                        arr.put(file.getPath(), file.getName());
                    }
                }
            }
        }
        return arr;
    }
    public static void mkBackUp(Map<String, String> arr, String path) throws IOException {
        for (Map.Entry<String, String> i : arr.entrySet()) {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(i.getKey()));
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path+"/"+i.getValue()))) {
                    byte[] b = new byte[1024];
                    int l;
                    while ((l=bis.read(b))>0){
                        bos.write(b, 0, l);
                    }
            }
        }
    }
}
