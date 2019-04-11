package com.bgn.baseframe.utils


import com.orhanobut.logger.Logger

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

import android.util.Base64

import javax.crypto.Cipher

/**
 * Created by 18162 on 2018/3/20.
 */

object RSAUtil {


    //    String str = "{\"email\":\" 18688726694\",\"pwd\":\"e10adc3949ba59abbe56e057f20f883e\",\"ak\":\"62bc02b64c63f26f24109ce48df1fc9b\"}";
    //
    //        LoggerUtil.d("TEST加密前：" + str);
    //    byte[] estr = RSAUtil.encrypt(str.getBytes(), RSAUtil.getPublicKey(), 2048, 11, "RSA/ECB/PKCS1Padding");
    //    String data = new String(estr);
    //
    //    byte[] dstr = RSAUtil.decrypt(estr, RSAUtil.getPrivateKey(), 2048, 11, "RSA/ECB/PKCS1Padding");
    //    String data2 = new String(dstr);
    //        LoggerUtil.d("TEST解密后：" + data2);


    val pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4fsC3Or73/m/Tr1ZWjP7\n" +
            "kQb6Di0qCRnB55Czd8/d1P6MkfZEHHkW4AZ9IXIZ6ZAwi6uB4NsW5nKK/81K76NV\n" +
            "u3Ksk5RlecchQ/vYBjILN5/YL57j+cRUEocAh7RBgRXf1FZfIBhuOOQOMwL1Fio2\n" +
            "zhG6UkddPnAr2OV9h25N8ZLSCwVGDTc5ZTmFRZ4VqT87TgROGJPtVZr4kylHwjTj\n" +
            "t8EuxpqhVv3bgFYTyOZvsP0HimkcHeU4920q8EFDcuP2ZyFPJqW0LPbdjMTWSwOY\n" +
            "CVNYasKUr68QEYsDXzSHasjSBaGw1M8Of9TBD2dT3qFICzbxBEISOCthIdh65mht\n" +
            "RQIDAQAB\n"


    val priKey = "\n" +
            "MIIEpgIBAAKCAQEA4fsC3Or73/m/Tr1ZWjP7kQb6Di0qCRnB55Czd8/d1P6MkfZE\n" +
            "HHkW4AZ9IXIZ6ZAwi6uB4NsW5nKK/81K76NVu3Ksk5RlecchQ/vYBjILN5/YL57j\n" +
            "+cRUEocAh7RBgRXf1FZfIBhuOOQOMwL1Fio2zhG6UkddPnAr2OV9h25N8ZLSCwVG\n" +
            "DTc5ZTmFRZ4VqT87TgROGJPtVZr4kylHwjTjt8EuxpqhVv3bgFYTyOZvsP0Himkc\n" +
            "HeU4920q8EFDcuP2ZyFPJqW0LPbdjMTWSwOYCVNYasKUr68QEYsDXzSHasjSBaGw\n" +
            "1M8Of9TBD2dT3qFICzbxBEISOCthIdh65mhtRQIDAQABAoIBAQCnrtCjCSK5QeL5\n" +
            "FHvW+Te0l8648j583kYKBE+HmhDrWa8JKAnGbvvQZEA5rycXLs029p67MRc/DMOj\n" +
            "qLvwMe0N2LvCDp11zZMblSbpAf1c2xlZzLcxwML5tH9qMxFgLdEXCLgxYq93O5Jl\n" +
            "u84NEmn0Yyc0oovIJwgCO9f3xJvFol2R4tfxeLAHmzbDucmT8lVWDv2tcOOg83O/\n" +
            "4m2R5Xuauo+HxsUYQMtafn4YZXxQ7Bj3GuzVYzlHOPJfknOZCIkdb527WFIPK5p5\n" +
            "lfGzwO8MCam0Hgn/y8E6gMKC/oS0h09lcDs7bH8s4BELr0VuUydNjvFwPRJg22mE\n" +
            "VxaHVL0BAoGBAPCmCo4ltcwYXGSKY+J6SUxtEZnG+qMmEsHgIG3mA3nLJIsgAi5n\n" +
            "fB2UBvTRI/ixcNjV2oSRm2FddGRl0E7qH/fHCwwhHd5izcAjziqz0pRfT8hhykru\n" +
            "koB6ehyG3FxBWXtC2B3+HCeuFoh/QlRVuYxt4FRIKcB5+Y/vu5w+eTXlAoGBAPBl\n" +
            "bg8MASaMHMKR+U5ZK3lmXP2jBegPorh/ecWXUhjHuB4C0BkR0dg6GtVbyEs6rGEO\n" +
            "g05HVk3tvucSMOzkrbR+lNxSXf+Blu3F3VpgH8kWzQwqvyDc0uX0MMQfmzR8DUQ6\n" +
            "EQbf9x9/VzM/W5qBb/vgb163PRpCG2ZqzjJFXuPhAoGBAIVOdWpo2VxF4miPgOVk\n" +
            "eFYt1SIqZ40Y+isLzOsUD7jYH1YyVQL/sHFyHqhfcl3BbZGwZWwqfLhi3KcA/3O3\n" +
            "nh3T1PrTHYijkvZs3zgohO5MHhYSlc0Me3tQXXSQAGHakYcGcFGtAku41CKo3EV7\n" +
            "d12jQDFwCDDnYKerVXIn6V1BAoGBAI6I9WrCiWQbq2nKN3G2+Unv4a8ZnATuUGLo\n" +
            "YoE/qZO7x3Uz4cMbh7qXKRXcZ7QDQ/YALuaY8ZkfIBPN+2haZowgiY7g348BfUXa\n" +
            "DdbqK66FcGqIZe7MP9fMo6WFv/asnwkXDMVwwgvQ6xkdtei0WnuTpTUZnIcXLzvY\n" +
            "lA3Gh+7BAoGBAM0e8rA1nEomA25S1SUfJxGJZveGTL+bg9MU+CFnG/CQ0mHoVnC+\n" +
            "mI3hC83eDfsOvcKlVLKcovONJo1KvhKzYoBoUQJMh4lpc3yprZDA/WmQeiz6S7pK\n" +
            "wUtBkQJMLRsqXCReNC2ym3ROsB6xdOaU7K5JsTEWfNZktlUA1YaHG6ix"


    val publicKey: PublicKey?
        get() {
            var publicKey: PublicKey? = null
            val keyBytes: ByteArray
            try {
                keyBytes = com.bgn.baseframe.utils.Base64.decode(pubKey)
                val keySpec = X509EncodedKeySpec(keyBytes)
                val keyFactory = KeyFactory.getInstance("RSA")
                publicKey = keyFactory.generatePublic(keySpec)
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return publicKey
        }

    val privateKey: PrivateKey?
        get() {
            var privateKey: PrivateKey? = null
            val keyBytes: ByteArray
            try {
                keyBytes = com.bgn.baseframe.utils.Base64.decode(priKey)
                val priPKCS8 = PKCS8EncodedKeySpec(keyBytes)
                val keyFactory = KeyFactory.getInstance("RSA")
                privateKey = keyFactory.generatePrivate(priPKCS8)
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return privateKey
        }

    fun getdecryptString(encryptedString: String): String {
        val dstr = RSAUtil.decrypt(com.bgn.baseframe.utils.Base64.decode(encryptedString), RSAUtil.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding")
        val data = String(dstr)
        Logger.d("TEST解密后：$data")
        return data
    }

    fun decrypt(encryptedBytes: ByteArray, privateKey: PrivateKey?, keyLength: Int, reserveSize: Int, cipherAlgorithm: String): ByteArray {
        val keyByteSize = keyLength / 8
        val decryptBlockSize = keyByteSize - reserveSize
        val nBlock = encryptedBytes.size / keyByteSize
        var outbuf: ByteArrayOutputStream? = null
        try {
            val cipher = Cipher.getInstance(cipherAlgorithm)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)

            outbuf = ByteArrayOutputStream(nBlock * decryptBlockSize)
            var offset = 0
            while (offset < encryptedBytes.size) {
                var inputLen = encryptedBytes.size - offset
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize
                }
                val decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen)
                outbuf.write(decryptedBlock)
                offset += keyByteSize
            }
            outbuf.flush()

        } catch (e: Exception) {
            Logger.d("DEENCRYPT ERROR:", e.message)
        } finally {
            try {
                outbuf?.close()
            } catch (e: Exception) {
                outbuf = null
                Logger.d("CLOSE ByteArrayOutputStream ERROR:", e.message)
            }

        }
        return outbuf!!.toByteArray()
    }

    fun getencrptString(olddataSrring: String): String? {
        Logger.d("TEST加密前：$olddataSrring")
        val estr = RSAUtil.encrypt(olddataSrring.toByteArray(), RSAUtil.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")
        return com.bgn.baseframe.utils.Base64.encode(estr)
    }

    fun encrypt(plainBytes: ByteArray, publicKey: PublicKey?, keyLength: Int, reserveSize: Int, cipherAlgorithm: String): ByteArray {
        val dataString: String? = null
        val keyByteSize = keyLength / 8
        val encryptBlockSize = keyByteSize - reserveSize
        var nBlock = plainBytes.size / encryptBlockSize
        if (plainBytes.size % encryptBlockSize != 0) {
            nBlock += 1
        }
        var outbuf: ByteArrayOutputStream? = null
        try {
            val cipher = Cipher.getInstance(cipherAlgorithm)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)

            outbuf = ByteArrayOutputStream(nBlock * keyByteSize)
            var offset = 0
            while (offset < plainBytes.size) {
                var inputLen = plainBytes.size - offset
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize
                }
                val encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen)
                outbuf.write(encryptedBlock)
                offset += encryptBlockSize
            }
            outbuf.flush()
            return outbuf.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                outbuf?.close()
            } catch (e: Exception) {
                outbuf = null
                e.printStackTrace()
            }

            return outbuf!!.toByteArray()
        }
    }

}
