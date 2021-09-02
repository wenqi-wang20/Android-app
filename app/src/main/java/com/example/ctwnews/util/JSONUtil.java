package com.example.ctwnews.util;


import com.example.ctwnews.models.news.Msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//用来封装网页获取到的json
public class JSONUtil {
    private static final String TAG = "JSONUtil";


    //通过回调函数，异步获取数据
    public static void sendRequestWithOkhttp(String url, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    //解析数据并且返回新闻列表
    public static List<Msg> parseJSON(Response response) throws IOException{
        String responseData = response.body().string();
        List<Msg> newsArray = new ArrayList<>();

        try {
            JSONObject ob = new JSONObject(responseData);
            String pageSize = ob.getString("pageSize");
            String currentPage = ob.getString("currentPage");
            int total = ob.getInt("total");

            JSONArray array = ob.getJSONArray("data");

            //循环处理得到的新闻内容
            for(int i = 0; i < array.length(); i++){
                JSONObject newsob = (JSONObject) array.get(i);

                Msg msg = new Msg();
                //新闻id
                msg.setId(i);

                //新闻标题
                msg.setTitle(newsob.getString("title"));

                //新闻内容（html格式）
                msg.setContent(newsob.getString("content"));

                //新闻出版时间
                msg.setPublishTime(newsob.getString("publishTime"));

                //新闻出版方
                msg.setPublisher(newsob.getString("publisher"));

                //新闻图片链接(需处理)
                String imageurls = newsob.getString("image");
                List<String> imageurlArray = new ArrayList<>();
                if(imageurls == "[]"){
                    //说明新闻返回空链接，设置一张自己的图片
                    String imageDefaulturl = "https://images.cnblogs.com/cnblogs_com/blogs/669967/galleries/1942265/o_210902084958newsbackground.jpg";
                    imageurlArray.add(imageDefaulturl);
                    msg.setImageUrl(imageurlArray);
                }else {
                    //说明新闻有自己的图片
                    String imageurllist = imageurls.replace("[", "").replace("]", "");
                    String[] str = imageurllist.split(",");
                    for (String url : str) {
                        imageurlArray.add(url);
                    }
                    msg.setImageUrl(imageurlArray);
                }

                //新闻视频链接（需处理）
                String videourls = newsob.getString("video");
                List<String> videourlArray = new ArrayList<>();
                if(videourls == "[]"){
                    //说明新闻返回空链接，设置一张自己的图片
                    msg.setVideoUrl(videourlArray);
                }else {
                    //说明新闻有自己的图片
                    String videourllist = imageurls.replace("[", "").replace("]", "");
                    String[] str = videourllist.split(",");
                    for (String url : str) {
                        videourlArray.add(url);
                    }
                    msg.setVideoUrl(videourlArray);
                }

                newsArray.add(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsArray;
    }

}
