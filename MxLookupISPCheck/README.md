MXLookup and ISP Check Utility
================

This jar can be called from the command line and passed as a parameter a TSV file (tab separated) that includes at least one header called "email_domain"
When run, it will create a new file "output.tsv" that will include 2 extra headers:
 - is_isp: boolean (true if the email domain is an ISP like hotmail.com, gmail.com,... or false otherwise)
 - email_domain_mxtype: string ("google", "MicrosoftExchange", "MicrosoftOnline" or 'Unknown')
 
To Build:
```
$ mvn package 
```

Usage:
```
$ java -jar target/MXLookup-1.0.jar <inputFile.tsv> <outputFileName>
```