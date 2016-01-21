package gamemei.qiyun.com.gamemei.bean;

import java.util.List;

/**
 * 游戏列表的Bean
 * 
 * @author hfcui
 */
public class PlayGameInfoBean {

	public List<Games> games;

	public class Games {

		// 游戏名称
		public String game_name;
		// 游戏缩略图地址
		public String game_image_url;
		// 下载地址
		public String game_download_url;
		// 游戏描述
		public String game_desc;

		public String getGame_name() {
			return game_name;
		}

		public void setGame_name(String game_name) {
			this.game_name = game_name;
		}

		public String getGame_image_url() {
			return game_image_url;
		}

		public void setGame_image_url(String game_image_url) {
			this.game_image_url = game_image_url;
		}

		public String getGame_download_url() {
			return game_download_url;
		}

		public void setGame_download_url(String game_download_url) {
			this.game_download_url = game_download_url;
		}

		public String getGame_desc() {
			return game_desc;
		}

		public void setGame_desc(String game_desc) {
			this.game_desc = game_desc;
		}

		@Override
		public String toString() {
			return "Games [game_name=" + game_name + ", game_image_url="
					+ game_image_url + ", game_download_url="
					+ game_download_url + ", game_desc=" + game_desc + "]";
		}
	}
}
