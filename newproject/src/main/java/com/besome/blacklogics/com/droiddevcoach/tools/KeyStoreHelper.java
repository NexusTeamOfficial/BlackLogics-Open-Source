package com.droiddevcoach.tools;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.math.BigInteger;
import sun1.security.provider.JavaProvider;
import sun1.security.x509.AlgorithmId;
import sun1.security.x509.CertificateAlgorithmId;
import sun1.security.x509.CertificateExtensions;
import sun1.security.x509.CertificateIssuerName;
import sun1.security.x509.CertificateSerialNumber;
import sun1.security.x509.CertificateSubjectName;
import sun1.security.x509.CertificateValidity;
import sun1.security.x509.CertificateVersion;
import sun1.security.x509.CertificateX509Key;
import sun1.security.x509.KeyIdentifier;
import sun1.security.x509.PrivateKeyUsageExtension;
import sun1.security.x509.SubjectKeyIdentifierExtension;
import sun1.security.x509.X500Name;
import sun1.security.x509.X509CertImpl;
import sun1.security.x509.X509CertInfo;

public class KeyStoreHelper {
    static {
        Security.addProvider(new JavaProvider());
    }

    public static void generate(KeyStoreHelper.Builder builder) throws IOException, GeneralSecurityException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(builder.alg.toString());
        keyPairGen.initialize(builder.size.getValue(), SecureRandom.getInstance("SHA1PRNG"));
        KeyPair keyPair = keyPairGen.generateKeyPair();

        X509Certificate cert = generateCertificate(
            new X500Name(builder.commonName, builder.organizationUnit, builder.organizationName, builder.cityOrLocality, builder.stateName, builder.countryCode),
            keyPair,
            builder.validity,
            builder.sigAlg.toString()
        );

        PrivateKey privateKey = keyPair.getPrivate();
        KeyStore keyStore = KeyStore.getInstance(builder.type.toString());
        keyStore.load(null, null);
        keyStore.setKeyEntry(builder.alias, privateKey, builder.keypass.toCharArray(), new Certificate[]{cert});

        if (builder.outputStream != null) {
            keyStore.store(builder.outputStream, builder.storepass.toCharArray());
        } else if (builder.outputFile != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(builder.outputFile)) {
                keyStore.store(fileOutputStream, builder.storepass.toCharArray());
            }
        } else {
            throw new IOException("No valid output destination set for KeyStore.");
        }
    }

    private static X509Certificate generateCertificate(X500Name x500Name, KeyPair keyPair, int validityYears, String sigAlgorithm) throws GeneralSecurityException, IOException {
        PrivateKey privateKey = keyPair.getPrivate();
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + (long) validityYears * 365 * 24 * 60 * 60 * 1000L);
        CertificateValidity certValidity = new CertificateValidity(startDate, endDate);
        BigInteger serialNumber = new BigInteger(64, new SecureRandom());

        X509CertInfo certInfo = new X509CertInfo();
        certInfo.set(X509CertInfo.VALIDITY, certValidity);
        certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(serialNumber));
        certInfo.set(X509CertInfo.SUBJECT, new CertificateSubjectName(x500Name));
        certInfo.set(X509CertInfo.ISSUER, new CertificateIssuerName(x500Name));
        certInfo.set(X509CertInfo.KEY, new CertificateX509Key(keyPair.getPublic()));
        certInfo.set(X509CertInfo.VERSION, new CertificateVersion(2));
        certInfo.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(AlgorithmId.get(sigAlgorithm)));

        CertificateExtensions certExtensions = new CertificateExtensions();
        certExtensions.set(SubjectKeyIdentifierExtension.NAME, new SubjectKeyIdentifierExtension(new KeyIdentifier(keyPair.getPublic()).getIdentifier()));
        certExtensions.set(PrivateKeyUsageExtension.NAME, new PrivateKeyUsageExtension(startDate, endDate));
        certInfo.set(X509CertInfo.EXTENSIONS, certExtensions);

        X509CertImpl cert = new X509CertImpl(certInfo);
        cert.sign(privateKey, sigAlgorithm);

        return cert;
    }

    public static class Builder {
        public KeyStoreHelper.Algorithm alg;
        public String alias;
        public String cityOrLocality;
        public String commonName;
        public String countryCode;
        public String keypass;
        public String organizationName;
        public String organizationUnit;
        public File outputFile;
        public OutputStream outputStream; // Added for OutputStream support
        public KeyStoreHelper.SigAlgorithm sigAlg;
        public KeyStoreHelper.Size size;
        public String stateName;
        public String storepass;
        public KeyStoreHelper.Type type;
        public int validity;

        // New setter for OutputStream
        public void setOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        // Existing setters for other fields
        public Builder setAlg(KeyStoreHelper.Algorithm alg) {
            this.alg = alg;
            return this;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder setCityOrLocality(String cityOrLocality) {
            this.cityOrLocality = cityOrLocality;
            return this;
        }

        public Builder setCommonName(String commonName) {
            this.commonName = commonName;
            return this;
        }

        public Builder setCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder setKeypass(String keypass) {
            this.keypass = keypass;
            return this;
        }

        public Builder setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder setOrganizationUnit(String organizationUnit) {
            this.organizationUnit = organizationUnit;
            return this;
        }

        public Builder setOutputFile(File outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public Builder setSigAlg(KeyStoreHelper.SigAlgorithm sigAlg) {
            this.sigAlg = sigAlg;
            return this;
        }

        public Builder setSize(KeyStoreHelper.Size size) {
            this.size = size;
            return this;
        }

        public Builder setStateName(String stateName) {
            this.stateName = stateName;
            return this;
        }

        public Builder setStorepass(String storepass) {
            this.storepass = storepass;
            return this;
        }

        public Builder setType(KeyStoreHelper.Type type) {
            this.type = type;
            return this;
        }

        public Builder setValidity(int validity) {
            this.validity = validity;
            return this;
        }

        public KeyStoreHelper build() {
            return new KeyStoreHelper();
        }
    }

    // Enum definitions for Algorithm, SigAlgorithm, Size, Type
    public enum Algorithm {
        RSA("RSA"),
        DSA("DSA");

        private final String value;

        Algorithm(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public enum SigAlgorithm {
        SHA256withRSA("SHA256withRSA"),
        SHA1withDSA("SHA1withDSA");

        private final String value;

        SigAlgorithm(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public enum Size {
        SIZE_2048(2048),
        SIZE_1024(1024);

        private final int value;

        Size(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Type {
        JKS("JKS"),
        PKCS12("PKCS12");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
