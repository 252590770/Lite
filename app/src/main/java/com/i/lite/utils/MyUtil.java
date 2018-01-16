package com.i.lite.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.support.annotation.Nullable;
//import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


@SuppressLint("NewApi")
public class MyUtil {


	public final static int NewSCede = 1000;

	/**
	 * ���壺SD����Ŀ¼
	 */
	public static final String PATH = Environment.getExternalStorageDirectory()
			.getPath();

	/**
	 * ��������: MD5����
	 */
	public static String MD5(String inStr) {

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		String strMD5_32 = hexValue.toString();

		return strMD5_32.toUpperCase();
	}

	/**
	 * ������������ȡ�豸mac��ַ
	 *
	 * @param ������ ��Context
	 * @return ����ֵ���壺mac��ַ
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static String getMacAddress(Context c) {

		try {
			return loadFileAsString("/sys/class/net/eth0/address")
					.toUpperCase().substring(0, 17);
		} catch (IOException e) {
			e.printStackTrace();
			WifiManager wifi = (WifiManager) c
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			return info.getMacAddress();
		}
	}

	/**
	 * ��̬����listView �ĸ߶�
	 *
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * ������������̬����GridView �ĸ߶�
	 *
	 * @param �����壺GridView���������
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gridView,
														int NumColumns) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int lines = listAdapter.getCount() % NumColumns > 0 ? listAdapter
				.getCount() / NumColumns + 1 : listAdapter.getCount()
				/ NumColumns;
		int totalHeight = 0;
		for (int i = 0; i < lines; i++) {
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
//		params.height = totalHeight + (gridView.getVerticalSpacing() * lines);
		params.height = totalHeight + (lines);

		gridView.setLayoutParams(params);

	}


	/**
	 * ExpandableListView  的高度设置
	 *
	 * @param listView
	 */
	public static void setExpandableListViewHeightBasedOnChildren(ExpandableListView listView) {
		//获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);  //计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		//listView.getDividerHeight()获取子项间分隔符占用的高度
		//params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}


	/**
	 * ������������̬����ViewPager �ĸ߶�
	 *
	 * @param �����壺ViewPager���������
	 * @return ����ֵ���壺
	 * @throws IOException
	 *             (null)
	 * @throws NullPointerException
	 *             (null)
	 */
//	public static void setViewPagerHeightBasedOnChildren(
//			final ViewPager viewPager) {
//		final int w = View.MeasureSpec.makeMeasureSpec(0,
//				View.MeasureSpec.UNSPECIFIED);
//		final int h = View.MeasureSpec.makeMeasureSpec(0,
//				View.MeasureSpec.UNSPECIFIED);
//		viewPager.measure(w, h);
//		ViewTreeObserver vto = viewPager.getViewTreeObserver();
//		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//
//			public void onGlobalLayout() {
//				viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(
//						this);
//				View view = viewPager.getChildAt(viewPager.getCurrentItem());
//				view.measure(w, h);
//				LayoutParams params = new LayoutParams(
//						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//				params.height = view.getMeasuredHeight();
//				viewPager.setLayoutParams(params);
//			}
//		});
//	}

	/**
	 * �����ļ��еĿռ�.
	 *
	 * @return the int
	 */
	public static int freeSpaceOnSD() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / 1024 * 1024;
		return (int) sdFreeMB;
	}

	/**
	 * ��ʽ����λ
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "B";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "K";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "M";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "G";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "T";
	}

	/**
	 * ��ȡ��Ӧ�õİ汾��
	 *
	 * @param context
	 * @return
	 */

	public static String getCurrVersionName(Context context) {
		String versionCode = null;
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			versionCode = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * ����������
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          ��ȡ�ļ��쳣
	 * @throws NullPointerException (null)
	 */
	private static String loadFileAsString(String filePath)
			throws IOException {

		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	/**
	 * ���������������ļ���SD��
	 *
	 * @param ������ ��fileName���ļ���ȫĿ¼ message���ļ�����
	 * @return ����ֵ���壺
	 * @throws IOException          �ļ�д���쳣
	 * @throws NullPointerException (null)
	 */
	public static void writeFileSdcard(String fileName, String message)
			throws IOException {

		byte[] bytes = message.getBytes();

		writeFileSdcard(fileName, bytes);

	}

	/**
	 * ����������
	 *
	 * @param ������ ��fileName���ļ���ȫĿ¼ bytes���ļ������ֽ�
	 * @return ����ֵ���壺
	 * @throws IOException          �ļ�д���쳣
	 * @throws NullPointerException (null)
	 */
	public static void writeFileSdcard(String fileName, byte[] bytes)
			throws IOException {

		FileOutputStream fout = new FileOutputStream(fileName);

		fout.write(bytes);

		fout.close();

	}

//	public static String readFileSdcard(String fileName) {
//
//		String res = "";
//
//		try {
//
//			FileInputStream fin = new FileInputStream(fileName);
//
//			int length = fin.available();
//
//			byte[] buffer = new byte[length];
//
//			fin.read(buffer);
//
//			res = EncodingUtils.getString(buffer, "UTF-8");
//
//			fin.close();
//
//		}
//
//		catch (Exception e) {
//
//			e.printStackTrace();
//
//		}
//
//		return res;
//
//	}

	/**
	 * ���������������ļ���
	 *
	 * @param ������ ��name �ļ�ȫĿ¼
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static void createFile(String name, Context c) {

		if (!isHavaSDCard()) {
			Toast.makeText(c, "�����SD���������޷������Ķ�����",
					Toast.LENGTH_SHORT).show();
			return;
		}
		File dir = new File(name);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * ����������ɾ���ļ����ļ���
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static void deleteFile(File file) {

		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}

	/**
	 * �����������Ƿ���SD��
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static boolean isHavaSDCard() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}

		return false;
	}

	/**
	 * �����������������ļ� ��������
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static final String readPreferences(Context context1, String dataName) {

		String data = context1.getSharedPreferences("MYDATA",
				Context.MODE_PRIVATE).getString(dataName, null);

		return data;

	}

	/**
	 * ����������д�����ļ�
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static final void writePreferences(Context context1,
											  String dataName, String data) {

		SharedPreferences settings = context1.getSharedPreferences("MYDATA",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(dataName, data);
		editor.commit();

	}

	public static Drawable loadImageFromUrl(String url, String path_name) {

		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();

			File myFileTemp = new File(path_name);
			if (!myFileTemp.exists()) {

				FileOutputStream fos = new FileOutputStream(myFileTemp);

				byte[] b = new byte[1024];
				int aa;
				while ((aa = i.read(b)) != -1) {
					fos.write(b, 0, aa);
				}

				fos.close();
				i.close();
			}
			return Drawable.createFromPath(path_name);

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return sb.toString();

	}

	/**
	 * ������������
	 *
	 * @param ������ ��milliseconds ʱ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static void Vibrate(Context activity, long milliseconds) {

		Vibrator vib = (Vibrator) activity
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	/**
	 * �����������и��ַ�������
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static final String[] split(String str, String pre) {

		if (str == null || str.length() < 1)
			return null;

		if (!str.contains(pre)) {
			String[] arr = new String[1];
			arr[0] = str;
			return arr;
		}

		Vector<String> veTmp = new Vector<String>();
		while (str.indexOf(pre) != -1) {// ȡ��ÿ����־ǰ����ַ�����
			veTmp.addElement(str.substring(0, str.indexOf(pre)));
			str = str.substring(str.indexOf(pre) + 1, str.length());
		}
		if (str.length() > 0) {// ���һ����־�����ַ������򱣴�
			veTmp.addElement(str);
		}
		if (veTmp.size() == 0) {
			return null;
		}
		String arrTmp[] = new String[veTmp.size()];
		for (int i = 0; i < arrTmp.length; i++) {
			arrTmp[i] = (String) veTmp.elementAt(i);
		}
		veTmp = null;
		return arrTmp;
	}

	/**
	 * �����������ж��Ƿ���ȷ��email��ʽ
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static boolean isEmail(String email) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern p = Pattern.compile(str);

		Matcher m = p.matcher(email);

		return m.matches();

	}

	/**
	 * �������������벦�Ž���
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static void dialMobileNum(Context context, String mobile) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + mobile));
		context.startActivity(intent);
	}

	/**
	 * ����������ֱ�Ӳ�������
	 *
	 * @param ������ ��
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
	public static void callMobileNum(Context context, String mobile) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + mobile));
		context.startActivity(intent);
	}

	/**
	 * ����������������
	 *
	 * @param ������ �� ���� ������
	 * @return ����ֵ���壺
	 * @throws IOException          (null)
	 * @throws NullPointerException (null)
	 */
//	public static void sendMSG(Context context, String mobile, String msg) {
//		Uri uri = Uri.parse("smsto:" + mobile);
//		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//		if (AbStrUtil.isEmpty(msg)) {
//			intent.putExtra("sms_body", "");
//		} else {
//			intent.putExtra("sms_body", msg);
//		}
//		context.startActivity(intent);
//	}

	/**
	 * �õ�֪ͨ���ĸ߶�
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	public static void daDianHua(Context context, String mobile) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + mobile));
		context.startActivity(intent);
	}

	/**
	 * getCurrVersionCode(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 *
	 * @return int
	 * @throws
	 * @since 1.0.0
	 */
	public static int getCurrVersionCode(Context context) {
		int versionCode = 0;
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static Boolean isPhonenumber(String phoneString) {
		if (phoneString.length() < 11) {
			return false;
		} else {
			String telRegex = "([1](([3][0-9])|([4][5|7|9])|([5][0-9])|([8][0|1|2|3|4|5|6|7|8|9])))\\d{8}";
			if (phoneString.matches(telRegex)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 获取屏幕宽
	 */
	public static int getScreenParameterScreenWidth(Activity thisActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		thisActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// int height = dm.heightPixels ;//高度
		// int width = dm.widthPixels;//宽度
		// ScreenWidth = dm.widthPixels;// 宽度
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高
	 */
	public static int getScreenParameterScreenHeight(Activity thisActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		thisActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		// int height = dm.heightPixels ;//高度
		// int width = dm.widthPixels;//宽度
		// ScreenWidth = dm.widthPixels;// 宽度
		return dm.heightPixels;
	}

	public static Date dateStingToDate(String time, int type) {

		Date mDate = null;
		SimpleDateFormat CeshiFmt7 = new SimpleDateFormat("yyyy-MM-dd");// HH

		try {
			mDate = CeshiFmt7.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mDate;


	}

	public static String dateFormat(String time, int type) {

		SimpleDateFormat CeshiFmt0 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// hh
		// 12小时制
		SimpleDateFormat CeshiFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH
		// 24小时制
		SimpleDateFormat CeshiFmt2 = new SimpleDateFormat("MM.dd");
		SimpleDateFormat CeshiFmt3 = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat CeshiFmt4 = new SimpleDateFormat("yyyy/MM/dd E");
		SimpleDateFormat CeshiFmt5 = new SimpleDateFormat(
				"一年中的第 D 天 ，第w个星期 ，一个月中第W个星期 ，k时 z时区");

		SimpleDateFormat CeshiFmt6 = new SimpleDateFormat("yyyy.MM.dd HH:mm");// HH

		SimpleDateFormat CeshiFmt7 = new SimpleDateFormat("yyyy-MM-dd");// HH

		SimpleDateFormat CeshiFmt8 = new SimpleDateFormat("MM.dd HH:mm");// HH

		SimpleDateFormat CeshiFmt9 = new SimpleDateFormat("yyyy.MM.dd");// HH

		SimpleDateFormat CeshiFmt10 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// HH  2016-07-05 12:00:00.0

		SimpleDateFormat CeshiFmt11 = new SimpleDateFormat("MM月dd日 HH:mm");// HH  07-05 12:00

		Date mDate = null;

		try {


			if (type == 0) {

				mDate = CeshiFmt1.parse(time);
				return CeshiFmt0.format(mDate);

			} else if (type == 1) {

				mDate = CeshiFmt1.parse(time);
				return CeshiFmt1.format(mDate);

			} else if (type == 2) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt2.format(mDate);

			} else if (type == 3) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt3.format(mDate);

			} else if (type == 4) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt4.format(mDate);

			} else if (type == 5) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt5.format(mDate);

			} else if (type == 6) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt6.format(mDate);
			} else if (type == 7) {

				mDate = CeshiFmt7.parse(time);
				return CeshiFmt7.format(mDate);
			} else if (type == 8) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt8.format(mDate);
			} else if (type == 9) {
				mDate = CeshiFmt1.parse(time);
				return CeshiFmt9.format(mDate);
			} else if (type == 10) {

				mDate = CeshiFmt10.parse(time);

				return CeshiFmt11.format(mDate);
			} else if (type == 11) {

				mDate = CeshiFmt7.parse(time);
				return mDate.getTime() + "";
			}


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	public static Date dateFormat2(String time, int type) {

		SimpleDateFormat CeshiFmt0 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// hh
		// 12小时制
		SimpleDateFormat CeshiFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH
		// 24小时制
		SimpleDateFormat CeshiFmt2 = new SimpleDateFormat("MM.dd");
		SimpleDateFormat CeshiFmt3 = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat CeshiFmt4 = new SimpleDateFormat("yyyy/MM/dd E");
		SimpleDateFormat CeshiFmt5 = new SimpleDateFormat(
				"一年中的第 D 天 ，第w个星期 ，一个月中第W个星期 ，k时 z时区");

		SimpleDateFormat CeshiFmt6 = new SimpleDateFormat("yyyy.MM.dd HH:mm");// HH

		SimpleDateFormat CeshiFmt7 = new SimpleDateFormat("yyyy-MM-dd");// HH

		SimpleDateFormat CeshiFmt8 = new SimpleDateFormat("MM.dd HH:mm");// HH

		SimpleDateFormat CeshiFmt9 = new SimpleDateFormat("yyyy.MM.dd");// HH

		SimpleDateFormat CeshiFmt10 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// HH  2016-07-05 12:00:00.0

		SimpleDateFormat CeshiFmt11 = new SimpleDateFormat("MM月dd日 HH:mm");// HH  07-05 12:00

		Date mDate = null;

		try {


			if (type == 0) {

				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 1) {

				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 2) {
				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 3) {
				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 4) {
				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 5) {
				mDate = CeshiFmt1.parse(time);
				return mDate;

			} else if (type == 6) {
				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 7) {

				mDate = CeshiFmt7.parse(time);
				return mDate;
			} else if (type == 8) {
				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 9) {
				mDate = CeshiFmt1.parse(time);
				return mDate;
			} else if (type == 10) {

				mDate = CeshiFmt10.parse(time);

				return mDate;
			} else if (type == 11) {

				mDate = CeshiFmt7.parse(time);
				return mDate;
			}


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}



	/**
	 * @param thisActivity
	 * @return 以屏幕宽度为宽高的 Param
	 */
	public static LayoutParams getParam(
			Activity thisActivity) {

		LayoutParams params = new LayoutParams(
				MyUtil.getScreenParameterScreenWidth(thisActivity),
				MyUtil.getScreenParameterScreenWidth(thisActivity));

		return params;
	}


	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH) - 1;

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth 
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth 
				age--;
			}
		}
		return age;
	}


	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}


	/**
	 * 字节数组转成图片
	 */
	public static Bitmap getPicFromBytes(byte[] bytes,
										 BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}


	/**
	 * 检查存储卡
	 *
	 * @param context
	 */
//	public static void initFolderDir(Context context) {
//		File PHOTO_DIR = null;
//
//		String photo_dir = AbFileUtil.getImageDownloadDir(context);
//		if (AbStrUtil.isEmpty(photo_dir)) {
//			AbToastUtil.showToast(context, "存储卡不存在");
//		} else {
//			PHOTO_DIR = new File(photo_dir);
//		}
//	}

	/**
	 * @param dp      安卓控件大小单位
	 * @param context
	 * @return px 像素
	 * @ dp:sp 总为1:1关系
	 * @ dpi 每英寸打的点数
	 * @ 安卓有四种dpi(dot per inch 每英寸像素)
	 * @ 一般情况下
	 * @ 1dpi(low)          = 120dot
	 * @ 1dpi(medium)       = 160dot
	 * @ 1dpi(high)         = 240dot
	 * @ 1dpi(extra high)   = 240dot
	 */
	public static int dp2px(int dp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}


	/**
	 * Drawable To Bitamp
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitamp(Drawable drawable) {
		Bitmap bm = null;

		BitmapDrawable bd = (BitmapDrawable) drawable;
		bm = bd.getBitmap();

		return bm;
	}


	/**
	 * Drawable To Bitamp
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitamp2(Drawable drawable)

	{
		Bitmap bitmap = null;
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		System.out.println("Drawable转Bitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		bitmap = Bitmap.createBitmap(w, h, config);
		// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}


	/**
	 * 计算两个日期相差的天数
	 *
	 * @param date1
	 * @param date2
	 * @return 相差的天数    date1 - date2
	 */
	public static int dateDiff(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
		// Use integer calculation, truncate the decimals
		int hr1 = (int) (ldate1 / 3600000); // 60*60*1000
		int hr2 = (int) (ldate2 / 3600000);

		int days1 = hr1 / 24;
		int days2 = hr2 / 24;

		int dateDiff = days1 - days2;
		return dateDiff;
	}
	
	
	/*
	public static String getAPPSecretString(Activity activity){
		String backString="";
		try {
		PackageInfo mPackageInfo=activity.getPackageManager().getPackageInfo(GlobalConfig.APP_PACKAGE_NAME, 64);
		Signature xx[]=mPackageInfo.signatures;
		byte[] arrayOfByte= mPackageInfo.signatures[0].toByteArray();
		backString= digest(arrayOfByte);
		} catch (NameNotFoundException e1) {
		e1.printStackTrace();
		}
		return backString;
		}


		private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		public static final String digest(String message) {
		try {
		byte[] strTemp = message.getBytes();
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
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
		return null;
		}
		}
		public static final String digest(byte[] strTemp) {
		try {
		// byte[] strTemp = message.getBytes();
		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
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
		return null;
		}
		}
	*/


	/**
	 * 以最省内存的方式读取本地资源的图片
	 *
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}



	/*
    Date date = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MMdd HH:mm:ss");
        try {
        date = format.parse("2017-1030 18:12:00");
    } catch (ParseException e) {
    }

    String  dataStr = CountDateUtil.getTimestampString(date);
    */


	//传入Date  返回明天今天昨天字符串
	public static String getDateTimeString(Date date) {

		String formatStr = null;
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//可以方便地修改日期格式
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH");//可以方便地修改日期格式

		String nowStr = dateFormat.format(now);
		String dateStr = dateFormat.format(date);

		String dateHour = dateFormat2.format(date);
		int hour = Integer.parseInt(dateHour);

		Date now1 = null;
		Date date1 = null;
		try {
			now1 =  dateFormat.parse(nowStr);
			date1 = dateFormat.parse(dateStr);
		}catch (Exception e){


		}

		int hr1 = (int) (now1.getTime() / 3600000); // 60*60*1000
		int hr2 = (int) (date1.getTime() / 3600000);

		int nowDays = hr1 / 24;
		int dateDays = hr2 / 24;


		if(dateDays-nowDays==1){//明天

			formatStr = "明天 HH:mm";

		}else if(dateDays-nowDays==0){//今天

			if (hour > 17) {
				formatStr = "晚上 HH:mm";//HH表示24小时,hh表示12小时进制，
			} else if ((hour >= 0) && (hour <= 6)) {
				formatStr = "凌晨 HH:mm";
			} else if ((hour > 11) && (hour <= 17)) {
				formatStr = "下午 HH:mm";
			} else {
				formatStr = "上午 HH:mm";
			}

		}else if(dateDays-nowDays==-1){//昨天

			formatStr = "昨天 HH:mm";

		}else if(dateDays-nowDays==-2){//前天

			formatStr = "前天 HH:mm";

		}else {

			formatStr = "M月d日 HH:mm";

		}

		String dateFormat3 = new SimpleDateFormat(formatStr, Locale.CHINA).format(date);
		return dateFormat3;

	}


	public static boolean isEmpty(String string){

		boolean ok = false;

		if(string.equals("")){
			ok = true;
			return ok;
		}

		return false;
	}
}