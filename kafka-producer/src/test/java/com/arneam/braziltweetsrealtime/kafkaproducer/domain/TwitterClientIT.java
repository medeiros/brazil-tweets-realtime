package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwitterClientIT {

  private TwitterAuthentication auth;

  @BeforeEach
  void init() {
    this.auth = TwitterAuthentication.builder().consumerKey("aaa").consumerSecret("s3cr3t")
        .token("aaaaa-bbbb").secret("xxxxx").build();
  }

  @Test
  void shouldConnectToTwitter() {
    TwitterClient client =
        TwitterClient.builder().authentication(auth).name("twitterClient").build();
    client.connect();
    client.stop();
    assertThat(client, notNullValue());
  }

}
