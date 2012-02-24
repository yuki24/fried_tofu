package net.yuki24.friedtofu

import java.io.ByteArrayOutputStream
import com.amazonaws._
import com.amazonaws.auth._
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model._
import org.im4java._
import org.im4java.core._
import javax.imageio.ImageIO

class Image(path:String) {
  val bucketName  = System.getenv("S3_BUCKET")
  val accessKey   = System.getenv("S3_ACCESS_KEY")
  val secretKey   = System.getenv("S3_SECRET_KEY")
  val credentials = new BasicAWSCredentials(accessKey,secretKey)
  val s3          = new AmazonS3Client(credentials)
  val objectData  = s3.getObject(bucketName, path)
  val content     = objectData.getObjectContent
  val contentType = objectData.getObjectMetadata.getContentType
  val extension   = contentType.split("/")(1)

  def resizeToFit(width:Integer, height:Integer) = {
    var op         = new IMOperation
    var convert    = new ConvertCmd
    var s2b        = new Stream2BufferedImage
    var byteStream = new ByteArrayOutputStream

    op.addImage()
    op.quality(0.8).resize(width, height).strip
    op.addImage(extension + ":-")
    convert.setOutputConsumer(s2b)
    convert.run(op, ImageIO.read(content))

    ImageIO.write(s2b.getImage, extension, byteStream)
    byteStream.flush
    var imageInByte = byteStream.toByteArray
    byteStream.close
    imageInByte
  }

  def resizeToFill(width:Integer, height:Integer, gravity:String = "center") = {
    var orgImage   = ImageIO.read(content)
    var op         = new IMOperation
    var convert    = new ConvertCmd
    var s2b        = new Stream2BufferedImage
    var byteStream = new ByteArrayOutputStream
    var cols       = orgImage.getWidth
    var rows       = orgImage.getHeight

    op.addImage()
    if(width != cols || height != rows) {
      var dimScale = Array((width.toDouble/cols), (height.toDouble/rows))
      var scale = if(dimScale(0) > dimScale(1)) dimScale(0) else dimScale(1)
      cols = (scale * (0.5 + cols)).toInt
      rows = (scale * (0.5 + rows)).toInt
      op.resize(cols, rows)
    }
    op.quality(0.8).gravity(gravity).strip
    if(cols != width || rows != height) op.extent(width, height)
    op.addImage(extension + ":-")
    convert.setOutputConsumer(s2b)
    convert.run(op, orgImage)

    ImageIO.write(s2b.getImage, extension, byteStream)
    byteStream.flush
    var imageInByte = byteStream.toByteArray
    byteStream.close
    imageInByte
  }
}
