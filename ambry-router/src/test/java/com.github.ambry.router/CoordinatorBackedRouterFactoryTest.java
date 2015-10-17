package com.github.ambry.router;

import com.github.ambry.clustermap.ClusterMap;
import com.github.ambry.clustermap.MockClusterMap;
import com.github.ambry.commons.LoggingNotificationSystem;
import com.github.ambry.config.VerifiableProperties;
import com.github.ambry.notification.NotificationSystem;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Unit tests for {@link CoordinatorBackedRouterFactory}.
 */
public class CoordinatorBackedRouterFactoryTest {

  /**
   * Tests the instantiation of an {@link CoordinatorBackedRouter} instance through the
   * {@link CoordinatorBackedRouterFactory}.
   * @throws InstantiationException
   * @throws IOException
   */
  @Test
  public void getCoordinatorBackedRouterTest()
      throws InstantiationException, IOException {
    VerifiableProperties verifiableProperties = getVprops();

    RouterFactory routerFactory =
        new CoordinatorBackedRouterFactory(verifiableProperties, new MockClusterMap(), new LoggingNotificationSystem());
    Router router = routerFactory.getRouter();
    assertNotNull("No RouterFactory returned", routerFactory);
    assertEquals("Did not receive an CoordinatorBackedRouter instance",
        CoordinatorBackedRouter.class.getCanonicalName(), router.getClass().getCanonicalName());
  }

  /**
   * Tests instantiation of {@link CoordinatorBackedRouterFactory} with bad input.
   * @throws IOException
   */
  @Test
  public void getCoordinatorBackedRouterFactoryWithBadInputTest()
      throws IOException {
    VerifiableProperties verifiableProperties = getVprops();
    ClusterMap clusterMap = new MockClusterMap();
    NotificationSystem notificationSystem = new LoggingNotificationSystem();

    // VerifiableProperties null.
    try {
      new CoordinatorBackedRouterFactory(null, clusterMap, notificationSystem);
    } catch (IllegalArgumentException e) {
      // expected. Nothing to do.
    }

    // ClusterMap null.
    try {
      new CoordinatorBackedRouterFactory(verifiableProperties, null, notificationSystem);
    } catch (IllegalArgumentException e) {
      // expected. Nothing to do.
    }

    // NotificationSystem null.
    try {
      new CoordinatorBackedRouterFactory(verifiableProperties, clusterMap, null);
    } catch (IllegalArgumentException e) {
      // expected. Nothing to do.
    }
  }

  public VerifiableProperties getVprops() {
    Properties properties = new Properties();
    properties.setProperty("coordinator.hostname", "localhost");
    properties.setProperty("coordinator.datacenter.name", "DC1");
    return new VerifiableProperties(properties);
  }
}
