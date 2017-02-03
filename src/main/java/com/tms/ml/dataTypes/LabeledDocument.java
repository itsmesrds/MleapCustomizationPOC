package com.tms.ml.dataTypes;

import java.io.Serializable;

/**
 * Created by Nir.Laor on 1/18/2017.
 */
@SuppressWarnings("serial")
public class LabeledDocument extends Document implements Serializable {

    private double label;

    public LabeledDocument(long id, String text, double label) {
        super(id, text);
        this.label = label;
    }

    public double getLabel() {
        return this.label;
    }
}
