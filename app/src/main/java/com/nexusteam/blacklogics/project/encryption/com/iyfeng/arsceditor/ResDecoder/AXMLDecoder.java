package com.iyfeng.arsceditor.ResDecoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.CharacterCodingException;
import java.util.List;

import com.iyfeng.arsceditor.ResDecoder.IO.LEDataInputStream;
import com.iyfeng.arsceditor.ResDecoder.IO.LEDataOutputStream;

public class AXMLDecoder {


    private static final int AXML_CHUNK_TYPE = 0x00080003;

    public StringBlock mTableStrings;

    private final LEDataInputStream mIn;

    private LEDataInputStream mIn2;

    private int chunkSize;

    private static byte[] bytes;


    private AXMLDecoder(LEDataInputStream in) {
        this.mIn = in;
    }


    private void readStrings() throws IOException {

        int type = mIn.readInt();

        checkChunk(type, AXML_CHUNK_TYPE);

        chunkSize = mIn.readInt();

        mTableStrings = StringBlock.read(this.mIn);
    }

    public static AXMLDecoder read(InputStream input) throws IOException {
        AXMLDecoder axml = new AXMLDecoder(new LEDataInputStream(input));
        axml.readStrings();
        bytes = LEDataInputStream.toByteArray(input);
        return axml;
    }

    public void getStrings(List<String> m_strings) throws CharacterCodingException {
        for (int i = 0; i < mTableStrings.getCount(); i++) {
            m_strings.add(mTableStrings.getString(i));
        }
    }

    public void write(List<String> stringlist_src, List<String> stringlist_tar, OutputStream out) throws IOException {
        write(stringlist_src, stringlist_tar, new LEDataOutputStream(out));
    }

    private void write(List<String> stringlist_src, List<String> stringlist_tar, LEDataOutputStream lmOut)
            throws IOException {

        for (int i = 0; i < stringlist_src.size(); i++)
            mTableStrings.sortStringBlock(stringlist_src.get(i), stringlist_tar.get(i));

        ByteArrayOutputStream mStrings = mTableStrings.writeString(mTableStrings.getList());

        lmOut.writeInt(AXML_CHUNK_TYPE);

        lmOut.writeInt(chunkSize + (mStrings.size() - mTableStrings.m_strings.length));

        mTableStrings.writeFully(lmOut, mStrings);

        mIn2 = new LEDataInputStream(new ByteArrayInputStream(bytes));

        int num;
        while ((num = mIn2.readByte()) != -1)
            lmOut.writeByte((byte) num);
    }

    private void checkChunk(int type, int expectedType) throws IOException {
        if (type != expectedType)
            throw new IOException(String.format("Invalid chunk type: expected=0x%08x, got=0x%08x",
                    new Object[] { Integer.valueOf(expectedType), Short.valueOf((short) type) }));
    }

}
