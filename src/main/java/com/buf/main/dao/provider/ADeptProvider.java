package com.buf.main.dao.provider;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by lyf on 2018/7/17.
 */
public class ADeptProvider {

    public String remove(JSONObject jsonParams){

        String IDS = jsonParams.getString("ids");
        String sql = new SQL() {
            {
                DELETE_FROM("IND_DEPT");
                WHERE("DEPT_NO in ('" + IDS + "')");

            }
        }.toString();

        return sql;
    }
}
