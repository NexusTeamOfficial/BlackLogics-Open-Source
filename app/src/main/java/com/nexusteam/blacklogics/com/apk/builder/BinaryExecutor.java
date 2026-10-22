package com.apk.builder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BinaryExecutor {

    private final ProcessBuilder mProcess = new ProcessBuilder();
    private String mLastOutput = "";

    public void setCommands(ArrayList<String> arrayList) {
        mProcess.command(arrayList);
    }

    public String execute() {
        final StringWriter writer = new StringWriter();

        try {
            final Process process = mProcess.start();

            Thread stdoutDrain = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner s = new Scanner(process.getInputStream());
                    while (s.hasNextLine()) {
                        s.nextLine();
                    }
                }
            });
            stdoutDrain.setDaemon(true);
            stdoutDrain.start();

            Scanner scanner = new Scanner(process.getErrorStream());
            while (scanner.hasNextLine()) {
                writer.append(scanner.nextLine());
                writer.append(System.lineSeparator());
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(writer));
        }

        mLastOutput = writer.toString();
        return mLastOutput;
    }

    public String getLog() {
        return mLastOutput;
    }
}
