package com.i.lite.entity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 2018/1/12.
 */

public class NewsEntity {

    public ArrayList<NewsInfo> T1348647909107;

    public class NewsInfo {

        public String template;
        public String lmodify;
        public String source;
        public String postid;
        public String title;
        public String mtime;
        public int hasImg;
        public String topic_background;
        public String digest;
        public String boardid;
        public String alias;
        public int hasAD;
        public String imgsrc;
        public String ptime;
        public String daynum;
        public int hasHead;
        public int order;
        public int votecount;
        public boolean hasCover;
        public String docid;
        public String tname;
        public String url_3w;
        public int priority;
        public String url;
        public String ename;
        public int replyCount;
        public String ltitle;
        public boolean hasIcon;
        public String subtitle;
        public String cid;
        public ArrayList<AdsBean> ads;

        public  class AdsBean {

            public String subtitle;
            public String skipType;
            public String skipID;
            public String tag;
            public String title;
            public String imgsrc;
            public String url;

        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
