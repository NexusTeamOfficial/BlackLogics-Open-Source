package apksigner.io;

import java.io.IOException;


public class CentralEnd 
{
    private int signature = 0x06054b50;
    private short numberThisDisk = 0;
    private short centralStartDisk = 0;
    short numCentralEntries;
    short totalCentralEntries;

    public int centralDirectorySize;
    public int centralStartOffset;
    public String fileComment;


    public static CentralEnd read(ZipInput input) throws IOException
    {

        int signature = input.readInt();
        if (signature != 0x06054b50) {

            input.seek( input.getFilePointer() - 4);
            return null;
        }

        CentralEnd entry = new CentralEnd();

        entry.doRead( input);
        return entry;
    }

    private void doRead( ZipInput input) throws IOException
    {


        numberThisDisk = input.readShort();

        centralStartDisk = input.readShort();

        numCentralEntries = input.readShort();

        totalCentralEntries = input.readShort();

        centralDirectorySize = input.readInt();

        centralStartOffset = input.readInt();

        short zipFileCommentLen = input.readShort();
        fileComment = input.readString(zipFileCommentLen);


    }


    public void write( ZipOutput output) throws IOException
    {


        output.writeInt( signature);
        output.writeShort( numberThisDisk);
        output.writeShort( centralStartDisk);
        output.writeShort( numCentralEntries);
        output.writeShort( totalCentralEntries);
        output.writeInt( centralDirectorySize );
        output.writeInt( centralStartOffset );
        output.writeShort( (short)fileComment.length());
        output.writeString( fileComment);


    }

}
