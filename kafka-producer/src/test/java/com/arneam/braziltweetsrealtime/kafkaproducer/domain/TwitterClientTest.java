package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwitterClientTest {

  private TwitterAuthentication auth;

  @BeforeEach
  void init() {
    this.auth = TwitterAuthentication.builder().consumerKey("aaa").consumerSecret("s3cr3t")
        .token("aaaaa-bbbb").secret("xxxxx").build();
  }

  @Test
  void shouldCreateTwitterClient() {
    TwitterClient client =
        TwitterClient.builder().authentication(auth).name("twitterClient").build();
    assertThat(client, notNullValue());
  }

  @Test
  void shouldNotCreateTwitterClientObjectIfAuthenticationObjectIsMissing() {
    Throwable throwable = assertThrows(NullPointerException.class,
        () -> TwitterClient.builder().authentication(null).name("test").build());
    assertThat(throwable.getMessage(), is(containsString("is marked non-null but is null")));
  }

  @Test
  void shouldNotCreateTwitterClientObjectIfNameIsMissing() {
    Throwable throwable = assertThrows(NullPointerException.class,
        () -> TwitterClient.builder().authentication(auth).name(null).build());
    assertThat(throwable.getMessage(), is(containsString("is marked non-null but is null")));
  }

}
