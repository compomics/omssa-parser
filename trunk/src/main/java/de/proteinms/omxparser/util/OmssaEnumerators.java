package de.proteinms.omxparser.util;

import java.util.HashMap;

/**
 * Contains mappings for all the enumerators used in the OMSSA.mod.xsd file. <br>
 * <b>Mapping updated July 16th 2010.</b>
 * <br><br>
 * Please see "OMSSA.mod.xsd" for further information:
 * <br><br>
 * See <a href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
 *
 * @author Steffen Huber
 * @author Harald Barsnes
 */
public class OmssaEnumerators {

    /**
     * A mapping of the MSEnzymes integer IDs to text description.
     */
    private static HashMap<Integer, String> MSEnzymes = new HashMap<Integer, String>();
    /**
     * A mapping of the MSMod integer IDs to text description.
     */
    private static HashMap<Integer, String> MSMod = new HashMap<Integer, String>();
    /**
     * A mapping of the MSModType integer IDs to text description.
     */
    private static HashMap<Integer, String> MSModType = new HashMap<Integer, String>();
    /**
     * A mapping of the MSSearchType integer IDs to text description.
     */
    private static HashMap<Integer, String> MSSearchType = new HashMap<Integer, String>();
    /**
     * A mapping of the MSZDependence integer IDs to text description.
     */
    private static HashMap<Integer, String> MSZDependence = new HashMap<Integer, String>();
    /**
     * A mapping of the MSSpectrumFileType integer IDs to text description.
     */
    private static HashMap<Integer, String> MSSpectrumFileType = new HashMap<Integer, String>();
    /**
     * A mapping of the MSSerialDataFormat integer IDs to text description.
     */
    private static HashMap<Integer, String> MSSerialDataFormat = new HashMap<Integer, String>();
    /**
     * A mapping of the MSIonType integer IDs to text description.
     */
    private static HashMap<Integer, String> MSIonType = new HashMap<Integer, String>();
    /**
     * A mapping of the MSIonIsotopicType integer IDs to text description.
     */
    private static HashMap<Integer, String> MSIonIsotopicType = new HashMap<Integer, String>();
    /**
     * A mapping of the MSHitError integer IDs to text description.
     */
    private static HashMap<Integer, String> MSHitError = new HashMap<Integer, String>();
    /**
     * A mapping of the MSUserAnnot integer IDs to text description.
     */
    private static HashMap<Integer, String> MSUserAnnot = new HashMap<Integer, String>();
    /**
     * A mapping of the MSResponseError integer IDs to text description.
     */
    private static HashMap<Integer, String> MSResponseError = new HashMap<Integer, String>();
    /**
     * A mapping of the MSIonNeutralLoss integer IDs to text description.
     */
    private static HashMap<Integer, String> MSIonNeutralLoss = new HashMap<Integer, String>();

    static {

        // MSEnzymes
        int index = 0;
        MSEnzymes.put(new Integer(index++), "trypsin");
        MSEnzymes.put(new Integer(index++), "argc");
        MSEnzymes.put(new Integer(index++), "cnbr");
        MSEnzymes.put(new Integer(index++), "chymotrypsin");
        MSEnzymes.put(new Integer(index++), "formicacid");
        MSEnzymes.put(new Integer(index++), "lysc");
        MSEnzymes.put(new Integer(index++), "lysc-p");
        MSEnzymes.put(new Integer(index++), "pepsin-a");
        MSEnzymes.put(new Integer(index++), "tryp-cnbr");
        MSEnzymes.put(new Integer(index++), "tryp-chymo");
        MSEnzymes.put(new Integer(index++), "trypsin-p");
        MSEnzymes.put(new Integer(index++), "whole-protein");
        MSEnzymes.put(new Integer(index++), "aspn");
        MSEnzymes.put(new Integer(index++), "gluc");
        MSEnzymes.put(new Integer(index++), "aspngluc");
        MSEnzymes.put(new Integer(index++), "top-down");
        MSEnzymes.put(new Integer(index++), "semi-tryptic");
        MSEnzymes.put(new Integer(index++), "no-enzyme");
        MSEnzymes.put(new Integer(index++), "chymotrypsin-p");
        MSEnzymes.put(new Integer(index++), "aspn-de");
        MSEnzymes.put(new Integer(index++), "gluc-de");
        MSEnzymes.put(new Integer(index++), "lysn");
        MSEnzymes.put(new Integer(index++), "thermolysin-p");
        MSEnzymes.put(new Integer(index++), "semi-chymotrypsin");
        MSEnzymes.put(new Integer(index++), "semi-gluc");
        MSEnzymes.put(new Integer(index++), "max");
        MSEnzymes.put(new Integer(255), "none");


        // MSMod
        index = 0;
        MSMod.put(new Integer(index++), "methylk - methylation of K");
        MSMod.put(new Integer(index++), "oxym - oxidation of methionine");
        MSMod.put(new Integer(index++), "carboxymethylc - carboxymethyl cysteine");
        MSMod.put(new Integer(index++), "carbamidomethylc - deamidation of K and Q");
        MSMod.put(new Integer(index++), "deamidationkq - propionamide cysteine");
        MSMod.put(new Integer(index++), "propionamidec - phosphorylation of S");
        MSMod.put(new Integer(index++), "phosphorylations - phosphorylation of T");
        MSMod.put(new Integer(index++), "phosphorylationt - phosphorylation of Y");
        MSMod.put(new Integer(index++), "phosphorylationy - N terminal methionine cleavage");
        MSMod.put(new Integer(index++), "ntermmcleave - N terminal methionine cleavage");
        MSMod.put(new Integer(index++), "ntermacetyl - N terminal protein acetyl");
        MSMod.put(new Integer(index++), "ntermmethyl - N terminal protein methyl");
        MSMod.put(new Integer(index++), "ntermtrimethyl - N terminal protein trimethyl");
        MSMod.put(new Integer(index++), "methythiold - beta methythiolation of D");
        MSMod.put(new Integer(index++), "methylq - methylation of Q");
        MSMod.put(new Integer(index++), "trimethylk - trimethylation of K");
        MSMod.put(new Integer(index++), "methyld - methylation of D");
        MSMod.put(new Integer(index++), "methyle - methylation of E");
        MSMod.put(new Integer(index++), "ctermpepmethyl - C terminal methylation");
        MSMod.put(new Integer(index++), "trideuteromethyld - trideuteromethylation of D");
        MSMod.put(new Integer(index++), "trideuteromethyle - trideuteromethylation of E");
        MSMod.put(new Integer(index++), "ctermpeptrideuteromethyl - C terminal trideuteromethylation");
        MSMod.put(new Integer(index++), "nformylmet");
        MSMod.put(new Integer(index++), "twoamino3oxobutanoicacid");
        MSMod.put(new Integer(index++), "acetylk");
        MSMod.put(new Integer(index++), "ctermamide");
        MSMod.put(new Integer(index++), "bmethylthiold");
        MSMod.put(new Integer(index++), "carbamidomethylk");
        MSMod.put(new Integer(index++), "carbamidometylh");
        MSMod.put(new Integer(index++), "carbamidomethyld");
        MSMod.put(new Integer(index++), "carbamidomethyle");
        MSMod.put(new Integer(index++), "carbamylk");
        MSMod.put(new Integer(index++), "ntermcarbamyl");
        MSMod.put(new Integer(index++), "citrullinationr");
        MSMod.put(new Integer(index++), "cysteicacidc");
        MSMod.put(new Integer(index++), "diiodinationy");
        MSMod.put(new Integer(index++), "dimethylk");
        MSMod.put(new Integer(index++), "dimethylr");
        MSMod.put(new Integer(index++), "ntermpepdimethyl");
        MSMod.put(new Integer(index++), "dihydroxyf");
        MSMod.put(new Integer(index++), "thioacetylk");
        MSMod.put(new Integer(index++), "ntermpeptioacetyl");
        MSMod.put(new Integer(index++), "farnesylationc");
        MSMod.put(new Integer(index++), "formylk");
        MSMod.put(new Integer(index++), "ntermpepformyl");
        MSMod.put(new Integer(index++), "formylkynureninw");
        MSMod.put(new Integer(index++), "phef");
        MSMod.put(new Integer(index++), "gammacarboxyld");
        MSMod.put(new Integer(index++), "gammacarboxyle");
        MSMod.put(new Integer(index++), "geranylgeranylc");
        MSMod.put(new Integer(index++), "ntermpepglucuronylg");
        MSMod.put(new Integer(index++), "glutathionec");
        MSMod.put(new Integer(index++), "glyglyk");
        MSMod.put(new Integer(index++), "guanidinationk");
        MSMod.put(new Integer(index++), "his2asnh");
        MSMod.put(new Integer(index++), "his2asph");
        MSMod.put(new Integer(index++), "ctermpephsem");
        MSMod.put(new Integer(index++), "ctermpephselactm");
        MSMod.put(new Integer(index++), "hydroxykynureninw");
        MSMod.put(new Integer(index++), "hydroxylationd");
        MSMod.put(new Integer(index++), "hydroxylationk");
        MSMod.put(new Integer(index++), "hydroxylationn");
        MSMod.put(new Integer(index++), "hydroxylationp");
        MSMod.put(new Integer(index++), "hydroxylationf");
        MSMod.put(new Integer(index++), "hydroxylationy");
        MSMod.put(new Integer(index++), "iodinationy");
        MSMod.put(new Integer(index++), "kynureninw");
        MSMod.put(new Integer(index++), "lipoylk");
        MSMod.put(new Integer(index++), "ctermpepmeester");
        MSMod.put(new Integer(index++), "meesterd");
        MSMod.put(new Integer(index++), "meestere");
        MSMod.put(new Integer(index++), "meesters");
        MSMod.put(new Integer(index++), "meestery");
        MSMod.put(new Integer(index++), "methylc");
        MSMod.put(new Integer(index++), "methylh");
        MSMod.put(new Integer(index++), "methyln");
        MSMod.put(new Integer(index++), "ntermpepmethyl");
        MSMod.put(new Integer(index++), "methylr");
        MSMod.put(new Integer(index++), "ntermpepmyristoyeylationg");
        MSMod.put(new Integer(index++), "ntermpepmyristoyl4hg");
        MSMod.put(new Integer(index++), "ntermpepmyristoylationg");
        MSMod.put(new Integer(index++), "myristoylationk");
        MSMod.put(new Integer(index++), "ntermformyl");
        MSMod.put(new Integer(index++), "nemc");
        MSMod.put(new Integer(index++), "nipcam");
        MSMod.put(new Integer(index++), "nitrow");
        MSMod.put(new Integer(index++), "nitroy");
        MSMod.put(new Integer(index++), "ctermpepo18");
        MSMod.put(new Integer(index++), "ctermpepdio18");
        MSMod.put(new Integer(index++), "oxyh");
        MSMod.put(new Integer(index++), "oxyw");
        MSMod.put(new Integer(index++), "ppantetheines");
        MSMod.put(new Integer(index++), "palmitoylationc");
        MSMod.put(new Integer(index++), "palmitoylationk");
        MSMod.put(new Integer(index++), "palmitoylations");
        MSMod.put(new Integer(index++), "palmitoylationt");
        MSMod.put(new Integer(index++), "phospholosss");
        MSMod.put(new Integer(index++), "phospholosst");
        MSMod.put(new Integer(index++), "phospholossy");
        MSMod.put(new Integer(index++), "phosphoneutrallossc");
        MSMod.put(new Integer(index++), "phosphoneutrallossd");
        MSMod.put(new Integer(index++), "phosphoneutrallossh");
        MSMod.put(new Integer(index++), "propionylk");
        MSMod.put(new Integer(index++), "ntermpeppropionyl");
        MSMod.put(new Integer(index++), "propionylheavyk");
        MSMod.put(new Integer(index++), "ntermpeppropionylheavy");
        MSMod.put(new Integer(index++), "pyridylk");
        MSMod.put(new Integer(index++), "ntermpeppyridyl");
        MSMod.put(new Integer(index++), "ntermpeppyrocmc");
        MSMod.put(new Integer(index++), "ntermpeppyroe");
        MSMod.put(new Integer(index++), "ntermpeppyroq");
        MSMod.put(new Integer(index++), "pyroglutamicp");
        MSMod.put(new Integer(index++), "spyridylethylc");
        MSMod.put(new Integer(index++), "semetm");
        MSMod.put(new Integer(index++), "sulfationy");
        MSMod.put(new Integer(index++), "suphonem");
        MSMod.put(new Integer(index++), "triiodinationy");
        MSMod.put(new Integer(index++), "trimethylationr");
        MSMod.put(new Integer(index++), "ntermpeptripalmitatec");
        MSMod.put(new Integer(index++), "usermod1 - start of user defined mods");
        MSMod.put(new Integer(index++), "usermod2");
        MSMod.put(new Integer(index++), "usermod3");
        MSMod.put(new Integer(index++), "usermod4");
        MSMod.put(new Integer(index++), "usermod5");
        MSMod.put(new Integer(index++), "usermod6");
        MSMod.put(new Integer(index++), "usermod7");
        MSMod.put(new Integer(index++), "usermod8");
        MSMod.put(new Integer(index++), "usermod9");
        MSMod.put(new Integer(index++), "usermod10 - end of user defined mods");
        MSMod.put(new Integer(index++), "icatlight");
        MSMod.put(new Integer(index++), "icatheavy");
        MSMod.put(new Integer(index++), "camthiopropanoylk");
        MSMod.put(new Integer(index++), "phosphoneutrallosss");
        MSMod.put(new Integer(index++), "phosphoneutrallosst");
        MSMod.put(new Integer(index++), "phosphoetdlosss");
        MSMod.put(new Integer(index++), "phosphoetdlosst");
        MSMod.put(new Integer(index++), "arg-13c6");
        MSMod.put(new Integer(index++), "arg-13c6-15n4");
        MSMod.put(new Integer(index++), "lys-13c6");
        MSMod.put(new Integer(index++), "oxy18");
        MSMod.put(new Integer(index++), "beta-elim-s");
        MSMod.put(new Integer(index++), "beta-elim-t");
        MSMod.put(new Integer(index++), "usermod11");
        MSMod.put(new Integer(index++), "usermod12");
        MSMod.put(new Integer(index++), "usermod13");
        MSMod.put(new Integer(index++), "usermod14");
        MSMod.put(new Integer(index++), "usermod15");
        MSMod.put(new Integer(index++), "usermod16");
        MSMod.put(new Integer(index++), "usermod17");
        MSMod.put(new Integer(index++), "usermod18");
        MSMod.put(new Integer(index++), "usermod19");
        MSMod.put(new Integer(index++), "usermod20");
        MSMod.put(new Integer(index++), "usermod21");
        MSMod.put(new Integer(index++), "usermod22");
        MSMod.put(new Integer(index++), "usermod23");
        MSMod.put(new Integer(index++), "usermod24");
        MSMod.put(new Integer(index++), "usermod25");
        MSMod.put(new Integer(index++), "usermod26");
        MSMod.put(new Integer(index++), "usermod27");
        MSMod.put(new Integer(index++), "usermod28");
        MSMod.put(new Integer(index++), "usermod29");
        MSMod.put(new Integer(index++), "usermod30");
        MSMod.put(new Integer(index++), "sulfinicacid");
        MSMod.put(new Integer(index++), "arg2orn");
        MSMod.put(new Integer(index++), "dehydro");
        MSMod.put(new Integer(index++), "carboxykynurenin");
        MSMod.put(new Integer(index++), "sumoylation");
        MSMod.put(new Integer(index++), "iTRAQ114nterm");
        MSMod.put(new Integer(index++), "iTRAQ114K");
        MSMod.put(new Integer(index++), "iTRAQ114Y");
        MSMod.put(new Integer(index++), "iTRAQ115nterm");
        MSMod.put(new Integer(index++), "iTRAQ115K");
        MSMod.put(new Integer(index++), "iTRAQ115Y");
        MSMod.put(new Integer(index++), "iTRAQ116nterm");
        MSMod.put(new Integer(index++), "iTRAQ116K");
        MSMod.put(new Integer(index++), "iTRAQ116Y");
        MSMod.put(new Integer(index++), "iTRAQ117nterm");
        MSMod.put(new Integer(index++), "iTRAQ117K");
        MSMod.put(new Integer(index++), "iTRAQ117Y");
        MSMod.put(new Integer(index++), "mmts");
        MSMod.put(new Integer(index++), "lys-2H4");
        MSMod.put(new Integer(index++), "lys-13C615N2");
        MSMod.put(new Integer(index++), "hexNAcN");
        MSMod.put(new Integer(index++), "dHexHexNAcN");
        MSMod.put(new Integer(index++), "hexNAcS");
        MSMod.put(new Integer(index++), "hexNAcT");
        MSMod.put(new Integer(index++), "mod186");
        MSMod.put(new Integer(index++), "mod187");
        MSMod.put(new Integer(index++), "mod188");
        MSMod.put(new Integer(index++), "mod189");
        MSMod.put(new Integer(index++), "mod190");
        MSMod.put(new Integer(index++), "mod191");
        MSMod.put(new Integer(index++), "mod192");
        MSMod.put(new Integer(index++), "mod193");
        MSMod.put(new Integer(index++), "mod194");
        MSMod.put(new Integer(index++), "mod195");
        MSMod.put(new Integer(index++), "mod196");
        MSMod.put(new Integer(index++), "mod197");
        MSMod.put(new Integer(index++), "mod198");
        MSMod.put(new Integer(index++), "mod199");
        MSMod.put(new Integer(index++), "mod200");
        MSMod.put(new Integer(index++), "mod201");
        MSMod.put(new Integer(index++), "mod202");
        MSMod.put(new Integer(index++), "mod203");
        MSMod.put(new Integer(index++), "mod204");
        MSMod.put(new Integer(index++), "mod205");
        MSMod.put(new Integer(index++), "mod206");
        MSMod.put(new Integer(index++), "mod207");
        MSMod.put(new Integer(index++), "mod208");
        MSMod.put(new Integer(index++), "mod209");
        MSMod.put(new Integer(index++), "mod210");
        MSMod.put(new Integer(index++), "mod211");
        MSMod.put(new Integer(index++), "mod212");
        MSMod.put(new Integer(index++), "mod213");
        MSMod.put(new Integer(index++), "mod214");
        MSMod.put(new Integer(index++), "mod215");
        MSMod.put(new Integer(index++), "mod216");
        MSMod.put(new Integer(index++), "mod217");
        MSMod.put(new Integer(index++), "mod218");
        MSMod.put(new Integer(index++), "mod219");
        MSMod.put(new Integer(index++), "mod220");
        MSMod.put(new Integer(index++), "mod221");
        MSMod.put(new Integer(index++), "mod222");
        MSMod.put(new Integer(index++), "mod223");
        MSMod.put(new Integer(index++), "mod224");
        MSMod.put(new Integer(index++), "mod225");
        MSMod.put(new Integer(index++), "mod226");
        MSMod.put(new Integer(index++), "mod227");
        MSMod.put(new Integer(index++), "mod228");
        MSMod.put(new Integer(index++), "mod229");
        MSMod.put(new Integer(index++), "mod230");
        MSMod.put(new Integer(index++), "max - maximum number of mods");
        MSMod.put(new Integer(9999), "unknown - modification of unknown type");
        MSMod.put(new Integer(10000), "none");


        // MSModType
        index = 0;
        MSModType.put(new Integer(index++), "modaa - at particular amino acids");
        MSModType.put(new Integer(index++), "modn - at the N terminus of a protein");
        MSModType.put(new Integer(index++), "modnaa - at the N terminus of a protein at particular amino acids");
        MSModType.put(new Integer(index++), "modc - at the C terminus of a protein");
        MSModType.put(new Integer(index++), "modcaa - at the C terminus of a protein at particular amino acids");
        MSModType.put(new Integer(index++), "modnp - at the N terminus of a peptide");
        MSModType.put(new Integer(index++), "modnpaa - at the N terminus of a peptide at particular amino acids");
        MSModType.put(new Integer(index++), "modcp - at the C terminus of a peptide");
        MSModType.put(new Integer(index++), "modcpaa - at the C terminus of a peptide at particular amino acids");
        MSModType.put(new Integer(index++), "modmax - the max number of modification types");


        // MSSearchType
        index = 0;
        MSSearchType.put(new Integer(index++), "monoisotopic");
        MSSearchType.put(new Integer(index++), "average");
        MSSearchType.put(new Integer(index++), "monon15");
        MSSearchType.put(new Integer(index++), "exact");
        MSSearchType.put(new Integer(index++), "multiisotope");
        MSSearchType.put(new Integer(index++), "max");


        // MSZDependence
        index = 0;
        MSZDependence.put(new Integer(index++), "independent - mass tol. invariant with charge");
        MSZDependence.put(new Integer(index++), "linearwithz - mass tol. scales with charge");
        MSZDependence.put(new Integer(index++), "max");


        // MSSpectrumFileType
        index = 0;
        MSSpectrumFileType.put(new Integer(index++), "dta");
        MSSpectrumFileType.put(new Integer(index++), "dtablank");
        MSSpectrumFileType.put(new Integer(index++), "dtaxml");
        MSSpectrumFileType.put(new Integer(index++), "asc");
        MSSpectrumFileType.put(new Integer(index++), "pkl");
        MSSpectrumFileType.put(new Integer(index++), "pks");
        MSSpectrumFileType.put(new Integer(index++), "sciex");
        MSSpectrumFileType.put(new Integer(index++), "mgf");
        MSSpectrumFileType.put(new Integer(index++), "unknown");
        MSSpectrumFileType.put(new Integer(index++), "oms - asn.1 binary for iterative search");
        MSSpectrumFileType.put(new Integer(index++), "omx - xml for iterative search");
        MSSpectrumFileType.put(new Integer(index++), "xml - xml MSRequest");
        MSSpectrumFileType.put(new Integer(index++), "omxbz - bzip2 omx file2");


        // MSSerialDataFormat
        index = 0;
        MSSerialDataFormat.put(new Integer(index++), "none");
        MSSerialDataFormat.put(new Integer(index++), "asntext - open ASN.1 text format");
        MSSerialDataFormat.put(new Integer(index++), "asnbinary - open ASN.1 binary format");
        MSSerialDataFormat.put(new Integer(index++), "xml - open XML format");
        MSSerialDataFormat.put(new Integer(index++), "csv - csv (excel)");
        MSSerialDataFormat.put(new Integer(index++), "pepxml - pepXML format");
        MSSerialDataFormat.put(new Integer(index++), "xmlbz2 - bzip2 XML format");


        // MSIonType
        index = 0;
        MSIonType.put(new Integer(index++), "a");
        MSIonType.put(new Integer(index++), "b");
        MSIonType.put(new Integer(index++), "c");
        MSIonType.put(new Integer(index++), "x");
        MSIonType.put(new Integer(index++), "y");
        MSIonType.put(new Integer(index++), "z");
        MSIonType.put(new Integer(index++), "parent");
        MSIonType.put(new Integer(index++), "internal");
        MSIonType.put(new Integer(index++), "immonium");
        MSIonType.put(new Integer(index++), "unknown");
        MSIonType.put(new Integer(index++), "adot");
        MSIonType.put(new Integer(index++), "x-CO2");
        MSIonType.put(new Integer(index++), "adot-CO2");
        MSIonType.put(new Integer(index++), "max");


        // MSIonIsotopicType
        index = 0;
        MSIonIsotopicType.put(new Integer(index++), "monoisotopic - no c13s in molecule");
        MSIonIsotopicType.put(new Integer(index++), "c13 - one c13 in molecule");
        MSIonIsotopicType.put(new Integer(index++), "c13two - two c13s in molecule");
        MSIonIsotopicType.put(new Integer(index++), "c13three - three c13s in molecule");
        MSIonIsotopicType.put(new Integer(index++), "c13four - four c13s in molecule");


        // MSHitError
        index = 0;
        MSHitError.put(new Integer(index++), "none");
        MSHitError.put(new Integer(index++), "generalerr");
        MSHitError.put(new Integer(index++), "unable2read - can't read the spectrum");
        MSHitError.put(new Integer(index++), "notenuffpeaks - not enough peaks to search");


        // MSUserAnnot
        index = 0;
        MSUserAnnot.put(new Integer(index++), "none");
        MSUserAnnot.put(new Integer(index++), "delete");
        MSUserAnnot.put(new Integer(index++), "flag");


        // MSResponseError
        index = 0;
        MSResponseError.put(new Integer(index++), "none");
        MSResponseError.put(new Integer(index++), "generalerr");
        MSResponseError.put(new Integer(index++), "noblastdb - unable to open blast library");
        MSResponseError.put(new Integer(index++), "noinput - input missing");


        // MSIonNeutralLoss
        index = 0;
        MSIonNeutralLoss.put(new Integer(index++), "water - minus 18 Da");
        MSIonNeutralLoss.put(new Integer(index++), "ammonia - minus 17 Da");
    }

    /**
     * Get the name of the enzyme as text, e.g., "trypsin" or "chymotrypsin".
     *
     * @param index the integer ID of the enzyme
     * @return the enzyme as text, e.g., "trypsin" or "chymotrypsin", null if not found
     */
    public static String getEnzymeAsText(int index) {
        return OmssaEnumerators.MSEnzymes.get(index);
    }
    
    /**
     * Get the name of the modification as text, e.g., "oxym - oxidation of methionine".
     *
     * @param index the integer ID of the modification
     * @return the modification as text, e.g., "oxym - oxidation of methionine", null if not found
     */
    public static String getModificationAsText(int index) {
        return OmssaEnumerators.MSMod.get(index);
    }
    
    /**
     * Get the type of the modification as text, e.g., "modaa - at particular amino acids".
     *
     * @param index the integer ID of the modification type
     * @return the modification type as text, e.g., "modaa - at particular amino acids", null if not found
     */
    public static String getModificationTypeAsText(int index) {
        return OmssaEnumerators.MSModType.get(index);
    }

    /**
     * Get the search type as text, e.g., "monoisotopic".
     *
     * @param index the integer ID of the search type
     * @return the search type as text, e.g., "monoisotopic", null if not found
     */
    public static String getMSSearchTypeAsText(int index) {
        return OmssaEnumerators.MSSearchType.get(index);
    }

    /**
     * Get the charge dependence of the mass tolerance as text, e.g., "independent - mass tol. invariant with charge".
     *
     * @param index the integer ID of the MSZDependence
     * @return the charge dependence of the mass tolerance as text, e.g., "independent - mass tol. invariant with charge", null if not found
     */
    public static String getMSZDependenceAsText(int index) {
        return OmssaEnumerators.MSZDependence.get(index);
    }

    /**
     * Get the spectrum file type as text, e.g., "dta".
     *
     * @param index the integer ID of the spectrum file type
     * @return the spectrum file type as text, e.g., "dta", null if not found
     */
    public static String getMSSpectrumFileTypeAsText(int index) {
        return OmssaEnumerators.MSSpectrumFileType.get(index);
    }

    /**
     * Get the serial data format as text, e.g., "xml - open XML format".
     *
     * @param index the integer ID of the serial data format
     * @return the serial data format as text, e.g., "xml - open XML format", null if not found
     */
    public static String getMSSerialDataFormatAsText(int index) {
        return OmssaEnumerators.MSSerialDataFormat.get(index);
    }

    /**
     * Get the ion type as text, e.g., "b".
     *
     * @param index the integer ID of the ion type
     * @return the ion type as text, e.g., "b", null if not found
     */
    public static String getMSIonTypeAsText(int index) {
        return OmssaEnumerators.MSIonType.get(index);
    }

    /**
     * Get the ion isotopic type as text, e.g., "monoisotopic - no c13s in molecule".
     *
     * @param index the integer ID of the ion isotopic type
     * @return the ion isotopic type as text, e.g., "monoisotopic - no c13s in molecule", null if not found
     */
    public static String getMSIonIsotopicTypeAsText(int index) {
        return OmssaEnumerators.MSIonIsotopicType.get(index);
    }

    /**
     * Get the MS hit error as text, e.g., "unable2read - can't read the spectrum".
     *
     * @param index the integer ID of the MS hit error
     * @return the MS hit error as text, e.g., "unable2read - can't read the spectrum", null if not found
     */
    public static String getMSHitErrorAsText(int index) {
        return OmssaEnumerators.MSHitError.get(index);
    }

    /**
     * Get the MS user annotation as text, e.g., "none", "delete" or "flag".
     *
     * @param index the integer ID of the MS user annotation
     * @return the MS user annotation as text, e.g., "none", "delete" or "flag", null if not found
     */
    public static String getMSUserAnnotAsText(int index) {
        return OmssaEnumerators.MSUserAnnot.get(index);
    }

    /**
     * Get the MS response error as text, e.g., "noblastdb - unable to open blast library".
     *
     * @param index the integer ID of the MS response error
     * @return the MSresponse error as text, e.g., "noblastdb - unable to open blast library", null if not found
     */
    public static String getMSResponseErrorAsText(int index) {
        return OmssaEnumerators.MSResponseError.get(index);
    }

    /**
     * Get the MS ion neutral loss as text, e.g.,"water - minus 18 Da".
     *
     * @param index the integer ID of the MS ion neutral loss
     * @return the MS ion neutral loss text, e.g., "water - minus 18 Da", null if not found
     */
    public static String getMSIonNeutralLossAsText(int index) {
        return OmssaEnumerators.MSIonNeutralLoss.get(index);
    }
}
