package com.cesar.poem.util;

import java.security.MessageDigest;
import java.util.List;

import com.cesar.poem.view.IconView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.view.View;

public class CommonUtil {
	
	private static CommonUtil commonUtil;
	
	private CommonUtil(){}

//	//获取当前时间
//	public String getFullCurrentDateTime(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//		String currentDateTime = sdf.format(new Date());		
//		return currentDateTime;
//	}
	//获取md5码
	public String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 获取当前网络连接状态，有为true，无为false
	 * @param context
	 * @return
	 */
	public boolean getNetState(Context context){
		ConnectivityManager nw = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = nw.getActiveNetworkInfo();
		if(netinfo!=null){
			return netinfo.isAvailable();
		}
		return false;
	}
	/**
	 * 返回list中最长的长度
	 * @param list
	 * @return
	 */
	public int getMaxLength(List<String> list){
		int max = 0;
		for(String s:list){
			if(s.length()>max){
				max = s.length();
			}
		}
		return max;
	}
	
	/**
	 * 针对于iconview的方法，重绘该list中所以噗view的icon透明度
	 * @param iconViews
	 */
	public void resetAllAlpha(List<IconView> iconViews) {
		// 先重置所有的颜色
		for (IconView iconView : iconViews) {
			iconView.setIconAlpha(0.0f);
		}
	}
	/**
	 * 通过位置改变iconview透明度，同样仅针对iconview
	 * @param position
	 * @param positionOffset
	 * @param iconViews
	 */
	public void changeIconColor(int position, float positionOffset,List<IconView> iconViews) {
		// positionoffset=0时。第四个页面会出现positon=4，导致溢出错误
		if (positionOffset > 0) {
			iconViews.get(position).setIconAlpha(1 - positionOffset);
			iconViews.get(position + 1).setIconAlpha(positionOffset);
		}
	}
	
	public void onTtabClick(View v,List<IconView> iconViews,ViewPager viewPager) {
		resetAllAlpha(iconViews);
		IconView iconView = (IconView) v;
		iconView.setIconAlpha(1.0f);
		for(int i=0;i<iconViews.size();i++){
			if(v.getId()==iconViews.get(i).getId()){
				viewPager.setCurrentItem(i);
				break;
			}
		}
	}
	public static CommonUtil getInstance() {
		if(commonUtil==null){
			commonUtil = new CommonUtil();
		}
		return commonUtil;
	}
}
