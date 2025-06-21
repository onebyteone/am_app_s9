# App S9 - SharedPreferences

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Release](https://img.shields.io/github/v/release/onebyteone/am_app_s9?include_prereleases)](https://github.com/onebyteone/am_app_s9/releases)
[![SDK](https://img.shields.io/badge/SDK-21--34-brightgreen)](#)

Aplicación Android que demuestra el uso básico de SharedPreferences para almacenamiento persistente de datos.

## 📱 Descripción

Esta aplicación implementa un sistema simple de SharedPreferences que permite:
- Guardar y recuperar datos de usuario
- Visualizar contador de visitas
- Almacenar datos de perfil de usuario
- Cambiar de tema a modo oscuro o claro

## 🚀 Características

- **SharedPreferencesHelper**: clase auxiliar que facilita leer y escribir preferencias.
- **Gestión de perfil**: guarda nombre, edad y correo electrónico del usuario.
- **Contador de aperturas**: registra cuántas veces se inició la aplicación.
- **Modo oscuro**: configurable desde la pantalla de preferencias y persistente entre sesiones.
- **Datos persistentes**: toda la información se conserva aun después de cerrar la app.

## 📋 Requisitos

- Android Studio Arctic Fox o superior
- SDK mínimo: API 21 (Android 5.0)
- SDK objetivo: API 34 (Android 14)
- Kotlin 1.9.0

## 🛠️ Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/GxJohan/app_s9.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza el proyecto con Gradle

4. Ejecuta la aplicación en un emulador o dispositivo físico

## 💻 Uso

1. Al abrir la aplicación verás un contador de cuántas veces se inició.
2. Pulsa **Reiniciar contador** para llevar ese valor a cero.
3. Desde el menú de la barra superior accede a **Perfil** para guardar tu información personal.
4. En **Preferencias** puedes activar o desactivar el modo oscuro.
5. La opción **Acerca de** muestra datos del desarrollador.

## 📷 Capturas de Pantalla

<div align="center">
  <img src="https://i.imgur.com/V6nfkWM.png" width="220"/>
  <img src="https://i.imgur.com/IOLMupd.png" width="220"/>
  <img src="https://i.imgur.com/xrG7Yl3.png" width="220"/>
  <img src="https://i.imgur.com/Z5OB7CU.png" width="220"/>
  <img src="https://i.imgur.com/bK1PmhZ.png" width="220"/>
  <img src="https://i.imgur.com/2S9HYih.png" width="220"/>
</div>

## 📂 Estructura del Proyecto

```
app_s9/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── java/com/example/app_s9/
│           │   ├── AboutActivity.kt
│           │   ├── MainActivity.kt
│           │   ├── MainViewModel.kt
│           │   ├── PreferencesActivity.kt
│           │   ├── ProfileActivity.kt
│           │   ├── SharedPreferencesHelper.kt
│           │   └── UserProfile.kt
│           └── res/layout/
│               ├── activity_about.xml
│               ├── activity_main.xml
│               ├── activity_preferences.xml
│               └── activity_profile.xml
├── SharedPreferences_Guide.md
├── build.gradle.kts
└── settings.gradle.kts
```

## 📖 Documentación

Para más detalles sobre la implementación y cómo extender la funcionalidad, consulta [SharedPreferences_Guide.md](SharedPreferences_Guide.md)

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la Licencia MIT.
