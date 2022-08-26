package com.bitcamp.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStream implements AutoCloseable{
  FileOutputStream out;

  public DataOutputStream(FileOutputStream out) {
    this.out = out;
  }

  @Override
  public void close() throws IOException {
    out.close();
  }

  public void writeByte(byte value) throws Exception {
    out.write(value);
  }                               

  public void writeShort(short value) throws Exception {
    out.write(value >> 8);
    out.write(value);
  }

  public void writeInt(int value) throws Exception {
    out.write(value >> 24);
    out.write(value >> 16);
    out.write(value >> 8);
    out.write(value);
  }

  public void writeLong(long value) throws Exception {
    out.write((int)(value >> 56));
    out.write((int)(value >> 48));
    out.write((int)(value >> 40));
    out.write((int)(value >> 32));
    out.write((int)(value >> 24));
    out.write((int)(value >> 16));
    out.write((int)(value >> 8));
    out.write((int)(value));
  }

  public void writeFloat(float value) throws Exception {
    int temp = Float.floatToIntBits(value);
    writeInt(temp);
  }

  public void writeDouble(double value) throws Exception {
    long temp = Double.doubleToLongBits(value);
    writeLong(temp);
  }

  public void writeBoolean(boolean value) throws Exception {
    out.write(value ? 1 : 0);
  }

  public void writeUTF(String str) throws Exception {
    byte[] bytes = str.getBytes("UTF-8");

    out.write(bytes.length >> 24);
    out.write(bytes.length >> 16);
    out.write(bytes.length >> 8);
    out.write(bytes.length);
    out.write(bytes);
  }
}
