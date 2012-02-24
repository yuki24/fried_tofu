package net.yuki24.friedtofu

import org.scalatra._
import scalate.ScalateSupport

class FriedTofu extends ScalatraServlet with ScalateSupport {

  get("/full/*") {
    val image = new Image(multiParams("splat")(0))
    contentType = image.contentType
    org.scalatra.util.io.copy(image.content, response.getOutputStream)
  }

  get("/rfit/*/*/*") {
    val width  = Integer.parseInt(multiParams("splat")(0))
    val height = Integer.parseInt(multiParams("splat")(1))
    val image  = new Image(multiParams("splat")(2))

    contentType = image.contentType
    response.getOutputStream.write(image.resizeToFit(width, height))
  }

  get("/rfill/*/*/*") {
    val width  = Integer.parseInt(multiParams("splat")(0))
    val height = Integer.parseInt(multiParams("splat")(1))
    val image  = new Image(multiParams("splat")(2))

    contentType = image.contentType
    response.getOutputStream.write(image.resizeToFill(width, height))
  }

  notFound {
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound() 
  }
}
