package com.jinuxes.cloud.mvc.handler;

import com.jinuxes.cloud.entity.File;
import com.jinuxes.cloud.entity.SecurityUserDetail;
import com.jinuxes.cloud.entity.User;
import com.jinuxes.cloud.service.api.FileService;
import com.jinuxes.cloud.utils.DateUtil;
import com.jinuxes.cloud.utils.FileUtils;
import com.jinuxes.cloud.utils.ResultEntity;
import com.jinuxes.cloud.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class FileHandler {

    @Autowired
    FileService fileService;

    @RequestMapping("/file")
    public String filePage(){
        return "file";
    }

    @RequestMapping("/file/home/info")
    @ResponseBody
    public ResultEntity<File> getHomePathInfo(HttpSession session){
        // 1.在session中获取用户信息
        User currentUser = getOriginalUser(session);
        // 2.获取用户账号，账号就是用户家目录的目录名
        String account = currentUser.getAccount();
        // 3. 根据“\”对应数据库t_file表的path字段且owner字段等于用户账号的进行筛选用户的家目录信息
        File homePathInfo = fileService.getHomePathInfoByPathAndOwner("\\", account);
        // System.out.println(homePathInfo);
        return ResultEntity.successWithData(homePathInfo);
    }

    @RequestMapping("/file/home")
    @ResponseBody
    public ResultEntity<List<File>> getPathFilesByPath(String path,HttpSession session){
        // 获取用户个人家目录下的文件信息
        // 1.在session中获取用户信息
        User currentUser = getOriginalUser(session);
        // 2.获取用户账号，账号就是用户家目录的目录名
        String account = currentUser.getAccount();
        // 3. 根据“\用户账号”对应数据库t_file表的path字段来查询用户家目录下的文件。“/”是存放文件的根目录，就是项目的files文件夹
        //    注意：t_file表的path字段实质含义是代表“文件或目录”所属的路径，也就path字段的某行是不包含“该文件名或目录名的”，仅仅是该文件所属的目录路径
        String homePath = "\\"+account + path;  // 前端传过来的路径是”\xxx\xxx“的，前面拼接上个人的家目录就是完整的相对于文件根目录的目录
        List<File> fileList = fileService.getFilesByPath(homePath);
        // System.out.println(fileList);
        return ResultEntity.successWithData(fileList);
    }

    @RequestMapping("/file/create/folder")
    @ResponseBody
    public ResultEntity createFolder(File file, HttpSession session){
        // 收集、封装参数
        // 获取用户家目录路径
        User currentUser = getOriginalUser(session);
        String account = currentUser.getAccount();
        String homePath = "\\" + account;
        String fullPath = homePath + file.getPath();
        // 回设file对象的path属性
        file.setPath(fullPath);
        // 设置fileId
        file.setFileId(UUIDUtils.getUUID());
        // 文件夹没有大小，不设置size
        // 设置owner
        file.setOwner(account);
        // 设置createTime
        file.setCreateTime(DateUtil.getCurrentDateTime());
        // 设置is_directory、share、trash、is_delete
        file.setIsDirectory(true);
        file.setShare(false);
        file.setTrash(false);
        file.setIsDelete(false);
        fileService.saveFile(file, session);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public ResultEntity fileUpload(MultipartFile[] multipartFiles, String path, String parentId, HttpSession session){
        for(MultipartFile multipartFile:multipartFiles){
            // 封装File对象，调用FileService将File对象插入数据库并在对应路径生成文件
            File fileEntity = new File();
            fileEntity.setFileId(UUIDUtils.getUUID());
            fileEntity.setName(multipartFile.getOriginalFilename());
            fileEntity.setSize(String.valueOf(multipartFile.getSize()));

            User currentUser = getOriginalUser(session);
            String account = currentUser.getAccount();
            fileEntity.setOwner(account);

            String homePath = "\\" + account;
            String fullPath = homePath + path;
            fileEntity.setPath(fullPath);
            fileEntity.setParentId(parentId);
            fileEntity.setCreateTime(DateUtil.getCurrentDateTime());
            fileEntity.setIsDirectory(false);
            fileEntity.setShare(false);
            fileEntity.setTrash(false);
            fileEntity.setIsDelete(false);
            fileService.saveUploadFile(multipartFile, fileEntity, session);
        }
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/file/checked")
    @ResponseBody
    public ResultEntity checkFileExist(@RequestParam("paths") List<String> paths, HttpSession session){
        String homePath = getCurrentUserHomePath(session, getOriginalUser(session).getAccount());
        for(String path:paths){
            // 判断路径是否是目录，是的话返回错误，提示不能下载目录
            if(path.lastIndexOf(java.io.File.separator) == (path.length()-1)){
                return ResultEntity.failed("不能下载目录："+path);
            }

            if(!FileUtils.isExist(homePath+path)){
                String fileName = path.substring(path.lastIndexOf(java.io.File.separator)+1);
                return ResultEntity.failed("文件["+ fileName + "]不存在");
            }
        }
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/file/download")
    public ResponseEntity<byte[]> downloadFiles(@RequestParam("paths") List<String> paths, HttpSession session) throws IOException {
        String homePath = getCurrentUserHomePath(session, getOriginalUser(session).getAccount());
        ResponseEntity<byte[]> responseEntity = null;
        // 路径过滤，删掉目录路径，不下载目录。以”\“结尾的就是目录。顺便把路径拼接成绝对路径
        List<String> absPath = new ArrayList<>();
        for(String path: paths){
            if(path.lastIndexOf(java.io.File.separator) == (path.length()-1)){
                continue;
            }
            absPath.add(homePath + path);
        }

        if(absPath.size() != 0){
            // 如果是下载单个文件
            if(absPath.size() == 1){
                String path = absPath.get(0);
                String fileName = path.substring(path.lastIndexOf(java.io.File.separator)+1);
                InputStream is = new FileInputStream(path);
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                MultiValueMap<String, String> headers = new HttpHeaders();
                headers.add("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName,"utf-8"));
                HttpStatus statusCode = HttpStatus.OK;
                responseEntity = new ResponseEntity<>(bytes,headers,statusCode);
                is.close();
            }else{
                // 下载多个文件
                // 先将多个文件进行压缩打包
                String zipFileName = UUIDUtils.getUUID() + ".zip";
                String zipFileSavePath = homePath + java.io.File.separator + zipFileName;
                // 文件打包压缩，生成临时压缩文件
                FileUtils.makeZipFile(absPath, zipFileSavePath);

                InputStream is = new FileInputStream(zipFileSavePath);
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                MultiValueMap<String, String> headers = new HttpHeaders();
                headers.add("Content-Disposition","attachment;filename="+URLEncoder.encode(zipFileName,"utf-8"));
                HttpStatus statusCode = HttpStatus.OK;
                responseEntity = new ResponseEntity<>(bytes,headers,statusCode);

                // 关闭流
                is.close();

                // 删除临时压缩文件
                java.io.File zipFile = new java.io.File(zipFileSavePath);
                zipFile.delete();
            }
            return responseEntity;
        }
        return null;
    }

    @RequestMapping("/file/folders")
    @ResponseBody
    public ResultEntity<List<File>> getAllFolders(HttpSession session){
        User user = getOriginalUser(session);
        String account = user.getAccount();

        List<File> folderList = fileService.getAllFoldersByOwner(account);
        return ResultEntity.successWithData(folderList);
    }

    @RequestMapping("/file/move")
    @ResponseBody
    public ResultEntity updateFileParentId(String fileId,
                                           String path,
                                           String fileType,
                                           String parentPath,
                                           String toFileId,
                                           String toPath,
                                           HttpSession session){
        if(fileType.equals("folder")){
            // 如果移动的是目录
            if(fileId.equals(toFileId)){
                return ResultEntity.failed("不能将目录移动到自身下");
            }
            if(toPath.equals(parentPath)){
                return ResultEntity.failed("需要移动的目录已经在这个目录下，无需移动");
            }
            if(toPath.startsWith(path)){
                return ResultEntity.failed("不能将目录移动到自身的子目录下");
            }

            // 查询数据库，获取目标目录信息
            // 判断目标目录是否是一个目录，如果不是目录而是文件，则返回
            File toFile = fileService.getFileById(toFileId);
            if(toFile == null){
                return ResultEntity.failed("目标位置不存在");
            }
            if(!toFile.getIsDirectory()){
                return ResultEntity.failed("目标位置不是一个目录");
            }

            // 查询要移动的目录是否存在于数据库中
            File file = fileService.getFileById(fileId);
            if(file == null){
                return ResultEntity.failed("需要移动的目录不存在");
            }

            // 查询目标目录下有没有同名的目录，根据目标目录的fileId=目标目录下文件的parentId即可查询到目标目录下的所有文件
            File existFile = fileService.getFileByParentIdAndName(toFileId, file.getName());
            if(existFile != null){
                return ResultEntity.failed("目标目录下已存在同名目录");
            }

            // 进行目录移动(数据库修改+真实路径上的文件移动)
            String account = getOriginalUser(session).getAccount();
            fileService.updateParentIdAndFilesPath(account, file.getName(), fileId, path, toFileId, toPath, session);
            return ResultEntity.successWithoutData();

        }else if(fileType.equals("file")){
            // 如果移动的是文件
            if(fileId.equals(toFileId)){
                return ResultEntity.failed("不能将文件移动到自身下");
            }
            if(toPath.equals(parentPath)){
                return ResultEntity.failed("需要移动的文件已经在这个目录下，无需移动");
            }

            // 查询数据库，获取目标目录信息
            // 判断目标目录是否是一个目录，如果不是目录而是文件，则返回
            File toFile = fileService.getFileById(toFileId);
            if(toFile == null){
                return ResultEntity.failed("目标位置不存在");
            }
            if(!toFile.getIsDirectory()){
                return ResultEntity.failed("目标位置不是一个目录");
            }

            // 查询要移动的文件是否存在于数据库中
            File file = fileService.getFileById(fileId);
            if(file == null){
                return ResultEntity.failed("需要移动的文件不存在");
            }

            // 查询目标目录下有没有同名的文件，根据目标目录的fileId=目标目录下文件的parentId即可查询到目标目录下的所有文件
            File existFile = fileService.getFileByParentIdAndName(toFileId, file.getName());
            if(existFile != null){
                return ResultEntity.failed("目标目录下已存在同名文件");
            }

            // 进行文件移动(数据库修改+真实路径上的文件移动)
            String account = getOriginalUser(session).getAccount();
            fileService.updateParentIdAndPath(account, file.getName(), fileId, path, toFileId, toPath, session);
            return ResultEntity.successWithoutData();

        }
        return ResultEntity.failed("参数”fileType“错误，只能是“file”或者”folder“");
    }

    @RequestMapping("/file/delete")
    @ResponseBody
    public ResultEntity deleteFiles(@RequestParam("fileIds") List<String> fileIds, HttpSession session){
        fileService.deleteFiles(fileIds, session);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/file/recycle")
    public String recyclePage(){
        return "recycle";
    }

    @RequestMapping("/file/recycle/info")
    @ResponseBody
    public ResultEntity<List<File>> getFileRecycleInfo(HttpSession session){
        User user = getOriginalUser(session);
        String account = user.getAccount();
        List<File> fileList = fileService.getFileRecycleInfo(account);
        return ResultEntity.successWithData(fileList);
    }

    // private String getServerRealPath(HttpSession session){
    //     ServletContext servletContext = session.getServletContext();
    //     String realPath = servletContext.getRealPath(java.io.File.separator);
    //     return realPath;
    // }

    private User getOriginalUser(HttpSession session){
        SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        SecurityUserDetail securityUserDetail = (SecurityUserDetail) authentication.getPrincipal();
        User originalUser = securityUserDetail.getOriginalUser();
        return originalUser;
    }

    private String getCurrentUserHomePath(HttpSession session, String account){
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath(java.io.File.separator);
        String filesRootPath = realPath+"\\"+"files";
        String currentUserHomePath = filesRootPath + "\\" + account;
        return currentUserHomePath;
    }
}
