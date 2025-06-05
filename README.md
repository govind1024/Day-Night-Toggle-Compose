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

https://github.com/govind1024/Day-Night-Toggle-Compose/preview_toggle.gif
https://github.com/govind1024/Day-Night-Toggle-Compose/assets/demo-preview2.gif

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
