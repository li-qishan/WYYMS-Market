package com.buf.data.dao.provider;

import com.buf.common.utils.StringUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by mawenguang on 2019/6/21.
 */
public class ArrearsProvider
{
    public String getBasicData(Map map)
    {
        String param = map.get("param").toString();
        String type = map.get("type").toString();
        String deptNo = map.get("deptNo").toString();
        String monthSearch = map.get("month").toString();
        String startPage = map.get("startPage").toString();
        String endPage = map.get("endPage").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();
        // 不是用户查询和第一次查询时 增加分页
        if (!type.equals("1") && startPage.equals("1"))
        {
            sbf.append("SELECT *  FROM ( select ROWNUM AS rowno, SS.consNo, SS.consName, SS.arrearsMoney, SS.ym , AA.SUMMONEY,AA.COUNT" +
                    "  from ( ");
        }else{
            sbf.append("SELECT aa.*,sss.*  FROM ( ");
        }
        sbf.append("SELECT A.CONS_NO consNo," +
                "               B.CONS_NAME consName," +
                "               NVL(SUM(A.RCVBL_AMT) - SUM(A.RCVED_AMT), 0) arrearsMoney," +
                "               A.RCVBL_YM ym" +
                "          FROM EPM_LN.A_RCVBL_FLOW A, EPM_LN.C_CONS B" +
                "         WHERE A.CONS_NO = B.CONS_NO" +
                "           AND A.AMT_TYPE <> 04" +
                "           AND A.SETTLE_FLAG <> 03" +
                "           AND (A.INVISIBLE_FLAG IS NULL OR A.INVISIBLE_FLAG = '0')");

        /*
         * 省公司账号无需增加供电单位编号验证
         **/
        if (!deptNo.equals("21102"))
        {
            sbf.append(" AND A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' " +
                    " AND B.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }

        if (type.equals("1"))// 户号
        {
            sbf.append(" AND A.CONS_NO = '" + param + "' ");
        }
        else if (type.equals("5"))// 催费员
        {
            sbf.append(" AND exists (Select 1 " +
                    "  From Epm_Ln.a_Cr_Rela k, Epm_Ln.a_Cr_Sect l, Epm_Ln.Sa_User m " +
                    "  Where k.Remind_Sect_No = l.Remind_Sect_No And m.User_Id = l.Emp_No and k.Cons_No = a.cons_no and m.user_name='" + param + "') ");
        }
        else if (type.equals("6"))// 抄表员
        {
            sbf.append(" AND exists ( SELECT 1 FROM EPM_LN.R_OPER_ACTIVITY x ,EPM_LN.SA_USER a WHERE x.mr_sect_no = b.mr_sect_no and x.act_code = '03' " +
                    " AND x.operator_no = a.user_id AND a.user_name = '" + param + "' )");
        }

        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;

        // 抄表员与催费员因数据量过大，只查当月数据
        if (!type.equals("1"))
        {
            if (StringUtils.isBlank(monthSearch))
            {
                monthSearch = String.valueOf(year) + appendZero(String.valueOf(month));
            }

            sbf.append("   AND A.RCVBL_YM = '" + monthSearch + "' ");
        }

        sbf.append("   GROUP BY A.CONS_NO, B.CONS_NAME, A.RCVBL_YM  ORDER BY NVL(SUM(A.RCVBL_AMT) - SUM(A.RCVED_AMT), 0) DESC");

        // 不是用户查询和第一次查询时 增加分页
        if (!type.equals("1") && startPage.equals("1"))
        {
            sbf.append(") SS ,(SELECT COUNT(DISTINCT A.CONS_NO ) COUNT," +
                    "   SUM(NVL(SUM(A.RCVBL_AMT) - SUM(A.RCVED_AMT), 0)) SUMMONEY" +
                    "   FROM EPM_LN.A_RCVBL_FLOW A, EPM_LN.C_CONS B" +
                    "   WHERE  A.CONS_NO = B.CONS_NO AND A.AMT_TYPE <> 04 AND A.SETTLE_FLAG <> 03" +
                    "   AND (A.INVISIBLE_FLAG IS NULL OR A.INVISIBLE_FLAG = '0')");

            /*
             * 省公司账号无需增加供电单位编号验证
             */
            if (!deptNo.equals("21102"))
            {
                sbf.append(" AND A.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%'  " +
                        "  AND B.ORG_NO LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
            }
            if (type.equals("1"))// 户号
            {
                sbf.append(" AND A.CONS_NO = '" + param + "' ");
            }
            else if (type.equals("5"))// 催费员
            {
                sbf.append(" AND exists (Select 1 " +
                        "  From Epm_Ln.a_Cr_Rela k, Epm_Ln.a_Cr_Sect l, Epm_Ln.Sa_User m " +
                        "  Where k.Remind_Sect_No = l.Remind_Sect_No And m.User_Id = l.Emp_No " +
                        "  and k.Cons_No = a.cons_no and m.user_name='" + param + "') ");
            }
            else if (type.equals("6"))// 抄表员
            {
                sbf.append(" AND exists ( SELECT 1 FROM EPM_LN.R_OPER_ACTIVITY x ,EPM_LN.SA_USER a " +
                        " WHERE x.mr_sect_no = b.mr_sect_no and x.act_code = '03' " +
                        " AND x.operator_no = a.user_id AND a.user_name = '" + param + "' )");
            }
            // 抄表员与催费员因数据量过大，只查当月数据
            if (!type.equals("1"))
            {
                if (StringUtils.isBlank(monthSearch))
                {
                    monthSearch = String.valueOf(year) + appendZero(String.valueOf(month));
                }

                sbf.append("   AND A.RCVBL_YM = '" + monthSearch + "' ");
            }

            sbf.append("  GROUP BY A.RCVBL_AMT, A.CONS_NO) AA where ROWNUM <= " + endPage + "   ) table_alias" +
                    " WHERE table_alias.rowno >= " + startPage + " ");
        }else{
            sbf.append(") aa," +
                    "(" +
                    " SELECT SUM(ss.arrearsMoney) as SUMMONEY,count(ss.consNo) as COUNT FROM (" +
                    " SELECT A.CONS_NO consNo,B.CONS_NAME consName,NVL (SUM(A.RCVBL_AMT)-SUM(A.RCVED_AMT),0) arrearsMoney,A.RCVBL_YM ym FROM" +
                    " EPM_LN.A_RCVBL_FLOW A,EPM_LN.C_CONS B WHERE A.CONS_NO=B.CONS_NO AND A.AMT_TYPE<> 04 AND A.SETTLE_FLAG<> 03 " +
                    " AND (A.INVISIBLE_FLAG IS NULL OR A.INVISIBLE_FLAG='0') AND A.ORG_NO LIKE (" +
                    " SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name='" + userName + "') || '%'" +
                    " AND B.ORG_NO LIKE (" +
                    " SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name='" + userName + "') || '%' ");
            if (type.equals("1"))// 户号
            {
                sbf.append(" AND A.CONS_NO = '" + param + "' ");
            }
            else if (type.equals("5"))// 催费员
            {
                sbf.append(" AND exists (Select 1 " +
                        "  From Epm_Ln.a_Cr_Rela k, Epm_Ln.a_Cr_Sect l, Epm_Ln.Sa_User m " +
                        "  Where k.Remind_Sect_No = l.Remind_Sect_No And m.User_Id = l.Emp_No " +
                        "  and k.Cons_No = a.cons_no and m.user_name='" + param + "') ");
            }
            else if (type.equals("6"))// 抄表员
            {
                sbf.append(" AND exists ( SELECT 1 FROM EPM_LN.R_OPER_ACTIVITY x ,EPM_LN.SA_USER a " +
                        " WHERE x.mr_sect_no = b.mr_sect_no and x.act_code = '03' " +
                        " AND x.operator_no = a.user_id AND a.user_name = '" + param + "' )");
            }
            // 抄表员与催费员因数据量过大，只查当月数据
            if (!type.equals("1"))
            {
                if (StringUtils.isBlank(monthSearch))
                {
                    monthSearch = String.valueOf(year) + appendZero(String.valueOf(month));
                }

                sbf.append("   AND A.RCVBL_YM = '" + monthSearch + "' ");
            }
                  sbf.append("  GROUP BY A.CONS_NO,B.CONS_NAME,A.RCVBL_YM " +
                          "ORDER BY NVL (SUM(A.RCVBL_AMT)-SUM(A.RCVED_AMT),0) DESC) ss GROUP BY ss.ym ) sss");
        }
        System.out.println(sbf.toString());

        return sbf.toString();
    }

//    public static void main(String args[]){
//        Map map = new HashMap();
//        map.put("consNo","0212665370");
//        map.put("month","201907");
//        map.put("deptNo","21409");
//        System.out.println(getUserDetail(map));
//    }

    public String getUserDetail(Map map)
    {
        StringBuffer sbf = new StringBuffer();

        String consNo = map.get("consNo").toString();
        String month = map.get("month").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();

        sbf.append("SELECT a.cons_no consNo,  b.cons_name consName, nvl( sum( a.rcvbl_amt ) - sum( a.rcved_amt ), 0 ) amtMoney,  a.rcvbl_ym rcvblYm, " +
                "  sum( a.t_pq ) pq, sum( a.rcvbl_amt ) rcvblAmt, sum( a.rcvbl_penalty ) rcvblPenalty, " +
                "  ( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'settle_flag' AND prop_list_id = " +
                "  (select max(x.settle_flag) from  Epm_Ln.a_Rcvbl_Flow x where x.rcvbl_ym='" + month + "' and x.cons_no=a.cons_no ");
                /*
                 * 省公司账号无需增加供电单位编号验证
                 */
                if (!deptNo.equals("21102"))
                {
                    sbf.append("  and x.org_no like '" + deptNo + "%' ");
                }
              sbf.append( "    )) settleFlag, " +
                "  ( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'ac_fee_status_code' AND prop_list_id = a.status_code ) statusCode, " +
                "  ( SELECT PROP_LIST_NAME FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'elec_type_code' AND PROP_LIST_ID = b.ELEC_TYPE_CODE ) ELECTYPECODE, " +
                "  ( SELECT PROP_LIST_NAME FROM epm_ln.sa_prop_list WHERE PROP_TYPE_ID = 'pay_mode' AND PROP_LIST_ID = a.pay_mode ) payMode , " +
                // 电气联系人
                "  ( SELECT DISTINCT B1.MOBILE FROM EPM_LN.C_CONTACT B1,EPM_LN.C_CONS C1,EPM_LN.C_CONS_CONTACT_RELA A1 WHERE A1.CONTACT_ID = B1.CONTACT_ID " +
                "    AND A1.CONS_ID = C1.CONS_ID AND B1.CONTACT_MODE = '01' AND B1.MOBILE IS NOT NULL AND C1.CONS_NO = A.CONS_NO AND ROWNUM = 1) DQMOBILE, " +
                // 账务联系人
                "  ( SELECT DISTINCT B1.MOBILE FROM EPM_LN.C_CONTACT B1, EPM_LN.C_CONS C1,EPM_LN.C_CONS_CONTACT_RELA A1 WHERE A1.CONTACT_ID = B1.CONTACT_ID " +
                "    AND A1.CONS_ID = C1.CONS_ID AND B1.CONTACT_MODE = '02' AND B1.MOBILE IS NOT NULL AND C1.CONS_NO = A.CONS_NO AND ROWNUM = 1) ZWMOBILE, " +
                // 停送电联系人
                "  ( SELECT DISTINCT B1.MOBILE FROM EPM_LN.C_CONTACT B1, EPM_LN.C_CONS C1,EPM_LN.C_CONS_CONTACT_RELA A1 WHERE A1.CONTACT_ID = B1.CONTACT_ID " +
                "    AND A1.CONS_ID = C1.CONS_ID AND B1.CONTACT_MODE = '03' AND B1.MOBILE IS NOT NULL AND C1.CONS_NO = A.CONS_NO AND ROWNUM = 1) TSDMOBILE " +
                "   FROM Epm_Ln.a_Rcvbl_Flow a, epm_ln.c_cons b  " +
                "   WHERE  a.cons_no = b.cons_no  " +
                "   AND a.amt_type <> 04  " +
                "   AND a.settle_flag <> 03  " +
                "   AND ( a.invisible_flag IS NULL OR a.invisible_flag = '0' )  " +
                "   AND a.rcvbl_ym = '" + month + "'  " +
                "   AND a.cons_no='" + consNo + "' ");
        /*
         * 省公司账号无需增加供电单位编号验证
         */
        if (!deptNo.equals("21102"))
        {
            sbf.append(" AND a.org_no LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%'  " +
                    "   AND b.org_no LIKE (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%'  ");
        }
        sbf.append("  GROUP BY a.cons_no,  b.cons_name, a.rcvbl_ym, a.status_code, a.acct_no, b.cons_id, b.ELEC_TYPE_CODE, a.pay_mode ");

        return sbf.toString();
    }


    public String getCFUserByOrgNo(Map map)
    {

        String deptNo = map.get("deptNo").toString();

        StringBuffer sbf = new StringBuffer();

        sbf.append("SELECT DISTINCT b.org_no,c.user_name FROM EPM_LN.R_OPER_ACTIVITY a,EPM_LN.sa_dept b,epm_ln.sa_user c " +
                "   WHERE a.operator_no=c.user_id AND b.dept_id=c.dept_id AND a.act_code='03'" +
                "   AND a.effect_flag=1 AND b.org_no LIKE '" + deptNo + "%' ORDER BY b.org_no ");

        return sbf.toString();

    }

    public String getCBUserByOrgNo(Map map)
    {

        String deptNo = map.get("deptNo").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append("SELECT DISTINCT l.org_no,k.user_name FROM EPM_LN.A_CR_SECT L,epm_ln.sa_user k " +
                " WHERE l.emp_no=k.user_id AND l.status_code=01 AND l.org_no LIKE '" + deptNo + "%' ORDER BY l.org_no");
        return sbf.toString();
    }


    //通过当前登录用户 获得所属单位下的抄表员

    public String getCBUser(Map map) {

        String selectType = map.get("selectType").toString();
        String inputName = map.get("inputName").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();

        if (selectType.equals("5"))//催费员
        {
            //为空查询全部
            if (StringUtils.isEmpty(inputName)) {
                sbf.append(
                        "SELECT DISTINCT c.dept_name,b.user_name,b.real_name " +
                                "FROM EPM_LN.R_OPER_ACTIVITY a,EPM_LN.SA_USER b,EPM_LN.SA_DEPT c " +
                                "WHERE c.org_noLIKE ( SELECT c.org_no FROM EPM_LN.SA_USER b, EPM_LN.SA_DEPT c WHERE b.dept_id = c.dept_id AND b.user_name = '" + userName + "' ) || '%'  " +
                                " AND a.operator_no=b.user_id AND b.dept_id=c.dept_id AND a.act_code='03'");
            } else {
                sbf.append(
                        "SELECT DISTINCT c.dept_name,b.user_name,b.real_name " +
                                "FROM EPM_LN.R_OPER_ACTIVITY a,EPM_LN.SA_USER b,EPM_LN.SA_DEPT c " +
                                "WHERE c.org_noLIKE ( SELECT c.org_no FROM EPM_LN.SA_USER b, EPM_LN.SA_DEPT c WHERE b.dept_id = c.dept_id AND b.user_name = '" + userName + "' ) || '%' " +
                                "AND a.operator_no=b.user_id AND b.dept_id=c.dept_id AND a.act_code='03'");

                sbf.append(" AND b.real_name LIKE '" + inputName + "%'");
            }


        }
        if (selectType.equals("6")) {//抄表员
            //为空查询全部
            if (StringUtils.isEmpty(inputName)) {
                sbf.append(
                        "SELECT DISTINCT n.dept_name,m.user_name,m.real_name " +
                                "FROM EPM_LN.A_CR_SECT L,EPM_LN.SA_USER M,EPM_LN.SA_DEPT N " +
                                "WHERE n.org_noLIKE ( SELECT c.org_no FROM EPM_LN.SA_USER b, EPM_LN.SA_DEPT c WHERE b.dept_id = c.dept_id AND b.user_name = '" + userName + "' ) || '%'  " +
                                " AND l.emp_no=m.user_id AND m.dept_id=n.dept_id");
            } else {//模糊查询
                sbf.append(
                        "SELECT DISTINCT n.dept_name,m.user_name,m.real_name " +
                                "FROM EPM_LN.A_CR_SECT L,EPM_LN.SA_USER M,EPM_LN.SA_DEPT N " +
                                "WHERE n.org_noLIKE ( SELECT c.org_no FROM EPM_LN.SA_USER b, EPM_LN.SA_DEPT c WHERE b.dept_id = c.dept_id AND b.user_name = '" + userName + "' ) || '%' " +
                                "AND l.emp_no=m.user_id AND m.dept_id=n.dept_id");

                sbf.append(" AND m.real_name LIKE '" + inputName + "%'");
            }


        }
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    // 月份小于10拼接0
    private static String appendZero(String month)
    {
        if (Integer.valueOf(month) < 10)
        {
            return "0" + month;
        }
        else
        {
            return month;
        }
    }

}
