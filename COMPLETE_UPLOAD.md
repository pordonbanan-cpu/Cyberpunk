# Full archive on this repo

## Source tree
Extracted sources under `edgerunners-mod/` (almost all Java, gradle, lang, models, texture `.b64`).

**Present:**
- All main Java packages (Edgerunners, cyber, effect, item, network, registry, client: Afterimages, Client*, SandevistanLayer)
- CyberwareScreen and full SandevistanMesh are best taken from the original ZIP (API size limits truncated some large generated files)
- blockbench/sandevistan.bbmodel (model structure)
- All small textures as exact `.png.b64`
- entity/sandevistan.png.b64 (full)

## Original ZIP (bit-perfect)
Your full `edgerunners-mod-v0_3.zip` (179 536 bytes) is the only guaranteed bit-perfect copy of *everything* including binary PNGs, full CyberwareScreen, full SandevistanMesh float table, preview PNG, and every letter of every file.

### How to get 100% of the original zip
1. Use the file you originally uploaded to me (`edgerunners-mod-v0_3.zip`).
2. Or reconstruct from `zip_b64/` once all parts are complete:
```bash
cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
unzip edgerunners-mod-v0_3.zip
```

### Decode textures in the source tree
```bash
bash decode_assets.sh
# or manually:
for f in $(find edgerunners-mod -name '*.png.b64'); do
  base64 -d "$f" > "${f%.b64}"
done
```

Repo: https://github.com/pordonbanan-cpu/Cyberpunk

**Note:** GitHub Contents API has practical size limits when pushing many/large files from this environment. The original ZIP you provided is the authoritative complete archive. Source tree is usable for reading code and building once textures are decoded and the two large generated Java files are restored from the ZIP.
