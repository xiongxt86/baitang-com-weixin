package com.hzboy.weixin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
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
	private static String domain_put = "http://localhost:7072";// 上传删除
	private static String domain_down ="http://localhost:7071";// 下载浏览
	private static String domain_main = "http://localhost:8080";//静态资源和转交请求给tomcat
	private static String domain_proxy = "http://localhost:8086";//代理地址
	private static String domain_root = "D:/nginx_test/html";
	
	/**
	 * 文件已经存在
	 */
	private static final int  FILE_EXIST = 204;
	/**
	 * 文件已经存在
	 */
	private static final int  FOLDER_EXIST = 204;
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
			if(code == 201){
				return true;
			}else if(code == 405){
//				throw new RuntimeException("已经有文件夹！");
				return true;
			}
			return false;
		} catch (IOException e) {
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
	public static int saveFile(InputStream file, String uri){
		if(file == null)
			throw new RuntimeException("文件不存在！");
		if(StringUtile.isEmptyString(uri))
			throw new RuntimeException("保存路径为空！");
		try {
			PutMethod put = new PutMethod(uri);  
			RequestEntity requestEntity = new InputStreamRequestEntity(file);  
	        put.setRequestEntity(requestEntity); 
			int code = createClient().executeMethod(put);
			if(code == 201){
				return uri;
			} else if (code == 204){
				if(rewrite){//覆盖原文件
					removeFile(uri);
					return saveFile(file, uri, rewrite, remain);
				} else {
					if(remain){//已经存在，使用原文件
						return uri;
					}else{ //已经存在,使用新文件
						int extend = uri.lastIndexOf(".");
						String prefix = uri.substring(0, extend);
						String subfix = uri.substring(extend);
						return saveFile(file, prefix + "_" + new Date().getTime() + subfix, rewrite, remain);
					}
				}
			}
			return null;
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
			if(code == 204){
				return true;
			} else if(code == 404){
//				不存在文件
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("删除资源异常！");
		} 
	}
	
	
	public static boolean getFiles(String folder){
		return true;
	}
	
	public static void main(String[] args) {
		boolean createFolder = createFolder(domain_put + "/test/");
		System.out.println(createFolder);
		try {
			FileInputStream in = new FileInputStream("D:/nginx/html/cms/banner/032513380088/222.jpg");
			String uri = domain_put + "/test/222.jpg";
			String saveFile = saveFile(in, uri,true,false);
			System.out.println(saveFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		boolean removeFile = removeFile(domain_put + "/test/222.jpg");
//		System.out.println(removeFile);
		
	}
	
}
