package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwitterClientIT {

  private TwitterAuthentication auth;

  @BeforeEach
  void init() {
    this.auth = TwitterAuthentication.builder().consumerKey("x")
        .consumerSecret("x")
        .token("x-x")
        .secret("x").build();
  }

  @Test
  void shouldConnectToTwitter() {
    TwitterClient client =
        TwitterClient.builder().authentication(auth).name("twitterClient").build();
    client.connect();
    for (int i = 0; i < 1000; i++) {
      if (client.isDone()) {
        System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
        break;
      }

      System.out.print("loading: " + i + "; ");
    }
    System.out.println("");
    client.stop();

    System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
    System.out.printf("Messages dropped: %d!\n", client.getStatsTracker().getNumMessagesDropped());
    System.out.printf("Connects: %d!\n", client.getStatsTracker().getNumConnects());
    System.out.printf("Connection failures: %d!\n",
        client.getStatsTracker().getNumConnectionFailures());
    System.out.printf("Disconnects: %d!\n", client.getStatsTracker().getNumDisconnects());
    System.out.printf("200: %d!\n", client.getStatsTracker().getNum200s());
    System.out.printf("400: %d!\n", client.getStatsTracker().getNum400s());
    System.out.printf("500: %d!\n", client.getStatsTracker().getNum500s());

    assertThat(client, notNullValue());
    assertThat(client.messages().size(), is(greaterThan(0)));
  }

}
