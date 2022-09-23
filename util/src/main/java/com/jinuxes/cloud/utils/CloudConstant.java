package com.jinuxes.cloud.utils;

public class CloudConstant {

    public static final String SUCCESS = "0";
    public static final String FAILED = "1";

    public static final String EMAILERROR = "邮箱格式不正确";
    public static final String USERNAMEERROR = "用户名必须是2-5位中文或者6-16位英文和数字的组合";
    public static final String PASSWORDERROR = "密码必须6-18位数字、字母";
    public static final String ACCOUNTERROR = "账号必须2-20位数字、字母";
    public static final String ROLENAMEERROR = "用户名必须是2-10位中文或者2-30位英文和数字的组合";
    public static final String AUTHORITYNAMEERROR = "权限名必须是2-10位中文或者2-30位英文和数字的组合";
    public static final String AUTHORITYTITLEERROR = "权限标题必须是2-10位中文或者2-30位英文和数字的组合";
    public static final String FORBIDDENMESSAGE = "没有权限";

    // 创建文件、目录相关
    public static final String PATHERROR = "父目录不存在";
    public static final String PATHALREADYEXISTERROR = "目录已存在";
    // 这个是配置目录长度用的，尽量要保持根数据库t_file表中的path字段长度一致，当然也可以小于t_file表中path字段的长度，但是不能大于t_file表中字段的长度。
    // 当大于t_file表中path字段的长度的时候，就会在mybatis插入数据的时候报错。
    // 这个是设置当实际目录长度大于MAXPATHLENGTH时，抛出DirectoryHierarchyIsTooDeepException异常，进而不用等到mybatis插入数据的时候抛出DataIntegrityViolationException异常
    public static final Integer MAXPATHLENGTH = 1024;
    public static final String PATHTOOLONG = "创建失败，目录层次太深";

    public static final String EXCEPTIONREQUESTDOMAINKEY = "exception";
    public static final String FILEALREADYEXISTERROR = "文件已存在";
    public static final String FILESIZEEXCEEDED = "文件大小超出限制，请上传小于4G大小的文件";

    // 文件、目录移动相关
    public static final String FILEORDIRALREADYEXISTERROR = "目标位置中已存在同名文件或目录，不能移动";
    public static final String FILEORDIRNOTEXISTERROR = "需要移动的文件或目录不存在";

    // 文件删除相关
    public static final String FILEINFONOTEXISTERROR = "文件信息不存在";
    public static final String FILENOTEXISTERROR = "目录下文件不存在";
    public static final String FILEALREADYEXISTINDIRERROR = "目录下已经存在相同名字的文件，操作失败";
}
