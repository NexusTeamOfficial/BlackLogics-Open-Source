<!-- ═══════════════════════════════════════════════════════════════════════ -->
<!--              BLACKLOGICS — README.md  (v0.1.5 · Professional)           -->
<!-- ═══════════════════════════════════════════════════════════════════════ -->

<div align="center">

# 🧠 BlackLogics

### The Next-Generation Android IDE — *Design · Wire · Protect · Ship*

**Independent architecture by NexusTeam × Smart India Gaming**

<br/>

[![Version](https://img.shields.io/badge/version-v0.1.5-blueviolet?style=for-the-badge)](CHANGELOG.md)
[![License](https://img.shields.io/badge/license-GPL--3.0-4c1?style=for-the-badge&logo=gnu&logoColor=white)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2021%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white)](#-installation)
[![Status](https://img.shields.io/badge/status-active-success?style=for-the-badge)]()

<br/>

[![Stars](https://img.shields.io/github/stars/NexusTeamOfficial/BlackLogics-Open-Source?style=flat-square&color=yellow&logo=github)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/stargazers)
[![Forks](https://img.shields.io/github/forks/NexusTeamOfficial/BlackLogics-Open-Source?style=flat-square&color=blue&logo=github)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/network/members)
[![Issues](https://img.shields.io/github/issues/NexusTeamOfficial/BlackLogics-Open-Source?style=flat-square&color=red&logo=github)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/issues)
[![PRs](https://img.shields.io/github/issues-pr/NexusTeamOfficial/BlackLogics-Open-Source?style=flat-square&color=brightgreen&logo=github)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/pulls)
[![Contributors](https://img.shields.io/github/contributors/NexusTeamOfficial/BlackLogics-Open-Source?style=flat-square&color=orange&logo=github)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/graphs/contributors)
[![Last Commit](https://img.shields.io/github/last-commit/NexusTeamOfficial/BlackLogics-Open-Source?style=flat-square&logo=github)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/commits)

<br/>

[🚀 Install](#-installation) · [✨ Features](#-key-features) · [🆕 What's New](#-whats-new-in-v015) · [📸 Preview](#-interface-preview) · [🤝 Contribute](#-contributing) · [💬 Telegram](https://t.me/blacklogics)

<br/>

> **⚡ Drag-and-drop simplicity, met with full Android SDK power.**
> Design UI visually → wire logic with blocks → lock releases with ProGuard / AiJiaMi Dex encryption / resource encryption → **export or run.**

</div>

---

## 📑 Table of Contents

- [🆕 What's New in v0.1.5](#-whats-new-in-v015)
- [📖 Overview](#-overview)
- [🎯 Why BlackLogics?](#-why-blacklogics)
- [✨ Key Features](#-key-features)
  - [🧩 Core IDE](#-core-ide)
  - [🛡️ Security Suite](#️-security-suite)
  - [📦 Additional Capabilities](#-additional-capabilities)
- [📸 Interface Preview](#-interface-preview)
- [🚀 Installation](#-installation)
- [📱 Quick Start](#-quick-start)
- [🛠️ Tech Stack](#️-tech-stack)
- [📜 Version History](#-version-history)
- [🤝 Contributing](#-contributing)
- [🗺️ Roadmap](#️-roadmap)
- [❓ FAQ](#-faq)
- [👥 Team](#-team)
- [📜 License](#-license)
- [🔐 Security Policy](#-security-policy)
- [🙏 Acknowledgements](#-acknowledgements)
- [🌐 Connect With Us](#-connect-with-us)

---

## 🆕 What's New in v0.1.5

<div align="center">

**Released: October 2026** · [Full Changelog →](CHANGELOG.md)

</div>

> [!TIP]
> **v0.1.5** is the biggest update yet — **Kotlin DSL export** is live, the **plugin system** is here, and builds are up to **40% faster**. Update now for the smoothest BlackLogics experience.

### 🚀 Headline Features

| Feature | Description |
| :--- | :--- |
| 🎯 **Kotlin DSL Export** | Export any project to `.kts` build files — full plugin management support |
| 🔌 **Plugin System** | Extend the IDE with community plugins — blocks, themes, export targets |
| ⚡ **40% Faster Builds** | New incremental Gradle cache layer |
| 🎨 **Material You** | Dynamic color theming on Android 12+ |
| 🪟 **Multi-Window Support** | Open multiple projects in separate windows |
| 📊 **Recent Projects Dashboard** | Jump back into work instantly |

### 📝 Full Changelog — v0.1.5

<details open>
<summary><b>✨ Added</b></summary>

- ✅ **Kotlin DSL (`.kts`)** export option
- ✅ **Plugin system** with `plugin.json` manifest
- ✅ **Material You** dynamic color theming
- ✅ **Build cache** for faster incremental compiles
- ✅ **Multi-window** project support
- ✅ New **"Recent Projects"** dashboard
- ✅ **SVG icon** import support
- ✅ **ProGuard rule validation** before build

</details>

<details open>
<summary><b>🔧 Improved</b></summary>

- ⚡ 40% faster incremental builds
- 🎨 Smoother animation transitions
- 🖼️ Icon library now supports SVG imports
- 📝 Source viewer syntax highlighting improved
- 🛡️ ProGuard rule validation before build
- 💾 Memory usage reduced by 25% on large projects

</details>

<details>
<summary><b>🐛 Fixed</b></summary>

- Fixed crash on opening large project files
- Fixed resource encryption on API 33+
- Fixed icon import on Android 14
- Fixed duplicate strings after package refactor
- Fixed block editor lag on low-RAM devices

</details>

<details>
<summary><b>⚠️ Breaking Changes</b></summary>

- Old plugin format (`v0.1.0`) is **no longer supported** — migrate to `plugin.json`
- Minimum **JDK 8** still required — **JDK 17 recommended**

</details>

---

## 📖 Overview

**BlackLogics** is an **independent, ground-up IDE architecture** by **NexusTeam (Smart India Gaming)** — it is **not a fork** of Sketchware or any other project. It bridges **visual drag-and-drop development** with the **full Android SDK** so you can build real, shippable Android apps without touching a single line of code — but still drop into raw Java whenever you want.

| 🎨 What you build with | 📦 What you ship |
| :--- | :--- |
| Visual UI designer (drag & drop) | Signed **APK / AAB** |
| Block-based logic editor | **Optimized** release builds |
| Live string & resource rendering | **ProGuard**-protected code |
| Material Components (MD3 + You) | **Dex-encrypted** binaries |
| Built-in source viewer | **Resource-encrypted** APKs |
| Plugin-powered extensions | **Multi-module** projects |

### 🔄 The BlackLogics Workflow

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   🎨 DESIGN  │ ─► │   🧠 WIRE    │ ─► │  🛡️ PROTECT  │ ─► │   📦 SHIP    │
│              │    │              │    │              │    │              │
│ Drag & drop  │    │ Blocks +     │    │ ProGuard +   │    │ APK / AAB    │
│ UI builder   │    │ Java code    │    │ Encryption   │    │ + Export     │
└──────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
```

---

## 🎯 Why BlackLogics?

| ⚡ Compared to traditional IDEs | ✅ BlackLogics |
| :--- | :--- |
| Steep learning curve | **Visual first** — learn by building |
| Manual XML + Java for every screen | **Drag-and-drop** with live preview |
| Setup build.gradle by hand | **Auto-generated** build config |
| No built-in security | **ProGuard + Dex + Resource encryption** one-click |
| Hard to inspect generated code | **Source viewer** always available |
| No plugin ecosystem | **Plugin system** for community extensions 🆕 |

---

## ✨ Key Features

### 🧩 Core IDE

<table>
<tr>
<td width="55%" valign="top">

| Feature | Description |
| :--- | :--- |
| 🏗️ **Independent Architecture** | No Sketchware dependency — pure NexusTeam design |
| 🅰️ **Full AndroidX Support** | Modern support libraries out of the box |
| 🔤 **Live String Rendering** | See strings as you type — no rebuild needed |
| 🎨 **Material You** | Dynamic color on Android 12+ |
| 🧠 **Visual Logic Editor** | Snap-together blocks for complex flows |
| 🖱️ **Drag-and-Drop Designer** | Pixel-accurate UI with live preview |
| 👁️ **Source Code Viewer** | Inspect generated Java anytime |
| 🖼️ **Integrated Icon Library** | Import PNG / SVG, manage & preview |
| 🎯 **Kotlin DSL Export** | Full `.kts` build file generation 🆕 |
| 🔌 **Plugin System** | Community extensions for blocks & themes 🆕 |
| 🪟 **Multi-Window** | Open multiple projects side-by-side 🆕 |
| 📊 **Recent Dashboard** | Jump back into work instantly 🆕 |

</td>
<td width="45%">

<div align="center">

**📊 Project Stats**

<br/><br/>

| Metric | Value |
| :---: | :---: |
| 🧩 Block Types | 250+ |
| 🎨 Widgets | 80+ |
| 🖼️ Icons | 2,000+ |
| 🌐 Languages | 12+ |
| 🔌 Plugins | Growing |

<br/><br/>

**⚡ Speed**

<br/><br/>

| Operation | Time |
| :---: | :---: |
| Incremental Build | ~40% faster |
| Full Build | ~2 min |
| APK Install | ~5 sec |

</div>

</td>
</tr>
</table>

---

### 🛡️ Security Suite

<table>
  <tr>
    <td width="33%" align="center">🛡️<br/><b>ProGuard</b><br/><sub>Shrink & obfuscate</sub></td>
    <td width="33%" align="center">🔑<br/><b>Dex Encryption</b><br/><sub>AiJiaMi Enterprise</sub></td>
    <td width="33%" align="center">🔒<br/><b>Resource Encryption</b><br/><sub>XML · assets · drawables</sub></td>
  </tr>
  <tr>
    <td width="33%" align="center">☕<br/><b>Java & Native</b><br/><sub>JNI support</sub></td>
    <td width="33%" align="center">🎵<br/><b>Assets & Permissions</b><br/><sub>Full manager</sub></td>
    <td width="33%" align="center">📦<br/><b>Package Refactor</b><br/><sub>One-click rename</sub></td>
  </tr>
</table>

<br/>

<details open>
<summary><h3>🛡️ ProGuard Manager — Shrink & Obfuscate</h3></summary>

Rename classes and methods at build time: **smaller APK, harder to reverse.**

- ✅ Custom **`-keep` rules** (reflection, native, serialization)
- ✅ **Deep library mode** — recompile local libraries from source
- ✅ Optional **mapping files** for readable crash reports
- ✅ **Rule validation** before build 🆕
- ✅ Per-module toggle — enable security only where needed

> [!WARNING]
> **Always test a release build** after enabling rules. Wrong `-keep` entries can crash at runtime on real devices.

</details>

<details open>
<summary><h3>🔑 Dex Encryption — AiJiaMi Enterprise</h3></summary>

Runtime protection against reverse engineering and code theft.

| ✅ Benefit | ⚖️ Trade-off |
| :--- | :--- |
| Blocks easy DEX decompilation | APK size may increase |
| Encrypts strings & classes | Slightly slower cold start |
| Anti-debug / anti-tamper | Harder to debug crashes |
| Configurable encryption levels | Requires correct setup |

> [!NOTE]
> **AiJiaMi Enterprise** is an optional runtime protection layer. Enable only for production builds — debug builds should remain unencrypted for easy debugging.

</details>

<details open>
<summary><h3>🔒 Resource Encryption</h3></summary>

Encrypt project XML, assets, and drawables into protected binary formats. Set and store your **encryption key** securely — the IDE remembers it per project.

> [!IMPORTANT]
> Once enabled, resources must be **decrypted** before any manual editing. Back up your key — losing it means losing access.

</details>

---

### 📦 Additional Capabilities

<details>
<summary><b>☕ Java & Native (JNI)</b></summary>

Full Java support with native C/C++ libraries via JNI — compile `.so` files alongside your Java sources.
</details>

<details>
<summary><b>🎵 Assets, Sounds & Permissions</b></summary>

Manage `assets/`, `res/raw/`, and `AndroidManifest.xml` permissions from one unified panel.
</details>

<details>
<summary><b>📦 Package Refactor</b></summary>

One-click rename across the entire project — no more broken references when you reorganize.
</details>

<details>
<summary><b>🔗 Multi-Module Projects</b></summary>

Split large apps into modules — each with its own build config, resources, and ProGuard rules.
</details>

<details>
<summary><b>🌙 Dark & Light Themes</b></summary>

Full theme support including AMOLED mode for OLED displays.
</details>

---

## 📸 Interface Preview

<table>
  <tr>
    <td align="center" width="33%"><sub><b>🎨 VISUAL EDITOR</b></sub></td>
    <td align="center" width="33%"><sub><b>🛡️ PROGUARD</b></sub></td>
    <td align="center" width="33%"><sub><b>🔑 DEX ENCRYPTION</b></sub></td>
  </tr>
  <tr>
    <td><img src="screenshots/Screenshot_20261021_100335.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_120203.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_120210.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center"><sub><b>🔒 RESOURCE ENCRYPTION</b></sub></td>
    <td align="center"><sub><b>🖼️ IMPORT ICON</b></sub></td>
    <td align="center"><sub><b>👁️ SOURCE CODE</b></sub></td>
  </tr>
  <tr>
    <td><img src="screenshots/Screenshot_20261021_120157.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_101421.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_100446.jpg" width="100%"/></td>
  </tr>
</table>

---

## 🚀 Installation

### 📋 Prerequisites

| Component | Requirement | Notes |
| :--- | :--- | :--- |
| ☕ **JDK** | Java 8 or higher | JDK 17 recommended |
| 🤖 **Android SDK** | API 21+ | Android 5.0 Lollipop |
| 🐘 **Build System** | Gradle (wrapper included) | `./gradlew` |
| 💾 **Storage** | ~2 GB free | SDK + build cache |
| 🧠 **RAM** | 4 GB minimum | 8 GB recommended |

### ⚙️ Steps

```bash
# 1️⃣  Clone the repository
git clone https://github.com/NexusTeamOfficial/BlackLogics-Open-Source.git

# 2️⃣  Enter the project folder
cd BlackLogics-Open-Source

# 3️⃣  Sync & build
./gradlew build

# 4️⃣  Install on device / emulator
./gradlew installDebug
```

### 🖥️ Android Studio Method

1. Open **Android Studio** → `File ▸ Open` → select the cloned folder
2. Wait for **Gradle Sync** to complete
3. Connect an **API 21+** device or start an emulator
4. Press **▶ Run**

### 🐧 CLI Only (Linux / macOS)

```bash
# Ensure ANDROID_HOME is set
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools

# Build and install
./gradlew installDebug
```

---

## 📱 Quick Start

```java
// Your first BlackLogics screen — generated Java preview
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn_hello);
        btn.setOnClickListener(v ->
            Toast.makeText(this, "Hello from BlackLogics!", Toast.LENGTH_SHORT).show()
        );
    }
}
```

> [!TIP]
> BlackLogics generates this code **automatically** when you drag a button and wire a toast block. Switch to the **Source Code Viewer** to see it live.

---

## 🛠️ Tech Stack

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Material Design](https://img.shields.io/badge/Material%20Design-757575?style=for-the-badge&logo=materialdesign&logoColor=white)
![XML](https://img.shields.io/badge/XML-FF6600?style=for-the-badge&logo=xml&logoColor=white)

</div>

---

## 📜 Version History

| Version | Date | Highlights |
| :---: | :---: | :--- |
| **v0.1.5** | Oct 2026 | Kotlin DSL · Plugin system · Material You · 40% faster builds |
| v0.1.0 | Jun 2026 | Plugin API preview · Multi-window |
| v0.0.7 | Mar 2026 | Security suite (ProGuard + Dex + Resource encryption) |
| v0.0.1 | Jan 2026 | First public alpha |

> 📖 See [`CHANGELOG.md`](CHANGELOG.md) for the full history.

---

## 🤝 Contributing

We ❤️ contributions of every size — code, docs, translations, bug reports.

### 🔀 Workflow

```bash
# 1️⃣  Fork the repo, then clone your fork
git clone https://github.com/YOUR_USERNAME/BlackLogics-Open-Source.git
cd BlackLogics-Open-Source

# 2️⃣  Create a feature branch
git checkout -b feature/YourFeatureName

# 3️⃣  Make changes and commit
git commit -m "Add: clear, descriptive message"

# 4️⃣  Push and open a PR
git push origin feature/YourFeatureName
```

### 📏 Guidelines

- 📖 Read [`CONTRIBUTING.md`](CONTRIBUTING.md) first
- 🐛 Report bugs via [Issues](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/issues)
- 💡 Discuss features in [Discussions](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/discussions)
- 🎯 **PR description must explain _what_ changed and _why_**
- ✅ Follow existing code style; include tests where possible
- 🌐 Translations welcome — Hindi, Bengali, Tamil, Telugu, Marathi

### 🏷️ Good First Issues

Looking to contribute? Start with issues tagged [`good first issue`](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/labels/good%20first%20issue) — these are curated for new contributors.

---

## 🗺️ Roadmap

| Version | Status | Highlights |
| :---: | :---: | :--- |
| `v0.1.5` | ✅ **Released** | Kotlin DSL · Plugin system · Material You |
| `v0.2.0` | 🚧 **In Progress** | Cloud build service · AAB export · Firebase integration |
| `v0.3.0` | 📋 **Planned** | Built-in emulator · Design templates marketplace |
| `v0.4.0` | 📋 **Planned** | Jetpack Compose export · Kotlin DSL only mode |
| `v1.0.0` | 🔮 **Future** | Stable API · Play Store publishing · Full documentation |

### ✅ v0.1.5 — Shipped

- [x] Kotlin DSL export
- [x] Plugin system
- [x] Material You
- [x] Faster builds (40%)
- [x] Multi-window
- [x] Recent projects dashboard

### 🚧 v0.2.0 — In Progress

- [ ] Cloud build service
- [ ] AAB export
- [ ] Firebase integration
- [ ] AI-assisted block suggestions
- [ ] Advanced debugging tools

---

## ❓ FAQ

<details>
<summary><b>Is BlackLogics a Sketchware fork?</b></summary>
<br/>
No. It is an <b>independent IDE architecture</b> built from the ground up by NexusTeam — no shared code lineage.
</details>

<details>
<summary><b>Which Android versions are supported?</b></summary>
<br/>
Apps built with BlackLogics target <b>API 21+</b> (Android 5.0 Lollipop and later). The IDE itself runs on modern Android.
</details>

<details>
<summary><b>Can I use BlackLogics commercially?</b></summary>
<br/>
The project is <b>GPL-3.0</b> from <code>v0.0.7+</code>. Commercial / closed-source use requires a separate license — see <a href="COMMERCIAL-LICENSE.md"><code>COMMERCIAL-LICENSE.md</code></a>.
</details>

<details>
<summary><b>Why is my encrypted resource not editable?</b></summary>
<br/>
Once <b>resource encryption</b> is enabled, files must be decrypted first — this is by design. Back up your encryption key.
</details>

<details>
<summary><b>Does BlackLogics support Java 17 / Kotlin DSL?</b></summary>
<br/>
Java 8+ is supported. <b>Kotlin DSL export</b> shipped in <b>v0.1.5</b>. JDK 17 is recommended.
</details>

<details>
<summary><b>How do I migrate from the old plugin format?</b></summary>
<br/>
Rename your <code>plugin.xml</code> to <code>plugin.json</code> and update the manifest schema — see <a href="docs/plugin-migration.md"><code>docs/plugin-migration.md</code></a> for the migration guide.
</details>

<details>
<summary><b>How do I report a security vulnerability?</b></summary>
<br/>
Please <b>do not</b> open a public issue. Email <a href="mailto:security@blacklogics.dev">security@blacklogics.dev</a> with details.
</details>

<details>
<summary><b>Where can I get help?</b></summary>
<br/>
Join our <a href="https://t.me/blacklogics">Telegram community</a> or open a <a href="https://github.com/NexusTeamOfficial/BlackLogics-Open-Source/discussions">Discussion</a>.
</details>

---

## 👥 Team

<div align="center">

| <img src="https://github.com/NexusTeamOfficial.png" width="60" alt="NexusTeam"/> | <img src="https://github.com/GauravDeveloper-Android.png" width="60" alt="Gaurav"/> |
| :---: | :---: |
| **NexusTeam** | **Gaurav** |
| Lead Architecture & UI | Android Contributions & Testing |
| [@NexusTeamOfficial](https://github.com/NexusTeamOfficial) | [@GauravDeveloper-Android](https://github.com/GauravDeveloper-Android) |

</div>

### 🙌 Core Maintainers

| Member | Role | Contact |
| :--- | :--- | :--- |
| **NexusTeam** | Architecture & UI | [GitHub](https://github.com/NexusTeamOfficial) |
| **SmartIndiaGaming** | Systems & multimedia | [GitHub](https://github.com/SmartIndiaGaming) |
| **Gaurav** | Android & testing | [GitHub](https://github.com/GauravDeveloper-Android) |

---

## 📜 License

<div align="center">

[![License: GPL v3](https://img.shields.io/badge/License-GPL--3.0-blue?style=for-the-badge&logo=gnu)](LICENSE)

</div>

- **GPL-3.0** applies to `v0.0.7+` — earlier versions were **MIT**
- Full terms → [`LICENSE`](LICENSE)
- Commercial / closed-source licensing → [`COMMERCIAL-LICENSE.md`](COMMERCIAL-LICENSE.md)

---

## 🔐 Security Policy

If you discover a security vulnerability, **please do not open a public issue**.

Instead, email **security@blacklogics.dev** with:
- Steps to reproduce
- Affected version
- Impact assessment

We'll respond within **48 hours** and credit you in the release notes.

---

## 🙏 Acknowledgements

- [Android Open Source Project](https://source.android.com/)
- [Material Design](https://m3.material.io/)
- [Gradle](https://gradle.org/)
- [ProGuard](https://www.guardsquare.com/proguard)
- All our **contributors** and **community members** ❤️

---

## 🌐 Connect With Us

<div align="center">

[![Telegram](https://img.shields.io/badge/Telegram-@BlackLogicsOfficial-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/blacklogics)
[![YouTube](https://img.shields.io/badge/YouTube-@BlackLogics-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://youtube.com/@blacklogics)
[![GitHub](https://img.shields.io/badge/GitHub-NexusTeamOfficial-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source)
[![Email](https://img.shields.io/badge/Email-contact@blacklogics.dev-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:contact@blacklogics.dev)

<br/>

### ⭐ Star History

<a href="https://star-history.com/#NexusTeamOfficial/BlackLogics-Open-Source&Date">
  <img src="https://api.star-history.com/svg?repos=NexusTeamOfficial/BlackLogics-Open-Source&type=Date" alt="Star History Chart" width="600"/>
</a>

<br/><br/>

**Made with ❤️ and ☕ by NexusTeam & SmartIndiaGaming**

⭐ **Star this repo if BlackLogics helps you build faster!**

</div>

<!-- ═══════════════════════════════════════════════════════════════════════ -->
<!--                              END OF README                              -->
<!-- ═══════════════════════════════════════════════════════════════════════ -->
