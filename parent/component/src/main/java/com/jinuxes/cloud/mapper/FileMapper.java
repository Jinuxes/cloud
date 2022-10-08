package com.jinuxes.cloud.mapper;

import com.jinuxes.cloud.entity.File;
import com.jinuxes.cloud.entity.FileExample;

import java.math.BigInteger;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileMapper {
    int countByExample(FileExample example);

    int deleteByExample(FileExample example);

    int deleteByPrimaryKey(String fileId);

    int insert(File record);

    int insertSelective(File record);

    List<File> selectByExample(FileExample example);

    File selectByPrimaryKey(String fileId);

    int updateByExampleSelective(@Param("record") File record, @Param("example") FileExample example);

    int updateByExample(@Param("record") File record, @Param("example") FileExample example);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);

    List<File> selectFilesByPath(String homePath);

    File selectHomePathInfoByPathAndOwner(@Param("homePath")String homePath, @Param("account")String account);

    List<File> selectAllFoldersByOwner(String owner);

    File selectFileByFileId(String fileId);

    void updateParentIdAndPathByFileId(@Param("fileId")String fileId, @Param("parentId")String parentId, @Param("path")String path);

    int updateChildrenFilesPathByPath(@Param("originPath")String originPath, @Param("afterPath")String afterPath, @Param("replaceOriginPath") String replaceOriginPath);

    File selectFileByParentIdAndName(@Param("parentId")String parentId, @Param("name")String name);

    File selectFileByFileIdAndTrash(String fileId);

    void updateTrashByFileId(@Param("fileId")String fileId, @Param("trashTime")String trashTime);

    List<File> selectFilesByOwnerAndTrashAndIsDelete(String account);

    void updateTrashByPath(@Param("path")String path, @Param("trashBy")String trashBy, @Param("trashTime")String trashTime);

    List<File> selectFilesByNameKeyword(@Param("owner")String owner, @Param("keyword")String keyword);

    File selectRecoveryFileByFileIdAndTrash(String fileId);

    void updateRecoveryTrashByFileId(String fileId);

    void updateRecoveryTrashByPathAndTrashBy(@Param("path")String path, @Param("trashBy")String trashBy);

    File selectTrashFileByFileIdAndTrash(String fileId);

    void updateIsDeleteByFileId(String fileId);

    void updateIsDeleteByTrashBy(String trashBy);

    BigInteger selectFileCapacityByOwner(String owner);
}