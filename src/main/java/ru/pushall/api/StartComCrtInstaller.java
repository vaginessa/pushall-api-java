package ru.pushall.api;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

class StartComCrtInstaller
{
	static void installCA()
	{
		try
		{
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			Path ksPath = Paths.get(System.getProperty("java.home"),
					"lib", "security", "cacerts");
			keyStore.load(Files.newInputStream(ksPath),
					"changeit".toCharArray());

			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			try(InputStream caInput = new BufferedInputStream(StartComCrtInstaller.class.getResourceAsStream("/ru/pushall/crt/StartCom.pem")))
			{
				Certificate crt = cf.generateCertificate(caInput);
				keyStore.setCertificateEntry("StartCom", crt);
			}

			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tmf.getTrustManagers(), null);
			SSLContext.setDefault(sslContext);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Failed to install StartCom root CA", e);
		}
	}
}
