# TicketAssistant
A simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue. I have developed this service using JAX_RS/Jersey.

Instructions:
<p>Run: clean install jetty:run -Djetty.port=9998
<p>Basic Uris to test the service:
<p>Number of rows in venue level 2: http://localhost:9998/TicketAssistant/ticket/seat/level/2
<p>Hold tickets: http://localhost:9998/TicketAssistant/ticket/seat?numSeats=100&minLevel=2&maxLevel=2&email=abc@gmail.com
<p>I have generated the link for reservation of seats as a response to hold request (HATEOS), which will look something like 
<p>...
<p>"links": [
<p>{"link": "http://localhost:9998/TicketAssistant/ticket/seat/102/abc@gmail.com","rel": "Book Uri"}
<p>]
<p>....
<p>Reserve tickets:http://localhost:9998/TicketAssistant/ticket/seat/102/abc@gmail.com
<p>These tests are also covered by TicketServiceTest.java (Used JerseyTestNg). 
