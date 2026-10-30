# Full original ZIP

The original `edgerunners-mod-v0_3.zip` (179536 bytes) contains **absolutely everything**:
- every .java (including full CyberwareScreen.java and SandevistanMesh.java)
- every texture .png
- blockbench/preview_sandevistan.png + sandevistan.bbmodel
- gradle files, langs, models, DEVLOG, README

Parts under `zip_b64/part_*.b64` are being filled. When complete:

```bash
cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
md5sum edgerunners-mod-v0_3.zip   # should match your original
unzip edgerunners-mod-v0_3.zip
```

Until then, keep your original uploaded zip — it is the bit-perfect source of truth.
