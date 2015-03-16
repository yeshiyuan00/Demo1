package com.exp.ysy.Tools;

public class NativeFir1 {

    public NativeFir1() {

    }

    public native void FirInit(double[] fir);

    public native void DoFir(short[] src, short[] dst);

    public native double DoFir1(double data);

}