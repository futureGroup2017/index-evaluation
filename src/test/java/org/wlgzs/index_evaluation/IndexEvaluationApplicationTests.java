package org.wlgzs.index_evaluation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexEvaluationApplicationTests {

    @Test //java io 解压zip文件
    public void contextLoads() throws IOException {
          /*  String zipPath  = "F:\\招生就业处资料\\2014\\文768人.zip";
            String outPath = "F:\\招生就业处资料\\";
        ZipFile zipFile = new ZipFile(zipPath,"GBK");
        for (Enumeration<ZipEntry> enumeration = zipFile.getEntries(); enumeration.hasMoreElements();){
            ZipEntry zipEntry = enumeration.nextElement(); //获取zipFile中元素
            if (!zipEntry.getName().endsWith(File.separator)){
                System.out.println("正在解压文件: "+zipEntry.getName());
                File f = new File(outPath+zipEntry.getName());
                if (!f.getParentFile().exists()){
                    f.getParentFile().mkdirs();
                }
                OutputStream os  = new FileOutputStream(outPath+zipEntry.getName());
                BufferedOutputStream  bfo = new  BufferedOutputStream(os);
                InputStream is  = zipFile.getInputStream(zipEntry); //读取元素
                BufferedInputStream bfi = new BufferedInputStream(is);
                CheckedInputStream cos = new CheckedInputStream(bfi,new CRC32()); //检查读取流，采用CRC32算法，保证文件的一致性
                byte[] b = new byte[1024]; //字节数组，每次读取1024个字节
                //循环读取压缩文件的值
                while (cos.read(b)!=-1){
                    bfo.write(b); //写入到新文件
                }
                cos.close();
                bfi.close();
                is.close();
                bfo.close();
                os.close();
            }else {
                //如果为空文件夹，则创建该文件夹
                new File(outPath+zipEntry.getName()).mkdirs();

            }
        }
        System.out.println("解压完成");
        zipFile.close();*/
        File file  = new File("./upload");
        System.out.println(file.isDirectory());
        System.out.println(deleteDir(file));

    }
    boolean deleteDir(File dir){
        if (dir.isDirectory()){
            String[] childens = dir.list();
            for (int i = 0; i <childens.length ; i++) {
                boolean isTrue = deleteDir(new File(dir,childens[i]));
                if (!isTrue){
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
