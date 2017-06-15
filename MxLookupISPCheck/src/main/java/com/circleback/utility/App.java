package com.circleback.utility;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

        if (args.length != 2) {
            System.out.println("Please run this jar with a TSV file as argument");
            System.out.println("Usage: $  java -jar target/MXLookup-1.0-ALL.jar <inputFile.tsv> <outputFileName>");
            System.exit(1);
        }

        // Input File
        String inputFile = args[0];
        System.out.println("inputFile: " + inputFile );

        // Output file
        String outputFile = args[1];
        System.out.println("outputFile: " + outputFile );


        Utils utils = new Utils();
        utils.parseTSVFile(inputFile, outputFile);

    }
}
