package com.italo.a32mask;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL); l.setPadding(32,48,32,32);
        TextView t = new TextView(this);
        t.setText("A32 Screen Mask\n\n1. Activa el servicio de accesibilidad de esta app.\n2. Vuelve aquí.\n3. La app cubrirá de negro las zonas dañadas sin bloquear los toques.\n\nEsta primera versión NO escala ni mueve aplicaciones: solo oculta las zonas dañadas.\n\nPara tu configuración actual, usa primero:\nadb shell wm size 810x2400");
        t.setTextSize(18); l.addView(t);
        TextView b1 = new TextView(this); b1.setText("\n  ABRIR AJUSTES DE ACCESIBILIDAD"); b1.setTextSize(18); b1.setPadding(0,24,0,24);
        b1.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        l.addView(b1); setContentView(l);
    }
}
