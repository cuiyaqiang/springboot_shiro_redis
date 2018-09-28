package com.guye.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by suneee on 2018/5/3.
 */
public interface UploadFileService {

    String UploadFile(MultipartFile file);
}
