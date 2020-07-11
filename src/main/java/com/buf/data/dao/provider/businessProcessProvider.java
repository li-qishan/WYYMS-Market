package com.buf.data.dao.provider;

import com.buf.common.utils.StringUtils;

import java.util.Map;

/**
 * 业扩流程查询
 *
 * @author liqishan
 * @create 2019-06-21 15:22
 */
public class businessProcessProvider
{


    /**
     * 工单信息查询（简略）
     *
     * @param map
     * @return
     */
    public String getLessBusinessProcessByOrderNum(Map map)
    {
        String orderNum = map.get("orderNum").toString();
        String deptNo = map.get("deptNo").toString();
        String selectType = map.get("selectType").toString();
        String userName = map.get("userName").toString();

        StringBuffer sbf = new StringBuffer();
        sbf.append("SELECT ((CASE" +
                "         WHEN (INSTANCE_STATUS = '01') THEN" +
                "          '创建'" +
                "         WHEN (INSTANCE_STATUS = '03') THEN" +
                "          '注销'" +
                "         WHEN (INSTANCE_STATUS = '04') THEN" +
                "          '取消'" +
                "         WHEN (INSTANCE_STATUS = '02') THEN" +
                "          '完成'" +
                "         ELSE" +
                "          '其他'" +
                "       END)) AS state," +
                "       A.APP_NO appNo," +
                "       a.cons_no consNo," +
                "       a.cons_name consName," +
                "       a.elec_addr consAddr," +
                "       A.HANDLE_TIME acceptTime," +
                "       WF.COMPLETE_TIME finishedTime," +
                "       (SELECT PROP_LIST_NAME" +
                "          FROM EPM_LN.SA_PROP_LIST" +
                "         WHERE PROP_TYPE_ID = 'workform_type'" +
                "           AND PROP_LIST_ID = WF.WORKFORM_TYPE) businessType," +
                "       (SELECT PROP_LIST_NAME" +
                "          FROM EPM_LN.SA_PROP_LIST" +
                "         WHERE PROP_TYPE_ID = 'cust_type_code'" +
                "           AND PROP_LIST_ID = A.CONS_SORT_CODE) consType," +
                "           a.cons_id" +
                "  FROM EPM_LN.S_APP A," +
                "       EPM_LN.SA_USER SU," +
                "       EPM_LN.SA_DEPT SD," +
                "        EPM_LN.SA_WORKFORM_INST WF" +
                " WHERE A.APP_NO = WF.KEYDATA_ID" +
                "   AND WF.ATTR2 IS NULL" +
                "   AND WF.INSTANCE_STATUS IS NOT NULL" +
                "   AND WF.BUILDER = SU.USER_ID(+)" +
                "   AND SU.DEPT_ID = SD.DEPT_ID(+)" +
                "   AND A.APP_TYPE_CODE = WF.WORKFORM_TYPE ");
//                "   and wf.attr5='-1'" +
        if (!deptNo.equals("21102"))
        {
            sbf.append("   and a.org_no like substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
        }
        if (selectType.equals("4"))
        {
            sbf.append("   AND A.APP_NO = '" + orderNum + "' ");
            sbf.append("union all SELECT ((CASE" +
                    "         WHEN (INSTANCE_STATUS = '01') THEN" +
                    "          '创建'" +
                    "         WHEN (INSTANCE_STATUS = '03') THEN" +
                    "          '注销'" +
                    "         WHEN (INSTANCE_STATUS = '04') THEN" +
                    "          '取消'" +
                    "         WHEN (INSTANCE_STATUS = '02') THEN" +
                    "          '完成'" +
                    "         ELSE" +
                    "          '其他'" +
                    "       END)) AS state," +
                    "       A.APP_NO appNo," +
                    "       a.cons_no consNo," +
                    "       a.cons_name consName," +
                    "       a.elec_addr consAddr," +
                    "       A.HANDLE_TIME acceptTime," +
                    "       WF.COMPLETE_TIME finishedTime," +
                    "       (SELECT PROP_LIST_NAME" +
                    "          FROM EPM_LN.SA_PROP_LIST" +
                    "         WHERE PROP_TYPE_ID = 'workform_type'" +
                    "           AND PROP_LIST_ID = WF.WORKFORM_TYPE) businessType," +
                    "       (SELECT PROP_LIST_NAME" +
                    "          FROM EPM_LN.SA_PROP_LIST" +
                    "         WHERE PROP_TYPE_ID = 'cust_type_code'" +
                    "           AND PROP_LIST_ID = A.CONS_SORT_CODE) consType," +
                    "           a.cons_id" +
                    "  FROM EPM_LN.ARC_S_APP A," +
                    "       EPM_LN.SA_USER SU," +
                    "       EPM_LN.SA_DEPT SD," +
                    "        EPM_LN.SA_WORKFORM_INST_HISTORY WF" +
                    " WHERE A.APP_NO = WF.KEYDATA_ID" +
                    "   AND WF.ATTR2 IS NULL" +
                    "   AND WF.INSTANCE_STATUS IS NOT NULL" +
                    "   AND WF.BUILDER = SU.USER_ID(+)" +
                    "   AND SU.DEPT_ID = SD.DEPT_ID(+)" +
                    "   AND A.APP_TYPE_CODE = WF.WORKFORM_TYPE ");
            if (!deptNo.equals("21102"))
            {
                sbf.append("   and a.org_no like substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
            }

            sbf.append("   AND A.APP_NO = '" + orderNum + "' ");
            sbf.append("order by state ASC,");
            sbf.append(" acceptTime DESC");

        }
        else if (selectType.equals("1"))
        {
            sbf.append("   AND A.CONS_NO = '" + orderNum + "' ");
            sbf.append("union all SELECT ((CASE" +
                    "         WHEN (INSTANCE_STATUS = '01') THEN" +
                    "          '创建'" +
                    "         WHEN (INSTANCE_STATUS = '03') THEN" +
                    "          '注销'" +
                    "         WHEN (INSTANCE_STATUS = '04') THEN" +
                    "          '取消'" +
                    "         WHEN (INSTANCE_STATUS = '02') THEN" +
                    "          '完成'" +
                    "         ELSE" +
                    "          '其他'" +
                    "       END)) AS state," +
                    "       A.APP_NO appNo," +
                    "       a.cons_no consNo," +
                    "       a.cons_name consName," +
                    "       a.elec_addr consAddr," +
                    "       A.HANDLE_TIME acceptTime," +
                    "       WF.COMPLETE_TIME finishedTime," +
                    "       (SELECT PROP_LIST_NAME" +
                    "          FROM EPM_LN.SA_PROP_LIST" +
                    "         WHERE PROP_TYPE_ID = 'workform_type'" +
                    "           AND PROP_LIST_ID = WF.WORKFORM_TYPE) businessType," +
                    "       (SELECT PROP_LIST_NAME" +
                    "          FROM EPM_LN.SA_PROP_LIST" +
                    "         WHERE PROP_TYPE_ID = 'cust_type_code'" +
                    "           AND PROP_LIST_ID = A.CONS_SORT_CODE) consType," +
                    "           a.cons_id" +
                    "  FROM EPM_LN.ARC_S_APP A," +
                    "       EPM_LN.SA_USER SU," +
                    "       EPM_LN.SA_DEPT SD," +
                    "        EPM_LN.SA_WORKFORM_INST_HISTORY WF" +
                    " WHERE A.APP_NO = WF.KEYDATA_ID" +
                    "   AND WF.ATTR2 IS NULL" +
                    "   AND WF.INSTANCE_STATUS IS NOT NULL" +
                    "   AND WF.BUILDER = SU.USER_ID(+)" +
                    "   AND SU.DEPT_ID = SD.DEPT_ID(+)" +
                    " AND A.APP_TYPE_CODE = WF.WORKFORM_TYPE ");

            if (!deptNo.equals("21102"))
            {
                sbf.append("   and a.org_no like substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
            }

            sbf.append("   AND A.CONS_NO = '" + orderNum + "' ");
            sbf.append("order by state ASC ,");
            sbf.append(" acceptTime DESC");

        }
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    /**
     * 工单信息查询（详细）
     *
     * @param map
     * @return
     */
    public String getMoreBusinessProcessByOrderNum(Map map)
    {
        String orderNum = map.get("orderNum").toString();
        String deptNo = map.get("deptNo").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append(" SELECT  distinct  C.NAME WORKNAME," +
                "              (select prop_list_name" +
                "                   from epm_ln.sa_prop_list" +
                "                  where prop_type_id = 'workitem_status'" +
                "                    and prop_list_id = c.current_state and length(prop_list_id)=1" +
                "                    and rownum = 1) STATE," +
                "                C.CREATE_TIME CREATETIME," +
                "                C.COMPLETE_TIME FINISHEDTIME," +
                "                 (select x.dept_name" +
                "                   from epm_ln.sa_dept x, epm_ln.sa_user y" +
                "                  where x.dept_id = y.dept_id" +
                "                    and y.user_id = c.user_id) DEALDEPT," +
                "                (select y.real_name" +
                "                   from epm_ln.sa_user y" +
                "                  where y.user_id = c.user_id) DEALPERSON" +
                "               " +
                "  FROM EPM_LN.SA_WORKFORM_INST   B, EPM_LN.RT_WORKITEMINST C " +
                " WHERE b.keydata_id = '" + orderNum + "' ");

        if (!deptNo.equals("21102"))
        {
            sbf.append(" and  b.ORG_NO LIKE substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
        }
        sbf.append(" AND C.PROC_INSTANCE_ID IN" +
                " (SELECT PROC_INSTANCE_ID" +
                "    FROM EPM_LN.RT_PROCESSINSTANCE" +
                "   WHERE (RT_PROCESSINSTANCE.PROC_INSTANCE_ID = B.PROCINST_ID OR" +
                "         RT_PROCESSINSTANCE.PARENT_PROCINC_ID = B.PROCINST_ID))" +
                "   union all " +
                " SELECT distinct  C.NAME WORKNAME," +
                "              (select prop_list_name" +
                "                   from epm_ln.sa_prop_list" +
                "                  where prop_type_id = 'workitem_status'" +
                "                    and prop_list_id = c.current_state and length(prop_list_id)=1" +
                "                    and rownum = 1) STATE," +
                "                C.CREATE_TIME CREATETIME," +
                "                C.COMPLETE_TIME FINISHEDTIME," +
                "                 (select x.dept_name" +
                "                   from epm_ln.sa_dept x, epm_ln.sa_user y" +
                "                  where x.dept_id = y.dept_id" +
                "                    and y.user_id = c.user_id) DEALDEPT," +
                "                (select y.real_name" +
                "                   from epm_ln.sa_user y" +
                "                  where y.user_id = c.user_id) DEALPERSON" +
                "  FROM EPM_LN.SA_WORKFORM_INST_history  B, EPM_LN.RT_WORKITEMINST C " +
                " WHERE b.keydata_id ='" + orderNum + "' ");
        if (!deptNo.equals("21102"))
        {
            sbf.append(" and  b.ORG_NO LIKE substr( (SELECT a.org_no FROM epm_ln.sa_org a,epm_ln.sa_dept b,epm_ln.sa_user c WHERE a.org_no=b.org_no AND b.dept_id=c.dept_id AND c.user_name = '" + userName + "'),1,5)  || '%' ");
        }
        sbf.append(" AND C.PROC_INSTANCE_ID IN" +
                " (SELECT PROC_INSTANCE_ID" +
                "    FROM EPM_LN.RT_PROCESSINSTANCE" +
                "   WHERE (RT_PROCESSINSTANCE.PROC_INSTANCE_ID = B.PROCINST_ID OR" +
                "         RT_PROCESSINSTANCE.PARENT_PROCINC_ID = B.PROCINST_ID))" +
                "  order by CREATETIME ASC ");
        System.out.println(sbf.toString());
        return sbf.toString();
    }
}
