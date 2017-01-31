# OMSSA Parser #

  * [Introduction](#introduction)
  * [Using OMSSA Parser](#using-omssa-parser)
  * [Result Analysis](#result-analysis)
  * [Maven Dependency](#maven-dependency)
  * [Troubleshooting](#troubleshooting)
  * [Screenshot](#screenshot)
  * [Example OMX File](#example-omx-file)

---

**OMSSA Parser Publications:**
  * [Barsnes et al: Proteomics 2009 Jul;9(14):3772-4](http://www.ncbi.nlm.nih.gov/pubmed/19639591).
  * If you use **OMSSA Parser** as part of a paper, please include the reference above.

---

**SearchGUI and PeptideShaker:**
  * To run OMSSA searches we recommend the use of [SearchGUI](http://compomics.github.io/projects/searchgui.html).
  * To visualize and analyze OMSSA results we recommend the use of [PeptideShaker](http://compomics.github.io/projects/peptide-shaker.html).

---

|   |   |   |
| :------------------------- | :---------------: | :--: |
| [![download](https://github.com/compomics/omssa-parser/wiki/images/download_button.png)](http://genesis.ugent.be/maven2/de/proteinms/omxparser/omssa-parser/1.9.0/omssa-parser-1.9.0.zip) | *v1.9.0 - All platforms* | [ReleaseNotes](https://github.com/compomics/omssa-parser/wiki/ReleaseNotes) |

---

## Introduction ##
**OMSSA Parser** is a Java based parser for [OMSSA (Open Mass Spectrometry Search Algorithm)](http://pubchem.ncbi.nlm.nih.gov/omssa/) omx files.

Initially created by Steffen Huber for Prof. Dr. Albert Sickmann under the guidance of Dr. Lennart Martens, and further developed by Harald Barsnes, including the addition of a simple, lightweight and platform independent OMSSA Viewer.

[Go to top of page](#omssa-parser)

---

## Using OMSSA Parser ##

There are basically two ways of using **OMSSA Parser**:

  * Using the [OMSSA Viewer](#omssa-viewer) to visualize the contents of an omx file, including the possibility of exporting parts of the dataset to other file formats.
  * Using the [OMSSA Parser Jar File](#omssa-parser-jar-file) as Java library in your own programs to extract the parts of the dataset you need.

### OMSSA Viewer ###

**To visualize and analyze OMSSA results we now recommend the use of [PeptideShaker](http://peptide-shaker.googlecode.com).**

OMSSA Viewer was mainly created for showing the usefulness of **OMSSA Parser** and as an example of how to use the parser. It displays the spectra file details (spectrum number, filename, charge and m/z-value), the identification details for a selected spectra (peptide sequence, E-value, protein accession number, etc.), and the selected spectrum including ion coverage. All in the same frame. OMSSA Viewer also includes the possibility of exporting the different components of an omx file as tab delimited text files, and all the spectra as dta files. A screenshot of OMSSA Viewer can be found [here](https://github.com/compomics/omssa-parser/wiki/images/omssaviewer.PNG).

Using OMSSA Viewer is straight-forward. Simply download the latest version of the **OMSSA Parser** zip file, unzip the file and double click on the jar file. (If nothing happens, download [Java 1.5](http://java.sun.com/javase/downloads/index.jsp) (or newer) and try again. If this does not fix your problem, see the Troubleshooting section below.) (See also [screenshot](https://github.com/compomics/omssa-parser/wiki/images/omssa-viewer_v1.1.PNG).)

[Go to top of page](#omssa-parser)

### OMSSA Parser Jar File ###
Using **OMSSA Parser** directly requires that you create your own Java project that uses the **OMSSA Parser** jar file as a library.

The library consists of three packages: (i) `de.proteinms.omxparser`, (ii) `de.proteinms.omxparser.util` and (iii) `de.proteinms.omxparser.tools`. The `de.proteinms.omxparser package` only contains the `OmssaOmxFile` class which manages the parsing of the omx file, and provides several methods to easily retrieve the parsed information.

To parse an omx file into an `OmssaOmxFile` object use the class’s constructor with the following input parameters: (i) the omx file, (ii) the OMSSA mods.xml file, and (iii) the OMSSA usermods.xml file. (The latter two are the files where OMSSA stores the properties of the amino acid modifications.) Only the first file is mandatory, but if the modification files are not provided no additional information about the modifications, besides a modification number, can be extracted. The creation of the `OmssaOmxFile` object creates an `OmxParser` object that does the actual parsing of the omx file. Creating an `OmssaOmxFile` also takes care of processing the results and creates several data structures that make it easier to extract commonly used information from the omx file. Among these is the method `getParserResult()`, that returns all data from the original omx file as an `MSSearch` object, and the method `getSpectrumToPeptideMap()`, that returns a HashMap where every spectrum is allocated to its corresponding peptides.

The `OmxParser` class is located in the `de.proteinms.omxparser.util` package along with the other classes needed for the actual parsing. The structure of the **OMSSA Parser** code is closely related to the omx file structure. When using the parser it is therefore recommended to have a copy of [OMSSA.mod.xsd](http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd) available as a reference.

[The example omx file](#example-omx-file) contains excerpts of an omx file. As an example of how to use **OMSSA Parser** we describe the code for extracting the m/z-values of all the spectra in a file (see [example omx file - Spectra](#spectra)):

```java
OmssaOmxFile omxFile = new OmssaOmxFile(“C:\\OMSSA_Files\\BSA.omx”);

HashMap<MSSpectrum, MSHitSet> results = omxFile.getSpectrumToHitSetMap();
Iterator<MSSpectrum> iterator = results.keySet().iterator();

ArrayList<List<Integer>> allMzValues = new ArrayList();

while (iterator.hasNext()) {
    MSSpectrum tempSpectrum = iterator.next();
    allMzValues.add(tempSpectrum.MSSpectrum_mz.MSSpectrum_mz_E);
}
```

The **OMSSA Parser** code uses public fields to move from a tag to its subtags, e.g., `MSSpectrum_mz.MSSpectrum_mz_E` moves from the `MSSpectrum_mz` tag to its subtag `MSSpectrum_mz_E`, and uses linked list whenever a subtag can occur more than once, e.g., `MSSpectrum_mz_E` is a linked list of integer values containing all the m/z-values for a given spectrum.

The last package in **OMSSA Parser** is `de.proteinms.omxparser.tools`, which contains a simple, lightweight viewer for OMSSA omx files called the [OMSSA Viewer](#omssa-viewer).

In addition to the packages detailed above, **OMSSA Parser** also requires the following four libraries: xpp3-1.1.4c.jar (for the XML parsing), utilities-2.9.jar (for the spectrum graph), looks-2.2.0.jar (for the graphical user interface) and log4j-1.2.15.jar (for logging functionality). All of these are available in Maven compliant repositories. They are also included in the **OMSSA Parser** zip file.

#### Modification Details ####
In the omx file the only information present about a given amino acid modification is its location in the peptide and a modification reference number. To extract additional information about a modification, e.g., the type or the modification mass, the two XML files `mods.xml` and `usermods.xml` are needed. These are normally located in the OMSSA installation folder, and if provided allows you to map a given modification reference number to an `OmssaModification` object containing the details about the modification. Use the method `getModifications()` in the `OmssaOmxFile` class to extract this information after parsing an omx file.

#### OMSSA Enumerations ####
The OMSSA OMX file uses several enumerations. When parsing a tag referring to an enumeration an integer ID is return. To map this ID to the corresponding text element, use the OmssaEnumerators class. This class has maps for all enumerations, e.g., MSEnzymes, MSIonType etc. By providing the integer ID the corresponding text element wil be returned.

#### Scaled Values ####
Note that all the m/z and abundance values in the omx file are stored as integers, and needs to be scaled to get the real values. Each spectrum has its own abundance scale, `MSSpectrum_iscale`, while the m/z-scales are given by `MSSearchSettings_scale` and `MSResponse_scale`.

[Go to top of page](#omssa-parser)

---

## Result Analysis ##

To visualize and analyze OMSSA results we recommend the use of [PeptideShaker](http://compomics.github.io/projects/peptide-shaker.html). **PeptideShaker** is a search engine independent platform for visualization of peptide and protein identification results from multiple search engines.

[Go to top of page](#omssa-parser)

## Maven Dependency ##

**OMSSA Parser** is available for use in Maven projects:

```
<dependencies>
    <dependency>
        <groupId>de.proteinms.omxparser</groupId>
        <artifactId>omssa-parser</artifactId>
        <version>X.Y.Z</version>
    </dependency>
</dependencies>
```
```
<repositories>

    <!-- Compomics Genesis Maven 2 repository -->
    <repository>
        <id>genesis-maven2-repository</id>
        <name>Genesis maven2 repository</name>
        <url>http://genesis.UGent.be/maven2</url>
        <layout>default</layout>
    </repository>

    <!-- old EBI repository -->
    <repository>
        <id>ebi-repo</id> 
        <name>The EBI internal repository</name>
        <url>http://www.ebi.ac.uk/~maven/m2repo</url>
    </repository>

    <!-- EBI repository -->
    <repository>
        <id>pst-release</id>
        <name>EBI Nexus Repository</name>
        <url>http://www.ebi.ac.uk/Tools/maven/repos/content/repositories/pst-release</url>
    </repository>

</repositories>
```

Update the version number to latest released version.

[Go to top of page](#omssa-parser)

---

## Troubleshooting ##

### Memory Settings ###
If you are parsing a large omx file (say bigger than 100MB), you'll need to allow for more memory space for the Java Virtual Machine (JVM). You can do this by appending the following start-up arguments to the `java` command:

```java
     java -Xmx999m ...
```

where you can substitute the '999' by the maximum amount of megabytes you want the JVM to access.

When using OMSSA Viewer the memory boundaries can be set it the `JavaOptions.txt` file in the `../Properties` folder using the same syntax as above.

**Note** that most 32 bit platforms will only allow memory allocations up to 1500 megabytes.

### MSBioSeq ###
Note that the OMSSA OMX file may contain references to the Bioseq module/schema FROM NCBI-Sequence. These elements are not currently parsed by **OMSSA Parser**. However, files containing such elements are of course parsed without errors.

[Go to top of page](#omssa-parser)

---

## Screenshot ##

(Click on figure to see the full size version)

[![](https://github.com/compomics/omssa-parser/wiki/images/omssaviewer_small.PNG)](https://github.com/compomics/omssa-parser/wiki/images/omssaviewer.PNG)

[Go to top of page](#omssa-parser)

---

## Example OMX File ##

### Spectra ###

```
<MSSearch ...>
  <MSSearch_request>
    <MSRequest>
      <MSRequest_spectra>
        <MSSpectrumset>
          <MSSpectrum>
            <MSSpectrum_number>0</MSSpectrum_number>
            <MSSpectrum_charge>
              <MSSpectrum_charge_E>1</MSSpectrum_charge_E>
            </MSSpectrum_charge>
            <MSSpectrum_precursormz>815340</MSSpectrum_precursormz>
            <MSSpectrum_mz>
              <MSSpectrum_mz_E>674700</MSSpectrum_mz_E>
              ...
              <MSSpectrum_mz_E>850500</MSSpectrum_mz_E>
            </MSSpectrum_mz>
            <MSSpectrum_abundance>
              <MSSpectrum_abundance_E>149900000</MSSpectrum_abundance_E>
              ...
              <MSSpectrum_abundance_E>119000000</MSSpectrum_abundance_E>
            </MSSpectrum_abundance>
            <MSSpectrum_iscale>100000</MSSpectrum_iscale>
            <MSSpectrum_ids>
              <MSSpectrum_ids_E>LCQ10486.10.10.1.dta</MSSpectrum_ids_E>
            </MSSpectrum_ids>
          </MSSpectrum>
          ...
        </MSSpectrumset>
      </MSRequest_spectra>
      …
    </MSRequest>
  </MSSearch_request>
  …
</MSSearch>
```

[Go to top of page](#omssa-parser)

### Identifications ###

```
<MSSearch ...>
  …
  <MSSearch_response>
    <MSResponse>
      <MSResponse_hitsets>
        <MSHitSet>
          <MSHitSet_number>21</MSHitSet_number>
          <MSHitSet_hits>
            <MSHits>
              <MSHits_evalue>3.95569513003838e-006</MSHits_evalue>
              <MSHits_pvalue>5.34553395951132e-009</MSHits_pvalue>
              <MSHits_charge>2</MSHits_charge>
              <MSHits_pephits>
                <MSPepHit>
                  <MSPepHit_start>528</MSPepHit_start>
                  <MSPepHit_stop>543</MSPepHit_stop>
                  <MSPepHit_gi>1351907</MSPepHit_gi>
                  <MSPepHit_accession>P02769</MSPepHit_accession>
                  <MSPepHit_defline>Serum albumin precursor (Allergen Bos d 6) (BSA)</MSPepHit_defline>
                  <MSPepHit_protlength>607</MSPepHit_protlength>
                  <MSPepHit_oid>1064518</MSPepHit_oid>
                </MSPepHit>
              </MSHits_pephits>
              <MSHits_mzhits>
                <MSMZHit>
                  <MSMZHit_ion>
                    <MSIonType>1</MSIonType>
                  </MSMZHit_ion>
                  <MSMZHit_charge>1</MSMZHit_charge>
                  <MSMZHit_number>4</MSMZHit_number>
                  <MSMZHit_mz>646335</MSMZHit_mz>
                </MSMZHit>
                ...
              </MSHits_mzhits>
              <MSHits_pepstring>LFTFHADICTLPDTEK</MSHits_pepstring>
              <MSHits_mass>1908857</MSHits_mass>
              <MSHits_pepstart>K</MSHits_pepstart>
              <MSHits_pepstop>Q</MSHits_pepstop>
              <MSHits_theomass>1906913</MSHits_theomass>
            </MSHits>
          </MSHitSet_hits>
          <MSHitSet_ids>
            <MSHitSet_ids_E> LCQ10486.10.10.1.dta</MSHitSet_ids_E>
          </MSHitSet_ids>
          <MSHitSet_settingid>0</MSHitSet_settingid>
        </MSHitSet>
        ...
      </MSResponse_hitsets>
      ...
    </MSResponse>
  </MSSearch_response>
</MSSearch>
```

[Go to top of page](#omssa-parser)
