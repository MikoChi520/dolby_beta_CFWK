# 杜比大喇叭β版 · Worker API

[![Release](https://img.shields.io/github/v/release/MikoChi520/dolby_beta_CFWK?label=Release)](https://github.com/MikoChi520/dolby_beta_CFWK/releases/latest)
[![License](https://img.shields.io/github/license/MikoChi520/dolby_beta_CFWK?label=License)](https://choosealicense.com/licenses/mit/)

网易云音乐音源替换模块。当前 fork **只保留 Cloudflare Worker API 模式**，不再包含本地 Node 脚本、HTTP 代理、CONNECT 或 TLS 中间人。

> **Vibecoding 产物说明**：本项目包含 AI / vibecoding 生成的代码、配置与文档，未经过完整安全审计和全平台测试。请自行判断是否使用，备份重要数据并自行承担风险。
>
> **自定义音源说明**：自定义音源没有做全面测试，不同的搜索接口、鉴权方式、字段路径和媒体地址可能存在差异；请自行小范围验证后再长期使用。

> 音源替换而非破解。单曲付费与无版权歌曲可能匹配错误，真心支持歌手请付费。

## 工作原理

模块在网易云拿到播放/下载响应后，把网易云歌曲 ID 发给 Cloudflare Worker：

```text
https://你的-worker/api/song/enhance/player/url?ids=[歌曲ID]
```

Worker 会先用这个 ID 查询网易云元数据（歌名、歌手、专辑、时长），再按音源分别处理：

- **pyncmd**：直接把网易云 ID 传给第三方 API。
- **netease**：直接用网易云 ID 调 oiapi。
- **qq**：用歌名和歌手搜索 QQ 音乐，再按时长做近似匹配。
- **custom**：用同一组关键词请求自定义搜索接口，按时长排序；媒体请求使用该源返回的 `songid`/`songmid`，不是网易云 ID。

所以只有 pyncmd 和 netease 是“网易云 ID 直接对应”；QQ 和自定义源是“先搜元数据、再按时长匹配”，可能匹配到翻唱、Live 或错误版本。

Worker 返回可用的音频地址后，模块只替换播放地址，不修改网易云账号数据，也不代理音频流量。

## 安装

1. 安装 APK，在 LSPosed 中勾选网易云作用域。
2. 打开网易云 → 设置 → **Worker API 设置**，打开开关并填写 Worker 地址。
3. 强制停止网易云，再重新打开。

Worker 地址默认为空，需要填写自己的地址，例如：

```text
https://music.nix.de5.net
```

设置中的地址可随时修改。

## Worker 端

仓库根目录的 `worker.js` 是**单文件 Worker**，已经内嵌监控页和全部 API，不需要额外部署前端，也不需要 Node 或 Assets 绑定。

它使用两个独立的 KV：

| 绑定名 | 用途 |
| --- | --- |
| `music_cache` | 音频缓存 + 最近一次返回记录，最近记录只保留 24 小时 |
| `custom_ms` | 右上角齿轮里保存的自定义音源 |

不要再使用旧的 `UNM_CACHE`。旧的 `UNM_CACHE` 数据不会自动迁移，自定义源需要在新的设置页重新保存一次。

### 方式一：Cloudflare 控制台部署

1. 登录 [Cloudflare Dashboard](https://dash.cloudflare.com/) → **Workers & Pages**。
2. 进入 **KV**，创建两个命名空间：
   - `music_cache`
   - `custom_ms`
3. 回到 **Workers & Pages** → **Create** → **Worker**，新建一个 Worker。
4. 打开该 Worker 的 **Settings → Bindings → Add binding → KV namespace**，添加：
   - Variable name: `music_cache`，选择 `music_cache` 命名空间
   - Variable name: `custom_ms`，选择 `custom_ms` 命名空间

   **Variable name 必须和上面完全一致。**
5. 打开 **Edit code**，删掉模板代码，把本仓库根目录的 `worker.js` 全部粘贴进去。
6. 点击 **Deploy**。
7. 访问 Worker 的 `https://<worker>.<subdomain>.workers.dev/`，能看到监控页即部署成功。
8. 如果要用自己的域名，在 Worker 的 **Settings → Domains & Routes** 里绑定自定义域名。

### 方式二：Wrangler 部署

在仓库根目录创建 `wrangler.toml`：

```toml
name = "neo-unblock-netease-music"
main = "worker.js"
compatibility_date = "2025-08-01"

[[kv_namespaces]]
binding = "music_cache"
id = "替换成 music_cache 的 id"

[[kv_namespaces]]
binding = "custom_ms"
id = "替换成 custom_ms 的 id"
```

创建 KV 并部署：

```bash
npx wrangler kv namespace create music_cache
npx wrangler kv namespace create custom_ms
npx wrangler deploy
```

### 配置到 Android 模块

Worker 部署好后，复制它的地址，例如：

```text
https://music.example.com
```

在网易云 → 设置 → **Worker API 设置** 中：

1. 打开 **Worker API 模式**
2. 在 **Worker 地址** 填入上面的地址，不要带末尾 `/`
3. 点击 **保存并重启**，或强制停止网易云后重新打开

### 验证

```bash
# 健康检查
curl https://你的-worker/health

# 直接测试一首歌
curl 'https://你的-worker/match?id=347230'
```

健康检查应返回 `providers` 中包含 `pyncmd`、`qq`、`netease`、`custom`。

### 更新 Worker

重新生成 `worker.js` 后，只需要在 Cloudflare 控制台 **Edit code** 中整段替换并重新 **Deploy**。KV 数据不会丢失，不需要重新绑定。

## 构建 Android APK

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
- [NikolaJyun/dolby_beta](https://github.com/NikolaJyun/dolby_beta)
