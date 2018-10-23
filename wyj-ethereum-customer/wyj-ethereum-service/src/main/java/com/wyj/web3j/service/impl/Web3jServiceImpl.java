package com.wyj.web3j.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

import com.wyj.web3j.common.client.Web3jClient;
import com.wyj.web3j.common.util.FileUtils;
import com.wyj.web3j.service.IWeb3jService;

@Service
public class Web3jServiceImpl implements IWeb3jService {

	//创建钱包接口
	//获取钱包信息接口
	//以太币转移接口
	//代币转移接口 	需要有专门的合约接口，建议创建新接口类
	//获取以太坊余额接口
	private String walletPath;
	
	
	@Override
	public String creataWallet(String password) throws Exception {
		String fileName = WalletUtils.generateNewWalletFile(password, new File(walletPath), true);
        Credentials credentials =
                WalletUtils.loadCredentials(
                		password,
                		walletPath+File.separator+fileName);
		return credentials.getAddress();
	}

	@Override
	public Map<String,String>  creataWalletMap(String password) throws Exception {
		String fileName = WalletUtils.generateNewWalletFile(password, new File(walletPath), true);
        Credentials credentials =
                WalletUtils.loadCredentials(
                		password,
                		walletPath+File.separator+fileName);
        Map<String,String> map = new HashMap<String,String>();
        map.put("address", credentials.getAddress());
        map.put("fileName", fileName);
        ECKeyPair keyPair = credentials.getEcKeyPair();
        map.put("privateKey",String.valueOf(keyPair==null?"":keyPair.getPrivateKey().toString(16)));
		return map;
	}
	
	@Override
	public BigInteger getEthBalance(String address) throws Exception{
		System.out.println("walletPath:"+walletPath);
		
		EthGetBalance ethGetBalance = Web3jClient.getClient().ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
		return ethGetBalance.getBalance();
	}

	@Override
	public boolean sendEth(String address,String source,String password, BigDecimal value, Unit unit) throws Exception{

        TransactionReceipt transferReceipt = Transfer.sendFunds(
        		Web3jClient.getClient(),  WalletUtils.loadCredentials(
                		password,
                		source),
        		address,  // you can put any address here
                value, unit)  // 1 wei = 10^-18 Ether
                .send();
        if(transferReceipt.getTransactionHash()!=null&&transferReceipt.getTransactionHash().trim()!="") {
        	return true;
        }
		return false;
	}

	
	public void downLoad(String fileName, HttpServletResponse response) throws Exception {
		FileUtils.downLoad(walletPath+File.separator+fileName, response, false);
	}
	
	public static void main(String[] args) {}
}
