package com.guye.controller.html.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.guye.model.easypoi.UserInfoT;
import com.guye.service.PoiService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by suneee on 2018/5/15.
 */
@Controller
public class PoiController {

    @Autowired
    private PoiService poiService;

    @RequestMapping("/testPoi")
    public void testPoi(){
        List<UserInfoT> userInfoList = poiService.getUserList();
        ExportParams params = new ExportParams("用户信息", "测试表", ExcelType.XSSF);
        Date start = new Date();
        Workbook workbook = ExcelExportUtil.exportExcel(params, UserInfoT.class, userInfoList);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:/excel/testPoi.xlsx");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
