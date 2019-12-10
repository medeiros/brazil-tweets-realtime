package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.StatsReporter;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.event.Event;
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

  private static BasicClient client;
  private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);

  public void connect() {
    client = createClient();
    client.connect();
  }

  public boolean isDone() {
    return client.isDone();
  }

  public Event getExitEvent() {
    return client.getExitEvent();
  }

  private BasicClient createClient() {
    return new ClientBuilder().name(name).hosts(Constants.STREAM_HOST).endpoint(endpoint())
        .authentication(oauth1()).processor(new StringDelimitedProcessor(queue)).build();
  }

  private Authentication oauth1() {
    return new OAuth1(authentication.consumerKey(), authentication.consumerSecret(),
        authentication.token(), authentication.secret());
  }

  private StatusesFilterEndpoint endpoint() {
    //StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
    //endpoint.stallWarnings(false);
    //return endpoint;

    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
    // add some track terms
    endpoint.trackTerms(Lists.newArrayList("twitterapi", "#yolo"));
    return endpoint;
  }

  public StatsReporter.StatsTracker getStatsTracker() {
    return client.getStatsTracker();
  }

  public void stop() {
    client.stop();
  }

  Collection<String> messages() {
    return queue;
  }
}
