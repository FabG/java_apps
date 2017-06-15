package com.circleback.utility;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fab on 6/15/17.
 */
public class Utils {

    public void parseTSVFile(String tsvFile, String outputFile) {
        int emailDomainPosition = 0;
        int headerCount = 0;
        int iter = 0;
        MXLookup mxlookup = new MXLookup();

        try {
            TsvParserSettings settings = new TsvParserSettings();
            TsvParser parser = new TsvParser(settings);

            // parses all rows in one go.
            List<String[]> allTSVRows = parser.parseAll(new FileReader(tsvFile));

            // Find position of "email_domain" in header (1st row)
            String[] headerRow = allTSVRows.get(0);
            headerCount = headerRow.length-1;
            System.out.println("Trying to find the field position of 'email_domain' in the header...");

            for (String headerField : headerRow) {
                //System.out.println(" - position: " + iter + " / header value: " + headerField );
                if (headerField.equals("email_domain")) {
                    emailDomainPosition = iter;
                    System.out.println(" => found email_domain => position: " + emailDomainPosition);
                    break;
                }

                if (iter != headerCount)
                    iter++;
                else {
                    System.out.println("=> Could not find email_domain in header. Please add to file in the header");
                    System.exit(1);
                }
            }

            // Add 2 more headers to the header: is_isp and email_domain_mxtype
            FileWriter writer = new FileWriter(outputFile);
            for (String out : headerRow) {
                writer.write(out+"\t");
            }
            writer.write("is_isp\temail_domain_mxtype");
            writer.write("\n");

            // Load ISP File
            Boolean isISP = true;
            String domain = null;
            String mxType = null;

            mxlookup.loadISPFile("src/main/resources/ISPDomains.tsv");

            // iterate over row #2 till end of file and try each email domain
            System.out.println("Looping through each row and running the email Domain through ISP and MXLookup check...");
            for (String[] row : allTSVRows.subList(1, allTSVRows.size())) {

                List<String> outputList = new ArrayList<String>();
                for (int i = 0; i < row.length; i++) {
                    outputList.add(row[i]);
                }

                domain = row[emailDomainPosition].toString();

                // ISP Check
                isISP = mxlookup.checkISP(domain);

                //MXLookup check
                mxType = mxlookup.checkMXRecords(domain);
                System.out.println("Domain: [" + domain + "] -> mxType: [" + mxType + "] / is ISP: [" + isISP + "]");
                //for (String out : outputList) System.out.println("out: " + out);

                // Add to List
                outputList.add(isISP.toString());
                outputList.add(mxType);

                // write data
                for (String out : outputList) {
                    writer.write(out+"\t");
                }
                writer.write("\n");

            }
            writer.close();


        } catch (IOException e) {
            System.err.println("Error parsing TSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }



}
