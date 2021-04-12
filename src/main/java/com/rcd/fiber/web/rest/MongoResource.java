package com.rcd.fiber.web.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.rcd.fiber.annotation.CheckPermission;
import com.rcd.fiber.domain.entity.ServiceFileInfo;
import com.rcd.fiber.service.MongoService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

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

    //王伟：获取service（latest）文件信息
    @RequestMapping("/getCurrentMxeFileInfo")
    @ResponseBody
    @Timed
    public ServiceFileInfo getCurrentMxeFileInfo(@RequestParam("site_name") String site_name,
                                                     @RequestParam("site_level") String site_level) {
        return service.getCurrentMxeFileInfo(site_name, site_level);
    }

    //王伟：下载service（latest）文件
    @RequestMapping("/getCurrentMxeFile")
    @ResponseBody
    @CheckPermission(value = false, object = "组态图", action = "查看")
    @Timed
    public void getCurrentMxeFile(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("site_name") String site_name,
                                      @RequestParam("site_level") String site_level) {
        System.out.println("site_name:"+site_name+"  site_level:"+site_level);
        service.getLatestServiceFile(request, response, site_name, site_level);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> upload(MultipartFile multipartFile,
                                          HttpServletRequest request) {
        Document metadata = new Document()
            .append("site_name", request.getParameter("site_name"))
            .append("site_level", request.getParameter("site_level"))
            .append("latest", request.getParameter("latest"))
            .append("filename",request.getParameter("filename"));
        return service.uploadServiceFile(multipartFile, metadata);
    }


    //赵艺：查询所有service文件信息
       /*
    * {
    file_name:
    site_name:
    site_level:
    upload_time:
    id:
    },*/
    @RequestMapping("/getAllServiceInfo")
    @ResponseBody
    public String getAllServiceInfo( ) throws ParseException {
        List<ServiceFileInfo> infos=service.getAllServiceInfo();
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        for(int i = 0 ; i < infos.size() ; i++) {
            System.out.println(infos.get(i));
            System.out.println("file_name:"+infos.get(i).getFilename());
            System.out.println("site_name:"+infos.get(i).getMetadata().getString("site_name"));
            System.out.println("site_level:"+infos.get(i).getMetadata().getString("site_level"));
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);
            System.out.println("upload_time:"+df.parse(infos.get(i).getUploadDate().toString()));
            System.out.println("_id:"+infos.get(i).get_id());

            jsonObject = new JSONObject();
            jsonObject.put("file_name", infos.get(i).getFilename());
            jsonObject.put("site_name", infos.get(i).getMetadata().getString("site_name"));
            jsonObject.put("site_level", infos.get(i).getMetadata().getString("site_level"));
            jsonObject.put("upload_time", infos.get(i).getUploadDate().toString());
            jsonObject.put("md5", infos.get(i).getMd5());
            jsonObject.put("id", infos.get(i).get_id());
            array.add(jsonObject);
        }
        System.out.println("array.toString():"+array.toString());
        return array.toString();
    }


    //赵艺：下载组态图文件
    @RequestMapping("/getFileByMD5")
    @ResponseBody
    @Timed
    public void getFileByMD5(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("md5") String md5) {
        System.out.println("get-md5: "+md5);
        service.getFileInfo(request, response, md5);
    }

    //赵艺：删除
    @GetMapping("/delete/{md5}")
    @Timed
    public void deleteSite(@PathVariable(value = "md5") String md5) {
        System.out.println("md5: "+md5);
        service.deleteFile(md5);
    }
}
