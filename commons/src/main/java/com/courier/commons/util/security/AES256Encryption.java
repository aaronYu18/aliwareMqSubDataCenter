package com.courier.commons.util.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

/**
 * AES算法加密解密工具类
 * 
 * @author Alex
 * 
 * @since 2014-10-08
 */
public class AES256Encryption {

	private static Logger logger = LoggerFactory.getLogger(AES256Encryption.class);
	
	private static final String KEY_ALGORITHM = "AES";
	private static final String CHARSET = "UTF-8";
	/**
	 * 加密、解密算法/工作模式/填充方式
	 * 
	 * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
	 * 
	 */
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
	/**
	 * 密钥填充字符28位
	 */
	private static final String DEFAULT_CONSTANT = "0000000000000000000000000000";

//	static Cipher cipher;
	
//	private static void initCipher(){
//		// 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		try {
//			cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
//		} catch (NoSuchAlgorithmException e) {
//			logger.error("获取加密实例Cipher失败！", e.getMessage());
//		} catch (NoSuchProviderException e) {
//			logger.error("获取加密实例Cipher失败！", e.getMessage());
//		} catch (NoSuchPaddingException e) {
//			logger.error("获取加密实例Cipher失败！", e.getMessage());
//		}
//	}

	private static Cipher getCipher(){
		// 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		} catch (NoSuchAlgorithmException e) {
			logger.error("获取加密实例Cipher失败！", e.getMessage());
		} catch (NoSuchProviderException e) {
			logger.error("获取加密实例Cipher失败！", e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error("获取加密实例Cipher失败！", e.getMessage());
		}
		return cipher;
	}



	/**
	 * 根据密文+密钥解密
	 * 
	 * @param encryptStr	
	 * 					密文
	 * @param authCode		
	 * 					密钥
	 * @return string
	 * 				解密后字符串
	 * @throws Exception
	 */
	public static String decryptByStr(String encryptStr, String authCode) throws Exception{
		byte [] encryptBytes = parseHexStr2Byte(encryptStr);
		if(encryptBytes == null) return null;

		byte [] decryptBytes = decrypt(encryptBytes, authCode);
		if(decryptBytes == null) return null;

		return new String(decryptBytes);
	}
	
	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param password
	 *            密钥
	 * @return byte[] 加密后的数据
	 */
	public static byte[] encrypt(byte[] data, String password) throws Exception {
		// 还原密钥
		Key k = generateKey(password);
//		if(cipher == null){
//			initCipher();
//		}
		// 初始化，设置为加密模式
		Cipher cipher = getCipher();
		if(cipher != null) {
			cipher.init(Cipher.ENCRYPT_MODE, k);
			// 执行操作
			return cipher.doFinal(data);
		}
		return null;
	}
	/**
	 * 加密数据
	 */
	public static String encrypt(String minwen, String password) throws Exception {
		byte[] data = encrypt(minwen.getBytes(CHARSET), password);
		return parseByte2HexStr(data);
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param password
	 *            密钥
	 * @return byte[] 解密后的数据
	 */
	public static byte[] decrypt(byte[] data, String password) throws Exception {
		// 欢迎密钥
		Key k = generateKey(password);
//		if(cipher == null){
//			initCipher();
//		}
		Cipher cipher = getCipher();
		if(cipher != null) {
			// 初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, k);
			// 执行操作
			return cipher.doFinal(data);
		}
		return null;
	}

	/**
	 * 转换密钥
	 * 
	 * @param password
	 *            二进制密钥
	 * @return Key 密钥
	 * 
	 */
	public static Key generateKey(String password) throws Exception {

		byte[] data = null;

		if (password == null) {
			password = "0000";
		}

		data = (password + DEFAULT_CONSTANT).getBytes(CHARSET);

		return new SecretKeySpec(data, KEY_ALGORITHM);
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws java.io.UnsupportedEncodingException
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String result = AES256Encryption.encrypt("000000", "9048");
		System.out.println(result);

		byte[] bytes = Base64.decodeBase64("MjZDODU4MDk1NjFBNTU5RTBEREI5RTFDNjYzMUIxQjA2QjkyQUQ4QTA5N0UxQzFENjM1NEE0RTREOTc2N0EwRDJFMDQ5NzlDMEM2OTcxRkYxNTJCQkM4RDMzQjQ1NzUyRDNCQUQwMTJDMkJGN0RCNzc4M0MyMDI0QTdBNUUyMjU5OEEwQzFENzQyQ0JDMTA1NTJERUVBMTg1OTMxN0RDNEU1NEJFQjBGRjg2MDNDMDQ5M0NFQjJBMUQzREI1Qjk4RjA4RURBNjEzRjNGMEFFRUNDRURGNERGQjNENzlDNEJGRTgxMjBBQURDN0U1NkQyNkQ2MzRFQjQ4RkRFNzU0M0JFMjlGRDY3NUZCRjQyMTg4MEQ5Q0VDNTBFODNFRDcyQjM5OTU4NUZBMENBNUJEQjg3RDI2NEMxQTkxQjhDRDBFNTM4Mzg1NzY5NTBCQTY4NzNGNzg2QTE5NEQxMzY3REE1NTVCREQ2QzhCOTIxNzkzQkVBRThEOTlCRjkzNjFF".getBytes("UTF-8"));
		String jiemi = AES256Encryption.decryptByStr(new String(bytes), "9048");
		System.out.println(jiemi);

	}
}