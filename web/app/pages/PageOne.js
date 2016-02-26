import React from "react";

var PageOne = React.createClass({
  render: function() {
    return <div>Hello World <a href="/pagetwo">Page Two</a></div>;
  }

});

module.exports = PageOne;