# 🌓 Animated Theme Toggle – Jetpack Compose

This Android app demonstrates a reusable and animated theme toggle button, inspired by Telegram's UI behavior. Built entirely in Jetpack Compose, it supports:

- System dark mode integration
- Circular reveal/conceal animation
- Switch toggle button
- Ripple FX on press
- Optional sound FX
- Clean and modern UI

---

## 📽 Demo

https://github.com/govind1024/Day-Night-Toggle-Compose/blob/master/preview_toggle.gif
![image](https://github.com/user-attachments/assets/55ddedc8-7558-426b-8807-b5e112d90147)

https://github.com/govind1024/Day-Night-Toggle-Compose/blob/master/switch_toggle.gif
![image](https://github.com/user-attachments/assets/9a39fb57-750c-4ace-8395-15bae4e08e31)



---

## 🚀 Features

- 🌗 Toggle between Light and Dark modes with animation
- 🎯 Ripple effect from the toggle icon center
- 🔊 Optional sound FX when toggling
- 🔁 Smooth circular reveal/conceal animation using `Canvas` and `Animatable`
- 📦 100% Jetpack Compose, no legacy Views
- 🧩 Easy to reuse across screens

---

## 🛠 Tech Stack

| Tech | Purpose |
|------|---------|
| 🧱 Jetpack Compose | Modern declarative UI |
| 🎨 Material3 | Theme & color management |
| 🔄 `Animatable` & `Canvas` | Custom animation drawing |
| 📡 `Modifier.onGloballyPositioned` | Calculate animation origin |
| 🔊 `MediaPlayer` | Built-in sound FX |
| ☀️ `isSystemInDarkTheme()` | Detect system theme |

---

## 📦 Getting Started

1. **Clone the repository**

   ```bash
   git clone https://github.com/govind1024/Day-Night-Toggle-Compose.git
