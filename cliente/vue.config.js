const { defineConfig } = require('@vue/cli-service');
const webpack = require('webpack');

module.exports = defineConfig({
  devServer: {
    port: 8084, // Mantén el puerto configurado
  },
  transpileDependencies: true, // Opción existente
  configureWebpack: {
    plugins: [
      new webpack.DefinePlugin({
        __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: JSON.stringify(false), // Configuración del feature flag
      }),
    ],
  },
});
