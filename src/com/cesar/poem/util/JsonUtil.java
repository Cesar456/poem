package com.cesar.poem.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.cesar.poem.bean.Author;
import com.cesar.poem.bean.Poem;

public class JsonUtil {

	private final static String baseUrlPoem = "http://*****:8080/PoemServer/Poem/";
	private final static String baseUrlUser = "http://*****:8080/PoemServer/User/";

	private final static String GETONERANDOM = baseUrlPoem + "getOneRandomPoem";
	private final static String GETRANDOMPOEM = baseUrlPoem + "getRandomPoem";
	private final static String GETPOEMBYPOEMID = baseUrlPoem + "getPoemByPoemId?poemId=%s";
	private final static String GETPOEMCOUNTBYAUTHORNAME = baseUrlPoem + "getPoemCountByAuthorName?authorName=%s";
	private final static String GETPOEMBYAUTHORNAME = baseUrlPoem + "getPoemByAuthorName?authorName=%s&pageNum=%s";
	private final static String GETPOEMCOUNTBYAUTHORID = baseUrlPoem + "getPoemCountByAuthorId?authorId=%s";
	private final static String GETPOEMBYAUTHORID = baseUrlPoem + "getPoemByAuthorId?authorId=%s&pageNum=%s";
	private final static String GETSEARCHTITLECOUNT = baseUrlPoem + "getSearchTitleCount?keyword=%s";
	private final static String SEARCHTITLE = baseUrlPoem + "searchTitle?keyword=%s&pageNum=%s";
	private final static String GETSEARCHCONTENTCOUNT = baseUrlPoem + "getSearchContentCount?keyword=%s";
	private final static String SEARCHCONTENT = baseUrlPoem + "searchContent?keyword=%s&pageNum=%s";
	private final static String GETPOEMCOUNTBYTAG = baseUrlPoem + "getPoemCountByTag?tag=%s";
	private final static String GETPOEMSBYTAG = baseUrlPoem + "getPoemsByTag?tag=%s&pageNum=%s";
	// TODO 加一个通过作者姓名获取作者详细信息
	private final static String GETAUTHORBYNAME = baseUrlPoem + "getAuthorByName?authorName=%s";

	private final static String LOGIN = baseUrlUser + "login";
	private final static String REGISTER = baseUrlUser + "register";

	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	private static HttpPost post;
	private static HttpGet get;
	private static HttpResponse response;
	private static HttpEntity entity;

	private static JsonUtil jsonUtil;
	private GsonUtil gsonUtil = new GsonUtil();

	private JsonUtil() {
	}

	public synchronized Poem getOneRandomPoem() {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(GETONERANDOM);
			response = httpClient.execute(get);
			entity = response.getEntity();

			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));

			if (maps.get("code").equals("200")) {
				Poem poem = gsonUtil.getPoemFromJson(maps.get("data"));
				return poem;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized List<Poem> getRandomPoem() {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(GETRANDOMPOEM);
			response = httpClient.execute(get);
			entity = response.getEntity();

			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));

			if (maps.get("code").equals("200")) {
				List<Poem> poems = gsonUtil.getPoemListFromJson(maps.get("data"));
				return poems;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized int getPoemCountByAuthorName(String authorName) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(GETPOEMCOUNTBYAUTHORNAME, authorName));
			response = httpClient.execute(get);
			entity = response.getEntity();

			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));

			if (maps.get("code").equals("200")) {
				return Integer.parseInt(maps.get("data"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public synchronized List<Poem> getPoemByAuthorName(String authorName, int pageNum) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(GETPOEMBYAUTHORNAME, authorName, pageNum));
			response = httpClient.execute(get);
			entity = response.getEntity();

			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));

			if (maps.get("code").equals("200")) {
				List<Poem> poems = gsonUtil.getPoemListFromJson(maps.get("data"));
				return poems;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized List<Poem> getPoemByAuthorID(int authorID, int pageNum) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(GETPOEMCOUNTBYAUTHORID, authorID, pageNum));
			response = httpClient.execute(get);
			entity = response.getEntity();

			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));

			if (maps.get("code").equals("200")) {
				return gsonUtil.getPoemListFromJson(maps.get("data"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized Author getAuthorByName(String authorName) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(GETAUTHORBYNAME, authorName));
			response = httpClient.execute(get);
			entity = response.getEntity();

			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));

			if (maps.get("code").equals("200")) {
				return gsonUtil.getAuthorFromJson(maps.get("data").toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized List<Poem> getPoemByTag(String tag, int page) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(GETPOEMSBYTAG, tag, page));
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));
			if (maps.get("code").equals("200")) {
				return gsonUtil.getPoemListFromJson(maps.get("data").toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 搜索标题，最多返回20条
	 * @param keyWord
	 * @param page
	 * @return
	 */

	public synchronized List<Poem> searchByTitle(String keyword) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(SEARCHTITLE, keyword,1));
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));
			if (maps.get("code").equals("200")) {
				return gsonUtil.getPoemListFromJson(maps.get("data").toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 搜索内容，最多20条
	 * @param keyword
	 * @param page
	 * @return
	 */
	public List<Poem> searchByContent(String keyword) {
		try {
			httpClient = new DefaultHttpClient();
			get = new HttpGet(String.format(SEARCHCONTENT, keyword,1));
			response = httpClient.execute(get);
			entity = response.getEntity();
			Map<String, String> maps = gsonUtil.getMapFromJson(EntityUtils.toString(entity));
			if (maps.get("code").equals("200")) {
				return gsonUtil.getPoemListFromJson(maps.get("data").toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static JsonUtil getInstance() {
		if (jsonUtil == null) {
			jsonUtil = new JsonUtil();
		}
		return jsonUtil;
	}

}
