package com.cesar.poem.util;

import java.util.List;
import java.util.Map;

import com.cesar.poem.bean.Author;
import com.cesar.poem.bean.Poem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class GsonUtil {
	
	private Gson gson = new Gson();
	
	public Map<String, String> getMapFromJson(String json){
		return gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
	}
	
	public Poem getPoemFromJson(String json){
		return gson.fromJson(json, Poem.class);
	}
	public List<Poem> getPoemListFromJson(String json){
		return gson.fromJson(json, new TypeToken<List<Poem>>() {}.getType());
	}
	public Author getAuthorFromJson(String json){
		return gson.fromJson(json, Author.class);
	}

}
