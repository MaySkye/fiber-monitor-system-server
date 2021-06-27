package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONArray;
import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.annotation.CheckPermission;
import com.rcd.fiber.domain.entity.MxeFileInfo;
import com.rcd.fiber.service.MongoService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;

/**
 * @Author:zhoayi
 * @Description:
 * @Data: Created in 23:35 2020/5/1
 * @Modify By:
 */

@RestController
@RequestMapping("/mongo")
@CrossOrigin(origins = "*") // 跨域
public class MongoResource {

    private final Logger logger = LoggerFactory.getLogger(MongoResource.class);
    private final MongoService service;

    public MongoResource(MongoService service) {
        this.service = service;
    }


    /**
     * 根据站点名、站点等级获取最新的组态图文件
     *
     * @param request
     * @param response
     * @param site_name
     */
    @RequestMapping("/getMxeFileBySiteName")
    @ResponseBody
    @CheckPermission(value = true, object = "", action = "查看", checkDepartment = true)
    @Timed
    public void getMxeFileBySiteName(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam("site_name") String site_name) {
        service.getMxeFileBySiteName(request, response, site_name);
    }

    /**
     * 上传组态图文件
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @CheckPermission(value = true, object = "", action = "编辑", checkDepartment = true)
    @ResponseBody
    public HashMap<String, String> upload(MultipartFile multipartFile,
                                          HttpServletRequest request) {
        Document metadata = new Document()
            .append("site_name", request.getParameter("site_name"))
            .append("site_level", request.getParameter("site_level"))
            .append("department", request.getParameter("department"))
            .append("filename", request.getParameter("filename"));
        return service.uploadMxeFile(multipartFile, metadata);
    }

    /**
     * 查询所有组态图文件信息
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getAllServiceInfo")
    @ResponseBody
    public JSONArray getAllServiceInfo() throws ParseException {
        return service.getAllServiceInfo();
    }


    /**
     * 根据objectId下载组态图文件
     *
     * @param request
     * @param response
     * @param objectId
     */
    @RequestMapping("/getMxeFileByObjectId")
    @ResponseBody
    @CheckPermission(value = true, object = "", action = "查看", checkDepartment = true)
    @Timed
    public void getMxeFileByObjectId(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam String objectId) {
        service.getMxeFileByObjectId(request, response, objectId);
    }


    /**
     * 根据objectId删除文件
     * @param objectId
     */
    @CheckPermission(value = true, object = "", action = "编辑", checkDepartment = true)
    @GetMapping("/deleteFileByObjectId")
    @Timed
    public void deleteFileByObjectId(@RequestParam String objectId) {
        service.deleteFileByObjectId(objectId);
    }
}
