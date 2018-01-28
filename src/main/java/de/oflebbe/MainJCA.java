package de.oflebbe;

import net.i2p.crypto.eddsa.EdDSASecurityProvider;
import org.bouncycastle.cert.CertException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Date;

public class MainJCA {

    public static void main(String[] args) {
	    // write your code here

        Security.addProvider( new BouncyCastleProvider());
        Security.addProvider( new EdDSASecurityProvider());

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PEMParser pemReader = new PEMParser(fileReader);
        Object obj = null;
        try {
            obj = pemReader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            pemReader.close(); // sloppy IO handling, be thorough in production code
        } catch (IOException e) {
            e.printStackTrace();
        }

        X509CertificateHolder cert = (X509CertificateHolder) obj;
        System.out.println(cert.getIssuer());
        System.out.println(cert.getSignatureAlgorithm().getAlgorithm());
        System.out.println(cert.getSubjectPublicKeyInfo().getAlgorithm().getAlgorithm());
        try {
            ContentVerifierProvider contentVerifierProvider = new JcaContentVerifierProviderBuilder()
                    .build(cert);
            System.out.println(cert.isSignatureValid(contentVerifierProvider));
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (CertException e) {
            e.printStackTrace();
        }

        System.out.println(cert.isValidOn( new Date()));
    }
}