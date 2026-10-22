
package com.github.megatronking.stringfog;

public interface IStringFog {

    byte[] encrypt(String data, byte[] key);

    String decrypt(byte[] data, byte[] key);

    boolean shouldFog(String data);

}
