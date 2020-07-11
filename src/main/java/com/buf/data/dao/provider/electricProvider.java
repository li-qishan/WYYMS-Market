package com.buf.data.dao.provider;

import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public class electricProvider {
//    public String getBasicData(Map map) {
//        String param = map.get("param").toString();
//        String type = map.get("type").toString();
//        String deptNo = map.get("deptNo").toString();
//        String dataGetNumber = map.get("dataGetNumber").toString();
//        StringBuffer sbf = new StringBuffer();
//        sbf.append("  select a.cons_no CONS_NO," +
//                "       a.ym YM," +
//                "       sum(b.t_settle_pq) T_SETTLE_PQ," +
//                "       sum(b.t_amt) t_amt " +
//                "       from  Epm_Ln.Arc_e_Cons_Snap a, Epm_Ln.Arc_e_Cons_Prc_Amt b , " +
//                "  epm_ln.c_meter cm," +
//                "  epm_ln.c_meter_mp_rela re where 1=1");
//        if (!deptNo.equals("21102")) {
//            sbf.append("  and a.org_no like '" + deptNo + "%'" +
//                    "     and b.org_no like '" + deptNo + "%'");
//        }
//        sbf.append("     and a.calc_id=b.calc_id" +
//                "     and cm.cons_mt_id = re.cons_mt_id" +
//                "     and re.cons_no=a.cons_no");
//        if (type.equals("1"))// 户号
//        {
//            sbf.append(" AND A.CONS_NO = '" + param + "' ");
//        }
////        else if (type.equals("2"))// 户名
////        {
////            sbf.append(" AND A.CONS_NAME like '%" + param + "%' ");
////        }
//        else if (type.equals("3"))// 表号
//        {
//            sbf.append("  and cm.bar_code='" + param + "' ");
//        }
//        sbf.append(" group by a.cons_no,a.ym ORDER BY a.ym DESC ");
//
//        System.out.println(sbf.toString());
//
//        String sql = sbf.toString();
//        if (dataGetNumber.equals("0")) {
//            sql = "SELECT * FROM (" + sbf.toString() + ") WHERE ROWNUM <= 6  ORDER BY ROWNUM ASC";
//        }
//
//        return sql;
//    }



    public String getBasicData(Map map) {
        String param = map.get("param").toString();
        String type = map.get("type").toString();
        String deptNo = map.get("deptNo").toString();
        String dataGetNumber = map.get("dataGetNumber").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append("  select a.cons_no CONS_NO," +
                "       a.rcvbl_ym YM," +
                "       sum(a.t_pq) T_SETTLE_PQ," +
                "       sum(a.rcvbl_amt) t_amt " +
                "       from Epm_Ln.a_rcvbl_flow    a  , " +
                "          epm_ln.c_meter            cm, " +
                "    epm_ln.c_meter_mp_rela    re   where 1=1");
        if (!deptNo.equals("21102")) {
            sbf.append("  and a.org_no like (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' " );
        }
        sbf.append("and a.amt_type<>04 " +
                "   and cm.cons_mt_id = re.cons_mt_id " +
                "   and re.cons_no = a.cons_no ");
        if (type.equals("1"))// 户号
        {
            sbf.append(" AND A.CONS_NO = '" + param + "' ");
        }
//        else if (type.equals("2"))// 户名
//        {
//            sbf.append(" AND A.CONS_NAME like '%" + param + "%' ");
//        }
        else if (type.equals("3"))// 表号
        {
            sbf.append("  and cm.bar_code='" + param + "' ");
        }
        sbf.append(" group by a.cons_no,a.rcvbl_ym ORDER BY a.rcvbl_ym DESC ");

        System.out.println(sbf.toString());

        String sql = sbf.toString();
        if (dataGetNumber.equals("0")) {
            sql = "SELECT * FROM (" + sbf.toString() + ") WHERE ROWNUM <= 6  ORDER BY ROWNUM ASC";
        }

        return sql;
    }


}
