package com.guye.controller.html;

import com.guye.service.UploadFileService;
import io.swagger.annotations.Api;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by suneee on 2018/5/3.
 */
@RestController
//@Api(value = "城市管理",tags = "城市管理",description = "城市接口类")
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ModelAndView uploadPic(@RequestParam("test") MultipartFile file){
        if (file.isEmpty()){
            return new ModelAndView("403");
        }
        String picUrl = uploadFileService.UploadFile(file);
        if (picUrl == null){
            return new ModelAndView("403");
        }
        return new ModelAndView("userInfoAdd");
    }

    @RequestMapping("/download")
    public ModelAndView downloadPic(HttpServletRequest request, HttpServletResponse response){
        String fileName = "City.java";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            /*String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//");*/
            String realPath = "d://";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" +  fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                    return new ModelAndView("login");
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ModelAndView("403");
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else {
                return new ModelAndView("403");
            }
        }else {
            return new ModelAndView("403");
        }
    }
}
