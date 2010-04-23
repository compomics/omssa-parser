package de.proteinms.omxparser.util;

import java.util.Vector;
import java.io.Serializable;

/**
 * This class contains the details on a OMSSA modification.
 *
 * @author  Harald Barsnes
 * 
 * Created August 2008
 */
public class OmssaModification implements Serializable {

    /**
     * The modification number
     */
    private Integer modNumber;
    /**
     * The modification type
     */
    private Integer modType;
    /**
     * The modification name
     */
    private String modName;
    /**
     * The modification mono mass
     */
    private Double modMonoMass;
    /**
     * The residues the modification can modidy
     */
    private Vector<String> modResidues;
    /**
     * modification at particular amino acids
     */
    public static final int MODAA = 0;
    /**
     * modification at the N terminus of a protein
     */
    public static final int MODN = 1;
    /**
     * modification at the N terminus of a protein
     */
    public static final int MODNAA = 2;
    /**
     * modification at the C terminus of a protein
     */
    public static final int MODC = 3;
    /**
     * modification at the C terminus of a protein at particular amino acids
     */
    public static final int MODCAA = 4;
    /**
     * modification at the N terminus of a peptide
     */
    public static final int MODNP = 5;
    /**
     * modification at the N terminus of a peptide at particular amino acids
     */
    public static final int MODNPAA = 6;
    /**
     * modification at the C terminus of a peptide
     */
    public static final int MODCP = 7;
    /**
     * modification at the C terminus of a peptide at particular amino acids
     */
    public static final int MODCPAA = 8;
    /**
     * the max number of modification types
     */
    public static final int MODMAX = 9;

    /**
     * Creates a new OmssaModification object.
     * 
     * @param modNumber
     * @param modName
     * @param modMonoMass
     * @param modResidues
     * @param modType
     */
    public OmssaModification(Integer modNumber, String modName,
            Double modMonoMass, Vector<String> modResidues, Integer modType) {
        this.modNumber = modNumber;
        this.modName = modName;
        this.modMonoMass = modMonoMass;
        this.modResidues = modResidues;
        this.modType = modType;
    }

    /**
     * Returns the modification number
     * 
     * @return the modification number
     */
    public Integer getModNumber() {
        return modNumber;
    }

    /**
     * Returns the modification name
     * 
     * @return the modification name
     */
    public String getModName() {
        return modName;
    }

    /**
     * Returns the modification mass
     * 
     * @return the modification mass
     */
    public Double getModMonoMass() {
        return modMonoMass;
    }

    /**
     * Returns the modified residues
     * 
     * @return the modified residues
     */
    public Vector<String> getModResidues() {
        return modResidues;
    }

    /**
     * Returns the modified residues as a String
     * 
     * @return the modified residues
     */
    public String getModResiduesAsString() {

        String temp = "";

        for (int i = 0; i < modResidues.size(); i++) {
            temp += modResidues.get(i) + ", ";
        }

        temp = temp.substring(0, temp.length() - 2);

        return temp;
    }

    /**
     * Returns the modification type
     *
     *  From OMSSA.mod.xsd:
     *
     *    0 - modaa     -  at particular amino acids
     *    1 - modn      -  at the N terminus of a protein
     *    2 - modnaa	-  at the N terminus of a protein at particular amino acids
     *    3 - modc      -  at the C terminus of a protein
     *    4 - modcaa	-  at the C terminus of a protein at particular amino acids
     *    5 - modnp     -  at the N terminus of a peptide
     *    6 - modnpaa	-  at the N terminus of a peptide at particular amino acids
     *    7 - modcp     -  at the C terminus of a peptide
     *    8 - modcpaa	-  at the C terminus of a peptide at particular amino acids
     *    9 - modmax	-  the max number of modification types
     *
     * @return the modification type
     */
    public Integer getModType() {
        return modType;
    }
}
