package com.file.util;

import java.io.*;

/**
 * 复制文件夹及其文本文件
 * Create By: Hxx
 */
public class FileCopy {
    public void toUnixFile(File file, File targetFile) {
        AccessFileCode accessFileCode = new AccessFileCode();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(targetFile);
            String fileCode = accessFileCode.getFileCode(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, fileCode);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, "gbk");
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line + "\n");
            }
            bufferedReader.close();
            bufferedWriter.close();
            outputStreamWriter.close();
            inputStreamReader.close();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 复制文件夹
    public void copyDirectiory(String sourceDir, String targetDir) {
        // 新建目标目录
        File newDir = new File(targetDir);
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new
                        File(new File(targetDir).getAbsolutePath()
                        + File.separator + file[i].getName());
                toUnixFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
}
