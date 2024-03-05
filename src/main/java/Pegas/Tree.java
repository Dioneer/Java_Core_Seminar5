package Pegas;

import java.io.File;
import java.util.Arrays;

public class Tree {
    public static void main(String[] args) {
        print(new File("."),"", true);
    }
    static void print(File file, String ind, boolean isLast){
        System.out.print(ind);
        if(isLast){
            System.out.print("└─");
            ind+="  ";
        }else{
            System.out.print("├─");
            ind+="│ ";
        }
        System.out.println(file.getName());
        File[] files = file.listFiles();
        if(files==null) return;
        int subDirTotal = 0;
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                subDirTotal++;
            }
        }
        int subDirCounter = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                print(files[i], ind, subDirTotal == ++subDirCounter);
            }else{
                print(files[i], ind, true);
            }
        }
    }
}
