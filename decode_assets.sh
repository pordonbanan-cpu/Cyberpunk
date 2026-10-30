#!/bin/bash
# Decode all .b64 files back to binary next to them
set -e
cd "$(dirname "$0")"

# Full original zip
if ls zip_b64/part_*.b64 1>/dev/null 2>&1; then
  cat zip_b64/part_*.b64 | base64 -d > edgerunners-mod-v0_3.zip
  echo "OK: edgerunners-mod-v0_3.zip"
fi

# Texture b64 files
find edgerunners-mod -name '*.b64' -type f | while read f; do
  out="${f%.b64}"
  base64 -d < "$f" > "$out"
  echo "OK: $out"
done

echo "Done."
