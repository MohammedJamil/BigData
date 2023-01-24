package org.bigdata.analyser;

public abstract class Analyzer {

    /**
     * Class Attributes.
     */
    protected Boolean analyzable;
    protected String key;
    protected String value;

    /**
     * Tells if a record is to be considered in the analysis.
     */
    public Boolean isAnalyzable(){
        return analyzable;
    }

    /**
     * Key Getter.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Value Getter.
     */
    public String getValue() {
        return this.value;
    }
}
