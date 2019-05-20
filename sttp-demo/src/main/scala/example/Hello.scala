package example
import com.softwaremill.sttp._
import Identity.fetchToken

object Hello extends App {
  implicit val backend = HttpURLConnectionBackend()
  println(fetchToken())
}

