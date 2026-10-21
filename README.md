<div align="center">

<img src="https://img.shields.io/badge/version-0.0.7+-black?style=for-the-badge&labelColor=111111&color=00d4ff" />
<img src="https://img.shields.io/badge/license-GPL--3.0-black?style=for-the-badge&labelColor=111111&color=9b59b6" />
<img src="https://img.shields.io/badge/platform-Android%20SDK%2021+-black?style=for-the-badge&labelColor=111111&color=2ecc71" />
<img src="https://img.shields.io/badge/open%20source-NexusTeam-black?style=for-the-badge&labelColor=111111&color=e74c3c" />

<br/><br/>

# ◼ BlackLogics

### Advanced Visual Integrated Development Environment

**A next-generation, open-source IDE for modern Android development.**  
Built from the ground up by **NexusTeam** — visual block logic, full AndroidX, and a professional security suite (ProGuard, Dex encryption, resource encryption).

<br/>

[📦 Clone & Install](#️-installation) · [✨ Features](#-key-features) · [📸 Screenshots](#-interface-preview) · [🤝 Contribute](#-contributing)

---

</div>

## ✦ Overview

**BlackLogics** is not a fork — it is an independent IDE architecture by **NexusTeam (Smart India Gaming)**. It bridges drag-and-drop simplicity with the full Android SDK: design UI visually, wire logic with blocks, protect release builds with ProGuard / AiJiaMi Dex encryption / resource encryption, then export or run.

---

## 🚀 Key Features

### 🛠️ Professional Development Suite

- **Independent architecture** — Custom core by NexusTeam, not a Sketchware fork.
- **Full AndroidX support** — Jetpack-ready projects and modern SDK targets.
- **Live string rendering** — `@string/...` resolves in the visual editor.
- **Material components** — App bars, navigation, dialogs, and a rich widget palette.

### 🧩 Logic & UI Engine

- **Visual logic editor** — Block-based behaviour without hand-written boilerplate.
- **Drag-and-drop UI designer** — Layouts, widgets, and XML side-by-side.
- **Source code viewer** — Inspect generated / project XML anytime.
- **Icon library** — Import outline / filled / round / sharp Material icons with color control.

### 📦 Resource & Security Managers

| Manager | What it does |
| :--- | :--- |
| **ProGuard Manager** | Code shrinking & obfuscation, custom keep rules, deep library mode, mapping files |
| **Dex Encryption (AiJiaMi)** | Enterprise Dex protection — anti-decompile, string/class encrypt, anti-debug |
| **Resource Encryption** | Encrypt XML, assets, and drawables; key-based unlock for edits |
| **Java & Native (JNI)** | Custom classes and native libs |
| **Assets, sounds, permissions** | Streamlined project resources and manifest toggles |

### 🚀 Package Refactor

Safely rename the application package with automatic import and directory updates.

---

### 🔐 ProGuard Manager

Turn on protection so classes and methods are renamed at build time — smaller APK, harder to reverse.

- Custom keep rules (reflection, native, serialization)
- Deep library mode (recompile local libs from source)
- Optional mapping files for crash debugging

> ⚠️ Test a **release** build after enabling rules. Wrong keeps can crash at runtime.

![ProGuard Manager](screenshots/proguard_manager.jpg)

---

### 🔐 Dex Encryption — *AiJiaMi Enterprise*

Runtime protection against reverse engineering and code theft.

| Benefit | Trade-off |
| :--- | :--- |
| Blocks easy Dex decompilation | APK size can grow |
| Encrypts strings & classes | Startup may be slower |
| Anti-debug / anti-tamper | Harder to debug crashes |
| Configurable encryption | Needs correct setup |

![Dex Encryption](screenshots/dex_encryption.jpg)

---

### 🔒 Resource Encryption

Encrypt project XML, assets, and drawables into protected formats. Set and store an **encryption key** securely.

> ⚠️ Once enabled, resources must be **decrypted** before manual edits.

![Resource Encryption](screenshots/resource_encryption.jpg)

---

### 🎨 Visual UI Editor & Icons

Build screens with Linear / Relative / Constraint / Motion layouts, live preview, and a full icon importer (outline, filled, round, sharp).

![Visual UI Editor](screenshots/visual_editor.jpg)

![Import Icon](screenshots/import_icon.jpg)

![Source Code Viewer](screenshots/source_code_viewer.jpg)

---

## 📸 Interface Preview

<table>
  <tr>
    <td align="center"><b>Visual editor</b></td>
    <td align="center"><b>ProGuard</b></td>
    <td align="center"><b>Dex encryption</b></td>
  </tr>
  <tr>
    <td><img src="screenshots/visual_editor.jpg" width="100%"/></td>
    <td><img src="screenshots/proguard_manager.jpg" width="100%"/></td>
    <td><img src="screenshots/dex_encryption.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center"><b>Resource encryption</b></td>
    <td align="center"><b>Import icon</b></td>
    <td align="center"><b>Source code</b></td>
  </tr>
  <tr>
    <td><img src="screenshots/resource_encryption.jpg" width="100%"/></td>
    <td><img src="screenshots/import_icon.jpg" width="100%"/></td>
    <td><img src="screenshots/source_code_viewer.jpg" width="100%"/></td>
  </tr>
</table>

---

## ⚙️ Installation

| Component | Requirement |
| :--- | :--- |
| **JDK** | Java 8+ |
| **Android SDK** | API 21+ |
| **Build** | Gradle |

```bash
git clone https://github.com/NexusTeamOfficial/BlackLogics-Open-Source.git
```

Open in Android Studio (or your environment), sync Gradle, run on API 21+ device/emulator.

---

## 🤝 Contributing

```bash
git clone https://github.com/YOUR_USERNAME/BlackLogics-Open-Source.git
git checkout -b feature/YourFeatureName
# ... changes ...
git commit -m "Add: clear description"
git push origin feature/YourFeatureName
```

Open a Pull Request with a short description of **what** and **why**.

---

## 👥 Team

| Member | Role |
| :--- | :--- |
| **NexusTeam** | Lead architecture & UI |
| **SmartIndiaGaming** | Systems & multimedia |
| **[Gaurav](https://github.com/GauravDeveloper-Android)** | Android contributions & testing |

---

## 📄 License

**GPL-3.0** for `v0.0.7+` (earlier versions were MIT). See [`LICENSE`](LICENSE).

Commercial / closed-source needs: see [`COMMERCIAL-LICENSE.md`](COMMERCIAL-LICENSE.md).

---

## 🔗 Connect

<div align="center">

[![Telegram](https://img.shields.io/badge/Telegram-@BlackLogicsOfficial-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/blacklogics)
[![YouTube](https://img.shields.io/badge/YouTube-@BlackLogics-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://youtube.com/@blacklogics)
[![GitHub](https://img.shields.io/badge/GitHub-NexusTeamOfficial-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source)

*Made with passion by **NexusTeam** & **SmartIndiaGaming***

</div>
