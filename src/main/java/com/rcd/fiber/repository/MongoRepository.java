package com.rcd.fiber.repository;


import com.mongodb.client.gridfs.model.GridFSFile;
import com.rcd.fiber.domain.entity.MxeFileInfo;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class MongoRepository {
    //王伟：拿取注入的Mongo相关Bean
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    GridFsTemplate gridFsTemplate;

    //王伟：从配置文件获取bucketName
    @Value("${spring.data.mongodb.bucket-name}")
    private String bucketName;


    //王伟：查询站点使用的service文件信息
    public MxeFileInfo getCurrentMxeFileInfo(Query query) {
        MxeFileInfo info = mongoTemplate.findOne(query, MxeFileInfo.class, bucketName + ".files");
        return info;
    }

    //赵艺：查询所有service文件信息
    public List<MxeFileInfo> getAllServiceInfo() {
        List<MxeFileInfo> infos = mongoTemplate.findAll(MxeFileInfo.class, bucketName + ".files");
        return infos;
    }

    //赵艺：查找某一文件
    public GridFSFile getFileInfo(Query query) {
        GridFSFile gsFile = gridFsTemplate.findOne(query);
        return gsFile;
    }

    //赵艺： 删除某一文件
    public void deleteFileByCondition(Query query) {
        gridFsTemplate.delete(query);
    }

    //王伟：下载service（latest）文件时查询信息
    public GridFSFile getLatestServiceFile(Query query) {
        GridFSFile gsFile = gridFsTemplate.findOne(query);
        return gsFile;
    }

    //王伟：上传service文件
    public ObjectId uploadMxeFile(InputStream inputStream, String filename, Document metadata) {
        ObjectId objectId = gridFsTemplate.store(inputStream, filename, metadata);
        return objectId;
    }
}
