package de.proteinms.omxparser.util;

import java.util.HashMap;

/**
 * Contains mappings for all the enumerators used in the OMSSA.mod.xsd file.
 * <br> <b>Mapping updated July 16th 2010.</b> <br><br> Please see
 * "OMSSA.mod.xsd" for further information: <br><br> See <a
 * href="http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd">http://www.ncbi.nlm.nih.gov/data_specs/schema/OMSSA.mod.xsd</a>
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
        MSEnzymes.put(Integer.valueOf(index++), "trypsin");
        MSEnzymes.put(Integer.valueOf(index++), "argc");
        MSEnzymes.put(Integer.valueOf(index++), "cnbr");
        MSEnzymes.put(Integer.valueOf(index++), "chymotrypsin");
        MSEnzymes.put(Integer.valueOf(index++), "formicacid");
        MSEnzymes.put(Integer.valueOf(index++), "lysc");
        MSEnzymes.put(Integer.valueOf(index++), "lysc-p");
        MSEnzymes.put(Integer.valueOf(index++), "pepsin-a");
        MSEnzymes.put(Integer.valueOf(index++), "tryp-cnbr");
        MSEnzymes.put(Integer.valueOf(index++), "tryp-chymo");
        MSEnzymes.put(Integer.valueOf(index++), "trypsin-p");
        MSEnzymes.put(Integer.valueOf(index++), "whole-protein");
        MSEnzymes.put(Integer.valueOf(index++), "aspn");
        MSEnzymes.put(Integer.valueOf(index++), "gluc");
        MSEnzymes.put(Integer.valueOf(index++), "aspngluc");
        MSEnzymes.put(Integer.valueOf(index++), "top-down");
        MSEnzymes.put(Integer.valueOf(index++), "semi-tryptic");
        MSEnzymes.put(Integer.valueOf(index++), "no-enzyme");
        MSEnzymes.put(Integer.valueOf(index++), "chymotrypsin-p");
        MSEnzymes.put(Integer.valueOf(index++), "aspn-de");
        MSEnzymes.put(Integer.valueOf(index++), "gluc-de");
        MSEnzymes.put(Integer.valueOf(index++), "lysn");
        MSEnzymes.put(Integer.valueOf(index++), "thermolysin-p");
        MSEnzymes.put(Integer.valueOf(index++), "semi-chymotrypsin");
        MSEnzymes.put(Integer.valueOf(index++), "semi-gluc");
        MSEnzymes.put(Integer.valueOf(index++), "max");
        MSEnzymes.put(Integer.valueOf(255), "none");


        // MSMod
        index = 0;
        MSMod.put(Integer.valueOf(index++), "methylk - methylation of K");
        MSMod.put(Integer.valueOf(index++), "oxym - oxidation of methionine");
        MSMod.put(Integer.valueOf(index++), "carboxymethylc - carboxymethyl cysteine");
        MSMod.put(Integer.valueOf(index++), "carbamidomethylc - deamidation of K and Q");
        MSMod.put(Integer.valueOf(index++), "deamidationkq - propionamide cysteine");
        MSMod.put(Integer.valueOf(index++), "propionamidec - phosphorylation of S");
        MSMod.put(Integer.valueOf(index++), "phosphorylations - phosphorylation of T");
        MSMod.put(Integer.valueOf(index++), "phosphorylationt - phosphorylation of Y");
        MSMod.put(Integer.valueOf(index++), "phosphorylationy - N terminal methionine cleavage");
        MSMod.put(Integer.valueOf(index++), "ntermmcleave - N terminal methionine cleavage");
        MSMod.put(Integer.valueOf(index++), "ntermacetyl - N terminal protein acetyl");
        MSMod.put(Integer.valueOf(index++), "ntermmethyl - N terminal protein methyl");
        MSMod.put(Integer.valueOf(index++), "ntermtrimethyl - N terminal protein trimethyl");
        MSMod.put(Integer.valueOf(index++), "methythiold - beta methythiolation of D");
        MSMod.put(Integer.valueOf(index++), "methylq - methylation of Q");
        MSMod.put(Integer.valueOf(index++), "trimethylk - trimethylation of K");
        MSMod.put(Integer.valueOf(index++), "methyld - methylation of D");
        MSMod.put(Integer.valueOf(index++), "methyle - methylation of E");
        MSMod.put(Integer.valueOf(index++), "ctermpepmethyl - C terminal methylation");
        MSMod.put(Integer.valueOf(index++), "trideuteromethyld - trideuteromethylation of D");
        MSMod.put(Integer.valueOf(index++), "trideuteromethyle - trideuteromethylation of E");
        MSMod.put(Integer.valueOf(index++), "ctermpeptrideuteromethyl - C terminal trideuteromethylation");
        MSMod.put(Integer.valueOf(index++), "nformylmet");
        MSMod.put(Integer.valueOf(index++), "twoamino3oxobutanoicacid");
        MSMod.put(Integer.valueOf(index++), "acetylk");
        MSMod.put(Integer.valueOf(index++), "ctermamide");
        MSMod.put(Integer.valueOf(index++), "bmethylthiold");
        MSMod.put(Integer.valueOf(index++), "carbamidomethylk");
        MSMod.put(Integer.valueOf(index++), "carbamidometylh");
        MSMod.put(Integer.valueOf(index++), "carbamidomethyld");
        MSMod.put(Integer.valueOf(index++), "carbamidomethyle");
        MSMod.put(Integer.valueOf(index++), "carbamylk");
        MSMod.put(Integer.valueOf(index++), "ntermcarbamyl");
        MSMod.put(Integer.valueOf(index++), "citrullinationr");
        MSMod.put(Integer.valueOf(index++), "cysteicacidc");
        MSMod.put(Integer.valueOf(index++), "diiodinationy");
        MSMod.put(Integer.valueOf(index++), "dimethylk");
        MSMod.put(Integer.valueOf(index++), "dimethylr");
        MSMod.put(Integer.valueOf(index++), "ntermpepdimethyl");
        MSMod.put(Integer.valueOf(index++), "dihydroxyf");
        MSMod.put(Integer.valueOf(index++), "thioacetylk");
        MSMod.put(Integer.valueOf(index++), "ntermpeptioacetyl");
        MSMod.put(Integer.valueOf(index++), "farnesylationc");
        MSMod.put(Integer.valueOf(index++), "formylk");
        MSMod.put(Integer.valueOf(index++), "ntermpepformyl");
        MSMod.put(Integer.valueOf(index++), "formylkynureninw");
        MSMod.put(Integer.valueOf(index++), "phef");
        MSMod.put(Integer.valueOf(index++), "gammacarboxyld");
        MSMod.put(Integer.valueOf(index++), "gammacarboxyle");
        MSMod.put(Integer.valueOf(index++), "geranylgeranylc");
        MSMod.put(Integer.valueOf(index++), "ntermpepglucuronylg");
        MSMod.put(Integer.valueOf(index++), "glutathionec");
        MSMod.put(Integer.valueOf(index++), "glyglyk");
        MSMod.put(Integer.valueOf(index++), "guanidinationk");
        MSMod.put(Integer.valueOf(index++), "his2asnh");
        MSMod.put(Integer.valueOf(index++), "his2asph");
        MSMod.put(Integer.valueOf(index++), "ctermpephsem");
        MSMod.put(Integer.valueOf(index++), "ctermpephselactm");
        MSMod.put(Integer.valueOf(index++), "hydroxykynureninw");
        MSMod.put(Integer.valueOf(index++), "hydroxylationd");
        MSMod.put(Integer.valueOf(index++), "hydroxylationk");
        MSMod.put(Integer.valueOf(index++), "hydroxylationn");
        MSMod.put(Integer.valueOf(index++), "hydroxylationp");
        MSMod.put(Integer.valueOf(index++), "hydroxylationf");
        MSMod.put(Integer.valueOf(index++), "hydroxylationy");
        MSMod.put(Integer.valueOf(index++), "iodinationy");
        MSMod.put(Integer.valueOf(index++), "kynureninw");
        MSMod.put(Integer.valueOf(index++), "lipoylk");
        MSMod.put(Integer.valueOf(index++), "ctermpepmeester");
        MSMod.put(Integer.valueOf(index++), "meesterd");
        MSMod.put(Integer.valueOf(index++), "meestere");
        MSMod.put(Integer.valueOf(index++), "meesters");
        MSMod.put(Integer.valueOf(index++), "meestery");
        MSMod.put(Integer.valueOf(index++), "methylc");
        MSMod.put(Integer.valueOf(index++), "methylh");
        MSMod.put(Integer.valueOf(index++), "methyln");
        MSMod.put(Integer.valueOf(index++), "ntermpepmethyl");
        MSMod.put(Integer.valueOf(index++), "methylr");
        MSMod.put(Integer.valueOf(index++), "ntermpepmyristoyeylationg");
        MSMod.put(Integer.valueOf(index++), "ntermpepmyristoyl4hg");
        MSMod.put(Integer.valueOf(index++), "ntermpepmyristoylationg");
        MSMod.put(Integer.valueOf(index++), "myristoylationk");
        MSMod.put(Integer.valueOf(index++), "ntermformyl");
        MSMod.put(Integer.valueOf(index++), "nemc");
        MSMod.put(Integer.valueOf(index++), "nipcam");
        MSMod.put(Integer.valueOf(index++), "nitrow");
        MSMod.put(Integer.valueOf(index++), "nitroy");
        MSMod.put(Integer.valueOf(index++), "ctermpepo18");
        MSMod.put(Integer.valueOf(index++), "ctermpepdio18");
        MSMod.put(Integer.valueOf(index++), "oxyh");
        MSMod.put(Integer.valueOf(index++), "oxyw");
        MSMod.put(Integer.valueOf(index++), "ppantetheines");
        MSMod.put(Integer.valueOf(index++), "palmitoylationc");
        MSMod.put(Integer.valueOf(index++), "palmitoylationk");
        MSMod.put(Integer.valueOf(index++), "palmitoylations");
        MSMod.put(Integer.valueOf(index++), "palmitoylationt");
        MSMod.put(Integer.valueOf(index++), "phospholosss");
        MSMod.put(Integer.valueOf(index++), "phospholosst");
        MSMod.put(Integer.valueOf(index++), "phospholossy");
        MSMod.put(Integer.valueOf(index++), "phosphoneutrallossc");
        MSMod.put(Integer.valueOf(index++), "phosphoneutrallossd");
        MSMod.put(Integer.valueOf(index++), "phosphoneutrallossh");
        MSMod.put(Integer.valueOf(index++), "propionylk");
        MSMod.put(Integer.valueOf(index++), "ntermpeppropionyl");
        MSMod.put(Integer.valueOf(index++), "propionylheavyk");
        MSMod.put(Integer.valueOf(index++), "ntermpeppropionylheavy");
        MSMod.put(Integer.valueOf(index++), "pyridylk");
        MSMod.put(Integer.valueOf(index++), "ntermpeppyridyl");
        MSMod.put(Integer.valueOf(index++), "ntermpeppyrocmc");
        MSMod.put(Integer.valueOf(index++), "ntermpeppyroe");
        MSMod.put(Integer.valueOf(index++), "ntermpeppyroq");
        MSMod.put(Integer.valueOf(index++), "pyroglutamicp");
        MSMod.put(Integer.valueOf(index++), "spyridylethylc");
        MSMod.put(Integer.valueOf(index++), "semetm");
        MSMod.put(Integer.valueOf(index++), "sulfationy");
        MSMod.put(Integer.valueOf(index++), "suphonem");
        MSMod.put(Integer.valueOf(index++), "triiodinationy");
        MSMod.put(Integer.valueOf(index++), "trimethylationr");
        MSMod.put(Integer.valueOf(index++), "ntermpeptripalmitatec");
        MSMod.put(Integer.valueOf(index++), "usermod1 - start of user defined mods");
        MSMod.put(Integer.valueOf(index++), "usermod2");
        MSMod.put(Integer.valueOf(index++), "usermod3");
        MSMod.put(Integer.valueOf(index++), "usermod4");
        MSMod.put(Integer.valueOf(index++), "usermod5");
        MSMod.put(Integer.valueOf(index++), "usermod6");
        MSMod.put(Integer.valueOf(index++), "usermod7");
        MSMod.put(Integer.valueOf(index++), "usermod8");
        MSMod.put(Integer.valueOf(index++), "usermod9");
        MSMod.put(Integer.valueOf(index++), "usermod10 - end of user defined mods");
        MSMod.put(Integer.valueOf(index++), "icatlight");
        MSMod.put(Integer.valueOf(index++), "icatheavy");
        MSMod.put(Integer.valueOf(index++), "camthiopropanoylk");
        MSMod.put(Integer.valueOf(index++), "phosphoneutrallosss");
        MSMod.put(Integer.valueOf(index++), "phosphoneutrallosst");
        MSMod.put(Integer.valueOf(index++), "phosphoetdlosss");
        MSMod.put(Integer.valueOf(index++), "phosphoetdlosst");
        MSMod.put(Integer.valueOf(index++), "arg-13c6");
        MSMod.put(Integer.valueOf(index++), "arg-13c6-15n4");
        MSMod.put(Integer.valueOf(index++), "lys-13c6");
        MSMod.put(Integer.valueOf(index++), "oxy18");
        MSMod.put(Integer.valueOf(index++), "beta-elim-s");
        MSMod.put(Integer.valueOf(index++), "beta-elim-t");
        MSMod.put(Integer.valueOf(index++), "usermod11");
        MSMod.put(Integer.valueOf(index++), "usermod12");
        MSMod.put(Integer.valueOf(index++), "usermod13");
        MSMod.put(Integer.valueOf(index++), "usermod14");
        MSMod.put(Integer.valueOf(index++), "usermod15");
        MSMod.put(Integer.valueOf(index++), "usermod16");
        MSMod.put(Integer.valueOf(index++), "usermod17");
        MSMod.put(Integer.valueOf(index++), "usermod18");
        MSMod.put(Integer.valueOf(index++), "usermod19");
        MSMod.put(Integer.valueOf(index++), "usermod20");
        MSMod.put(Integer.valueOf(index++), "usermod21");
        MSMod.put(Integer.valueOf(index++), "usermod22");
        MSMod.put(Integer.valueOf(index++), "usermod23");
        MSMod.put(Integer.valueOf(index++), "usermod24");
        MSMod.put(Integer.valueOf(index++), "usermod25");
        MSMod.put(Integer.valueOf(index++), "usermod26");
        MSMod.put(Integer.valueOf(index++), "usermod27");
        MSMod.put(Integer.valueOf(index++), "usermod28");
        MSMod.put(Integer.valueOf(index++), "usermod29");
        MSMod.put(Integer.valueOf(index++), "usermod30");
        MSMod.put(Integer.valueOf(index++), "sulfinicacid");
        MSMod.put(Integer.valueOf(index++), "arg2orn");
        MSMod.put(Integer.valueOf(index++), "dehydro");
        MSMod.put(Integer.valueOf(index++), "carboxykynurenin");
        MSMod.put(Integer.valueOf(index++), "sumoylation");
        MSMod.put(Integer.valueOf(index++), "iTRAQ114nterm");
        MSMod.put(Integer.valueOf(index++), "iTRAQ114K");
        MSMod.put(Integer.valueOf(index++), "iTRAQ114Y");
        MSMod.put(Integer.valueOf(index++), "iTRAQ115nterm");
        MSMod.put(Integer.valueOf(index++), "iTRAQ115K");
        MSMod.put(Integer.valueOf(index++), "iTRAQ115Y");
        MSMod.put(Integer.valueOf(index++), "iTRAQ116nterm");
        MSMod.put(Integer.valueOf(index++), "iTRAQ116K");
        MSMod.put(Integer.valueOf(index++), "iTRAQ116Y");
        MSMod.put(Integer.valueOf(index++), "iTRAQ117nterm");
        MSMod.put(Integer.valueOf(index++), "iTRAQ117K");
        MSMod.put(Integer.valueOf(index++), "iTRAQ117Y");
        MSMod.put(Integer.valueOf(index++), "mmts");
        MSMod.put(Integer.valueOf(index++), "lys-2H4");
        MSMod.put(Integer.valueOf(index++), "lys-13C615N2");
        MSMod.put(Integer.valueOf(index++), "hexNAcN");
        MSMod.put(Integer.valueOf(index++), "dHexHexNAcN");
        MSMod.put(Integer.valueOf(index++), "hexNAcS");
        MSMod.put(Integer.valueOf(index++), "hexNAcT");
        MSMod.put(Integer.valueOf(index++), "mod186");
        MSMod.put(Integer.valueOf(index++), "mod187");
        MSMod.put(Integer.valueOf(index++), "mod188");
        MSMod.put(Integer.valueOf(index++), "mod189");
        MSMod.put(Integer.valueOf(index++), "mod190");
        MSMod.put(Integer.valueOf(index++), "mod191");
        MSMod.put(Integer.valueOf(index++), "mod192");
        MSMod.put(Integer.valueOf(index++), "mod193");
        MSMod.put(Integer.valueOf(index++), "mod194");
        MSMod.put(Integer.valueOf(index++), "mod195");
        MSMod.put(Integer.valueOf(index++), "mod196");
        MSMod.put(Integer.valueOf(index++), "mod197");
        MSMod.put(Integer.valueOf(index++), "mod198");
        MSMod.put(Integer.valueOf(index++), "mod199");
        MSMod.put(Integer.valueOf(index++), "mod200");
        MSMod.put(Integer.valueOf(index++), "mod201");
        MSMod.put(Integer.valueOf(index++), "mod202");
        MSMod.put(Integer.valueOf(index++), "mod203");
        MSMod.put(Integer.valueOf(index++), "mod204");
        MSMod.put(Integer.valueOf(index++), "mod205");
        MSMod.put(Integer.valueOf(index++), "mod206");
        MSMod.put(Integer.valueOf(index++), "mod207");
        MSMod.put(Integer.valueOf(index++), "mod208");
        MSMod.put(Integer.valueOf(index++), "mod209");
        MSMod.put(Integer.valueOf(index++), "mod210");
        MSMod.put(Integer.valueOf(index++), "mod211");
        MSMod.put(Integer.valueOf(index++), "mod212");
        MSMod.put(Integer.valueOf(index++), "mod213");
        MSMod.put(Integer.valueOf(index++), "mod214");
        MSMod.put(Integer.valueOf(index++), "mod215");
        MSMod.put(Integer.valueOf(index++), "mod216");
        MSMod.put(Integer.valueOf(index++), "mod217");
        MSMod.put(Integer.valueOf(index++), "mod218");
        MSMod.put(Integer.valueOf(index++), "mod219");
        MSMod.put(Integer.valueOf(index++), "mod220");
        MSMod.put(Integer.valueOf(index++), "mod221");
        MSMod.put(Integer.valueOf(index++), "mod222");
        MSMod.put(Integer.valueOf(index++), "mod223");
        MSMod.put(Integer.valueOf(index++), "mod224");
        MSMod.put(Integer.valueOf(index++), "mod225");
        MSMod.put(Integer.valueOf(index++), "mod226");
        MSMod.put(Integer.valueOf(index++), "mod227");
        MSMod.put(Integer.valueOf(index++), "mod228");
        MSMod.put(Integer.valueOf(index++), "mod229");
        MSMod.put(Integer.valueOf(index++), "mod230");
        MSMod.put(Integer.valueOf(index++), "max - maximum number of mods");
        MSMod.put(Integer.valueOf(9999), "unknown - modification of unknown type");
        MSMod.put(Integer.valueOf(10000), "none");


        // MSModType
        index = 0;
        MSModType.put(Integer.valueOf(index++), "modaa - at particular amino acids");
        MSModType.put(Integer.valueOf(index++), "modn - at the N terminus of a protein");
        MSModType.put(Integer.valueOf(index++), "modnaa - at the N terminus of a protein at particular amino acids");
        MSModType.put(Integer.valueOf(index++), "modc - at the C terminus of a protein");
        MSModType.put(Integer.valueOf(index++), "modcaa - at the C terminus of a protein at particular amino acids");
        MSModType.put(Integer.valueOf(index++), "modnp - at the N terminus of a peptide");
        MSModType.put(Integer.valueOf(index++), "modnpaa - at the N terminus of a peptide at particular amino acids");
        MSModType.put(Integer.valueOf(index++), "modcp - at the C terminus of a peptide");
        MSModType.put(Integer.valueOf(index++), "modcpaa - at the C terminus of a peptide at particular amino acids");
        MSModType.put(Integer.valueOf(index++), "modmax - the max number of modification types");


        // MSSearchType
        index = 0;
        MSSearchType.put(Integer.valueOf(index++), "monoisotopic");
        MSSearchType.put(Integer.valueOf(index++), "average");
        MSSearchType.put(Integer.valueOf(index++), "monon15");
        MSSearchType.put(Integer.valueOf(index++), "exact");
        MSSearchType.put(Integer.valueOf(index++), "multiisotope");
        MSSearchType.put(Integer.valueOf(index++), "max");


        // MSZDependence
        index = 0;
        MSZDependence.put(Integer.valueOf(index++), "independent - mass tol. invariant with charge");
        MSZDependence.put(Integer.valueOf(index++), "linearwithz - mass tol. scales with charge");
        MSZDependence.put(Integer.valueOf(index++), "max");


        // MSSpectrumFileType
        index = 0;
        MSSpectrumFileType.put(Integer.valueOf(index++), "dta");
        MSSpectrumFileType.put(Integer.valueOf(index++), "dtablank");
        MSSpectrumFileType.put(Integer.valueOf(index++), "dtaxml");
        MSSpectrumFileType.put(Integer.valueOf(index++), "asc");
        MSSpectrumFileType.put(Integer.valueOf(index++), "pkl");
        MSSpectrumFileType.put(Integer.valueOf(index++), "pks");
        MSSpectrumFileType.put(Integer.valueOf(index++), "sciex");
        MSSpectrumFileType.put(Integer.valueOf(index++), "mgf");
        MSSpectrumFileType.put(Integer.valueOf(index++), "unknown");
        MSSpectrumFileType.put(Integer.valueOf(index++), "oms - asn.1 binary for iterative search");
        MSSpectrumFileType.put(Integer.valueOf(index++), "omx - xml for iterative search");
        MSSpectrumFileType.put(Integer.valueOf(index++), "xml - xml MSRequest");
        MSSpectrumFileType.put(Integer.valueOf(index++), "omxbz - bzip2 omx file2");


        // MSSerialDataFormat
        index = 0;
        MSSerialDataFormat.put(Integer.valueOf(index++), "none");
        MSSerialDataFormat.put(Integer.valueOf(index++), "asntext - open ASN.1 text format");
        MSSerialDataFormat.put(Integer.valueOf(index++), "asnbinary - open ASN.1 binary format");
        MSSerialDataFormat.put(Integer.valueOf(index++), "xml - open XML format");
        MSSerialDataFormat.put(Integer.valueOf(index++), "csv - csv (excel)");
        MSSerialDataFormat.put(Integer.valueOf(index++), "pepxml - pepXML format");
        MSSerialDataFormat.put(Integer.valueOf(index++), "xmlbz2 - bzip2 XML format");


        // MSIonType
        index = 0;
        MSIonType.put(Integer.valueOf(index++), "a");
        MSIonType.put(Integer.valueOf(index++), "b");
        MSIonType.put(Integer.valueOf(index++), "c");
        MSIonType.put(Integer.valueOf(index++), "x");
        MSIonType.put(Integer.valueOf(index++), "y");
        MSIonType.put(Integer.valueOf(index++), "z");
        MSIonType.put(Integer.valueOf(index++), "parent");
        MSIonType.put(Integer.valueOf(index++), "internal");
        MSIonType.put(Integer.valueOf(index++), "immonium");
        MSIonType.put(Integer.valueOf(index++), "unknown");
        MSIonType.put(Integer.valueOf(index++), "adot");
        MSIonType.put(Integer.valueOf(index++), "x-CO2");
        MSIonType.put(Integer.valueOf(index++), "adot-CO2");
        MSIonType.put(Integer.valueOf(index++), "max");


        // MSIonIsotopicType
        index = 0;
        MSIonIsotopicType.put(Integer.valueOf(index++), "monoisotopic - no c13s in molecule");
        MSIonIsotopicType.put(Integer.valueOf(index++), "c13 - one c13 in molecule");
        MSIonIsotopicType.put(Integer.valueOf(index++), "c13two - two c13s in molecule");
        MSIonIsotopicType.put(Integer.valueOf(index++), "c13three - three c13s in molecule");
        MSIonIsotopicType.put(Integer.valueOf(index++), "c13four - four c13s in molecule");


        // MSHitError
        index = 0;
        MSHitError.put(Integer.valueOf(index++), "none");
        MSHitError.put(Integer.valueOf(index++), "generalerr");
        MSHitError.put(Integer.valueOf(index++), "unable2read - can't read the spectrum");
        MSHitError.put(Integer.valueOf(index++), "notenuffpeaks - not enough peaks to search");


        // MSUserAnnot
        index = 0;
        MSUserAnnot.put(Integer.valueOf(index++), "none");
        MSUserAnnot.put(Integer.valueOf(index++), "delete");
        MSUserAnnot.put(Integer.valueOf(index++), "flag");


        // MSResponseError
        index = 0;
        MSResponseError.put(Integer.valueOf(index++), "none");
        MSResponseError.put(Integer.valueOf(index++), "generalerr");
        MSResponseError.put(Integer.valueOf(index++), "noblastdb - unable to open blast library");
        MSResponseError.put(Integer.valueOf(index++), "noinput - input missing");


        // MSIonNeutralLoss
        index = 0;
        MSIonNeutralLoss.put(Integer.valueOf(index++), "water - minus 18 Da");
        MSIonNeutralLoss.put(Integer.valueOf(index++), "ammonia - minus 17 Da");
    }

    /**
     * Get the name of the enzyme as text, e.g., "trypsin" or "chymotrypsin".
     *
     * @param index the integer ID of the enzyme
     * @return the enzyme as text, e.g., "trypsin" or "chymotrypsin", null if
     * not found
     */
    public static String getEnzymeAsText(int index) {
        return OmssaEnumerators.MSEnzymes.get(index);
    }

    /**
     * Get the name of the modification as text, e.g., "oxym - oxidation of
     * methionine".
     *
     * @param index the integer ID of the modification
     * @return the modification as text, e.g., "oxym - oxidation of methionine",
     * null if not found
     */
    public static String getModificationAsText(int index) {
        return OmssaEnumerators.MSMod.get(index);
    }

    /**
     * Get the type of the modification as text, e.g., "modaa - at particular
     * amino acids".
     *
     * @param index the integer ID of the modification type
     * @return the modification type as text, e.g., "modaa - at particular amino
     * acids", null if not found
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
     * Get the charge dependence of the mass tolerance as text, e.g.,
     * "independent - mass tol. invariant with charge".
     *
     * @param index the integer ID of the MSZDependence
     * @return the charge dependence of the mass tolerance as text, e.g.,
     * "independent - mass tol. invariant with charge", null if not found
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
     * @return the serial data format as text, e.g., "xml - open XML format",
     * null if not found
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
     * Get the ion isotopic type as text, e.g., "monoisotopic - no c13s in
     * molecule".
     *
     * @param index the integer ID of the ion isotopic type
     * @return the ion isotopic type as text, e.g., "monoisotopic - no c13s in
     * molecule", null if not found
     */
    public static String getMSIonIsotopicTypeAsText(int index) {
        return OmssaEnumerators.MSIonIsotopicType.get(index);
    }

    /**
     * Get the MS hit error as text, e.g., "unable2read - can't read the
     * spectrum".
     *
     * @param index the integer ID of the MS hit error
     * @return the MS hit error as text, e.g., "unable2read - can't read the
     * spectrum", null if not found
     */
    public static String getMSHitErrorAsText(int index) {
        return OmssaEnumerators.MSHitError.get(index);
    }

    /**
     * Get the MS user annotation as text, e.g., "none", "delete" or "flag".
     *
     * @param index the integer ID of the MS user annotation
     * @return the MS user annotation as text, e.g., "none", "delete" or "flag",
     * null if not found
     */
    public static String getMSUserAnnotAsText(int index) {
        return OmssaEnumerators.MSUserAnnot.get(index);
    }

    /**
     * Get the MS response error as text, e.g., "noblastdb - unable to open
     * blast library".
     *
     * @param index the integer ID of the MS response error
     * @return the MSresponse error as text, e.g., "noblastdb - unable to open
     * blast library", null if not found
     */
    public static String getMSResponseErrorAsText(int index) {
        return OmssaEnumerators.MSResponseError.get(index);
    }

    /**
     * Get the MS ion neutral loss as text, e.g.,"water - minus 18 Da".
     *
     * @param index the integer ID of the MS ion neutral loss
     * @return the MS ion neutral loss text, e.g., "water - minus 18 Da", null
     * if not found
     */
    public static String getMSIonNeutralLossAsText(int index) {
        return OmssaEnumerators.MSIonNeutralLoss.get(index);
    }
}
