package com.resources;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ysy on 2015/2/28.
 */
public class VIR extends Activity{



    public static double[] DoVir(double inputL, double inputR) {

//        Add_C=0.57*(inputL+inputR);
//        Add_C_2=(inputL+inputR);
//        Sub1_2=(inputL-inputR);
//        FIR3_OUT=filter(FIR3,1,Add_C_2);
//        FIR4_OUT=filter(FIR4,1,Sub1_2);
//        Mixer_out=(Delay(42)+FIR3_OUT)*10^(-3/20);
//        Delay=[Add_C,Delay(1:41)];
//        outputL=Mixer_out+FIR4_OUT;
//        outputR=Mixer_out-FIR4_OUT;
        double[] result = new double[2];

        double Add_C = 0.57 * (inputL + inputR);
        double Add_C_2 = (inputL + inputR);
        double Sub1_2 = (inputL - inputR);

        return result;

    }
}
