package com.hzboy.weixin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;

import core.StringUtile;

/**
 * 文件操作
 * @ClassName: WebDavUtil 
 * @Description: TODO 文件操作
 * @author xiongxiaotun@126.com 
 * @date 2015年4月23日 上午9:29:12 
 *
 */
public class WebDavUtil {
	private static String domain_put = "http://localhost:7072/";// 上传删除
	private static String domain_down ="http://localhost:7071/";// 下载浏览
	private static String domain_main = "http://localhost:8080/";//静态资源和转交请求给tomcat
	private static String domain_proxy = "http://localhost:8085/";//代理地址
	private static String domain_root = "D:/nginx_test/html/";
	
	/**
	 * 响应成功
	 */
	private static final int  RESPONSE_SUCCESS = 201;
	/**
	 * 文件已经存在
	 */
	private static final int  FILE_EXIST = 204;
	/**
	 * 删除成功
	 */
	private static final int  DELETE_OK = 204;
	/**
	 * 没有可删除资源
	 */
	private static final int  DELETE_NONE = 404;
	/**
	 * 文件夹已经存在
	 */
	private static final int  FOLDER_EXIST = 405;
	/**
	 * 响应失败
	 */
	private static final int  RESPONSE_FAIL = 0;
	/**
	 * 创建客户端
	 * @param username 用户名
	 * @param password 用户密码
	 */
	public static HttpClient createClient(String username,String password){
		HttpClient client = new HttpClient();
        Credentials creds = new UsernamePasswordCredentials(username,password);
        client.getState().setCredentials(AuthScope.ANY, creds);
		return client;
	}
	/**
	 * 创建客户端
	 */
	public static HttpClient createClient(){
		return createClient("","");
	}
	/**
	 * 创建文件夹
	 * @param folder
	 * @return 是否存在文件夹（创建或者已有）
	 */
	public static boolean createFolder(String folder){
		if(StringUtile.isEmptyString(folder))
			throw new RuntimeException("资源文件夹不能为空！");
		try {
			DavMethod mkCol = new MkColMethod(folder);
			int code = createClient().executeMethod(mkCol);
			if(code == RESPONSE_SUCCESS){
				return true;
			} else if(code == FOLDER_EXIST){
				return true;
			}
			throw new RuntimeException("未知原因导致创建文件夹失败！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("创建文件夹异常！");
		}
	}
	/**
	 * 保存文件
	 * @param file 保存的文件
	 * @param uri 保存路径
	 * @return 响应码
	 */
	public static String saveFile(InputStream file, String uri, boolean rewrite){
		if(file == null)
			throw new RuntimeException("文件不存在！");
		if(StringUtile.isEmptyString(uri))
			throw new RuntimeException("保存路径为空！");
		try {
			if(rewrite){
				removeFile(uri);
			}
			PutMethod put = new PutMethod(uri);  
			RequestEntity requestEntity = new InputStreamRequestEntity(file);  
	        put.setRequestEntity(requestEntity); 
			int code = createClient().executeMethod(put);
			if(code == RESPONSE_SUCCESS){
				return uri;
			} else if(code == FILE_EXIST){
				throw new RuntimeException("存在同名文件！");
			}
			throw new RuntimeException("未知原因导致保存失败！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("保存文件异常！");
		} finally{
			try {
				if(file != null)
					file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 删除文件/文件夹
	 * @param uri
	 * @return
	 */
	public static boolean removeFile(String uri){
		if(StringUtile.isEmptyString(uri))
			throw new RuntimeException("资源路径为空！");
		try {
			DeleteMethod del = new DeleteMethod(uri);  
			int code = createClient().executeMethod(del);
			if(code == DELETE_OK){
				return true;
			} else if(code == DELETE_NONE){
//				不存在文件
				return true;
			}
			throw new RuntimeException("未知原因删除失败！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("删除资源异常！");
		} 
	}
	
	/**
	 * 读取文件
	 * @param folder
	 * @return
	 */
	public static boolean getFiles(String folder){
		if(StringUtile.isEmptyString(folder))
			throw new RuntimeException("资源文件夹不能为空！");
		try {
			DavMethod find = new PropFindMethod(folder, DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
			int code = createClient().executeMethod(find); 
			System.out.println(code);
	        MultiStatus multiStatus = find.getResponseBodyAsMultiStatus();  
	        MultiStatusResponse[] responses = multiStatus.getResponses();  
	        System.out.println("Folders and files:");  
	        for (int i=0; i<responses.length; i++) {  
	            System.out.println(responses[i].getHref());  
	        }    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}
	
	public static void main(String[] args) {
		boolean files = getFiles("http://localhost:7072/test");
//		boolean createFolder = createFolder(domain_put + "test/test/");
//		System.out.println(createFolder);
//		boolean removeFile = removeFile(domain_put + "test/");
//		System.out.println(removeFile);
		//=========================================
//		try {
//			FileInputStream in = new FileInputStream("E:/pictuers/2013年4月11日/IMG_20130406_073832.jpg");
//			String uri = domain_put + "/test/222.jpg";
//			String saveFile = saveFile(in, uri,true);
//			System.out.println(saveFile);
//			FileInputStream in1 = new FileInputStream("E:/pictuers/2013年4月11日/IMG_20130406_073832.jpg");
//			String uri1 = domain_put + "/test/test/222.jpg";
//			String saveFile1 = saveFile(in1, uri1,true);
//			System.out.println(saveFile1);
//			FileInputStream in2 = new FileInputStream("E:/pictuers/2013年4月11日/IMG_20130406_073832.jpg");
//			String uri2 = domain_put + "/test/test/test/222.jpg";
//			String saveFile2 = saveFile(in1, uri2,true);
//			System.out.println(saveFile2);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		boolean removeFile = removeFile(domain_put + "/test/222.jpg");
//		System.out.println(removeFile);
		
	}
	
}
