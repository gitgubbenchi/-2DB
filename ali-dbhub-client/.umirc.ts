import { defineConfig } from 'umi';
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
// const MonacoWebpackPlugin = require('monaco-editor-esm-webpack-plugin');

const chainWebpack = (config: any, { webpack }: any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: ['mysql', 'pgsql', 'sql'],
    },
  ]);

  // TODO: Monaco汉化
  // config.module.rules()
  // .test(/\.js/)
  // .use(MonacoWebpackPlugin.loader)
  // .loader(MonacoWebpackPlugin.loader)
  // .options({name: /node_modules[\\\/]monaco-editor[\\\/]esm/ ,esModule: false});
};

export default defineConfig({
  title: 'dbHub',
  history: {
    type: 'hash',
  },
  base: '/',
  publicPath: '/',
  // publicPath: './static/front/',
  hash: false,
  routes: [
    {
      path: '/',
      component: '@/components/AppContainer',
      routes: [
        { path: '/login', exact: true, component: '@/pages/login' },
        { path: '/error', component: '@/pages/error' },
        { path: '/demo', exact: true, component: '@/pages/demo' },
        {
          path: '/',
          component: '@/layouts/BaseLayout',
          routes: [
            {
              exact: true,
              path: '/',
              component: '@/pages/database',
            },
            {
              exact: true,
              path: '/database',
              component: '@/pages/database',
            },
            {
              path: '/sql-history',
              exact: true,
              component: '@/pages/sql-history',
            },
            {
              path: '/manage',
              exact: true,
              component: '@/pages/manage',
            },
            {
              path: '/chat',
              exact: true,
              component: '@/pages/chat-ai'
            },
            {
              redirect: '/error',
            }
          ]
        }
      ],
    },
  ],

  mfsu: {},
  fastRefresh: {},
  nodeModulesTransform: {
    type: 'none',
  },
  chainWebpack,
  devServer: {
    port: 8001,
    host: '127.0.0.1',
  },
});