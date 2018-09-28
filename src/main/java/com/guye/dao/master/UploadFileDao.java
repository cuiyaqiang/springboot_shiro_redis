package com.guye.dao.master;

import com.guye.model.Pic;
import org.springframework.stereotype.Repository;

/**
 * Created by suneee on 2018/5/3.
 */
@Repository
public interface UploadFileDao {

    int add(Pic pic);
}
