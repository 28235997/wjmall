package com.zb.wjmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WjmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {

        String path = WjmallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();

        ClientGlobal.init(path);

        TrackerClient trackerClient = new TrackerClient();

        TrackerServer trackerServer = trackerClient.getConnection();

        StorageClient storageClient = new StorageClient(trackerServer, null);

        String[] gifs = storageClient.upload_file("F:/333333333.jpg", "jpg", null);

        String url = "http://192.168.0.105";
        for (String gif :
         gifs) {
            url = url + "/" + gif;
        }
        System.out.println(url);
    }


}






















