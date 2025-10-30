package com.besome.blacklogics.logic.editor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import com.besome.blacklogics.R;

public class StringUtil {
    public static String clipCurrencyCode(String str) {
        String str2 = "";
        for (char c : str.toCharArray()) {
            if (c >= '0' && c <= '9') {
                return str2 + c;
            }
        }
        return str2;
    }

    public static void copyToClipboard(Context context, String str, String str2) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(str, str2));
    }

    public static String encryptSha256(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String escape(String str) {
        return str.replaceAll("/[\\%@]/g", "\\$&");
    }

    public static String formatCurrency(int i) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.KOREA);
        currencyInstance.setMinimumFractionDigits(0);
        currencyInstance.setMaximumFractionDigits(0);
        return currencyInstance.format((long) i);
    }

    public static String getComma(int i) {
        return NumberFormat.getInstance().format((long) i);
    }

    public static String getComma(String str) {
        return getComma(Integer.parseInt(str));
    }

    public static int parseCurrency(String str) {
        try {
            return NumberFormat.getCurrencyInstance(Locale.KOREA).parse(str).intValue();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static int removeComma(String str) {
        try {
            return NumberFormat.getInstance().parse(str).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String shortenNumber(int i) {
        if (((float) i) >= 1000.0f && ((float) i) < 1000000.0f) {
            return new DecimalFormat("#.#K").format((double) (((float) i) / 1000.0f));
        } else if (((float) i) >= 1000000.0f && ((float) i) < 1.0E9f) {
            return new DecimalFormat("#.#M").format((double) (((float) i) / 1000000.0f));
        } else if (((float) i) < 1.0E9f || ((float) i) >= 1.0E12f) {
            return String.valueOf(i);
        } else {
            return new DecimalFormat("#.#G").format((double) (((float) i) / 1.0E9f));
        }
    }

    public static ArrayList<String> tokenize(String str) {
        ArrayList<String> arrayList = new ArrayList();
        TokenParser tokenParser = new TokenParser(str);
        while (!tokenParser.atEnd()) {
            String nextToken = tokenParser.nextToken();
            if (nextToken.length() > 0) {
                arrayList.add(nextToken);
            }
        }
        return arrayList;
    }
    
    static class TokenParser {
        private int i = 0;
        private String src;

        public TokenParser(String str) {
            this.src = str;
        }

        public boolean atEnd() {
            return this.i >= this.src.length();
        }

        public String next() {
            if (this.i >= this.src.length()) {
                return "";
            }
            String str = this.src;
            int i = this.i;
            this.i = i + 1;
            return String.valueOf(str.charAt(i));
        }

        public String nextToken() {
            skipWhiteSpace();
            if (atEnd()) {
                return "";
            }
            String str = "";
            Object obj = null;
            int i = this.i;
            while (this.i < this.src.length() && this.src.charAt(this.i) != ' ') {
                char charAt = this.src.charAt(this.i);
                if (charAt == '\\') {
                    str = str + (charAt + this.src.charAt(this.i + 1));
                    this.i += 2;
                } else {
                    if (charAt == '%') {
						if (this.i > i) {
							return str;
						}
						obj = 1;
					}
					if (obj != null && (charAt == '?' || charAt == '-')) {
						return str;
					}
					str = str + charAt;
					this.i++;
                }
            }
            return str;
        }

        public void skipWhiteSpace() {
            while (this.i < this.src.length() && this.src.charAt(this.i) == ' ') {
                this.i++;
            }
        }
    }

    public static String unescape(String str) {
        String str2 = "";
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '\\') {
                str2 = str2 + str.charAt(i + 1);
                i++;
            } else {
                str2 = str2 + charAt;
            }
            i++;
        }
        return str2;
    }
}
