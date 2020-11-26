package ai.ssy.common.ras;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

/**
 * @program: cryptogram
 * @description: RSA2测试类
 * @author: ssy
 * @create: 2018-07-03 19:26
 **/
class RSAUtilsCodeTest {
    private static final Logger log = LoggerFactory.getLogger(RSAUtilsCodeTest.class);

    //    private static final String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM/hOFCwBxogUSIh14pRbRQO7LoTDhPk7CEdoKsg8Dm34HY+3T/YntdWzmCHTFCassrEpsZHYCiZrkFb2+UOckBGvf9b2FVXYi16n+QeZ5zTnKuXtLEksnW0tjG2LOr4fB4QXjKhy2TGfJ3S6kRt4qQpu3ewA/4dbvCep0mzHXLLcOkpTknAxCpw2g2dbBOE+hsmQfbuGqokCiJVbKAYlwIk1iQ6In/o05An9r4dPuH+w3nTVX0K8Av3KEogr/vdoQxrl2yioYAWFggGt03IpdnUwQcW+BjaRVZQ8kX9fE/QD4xfABEv+wDHyAlcK2ILCwBza4j4EB5b71h5hbYUxhAgMBAAECggEANDuAUb50qKRESqZ20pYecOEkVi3eJOPnW4AvjdzPMR/uvaAooGuCKIVjFYk2cTSWxJMDc0p4aZI+3parV5EzQCWfLTYlwvqZLGlHIdmaH6+G5HJ4maQqUWdB83JW1YkzuwC5JKIaRvLhVGhrehKDBZbthzi7K+9np+C9iftgqHzoAV/wb7ih8R5pxBfKMwDr/OlnRiMkTPB6X1w3itZJGx4l7WW7csRvFtkWciK+kaOGJ3twvX0vtD/Fog6DXgz4CIcQpdS/I0+E92sSSCDG1TLS+1p00m0DD+q+kG9SrsmwsxVbp4JSks1vJuOp/4VnZSUCyC2zsWlpIwKj72ELXQKBgQD0jHFLbqVAMpI4+JUzCBovD4Dyqf3icmLWNsbVDhLSlGt4WKoA3lQtRobnILGSLuaWru9rBj0OrfziNrdRmC8YS9WSej/+55OVWctTOWruKrsIc8uzyY8Moc8XbqEO8RqmjzhZV6jrkQgrUCNVwr/7s8ReHa6fwziGPHXRyef4NwKBgQCTmD3XpBKYS0BGCFcL4++bVnI5YSvYHF8Y77ZfKNK4Z2u0fiBf5gkgIaJUrsEdWvLcDivdGTK8DLXpekDVVyXCCciI25s6uFMjesQMobBxW77t0BD5srllqPWbpcheKZfaoX/aMrWQGOa1tpSO2RZMVw1QhV0HAISkYltv8BNkJwKBgQDSGYsIpKHi2s/XlHISquZcAjTlxvhxwZZ6tgrzZqjzNEJ03bAJGPo2/73M+sC0eAjx8o+NYom4f/CgpSOwPVBQzm5J7y/mHy0jhkbyVIUy45FWvK2I5CU7VBUo64oB3VDpQmjGEVL2N99N3wbMmon4hMSzm0q4xa9dXLpYp9/c1wKBgERykROFYp5AH8GasouwHHmkoHpdxxCFL9EHskL1Uqv0Qm3FJEx1CPxevMKa/LIZcJF+/PafbfjpyB9L8+88aCVRli9p8NZNjzjgATV+cq5Phv8QxB6YtQQhVC3nt0F79pGM3hcZ9zupYK0azFBm+sFSibky8A6EPf9iIdeKteN3AoGAEUyPDZN7ETREbJJ58E7lfItyFHra1MQlfPjBNX86e2mcF6uxoB7Cz7FGmn95ktR3Va3/FVcUaJkGOo5j4gJPtXBKNxmHAWZCkl0mgouF/Zk7yEd5Ajk/mlluyWNPuDlpwovdjCeeAzSxPW9iScR5Un1N9D3CKPIeejsu86kF3ls=";
    private static final String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCrdySkMLG9OGStwwfKojTpfbK6o87xCjkgmspMfAibCOwdwTWTC8SzWzP2sH02WRzgE3X2NI3QCMF4uAGf2ynLA+IZvwbgZAYf1L4eMGtt9fGp2h5BIffowzoVjRffjWszEusxJNRrYLu7yACde4kGZj5m87pxDgQ6qc5flVBTfJuCuruX4xxSGspfue+Z8fIXewsnM9lwwbNVJ5ZWwrKZyMqkWqcT+AFQj71u3wJij38UhmZ7gRwa6SD3SNRg419ENbQQY6NNcGH8XjFmuyDOotPVKWScWoUxcfO/jaiP8mggT5aqF5jvG0W9SN0Kgzq1OmMI302+r6nGX47snbhXAgMBAAECggEAHvTLsLj53eeohZaIeZT/aIhBFGEuo2XtGm3XyCc8hoD94AFZ4splINx+bTM/5+f+xpW83k5uq/hotZrC8FFpIjX5RByw6SIl2OlXyauEOb4Fe1zdyXSQhX15pxpqDPpDOF1aCO1VTG2SdzyMn+IFwxoCeiPWrUKSkOTHVYpnjirNPhy/YKXkWnVaAYIvPkmY6r/8sYAae+5uKntc7J96gnGb6TUDiuKZS1ZndX3p/aCCflRZkpMNB6xepsRlru4aumPIRIIFDE6toAb6WZrLxJuPyK2CB0CS/QI0GPp1TJTVKVuwpkbtmSliBc9f2wq36qy9CKWBqK8BIocRlng/QQKBgQDep6wNEPxQjrV/BYb2S4lrRoRh/Ts2ZE05QiVxo0JvNr7RZjnj0hjC0N97JmPGO9TlHO7cbmplj6RG7RUq1gZEMFvyivly7Z8uduq1x49eaviv+FqpSyIWstHTrXOHBnvVeg+FZTxGWEze54I0fY53j0egg/WbfCAzAX5rtl75TQKBgQDFJOx22S2nlHOQcJwyPh6iaqXXACccpMEcPWHf/BqBT7yjVENzdw0FxRqmElxvp8zF9QVHx+0Bx3lVjuVUT2lyIY6T0bKuEuJ363l9Vw3fIpjov0SMIkG64SymfiQ1He2e+lqBvB/0mWy1yBJZZW+XlgKEb72ufb4orB+IsiNGMwKBgAkMGSKiyQ3g7qENsXE6mofgYqq0pn5MTxL/jBP1BVMNj21k0P9f3x+zaxFO+wjoD3uL1Gzik7IyxwMWySBnd/O3X1UYrpAr9abmsBzY0guNHzPPTKfKZMU1WO9YAfS/n7KJ9i1+cpeNKPp5v8GwRGS5TrjIA+fggFJyQhfQXkWVAoGAIDCoP7u0fbF6sTIB/x4viFubmqCWwlGQipjnO1iBx22KoqymmtrskG2frVBAMacXg5c/dnoJlAnLIRJFFeAlmpIqIq7R2ySQP/5+Bt5JEI8oopBgk/UgAHC2BrWAGgA6BYRhdN+P5DONEZ2KiXhLoVfIL3HDahN0ElYQjBRyOAsCgYEA0Q72xZOb8r0CMR3AbcJJETmm+Z2OnpY2IN+9gu9GemT4JstCZXEQT0xzFkUqxHzJt9y3cpThVQiSwEbCdfsTV5KfyqxXxH7fNmj+owqaPiRkR68/PIiCnkIvy3iw9bdRQx5KUGMIu/KmO+y8eCz49Okya6wstUnfCFtavzuysO0=";
    //    private static final String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjP4ThQsAcaIFEiIdeKUW0UDuy6Ew4T5OwhHaCrIPA5t+B2Pt0/2J7XVs5gh0xQmrLKxKbGR2Aoma5BW9vlDnJARr3/W9hVV2Itep/kHmec05yrl7SxJLJ1tLYxtizq+HweEF4yoctkxnyd0upEbeKkKbt3sAP+HW7wnqdJsx1yy3DpKU5JwMQqcNoNnWwThPobJkH27hqqJAoiVWygGJcCJNYkOiJ/6NOQJ/a+HT7h/sN501V9CvAL9yhKIK/73aEMa5dsoqGAFhYIBrdNyKXZ1MEHFvgY2kVWUPJF/XxP0A+MXwARL/sAx8gJXCtiCwsAc2uI+BAeW+9YeYW2FMYQIDAQAB";
    private static final String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq3ckpDCxvThkrcMHyqI06X2yuqPO8Qo5IJrKTHwImwjsHcE1kwvEs1sz9rB9Nlkc4BN19jSN0AjBeLgBn9spywPiGb8G4GQGH9S+HjBrbfXxqdoeQSH36MM6FY0X341rMxLrMSTUa2C7u8gAnXuJBmY+ZvO6cQ4EOqnOX5VQU3ybgrq7l+McUhrKX7nvmfHyF3sLJzPZcMGzVSeWVsKymcjKpFqnE/gBUI+9bt8CYo9/FIZme4EcGukg90jUYONfRDW0EGOjTXBh/F4xZrsgzqLT1SlknFqFMXHzv42oj/JoIE+WqheY7xtFvUjdCoM6tTpjCN9Nvq+pxl+O7J24VwIDAQAB";

    public static void main(String[] args) {

        ImmutableMap.Builder<String, String> bizBuilder = ImmutableMap.builder();
        ImmutableMap<String, String> map2 = bizBuilder
                .put("body", "123456")
                .put("subject", "参数能胡")
                .put("out_trade_no", "202805031634738401")
                .put("accept_name", "奥术大师多")
                .put("accept_card", "8937647237823")
                .put("amount", "200.00")
                .build();

        HashMap<String, String> hashMap = Maps.newHashMap(map2);
        //拼接传输参数
        try {

            String bizContent = RSAUtils.getEncodeSignContent(hashMap);

            PrivateKey privateKey = RSAUtils.getPrivateKey(privateKeyStr);
            PublicKey publicKey = RSAUtils.getPublic(publicKeyStr);

            //加签
            String sign = RSAUtils.rsa256Sign(bizContent, privateKey);
            log.info("加签结果:{}", sign);
            //验签
            boolean a = RSAUtils.rsa256Check(bizContent, sign, publicKey);
            log.info("验签结果:{}", a);

            //私钥加密
            String s1 = RSAUtils.encryptByPrivateKey(bizContent, privateKey);
            log.info("私钥加密结果:{}", s1);
            //公钥解密
            String s11 = RSAUtils.decryptByPublicKey(s1, publicKey);
            log.info("公钥解密结果:{}", s11);

            //公钥加密
            String s2 = RSAUtils.encryptByPublicKey(bizContent, publicKey);
            log.info("公钥加密结果:{}", s2);
            //私钥解密
            String s21 = RSAUtils.decryptByPrivateKey(s2, privateKey);
            log.info("私钥解密结果:{}", s21);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
