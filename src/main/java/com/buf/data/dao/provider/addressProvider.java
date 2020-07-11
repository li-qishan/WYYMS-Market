package com.buf.data.dao.provider;

import java.util.Map;

public class addressProvider {

    public String getUserAddressDetail(Map map) {

        String consNo = map.get("consNo").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();
        sbf.append(
                "SELECT "
                        + "a.cons_no,"
                        + "a.cons_name,"
                        + "a.elec_addr,"
                        + "( SELECT district_name FROM epm_ln.sa_c_district WHERE district_no = b.province_code AND district_type = '01' ) province ,"
                        + "b.province_code, "
                        + "( SELECT district_name FROM epm_ln.sa_c_district WHERE district_no = b.city_code AND district_type = '02' ) city ,"
                        + "b.city_code ,"
                        + "( SELECT district_name FROM epm_ln.sa_c_district WHERE district_no = b.county_code AND district_type = '03' ) county ,"
                        + "b.county_code , "
                        + " ( SELECT prop_list_name FROM epm_ln.sa_prop_list WHERE prop_type_id = 'addr_type_code' AND prop_list_id = c.TYPE_CODE ) addr_type ,"
                        + "b.street_code streetCode,"
                        + "b.street_name streetName,"
                        + "b.village_code villageCode,"
                        + "b.village_name villageName,"
                        + "b.road_code rodeCode,"
                        + "b.road_name roadName,"
                        + "b.community_code communityCode,"
                        + "b.community_name communityName,"
                        + "b.plate_no plateNo,"
                        + "b.building building "
                        + " FROM "
                        + "epm_ln.c_cons a,"
                        + "epm_ln.c_elec_addr b ,"
                        + " EPM_LN.C_CUST_ADDR c "
                        + " WHERE "
                        + "a.org_no LIKE substr("
                        + "("
                        + "SELECT "
                        + "de.org_no "
                        + " FROM "
                        + "epm_ln.sa_dept de,"
                        + "epm_ln.sa_user us "
                        + " WHERE "
                        + "de.dept_id = us.dept_id "
                        + "AND us.user_name = '" + userName + "' "
                        + "),"
                        + "1,"
                        + "5 "
                        + ") || '%' "
                        + "AND a.status_code <> '9' "
                        + "AND a.cons_id = b.cons_id "
                        + "and a.CUST_ID = c.CUST_ID "
                        + "AND a.cons_no = '" + consNo + "'");
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    public String getUserCountryArea(Map map) {

        //String consNo = map.get("consNo").toString();
        String userName = map.get("userName").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append(
                "SELECT DISTINCT "
                        + " district_no, "
                        + " district_type, "
                        + " district_name, "
                        + " p_district_no, "
                        + " org_no  "
                        + "FROM "
                        + " ( "
                        + "SELECT "
                        + " aa.district_no, "
                        + " aa.district_type, "
                        + " aa.district_name, "
                        + " aa.p_district_no, "
                        + " regexp_substr ( aa.org_no, '[^,]+', 1, LEVEL ) org_no  "
                        + "FROM "
                        + " epm_ln.sa_c_district AA connect BY LEVEL <= length( aa.org_no ) - length( REPLACE ( aa.org_no, ',''' ) ) + 1  "
                        + " )  "
                        + "WHERE "
                        + " org_no LIKE substr( "
                        + " ( "
                        + "SELECT "
                        + " de.org_no  "
                        + "FROM "
                        + " epm_ln.sa_dept de, "
                        + " epm_ln.sa_user us  "
                        + "WHERE "
                        + " de.dept_id = us.dept_id  "
                        + " AND us.user_name = '" + userName + "'  "
                        + " ), "
                        + " 1, "
                        + " 5  "
                        + " ) || '%'");
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    //--2--取区县下街道
    public String getUserStreet(Map map) {

        String countyCode = map.get("countyCode").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append(" select  bb.county_code, "
                + "   bb.code_type, "
                + "   bb.value, "
                + "   bb.name "
                + "   from epm_ln.c_elec_addr_det bb "
                + "   where bb.code_type='04'  "
                + "   and bb.is_delete='0' "
                + "   and bb.county_code='" + countyCode + "' ");
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    // --3--取区县下道路
    public String getUserRoad(Map map) {

        String countyCode = map.get("countyCode").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append(
                "select  cc.county_code, "
                        + "   cc.code_type, "
                        + "   cc.value, "
                        + "   cc.name "
                        + "   from epm_ln.c_elec_addr_det cc "
                        + "   where cc.code_type='06'  "
                        + "   and cc.is_delete='0' "
                        + "   and cc.county_code='" + countyCode + "'");
        System.out.println(sbf.toString());
        return sbf.toString();
    }


    public String getUserCommunity(Map map) {

        String countyCode = map.get("countyCode").toString();
        String pCode = map.get("pCode").toString();
        StringBuffer sbf = new StringBuffer();

        sbf.append(" select  dd.county_code, "
                + "         dd.code_type, "
                + "         dd.value, "
                + "         dd.name, "
                + "         dd.p_code_type "
                + "   from epm_ln.c_elec_addr_det dd "
                + "   where dd.code_type='05'  "
                + "   and dd.is_delete='0' "
                + "   and dd.county_code='" + countyCode + "'    "
                + "   and dd.p_code='" + pCode + "'");
        System.out.println(sbf.toString());
        return sbf.toString();
    }


    public String getUserSmall(Map map) {

        String countyCode = map.get("countyCode").toString();
        String pCode = map.get("pCode").toString();

        StringBuffer sbf = new StringBuffer();

        sbf.append(" select  dd.county_code, "
                + "         dd.code_type, "
                + "         dd.value, "
                + "         dd.name, "
                + "         dd.p_code_type "
                + "   from epm_ln.c_elec_addr_det dd "
                + "   where dd.code_type='07'  "
                + "   and dd.is_delete='0' "
                + "   and dd.county_code='" + countyCode + "'    "
                + "   and dd.p_code='" + pCode + "'    ");
        System.out.println(sbf.toString());
        return sbf.toString();
    }

}


