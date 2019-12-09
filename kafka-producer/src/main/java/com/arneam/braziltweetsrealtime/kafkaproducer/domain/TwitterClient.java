package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
public class TwitterClient {

  @NonNull
  private TwitterAuthentication authentication;
  @NonNull
  private String name;

  private final BasicClient client;
  private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);

  public void connect() {
    BasicClient client = createClient();
    client.connect();
  }

  private BasicClient createClient() {
    return new ClientBuilder().name(name).hosts(Constants.STREAM_HOST).endpoint(endpoint())
        .authentication(oauth1()).processor(new StringDelimitedProcessor(queue)).build();
  }

  private Authentication oauth1() {
    return new OAuth1(authentication.consumerKey(), authentication.consumerSecret(),
        authentication.token(), authentication.secret());
  }

  private StatusesSampleEndpoint endpoint() {
    StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
    endpoint.stallWarnings(false);
    return endpoint;
  }

  public void stop() {
  }

  Collection<String> messages() {
    return queue;
  }
}
