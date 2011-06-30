package br.com.jera.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Preferences {

	public final static boolean PAID = true;
	
	static {
		if (PAID == true) {
			PACKAGE_NAME = "br.com.jera.vikingspaid";
			PATH = "/data/data/br.com.jera.vikingspaid/";
			PATH_URL = "http://games.jera.com.br/splash/vvzpaid/";
			PREFS_NAME = "vvzpaid";
			FLURRY_KEY = "DXAMJEXYE2YYM3XNQ7ZK";
		} else {
			PACKAGE_NAME = "br.com.jera.vikings";
			PATH = "/data/data/br.com.jera.vikings/";
			PATH_URL = "http://games.jera.com.br/splash/vvz/";
			PREFS_NAME = "vvz";
			FLURRY_KEY = "XINMTTGW41TKQ6SDWGDM";
		}
	}

	public final static String PACKAGE_NAME;
	public final static String PATH;
	public final static String PATH_URL;
	public static final String PREFS_NAME;
	public static final String FLURRY_KEY;

	public static String readString(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(key, null);
	}

	public static boolean readBoolean(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean(key, false);
	}

	public static void write(Activity activity, String key, Object value) {
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof String) {
			editor.putString(key, (String) value);
		}
		editor.commit();
	}

}
