package com.resources;

/**
 * Created by ysy on 2014/12/23.
 */
public class FIR {
    private int length, count = 0;
    private double[] delayLine;
    private double[] impResp;

    public FIR(double[] coefs) {
        length = coefs.length;
        impResp = coefs;
        delayLine = new double[length];
    }

    public double getOutputSample(double inputSample) {
        delayLine[count] = inputSample;
        double result = 0.0;
        int index = count;
        for (int i = 0; i < length; i++) {
            result += impResp[i] * delayLine[index--];
            if (index < 0) index = length - 1;
        }
        if (++count >= length) count = 0;
        return result;
    }
}