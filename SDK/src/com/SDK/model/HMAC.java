package com.SDK.model;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
    static public byte[] calcHmacSha1(byte[] secretKey, byte[] message) {
        byte[] hmacSha1 = null;
        try {
          Mac mac = Mac.getInstance("HmacSHA1");
          SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA1");
          mac.init(secretKeySpec);
          hmacSha1 = mac.doFinal(message);
        } catch (Exception e) {
          throw new RuntimeException("Failed to calculate hmac-sha1", e);
        }
        return hmacSha1;
      }
}
