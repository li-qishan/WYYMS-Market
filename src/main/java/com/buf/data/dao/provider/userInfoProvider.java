package com.buf.data.dao.provider;

import com.buf.common.utils.StringUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public class userInfoProvider
{


    public String getSearchData(Map map)
    {
        String param = map.get("param").toString();
        String type = map.get("type").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();

        StringBuffer sbf = new StringBuffer();
        sbf.append("SELECT DISTINCT " +
                " a.cons_no CONSNO, " +
                " a.cons_name CONSNAME, " +
                " a.elec_addr ELECADDR, " +
                " c.mobile  " +
                "FROM " +
                " epm_ln.c_cons a, " +
                " epm_ln.c_cons_contact_rela b, " +
                " epm_ln.c_contact c, " +
                " epm_ln.c_meter cm, " +
                " epm_ln.c_meter_mp_rela re  " +
                "WHERE a.status_code <> 9  " +
                " AND a.cons_id = b.cons_id ( + )  " +
                " AND b.contact_id = c.contact_id  " +
                " AND c.contact_mode = '01'  " +
                " AND cm.cons_mt_id = re.cons_mt_id  " +
                " AND re.cons_no = a.cons_no ");
        /*
         * 省公司账号无需增加供电单位编号验证
         **/
        if (!deptNo.equals("21102"))
        {
            sbf.append(" and a.org_no like  (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }

        if (type.equals("1"))// 户号
        {
            sbf.append(" AND a.cons_no = '" + param + "'");
            sbf.append("   and rownum = 1");
        }
        else if (type.equals("2"))// 手机号码
        {
            sbf.append(" AND c.mobile = '" + param + "'");
        }
        else if (type.equals("3"))// 表号
        {
            sbf.append( " and cm.bar_code='" + param + "' ");
        }
        else if (type.equals("7"))// 用户名
        {
            sbf.append("and a.CONS_NAME = '" + param + "'");
        }

        return sbf.toString();
    }


    public String getBasicData(Map map)
    {
        String param = map.get("param").toString();
        String type = map.get("type").toString();
        String deptNo = map.get("deptNo").toString();
        String dataGetNumber = map.get("dataGetNumber").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();

        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;


        sbf.append("select (select org_name from epm_ln.sa_org where org_no = a.org_no and rownum=1 ) ORG_NAME," +
                "       (SELECT epm_ln.SA_DEPT.DEPT_NAME" +
                "          FROM EPM_LN.R_OPER_ACTIVITY, epm_ln.SA_USER, epm_ln.SA_DEPT" +
                "         WHERE EPM_LN.R_OPER_ACTIVITY.MR_SECT_NO = A.MR_SECT_NO" +
                "           AND EPM_LN.R_OPER_ACTIVITY.OPERATOR_NO = epm_ln.SA_USER.USER_ID" +
                "           AND epm_ln.SA_USER.DEPT_ID = epm_ln.SA_DEPT.DEPT_ID" +
                "           AND EPM_LN.R_OPER_ACTIVITY.ACT_CODE = '03' and rownum=1) DEPT_NAME," +
                "       (SELECT epm_ln.SA_USER.REAL_NAME" +
                "          FROM EPM_LN.R_OPER_ACTIVITY, epm_ln.SA_USER" +
                "         WHERE EPM_LN.R_OPER_ACTIVITY.MR_SECT_NO = A.MR_SECT_NO" +
                "           AND EPM_LN.R_OPER_ACTIVITY.OPERATOR_NO = epm_ln.SA_USER.USER_ID" +
                "           AND EPM_LN.R_OPER_ACTIVITY.ACT_CODE = '03' and rownum=1 ) CB_REAL_NAME," +
                "       (Select m.Real_Name" +
                "          From Epm_Ln.a_Cr_Rela k, Epm_Ln.a_Cr_Sect l, Epm_Ln.Sa_User m" +
                "         Where k.Remind_Sect_No = l.Remind_Sect_No" +
                "           And m.User_Id = l.Emp_No" +
                "           and k.Cons_No = a.Cons_No and rownum=1) CF_REAL_NAME," +
                "       a.cons_no CONS_NO," +
                "       a.cons_name CONS_NAME," +
                "       a.elec_addr ELEC_ADDR," +
                "       a.mr_sect_no MR_SECT_NO," +
                "       (SELECT prop_list_name" +
                "          FROM epm_ln.SA_PROP_LIST" +
                "         WHERE PROP_TYPE_ID = 'cons_status_code'" +
                "           and prop_list_id = a.status_code ) STATUS_CODE," +
                "       a.build_date BUILD_DATE," +
                "       a.cust_query_no CUST_QUERY_NO," +
                "       (SELECT PROP_LIST_NAME " +
                "                    FROM epm_ln.SA_PROP_LIST " +
                "                   WHERE PROP_TYPE_ID = 'volt_code' AND PROP_LIST_ID = A.VOLT_CODE) DYDJ, " +
                "          (SELECT PROP_LIST_NAME " +
                "                    FROM epm_ln.SA_PROP_LIST " +
                "                   WHERE PROP_TYPE_ID = 'meas_mode' AND PROP_LIST_ID = A.MEAS_MODE) JLFS, " +
                "       (select distinct b1.mobile" +
                "          from epm_ln.c_contact           b1," +
                "               epm_ln.c_cons              c1," +
                "               epm_ln.c_cons_contact_rela a1" +
                "         where a1.contact_id = b1.contact_id" +
                "           and a1.cons_id = c1.cons_id" +
                "           and b1.contact_mode = '01'" +
                "           and b1.mobile is not null" +
                "           and c1.cons_no = a.cons_no" +
                "           and rownum = 1) DQMOBILE," +
                "               (select distinct b1.mobile " +
                "          from epm_ln.c_contact           b1, " +
                "               epm_ln.c_cons              c1, " +
                "               epm_ln.c_cons_contact_rela a1 " +
                "         where a1.contact_id = b1.contact_id " +
                "           and a1.cons_id = c1.cons_id " +
                "           and b1.contact_mode = '02' " +
                "           and b1.mobile is not null " +
                "           and c1.cons_no = a.cons_no " +
                "           and rownum = 1) ZWMOBILE, " +
                "             (select distinct b1.mobile " +
                "          from epm_ln.c_contact           b1, " +
                "               epm_ln.c_cons              c1, " +
                "               epm_ln.c_cons_contact_rela a1 " +
                "         where a1.contact_id = b1.contact_id " +
                "           and a1.cons_id = c1.cons_id " +
                "           and b1.contact_mode = '03' " +
                "           and b1.mobile is not null " +
                "           and c1.cons_no = a.cons_no " +
                "           and rownum = 1) TSDMOBILE, " +
                "       (SELECT PROP_LIST_NAME" +
                "          FROM epm_ln.SA_PROP_LIST" +
                "         WHERE PROP_TYPE_ID = 'elec_type_code'" +
                "           AND PROP_LIST_ID = A.ELEC_TYPE_CODE) TYPE," +
                "       (select TRADE" +
                "          from epm_ln.sa_c_trade_type" +
                "         where a.TRADE_CODE = sa_c_trade_type.TRADE_CODE) TRADE," +
                "        cm.BAR_CODE ," +
                "       cm.T_FACTOR ," +
                "  (select xx.tg_name from epm_ln.g_tg xx ,epm_ln.c_mp yy where xx.tg_id=yy.tg_id and yy.cons_id=a.cons_id and rownum=1) TGNAME," +
//                "       (select max(xx.pap_r)" +
//                "          from edas.E_MP_day_read@cj_Read xx" +
//                "         where xx.org_no like '21409%'" +
//                "           and xx.data_date = to_date('2019-05-13', 'yyyy-mm-dd')" +
//                "           and xx.cons_no = a.cons_no) PAP_R," +
                "       a.contract_cap CONTRACT_CAP," +
                "       a.run_cap RUN_CAP," +
                "       (select max(x.this_read) from epm_ln.arc_r_data x where x.cons_no=a.cons_no  " +
                "       and x.amt_ym='"+ String.valueOf(year) + appendZero(String.valueOf(month)) +"') THIS_READ, " +
                "       ( select x.prepay_bal  from epm_ln.a_acct_bal x where x.cons_no=a.cons_no ) PREPAY_BAL  " +
                "       from epm_ln.c_cons a,epm_ln.c_meter cm, epm_ln.c_meter_mp_rela re  where 1=1");
        // 省公司账号无需增加供电单位编号验证
        if (!deptNo.equals("21102"))
        {
            sbf.append(" and a.org_no like  (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }
        sbf.append(" and a.status_code <> 9 " +
                "   and cm.cons_mt_id = re.cons_mt_id " +
                "   and re.cons_id=a.cons_id ");
        if (type.equals("1"))// 户号
        {
            sbf.append(" AND A.CONS_NO = '" + param + "' ");
        }
        else if (type.equals("2"))// 手机号码
        {
            sbf.append(" AND A.CONS_NAME LIKE '%" + param + "%' ");
        }
        else if (type.equals("3"))// 表号
        {
            sbf.append(" and cm.bar_code='" + param + "' ");
        }
        else if (type.equals("7"))// 用户名
        {
            sbf.append("and a.CONS_NAME = '" + param + "'");
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
