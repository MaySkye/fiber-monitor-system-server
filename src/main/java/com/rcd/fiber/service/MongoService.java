package com.rcd.fiber.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.rcd.fiber.domain.entity.ServiceFileInfo;
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

    //王伟：查询站点使用的service文件信息
    public ServiceFileInfo getCurrentMxeFileInfo(String site_name, String site_level) {
        Query query = new Query(Criteria
            .where("metadata.site_name").is(site_name)
            .and("metadata.site_level").is(site_level)
        );
        return mongoRepository.getCurrentMxeFileInfo(query);
    }

    //王伟：下载站点使用的service文件
    public void getLatestServiceFile(HttpServletRequest request, HttpServletResponse response, String site_name, String site_level) {
        //查询GridFSFile文件信息
        Query query = new Query(Criteria
            .where("metadata.site_name").is(site_name)
            .and("metadata.site_level").is(site_level));
        query.with(Sort.by(Sort.Order.desc("uploadDate")));
        GridFSFile gsFile = mongoRepository.getLatestServiceFile(query);
        System.out.println("查询完成");
        //返回结果
        HashMap<String,String> res = new HashMap<>();
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
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
    }

    //王伟：上传service文件
    public HashMap<String, String> uploadServiceFile(MultipartFile multipartFile, Document metadata) {
        HashMap<String, String> res = new HashMap<>();
        try {
            String fixedFilename = metadata.getString("filename");
            metadata.remove("filename");
            InputStream inputStream = multipartFile.getInputStream();
            ObjectId objectId = mongoRepository.uploadServiceFile(inputStream, fixedFilename, metadata);
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

    //赵艺：查询所有service文件信息
    public List<ServiceFileInfo> getAllServiceInfo(){
        return  mongoRepository.getAllServiceInfo();
    }

    //赵艺：查询某一文件信息
    public void getFileInfo(HttpServletRequest request, HttpServletResponse response,String md5) {
        Query query = new Query(Criteria
            .where("md5").is(md5)
        );
        GridFSFile gsFile = mongoRepository.getFileInfo(query);
        //返回结果
        HashMap<String,String> res = new HashMap<>();
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
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
    }

    //赵艺：删除某一service文件
    public void deleteFile(String md5){
        Query query = new Query(Criteria
            .where("md5").is(md5)
        );
        mongoRepository.delete(query);
    }

}
