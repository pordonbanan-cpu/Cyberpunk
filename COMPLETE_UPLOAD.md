# Full archive on this repo

## Source tree
Extracted sources under `edgerunners-mod/` (almost all Java, gradle, lang, models, texture `.b64`).

**Java present:** Edgerunners, ModGameEvents, all network, registry, cyber, effect (incl. SandevistanEffect), item (CyberwareItem, AttributeImplantItem, SandevistanItem), client (Afterimages, ClientModEvents, ClientGameEvents, SandevistanLayer; SandevistanMesh placeholder — full geometry is in the original ZIP).

**Still best recovered from original ZIP:** full `CyberwareScreen.java`, full `SandevistanMesh.java` (float array), `blockbench/sandevistan.bbmodel` + `preview_sandevistan.png`, entity texture (already as .b64).

## Original ZIP (bit-perfect)
Your full `edgerunners-mod-v0_3.zip` is uploaded as base64 under `zip_b64/`.

```bash
bash decode_assets.sh
# or:
cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
unzip edgerunners-mod-v0_3.zip
```

Textures stored as `*.png.b64` — `decode_assets.sh` restores real `.png` files.

Repo: https://github.com/pordonbanan-cpu/Cyberpunk

If any part is missing, re-run the upload or download the original zip you provided and place it here.
