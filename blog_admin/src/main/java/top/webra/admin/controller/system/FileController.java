package top.webra.admin.controller.system;


import com.qiniu.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.webra.constants.ResponseStateConstant;
import top.webra.admin.bean.CustomResponse;
import top.webra.pojo.FileHash;
import top.webra.pojo.Record;
import top.webra.pojo.Website;
import top.webra.service.impl.FileHashServiceImpl;
import top.webra.service.impl.RecordServiceImpl;
import top.webra.service.impl.WebsiteServiceImpl;
import top.webra.utils.PathUtil;

import java.io.File;
import java.io.IOException;

/**
 * 文件、图片上传
 * @author webra
 */
@Controller
@RequestMapping(value = "/system/file/")
public class FileController {

    @Autowired
    private CustomResponse customResponse;
    @Autowired
    private FileHashServiceImpl fileHashService;
    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private WebsiteServiceImpl websiteService;

//    private String imagePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()
//            .replaceFirst("file:/","")
//            .replaceFirst("blog-admin-1.0-SNAPSHOT.jar!/BOOT-INF/classes!", "img");

    @ResponseBody
    @PostMapping("/logo/upload")
    public CustomResponse uploadLogo(@RequestPart("file_data") MultipartFile file){
        // 获得静态资源路径
        File filePath = new File(PathUtil.imagePatg() + "/logo.png");
        try {
            file.transferTo(filePath);
            customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            customResponse.setMes("/gallery/img/logo.png");
            recordService.insertRecord(new Record("网站Logo","更新操作"));
        } catch (IOException e) {
            customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
        }
//        boolean delete = filePath.delete();
//        if (delete){
//            try {
//                file.transferTo(filePath);
//                customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
//                customResponse.setMes("/static/img/logo.png");
//                recordService.insertRecord(new Record("网站Logo","更新操作"));
//                websiteService.updWebsite(new Website("","/static/img/logo.png"));
//            } catch (IOException e) {
//                customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
//            }
//        }else {
//            customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
//        }

        return customResponse;
    }
    @ResponseBody
    @PostMapping("/fav/upload")
    public CustomResponse uploadFav(@RequestPart("file_data") MultipartFile file){
        // 获得静态资源路径
        File filePath = new File(PathUtil.imagePatg() + "/fav.ico");
        try {
            file.transferTo(filePath);
            customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
            customResponse.setMes("/gallery/img/fav.ico");
            recordService.insertRecord(new Record("网站Favicon","更新操作"));
        } catch (IOException e) {
            customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
        }

//        boolean delete = filePath.delete();
//        if (delete){
//            try {
//                file.transferTo(filePath);
//                customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
//                customResponse.setMes("/static/img/fav.ico");
//                recordService.insertRecord(new Record("网站Favicon","更新操作"));
//                websiteService.updWebsite(new Website("","/static/img/logo.png"));
//            } catch (IOException e) {
//                customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
//            }
//        }else {
//            customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
//        }

        return customResponse;
    }

    @ResponseBody
    @PostMapping("/image/upload")
    public CustomResponse uploadImage(@RequestPart("file_data") MultipartFile file,
                                      @RequestParam("fileId") String fileName){
        try {
            // 获取文件的后缀名
            String[] split = fileName.split("\\.");
            // 生成MD5 作为文件名
            String fileMd5 = Md5.md5(file.getBytes());
            // 检测该md5 是否存在
            FileHash fileHash1 = fileHashService.queFileHashByFileHash(fileMd5);
            // 如果不存在，则向静态资源路径添加该文件，并向数据库插入查image的记录
            if (fileHash1==null){
                fileName = fileMd5 + "." + split[split.length - 1];
                File filePath = new File(PathUtil.imagePatg()+fileName);
                file.transferTo(filePath);
                customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
                customResponse.setMes("/gallery/img/"+fileName);
                FileHash fileHash = new FileHash(1,fileName, fileMd5);
                fileHashService.insertFileHash(fileHash);
            }else {
                customResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
                customResponse.setMes("/gallery/img/"+fileHash1.getFileName());
            }
        } catch (IOException e) {
            customResponse.setCode(ResponseStateConstant.RESPONSE_FAILURE);
        }

        return customResponse;
    }

/** 七牛云上传实例
    @ResponseBody
    @PostMapping("/image/upload")
    public CostomResponse uploadImage(@RequestPart("file_data") MultipartFile file,
                                      @RequestParam("fileId") String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "ObcystkEw1OIezPb0QAJCGSmMIObXOXETEVtcS9T";
        String secretKey = "YIW69ZyF1u_1HI877BNDpx7_TusX-McXkM0ap-Cc";
        String bucket = "webra";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String[] split = fileName.split("/.");
        //        String key = UUID.randomUUID() + "." + split[split.length-1];
        String key = file.hashCode() + "." + split[split.length-1];

//        String key = null;
        try {
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(file.getBytes());
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                // 这里的key 为null  的话    文件以hash值为名
                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //                System.out.println(putRet.key);
                costomResponse.setCode(ResponseStateConstant.RESPONSE_SUCCESS);
                costomResponse.setMes("http://ql64gwkxm.hb-bkt.clouddn.com/"+putRet.key);
                //                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        return costomResponse;
    }
    **/




    @ResponseBody
    @PostMapping("/image/delete")
    public CustomResponse delImage(){
        return customResponse;
    }


    @ResponseBody
    @PostMapping("/excel/upload")
    public CustomResponse uploadExcel(){

        return customResponse;
    }

}
