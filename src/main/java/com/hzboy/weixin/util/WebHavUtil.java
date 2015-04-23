package com.hzboy.weixin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.StatusLine;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;

import core.StringUtile;

/**
 * 文件操作
 * @author xiongxt
 * @2015年4月22日 下午9:35:53
 */
public class WebHavUtil {
	/**
	 * 客户端初始化
	 */
	public static HttpClient createClient(String username, String password) {
		HttpClient client = new HttpClient();
        Credentials creds = new UsernamePasswordCredentials(username,password);
        client.getState().setCredentials(AuthScope.ANY, creds);
        return client;
	}
	/**
	 * 创建远程目录
	 * @param folder 目录
	 */
	public static boolean mkDir(String folder){
		DavMethod mkCol = new MkColMethod(folder);  
		try {
			int code = createClient("", "").executeMethod(mkCol);
			if(code == 200){
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("创建目录!");
		}
		
	}
	
	/**
	 * 保存文件
	 * @param uri 文件远程路径
	 * @param file 文件本地路径
	 * @return
	 */
	public static boolean saveFile(String uri, String file) {
		if(StringUtile.isEmptyString(file)) 
			throw new RuntimeException("文件不存在!");
		try {
			InputStream inputStream = new FileInputStream(file);
			return saveFile(uri, inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("保存文件异常!");
		}
	}
	/**
	 * 保存文件
	 * @param uri 远程路径
	 * @param file 本地文件
	 * @return
	 */
	public static boolean saveFile(String uri, InputStream file) {
		if(file == null) 
			throw new RuntimeException("文件不存在!");
		PutMethod put = new PutMethod(uri);
		RequestEntity requestEntity = null;
		try {
			requestEntity = new InputStreamRequestEntity(file);
			put.setRequestEntity(requestEntity);
			int code = createClient("","").executeMethod(put);
			if(code == 200){
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException("保存文件异常!");
		}
	}
	/**
	 * 删除文件
	 * @param uri 要删除文件路径
	 * @return
	 */
	public boolean removeFile(String uri) {
		if(StringUtile.isEmptyString(uri)) 
			throw new RuntimeException("文件不存在!");
		DavMethod delete = new DeleteMethod(uri);
		try {
			int code = createClient("", "").executeMethod(delete);
			if(code == 200){
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException("删除文件异常!");
		} 
	}
	/**
	 * 打开文件
	 * @param uri 文件url
	 * @return
	 */
	public static InputStream openFile(String uri) {
		if(StringUtile.isEmptyString(uri)) 
			throw new RuntimeException("文件不存在!");
		DavMethod delete = new DeleteMethod(uri);
		return null;
	}
	/**
	 * 查询目录下的文件
	 * @param folder 目录
	 * @return 
	 */
	public static List<String> findFile(String folder){
		List<String> uris = new ArrayList<String>();
		if(StringUtile.isEmptyString(folder)) 
			throw new RuntimeException("文件夹不存在!");
		try {
			DavMethod find = new PropFindMethod(folder, DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
			int code = createClient("", "").executeMethod(find);
			if(code == 200){
				MultiStatus multiStatus = find.getResponseBodyAsMultiStatus();  
				MultiStatusResponse[] responses = multiStatus.getResponses(); 
				for (int i=0; i<responses.length; i++) {  
					uris.add(responses[i].getHref());  
		        }
			}
			return uris;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("文件查询异常!");
		}
	}
	public static void main(String[] args) {
		List<String> findFiles = findFile("http://localhost:8080/temp");
		for(String findFile:findFiles)
		System.out.println(findFile);
	}
}
