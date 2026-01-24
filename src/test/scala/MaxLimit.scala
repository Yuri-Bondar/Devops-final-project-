import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MaxLimit extends Simulation {

  val httpProtocol = http
    .baseUrl("http://4.178.56.71:8081")
    .acceptHeader("text/html")
    .userAgentHeader("Mozilla/5.0")

  val scn = scenario("Max Limit Validation - 30 Concurrent Users")
    .exec(
      http("GET Home")
        .get("/Devops-final-project-/adamliadadiramityuri/")
        .check(status.is(200))
    )
    .pause(300.millis, 900.millis)

  setUp(
    scn.inject(
      // reach the supposed max limit safely
      rampConcurrentUsers(1).to(30).during(30.seconds),

      // short hold to prove stability at max limit
      constantConcurrentUsers(30).during(60.seconds)
    )
  ).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.gt(100),      // Fail if less than 100% request succeed
      global.responseTime.percentile3.lt(15000)       // Fail if response time is more than 15 seconds
    )
}

