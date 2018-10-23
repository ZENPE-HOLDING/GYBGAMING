package com.wyj.web3j.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.web3j.utils.Convert.Unit;

import com.wyj.web3j.common.util.FileUtils;

public interface IWeb3jService {
	/**
	 * 根据密码创建以太坊钱包，并返回地址
	 * @param password 密码
	 * @return
	 * @throws Exception 
	 */
	String creataWallet(String password) throws Exception;
	/**
	 * 获取以太余额
	 * @param address 地址
	 * @return
	 */
	BigInteger 	getEthBalance(String address) throws Exception;
	/**
	 * 以太币转账
	 * @param address
	 * @param filePath
	 * @param password
	 * @param value
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	boolean sendEth(String address, String source, String password, BigDecimal value, Unit unit) throws Exception;
	/**
	 * @param password
	 * @return
	 * @throws Exception
	 */
	Map<String, String> creataWalletMap(String password) throws Exception;
	
	void downLoad(String fileName, HttpServletResponse response) throws Exception;
}
