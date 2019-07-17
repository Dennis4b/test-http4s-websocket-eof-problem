This project runs a small websocket server using ZIO, http4s, and fs2.

When connecting to it with websocat:
 websocket ws://localhost:10080/wstest

and then interrupting websocat (ctrl+C) gives an error trace on the server for a missed EOF.


