package org.cloudbus.cloudsim.examples;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileEncryptionDemo {

    public static void main(String[] args) {
        try {
            // 👉 Project root folder (your Cloudsim folder)
            Path projectRoot = Paths.get("").toAbsolutePath();

            // 👉 File paths (all will be created in Cloudsim/)
            Path inputPath = projectRoot.resolve("input.txt");
            Path encryptedPath = projectRoot.resolve("encrypted.txt");
            Path decryptedPath = projectRoot.resolve("decrypted.txt");

            // 👉 Ensure input file exists (auto-create if missing)
            if (!Files.exists(inputPath)) {
                Files.write(inputPath, "Hello this is my cloud project".getBytes());
                System.out.println("Created input.txt automatically.");
            }

            // 👉 Read input file
            byte[] data = Files.readAllBytes(inputPath);

            // 👉 Generate AES key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();

            // 👉 Encrypt
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data);

            Files.write(encryptedPath, encryptedData);

            // 👉 Decrypt
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            Files.write(decryptedPath, decryptedData);

            // 👉 Output paths so you KNOW where files are
            System.out.println("\nFILES CREATED HERE:");
            System.out.println("Input File:     " + inputPath.toAbsolutePath());
            System.out.println("Encrypted File: " + encryptedPath.toAbsolutePath());
            System.out.println("Decrypted File: " + decryptedPath.toAbsolutePath());

            System.out.println("\nFile encryption & decryption successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}