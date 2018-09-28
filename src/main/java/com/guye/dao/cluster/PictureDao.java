package com.guye.dao.cluster;

import com.guye.model.Pic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by suneee on 2018/5/4.
 */
@Repository
public interface PictureDao {

    Pic findOnePic(@Param("id") int id);
}
