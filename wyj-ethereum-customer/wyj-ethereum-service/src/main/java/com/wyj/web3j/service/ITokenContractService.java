package com.wyj.web3j.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert.Unit;

public interface ITokenContractService extends IContractService{

	/**
	 * 获取代币余额
	 * @param address 地址
	 * @return
	 */
	BigInteger getTokenBalance(String address, String fileName, String password,String tokenAddress) throws Exception;
	
	




	boolean transferToken(String toAddress, BigInteger transferQuantity, String fileName, String password,String tokenAddress)
			throws Exception;





}
