Unframework is a stuab that is an alternative to using a web framework. It puts routing of API and web requests into application code and relies on libraries for various bits of functionality. You can get more of the backstory in [this Medium post](https://medium.com/@krave/controversial-coding-opinions-part-i-web-frameworks-are-evil-1f203d173594#.toq5kbtxh).

UnframeworkServer dispatches to ApiServlet and WebServlet.
ApiServlet then routes to the particular FooEndpoints file.
FooEndpoints then calls the appropriate Endpoint impementation.
Simple as that!

I run this server by importing into IntelliJ and creating a run configration for UnframeworkServer. You'll want to add an environment variable for `PORT` and run a local mongod.

The Javascript side can be run with `npm install` and `npm run dev` from the `web/app` directory. `npm run package` will package up the Javascript.
