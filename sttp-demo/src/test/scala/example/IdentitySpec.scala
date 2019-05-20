package example

import org.scalatest._
import com.softwaremill.sttp.testing.SttpBackendStub
import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import example.Identity._

class IdentitySpec extends WordSpec with Matchers {
  "The Identity object" should {
    "fail on http errors" in {
      implicit val backendStub = SttpBackendStub.synchronous.whenAnyRequest.thenRespondServerError()
      val response = Identity.fetchToken()
      response shouldEqual Left(Identity.IdentityError("Http Error: Internal server error"))
    }
    "fetch token" in {
      val fakeResponse = IdentityResponse("tkn", "bearer", 123)
      implicit val backendStub = SttpBackendStub.synchronous.
        whenRequestMatches(r => 
          r.uri.toString() == "https://accounts.autoscout24.com/oidc/token" 
          && r.method == Method.POST
        )
        .thenRespond(Right(IdentityResponse("tkn", "bearer", 123)))
      val response = Identity.fetchToken()
      fakeResponse shouldEqual response.right.get
    }
    "fail first, pass second" in {
      implicit val backendStub = SttpBackendStub.synchronous.
        whenAnyRequest.thenRespondCyclicResponses(
          Response.error("Internal server error", 500),
          Response.ok(Right(IdentityResponse("tkn", "bearer", 123))))

      fetchToken() shouldEqual Left(IdentityError("Http Error: Internal server error"))
      fetchToken() shouldEqual Right(IdentityResponse("tkn", "bearer", 123))
    }
  }
}
