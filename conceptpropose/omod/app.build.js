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
    'jquery-ui': ['lib/jquery'],
    'angular': {
      deps: ['lib/jquery'],
      exports: 'angular'
    },
    'angular-resource': ['angular']
  }
})
