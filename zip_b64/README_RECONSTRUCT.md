# Full original ZIP — bit-perfect copy of your edgerunners-mod-v0_3.zip

## Fastest way (single file)
```bash
base64 -d zip_b64/edgerunners-mod-v0_3.zip.b64 > edgerunners-mod-v0_3.zip
unzip edgerunners-mod-v0_3.zip
```

## Or from numbered parts (if single file not yet complete)
```bash
cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
unzip edgerunners-mod-v0_3.zip
```

## Decode texture .b64 files to real PNGs
```bash
bash decode_assets.sh
# or manually:
for f in edgerunners-mod/src/main/resources/assets/edgerunners/textures/**/*.png.b64; do
  base64 -d "$f" > "${f%.b64}"
done
```

After unzip you have **absolutely everything** from your original zip: all Java sources (including CyberwareScreen, SandevistanMesh full geometry), blockbench model + preview, all textures, gradle files, DEVLOG, etc.

Repo: https://github.com/pordonbanan-cpu/Cyberpunk
