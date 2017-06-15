MXLookup and ISP Check Utility
================

This jar can be called from the command line and passed as a parameter a TSV file (tab separated) that includes at least one header called "email_domain"
When run, it will detect what position is the "email_domain" and loop through each row to check:
 - is_isp: boolean (true if the email domain is an ISP like hotmail.com, gmail.com,... or false otherwise)
 - email_domain_mxtype: string ("google", "MicrosoftExchange", "MicrosoftOnline" or 'Unknown')
Both flags will be appended at the end of each row and the output file will end up with the same columns + 2 extra
Format of the output file will be as the input file - a tab separated file
 
To Build:
```
$ mvn package 
```

Usage:
```
$  java -jar target/MXLookup-1.0-ALL.jar <inputFile.tsv> <outputFileName>
```