package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
public class TwitterClient {

  @NonNull private TwitterAuthentication authentication;
  @NonNull private String name;

}
