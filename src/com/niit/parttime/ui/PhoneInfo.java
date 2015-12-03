package com.niit.parttime.ui;

import android.content.Context; 
import android.telephony.TelephonyManager; 
public class PhoneInfo {
	/**
     * �����ƶ��û�ʶ����
     */ 
	private TelephonyManager telephonyManager; 
    private Context cxt; 
    public PhoneInfo(Context context) { 
        cxt=context;        
        telephonyManager = (TelephonyManager) context 
                .getSystemService(Context.TELEPHONY_SERVICE); 
    } 
    
    public String getNativePhoneIMSI()
    {
    	TelephonyManager tm = (TelephonyManager)cxt.getSystemService(Context.TELEPHONY_SERVICE); 
    	return tm.getSubscriberId();
    }
}
