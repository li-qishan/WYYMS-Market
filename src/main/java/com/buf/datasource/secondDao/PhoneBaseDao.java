package com.buf.datasource.secondDao;

import com.buf.data.entity.UpdatePhoneList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by mawenguang on 2019/6/25.
 */
public interface PhoneBaseDao
{
    @Insert("INSERT INTO  UPDATE_PHONE_LIST( CONSNO, CONSNAME, OPERTYPE, NEWPHONE, OLDPHONE, OPERTIME, OPERBY, CONTACTMODE) " +
            " VALUES (#{consNo}, #{consName}, #{operType},#{newPhone}, #{oldPhone}, TO_DATE(#{operTime}, 'SYYYY-MM-DD HH24:MI:SS'),#{operBy},  #{contactMode} ) ")
    int insert(UpdatePhoneList updatePhoneList);

    @Update("UPDATE UPDATE_PHONE_LIST SET CONSNO = #{consNo}, CONSNAME = #{consName}, OPERTYPE = #{operType}, NEWPHONE = #{newPhone} ," +
            " OLDPHONE = #{oldPhone} , OPERTIME = TO_DATE(#{operTime}, 'SYYYY-MM-DD HH24:MI:SS'), OPERBY = #{operBy}, CONTACTMODE = #{contactMode} " +
            " WHERE CONSNO = #{consNo} AND  CONTACTMODE = #{contactMode}")
    int update(UpdatePhoneList updatePhoneList);

    @Select("SELECT count(*) FROM UPDATE_PHONE_LIST WHERE CONSNO = #{consNo} AND CONTACTMODE  = #{contactMode} ")
    int selectCount(@Param("consNo") String consNo,@Param("contactMode") String contactMode);

    @Select("SELECT CONSNO consNo, CONSNAME consName, OPERTYPE operType, NEWPHONE newPhone, OLDPHONE oldPhone, OPERTIME operTime, OPERBY operBy, CONTACTMODE contactMode " +
            " FROM UPDATE_PHONE_LIST WHERE OPERBY = #{username} AND TO_CHAR(opertime, 'YYYY-MM-DD')  = TO_CHAR(sysdate, 'YYYY-MM-DD')  ORDER BY OPERTIME desc")
    List<UpdatePhoneList> selectAll(@Param("username") String userName);
}
