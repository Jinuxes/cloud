package com.jinuxes.cloud.service.Impl;

import com.jinuxes.cloud.entity.File;
import com.jinuxes.cloud.exception.DirectoryHierarchyIsTooDeepException;
import com.jinuxes.cloud.exception.FileNotExistException;
import com.jinuxes.cloud.exception.RecoveryFileException;
import com.jinuxes.cloud.mapper.FileMapper;
import com.jinuxes.cloud.service.api.FileService;
import com.jinuxes.cloud.utils.CloudConstant;
import com.jinuxes.cloud.utils.DateUtil;
import com.jinuxes.cloud.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager; // 批处理数据时，手动提交、回滚事务使用

    @Override
    public void saveFile(File file, HttpSession session){
        // 将信息插入数据库
        // 虽然信息插入数据库操作比在真实路径上创建文件夹的操作早，但是因为已经配置好事务，
        // 所以service层即使在下面代码中创建目录失败抛出异常后，也会进行回滚。
        // 因此创建目录失败后不需要手工去数据库删除信息。
        if(file.getPath().length() > CloudConstant.MAXPATHLENGTH){
            throw new DirectoryHierarchyIsTooDeepException(CloudConstant.PATHTOOLONG);
        }
        fileMapper.insertSelective(file);
        // 在项目的files目录下创建对应的路径下的文件夹
        try {
            // 获取路径+文件名
            String filesRootPath = getFilesRootPath(session);
            String absolutePath = filesRootPath + file.getPath() + file.getName();
            // System.out.println(absolutePath);
            FileUtils.makeDirectory(absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<File> getFilesByPath(String homePath) {
        return fileMapper.selectFilesByPath(homePath);
    }

    @Override
    public File getHomePathInfoByPathAndOwner(String homePath, String account) {
        return fileMapper.selectHomePathInfoByPathAndOwner(homePath, account);
    }

    @Override
    public void saveUploadFile(MultipartFile multipartFile, File file, HttpSession session) {
        if(file.getPath().length() > CloudConstant.MAXPATHLENGTH){
            throw new DirectoryHierarchyIsTooDeepException(CloudConstant.PATHTOOLONG);
        }
        fileMapper.insertSelective(file);

        // 在项目的files目录下保存文件
        // 获取路径+文件名
        String filesRootPath = getFilesRootPath(session);
        String absolutePath = filesRootPath + file.getPath() + file.getName();
        // System.out.println(absolutePath);
        try {
            // 创建空文件
            java.io.File fileObj = FileUtils.makeFile(absolutePath);
            // 将数据写入空文件
            multipartFile.transferTo(fileObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<File> getAllFoldersByOwner(String owner) {
        return fileMapper.selectAllFoldersByOwner(owner);
    }

    @Override
    public File getFileById(String fileId) {
        return fileMapper.selectFileByFileId(fileId);
    }

    // 移动目录
    @Override
    public void updateParentIdAndFilesPath(String account, String name, String fileId, String path, String toFileId, String toPath, HttpSession session) {
        // 更新数据库中原目录的parentId字段以及path字段
        // 目标路径加上账号(数据库中path保存的路径是相对于files目录来说的，\就是files目录)
        String targetPath = java.io.File.separator + account + toPath;
        fileMapper.updateParentIdAndPathByFileId(fileId, toFileId, targetPath);

        // 更新数据库中原目录下的所有子文件、子目录的路径
        // 修改前原文件夹相对于files目录的路径(数据库中path保存的路径是相对于files目录来说的，\就是files目录)
        String originPath = java.io.File.separator + account + path;
        // 修改后文件夹相对于files目录的路径
        String afterPath = java.io.File.separator + account + toPath + name + java.io.File.separator;
        /*
           说明：这里将一个斜杠"\"全部换成两个斜杠"\\"，是因为mysql中like模糊查询是会经过两次解析
                like后边的字符串除了会在语法解析时转义一次外，还会在正则匹配时进行第二次的转义。因此如果期望最终匹配到"\"，就要反转义两次，也就是由"\\\\"到"\\"再到"\"。
                其实插入的时候也是会经过语法解析的转义的，但是mybatis帮我们处理了，所以正常插入的时候我没有进行replaceAll。
                而正则表达式的转义mybatis没有帮我们处理，所以需要把一个斜杠"\"换成两个"\"。如果mybatis也没有帮我们进行语法解析转义，那么我们就要把一个斜杠"\"换成四个斜杠"\"。
           下面replaceAll函数中，参数是需要正则表达式的。而java中正则表达式中要匹配一个反斜杠"\"，需要四个反斜杠。
                如：String pattern = "a\\\\b"。首先被java解释器解释为“a\\b”（第一个和第三个\代表转义，此时正则表达式受到保护，不被解释），再被正则解释器解释为"a\b"。即java中由4个\表示一个\。也就是他文中所说的java正则表达式被解释两次。
         */
        String replaceOriginPath = originPath.replaceAll("\\\\", "\\\\\\\\");
        // System.out.println(originPath);
        // System.out.println(afterPath);
        // System.out.println(replaceOriginPath);
        // 更新数据库中原目录下的所有子文件、子目录的path字段的前面部分路径，因为相对于子文件子目录来说，只是父目录移动了，所以路径只需要改部分即可
        // 使用mysql的replace函数可以替换一个字段中的部分内容。但是replace会替换掉同一个字符串中所有的匹配结果，因此不能用。
        fileMapper.updateChildrenFilesPathByPath(originPath, afterPath, replaceOriginPath);

        // 移动实际目录
        String homePath = getFilesRootPath(session) + java.io.File.separator + account;
        String absoluteFromPath = homePath + path;
        String absoluteToPath = homePath + toPath + name;
        try {
            FileUtils.moveFile(absoluteFromPath,absoluteToPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFileByParentIdAndName(String parentId, String name) {
        return fileMapper.selectFileByParentIdAndName(parentId, name);
    }

    // 移动文件
    @Override
    public void updateParentIdAndPath(String account, String name, String fileId, String path, String toFileId, String toPath, HttpSession session) {
        // 数据库操作
        String targetPath = java.io.File.separator + account + toPath;
        fileMapper.updateParentIdAndPathByFileId(fileId, toFileId, targetPath);

        // 实际文件移动
        String homePath = getFilesRootPath(session) + java.io.File.separator + account;
        String absoluteFromPath = homePath + path;
        String absoluteToPath = homePath + toPath + name;
        try {
            FileUtils.moveFile(absoluteFromPath,absoluteToPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFiles(List<String> fileIds, HttpSession session) {
        List<File> fileList = new ArrayList<>();
        for(String fileId: fileIds){
            // 查询看传过来的文件ID是否在数据库中存在，并且trash字段为0
            File file = fileMapper.selectFileByFileIdAndTrash(fileId);
            if(file == null){
                throw new FileNotExistException(CloudConstant.FILEINFONOTEXISTERROR);
            }
            fileList.add(file);
        }
        for(File file:fileList){
            String trashFileName = file.getFileId()+file.getName();
            String absPath = getFilesRootPath(session) + file.getPath() + file.getName();
            String newAbsPath = getFilesRootPath(session) + file.getPath() + trashFileName;
            // 如果所有需要删除的文件在数据库中都存在，则修改数据库的trash字段为1，将文件标记为回收站文件
            String trashTime = DateUtil.getCurrentDateTime();
            fileMapper.updateTrashByFileId(file.getFileId(), trashTime);

            // 如果删除的是目录，那么目录下的所有子文件的trash字段都设置为2。这样设置是为了方便回收站中显示被删除的目录，而不显示被删除目录下的文件。
            // 说明：
            //      trash字段为0表示文件没有删除至回收站
            //      trash字段为1表示文件不是连带删除至回收站
            //      trash字段为2表示文件是连带删除至回收站的，就是删除了其父目录导致文件被删除。
            if(file.getIsDirectory()){
                String currentDirPath = (file.getPath() + file.getName() + java.io.File.separator).replaceAll("\\\\", "\\\\\\\\");
                fileMapper.updateTrashByPath(currentDirPath, file.getFileId(), trashTime);
            }

            // 修改原路径下的文件名，将fileId作为文件名的前缀。
            // 这个操作的目的是为了删除文件后，能够在原来位置上传或创建原文件名的文件或目录，保证不重复。
            // 后续从回收站恢复文件的时候，就先判断对应目录下是否存在同名文件；若存在就抛异常不能恢复，不存在就恢复，修改对应目录下的文件名。
            // 这个操作不影响数据库中的数据。
            FileUtils.renameFile(absPath, newAbsPath);
        }
    }

    @Override
    public List<File> getFileRecycleInfo(String account) {
        return fileMapper.selectFilesByOwnerAndTrashAndIsDelete(account);
    }

    @Override
    public List<File> getFileByNameKeyword(String owner, String keyword) {
        return fileMapper.selectFilesByNameKeyword(owner, keyword);
    }

    @Override
    // public void updateFileAndRecoveryByFileIds(List<String> fileIds, HttpSession session) {
    public void recoveryFileByFileIds(List<String> fileIds, HttpSession session) {
        List<File> fileList = new ArrayList<>();
        for(String fileId: fileIds){
            // 查询看传过来的文件ID是否在数据库中存在，并且trash字段为1或2
            File file = fileMapper.selectRecoveryFileByFileIdAndTrash(fileId);
            if(file == null){
                throw new FileNotExistException(CloudConstant.FILEINFONOTEXISTERROR);
            }
            fileList.add(file);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);  // 设置事务传播行为
        Map<String,String> recoveryFileErrorMessages = new HashMap<>();

        for(File file:fileList){
            // 获取事务的状态
            TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

            try{
                String absPath = getFilesRootPath(session) + file.getPath() + file.getFileId() + file.getName();
                String newAbsPath = getFilesRootPath(session) + file.getPath() + file.getName();
                fileMapper.updateRecoveryTrashByFileId(file.getFileId());
                if(file.getIsDirectory()){
                    String currentDirPath = (file.getPath() + file.getName() + java.io.File.separator).replaceAll("\\\\", "\\\\\\\\");
                    fileMapper.updateRecoveryTrashByPathAndTrashBy(currentDirPath, file.getFileId());
                }
                FileUtils.renameFile(absPath, newAbsPath);
                dataSourceTransactionManager.commit(status);  // 提交事务
            }catch(RuntimeException e){
                // 发生异常，回滚一次循环中的事务
                dataSourceTransactionManager.rollback(status);
                recoveryFileErrorMessages.put(file.getName(),e.getMessage());
                // continue;
            }
            // this.updateRecoveryOneFile(session, file);
        }
        if(recoveryFileErrorMessages.size() != 0){
            String errorMsg = "";
            for(Map.Entry<String,String> entry:recoveryFileErrorMessages.entrySet()){
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                errorMsg += "[" + mapKey + ": " + mapValue + "] ";
            }
            throw new RecoveryFileException(errorMsg);
        }
    }

    @Override
    public void deleteFileByFileId(List<String> fileIds) {
        // 回收站删除文件，逻辑上永久删除，只是将is_delete字段设置为1，真实路径下的文件不用任何操作
        List<File> fileList = new ArrayList<>();
        for(String fileId: fileIds){
            // 查询看传过来的文件ID是否在数据库中存在
            File file = fileMapper.selectTrashFileByFileIdAndTrash(fileId);
            if(file == null){
                throw new FileNotExistException(CloudConstant.FILEINFONOTEXISTERROR);
            }
            fileList.add(file);
        }

        for(File file:fileList){
            fileMapper.updateIsDeleteByFileId(file.getFileId());
            if(file.getIsDirectory()){
                // 如果删除的文件是一个目录，那么也要将其下的所有子文件的is_delete字段设置为1
                fileMapper.updateIsDeleteByTrashBy(file.getFileId());
            }
        }
    }

    @Override
    public BigInteger getFileCapacity(String owner) {
        return fileMapper.selectFileCapacityByOwner(owner);
    }

// // 将批量恢复文件，对文件进行改名字修改数据库的循环操作封装成一个service层的方法，让每次for循环中对一个文件的操作都单独创建一个事务。
    // // 这样在批量恢复文件的时候，如果某个文件在原来文件夹已经有同名文件，就会单独回滚，不至于影响其它文件。导致其它文件成功恢复了，但是数据库回滚了，结果就是数据库中显示文件已被删除到回收站，但是实际文件名字已经改成没有删除至回收站的结果。
    // // 当然，这样做有效的前提是配置好事务，指定事物的propagation="REQUIRES_NEW"，且文件名在切入点表达式范围。还需要给调用这个service方法的service加上默认的事务行为。即PROPAGATION_REQUIRED
    // @Override
    // public void updateRecoveryOneFile(HttpSession session, File file){
    //     String absPath = getFilesRootPath(session) + file.getPath() + file.getFileId() + file.getName();
    //     String newAbsPath = getFilesRootPath(session) + file.getPath() + file.getName();
    //     fileMapper.updateRecoveryTrashByFileId(file.getFileId());
    //     if(file.getIsDirectory()){
    //         String currentDirPath = (file.getPath() + file.getName() + java.io.File.separator).replaceAll("\\\\", "\\\\\\\\");
    //         fileMapper.updateRecoveryTrashByPath(currentDirPath);
    //     }
    //     FileUtils.renameFile(absPath, newAbsPath);
    // }


// private String getCurrentUserHomePath(HttpSession session, String account){
    //     ServletContext servletContext = session.getServletContext();
    //     String realPath = servletContext.getRealPath(java.io.File.separator);
    //     String filesRootPath = realPath+"\\"+"files";
    //     String currentUserHomePath = filesRootPath + "\\" + account;
    //     return currentUserHomePath;
    // }

    // 获取存储文件的根目录，也就是项目下的files文件夹
    private String getFilesRootPath(HttpSession session){
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath(java.io.File.separator);
        String filesRootPath = realPath+"\\"+"files";
        return filesRootPath;
    }
}
