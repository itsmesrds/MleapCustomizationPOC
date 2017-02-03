package com.tms.ml.dataTypes;

import java.io.Serializable;

/**
 * Created by Nir.Laor on 1/18/2017.
 */
@SuppressWarnings("serial")
public class Document implements Serializable {

    private long id;
    private String text;

    public Document(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }
}
