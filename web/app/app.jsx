import React from "react";
import ReactDOM from "react-dom";

var PageOne = require('./pages/PageOne');
var PageTwo = require('./pages/PageTwo');


var Navigation = require('./utils/Navigation');

window.addEventListener('click', function(event) {    
  if (event.defaultPrevented || event.cancelBubble || PreventUnload.dirty || event.metaKey) return;
  if (event.target.tagName == 'A') {
    var href = event.target.href;
    var currentPrefix = window.location.protocol + '//' + window.location.host + '/';
    if (href.indexOf(currentPrefix) == 0) {
      var path = href.substring(currentPrefix.length);
      Navigation.view(path);
      event.preventDefault();
    }
  }
}, true);

var Router = React.createClass({

  getInitialState: function() {
    return { path: window.location.pathname };
  },

  handlePopState: function() {    
    this.setState({path: window.location.pathname});
  },

  componentDidMount: function() {    
    window.addEventListener('popstate', (s) => this.handlePopState(s), true);
    Navigation.callback = (() => this.handlePopState());    
    Cache.callback = ((state) => this.setState({loaded: state}));
  },

  render: function() {
    return <div>{this.renderCurrentRoute()}</div>;
  },

  renderCurrentRoute: function() {
    var path = this.state.path.split('/');
    console.log(path);
    switch (path[1]) {
      case '':
        return <PageOne />;
      case 'pagetwo':
        return <PageTwo />;
      default:
        return <PageOne />;
    }
  }
});

var rootInstance = ReactDOM.render(<Router />, document.getElementById("content"));


