# A32 Screen Mask V2

Primera prueba de V2 para Galaxy A32 / Android 13.

Esta versión usa MediaProjection para capturar la pantalla y una ventana de accesibilidad para mostrar una copia reducida en la zona sana. También intenta traducir los toques mediante AccessibilityService.

## Prueba recomendada

Mantén:

```bash
adb shell wm size 810x2400
adb shell wm density 420
```

Instala la APK y activa el servicio de accesibilidad. Luego abre la app y pulsa **INICIAR V2**. Acepta el diálogo de captura de pantalla.

### Importante

Esta es una V2 experimental. MediaProjection puede no capturar determinadas ventanas protegidas (`FLAG_SECURE`) y la captura puede variar según One UI. Si aparece una imagen negra, parpadeo o efecto espejo, detén V2 y vuelve a la V1.

## GitHub Actions

Ve a Actions → Build APK → Run workflow. El artefacto será `A32ScreenMask-V2-debug`.
