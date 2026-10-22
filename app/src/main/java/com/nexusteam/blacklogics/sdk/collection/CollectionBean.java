package com.nexusteam.blacklogics.sdk.collection;

import com.google.gson.annotations.Expose;

/**
 * Sketchware-style collection entry.
 * data = each ViewBean as one Gson JSON line (newline separated).
 * MUST use @Expose — WidgetCollectionManager Gson excludes fields without it.
 */
public class CollectionBean {
    @Expose
    public String name;
    @Expose
    public String data;
    @Expose
    public String reserved1;

    public CollectionBean() {}

    public CollectionBean(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public CollectionBean(String name, String data, String reserved1) {
        this.name = name;
        this.data = data;
        this.reserved1 = reserved1;
    }
}
