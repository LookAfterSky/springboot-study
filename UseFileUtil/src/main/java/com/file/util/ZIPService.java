package com.file.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZIPService {
	public static void readZipFile(File file,String destPath) {
        File destDir = new File(destPath);
        try (ZipFile zipFile = new ZipFile(file,Charset.forName("GBK"))) {
            for (Enumeration entries = zipFile.entries(); entries.hasMoreElements(); ) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (zipEntry.isDirectory()) {

                } else {
                    String s = destDir.getAbsolutePath() + "/" + zipEntry.getName();
                    File destDirs = new File(s.substring(0, s.lastIndexOf("/")));
                    if (!destDirs.exists()) {
                        destDirs.mkdirs();
                    }
                    System.out.println(zipEntry.getName() + ":" + zipEntry.getSize());
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    FileOutputStream fos = new FileOutputStream(new File(s));
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = inputStream.read(bytes))>0) {
                        fos.write(bytes,0,len);
                    }
                    inputStream.close();
                    fos.close();
                }
            }
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		System.out.println("1111");
		readZipFile(new File("C:\\Users\\HXX\\Desktop\\testA/PDDA201937052300000318v01.zip"), "src/data");
	}
}
