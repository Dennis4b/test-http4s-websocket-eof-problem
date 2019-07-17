package http4seofminimalexample

import org.http4s._, org.http4s.dsl._
import org.http4s.implicits._
import org.http4s.server._
import org.http4s.server.blaze._
import org.http4s.server.websocket._
import org.http4s.server.websocket.WebSocketBuilder
import org.http4s.websocket.WebSocketFrame
import org.http4s.websocket.WebSocketFrame._
import cats._, cats.data._, cats.implicits._, cats.effect._
import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._



object Main extends App {

    override def run(args: List[String]): ZIO[Environment,Nothing,Int] = {
        val dsl = Http4sDsl[Task]
        import dsl._

        val app = ZIO.runtime[Environment].flatMap{ implicit rts => for {

            /* Don't get distracted by the Queue, it's just there to make an outputstream that stays
             * around long enough. The bug has been reproduced also with other types of streams
             */
            queue <- fs2.concurrent.Queue.unbounded[Task,WebSocketFrame]
            routes = HttpRoutes.of[Task] {
                case req @ GET -> Root => {
                    WebSocketBuilder[Task].build(
                        queue.dequeue.onFinalize(ZIO.effect{println(">> Outputstream closed")}),
                        _.evalMap(_ => ZIO.unit).onFinalize(ZIO.effect{println(">> Inputstream closed")})
                    )
                }
            }
            wsApp = Router[Task](
                "/wstest"     -> routes
            ).orNotFound

            server <- BlazeServerBuilder[Task]
                .bindHttp(10080, "localhost")
                .withHttpApp(wsApp)
                .serve
                .compile[Task, Task, ExitCode]
                .drain

        } yield 0}

        app.orDie
    }

}
