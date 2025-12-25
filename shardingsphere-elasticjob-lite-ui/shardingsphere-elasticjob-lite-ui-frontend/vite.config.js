import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
    extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".vue"],
  },
  server: {
    port: 8080,
    open: true,
    proxy: {
      "/api": {
        // your domain
        target: "http://localhost:8088",
        changeOrigin: true,
        rewrite: (path) => path, // 保持原样，也可以去掉这行，默认不重写就是保持原样
      },
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        api: "modern-compiler", // dart-sass modern api
      },
    },
  },
});
