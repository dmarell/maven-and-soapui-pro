# soapui-pro

This Maven module packages SoapUI Pro jar files from your local SoapUI Pro installation.

It is created for SoapUI Pro 5.1.2 but can be adapted for another version by changing the pom.xml to reflect
the other versions jar files. There are a large amount of jar files, therefore the utility program  in the source
directory was created, it generates needed entries for pom.xml from whatever version you have installed of SoapUI Pro.
This tool was tested for SoapUI Pro 5.1.2 only.

## Update for new SoapUI Pro version

- Install new SoapUI Pro version locally
- Run the class CreatePomSnippetsFromFileListing
- Paste the snippets into the pom of this module, replacing the corresponsing sections already there

Happy coding,
Daniel Marell