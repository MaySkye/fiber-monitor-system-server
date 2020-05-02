package com.rcd.fiber.web.rest;

import com.rcd.fiber.domain.entity.TelemetryImage;
import com.rcd.fiber.service.TelemetryImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HUHU
 * @Date: 2019/7/16 11:43
 */
@RestController
@RequestMapping("/telemetry_image")
@CrossOrigin // 防止跨域
public class TelemetryImageResource {

    private final Logger log = LoggerFactory.getLogger(TelemetryImageResource.class);
    private final TelemetryImageService service;

    public TelemetryImageResource(TelemetryImageService service) {
        this.service = service;
    }

    @GetMapping(value = "/siteimage")
    public ResponseEntity<Map<String, java.io.Serializable>> getBySiteName(String siteName, HttpServletRequest request){
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        if (siteName == null||siteName.isEmpty())
        {
            map.put("status", 0);
            map.put("msg", "参数错误");
            return  ResponseEntity.ok(map);
        }
        // 获取数据
        List<TelemetryImage> list = service.findBySiteName(siteName);
        if (list.size() == 0) {
            map.put("status", 2);
            map.put("msg", "无此站点，请使用默认站点");
            return  ResponseEntity.ok(map);
        }
        TelemetryImage telemetryImage = list.get(0);
        //转换时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sd = df.format(telemetryImage.getTimestamp()); // 时间戳转换日期
        // 文件地址
        String filePath = null;
        // 写入图片
        BASE64Decoder decoder = new BASE64Decoder();

//        try {
//            // 存放上传图片的文件夹
//            File fileDir = UploadUtils.getImgDirFile();
//            // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
//            System.out.println(fileDir.getAbsolutePath());
//            // 构建真实的文件路径
//            File newFile = new File(fileDir.getAbsolutePath() + File.separator + telemetryImage.getSiteName() + ".png");
//            System.out.println(newFile.getAbsolutePath());
//            // 复制地址
//            FileOutputStream write = new FileOutputStream(newFile);
//            byte[] decoderBytes = decoder.decodeBuffer(telemetryImage.getStateImage());
//            write.write(decoderBytes);
//            write.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
      //  String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/static/siteimgs/" + telemetryImage.getSiteName() + ".png";
        map.put("status", 1);
        map.put("msg", "请求成功");
        // map.put("imgurl", url);
        return ResponseEntity.ok(map);
    }

    // 以流的方式向前端传递数据
    @GetMapping("/getimage")
    public void getImage(String siteName, HttpServletResponse response) {

        //设置ContentType的类型
        String type = "image/png";
        FileInputStream inputStream = null;
        OutputStream stream = null;

        try {
            String imageString = "";
            // 获取数据
            List<TelemetryImage> list = service.findBySiteName(siteName);
            if (list.isEmpty()) {
               // imageString = noPic;
            } else {
                imageString = list.get(0).getStateImage();
            }
            // 写入图片
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] data = decoder.decodeBuffer(imageString);
            //setContentType("text/plain; charset=utf-8"); 文本
            response.reset();
            response.setContentType(type);
            stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // final String noPic = "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";
}
