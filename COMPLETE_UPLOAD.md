# Full archive on this repo

## Source tree
Extracted sources under `edgerunners-mod/` (Java, gradle, lang, models, texture `.b64`).

## Original ZIP (bit-perfect)
Your full `edgerunners-mod-v0_3.zip` is being uploaded as base64 parts under `zip_b64/`.

```bash
bash decode_assets.sh
# or:
cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
unzip edgerunners-mod-v0_3.zip
```

Textures stored as `*.png.b64` — `decode_assets.sh` restores real `.png` files.

Repo: https://github.com/pordonbanan-cpu/Cyberpunk
