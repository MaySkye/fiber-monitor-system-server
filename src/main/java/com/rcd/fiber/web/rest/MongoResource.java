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
     * 根据站点名、站点等级获取文件的元数据
     *
     * @param site_name
     * @param site_level
     * @return
     */
    @RequestMapping("/getMxeFileInfoBySiteNameAndLevel")
    @ResponseBody
    @Timed
    public MxeFileInfo getMxeFileInfoBySiteNameAndLevel(@RequestParam("site_name") String site_name,
                                                        @RequestParam("site_level") String site_level) {
        return service.getMxeFileInfoBySiteNameAndLevel(site_name, site_level);
    }

    /**
     * 根据站点名、站点等级获取最新的组态图文件
     *
     * @param request
     * @param response
     * @param site_name
     * @param site_level
     */
    @RequestMapping("/getMxeFileBySiteNameAndLevel")
    @ResponseBody
    @CheckPermission(value = true, object = "", action = "查看", checkDepartment = true)
    @Timed
    public void getMxeFileBySiteNameAndLevel(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam("site_name") String site_name,
                                             @RequestParam("site_level") String site_level) {
        System.out.println("site_name:" + site_name + "  site_level:" + site_level);
        service.getMxeFileBySiteNameAndLevel(request, response, site_name, site_level);
    }

    /**
     * 上传组态图文件
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @CheckPermission(value = false, object = "", action = "上传", checkDepartment = true)
    @ResponseBody
    public HashMap<String, String> upload(MultipartFile multipartFile,
                                          HttpServletRequest request) {
        Document metadata = new Document()
            .append("site_name", request.getParameter("site_name"))
            .append("site_level", request.getParameter("site_level"))
            .append("department", request.getParameter("department"))
            .append("filename", request.getParameter("filename"));
        return service.uploadServiceFile(multipartFile, metadata);
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
     * 根据md5下载组态图文件
     *
     * @param request
     * @param response
     * @param md5
     */
    @RequestMapping("/getMxeFileByMd5")
    @ResponseBody
    @CheckPermission(value = true, object = "", action = "查看", checkDepartment = true)
    @Timed
    public void getMxeFileByMd5(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("md5") String md5) {
        System.out.println("get-md5: " + md5);
        service.getMxeFileByMd5(request, response, md5);
    }

    /**
     * 根据md5删除组态图文件
     *
     * @param md5
     */
    @GetMapping("/delete/{md5}")
    @Timed
    public void deleteSite(@PathVariable(value = "md5") String md5) {
        System.out.println("md5: " + md5);
        service.deleteFile(md5);
    }
}
