# 概要

Native Androidで作るGithubリポジトリ検索アプリになります。<br>

# 開発環境
- IDE: Android Studio Dolphin 2021.3.1 Patch 1
- 動作OSバージョン: OS10.0以上

# アプリ開発時の準備 (重要)
秘匿情報の管理を`local.properties`で行っています。<br>
[fine-grained personal access token作成手順](https://docs.github.com/ja/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#fine-grained-personal-access-token-%E3%81%AE%E4%BD%9C%E6%88%90) に沿ってaccess tokenを作成し、以下の11行目の通り設定値を追加してください。
<img src= "https://user-images.githubusercontent.com/16476224/208823985-4d0fa8ec-ef81-48de-94e7-8552728e304a.png" />

# デザイン面：アピールポイント
- Themed Iconの適用
- Material3 Themeの適用
- Jetpack Compose対応
- Responsive UI対応

# 機能面:アピールポイント
- Paging対応

# capture (Resisable Emulator API 33): 正常系

| Compact | Medium | Expanded |
|:---|:---:| :---:|
|<img src="https://user-images.githubusercontent.com/16476224/209779668-36390bc8-6d24-495d-9401-2bc9c0ac1b27.gif" height=891 width=411 /> | <img src="https://user-images.githubusercontent.com/16476224/209779932-81a8319c-ae17-479b-953e-5845451e857e.gif" height=841 width=673 /> | <img src="https://user-images.githubusercontent.com/16476224/209780283-64a005d9-b2d9-464a-8517-9b6858a74f45.gif" height=800 width=1280 /> |

# capture (Resisable Emulator API 33): 異常系 (機内モードON)

| 通信エラー | 復帰 |
|:---| :---:|
|<img src="https://user-images.githubusercontent.com/16476224/209780622-e42e338f-d18d-467e-aba0-9aa70af65d65.png" height=891 width=411 /> | <img src="https://user-images.githubusercontent.com/16476224/209780951-e9655d68-1f2f-4b2a-974c-853f94047737.gif" height=891 width=411 /> |