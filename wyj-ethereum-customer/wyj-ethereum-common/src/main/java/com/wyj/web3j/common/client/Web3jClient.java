package com.wyj.web3j.common.client;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3jClient {
	
	public static  Web3j web3j = null;
	
	public static void initClient(){
		
		if(web3j==null) {
			web3j = Web3j.build(new HttpService("")); 	
		}
	}
	
	
	public static  Web3j getClient() {
		initClient();
		return web3j;
	}
	
}
