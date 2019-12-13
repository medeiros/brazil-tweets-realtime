package com.arneam.braziltweetsrealtime.kafkaproducer.domain;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.Accessors;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@Builder
@Accessors(fluent = true)
public class Twitter4JClient {

//  @NonNull
  private TwitterAuthentication authentication;
  @NonNull
  private String name;

  private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);

  public void connect() {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true);
    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

    StatusListener listener = new StatusListener() {
      @Override
      public void onStatus(Status status) {
        if (status.getUser().getLocation() != null && status.getUser().getLocation().contains(
            "Brasil")) {
          System.out.println(
              "@ [" + status.getLang() + "] " + status.getUser().getScreenName() + " - " + status.getText());
        }
      }

      @Override
      public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
      }

      @Override
      public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
      }

      @Override
      public void onScrubGeo(long userId, long upToStatusId) {
//        System.out
//            .println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
      }

      @Override
      public void onStallWarning(StallWarning warning) {
//        System.out.println("Got stall warning:" + warning);
      }

      @Override
      public void onException(Exception ex) {
        ex.printStackTrace();
      }
    };
    twitterStream.addListener(listener);
//    twitterStream.sample();

    FilterQuery filtre = new FilterQuery();
    String[] keywordsArray = {"bolsonaro"};
    filtre.track(keywordsArray);
    twitterStream.filter(filtre);
  }

  public void stop() {

  }

  Collection<String> messages() {
    return queue;
  }

}
