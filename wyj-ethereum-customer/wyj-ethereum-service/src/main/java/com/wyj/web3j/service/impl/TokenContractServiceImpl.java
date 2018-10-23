package com.wyj.web3j.service.impl;

import java.io.File;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.alibaba.fastjson.JSONObject;
import com.wyj.web3j.common.client.Web3jClient;
import com.wyj.web3j.service.ITokenContractService;
import com.wyj.web3j.service.generated.HumanStandardToken;

@Service
public class TokenContractServiceImpl implements ITokenContractService {

	
    static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
    static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    
    
	private String walletPath;
    
	@Override
	public BigInteger getTokenBalance(String address,String fileName,String password,String tokenAddress) throws Exception {
		
		System.out.println("walletPath:"+walletPath);
		
        Credentials credentials =
                WalletUtils.loadCredentials(
                		password,
                		walletPath+File.separator+fileName);
		
	     HumanStandardToken contract = getContract(credentials, tokenAddress);
		
        return contract.balanceOf(address).send();
	}



	@Override
	public boolean transferToken(String toAddress, BigInteger transferQuantity,String fileName,String password,String tokenAddress) throws Exception {
        Credentials credentials =
                WalletUtils.loadCredentials(
                		password,
                		walletPath+File.separator+fileName);
		
	     HumanStandardToken contract = getContract(credentials, tokenAddress);
        TransactionReceipt aliceTransferReceipt = contract.transfer(
        		toAddress, transferQuantity).send();
		
        System.out.println("-----------------------------------------------------");

        System.out.println(JSONObject.toJSONString(aliceTransferReceipt));
        
        System.out.println("-----------------------------------------------------");

        
        if(aliceTransferReceipt.getBlockNumber()!=null) {
        	return true;
        }
		return false;
	}
	
	
	private HumanStandardToken getContract(Credentials credentials,String tokenAddress) {
        HumanStandardToken contract = HumanStandardToken.load(tokenAddress,Web3jClient.getClient(),credentials,GAS_PRICE, GAS_LIMIT);
		return contract;
	}
	
	public static void main(String[] args) {}

	
}
