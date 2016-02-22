var path = require('path');

var node_modules = path.resolve(__dirname, 'node_modules');

module.exports = {
  entry: {
    'app': './app/app',
  },
  output: {    
    filename: "[name].js",
    publicPath: "http://localhost:9090/assets/"
  },
  module: {
    loaders: [
      { test: /\.css$/, loader: "style!css" },
      { test: /\.jsx?$/,
        loaders: ['react-hot', 'babel'],
        include: path.join(__dirname, 'app')
      },
      { test: /\.less$/, loader: "style-loader!css-loader!less-loader" },
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx'],
  },
  devServer: {
    inline: true,
    hot: true,
    port: 9090
  }
};
