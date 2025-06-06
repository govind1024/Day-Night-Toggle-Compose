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

https://github.com/govind1024/Day-Night-Toggle-Compose/blob/master/switch_toggle.gif

![ezgif-1d20644442cc9a](https://github.com/user-attachments/assets/4e31430d-b181-41f4-ba34-01552d3c2ec5)               ![ezgif-17232ccac96813](https://github.com/user-attachments/assets/2b0c139d-75a6-4cc4-8693-18363b94fe6c)


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
2. ```bash
   git clone https://github.com/govind1024/Day-Night-Toggle-Compose.git
   
3. Open in Android Studio Hedgehog+
4. Run on device/emulator (API 23+)

---

## **🙌 Contributing / License**

```markdown
## 🙌 Contributing

PRs and suggestions are welcome! Just open an issue or fork the repo 🚀

## 📄 License

This project is open source under the [MIT License](LICENSE).
