
package mod.agus.jcoderz.dx.io;

import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.io.instructions.DecodedInstruction;

public final class CodeReader {
    private Visitor fallbackVisitor = null;
    private Visitor stringVisitor = null;
    private Visitor typeVisitor = null;
    private Visitor fieldVisitor = null;
    private Visitor methodVisitor = null;
    private Visitor methodAndProtoVisitor = null;
    private Visitor callSiteVisitor = null;

    public void setAllVisitors(Visitor visitor) {
        fallbackVisitor = visitor;
        stringVisitor = visitor;
        typeVisitor = visitor;
        fieldVisitor = visitor;
        methodVisitor = visitor;
        methodAndProtoVisitor = visitor;
        callSiteVisitor = visitor;
    }

    public void setFallbackVisitor(Visitor visitor) {
        fallbackVisitor = visitor;
    }

    public void setStringVisitor(Visitor visitor) {
        stringVisitor = visitor;
    }

    public void setTypeVisitor(Visitor visitor) {
        typeVisitor = visitor;
    }

    public void setFieldVisitor(Visitor visitor) {
        fieldVisitor = visitor;
    }

    public void setMethodVisitor(Visitor visitor) {
        methodVisitor = visitor;
    }

    public void setMethodAndProtoVisitor(Visitor visitor) {
        methodAndProtoVisitor = visitor;
    }

    public void setCallSiteVisitor(Visitor visitor) {
        callSiteVisitor = visitor;
    }

    public void visitAll(DecodedInstruction[] decodedInstructions)
            throws DexException {
        int size = decodedInstructions.length;

        for (int i = 0; i < size; i++) {
            DecodedInstruction one = decodedInstructions[i];
            if (one == null) {
                continue;
            }

            callVisit(decodedInstructions, one);
        }
    }

    public void visitAll(short[] encodedInstructions) throws DexException {
        DecodedInstruction[] decodedInstructions =
            DecodedInstruction.decodeAll(encodedInstructions);
        visitAll(decodedInstructions);
    }

    private void callVisit(DecodedInstruction[] all, DecodedInstruction one) {
        Visitor visitor = null;

        switch (OpcodeInfo.getIndexType(one.getOpcode())) {
            case STRING_REF:           visitor = stringVisitor;         break;
            case TYPE_REF:             visitor = typeVisitor;           break;
            case FIELD_REF:            visitor = fieldVisitor;          break;
            case METHOD_REF:           visitor = methodVisitor;         break;
            case METHOD_AND_PROTO_REF: visitor = methodAndProtoVisitor; break;
            case CALL_SITE_REF:        visitor = callSiteVisitor;       break;
        }

        if (visitor == null) {
            visitor = fallbackVisitor;
        }

        if (visitor != null) {
            visitor.visit(all, one);
        }
    }

    public interface Visitor {
        void visit(DecodedInstruction[] all, DecodedInstruction one);
    }
}
