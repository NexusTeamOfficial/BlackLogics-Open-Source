
package mod.agus.jcoderz.dx.command.findusages;

import mod.agus.jcoderz.dex.Dex;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public final class Main {
    public static void main(String[] args) throws IOException {
        String dexFile = args[0];
        String declaredBy = args[1];
        String memberName = args[2];

        Dex dex = new Dex(new File(dexFile));
        PrintWriter out = new PrintWriter(System.out);
        new FindUsages(dex, declaredBy, memberName, out).findUsages();
        out.flush();
    }
}
