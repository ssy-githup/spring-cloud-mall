package ai.ssy.common.ras;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @description: RSA2 加解密工具类
 * @author: ssy
 * @create: 2018-07-03 19:26
 **/
public class RSAUtils {
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;
    /**
     * RSA最大加密大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 默认编码
     */
    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * RSA2算法
     */
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    /**
     * RSA算法
     */
    private static final String ALGORITHM = "RSA";
    private static final String ENCRYPT_ALGORITHM = "RSA/ECB/PKCS1Padding";


    /**
     * 公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, PrivateKey privateKey) throws Exception {
        Cipher ci = Cipher.getInstance(ENCRYPT_ALGORITHM);
        ci.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = data.getBytes();
        int inputLen = bytes.length;
        int offLen = 0;//偏移量
        int i = 0;
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > MAX_ENCRYPT_BLOCK) {
                cache = ci.doFinal(bytes, offLen, MAX_ENCRYPT_BLOCK);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            bops.write(cache);
            i++;
            offLen = MAX_ENCRYPT_BLOCK * i;
        }
        bops.close();
        byte[] encryptedData = bops.toByteArray();
        String encodeToString = Base64.getEncoder().encodeToString(encryptedData);
        return encodeToString;
    }


    /**
     * 私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, PublicKey aPublic) throws Exception {
        Cipher ci = Cipher.getInstance(ENCRYPT_ALGORITHM);
        ci.init(Cipher.DECRYPT_MODE, aPublic);
        byte[] bytes = Base64.getDecoder().decode(data);
        int inputLen = bytes.length;
        int offLen = 0;
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > MAX_DECRYPT_BLOCK) {
                cache = ci.doFinal(bytes, offLen, MAX_DECRYPT_BLOCK);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            byteArrayOutputStream.write(cache);
            i++;
            offLen = MAX_DECRYPT_BLOCK * i;

        }
        byteArrayOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(byteArray);
    }


    /**
     * 公钥加密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, PublicKey publicKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(content.getBytes());

            return Base64.getEncoder().encodeToString(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            throw e;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 获取公钥方法
     *
     * @param publicKeyStr 公钥字符串
     * @return
     * @throws Exception
     */
    public static PublicKey getPublic(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 私钥解密
     *
     * @param cipherStr 密文数据
     * @throws Exception 解密过程中的异常信息
     * @return明文
     */
    public static String decryptByPrivateKey(String cipherStr, PrivateKey privateKey) throws Exception {
        byte[] cipherData = Base64.getDecoder().decode(cipherStr);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 获取私钥方法
     *
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }


    /**
     * RSA2 加签
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String rsa256Sign(String content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        signature.initSign(privateKey);
        signature.update(content.getBytes(DEFAULT_CHARSET));
        byte[] signed = signature.sign();
        return Base64.getEncoder().encodeToString(signed);
    }

    /**
     * RSA2 验签
     *
     * @param content 验签内容
     * @param sign    签名
     * @return
     * @throws Exception
     */
    public static boolean rsa256Check(String content, String sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        signature.initVerify(publicKey);
        signature.update(content.getBytes(DEFAULT_CHARSET));
        byte[] decode = Base64.getDecoder().decode(sign.getBytes());
        return signature.verify(decode);

    }


    /**
     * 同 PHP http_build_query方法,并过滤签名key
     *
     * @param sortedParams 需要处理的参数map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getEncodeSignContent(Map<String, String> sortedParams) throws
            UnsupportedEncodingException {
        StringBuffer content = new StringBuffer();
        sortedParams.remove("sign");
        List<String> keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (int i = 0; i < keys.size(); ++i) {
            String publicKey = keys.get(i);
            String value = sortedParams.get(publicKey);
            if (RSAUtils.areNotEmpty(new String[]{publicKey, value})) {
                value = URLEncoder.encode(value, "utf-8");
                content.append((index == 0 ? "" : "&") + publicKey + "=" + value);
                ++index;
            }
        }
        return content.toString();
    }


    /**
     * 检查指定的字符串是否为空。
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
}
