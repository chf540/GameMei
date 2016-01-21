package gamemei.qiyun.com.gamemei.bean;

import java.util.List;

/**
 * 游戏列表的Bean
 *
 * @author hfcui
 */
public class InformationBean {

    public List<News> news;

    public class News {
        //用户头像
        public String user_icon;
        //用户名字
        public String user_name;
        // 标题
        public String news_title;
        // 内容
        public String news_content;

        public String getUser_icon() {
            return user_icon;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_content() {
            return news_content;
        }

        public void setNews_content(String news_content) {
            this.news_content = news_content;
        }

        @Override
        public String toString() {
            return "News{" +
                    "user_icon='" + user_icon + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", news_title='" + news_title + '\'' +
                    ", news_content='" + news_content + '\'' +
                    '}';
        }
    }
}
