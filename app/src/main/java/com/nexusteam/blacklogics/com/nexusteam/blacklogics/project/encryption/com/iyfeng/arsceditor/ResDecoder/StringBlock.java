/**
 *  Copyright 2011 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Modified Copyright 2015 com.iyfengHai <2801045898@qq.com>
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.iyfeng.arsceditor.ResDecoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

import com.iyfeng.arsceditor.ResDecoder.IO.LEDataInputStream;
import com.iyfeng.arsceditor.ResDecoder.IO.LEDataOutputStream;


/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 * @author Dmitry Skiba
 * @author com.iyfengHai
 * 
 *         Block of strings, used in binary xml and arsc.
 * 
 *         TODO: - implement get()
 * 
 */
public class StringBlock {

    private static final int CHUNK_NULL_TYPE = 0x00000000;

    private static final int CHUNK_STRINGPOOL_TYPE = 0x001C0001;


    private static final CharsetDecoder UTF16LE_DECODER = Charset.forName("UTF-16LE").newDecoder();


    private static final CharsetDecoder UTF8_DECODER = Charset.forName("UTF-8").newDecoder();

    private static final int UTF8_FLAG = 0x00000100;

    private static final int getShort(byte[] array, int offset) {
        return (array[offset + 1] & 0xff) << 8 | array[offset] & 0xff;
    }

    private static final int[] getVarint(byte[] array, int offset) {
        int val = array[offset];
        boolean more = (val & 0x80) != 0;
        val &= 0x7f;

        if (!more) {
            return new int[] { val, 1 };
        } else {
            return new int[] { val << 8 | array[offset + 1] & 0xff, 2 };
        }
    }

    /**
     * Reads whole (including chunk type) string block from stream. Stream must
     * be at the chunk type.
     */
    public static StringBlock read(LEDataInputStream reader) throws IOException {
        StringBlock block = new StringBlock();

        block.ChunkTypeInt = reader.skipCheckChunkTypeInt(CHUNK_STRINGPOOL_TYPE, CHUNK_NULL_TYPE);

        block.chunkSize = reader.readInt();


        block.stringCount = reader.readInt();


        block.styleOffsetCount = reader.readInt();


        block.flags = reader.readInt();


        block.stringsOffset = reader.readInt();


        block.stylesOffset = reader.readInt();


        block.m_isUTF8 = (block.flags & UTF8_FLAG) != 0;

        block.m_stringOffsets = reader.readIntArray(block.stringCount);


        if (block.styleOffsetCount != 0) {

            block.m_styleOffsets = reader.readIntArray(block.styleOffsetCount);


        }

        {
            int size = ((block.stylesOffset == 0) ? block.chunkSize : block.stylesOffset) - block.stringsOffset;
            if ((size % 4) != 0) {
                throw new IOException("String data size is not multiple of 4 (" + size + ").");
            }
            block.m_strings = new byte[size];
            reader.readFully(block.m_strings);


            block.strings = new ArrayList<String>();
            for (int i = 0; i < block.stringCount; i++) {
                block.strings.add(block.getString(i));
            }
        }

        if (block.stylesOffset != 0) {
            int size = (block.chunkSize - block.stylesOffset);
            if ((size % 4) != 0) {
                throw new IOException("Style data size is not multiple of 4 (" + size + ").");
            }
            block.m_styles = reader.readIntArray(size / 4);


            int remaining = size % 4;
            if (remaining >= 1) {
                while (remaining-- > 0) {
                    reader.skipByte();
                }
            }
        }

        return block;
    }


    private int chunkSize;


    private int ChunkTypeInt;


    private int flags;


    private boolean m_isUTF8;


    private int[] m_stringOffsets;

    public byte[] m_strings;


    private int[] m_styleOffsets;

    private int[] m_styles;

    private int stringCount;

    private int[] stringOffsets;

    private List<String> strings;

    private int stringsOffset;

    private int styleOffsetCount;

    private int stylesOffset;


    private String decodeString(int offset, int length) throws CharacterCodingException {
        return (m_isUTF8 ? UTF8_DECODER : UTF16LE_DECODER).decode(ByteBuffer.wrap(m_strings, offset, length))
                .toString();

    }

    /**
     * Finds index of the string. Returns -1 if the string was not found.
     */
    public int find(String string) {
        if (string == null) {
            return -1;
        }
        for (int i = 0; i != m_stringOffsets.length; ++i) {
            int offset = m_stringOffsets[i];
            int length = getShort(m_strings, offset);
            if (length != string.length()) {
                continue;
            }
            int j = 0;
            for (; j != length; ++j) {
                offset += 2;
                if (string.charAt(j) != getShort(m_strings, offset)) {
                    break;
                }
            }
            if (j == length) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取字符串数量
     */
    public int getCount() {
        return m_stringOffsets != null ? m_stringOffsets.length : 0;
    }


    public List<String> getList() {
        return strings;
    }

    /**
     * 获取常量池中第index个字符串的方法
     * 
     * @throws CharacterCodingException
     */
    public String getString(int index) throws CharacterCodingException {

        if (index < 0 || m_stringOffsets == null || index >= m_stringOffsets.length) {

            return null;
        }

        int offset = m_stringOffsets[index];

        int length;

        if (!m_isUTF8) {

            length = getShort(m_strings, offset) * 2;

            offset += 2;
        } else { // 如果是UTF-8型

            offset += getVarint(m_strings, offset)[1];
            int[] varint = getVarint(m_strings, offset);

            offset += varint[1];

            length = varint[0];
        }

        return decodeString(offset, length);
    }



    public void sortStringBlock(String src, String tar) {

        int position = strings.indexOf(src);

        if (position >= 0 && !tar.equals("")) {



            strings.set(position, tar);
        }
    }

    public void writeFully(LEDataOutputStream lmOut, ByteArrayOutputStream bOut) throws IOException {

        int newStylesOffset = 0;

        int newChunkSize = chunkSize;


        if (stylesOffset == 0)


            newChunkSize = newChunkSize + (bOut.size() - m_strings.length);
        else { // 如果字符串样式偏移不为0


            newChunkSize = newChunkSize + (bOut.size() - m_strings.length);
            newStylesOffset = stylesOffset + (bOut.size() - m_strings.length);
        }

        lmOut.writeInt(ChunkTypeInt);

        lmOut.writeInt(newChunkSize);


        lmOut.writeInt(stringCount);


        lmOut.writeInt(styleOffsetCount);


        lmOut.writeInt(flags);


        lmOut.writeInt(stringsOffset);


        lmOut.writeInt(newStylesOffset);


        lmOut.writeIntArray(stringOffsets);


        if (styleOffsetCount != 0) {

            lmOut.writeIntArray(m_styleOffsets);
        }

        lmOut.writeFully(bOut.toByteArray());

        if (stylesOffset != 0) {

            lmOut.writeIntArray(m_styles);
            int size = (chunkSize - stylesOffset);

            int remaining = size % 4;
            if (remaining >= 1) {
                while (remaining-- > 0) {

                    lmOut.writeByte((byte) 0);
                }
            }
        }
    }

    public ByteArrayOutputStream writeString(List<String> stringlist) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        LEDataOutputStream mStrings = new LEDataOutputStream(bOut);


        int size = stringlist.size();


        stringOffsets = new int[size];

        int len = 0;

        for (int i = 0; i < size; i++) {

            String var = stringlist.get(i);

            int length = var.length();

            stringOffsets[i] = len;

            writeString(var, mStrings);

            if ((flags & UTF8_FLAG) == 0) {

                if (length > 0x00007fff) {

                    len += 2;
                }

                len += 2;

                len += var.getBytes("UTF-16LE").length;

                len += 2;
            } else { // 如果是UTF-8型
                if (length > 0x0000007f) {

                    len += 1;
                }

                len += 1;

                byte[] bytes = var.getBytes("UTF8");

                length = bytes.length;
                if (length > 0x0000007f) {

                    len += 1;
                }

                len += 1;

                len += length;

                len += 1;
            }
        }


        /*
         * while (mStrings.size()%4!=0) { mStrings.writeByte((byte)0); }
         */
        int size_mod = mStrings.size() % 4;// m_strings_size%4

        for (int i = 0; i < 4 - size_mod; i++) {
            mStrings.writeByte((byte) 0);
        }
        bOut.close();
        return bOut;
    }

    /**
     * 写入第index个字符串的方法
     * 
     * @throws IOException
     */
    private void writeString(String str, LEDataOutputStream lmString) throws IOException {

        int length = str.length();

        if ((flags & UTF8_FLAG) == 0) {

            if (length > 0x00007fff) {
                int i5 = 0x00008000 | (length >> 16);
                lmString.writeByte((byte) i5);
                lmString.writeByte((byte) (i5 >> 8));
            }
            lmString.writeByte((byte) length);
            lmString.writeByte((byte) (length >> 8));
            lmString.writeFully(str.getBytes("UTF-16LE"));
            lmString.writeByte((byte) 0);
            lmString.writeByte((byte) 0);
        } else { // 如果是UTF-8型
            if (length > 0x0000007f) {
                lmString.writeByte((byte) ((length >> 8) | 0x00000080));
            }
            lmString.writeByte((byte) length);
            byte[] bytes = str.getBytes("UTF8");
            length = bytes.length;
            if (length > 0x0000007f) {
                lmString.writeByte((byte) ((length >> 8) | 0x00000080));
            }
            lmString.writeByte((byte) length);

            lmString.writeFully(bytes);
            lmString.writeByte((byte) 0);
        }
    }
}
