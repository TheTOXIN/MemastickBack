package com.memastick.backmem.main.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.BLOCK_DFCLT;

public class CryptoUtil {

    public static String hashSHA256(String data) {
        return Hashing.sha256()
            .hashString(data, StandardCharsets.UTF_8)
            .toString();
    }

    public static String profString() {
        return new String(new char[BLOCK_DFCLT]).replace('\0', '0');
    }
}
