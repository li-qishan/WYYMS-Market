package com.buf.data.dao.provider;

import java.util.Map;

/**
 * Created by mawenguang on 2019/7/23.
 */
public class SpPrcProvider
{
    public String getSpPrcData(Map map)
    {
        StringBuffer sb = new StringBuffer();

        String consNo = map.get("consNo").toString();

        sb.append("SELECT DISTINCT " +
                "	( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'prc_type_code' AND prop_list_id = c.type_code ) prcType, " +
                "	( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'ba_calc_mode' AND prop_list_id = c.ba_calc_mode ) baCalc, " +
                "	c.dmd_spec_value dmdSpecValue, " +
                "	( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'pf_eval_mode' AND prop_list_id = c.pf_eval_mode ) pfEvalMode, " +
                "	a.prc_code prcCode, " +
                "	dj.cat_prc_name catPrcName, " +
                "	dj.kwh_prc kwhPrc, " +
                "	d.trade trade, " +
                "	( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'yes_no_flag' AND prop_list_id = a.ts_flag ) fsFlag, " +
                "	( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'pf_std' AND prop_list_id = a.pf_std_code ) pfStd, " +
                "	a.fix_ratio fixRatio, " +
                "	e.cons_no consNo, " +
                "	e.org_no orgNo, " +
                "	f.sp_id spId, " +
                "	f.sp_name spName  " +
                "FROM " +
                "	epm_ln.c_cons_prc a, " +
                "	( " +
                "		SELECT " +
                "			a.prc_code, " +
                "			a.cat_prc_name, " +
                "			b.kwh_prc  " +
                "		FROM " +
                "			epm_ln.e_cat_prc a, " +
                "			epm_ln.e_cat_prc_det b  " +
                "		WHERE " +
                "			a.cat_prc_id = b.cat_prc_id  " +
                "			AND a.para_vn = ( SELECT max( para_vn ) FROM epm_ln.e_bill_para_ver WHERE par_ver_type = 1 AND release_flag = 1 )  " +
                "			AND ( b.IDU_FLAG = '0' OR b.IDU_FLAG = '1' OR b.IDU_FLAG IS NULL )  " +
                "			AND b.REF_TS_FLAG = '1'  " +
                "	) dj, " +
                "	epm_ln.c_cons_prc_tactic c, " +
                "	epm_ln.sa_c_trade_type d, " +
                "	epm_ln.c_cons e, " +
                "	epm_ln.c_sp f  " +
                "WHERE " +
                "	a.prc_code = dj.prc_code ( + )  " +
                "	AND a.cons_id = c.cons_id ( + )  " +
                "	AND a.trade_code = d.trade_code ( + )  " +
                "	AND a.cons_id = e.cons_id  " +
                "	AND e.cons_id = f.cons_id  " +
                "	AND a.sp_id = f.sp_id  " +
                "	AND e.cons_no = '" + consNo + "'  " +
                "ORDER BY " +
                "	f.sp_id");


        return sb.toString();
    }

    public String getMpDetail(Map map)
    {

        StringBuffer sb = new StringBuffer();
        String consNo = map.get("consNo").toString();
        String prcCode = map.get("prcCode").toString();
        String spId = map.get("spId").toString();
        sb.append("SELECT " +
                "  a.MP_ID mpId," +
                "  a.mp_no mpNo, " +
                "  a.mp_name mpName, " +
                "  a.mp_addr mpAddr, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'mp_attr_code' AND prop_list_id = a.mp_attr_code ) mpAttr, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'md_type_code' AND prop_list_id = a.md_type_code ) mdType, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'mp_type_code' AND prop_list_id = a.type_code ) mpType, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'usage_type_code' AND prop_list_id = a.usage_type_code ) usageType, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'exchg_sort_code' AND prop_list_id = a.exchg_type_code ) exchgSort, " +
                "  a.mp_cap mpCap, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'volt_code' AND prop_list_id = a.volt_code ) voltCode, " +
                "  b.prc_code prcCode, " +
                "  b.kwh_prc kwhPrc, " +
                "  b.cat_prc_name catPrcName, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'pq_calc_mode' AND prop_list_id = a.CALC_MODE ) pqCalc, " +
                "  a.fqr_value fqrValue, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'yes_no_flag' AND prop_list_id = a.FR_DEDUCT_FLAG ) frDeductFlag, " +
                "  a.deduct_order deductOrder, " +
                "  (SELECT  max( ls.prop_list_name ) FROM  epm_ln.SA_PROP_LIST ls,  epm_ln.c_mp mp WHERE  ls.PROP_TYPE_ID = 'yes_no_flag'  " +
                "    AND ls.prop_list_id = mp.tl_bill_flag   AND mp.cons_id = a.cons_id   ) tlBillFlag, " +
                "  ( SELECT  max( ls.prop_list_name ) FROM  epm_ln.SA_PROP_LIST ls,  epm_ln.c_mp mp WHERE  ls.PROP_TYPE_ID = 'tl_share_flag'  " +
                "    AND ls.prop_list_id = mp.tl_share_flag   AND mp.cons_id = a.cons_id   ) tlShareFlag, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'meas_mode' AND prop_list_id = a.meas_mode ) measMode, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'TL_SHARE_VALUE' AND prop_list_id = a.TL_SHARE_VALUE ) tlShareValue, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'yes_no_flag' AND prop_list_id = a.ll_bill_flag ) llBillFlag, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'mp_ll_calc_code' AND prop_list_id = a.LL_CALC_MODE ) llCalcMode, " +
                "  a.ap_ll_value apLlValue, " +
                "  a.rp_ll_value rpLlValue, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'yes_no_flag' AND prop_list_id = a.LL_SHARE_FLAG ) llShareFlag, " +
                "  a.ll_share_value llShareValue, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'yes_no_flag' AND prop_list_id = a.METER_FLAG ) meterFlag, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'yes_no_flag' AND prop_list_id = a.LC_FLAG ) lcFlag, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'mp_status' AND prop_list_id = a.STATUS_CODE ) mpStatus, " +
                "  c.tg_name tgName, " +
                "  d.line_name lineName, " +
                "  ( SELECT prop_list_name FROM epm_ln.SA_PROP_LIST WHERE PROP_TYPE_ID = 'wiring_mode' AND prop_list_id = a.wiring_mode ) wiringMode  " +
                "FROM " +
                "  epm_ln.c_mp a, " +
                "  ( SELECT x.cons_id,y.cat_prc_name,y.prc_code,z.cat_kwh_prc,x.tariff_id,z.kwh_prc FROM  " +
                "       epm_ln.c_cons_prc x,epm_ln.E_CAT_PRC y,epm_ln.E_CAT_PRC_DET z WHERE x.prc_code = y.prc_code  " +
                "       AND y.para_vn = ( SELECT max( para_vn ) FROM epm_ln.e_bill_para_ver WHERE par_ver_type = 1 AND release_flag = 1 )" +
                "       AND y.cat_prc_id = z.cat_prc_id AND z.ref_Ts_flag = '1' ) b, " +
                "  epm_ln.g_tg c, " +
                "  epm_ln.g_line d  " +
                "WHERE " +
                "  a.cons_no = '" + consNo + "'  " +
                "  AND a.SP_ID = '" + spId + "' " +
                "  AND b.PRC_CODE = '" + prcCode + "' " +
                "  AND a.tg_id = c.tg_id ( + )  " +
                "  AND a.line_id = d.line_id ( + )  " +
                "  AND a.cons_id = b.cons_id ( + )  " +
                "  AND a.tariff_id = b.tariff_id ( + )");


        return sb.toString();
    }

    public String getMeterDetail(Map map)
    {
        StringBuffer sb = new StringBuffer();

        String consNo = map.get("consNo").toString();
        String mpId = map.get("mpId").toString();

        sb.append("SELECT " +
                "  a.CONS_MT_ID consMtId, " +
                "  a.bar_code barCode, " +
                "  a.t_factor tFactor, " +
                "  to_char(a.inst_date ,'yyyy-MM-dd')instDate, " +
                "  a.last_chk_date lastCheckDate  " +
                "FROM " +
                "  epm_ln.c_meter a, " +
                "  epm_ln.c_meter_mp_rela b, " +
                "  epm_ln.c_cons c  " +
                "WHERE " +
                "  a.cons_mt_id = b.cons_mt_id  " +
                "  AND c.cons_id = b.cons_id  " +
                "  AND b.MP_ID = '" + mpId + "'  " +
                "  AND c.cons_no = '" + consNo + "'");

        return sb.toString();
    }

    public String getMeterReadDetail(Map map)
    {
        StringBuffer sb = new StringBuffer();

        String consNo = map.get("consNo").toString();
        String consMtId = map.get("consMtId").toString();

        sb.append("SELECT * FROM ( " +
                "  SELECT " +
                "   ( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'rd_type' AND prop_list_id = a.read_type_code ) readType, " +
                "   a.READ readCount, " +
                "   ( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'read_check_type' AND prop_list_id = a.check_type ) checkType, " +
                "   to_char ( a.mr_date, 'yyyy-MM-dd' ) mrDate, " +
                "   ( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'usage_type_code' AND prop_list_id = a.usage_code ) usageCode  " +
                "  FROM " +
                "   epm_ln.c_meter_read a  " +
                "  WHERE " +
                "   a.CONS_MT_ID = '" + consMtId + "'  " +
                "   AND a.cons_no = '" + consNo + "' " +
                "   AND to_char ( sysdate, 'yyyy' ) = to_char ( a.mr_date, 'yyyy' ) " +
                "  ORDER BY a.mr_pq DESC  " +
                " ) ss  " +
                "WHERE ss.readType NOT LIKE '反%' ");

        return sb.toString();
    }

}
