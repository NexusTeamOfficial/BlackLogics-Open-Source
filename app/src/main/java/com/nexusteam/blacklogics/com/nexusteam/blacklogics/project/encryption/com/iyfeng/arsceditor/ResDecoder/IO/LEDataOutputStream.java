
package com.iyfeng.arsceditor.ResDecoder.IO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LEDataOutputStream {

    private DataOutputStream dos;

    public LEDataOutputStream(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void close() throws IOException {
        dos.flush();
        dos.close();
    }

    public int size() {
        return dos.size();
    }

    public void writeByte(byte b) throws IOException {
        dos.writeByte(b);
    }

    public void writeBytes(int length) throws IOException {
        for (int i = 0; i < length; i++)
            dos.writeByte(0);
    }

    public void writeCharArray(char[] charbuf) throws IOException {
        int length = charbuf.length;
        for (int i = 0; i < length; i++)
            writeShort((short) charbuf[i]);
    }

    public void writeFully(byte[] b) throws IOException {
        dos.write(b, 0, b.length);
    }

    public void writeFully(byte[] buffer, int offset, int count) throws IOException {
        dos.write(buffer, offset, count);
    }

    public void writeInt(int i) throws IOException {
        dos.writeByte(i & 0xff);
        dos.writeByte((i >> 8) & 0xff);
        dos.writeByte((i >> 16) & 0xff);
        dos.writeByte((i >> 24) & 0xff);
    }

    public void writeIntArray(int[] buf) throws IOException {
        writeIntArray(buf, 0, buf.length);
    }

    private void writeIntArray(int[] buf, int s, int end) throws IOException {
        for (; s < end; s++)
            writeInt(buf[s]);
    }

    public void writeNulEndedString(String name) throws IOException {
        char[] ch = name.toCharArray();
        int length = ch.length;

        for (int i = 0; i < length; i++)
            writeShort((short) ch[i]);

        writeBytes(128 * 2 - length * 2);
    }

    public void writeShort(short s) throws IOException {
        dos.writeByte(s & 0xff);
        dos.writeByte((s >>> 8) & 0xff);
    }
}
