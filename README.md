# 概要

Native Androidで作るGithubリポジトリ検索アプリになります。<br>

# アーキテクチャ
[Wiki](https://github.com/LeoAndo/AndroidGithubSearch/wiki/architecture)にまとめています

# 開発環境
- IDE: Android Studio Electric Eel 2022.1.1
- 動作OSバージョン: OS10.0以上

# アプリ開発時の準備 (重要)
[Wiki](https://github.com/LeoAndo/AndroidGithubSearch/wiki/setup)にまとめています

# デザイン面：アピールポイント
- Themed Iconの適用
- Material3 Themeの適用
- Jetpack Compose対応
- Responsive UI対応
- Dark Theme対応

# 機能面:アピールポイント
- Paging対応

# capture (Resizable Emulator API 33): 正常系

<strong>R8 / Proguard適用済みのapkで動作確認しています。</strong>

| Compact | Medium | Expanded |
|:---|:---:| :---:|
|<img src="https://user-images.githubusercontent.com/16476224/209779668-36390bc8-6d24-495d-9401-2bc9c0ac1b27.gif" height=891 width=411 /> | <img src="https://user-images.githubusercontent.com/16476224/209779932-81a8319c-ae17-479b-953e-5845451e857e.gif" height=841 width=673 /> | <img src="https://user-images.githubusercontent.com/16476224/209780283-64a005d9-b2d9-464a-8517-9b6858a74f45.gif" height=800 width=1280 /> |

# capture (Resizable Emulator API 33): 異常系 (機内モードON)

| 通信エラー | 復帰 |
|:---| :---:|
|<img src="https://user-images.githubusercontent.com/16476224/209837018-6db80528-412b-484a-9b63-8f6817a56a2b.gif" height=891 width=411 /> | <img src="https://user-images.githubusercontent.com/16476224/209837032-1ecc630c-3735-472b-9af7-ffd15690c38f.gif" height=891 width=411 /> |

# capture (Resizable Emulator API 33): ダークテーマ

<img src="https://user-images.githubusercontent.com/16476224/209815267-7ba8ee81-4430-4d82-a5a9-3500e1473462.png" height=891 width=411/>
