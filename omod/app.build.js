({
  appDir: "./src/main/webapp/resources",
  baseUrl: ".",
  dir: "./src/main/webapp/build",
  optimizeCss: "none",
  modules: [
    {
      name: "app"
    }
  ],
  shim: {
    'jquery-ui': ['jquery'],
    'angular': {
      deps: ['jquery'],
      exports: 'angular'
    }
  }
})
