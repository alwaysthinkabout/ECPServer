package ECP.util.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.json.JSONObject;

import sun.misc.BASE64Decoder;

public class CommonUtil {
	
	public static boolean GenerateFile(String Str, String fileName){
		if(Str == null){
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try{
			byte[] b = decoder.decodeBuffer(Str);
			for(int i=0;i<b.length;i++){
				if(b[i]<0){
					b[i]+=256;
				}
			}
			OutputStream out = new FileOutputStream(fileName);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 音视频图片处理
	 * 
	 * @param mStr
	 * @return
	 * @throws Exception
	 */
	public static String f_uploadVedio(JSONObject jsonObject) throws Exception {
		String mResult = "";
		String fileType = "video";
		int startPosL = 0;
		RandomAccessFile oSavedFile = null;
		//byte[] vedioBytes = jsonObject.getString("VEDIO").getBytes("UTF-8");
		byte[] vedioBytes = decode(jsonObject.getString("buffer"));
		for (int i = 0; i < vedioBytes.length; ++i) {  
            if (vedioBytes[i] < 0) {  
                // 调整异常数据  
            	vedioBytes[i] += 256;  
            }  
        }  
		startPosL = (Integer) jsonObject.get("start"); // 接收客户端的开始位置(文件读取到的字节大小)
		fileType = (String) jsonObject.getString("filetype");
		String fileName = (String) jsonObject.getString("FileName");
		System.out.println("fileName " + fileName);
		if (fileType.equals("picture")) {
			oSavedFile = new RandomAccessFile("F:\\" + fileName, "rw");
		} else if (fileType.equals("photo")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName + ".jpg", "rw");
		} else if (fileType.equals("voice")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName + ".mp3", "rw");
		} else if (fileType.equals("video")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName, "rw");
		}
		// 设置标志位,标志文件存储的位置
		oSavedFile.seek(startPosL);
		oSavedFile.write(vedioBytes);
		oSavedFile.close();
		mResult = "000";
		return mResult;
	}
	
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}

}
