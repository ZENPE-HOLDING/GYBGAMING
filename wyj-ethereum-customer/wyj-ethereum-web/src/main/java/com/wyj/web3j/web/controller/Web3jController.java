package com.wyj.web3j.web.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.utils.Convert.Unit;

import com.alibaba.fastjson.JSON;
import com.wyj.web3j.common.json.ReturnJson;
import com.wyj.web3j.service.ITokenContractService;
import com.wyj.web3j.service.IWeb3jService;


@Controller
@RequestMapping("/web3j")
public class Web3jController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeb3jService web3jService;
	
	@Autowired
	private ITokenContractService tokenContractService;
	
	

	@RequestMapping(value = "/createWallet")
	@ResponseBody
	private ReturnJson<String> createWallet(String password) {
		logger.info("createWallet入参："+String.format("password=%s", password));
		ReturnJson<String>  result = new ReturnJson<String>();
		try {
			result.setResult( web3jService.creataWallet(password));
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		logger.info("createWallet出参："+String.format("result=%s", JSON.toJSONString(result)));
		return result;
	}
	
	
	@RequestMapping(value = "/createWalletMap")
	@ResponseBody
	private ReturnJson<Map<String,String>> createWalletMap(String password) {
		logger.info("createWalletMap入参："+String.format("password=%s", password));
		ReturnJson<Map<String,String>>  result = new ReturnJson<Map<String,String>>();
		try {
			result.setResult( web3jService.creataWalletMap(password));
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		logger.info("createWalletMap出参："+String.format("result=%s", JSON.toJSONString(result)));
		return result;
	}
	
	
	
	@RequestMapping(value = "/getEthBalance")
	@ResponseBody
	private ReturnJson<BigInteger> getEthBalance(String address) {
		logger.info("getEthBalance入参："+String.format("address=%s", address));
		ReturnJson<BigInteger>  result = new ReturnJson<BigInteger>();
		try {
			result.setResult( web3jService.getEthBalance(address));
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		logger.info("getEthBalance出参："+String.format("result=%s", JSON.toJSONString(result)));
		return result;
	}

	/**
	 * 以位为单位转账
	 * @param address
	 * @param source
	 * @param password
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/sendEth")
	@ResponseBody
	private ReturnJson<Boolean> sendEth(String address,String source,String password, BigDecimal value) {
		logger.info("sendEth入参："+String.format("address=%s，source=%s，password=%s，value=%s", address,source,password,value));
		ReturnJson<Boolean>  result = new ReturnJson<Boolean>();
		try {
			result.setResult( web3jService.sendEth(address, source, password, value,Unit.WEI));
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		logger.info("sendEth出参："+String.format("result=%s", JSON.toJSONString(result)));
		return result;
	}
	
	@RequestMapping(value = "/downLoad")
	@ResponseBody
	public ReturnJson<String> downLoad(String fileName, HttpServletResponse response) throws Exception {
		logger.info("downLoad入参："+String.format("fileName=%s", fileName));
		ReturnJson<String>  result = new ReturnJson<String>();
		try {
			web3jService.downLoad(fileName, response);
			result.setResult("下载成功");
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	
//-----------------------------代币------------------------------------------------
	
	
	
	@RequestMapping(value = "/getTokenBalance")
	@ResponseBody
	private ReturnJson<BigInteger> getTokenBalance(String address,String fileName,String password,String tokenAddress) {
		logger.info("getTokenBalance入参："+String.format("address=%s，fileName=%s，password=%s", address,fileName,password));
		ReturnJson<BigInteger>  result = new ReturnJson<BigInteger>();
		try {
			result.setResult( tokenContractService.getTokenBalance(address,fileName,password,tokenAddress));
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		logger.info("getTokenBalance出参："+String.format("result=%s", JSON.toJSONString(result)));
		return result;
	}

	
	@RequestMapping(value = "/transferToken")
	@ResponseBody
	private ReturnJson<Boolean> transferToken(String toAddress, BigInteger transferQuantity,String fileName,String password,String tokenAddress) {
		logger.info("transferToken入参："+String.format("address=%s，transferQuantity=%s，fileName=%s，password=%s", toAddress,transferQuantity,fileName,password));
		ReturnJson<Boolean>  result = new ReturnJson<Boolean>();
		try {
			result.setResult( tokenContractService.transferToken(toAddress,transferQuantity,fileName,password,tokenAddress));
		} catch (Throwable e) {
			result.setCode("0001");
			result.setErrMsg(e.getMessage());
			result.setSuccess(false);
		}
		logger.info("transferToken出参："+String.format("result=%s", JSON.toJSONString(result)));
		return result;
	}
	
}
