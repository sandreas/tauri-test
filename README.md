# tauri-test
tauri-test project to demonstrate communication between tauri and kotlin / android native libs

# Commands executed

```bash
npm create tauri-app@latest
cd tauri-app
npm install
npm run tauri android init
npm run dev
npm run tauri android dev -- --open

cd ..
npx @tauri-apps/cli plugin new mediabutton --android


# build apk: cd tauri-test/tauri-app/
npm run tauri android build
```

# Questions

- How could I open / test a tauri plugin in android studio? (references are missing somehow, no --open for Android studio?)
