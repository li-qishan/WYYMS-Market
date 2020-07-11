package com.buf.common.utils;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

/**
 * Created by mawenguang on 2019/4/4.
 */
public class XmlToJsonUtils

{
    /**
     * 将xml字符串<STRONG>转换</STRONG>为JSON字符串
     *
     * @param xmlString xml字符串
     * @return JSON<STRONG>对象</STRONG>
     */
    public static String xml2json(String xmlString)
    {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString(1);
    }


    public static void main(String arg[]){
        String str = "<data>\n" +
                "\t<sysUserName>xjy_jz</sysUserName>\n" +
                "\t<userName>谢敬一</userName>\n" +
                "\t<orgNo>2140314</orgNo>\n" +
                "\t<deptNo>0300000043</deptNo>\n" +
                "\t<orgName>国网北镇市供电公司</orgName>\n" +
                "\t<deptName>计量班</deptName>\n" +
                "\t<bureauNo>21403</bureauNo>\n" +
                "\t<bureauName>国网锦州供电公司</bureauName>\n" +
                "\t<sysAdmin>false</sysAdmin>\n" +
                "\t<provinceCode>210000</provinceCode>\n" +
                "\t<provinceName>辽宁省</provinceName>\n" +
                "\t<cityCode>210700</cityCode>\n" +
                "\t<cityName>锦州市</cityName>\n" +
                "\t<countyCode>210782</countyCode>\n" +
                "\t<countyName>北镇市</countyName>\n" +
                "\t<result>1</result>\n" +
                "\t<returnCode>1000</returnCode>\n" +
                "\t<returnMSG></returnMSG>\n" +
                "</data>";

        System.out.print(xml2json(str));
    }
}
