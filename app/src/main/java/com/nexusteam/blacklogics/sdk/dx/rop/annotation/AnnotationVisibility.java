
package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.util.ToHuman;

public enum AnnotationVisibility implements ToHuman {
    RUNTIME("runtime"),
    BUILD("build"),
    SYSTEM("system"),
    EMBEDDED("embedded");

    private final String human;

    private AnnotationVisibility(String human) {
        this.human = human;
    }

    @Override
    public String toHuman() {
        return human;
    }
}
