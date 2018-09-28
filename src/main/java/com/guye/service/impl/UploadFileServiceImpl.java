package com.guye.service.impl;

import com.guye.dao.master.UploadFileDao;
import com.guye.model.Pic;
import com.guye.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by suneee on 2018/5/3.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileDao uploadFileDao;

    @Override
    public String UploadFile(MultipartFile file) {
        try {
            //图片新名字
            String name = UUID.randomUUID().toString();
            //图片原名字
            String oldName = file.getOriginalFilename();
            //后缀名
            String exeName = oldName.substring(oldName.lastIndexOf("."));
            //文件上传后的路径
            String filePath = "E://fileUpload//";
            //保存文件路径为绝对路径
            File pic = new File(filePath+name+exeName);
            //检测是否存在目录
            if (!pic.getParentFile().exists()){
                pic.getParentFile().mkdir();
            }
            //保存文件到本地磁盘
            file.transferTo(pic);
            //保存数据到数据库中
            uploadFileDao.add(new Pic(name,filePath+name+exeName));
            return filePath+name+exeName;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
