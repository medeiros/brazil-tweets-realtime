package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
@Getter
public class TwitterAuthentication {

  @NonNull
  private String consumerKey;

  @NonNull
  private String consumerSecret;

  @NonNull
  private String token;

  @NonNull
  private String secret;

}
