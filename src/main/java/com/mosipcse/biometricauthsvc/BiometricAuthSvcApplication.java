package com.mosipcse.biometricauthsvc;

import com.mosipcse.fingerprintutils.FingerPrintHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = FingerPrintHandler.class)
@ComponentScan(basePackages = "com.mosipcse.biometricauthsvc")
public class BiometricAuthSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiometricAuthSvcApplication.class, args);
	}

}
