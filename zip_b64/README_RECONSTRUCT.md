# Full original ZIP (base64 parts)

The complete original archive `edgerunners-mod-v0_3.zip` is stored as base64 chunks so **every byte** is on GitHub.

## Reconstruct

```bash
cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
unzip edgerunners-mod-v0_3.zip
```

This recovers textures, Java, blockbench models — the exact zip you uploaded.
