package gamemei.qiyun.com.gamemei.bean;

import java.util.List;

/**
 * 游戏列表的Bean
 *
 * @author hfcui
 */
public class Top_HotTopicBean {

    public List<HotSpot> hotspots;

    public class HotSpot {

        // 热点标题
        public String hotspots_title;
        // 游戏缩略图地址
        public String hotspots_url;
        // 热点内容
        public String hotspots_desc;

        public String getHotspots_name() {
            return hotspots_title;
        }

        public void setHotspots_name(String hotspots_name) {
            this.hotspots_title = hotspots_name;
        }


        public String getHotspots_url() {
            return hotspots_url;
        }

        public void setHotspots_url(String hotspots_url) {
            this.hotspots_url = hotspots_url;
        }

        public String getHotspots_desc() {
            return hotspots_desc;
        }

        public void setHotspots_desc(String hotspots_desc) {
            this.hotspots_desc = hotspots_desc;
        }

        @Override
        public String toString() {
            return "HotSpot{" +
                    "hotspots_name='" + hotspots_title + '\'' +
                    ", hotspots_url='" + hotspots_url + '\'' +
                    ", hotspots_desc='" + hotspots_desc + '\'' +
                    '}';
        }
    }
}
