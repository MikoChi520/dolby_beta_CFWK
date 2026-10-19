# 杜比大喇叭β版

[![Release](https://img.shields.io/github/v/release/NikolaJyun/dolby_beta?label=Release)](https://github.com/NikolaJyun/dolby_beta/releases/latest)
[![License](https://img.shields.io/github/license/NikolaJyun/dolby_beta?label=License)](https://choosealicense.com/licenses/mit/)

网易云音乐音源代理模块。工作原理为**音源替换而非破解**，单曲付费与无版权歌曲有几率匹配错误，真心支持歌手请付费。

网易云音乐音源代理模块。工作原理为**音源替换而非破解**，单曲付费与无版权歌曲有几率匹配错误，真心支持歌手请付费。

网易云音乐音源代理模块。工作原理为**音源替换而非破解**，单曲付费与无版权歌曲有几率匹配错误，真心支持歌手请付费。

当前版本 **4.1.0**（fork 自 [luoxingran/dolby_beta](https://github.com/luoxingran/dolby_beta)，原作 [nining377/dolby_beta](https://github.com/nining377/dolby_beta)）。

安装后请在 LSPosed 勾选网易云，并**强制停止**后再打开。设置入口在网易云「设置」页。脚本异常时可在模块里使用「重新释放脚本」。已在网易云 **9.5.70** 验证。

## Cloudflare Worker 模式（本 fork）

「音源代理设置」新增 **Worker API 模式**。开启后模块不再启动本地 Node 脚本，也不设置 HTTP 代理或 TLS 中间人，而是在网易云拿到播放/下载响应后直接请求 Worker：

```text
https://你的-worker.workers.dev/api/song/enhance/player/url?ids=[歌曲ID]
```

默认地址为 `https://music.bro.us.ci`，可在「Worker 地址」中修改。关闭该开关并重启网易云即可回到原来的代理模式。Worker 端需要部署本项目 `server/` 或仓库根目录的单文件 `worker.js`。

<img src="image/setting_rn.png" width="30%"> <img src="image/proxy_settings.png" width="30%"> <img src="image/about_ncm.png" width="30%">

## 4.1.0 更新

- 内嵌 [UnblockNeteaseMusic/server](https://github.com/UnblockNeteaseMusic/server) `enhanced` 最新脚本（v0.28.0）

[下载 Release](https://github.com/NikolaJyun/dolby_beta/releases/latest)

## 打赏

如果这个模块对你有帮助，欢迎打赏支持。

<img src="image/donate_wechat.png" width="40%"> <img src="image/donate_alipay.png" width="40%">

## 致谢

[UnblockNeteaseMusic/server](https://github.com/UnblockNeteaseMusic/server)

[nondanee/UnblockNeteaseMusic](https://github.com/nondanee/UnblockNeteaseMusic)

[luoxingran/dolby_beta](https://github.com/luoxingran/dolby_beta)

[nining377/dolby_beta](https://github.com/nining377/dolby_beta)
