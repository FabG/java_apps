package com.circleback.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Fab on 6/14/17.
 */
public class App {

    public static void main(String args[]) {
        Properties prop = new Properties();
        InputStream config = null;

        try {
            config = new FileInputStream("src/main/resources/config.properties");
            prop.load(config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (args.length != 1) {
            System.out.println("Please run this jar with a TSV file as argument");
            System.out.println("Usage: > $ java -jar target/MXLookup-1.0.jar <inputfile.tsv>");
            System.exit(1);
        }

        String inputFile = args[0];
        System.out.println("inputFile: " + inputFile );


        Utils utils = new Utils();
        utils.parseTSVFile(inputFile);

    }
}
