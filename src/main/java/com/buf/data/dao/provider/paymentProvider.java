package com.buf.data.dao.provider;

import java.util.Map;

/**
 * Created by mawenguang on 2019/5/14.
 */
public class paymentProvider {
    public String getBasicData(Map map) {
        String param = map.get("param").toString();
        String type = map.get("type").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();
        String dataGetNumber = map.get("dataGetNumber").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append("select c.cons_no CONS_NO," +
                "   c.charge_date CHARGE_YM," +
                " (select prop_list_name from epm_ln.sa_prop_list where prop_type_id='pay_mode' and prop_list_id=c.pay_mode) PAYMENTTYPE," +
                " c.rcv_amt RCV_AMT" +
                " from  epm_ln.a_pay_flow c , " +
                "  epm_ln.c_meter cm," +
                "  epm_ln.c_meter_mp_rela re , epm_ln.c_cons cs where 1=1 ");
        if (!deptNo.equals("21102")) {
            sbf.append("  and c.org_no like  (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "')  || '%' ");
        }
        sbf.append("  and cm.cons_mt_id = re.cons_mt_id" +
                " and c.cons_no=re.cons_no " +
                " and cs.cons_no=c.cons_no" +
                " and c.type_code like '1%'");
        if (type.equals("1"))// 户号
        {
            sbf.append(" AND C.CONS_NO = '" + param + "'");
        }
//        else if (type.equals("2"))// 户名
//        {
//            sbf.append("AND c.cons_no = cs.cons_no " +
//                    " AND cs.cons_name like '" + param + "'");
//        }
        else if (type.equals("3"))// 表号
        {
            sbf.append(" AND cm.bar_code = '" + param + "'");
        }
        sbf.append(" ORDER BY c.charge_ym DESC ");

        String sql = sbf.toString();
        if (dataGetNumber.equals("0")) {
            sql = "SELECT * FROM (" + sbf.toString() + ") WHERE ROWNUM <= 6  ORDER BY ROWNUM ASC";
        }

        return sql;
    }


}
