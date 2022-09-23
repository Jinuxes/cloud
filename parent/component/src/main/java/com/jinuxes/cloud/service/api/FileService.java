package com.jinuxes.cloud.service.api;

import com.jinuxes.cloud.entity.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FileService {
    void saveFile(File file, HttpSession session);

    List<File> getFilesByPath(String homePath);

    File getHomePathInfoByPathAndOwner(String homePath, String account);

    void saveUploadFile(MultipartFile multipartFile, File file, HttpSession session);

    List<File> getAllFoldersByOwner(String owner);

    File getFileById(String fileId);

    void updateParentIdAndFilesPath(String account, String name, String fileId, String path, String toFileId, String toPath, HttpSession session);

    File getFileByParentIdAndName(String parentId, String name);

    void updateParentIdAndPath(String account, String name, String fileId, String path, String toFileId, String toPath, HttpSession session);

    void deleteFiles(List<String> fileIds, HttpSession session);

    List<File> getFileRecycleInfo(String account);

    List<File> getFileByNameKeyword(String owner, String keyword);

    void recoveryFileByFileIds(List<String> fileIds, HttpSession session);
    // void updateFileAndRecoveryByFileIds(List<String> fileIds, HttpSession session);

    // void updateRecoveryOneFile(HttpSession session, File file);
}
