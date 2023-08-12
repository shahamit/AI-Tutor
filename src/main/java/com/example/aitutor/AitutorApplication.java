package com.example.aitutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AitutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AitutorApplication.class, args);

		String code = "public class Factorial {\n" +
				"\n" +
				"    public static void main(String[] args) {\n" +
				"\n" +
				"        int num = 10;\n" +
				"        long factorial = 1;\n" +
				"        for(int i = 1; i <= num; ++i)\n" +
				"        {\n" +
				"            // factorial = factorial * i;\n" +
				"            factorial *= i;\n" +
				"        }\n" +
				"        System.out.printf(\"Factorial of %d = %d\", num, factorial);\n" +
				"    }\n" +
				"}";
	}

}
