@echo off
"C:\\Program Files\\Android\\Android Studio\\jbr\\bin\\java" ^
  --class-path ^
  "C:\\Users\\ipekc\\.gradle\\caches\\modules-2\\files-2.1\\com.google.prefab\\cli\\2.0.0\\f2702b5ca13df54e3ca92f29d6b403fb6285d8df\\cli-2.0.0-all.jar" ^
  com.google.prefab.cli.AppKt ^
  --build-system ^
  cmake ^
  --platform ^
  android ^
  --abi ^
  arm64-v8a ^
  --os-version ^
  24 ^
  --stl ^
  c++_shared ^
  --ndk-version ^
  27 ^
  --output ^
  "C:\\Users\\ipekc\\AppData\\Local\\Temp\\agp-prefab-staging11573296014850074602\\staged-cli-output" ^
  "C:\\Users\\ipekc\\.gradle\\caches\\transforms-3\\58f33ab6fd01544f2a4a3a986ae16d6a\\transformed\\games-activity-3.0.5\\prefab"
