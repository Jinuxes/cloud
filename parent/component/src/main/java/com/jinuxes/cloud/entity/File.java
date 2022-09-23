package com.jinuxes.cloud.entity;

public class File {
    private String fileId;

    private String name;

    private Long size;

    private String owner;

    private String path;

    private String parentId;

    private String createTime;

    private String modiTime;

    private Boolean isDirectory;

    private Boolean share;

    private Integer trash;

    private String deleteTime;

    private Boolean isDelete;

    public File() {
    }

    public File(String fileId, String name, Long size, String owner, String path, String parentId, String createTime, String modiTime, Boolean isDirectory, Boolean share, Integer trash, String deleteTime, Boolean isDelete) {
        this.fileId = fileId;
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.path = path;
        this.parentId = parentId;
        this.createTime = createTime;
        this.modiTime = modiTime;
        this.isDirectory = isDirectory;
        this.share = share;
        this.trash = trash;
        this.deleteTime = deleteTime;
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId='" + fileId + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", owner='" + owner + '\'' +
                ", path='" + path + '\'' +
                ", parentId='" + parentId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modiTime='" + modiTime + '\'' +
                ", isDirectory=" + isDirectory +
                ", share=" + share +
                ", trash=" + trash +
                ", deleteTime='" + deleteTime + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getModiTime() {
        return modiTime;
    }

    public void setModiTime(String modiTime) {
        this.modiTime = modiTime == null ? null : modiTime.trim();
    }

    public Boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Integer getTrash() {
        return trash;
    }

    public void setTrash(Integer trash) {
        this.trash = trash;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime == null ? null : deleteTime.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}