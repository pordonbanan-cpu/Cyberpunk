#!/bin/bash
# Decode all .png.b64 back to real .png files
set -e
cd "$(dirname "$0")"

echo "Decoding texture .b64 files..."
find edgerunners-mod -name '*.png.b64' | while read f; do
  out="${f%.b64}"
  base64 -d "$f" > "$out"
  echo "  $out"
done

echo ""
echo "To get the FULL original zip (all files bit-perfect including CyberwareScreen, SandevistanMesh, blockbench, etc.):"
echo "  cat zip_b64/part_*.b64 2>/dev/null | base64 -d > edgerunners-mod-v0_3.zip"
echo "  # or if single file is present:"
echo "  base64 -d zip_b64/edgerunners-mod-v0_3.zip.b64 > edgerunners-mod-v0_3.zip"
echo "  unzip edgerunners-mod-v0_3.zip"
echo ""
echo "Done."
