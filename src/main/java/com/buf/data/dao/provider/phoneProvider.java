package com.buf.data.dao.provider;

import com.buf.common.utils.StringUtils;

import java.util.Map;

/**
 * Created by mawenguang on 2019/5/22.
 */
public class phoneProvider
{
    public String getBasicData(Map map)
    {
        String param = map.get("param").toString();
        String type = map.get("type").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();
        // tab页的标识符。userInfo：用户列表，userUpdate：已修改用户列表
        String index = map.get("index").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append("SELECT a.cons_no   CONSNO, " +
                "       a.cons_name CONSNAME, " +
                "       a.elec_addr ELECADDR, " +
                "       c.mobile    MOBILE " +
                "  FROM epm_ln.c_cons a " +
                "  left join  epm_ln.c_cons_contact_rela b on a.cons_id = b.cons_id " +
                "  left join  epm_ln.c_contact c on b.contact_id = c.contact_id and  c.contact_mode = '01' " +
                " WHERE a.status_code <> 9 ");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("  AND a.org_no LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%'  ");
        }
        if (type.equals("1") && StringUtils.isNotBlank(param))// 户号
        {
            sbf.append(" AND a.cons_no = '" + param + "'");
            sbf.append("and rownum = 1");
        }
        else if (type.equals("2") && StringUtils.isNotBlank(param))// 手机号+
        {
            sbf.append(" AND c.mobile = '" + param + "'");
        }
        return sbf.toString();
    }

    public String getUserDetail(Map map)
    {
        String consNo = map.get("consNo").toString();
        String index = map.get("index").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append("SELECT DISTINCT " +
                " a.cons_no CONS_NO, " +
                " a.cons_name CONS_NAME, " +
                " a.elec_addr ELEC_ADDR, " +
                " a.cust_query_no CUST_QUERY_NO, " +
                " ( SELECT PROP_LIST_NAME FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'elec_type_code' AND PROP_LIST_ID = A.ELEC_TYPE_CODE ) ELEC_TYPE_CODE , " +
                " substr( ( SELECT org_name FROM epm_ln.sa_org WHERE org_no = substr( a.org_no, 1, 5 ) ), 3, 200 ) || '-' || substr( ( SELECT org_name FROM epm_ln.sa_org WHERE org_no = a.org_no ), 3, 200 ) ORG_NAME " +
                " FROM " +
                " epm_ln.c_cons a, " +
                " epm_ln.c_cons_contact_rela b, " +
                " epm_ln.c_contact c  " +
                " WHERE a.status_code <> 9  " +
                " AND a.cons_id = b.cons_id ( + )  " +
                " AND b.contact_id = c.contact_id ");

         /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("  AND a.org_no LIKE  (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }


        sbf.append(" AND a.cons_no = '" + consNo + "'");
//        AND c.contact_mode = '" + index + "'
        return sbf.toString();
    }

    public String getUserPhones(Map map)
    {
        String consNo = map.get("consNo").toString();
        String index = map.get("index").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append("select * from (" +
                "       select x1.cons_no CONS_NO," +
                "       x1.CONTACT_TYPE CONTACT_TYPE," +
                "       x1.MOBILE MOBILE," +
                "       (case when CONS_CHANNEL_NO=MOBILE then 1 else 0 end ) COST_PHONE,x1.CONTACT_ID" +
                "       from (SELECT distinct  A.CONS_NO CONS_NO," +
//                "       (SELECT PROP_LIST_NAME  FROM EPM_LN.SA_PROP_LIST" +
//                "       WHERE PROP_TYPE_ID = 'contact_type'" +
//                "       AND PROP_LIST_ID = B.CONTACT_MODE) CONTACT_TYPE," +
                "       '移动电话' CONTACT_TYPE," +
                "       b.office_tel,b.homephone,B.MOBILE MOBILE,b.contact_id CONTACT_ID " +
                "       FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C" +
                " WHERE A.CONS_ID = C.CONS_ID" +
                "   AND C.CONTACT_ID = B.CONTACT_ID  " +
                " AND A.CONS_NO = '" + consNo + "' " +
                "   AND B.CONTACT_MODE = '" + index + "' ");

        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("  AND A.ORG_NO LIKE  (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }
        sbf.append(") x1" +
                "   left join   (SELECT x.cons_no ,Y.CONS_CHANNEL_NO" +
                "         FROM C_SUBSCIBE@CFPT X, C_SUBSCIBE_DET@CFPT  Y" +
//                "         FROM epc_ln.C_SUBSCIBE X,  epc_ln.C_SUBSCIBE_DET  Y" +
                "         WHERE X.SUBSCIBE_ID = Y.SUBSCIBE_ID and x.cons_no = '" + consNo + "' ");
         /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("    AND X.ORG_NO LIKE substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
        }

        sbf.append("  )x2  on x1.cons_no=x2.cons_no and x2.CONS_CHANNEL_NO=x1.MOBILE        " +
                "     union all  " +
                "     select x1.cons_no CONS_NO," +
                "       x1.CONTACT_TYPE CONTACT_TYPE," +
                "       x1.office_tel MOBILE," +
                "       (case when CONS_CHANNEL_NO=office_tel then 1 else 0 end ) COST_PHONE,x1.CONTACT_ID" +
                " from (SELECT distinct  A.CONS_NO CONS_NO," +
                "          '办公电话' CONTACT_TYPE," +
                "           b.office_tel" +
                "           ,b.homephone," +
                "       B.MOBILE MOBILE,b.contact_id CONTACT_ID   FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C" +
                " WHERE A.CONS_ID = C.CONS_ID" +
                "   AND C.CONTACT_ID = B.CONTACT_ID  " +
                " AND A.CONS_NO = '" + consNo + "'" +
                "   AND B.CONTACT_MODE ='" + index + "'");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("   AND A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }
        sbf.append(") x1" +
                "   left join   (SELECT x.cons_no ,Y.CONS_CHANNEL_NO" +
                "         FROM C_SUBSCIBE@CFPT X, C_SUBSCIBE_DET@CFPT  Y" +
//                "         FROM epc_ln.C_SUBSCIBE X,  epc_ln.C_SUBSCIBE_DET  Y" +
                "  WHERE X.SUBSCIBE_ID = Y.SUBSCIBE_ID and x.cons_no= '" + consNo + "' ");
          /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("    AND X.ORG_NO LIKE substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
        }

        sbf.append("  )x2  on x1.cons_no=x2.cons_no and x2.CONS_CHANNEL_NO=x1.office_tel" +
                "    union all    " +
                "     select x1.cons_no CONS_NO," +
                "       x1.CONTACT_TYPE CONTACT_TYPE," +
                "       x1.homephone MOBILE," +
                "       (case when CONS_CHANNEL_NO=office_tel then 1 else 0 end ) COST_PHONE,x1.CONTACT_ID" +
                " from (SELECT distinct  A.CONS_NO CONS_NO," +
                "          '固定电话' CONTACT_TYPE," +
                "           b.office_tel,b.homephone,B.MOBILE MOBILE,b.contact_id CONTACT_ID  " +
                "   FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C" +
                " WHERE A.CONS_ID = C.CONS_ID ");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append(" and A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }

        sbf.append("   AND C.CONTACT_ID = B.CONTACT_ID  " +
                " AND A.CONS_NO = '" + consNo + "' " +
                "   AND B.CONTACT_MODE = '" + index + "' ) x1" +
                "   left join   (SELECT x.cons_no ,Y.CONS_CHANNEL_NO" +
                "         FROM C_SUBSCIBE@CFPT X, C_SUBSCIBE_DET@CFPT  Y" +
//                "         FROM epc_ln.C_SUBSCIBE X,  epc_ln.C_SUBSCIBE_DET  Y" +
                "         WHERE  X.SUBSCIBE_ID = Y.SUBSCIBE_ID ");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("    AND X.ORG_NO LIKE substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
        }
        sbf.append("   and x.cons_no = '" + consNo + "'" +
                "  )x2  on x1.cons_no=x2.cons_no and x2.CONS_CHANNEL_NO=x1.homephone" +
                "   )  where mobile is not null ");
        return sbf.toString();
    }


    public String getUserPhonesTest(Map map)
    {
        String consNo = map.get("consNo").toString();
        String index = map.get("index").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append("select * from (" +
                "       select x1.cons_no CONS_NO," +
                "       x1.CONTACT_TYPE CONTACT_TYPE," +
                "       x1.MOBILE MOBILE," +
                "       ( CASE WHEN 1 = 2 THEN 1 ELSE 0 END )  COST_PHONE,x1.CONTACT_ID" +
                "       from (SELECT distinct  A.CONS_NO CONS_NO," +
//                "       (SELECT PROP_LIST_NAME  FROM EPM_LN.SA_PROP_LIST" +
//                "       WHERE PROP_TYPE_ID = 'contact_type'" +
//                "       AND PROP_LIST_ID = B.CONTACT_MODE) CONTACT_TYPE," +
                "       '移动电话' CONTACT_TYPE," +
                "       b.office_tel,b.homephone,B.MOBILE MOBILE,b.contact_id CONTACT_ID " +
                "       FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C" +
                " WHERE A.CONS_ID = C.CONS_ID" +
                "   AND C.CONTACT_ID = B.CONTACT_ID  " +
                " AND A.CONS_NO = '" + consNo + "' " +
                "   AND B.CONTACT_MODE = '" + index + "' ");

        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("  AND A.ORG_NO LIKE  (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }
        sbf.append(") x1"+
                "     union all  " +
                "     select x1.cons_no CONS_NO," +
                "       x1.CONTACT_TYPE CONTACT_TYPE," +
                "       x1.office_tel MOBILE," +
                "       (case when ''=office_tel then 1 else 0 end ) COST_PHONE,x1.CONTACT_ID" +
                " from (SELECT distinct  A.CONS_NO CONS_NO," +
                "          '办公电话' CONTACT_TYPE," +
                "           b.office_tel" +
                "           ,b.homephone," +
                "       B.MOBILE MOBILE,b.contact_id CONTACT_ID   FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C" +
                " WHERE A.CONS_ID = C.CONS_ID" +
                "   AND C.CONTACT_ID = B.CONTACT_ID  " +
                " AND A.CONS_NO = '" + consNo + "'" +
                "   AND B.CONTACT_MODE ='" + index + "'");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("   AND A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }
        sbf.append(") x1" +
                "    union all    " +
                "     select x1.cons_no CONS_NO," +
                "       x1.CONTACT_TYPE CONTACT_TYPE," +
                "       x1.homephone MOBILE," +
                "       (case when ''=office_tel then 1 else 0 end ) COST_PHONE,x1.CONTACT_ID" +
                " from (SELECT distinct  A.CONS_NO CONS_NO," +
                "          '固定电话' CONTACT_TYPE," +
                "           b.office_tel,b.homephone,B.MOBILE MOBILE,b.contact_id CONTACT_ID  " +
                "   FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C" +
                " WHERE A.CONS_ID = C.CONS_ID ");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append(" and A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }

        sbf.append("   AND C.CONTACT_ID = B.CONTACT_ID  " +
                " AND A.CONS_NO = '" + consNo + "' " +
                "   AND B.CONTACT_MODE = '" + index + "' ) x1" +
                "   )  where mobile is not null ");


        return sbf.toString();
    }

    public String getUserId(Map map)
    {
        StringBuffer sbf = new StringBuffer();

        String consNo = map.get("consNo").toString();
        String index = map.get("index").toString();
        String deptNo = map.get("deptNo").toString();
        String phone = map.get("phone").toString();
        String operType = map.get("operType").toString();
        String userName = map.get("userName").toString();

        sbf.append("select b.contact_id contactId, b.contact_name contactName " +
                "  from epm_ln.c_cons a, epm_ln.c_contact b, epm_ln.c_cons_contact_rela c " +
                " where  a.cons_id = c.cons_id " +
                "   and c.contact_id = b.contact_id " +
                "   and a.cons_no = '" + consNo + "'"); // 条件为用户编号
/*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("  and a.org_no like (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%'  ");// 单位编号
        }

        if (!operType.equals("0"))
        {
            sbf.append("   and (b.HOMEPHONE = '" + phone + "' or b.MOBILE = '" + phone + "' or b.OFFICE_TEL = '" + phone + "')" +// 手机号
                    "   and b.contact_mode = '" + index + "'");// 手机号类型
        }
        sbf.append("and rownum = 1");

        return sbf.toString();
    }


    public String getUserPhoneForBatchOpera(Map map)
    {
        String consNo = map.get("consNo").toString();
        String deptNo = map.get("deptNo").toString();
        String oldPhone = map.get("oldPhone").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append("SELECT " +
                "        B.CONTACT_ID contactId, " +
                "        C.CONTACT_NAME contactName, " +
                "        C.MOBILE MOBILE, " +
                "        c.HOMEPHONE, " +
                "        c.OFFICE_TEL," +
                "        C.CONTACT_MODE CONTACTMODE" +
                "   FROM " +
                "        EPM_LN.C_CONS A, " +
                "        EPM_LN.C_CONS_CONTACT_RELA B, " +
                "        EPM_LN.C_CONTACT C " +
                "   WHERE A.STATUS_CODE<> 9 " +
                "        AND A.CONS_ID = B.CONS_ID(+) " +
                "        AND B.CONTACT_ID = C.CONTACT_ID " +
                "        AND C.CONTACT_MODE IN ('02', '03' ) " +
                "        AND A.CONS_NO = '" + consNo + "' " +
                "        AND (c.HOMEPHONE = '" + oldPhone + "' or c.MOBILE = '" + oldPhone + "' or c.OFFICE_TEL = '" + oldPhone + "')");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append("  AND  A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%'  ");
        }
        return sbf.toString();
    }


}

// 获取用户某一类型电话---老版本
//       sbf.append("select x1.cons_no,  " +
//                "       x1.CONTACT_TYPE,  " +
//                        "       x1.MOBILE,  " +
//                        "       (case when CONS_CHANNEL_NO=MOBILE then 1 else 0 end )  COST_PHONE " +
//                        " from (SELECT distinct  A.CONS_NO CONS_NO,  " +
//                        "       (SELECT PROP_LIST_NAME  FROM EPM_LN.SA_PROP_LIST  " +
//                        "         WHERE PROP_TYPE_ID = 'contact_type'  " +
//                        "           AND PROP_LIST_ID = B.CONTACT_MODE) CONTACT_TYPE,  " +
//                        "       B.MOBILE MOBILE  FROM EPM_LN.C_CONS A ,EPM_LN.C_CONTACT B, EPM_LN.C_CONS_CONTACT_RELA C  " +
//                        " WHERE A.ORG_NO LIKE '" + deptNo + "%'  " +
//                        "   AND A.CONS_ID = C.CONS_ID  " +
//                        "   AND C.CONTACT_ID = B.CONTACT_ID  " +
//                        "   AND A.CONS_NO = '" + consNo + "'  " +
//                        "   AND B.CONTACT_MODE = '" + index + "' ) x1  " +
//                        "   left join   (SELECT x.cons_no ,Y.CONS_CHANNEL_NO  " +
//                        "         FROM C_SUBSCIBE@CFPT X, C_SUBSCIBE_DET@CFPT  Y  " +
//                        "         WHERE X.ORG_NO LIKE '" + deptNo + "%'  " +
//                        "         AND X.SUBSCIBE_ID = Y.SUBSCIBE_ID and x.cons_no = '" + consNo + "'  " +
//                        "         )x2  on x1.cons_no=x2.cons_no and x2.CONS_CHANNEL_NO=x1.MOBILE  ");