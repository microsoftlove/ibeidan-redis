/*
package com.ibeidan.client;

import com.ibeidan.utils.pojo.OAuth2Authentication;
import com.ibeidan.utils.pojo.ProtoStuffUtils;
import io.lettuce.core.codec.RedisCodec;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

*/
/**
 * @author lee
 * DATE 2021/3/29 14:31
 *//*

public class PbSerializedObjectCodec<String,V> implements RedisCodec<String,V> {
    private Charset charset = StandardCharsets.UTF_8;
    @Override
    public java.lang.String decodeKey(ByteBuffer bytes) {
        return charset.decode(bytes);
    }

    @Override
    public V decodeValue(ByteBuffer bytes) {

        byte[] v = getBytes(bytes);
        return ProtoStuffUtils.deserialize(v,OAuth2Authentication.class);

    }

    @Override
    public ByteBuffer encodeKey(java.lang.String key) {
        byte[] keys =  ProtoStuffUtils.serialize(key);
        return ByteBuffer.wrap(keys);
    }

    @Override
    public ByteBuffer encodeValue(V value) {
        byte[] v =  ProtoStuffUtils.serialize(value);
        return ByteBuffer.wrap(v);
    }
    private static final byte[] EMPTY = new byte[0];
    private static byte[] getBytes(ByteBuffer buffer) {

        int remaining = buffer.remaining();

        if (remaining == 0) {
            return EMPTY;
        }

        byte[] b = new byte[remaining];
        buffer.get(b);
        return b;
    }

}
*/
