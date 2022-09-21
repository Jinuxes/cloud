package com.jinuxes.cloud.utils;

import com.jinuxes.cloud.exception.DirectoryAlreadyExistsException;
import com.jinuxes.cloud.exception.FileAlreadyExistsException;
import com.jinuxes.cloud.exception.FileNotExistException;
import com.jinuxes.cloud.exception.ParenPathDoesNotExistException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 操作文件的工具类，用户创建文件夹、删除文件夹等
 */
public class FileUtils {
    // private static String getServerRealPath(HttpSession session){
    //     ServletContext servletContext = session.getServletContext();
    //     String realPath = servletContext.getRealPath(File.separator);
    //     return realPath;
    // }

    // /**
    //  * 创建文件夹
    //  * @param relativePath 相对于服务器种项目路径中的files目录下的路径。
    //  * @param session
    //  */
    // public static void makeDirectory(String relativePath, HttpSession session) throws IOException {
    //     String realPath = getServerRealPath(session);
    //     String filesRootPath = realPath+"\\"+"files";
    //     String fullPath = filesRootPath + relativePath;
    //
    //     Path path = Paths.get(fullPath);
    //     if(!Files.exists(path)){
    //         Files.createDirectory(path);
    //     }
    // }

    /**
     * 判断路径是否存在
     */
    public static boolean isExist(String path){
        Path pathObj = Paths.get(path);
        return Files.exists(pathObj);
    }

    /**
     * 根据字符串路径创建文件夹
     */
    public static void makeDirectory(String absolutePath) throws IOException {
        Path pathObj = Paths.get(absolutePath);
        if(!Files.exists(pathObj)){
            try{
                Files.createDirectory(pathObj);
            }catch(NoSuchFileException e){
                throw new ParenPathDoesNotExistException(CloudConstant.PATHERROR);
            }
        }else{
            throw new DirectoryAlreadyExistsException(CloudConstant.PATHALREADYEXISTERROR);
        }
    }

    /**
     * 创建一个空文件，写入数据的操作让FileService处理，否则又要在创建文件这个函数里面传入文件内容的对象
     */
    public static java.io.File makeFile(String absolutePath) throws IOException {
        Path pathObj = Paths.get(absolutePath);
        if(!Files.exists(pathObj)){
            try{
                return Files.createFile(pathObj).toFile();
            }catch(NoSuchFileException e){
                throw new ParenPathDoesNotExistException(CloudConstant.PATHERROR);
            }

        }else{
            throw new FileAlreadyExistsException(CloudConstant.FILEALREADYEXISTERROR+": "+pathObj.getFileName());
        }
    }

    /**
     * 对文件进行压缩
     */
    public static void makeZipFile(List<String> filePaths, String zipFileSavePath) throws IOException {
        File zipFile = new File(zipFileSavePath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        for(String path:filePaths){
            java.io.File file = new File(path);
            // 读取需要压缩的文件
            InputStream inputStream = new FileInputStream(file);
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            // 读取文件写入到压缩包
            int temp = 0;
            byte[] bytes = new byte[1024*1024];
            while((temp=inputStream.read(bytes)) != -1){
                zipOutputStream.write(bytes,0,temp);
            }
            inputStream.close();
        }
        zipOutputStream.close();
    }

    /**
     * 对文件（文件夹）进行移动
     * @param fromPath 源文件或目录路径，不能是其父路径，一定要带上文件或目录名
     * @param toPath 目标位置路径，不能是其父路径，一定要带上文件或目录名
     */
    public static void moveFile(String fromPath, String toPath) throws IOException {
        if(!isExist(fromPath)){
            throw new FileNotExistException(CloudConstant.FILEORDIRNOTEXISTERROR);
        }
        try{
            Files.move(Paths.get(fromPath), Paths.get(toPath));
        }catch(java.nio.file.FileAlreadyExistsException e){
            throw new FileAlreadyExistsException(CloudConstant.FILEORDIRALREADYEXISTERROR);
        }
    }

    /**
     * 修改文件名
     * @param originPath 原文件路径
     * @param newPath 新文件路径
     */
    public static void renameFile(String originPath, String newPath){
        File file = new File(originPath);
        File newFile = new File(newPath);
        if(!file.exists()){
            throw new FileNotExistException(CloudConstant.FILENOTEXISTERROR);
        }
        if(newFile.exists()){
            throw new FileAlreadyExistsException(CloudConstant.FILEALREADYEXISTINDIRERROR);
        }
        file.renameTo(newFile);
    }
}
