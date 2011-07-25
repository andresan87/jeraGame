package br.com.jera.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import br.com.jera.androidutil.Preferences;

import com.tapjoy.TapjoyConnect;
import com.tapjoy.VGStoreItem;


public class VirtualGoods {

	public static final String APP_ID = "f9ee3e4d-4e4a-455b-a7d1-7be17fae38ba";
	public static final String APP_SECRET_KEY = "3rT18eY8JBrSBHwDKmvF";

	public static List<Integer> listMaps = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
	public static List<Integer> listTowers = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
	public static List<Integer> listSpeeds = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
	
	public static List<Integer> purchasedMaps = new ArrayList<Integer>(Arrays.asList(1));
	public static List<Integer> purchasedTowers = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
	public static List<Integer> purchasedSpeeds = new ArrayList<Integer>(Arrays.asList(0, 1));

	public void connect(Activity activity) {
		if (Preferences.PAID) {
			purchasedMaps = listMaps;
			purchasedTowers = listTowers;
			purchasedSpeeds = listSpeeds;
			return;
		}
		TapjoyConnect.requestTapjoyConnect(activity.getApplicationContext(), APP_ID, APP_SECRET_KEY);
		TapjoyConnect.getTapjoyConnectInstance().checkForVirtualGoods(null);
		populateMaps();
		populateTowers();
		populateSpeeds();
	}

	public void openStore(Activity activity) {
		this.connect(activity);
		TapjoyConnect.getTapjoyConnectInstance().setUserDefinedColor(0x005C3317);
		TapjoyConnect.getTapjoyConnectInstance().showVirtualGoods(null);
	}

	public ArrayList<VGStoreItem> getList() {
		return TapjoyConnect.getTapjoyConnectInstance().getPurchasedItems();
	}

	private void populateMaps() {
		ArrayList<VGStoreItem> purchasedItems = TapjoyConnect.getTapjoyConnectInstance().getPurchasedItems();
		for (int i = 0; i < purchasedItems.size(); i++) {
			VGStoreItem item = purchasedItems.get(i);
			if (item.getVgStoreItemTypeName().equals("map")) {
				for (int j = 0; j < item.getVgStoreItemsAttributeValueList().size(); j++) {
					if (item.getVgStoreItemsAttributeValueList().get(j).getAttributeType().equals("id")) {
						purchasedMaps.add(Integer.parseInt(item.getVgStoreItemsAttributeValueList().get(j).getAttributeValue()));
					}
				}
			}
		}
	}

	private void populateTowers() {
		ArrayList<VGStoreItem> purchasedItems = TapjoyConnect.getTapjoyConnectInstance().getPurchasedItems();
		for (int i = 0; i < purchasedItems.size(); i++) {
			VGStoreItem item = purchasedItems.get(i);
			if (item.getVgStoreItemTypeName().equals("tower")) {
				for (int j = 0; j < item.getVgStoreItemsAttributeValueList().size(); j++) {
					if (item.getVgStoreItemsAttributeValueList().get(j).getAttributeType().equals("id")) {
						purchasedTowers.add(Integer.parseInt(item.getVgStoreItemsAttributeValueList().get(j).getAttributeValue()));
					}
				}
			}
		}
	}

	private void populateSpeeds() {
		ArrayList<VGStoreItem> purchasedItems = TapjoyConnect.getTapjoyConnectInstance().getPurchasedItems();
		for (int i = 0; i < purchasedItems.size(); i++) {
			VGStoreItem item = purchasedItems.get(i);
			if (item.getVgStoreItemTypeName().equals("speed")) {
				for (int j = 0; j < item.getVgStoreItemsAttributeValueList().size(); j++) {
					if (item.getVgStoreItemsAttributeValueList().get(j).getAttributeType().equals("id")) {
						purchasedSpeeds.add(Integer.parseInt(item.getVgStoreItemsAttributeValueList().get(j).getAttributeValue()));
					}
				}
			}
		}
	}
}
