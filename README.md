<div align="center">

<img src="assets/banner.svg" alt="BlackLogics" width="100%" />

<br/>

<a href="#-installation"><img src="assets/b-install.svg" alt="Install" height="40"/></a>
<a href="#-key-features"><img src="assets/b-features.svg" alt="Features" height="40"/></a>
<a href="#-interface-preview"><img src="assets/b-preview.svg" alt="Preview" height="40"/></a>
<a href="#-contributing"><img src="assets/b-contribute.svg" alt="Contribute" height="40"/></a>

</div>

<br/>

<img src="assets/h-overview.svg" alt="Overview" width="100%" />

**BlackLogics** is not a fork. It is an independent IDE architecture by **NexusTeam (Smart India Gaming)**. It bridges drag-and-drop simplicity with the full Android SDK: design UI visually, wire logic with blocks, protect release builds with ProGuard / AiJiaMi Dex encryption / resource encryption, then export or run.

<div align="center">
<img src="assets/workflow.svg" alt="Design, Wire, Protect, Ship" width="100%" />
</div>

<br/>

<img src="assets/h-features.svg" alt="Key Features" width="100%" />

<table>
<tr><td><img src="assets/f1.svg" width="100%" alt="Independent architecture"/></td><td><img src="assets/f2.svg" width="100%" alt="Full AndroidX support"/></td></tr>
<tr><td><img src="assets/f3.svg" width="100%" alt="Live string rendering"/></td><td><img src="assets/f4.svg" width="100%" alt="Material components"/></td></tr>
<tr><td><img src="assets/f5.svg" width="100%" alt="Visual logic editor"/></td><td><img src="assets/f6.svg" width="100%" alt="Drag-and-drop designer"/></td></tr>
<tr><td><img src="assets/f7.svg" width="100%" alt="Source code viewer"/></td><td><img src="assets/f8.svg" width="100%" alt="Icon library"/></td></tr>
</table>

<br/>

<img src="assets/h-security.svg" alt="Security Suite" width="100%" />

<table>
<tr><td><img src="assets/s1.svg" width="100%" alt="ProGuard Manager"/></td><td><img src="assets/s2.svg" width="100%" alt="Dex Encryption"/></td></tr>
<tr><td><img src="assets/s3.svg" width="100%" alt="Resource Encryption"/></td><td><img src="assets/s4.svg" width="100%" alt="Java and Native"/></td></tr>
<tr><td><img src="assets/s5.svg" width="100%" alt="Assets, sounds, permissions"/></td><td><img src="assets/s6.svg" width="100%" alt="Package Refactor"/></td></tr>
</table>

<br/>

### 🛡️ ProGuard Manager

Turn on protection so classes and methods are renamed at build time: smaller APK, harder to reverse.

- Custom keep rules (reflection, native, serialization)
- Deep library mode (recompile local libs from source)
- Optional mapping files for crash debugging

> [!WARNING]
> Test a **release** build after enabling rules. Wrong keeps can crash at runtime.

### 🔑 Dex Encryption — *AiJiaMi Enterprise*

Runtime protection against reverse engineering and code theft.

| ✅ Benefit | ⚖️ Trade-off |
| :--- | :--- |
| Blocks easy Dex decompilation | APK size can grow |
| Encrypts strings & classes | Startup may be slower |
| Anti-debug / anti-tamper | Harder to debug crashes |
| Configurable encryption | Needs correct setup |

### 🔒 Resource Encryption

Encrypt project XML, assets, and drawables into protected formats. Set and store an **encryption key** securely.

> [!IMPORTANT]
> Once enabled, resources must be **decrypted** before manual edits.

<br/>

<img src="assets/h-preview.svg" alt="Interface Preview" width="100%" />

<table>
  <tr>
    <td align="center" width="33%"><sub><b>VISUAL EDITOR</b></sub></td>
    <td align="center" width="33%"><sub><b>PROGUARD</b></sub></td>
    <td align="center" width="33%"><sub><b>DEX ENCRYPTION</b></sub></td>
  </tr>
  <tr>
    <td><img src="screenshots/Screenshot_20261021_100335.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_120203.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_120210.jpg" width="100%"/></td>
  </tr>
  <tr>
    <td align="center"><sub><b>RESOURCE ENCRYPTION</b></sub></td>
    <td align="center"><sub><b>IMPORT ICON</b></sub></td>
    <td align="center"><sub><b>SOURCE CODE</b></sub></td>
  </tr>
  <tr>
    <td><img src="screenshots/Screenshot_20261021_120157.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_101421.jpg" width="100%"/></td>
    <td><img src="screenshots/Screenshot_20261021_100446.jpg" width="100%"/></td>
  </tr>
</table>

<br/>

<img src="assets/h-install.svg" alt="Installation" width="100%" />

| Component | Requirement |
| :--- | :--- |
| ☕ **JDK** | Java 8+ |
| 🤖 **Android SDK** | API 21+ |
| 🐘 **Build** | Gradle |

```bash
git clone https://github.com/NexusTeamOfficial/BlackLogics-Open-Source.git
```

Open in Android Studio (or your environment), sync Gradle, then run on an API 21+ device or emulator.

<br/>

<img src="assets/h-contribute.svg" alt="Contributing" width="100%" />

```bash
git clone https://github.com/YOUR_USERNAME/BlackLogics-Open-Source.git
git checkout -b feature/YourFeatureName
# ... changes ...
git commit -m "Add: clear description"
git push origin feature/YourFeatureName
```

Open a Pull Request with a short description of **what** changed and **why**.

<br/>

<img src="assets/h-team.svg" alt="Team and License" width="100%" />

| Member | Role |
| :--- | :--- |
| **NexusTeam** | Lead architecture & UI |
| **SmartIndiaGaming** | Systems & multimedia |
| **[Gaurav](https://github.com/GauravDeveloper-Android)** | Android contributions & testing |

**License:** GPL-3.0 for `v0.0.7+` (earlier versions were MIT). See [`LICENSE`](LICENSE). Commercial / closed-source needs: see [`COMMERCIAL-LICENSE.md`](COMMERCIAL-LICENSE.md).

<br/>

<img src="assets/h-connect.svg" alt="Connect" width="100%" />

<div align="center">

[![Telegram](https://img.shields.io/badge/Telegram-@BlackLogicsOfficial-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/blacklogics)
[![YouTube](https://img.shields.io/badge/YouTube-@BlackLogics-FF0000?style=for-the-badge&logo=youtube&logoColor=white)](https://youtube.com/@blacklogics)
[![GitHub](https://img.shields.io/badge/GitHub-NexusTeamOfficial-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/NexusTeamOfficial/BlackLogics-Open-Source)

<sub>Made with passion by <b>NexusTeam</b> & <b>SmartIndiaGaming</b></sub>

</div>
