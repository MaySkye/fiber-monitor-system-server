package com.rcd.fiber.domain.entity;


import java.io.File;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bucket")
public class ServiceFileInfo {
    @Id
    private ObjectId _id;
    private String filename;
    private long length;
    private long chunkSize;
    private Date uploadDate;
    private String md5;
    private org.bson.Document metadata;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public org.bson.Document getMetadata() {
        return metadata;
    }

    public void setMetadata(org.bson.Document metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "ServiceFileInfo{" +
            "_id=" + _id +
            ", filename='" + filename + '\'' +
            ", length=" + length +
            ", chunkSize=" + chunkSize +
            ", uploadDate=" + uploadDate +
            ", md5='" + md5 + '\'' +
            ", metadata=" + metadata +
            '}';
    }
}

