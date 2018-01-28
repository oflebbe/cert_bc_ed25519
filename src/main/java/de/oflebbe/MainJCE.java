package de.oflebbe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.*;
import java.security.cert.*;

public class MainJCE {

    public static void main(String[] args) {
        //      Security.addProvider( new BouncyCastleProvider());

        CertificateFactory fact = null;
        try {
            fact = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        X509Certificate cert = null;
        try {
            cert = (X509Certificate) fact.generateCertificate(is);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        System.out.println(cert.getIssuerX500Principal());
        PublicKey key = cert.getPublicKey();

        System.out.println(key.toString());

        System.out.println(cert.getSigAlgOID());
        System.out.println(cert.getSigAlgName());
        System.out.println(cert.getSigAlgParams());

        try {
            cert.checkValidity();
        } catch (CertificateExpiredException e) {
            e.printStackTrace();
        } catch (CertificateNotYetValidException e) {
            e.printStackTrace();
        }
    }
}
