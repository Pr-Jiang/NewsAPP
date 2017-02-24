package com.example.jiangrui.pioneer.Model;

import java.util.List;

/**
 * Created by jiangrui on 2017/1/16.
 */

public class ZhiHu {

    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";
    /**
     * date : 20170116
     * stories :
     * [{"title":"去万米之上的高空看一眼，是它们的使命","ga_prefix":"011611","images":["http://pic1.zhimg.com/e90beee0709d9a46f8fb2707e111513c.jpg"],"multipic":true,"type":0,"id":9152409},
     * {"title":"书籍与字体的变化，是西方「图像装饰」的演变史","ga_prefix":"011610","images":["http://pic3.zhimg.com/8d1510b3c7f7d76cdcd7938fe514505a.jpg"],"multipic":true,"type":0,"id":9150747},
     * {"images":["http://pic2.zhimg.com/ad7099e1bfdb8e4413c739c016950a45.jpg"],"type":0,"id":9151440,"ga_prefix":"011609","title":"号称更健康的「A2 牛奶」和普通牛奶有什么区别？"},
     * {"images":["http://pic2.zhimg.com/d6bcb0c01f920a47e8ce3886e866ff71.jpg"],"type":0,"id":9151839,"ga_prefix":"011608","title":"经济总是起起落落，宏观经济学是怎么解释的？"},
     * {"images":["http://pic4.zhimg.com/5bf9b69b42f23d7e39d6c861de7dbf03.jpg"],"type":0,"id":9151622,"ga_prefix":"011607","title":"能杀死癌细胞的重水，毒性是从哪儿来的？"},
     * {"title":"从死亡到白骨会经历怎样的过程？","ga_prefix":"011607","images":["http://pic1.zhimg.com/f77968c12ea2148a07e4062f8fb7c530.jpg"],"multipic":true,"type":0,"id":9151499},
     * {"images":["http://pic1.zhimg.com/73180778687a6bdaa544febdfe1e1594.jpg"],"type":0,"id":9149523,"ga_prefix":"011607","title":"给了帐号还想要我的公司和学校信息，微博怒了，脉脉输了"},
     * {"images":["http://pic3.zhimg.com/41c813e8bf709e37371e0b973f89817e.jpg"],"type":0,"id":9151464,"ga_prefix":"011606","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic1.zhimg.com/432faa494ef334041aa1b70314e01654.jpg","type":0,"id":9151499,"ga_prefix":"011607","title":"从死亡到白骨会经历怎样的过程？"},{"image":"http://pic4.zhimg.com/0b531e9eae3e350228a9f186291bb4bf.jpg","type":0,"id":9149523,"ga_prefix":"011607","title":"给了帐号还想要我的公司和学校信息，微博怒了，脉脉输了"},{"image":"http://pic4.zhimg.com/3aa477c9092e05116ccfbdb856d660c3.jpg","type":0,"id":9149423,"ga_prefix":"011507","title":"为什么要报仇？因为真的爽啊"},{"image":"http://pic4.zhimg.com/51d43f84fad51af03fee35b5e246b7df.jpg","type":0,"id":9150019,"ga_prefix":"011515","title":"在你的专业里，有什么基础知识和普通人的认识不相符？"},{"image":"http://pic3.zhimg.com/74475247cad4f4685df692ae13999f7e.jpg","type":0,"id":9149455,"ga_prefix":"011507","title":"「赤道国家比较穷是因为紫外线太强」，别笑，我有证据"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * title : 去万米之上的高空看一眼，是它们的使命
         * ga_prefix : 011611
         * images : ["http://pic1.zhimg.com/e90beee0709d9a46f8fb2707e111513c.jpg"]
         * multipic : true
         * type : 0
         * id : 9152409
         */

        private String title;
        private String ga_prefix;
        private boolean multipic;
        private int type;
        private int id;
        private List<String> images;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : http://pic1.zhimg.com/432faa494ef334041aa1b70314e01654.jpg
         * type : 0
         * id : 9151499
         * ga_prefix : 011607
         * title : 从死亡到白骨会经历怎样的过程？
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
