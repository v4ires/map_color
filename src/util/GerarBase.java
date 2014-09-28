/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author vinicius
 */
public class GerarBase {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(new File("")));
        while(in.hasNext()){
            String[] strLine = in.nextLine().split(":");
            for(int i = 1 ; i < strLine[0].toCharArray().length; i++){
               
            }
        }
    }
}
