package com.exp.ysy.Tools;

public class NativeFir {

    public NativeFir() {

    }

    public native void FirInit(double[] fir);

    public native void DoFir(short[] src, short[] dst);

    public native double DoFir1(double data);

}