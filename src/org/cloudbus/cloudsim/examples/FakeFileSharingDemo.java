package org.cloudbus.cloudsim.examples;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.file.*;

public class FakeFileSharingDemo {

    public static void main(String[] args) {
        try {
            // 👉 FORCE FILES INSIDE PROJECT (Cloudsim folder)
            Path root = Paths.get(System.getProperty("user.dir")).resolve("project_files");

            // Create folder if not exists
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            Path input = root.resolve("input.txt");
            Path encrypted = root.resolve("encrypted.txt");
            Path cloudFolder = root.resolve("cloud_storage");
            Path cloudFile = cloudFolder.resolve("uploaded.enc");
            Path downloaded = root.resolve("downloaded.enc");
            Path decrypted = root.resolve("decrypted.txt");

            if (!Files.exists(input)) {
                Files.write(input, "Hello this is my cloud project".getBytes());
            }

            if (!Files.exists(cloudFolder)) {
                Files.createDirectories(cloudFolder);
            }

            byte[] data = Files.readAllBytes(input);

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();

            Cipher cipher = Cipher.getInstance("AES");

            // ENCRYPT
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data);
            Files.write(encrypted, encryptedData);

            // UPLOAD (copy)
            Files.copy(encrypted, cloudFile, StandardCopyOption.REPLACE_EXISTING);

            // DOWNLOAD
            Files.copy(cloudFile, downloaded, StandardCopyOption.REPLACE_EXISTING);

            // DECRYPT
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(Files.readAllBytes(downloaded));
            Files.write(decrypted, decryptedData);

            System.out.println("\nFILES STORED INSIDE PROJECT HERE:");
            System.out.println(root.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}