# A32 Screen Mask

Proyecto Android mínimo para Galaxy A32 / Android 13.

## Compilar con GitHub Actions

1. Sube todo el contenido de este proyecto a un repositorio de GitHub.
2. Ve a **Actions**.
3. Selecciona **Build APK**.
4. Pulsa **Run workflow**.
5. Cuando termine, abre la ejecución.
6. En **Artifacts**, descarga `A32ScreenMask-debug`.
7. Descomprime el ZIP y obtendrás `app-debug.apk`.

## Instalar por ADB

Desde el Mac:

```bash
adb install app-debug.apk
```

Después activa el servicio en:

Ajustes → Accesibilidad → Servicios instalados → A32 Screen Mask.
