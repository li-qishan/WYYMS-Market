package com.buf.main.dao;

import com.alibaba.fastjson.JSONObject;
import com.buf.main.dao.provider.ADeptProvider;
import com.buf.main.entity.ADept;
import com.buf.main.entity.BDept;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/7/16.
 */
@Mapper
public interface ADeptMapper {
    @Select("SELECT  a.DEPT_NO id, a.DEPT_NAME text, a.* FROM IND_DEPT a WHERE PARENT_NO = '0' ")
    @Results({ @Result(id = true, column = "DEPT_NO", property = "DEPT_NO"),
            @Result(column = "DEPT_NO", property = "children", many = @Many(select = "com.buf.main.dao.ADeptMapper.getchildren", fetchType = FetchType.EAGER))
    })
    public ADept getrow();

    @Select("SELECT  a.DEPT_NO id, a.DEPT_NAME text, a.* FROM IND_DEPT a WHERE PARENT_NO = #{dept} ORDER BY a.DEPT_NO ")
    @Results({ @Result(id = true, column = "DEPT_NO", property = "DEPT_NO"),
            @Result(column = "DEPT_NO", property = "children", many = @Many(select = "com.buf.main.dao.ADeptMapper.getchildren", fetchType = FetchType.EAGER))
    })
    public List<ADept> getchildren(@Param("dept") String dept);

    @Select("SELECT count(*)  FROM IND_DEPT a WHERE  a.DEPT_NO = #{no} or a.DEPT_NAME = #{name}")
    public int getByNames(@Param("no") String no , @Param("name") String name);

    @Insert("INSERT INTO IND_DEPT (DEPT_NO, DEPT_NAME , PARENT_NO, ORG_NO , SHORT_NAME, IND_DIM_ID) " +
            "values " +
            "(#{dept_NO}, #{dept_NAME}, #{parent_NO},  #{org_NO}, #{short_NAME}, #{ind_DIM_ID})")
    public int add(BDept obj);

    @Update("UPDATE IND_DEPT a SET a.DEPT_NAME = #{dept_NAME}, a.SHORT_NAME = #{short_NAME} WHERE a.DEPT_NO = #{dept_NO}")
    public int update(BDept obj);

    @DeleteProvider(type = ADeptProvider.class, method = "remove")
    public int remove(JSONObject params);
}
