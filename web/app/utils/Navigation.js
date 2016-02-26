var Navigation = {

 callback: function() {},

 view: function(path) {
   window.history.pushState(null, '', '/' + path);
   this.callback();
 },

};

module.exports = Navigation;