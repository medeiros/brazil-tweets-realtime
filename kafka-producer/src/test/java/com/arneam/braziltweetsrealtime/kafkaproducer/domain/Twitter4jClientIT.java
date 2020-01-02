package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Twitter4jClientIT {

  @Test
  void shouldConnectToTwitter() throws InterruptedException {
    Twitter4JClient client =
        Twitter4JClient.builder().authentication(null).name("brazil-tweets-realtime").build();

    client.connect();

    Thread.sleep(10000);

    assertThat(client, notNullValue());
  }

}
