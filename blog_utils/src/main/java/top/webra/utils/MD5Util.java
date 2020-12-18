package top.webra.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 辅助方法
     * @param filename
     * @return
     */
    private static byte[] createChecksum(String filename) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead = -1;

            while ((numRead = fis.read(buffer)) != -1) {
                complete.update(buffer, 0, numRead);
            }
            return complete.digest();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 生成文件的MD5码
     * @param filePath 文件路径
     * @return 该文件的MD5码
     */
    private static String generateMD5(String filePath){
        if (!new File(filePath).isFile()) {
            System.err.println("Error: " + filePath
                    + " is not a valid file.");
            return null;
        }
        byte[] b = createChecksum(filePath);
        if(null == b){
            System.err.println(("Error:create md5 string failure!"));
            return null;
        }
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return result.toString();
    }

    /**
     * 根据文件，在同一文件夹下生成相应的MD5文件，文件名相同，仅后缀名不同
     * 比如文件为helloworld.txt，生成MD5文件：helloworld.md5
     * @param filePath 文件绝对路径
     */
    public static void generateMD5File(String filePath) throws IOException {
        //获得该文件实例
        File file = new File(filePath);

        //获得该文件的MD5码
        String md5code = generateMD5(filePath);

        //获得该文件的文件夹路径
        String directoryPath = file.getParentFile().getAbsolutePath();

        //生成该文件对应的MD5文件的文件名
        String md5FileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + ".md5";

        //获得该文件对应的MD5文件的绝对路径
        String md5FilePath = directoryPath + File.separator + md5FileName;

        //生成MD5文件
        File md5File = new File(md5FilePath);
        if(!md5File.exists()){
            md5File.createNewFile();
        }

        //文件写入MD5码的FileWriter类
        FileWriter fileWriter = new FileWriter(md5File);
        //将MD5码写入到MD5文件中
        fileWriter.write(md5code);
        //刷新流
        fileWriter.flush();
        //关流
        fileWriter.close();

        //打印提示信息
        System.out.println("创建MD5文件成功：" + md5FilePath);
    }


    /**
     * 输入文件夹名称，将该文件夹下所有的文件都生成同名的MD5文件
     * @param directoryPath
     */
    public static void generateMD5Files(String directoryPath) throws IOException {
        //创建文件夹实例
        File directory = new File(directoryPath);
        //文件数组
        File[] files = null;

        if(directory.isDirectory()){
            //获得所有文件
            files = directory.listFiles();
            //分别为每一个文件创建MD5文件
            for(int i=0; i<files.length; i++){
                generateMD5File(files[i].getAbsolutePath());
            }
        }else{
            System.err.println("不是文件夹，请检查路径！");
            return;
        }

    }
}
