package com.ylz.packcommon.ctyunOos;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.*;

public class Common {
    // replace the ownerName , secret,and displayName with your own account.
    public static String ownerName = "12581ee91f8c4ee88506";
    public static String secret = "e08f5ed167a73ad1686ce8abf963b3fd9383e810";
    public static String displayName = "龚凌";
    final static String OOS_DOMAIN = "oos-fj2.ctyunapi.cn";

    public static AmazonS3Client getClient() {
        AmazonS3Client client = new AmazonS3Client(new AWSCredentials() {
            public String getAWSAccessKeyId() {
                return ownerName;
            }

            public String getAWSSecretKey() {
                return secret;
            }
        });
        client.setEndpoint(OOS_DOMAIN);
        return client;
    }

    public static File createSampleFile(String content) throws IOException {
        File file = File.createTempFile("oos-java-sdk-", ".txt");
        file.deleteOnExit();
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return file;
    }

//    public static File createSampleFileImage(String content) throws IOException {
//        String path = this.getSysDao().getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"), PropertiesUtil.getConfValue("filePictureHz"),vo.getPatientImageName());
//        FileUtils.decoderBase64File(vo.getPatientImageurl(),PropertiesUtil.getConfValue("filePicture")+path);
//    }

    public static void downloadInputStream(InputStream input, String fileName)
            throws IOException {
        int c;
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            while ((c = input.read()) != -1) {
                fos.write(c);
            }
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
