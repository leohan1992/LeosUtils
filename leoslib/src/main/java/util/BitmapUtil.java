package util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BitmapUtil {
	public static String cachePath =Environment.getExternalStorageDirectory().getAbsolutePath()+"/cache/";
	public static Bitmap getCompressedBitmap(Context mcontext, int resId) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;// 表示不真正解析图片，只解析图片的头部信息
		BitmapFactory.decodeResource(mcontext.getResources(), resId, opts);
		int height = opts.outHeight;
		int width = opts.outWidth;
		int scaleX = width / 3;
		int scaleY = height / 3;
		int scale = 3;
		if (scaleX > scaleY) {
			scale = scaleX;
		} else {
			scale = scaleY;
		}

		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeResource(mcontext.getResources(),
				resId, opts);
		return bm;
	}

	public static Bitmap getCompressedBitmap(String resPath) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;// 表示不真正解析图片，只解析图片的头部信息
		BitmapFactory.decodeFile(resPath, opts);
		int height = opts.outHeight;
		int width = opts.outWidth;
		int scaleX = width /100;
		int scaleY = height /100;
		int scale = 0;
		if (scaleX > scaleY) {
			scale = scaleX;
		} else {
			scale = scaleY;
		}

		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(resPath, opts);
		
		return bm;
	}
	/**
	 * 
	 * 方法说明：取得压缩后的bitmap ，根据屏幕比例裁剪
	 *
	 * @param mcontext 上下文
	 * @param id 图片资源id
	 * @return 压缩后的bitmap
	 */
	public static Bitmap getCompressedBitmapFitScreen(Context mcontext, int id) {
	;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;// 表示不真正解析图片，只解析图片的头部信息
		BitmapFactory.decodeResource(mcontext.getResources(), id, opts);
		int height = opts.outHeight;
		int width = opts.outWidth;
		int scaleX = width / (((Activity)(mcontext)).getWindowManager().getDefaultDisplay().getWidth());
		int scaleY = height / (((Activity)(mcontext)).getWindowManager().getDefaultDisplay().getHeight());
		int scale = 3;
		if (scaleX > scaleY) {
			scale = scaleX;
		} else {
			scale = scaleY;
		}

		opts.inSampleSize = scale + 1;
		opts.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeResource(mcontext.getResources(), id,
				opts);
		return bm;

	}
	/**
	 * 
	 * 方法说明：取得压缩后的bitmap ，根据屏幕比例裁剪
	 *
	 * @param path 图片文件路径
	 * @return 压缩后的bitmap
	 */
	public static Bitmap getCompressedBitmapFitScreen(Context mcontext,String path) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;// 表示不真正解析图片，只解析图片的头部信息
		BitmapFactory.decodeFile(path, opts);
		int height = opts.outHeight;
		int width = opts.outWidth;
		int scaleX = width / ((Activity)(mcontext)).getWindowManager().getDefaultDisplay().getWidth();
		int scaleY = height / ((Activity)(mcontext)).getWindowManager().getDefaultDisplay().getHeight();
		int scale = 3;
		if (scaleX > scaleY) {
			scale = scaleX;
		} else {
			scale = scaleY;
		}

		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(path, opts);

		return bm;
	}
	public static File getCompressedBitmapFile(Context context,String path){
		Bitmap bm=getCompressedBitmapFitScreen(context, path);
		CompressFormat format= CompressFormat.JPEG;
		int quality=40;
		File fileDir=new File(cachePath);
		if  (!fileDir .exists()  && !fileDir .isDirectory())      
		{ 
			fileDir.mkdirs();
		}
		try {
			OutputStream ops=new FileOutputStream(cachePath+"head.jpg");
			bm.compress(format, quality, ops);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file=new File(cachePath+"head.jpg");
		return file;
	}
	public static File getCompressedBitmapFile(Context context,String path,String name){
		Bitmap bm=getCompressedBitmapFitScreen(context, path);
		CompressFormat format= CompressFormat.JPEG;
		int quality=40;
		File fileDir=new File(cachePath);
		if  (!fileDir .exists()  && !fileDir .isDirectory())      
		{ 
			fileDir.mkdirs();
		}
		try {
			OutputStream ops=new FileOutputStream(cachePath+name+".jpg");
			bm.compress(format, quality, ops);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file=new File(cachePath+name+".jpg");
		return file;
	}
}
