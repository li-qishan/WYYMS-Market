package com.buf.common.utils;

import com.buf.common.config.Global;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mawenguang on 2019/4/17.
 */
public class UploadUtils
{

    /**
     * @return fileName, fileType, Url
     * @Author mawenguang
     * @Description //TODO 上传文件
     * @Date 13:53 2019/5/27
     * @Param [request, url]
     **/
    public static List<Map<String, Object>> uploadFile(HttpServletRequest request, String url) throws Exception
    {
        List<Map<String, Object>> list = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request))
        {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();
            while (iterator.hasNext())
            {

                Map<String, Object> map = new HashMap<>();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iterator.next());
                if (file != null)
                {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "")
                    {
                        // 文件类型
                        String fileType = myFileName.substring(myFileName.lastIndexOf("."));
                        // String tempName="demo"+fileTyps;
//                        String tempName = myFileName.substring(0, myFileName.lastIndexOf(".")) + "-" +UUID.randomUUID().toString() + fileType;
                        String tempName = UUID.randomUUID().toString() + fileType;
                        File path = new File(uploadUrl());

                        // 创建文件夹
                        File fileFolder = new File(url);
                        if (!fileFolder.exists() && !fileFolder.isDirectory())
                        {
                            fileFolder.mkdirs();
                        }
                        File uploadFile = new File(fileFolder.getPath() + "/" + tempName);
                        file.transferTo(uploadFile);
//                        myFileName =  tempName;
                        map.put("fileName", tempName);
                        map.put("fileType", fileType);
                        map.put("url", UploadUtils.uploadUrl() + tempName);
                        list.add(map);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 得年月日的文件夹名称
     *
     * @return
     */
    public static String getCurrentFilderName() throws Exception
    {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "" + (now.get(Calendar.MONTH) + 1) + "" + now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 创建文件夹
     *
     * @param filderName
     */
    public static void createFilder(String filderName) throws Exception
    {
        File file = new File(filderName);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory())
        {
            file.mkdirs();
        }
    }

    /**
     * 文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String extFile(String fileName) throws Exception
    {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 当前日期当文件夹名
     *
     * @return
     */
    public static String folderName() throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        return str;
    }

    // 针对不同系统返回不同路径
    public static String uploadUrl()
    {
        String url = "";
        Properties props = System.getProperties();
        // windows系统
        if (props.getProperty("os.name").contains("Windows"))
        {
            url = Global.getProperty("web.upload-path");
        }
        else
        {
            url = Global.getProperty("web.upload-path-linux");
        }
        return url;
    }

    public static void main(String args[]) throws Exception
    {


        Properties props = System.getProperties();
        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
        System.out.println(uploadUrl());
        System.out.println(folderName());
    }
}
