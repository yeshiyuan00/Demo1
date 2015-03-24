package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ysy on 2015/3/24.
 */
public class DownUtil {
    //定义下载资源的路径
    private String path;
    //指定所下载的文件的保存位置
    private String targetFile;
    //定义需要使用多少线程来下载资源
    private int threadNum;
    //定义下载的线程对象
    private DownThread[] threads;
    //定义下载的文件的总大小
    private int fileSize;

    public DownUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        //初始化threads数组
        threads = new DownThread[threadNum];
    }

    public void download() throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif,image/jpeg,image/pjpeg,image/pjpeg," +
                "application/x-shockwave-flash,application/xaml+xml," +
                "application/vnd.ms-xpsdocument,application/x-ms-xbap," +
                "application/x-ms-application,application/vnd.ms-excel," +
                "application/vnd.ms-powerpoint,application/msword,*/*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        //得到文件大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartSize = fileSize / threadNum + 1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        //设置本地文件的大小
        file.setLength(fileSize);
        file.close();
        for (int i = 0; i < threadNum; i++) {
            //计算每条线程的下载开始位置
            int startPos = i * currentPartSize;
            //让每个线程使用一个RandomAccessFile进行下载
            RandomAccessFile currentPart = new RandomAccessFile(targetFile, "rw");
            //定义该线程的下载位置
            currentPart.seek(startPos);
            //创建下载线程
            threads[i] = new DownThread(startPos, currentPartSize, currentPart);
            //启动下载线程
            threads[i].start();
        }

    }

    //获取下载的完成百分比
    public double getCompleteRate() {
        //统计多条线程已经下载的总大小
        int sumSize = 0;
        for (int i = 0; i < threadNum; i++) {
            sumSize += threads[i].length;
        }

        return sumSize * 1.0 / fileSize;
    }

    private class DownThread extends Thread {
        //定义该线程已经下载的字节数
        public int length;
        //当前线程的下载位置
        private int startPos;
        //定义当前进程负责下载文件的大小
        private int currentPartSize;
        //当前进程需要下载的文件块
        private RandomAccessFile currentpart;


        public DownThread(int startPos, int currentPartSize, RandomAccessFile currentPart) {
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentpart = currentPart;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "image/gif,image/jpeg,image/pjpeg,image/pjpeg," +
                        "application/x-shockwave-flash,application/xaml+xml," +
                        "application/vnd.ms-xpsdocument,application/x-ms-xbap," +
                        "application/x-ms-application,application/vnd.ms-excel," +
                        "application/vnd.ms-powerpoint,application/msword,*/*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Connection", "Keep-Alive");
                InputStream inputStream = conn.getInputStream();
                //跳过startPos个字节，表面该线程只下载自己负责的那部分文件
                inputStream.skip(this.startPos);
                byte[] buff = new byte[1024];
                int hasRead = 0;
                //读取网络数据，并写入本地文件
                while (length < currentPartSize && (hasRead = inputStream.read(buff)) > 0) {
                    currentpart.write(buff, 0, hasRead);
                    //累计该线程下载的总大小
                    length += hasRead;
                }
                currentpart.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
