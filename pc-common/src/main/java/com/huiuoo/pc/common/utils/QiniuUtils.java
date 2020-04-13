package com.huiuoo.pc.common.utils;

import com.huiuoo.pc.common.error.BusinessException;
import com.huiuoo.pc.common.error.EmBusinessError;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Component
public class QiniuUtils {

    // 设置需要操作的账号的AK和SK
    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    // 要上传的空间
    private static String BUCKET;
    //外链
    private static String DOMAIN;
    private static String style = "自定义的图片样式";

    @Value("${qny.ACCESS_KEY}")
    public void setAccessKey(String accessKey) {
        ACCESS_KEY = accessKey;
    }

    @Value("${qny.SECRET_KEY}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Value("${qny.bucket}")
    public void setBucket(String BUCKET) {
        QiniuUtils.BUCKET = BUCKET;
    }

    @Value("${qny.DOMAIN}")
    public void setDOMAIN(String DOMAIN) {
        QiniuUtils.DOMAIN = DOMAIN;
    }

    /**
     * 华东机房,配置自己空间所在的区域
     */
    @Bean
    public static Configuration qiniuConfig() {
        return new Configuration(Zone.zone0());
    }

    /**
     * @param file,key
     */
    public static void uploadImage(MultipartFile file, String key) throws QiniuException, BusinessException {

        if (StringUtils.isBlank(key)) {
            throw new BusinessException(EmBusinessError.REQUEST_PARAM_ERROR, "图片名字不能不指定");
        }

        //MultipartFile 转 字节数组
        byte[] imgBytes = new byte[0];
        try {
            imgBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 密钥
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //图片的外链地址
        UploadManager uploadManager = new UploadManager(QiniuUtils.qiniuConfig());
        //...生成上传凭证，然后准备上传
        String upToken = auth.uploadToken(BUCKET);
        //获取文件名
        //  String fileName = file.getOriginalFilename();
        //上传文件
        uploadManager.put(imgBytes, key, upToken);
        // 解析返回结果
       // DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    }



    /**
     * 获取下载文件路径，即：donwloadUrl
     *
     * @return
     */
    public String getDownloadUrl(String targetUrl) {
        // 密钥
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String downloadUrl = auth.privateDownloadUrl(DOMAIN + targetUrl);
        return downloadUrl;
    }


    /**
     * 文件下载
     *
     * @param targetUrl
     */
    public void download(String targetUrl, String imageName) {
        //获取downloadUrl
        String downloadUrl = getDownloadUrl(targetUrl);
        String filePath = "D:\\huiuoo\\image";
        download(downloadUrl, filePath, imageName);
    }


    /**
     * 通过发送http get 请求获取文件资源
     *
     * @param url
     * @param filepath
     * @return
     */
    public static void download(String url, String filepath, String imageName) {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data = readInputStream(is);
                //判断文件夹是否存在，不存在则创建
                File file = new File(filepath);
                if (!file.exists() && !file.isDirectory()) {
                    log.info("=====下载图片=====");
                    log.info("===文件夹不存在===创建===={}", file);
                    file.mkdir();
                }
                File imgFile = new File(filepath + imageName);
                FileOutputStream fops = new FileOutputStream(imgFile);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // System.out.println("Unexpected code " + resp);
        }
    }

    /**
     * 读取字节输入流内容
     *
     * @param is
     * @return
     */
    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    public static void delImage(String key) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //删除
        bucketManager.delete(BUCKET, key);
    }

    /**
     * 描述：修改图片名称
     */
    public static void rename(String oldFileKey, String newFileKey) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //重命名
        bucketManager.rename(BUCKET, oldFileKey, newFileKey);
    }

    /**
     * 描述： 将字符串文件转为字节数组上传
     */
    public static void upLoadBytes(String str, String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = str.getBytes("utf-8");
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(BUCKET);
            try {
                uploadManager.put(uploadBytes, key, upToken);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
            ex.printStackTrace();
        }
    }
}