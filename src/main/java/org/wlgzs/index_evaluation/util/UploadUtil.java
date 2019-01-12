package org.wlgzs.index_evaluation.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class UploadUtil {
    // 文件写入
    public void saveFile(MultipartFile file, String filePath) {
        try {
            if (!file.isEmpty()) {
                File saveFile = new File("." + filePath);
                if (!saveFile.getParentFile().exists()) {
                    saveFile.getParentFile().mkdirs();
                }
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                BufferedOutputStream out = new BufferedOutputStream(outputStream);
                out.write(file.getBytes());
                out.flush();
                out.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //删除文件
    public boolean deleteFile(String url) {
        File file = new File("." + url);
        if (file.exists() && file.exists()) {
            return file.delete();
        }
        return false;
    }
    // 删除文件夹
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children!=null) {
                for (String aChildren : children) {
                    boolean success = deleteDir(new File(dir, aChildren));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        //目录此时为空，可以删除
        return dir.delete();
    }
}
