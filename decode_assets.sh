#!/bin/bash
# Decode all .png.b64 back to real .png files
set -e
cd "$(dirname "$0")"
find edgerunners-mod -name '*.png.b64' | while read f; do
  out="${f%.b64}"
  echo "Decoding $f -> $out"
  base64 -d "$f" > "$out"
done
echo "Done. You can now build / open textures."
echo "For the complete original archive use your edgerunners-mod-v0_3.zip or reconstruct from zip_b64/."
