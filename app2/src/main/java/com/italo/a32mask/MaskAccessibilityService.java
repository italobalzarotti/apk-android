package com.italo.a32mask;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.content.res.Configuration;

public class MaskAccessibilityService extends AccessibilityService {
    private WindowManager wm;
    private View left, bottom;
    private int lastRotation = -1;

    @Override public void onServiceConnected() { super.onServiceConnected(); wm=(WindowManager)getSystemService(WINDOW_SERVICE); rebuild(); }
    @Override public void onAccessibilityEvent(AccessibilityEvent e) { }
    @Override public void onInterrupt() { }
    @Override public void onConfigurationChanged(Configuration c) { super.onConfigurationChanged(c); rebuild(); }

    private int rotation() { return wm.getDefaultDisplay().getRotation(); }
    private View black() { View v=new View(this); v.setBackgroundColor(Color.BLACK); v.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO); return v; }
    private WindowManager.LayoutParams lp(int w,int h,int gravity) {
        int type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        return new WindowManager.LayoutParams(w,h,type,flags,PixelFormat.OPAQUE);
    }
    private void safeRemove(View v){ if(v!=null) try{wm.removeView(v);}catch(Exception ignored){} }

    private void rebuild() {
        if(wm==null) return;
        safeRemove(left); safeRemove(bottom); left=null; bottom=null;
        Display d=wm.getDefaultDisplay(); android.util.DisplayMetrics m=new android.util.DisplayMetrics(); d.getRealMetrics(m);
        int W=m.widthPixels, H=m.heightPixels;
        int stripX=Math.max(1, Math.round(W*0.25f));
        int stripY=Math.max(1, Math.round(H*0.125f));
        int r=rotation();
        // Damage described by the user in portrait: left ~25% and bottom ~12.5%.
        // After rotation, those physical damaged edges rotate too.
        if(r==SurfaceRotation.ROTATION_0){
            left=black(); WindowManager.LayoutParams a=lp(stripX,H,Gravity.LEFT|Gravity.TOP); a.gravity=Gravity.LEFT|Gravity.TOP; wm.addView(left,a);
            bottom=black(); WindowManager.LayoutParams b=lp(W-stripX,H==0?1:stripY,Gravity.LEFT|Gravity.BOTTOM); b.gravity=Gravity.LEFT|Gravity.BOTTOM; wm.addView(bottom,b);
        } else if(r==SurfaceRotation.ROTATION_90){
            // Portrait bottom -> landscape left; portrait left -> landscape bottom.
            left=black(); WindowManager.LayoutParams a=lp(stripY,H,Gravity.LEFT|Gravity.TOP); a.gravity=Gravity.LEFT|Gravity.TOP; wm.addView(left,a);
            bottom=black(); WindowManager.LayoutParams b=lp(W,stripX,Gravity.LEFT|Gravity.BOTTOM); b.gravity=Gravity.LEFT|Gravity.BOTTOM; wm.addView(bottom,b);
        } else if(r==SurfaceRotation.ROTATION_180){
            left=black(); WindowManager.LayoutParams a=lp(stripX,H,Gravity.RIGHT|Gravity.TOP); a.gravity=Gravity.RIGHT|Gravity.TOP; wm.addView(left,a);
            bottom=black(); WindowManager.LayoutParams b=lp(W-stripX,stripY,Gravity.LEFT|Gravity.TOP); b.gravity=Gravity.LEFT|Gravity.TOP; wm.addView(bottom,b);
        } else {
            // Portrait bottom -> landscape right; portrait left -> landscape top.
            left=black(); WindowManager.LayoutParams a=lp(stripY,H,Gravity.RIGHT|Gravity.TOP); a.gravity=Gravity.RIGHT|Gravity.TOP; wm.addView(left,a);
            bottom=black(); WindowManager.LayoutParams b=lp(W,stripX,Gravity.LEFT|Gravity.TOP); b.gravity=Gravity.LEFT|Gravity.TOP; wm.addView(bottom,b);
        }
    }
    private static final class SurfaceRotation { static final int ROTATION_0=0, ROTATION_90=1, ROTATION_180=2, ROTATION_270=3; }
}
