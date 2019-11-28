package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TwitterAuthenticationTest {

  @Test
  void shouldCreateAuthenticationObject() {
    TwitterAuthentication auth =
        TwitterAuthentication.builder().consumerKey("aaa").consumerSecret("s3cr3t")
            .token("aaaaa-bbbb").secret("xxxxx").build();
    assertThat(auth, notNullValue());
  }

  @ParameterizedTest
  @CsvSource({", secret, aaa-aaa, sec", "aaa, , aaa-aaa, sec", "aaa, secret, , sec",
      "aaa, secret, aaa-aaa, "})
  void shouldNotCreateAuthenticationObjectIfAnyFieldIsMissing(String consumerKey,
      String consumerSecret, String token, String secret) {
    Throwable throwable = assertThrows(NullPointerException.class,
        () -> TwitterAuthentication.builder().consumerKey(consumerKey)
            .consumerSecret(consumerSecret).token(token).secret(secret).build());
    assertThat(throwable.getMessage(), is(containsString("is marked non-null but is null")));
  }

}
