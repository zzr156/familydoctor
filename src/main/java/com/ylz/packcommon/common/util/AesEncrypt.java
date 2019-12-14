package com.ylz.packcommon.common.util;

public class AesEncrypt {
	private static final String IV = "98516D446AF418112933CC64F9A98C05";
	private static final String SALT = "1D2F0B381038C6E1";
	private static final int KEY_SIZE = 128;
	private static final int ITERATION_COUNT = 1;
	private String passPhrase;
	private AesUtil util;

	public AesEncrypt(String passPhrase) {
		this.passPhrase = passPhrase;
		this.util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
	}

	public String encrypt(String plainText) {
		String encrypt = util.encrypt(SALT, IV, passPhrase, plainText);
		return AesUtil.hex(AesUtil.base64(encrypt));
	}

	public String decrypt(String cipherText) {
		cipherText = AesUtil.base64(AesUtil.hex(cipherText));
		String decrypt = util.decrypt(SALT, IV, passPhrase, cipherText);
		return decrypt;
	}
	
}
