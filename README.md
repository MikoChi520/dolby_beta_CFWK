# 杜比大喇叭β版 · Worker API

[![Release](https://img.shields.io/github/v/release/MikoChi520/dolby_beta_CFWK?label=Release)](https://github.com/MikoChi520/dolby_beta_CFWK/releases/latest)
[![License](https://img.shields.io/github/license/MikoChi520/dolby_beta_CFWK?label=License)](https://choosealicense.com/licenses/mit/)

网易云音乐音源替换模块。当前 fork **只保留 Cloudflare Worker API 模式**，不再包含本地 Node 脚本、HTTP 代理、CONNECT 或 TLS 中间人。

> 音源替换而非破解。单曲付费与无版权歌曲可能匹配错误，真心支持歌手请付费。

## 工作原理

模块在网易云拿到播放/下载响应后，把歌曲 ID 发给 Cloudflare Worker：

```text
https://你的-worker/api/song/enhance/player/url?ids=[歌曲ID]
```

Worker 返回可用的音频地址后，模块只替换播放地址，不修改网易云账号数据，也不代理音频流量。

## 安装

1. 安装 APK，在 LSPosed 中勾选网易云作用域。
2. 打开网易云 → 设置 → **Worker API 设置**，确认开关已开启并填写 Worker 地址。
3. 强制停止网易云，再重新打开。

默认 Worker 地址：

```text
https://music.nix.de5.net
```

可在设置中改成自己的地址。

## Worker 端

Worker 需要部署配套的 Cloudflare Worker（根目录单文件 `worker.js`，或对应 `server/` 工程）。KV 绑定变量名为 `UNM_CACHE`，Cookie 通过 KV 管理。

## 构建

仓库通过 GitHub Actions 构建：

```text
Actions → Android CI → Run workflow
```

构建产物在 `app-release` artifact 中。

## 说明

- 不包含本地脚本和服务器代理模式。
- 不包含脚本本地 VIP 和自助打卡。
- 云盘歌曲保持原始 URL，只替换普通播放/下载地址。
- 某首歌只播放 2.6 秒时，通常是之前失败尝试留下的试听缓存，清除网易云缓存后重试。

## 致谢

- [UnblockNeteaseMusic/server](https://github.com/UnblockNeteaseMusic/server)
- [nondanee/UnblockNeteaseMusic](https://github.com/nondanee/UnblockNeteaseMusic)
- [luoxingran/dolby_beta](https://github.com/luoxingran/dolby_beta)
- [nining377/dolby_beta](https://github.com/nining377/dolby_beta)
