package example

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import io.circe.generic.auto._

object Identity {

  case class IdentityResponse(access_token: String, token_type: String, expires_in: Int)
  case class IdentityError(message: String)

  def fetchToken()(implicit sttpBackend: SttpBackend[Id, Nothing]): Either[IdentityError, IdentityResponse] =
    sttp
      .post(uri"https://accounts.autoscout24.com/oidc/token")
      .body(Map("grant_type" -> "client_credentials"))
      .auth
      .basic(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET"))
      .response(asJson[IdentityResponse])
      .send()
      .body
      match {
        case Left(e: String) => Left(IdentityError(s"Http Error: $e"))
        case Right(Left(e)) => Left(IdentityError(s"JSON Error: $e"))
        case Right(Right(r: IdentityResponse)) => Right(r)
      }
}