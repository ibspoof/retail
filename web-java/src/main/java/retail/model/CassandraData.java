package retail.model;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * DataStax Academy Sample Application
 *
 * Copyright 2013 DataStax
 *
 * This is a Singleton class that holds 1 Cassandra session that all requests will share.
 * It has 1 public method to return that session.
 *
 *
 *
 *
 */

public class CassandraData {

  //
  // A static variable that holds the session.  Only one of these will exist for the whole application
  //

  private static Session cassandraSession = null;

  /**
   * Required constructor, but it doesn't need to do anything.
   */

  CassandraData () {
    // Do nothing
  }

  /**
   *
   *  Return the Cassandra session.
   *  When the application starts up, the session
   *  is set to null.  When this function is called, it checks to see if the session is null.
   *  If so, it creates a new session, and sets the static session.
   *
   *  All of the DAO classes are subclasses of this
   *
   * @return - a valid cassandra session
   */

  public static Session getSession() {

    if (cassandraSession == null) {
      cassandraSession = createSession();
    }

    return cassandraSession;

  }

  /**
   *
   * Create a new cassandra Cluster() and Session().  Returns the Session.
   *
   * @return A new Cassandra session
   */

  protected static Session createSession() {
    Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
    return cluster.connect();
  }

  public static String makeSolrQueryString(String search_term, String filter_by) {
    String solr_query = "\"q\":\"title:"+ escapeQuotes(search_term) +"\"";

    if (filter_by != null && !filter_by.isEmpty()) {
      solr_query += ",\"fq\":\"" + escapeQuotes(filter_by) + "\"";
    }
    return solr_query;
  }

  private static String escapeQuotes(String s) {
    if (s == null) {
      return null;
    }
    return s.replace("\"","\\\"");
  }
}