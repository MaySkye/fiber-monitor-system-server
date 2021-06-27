package com.rcd.fiber.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.rcd.fiber.domain.entity.MxeFileInfo;
import com.rcd.fiber.repository.MongoRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 23:31 2020/5/1
 * @Modify By:
 */
@Service
@Transactional
public class MongoService {

    private final Logger log = LoggerFactory.getLogger(MongoService.class);
    //王伟：拿取注入的mongoDao
    @Autowired
    private MongoRepository mongoRepository;
    //王伟：拿取注入的Mongo相关Bean
    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 根据站点名、站点等级获取文件的元数据
     *
     * @param siteName
     * @return
     */
    public MxeFileInfo getMxeFileInfoBySite(String siteName) {
        Query query = new Query(Criteria
            .where("metadata.site_name").is(siteName)
        );
        query.with(Sort.by(Sort.Order.desc("uploadDate")));
        return mongoRepository.getCurrentMxeFileInfo(query);
    }


    /**
     * 根据md5获取文件元数据
     *
     * @param objectId
     * @return
     */
    public MxeFileInfo getMxeFileInfoByObjectId(String objectId) {
        Query query = new Query(Criteria.
            where("_id").is(objectId));
        return mongoRepository.getCurrentMxeFileInfo(query);
    }

    //王伟：下载站点使用的service文件
    public void getMxeFileBySiteName(HttpServletRequest request, HttpServletResponse response, String site_name) {
        //查询GridFSFile文件信息
        Query query = new Query(Criteria
            .where("metadata.site_name").is(site_name)
        );
        query.with(Sort.by(Sort.Order.desc("uploadDate")));
        GridFSFile gsFile = mongoRepository.getLatestServiceFile(query);
        System.out.println("查询完成");
        //返回结果
        HashMap<String, String> res = new HashMap<>();
        try {
            System.out.println("开始try");
            //获取IO流
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gsFile.getObjectId());
            OutputStream outputStream = response.getOutputStream();
            //设置编码，防止中文乱码
            response.setCharacterEncoding("utf-8");
            // 设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(gsFile.getFilename(), "UTF-8"));

            byte[] buffer = new byte[2048];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区当中
            while ((len = downloadStream.read(buffer)) > 0) {
                // 输出缓冲区的内容到浏览器，实现文件下载
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * 上传组态图文件
     *
     * @param multipartFile
     * @param metadata
     * @return
     */
    public HashMap<String, String> uploadMxeFile(MultipartFile multipartFile, Document metadata) {
        HashMap<String, String> res = new HashMap<>();
        try {
            String fixedFilename = metadata.getString("filename");
            metadata.remove("filename");
            InputStream inputStream = multipartFile.getInputStream();
            ObjectId objectId = mongoRepository.uploadMxeFile(inputStream, fixedFilename, metadata);
            //System.out.println("multipartFile.getOriginalFilename():"+multipartFile.getOriginalFilename());
            res.put("objectId", objectId.toString());
            res.put("type", "success");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.put("type", "error");
            return res;
        }
    }

    /**
     * 查询所有mxe文件信息
     *
     * @return
     */
    public JSONArray getAllServiceInfo() {
        List<MxeFileInfo> allMxeFilesInfo = mongoRepository.getAllServiceInfo();
        JSONArray array = new JSONArray();
        for (int i = 0; i < allMxeFilesInfo.size(); i++) {
            MxeFileInfo mxeFileInfo = allMxeFilesInfo.get(i);
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file_name", mxeFileInfo.getFilename());
            jsonObject.put("site_name", mxeFileInfo.getMetadata().getString("site_name"));
            jsonObject.put("site_level", mxeFileInfo.getMetadata().getString("site_level"));
            jsonObject.put("upload_time", mxeFileInfo.getUploadDate().toString());
            jsonObject.put("department", mxeFileInfo.getMetadata().getString("department"));
            jsonObject.put("md5", mxeFileInfo.getMd5());
            jsonObject.put("_id", mxeFileInfo.get_id().toString());
            array.add(jsonObject);
        }
        return array;
    }

    /**
     * 根据objectId下载组态图文件
     *
     * @param request
     * @param response
     * @param objectId
     */
    public void getMxeFileByObjectId(HttpServletRequest request, HttpServletResponse response, String objectId) {
        Query query = new Query(Criteria
            .where("_id").is(objectId)
        );
        GridFSFile gsFile = mongoRepository.getFileInfo(query);
        //返回结果
        HashMap<String, String> res = new HashMap<>();
        try {
            //获取IO流
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gsFile.getObjectId());
            OutputStream outputStream = response.getOutputStream();
            //设置编码，防止中文乱码
            response.setCharacterEncoding("utf-8");
            // 设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(gsFile.getFilename(), "UTF-8"));
            byte[] buffer = new byte[2048];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区当中
            while ((len = downloadStream.read(buffer)) > 0) {
                // 输出缓冲区的内容到浏览器，实现文件下载
                outputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * 根据objectId删除文件
     *
     * @param objectId
     */
    public void deleteFileByObjectId(String objectId) {
        Query query = new Query(Criteria.where("_id").is(objectId));
        mongoRepository.deleteFileByCondition(query);
    }
}
