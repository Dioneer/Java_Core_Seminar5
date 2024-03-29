package Pegas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program{
    private static final Random random = new Random();
    private static final int CHAR_BOUND_L=65;
    private static final int CHAR_BOUND_H=90;
    private static final String TO_SEARCH = "GeekBrains";

    public static void main( String[] args ) throws IOException {

        writeFileContents("sample1.txt", 35, 3);
        writeFileContents("sample2.txt", 35, 2);
        concat("sample1.txt", "sample2.txt", "concat.txt");
        System.out.println(searchInFile("sample2.txt",TO_SEARCH));

        int i = 0;
        while ((i=searchInFile("concat.txt", i, TO_SEARCH))>0){
            System.out.printf("File include search word and offcet: %d\n", i);
        }
        String[] fileNames = new String[10];
        for (int j = 0; j < fileNames.length; j++) {
            fileNames[j] = "file"+ j+".txt";
            writeFileContents(fileNames[j], 30, 3);
            System.out.printf("File %s create", fileNames[j]);
        }
        List<String> rez= searchMatch(new File("."), TO_SEARCH);
        for(String s: rez){
            System.out.printf("File %s has your word %s.\t\n", s, TO_SEARCH);
        }
    }
    private static String generateSymbols(int amount){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            stringBuilder.append((char) random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H+1));
        }
        return stringBuilder.toString();
    }
    private static void writeFileContents(String fileName, int length, int words) throws IOException {
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))){
            for (int i = 0; i < words; i++) {
                bos.write(generateSymbols(length).getBytes(StandardCharsets.UTF_8));
                if(random.nextInt(3)==0){
                    bos.write(TO_SEARCH.getBytes(StandardCharsets.UTF_8));
                    bos.write(generateSymbols(length).getBytes(StandardCharsets.UTF_8));
                }
            }
        }
    }
    private static void concat(String file1, String file2, String fileOut) throws IOException {
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileOut))){
            try(BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(file1))){
                byte[] arr = new byte[1024];
                int r;
                while((r=bis1.read(arr))>0){
                    bos.write(arr, 0 , r);
                }
            }
            try(BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(file2))){
                byte[] arr = new byte[1024];
                int r;
                while ((r=bis2.read(arr))>0){
                    bos.write(arr, 0 , r);
                }
            }
        }
    }
    private static int searchInFile(String fileName, String search) throws IOException{
        return searchInFile(fileName, 0, search);
    }
    private static int searchInFile(String fileName, int offset, String search) throws IOException{
        try(BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(fileName))){
            bis2.skipNBytes(offset);
            byte[] arr = search.getBytes();
            int r;
            int i = 0;
            while ((r=bis2.read())!=-1) {
                if(r==arr[i]){
                    i++;
                }else{
                    i=0;
                    if(r==arr[i]) {
                        i++;
                    }
                }
                if(i==arr.length){
                    return offset;
                }
                offset++;
            }
        }
        return -1;
    }
    static List<String> searchMatch(File file, String search) throws IOException {
        List<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        if(files==null)return list;
        for (int i = 0; i < files.length; i++) {
            if(files[i].isFile()){
                if(searchInFile(files[i].getCanonicalPath(), search)>-1){
                    list.add(files[i].getCanonicalPath());
                }
            }
        }
        return list;
    }
}
