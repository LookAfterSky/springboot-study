package com.file.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompress {
    private String zipFileName;      // Ŀ�ĵ�Zip�ļ�
    private String sourceFileName;   //Դ�ļ�����ѹ�����ļ����ļ��У�

    public ZipCompress(String zipFileName, String sourceFileName) {
        this.zipFileName = zipFileName;
        this.sourceFileName = sourceFileName;
    }

    public void zip(){
        File zipFile = new File(sourceFileName);
        File[] files = zipFile.listFiles();
        if (files[0].isDirectory()){
            sourceFileName = files[0].getAbsolutePath();
        }
        System.out.println("ѹ����...");

        //����zip�����
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File sourceFile = new File(sourceFileName);

        //���ú���
        try {
            compress(out,  sourceFile, sourceFile.getName());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ѹ�����");

    }

    public void compress(ZipOutputStream out, File sourceFile, String base) throws Exception {
        //���·��ΪĿ¼���ļ��У�
        if (sourceFile.isDirectory()) {

            //ȡ���ļ����е��ļ��������ļ��У�
            File[] flist = sourceFile.listFiles();

            if (flist.length == 0)//����ļ���Ϊ�գ���ֻ����Ŀ�ĵ�zip�ļ���д��һ��Ŀ¼�����
            {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            } else//����ļ��в�Ϊ�գ���ݹ����compress���ļ����е�ÿһ���ļ������ļ��У�����ѹ��
            {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, flist[i], base + "/" + flist[i].getName());
                }
            }
        } else//�������Ŀ¼���ļ��У�����Ϊ�ļ�������д��Ŀ¼����㣬֮���ļ�д��zip�ļ���
        {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            //��Դ�ļ�д�뵽zip�ļ���
            while ((tag = bis.read()) != -1) {
                out.write(tag);
            }
            bis.close();
            fos.close();

        }
    }
}

