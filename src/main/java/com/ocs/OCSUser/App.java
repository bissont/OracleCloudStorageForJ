package com.ocs.OCSUser;

import java.util.List;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import oracle.cloud.storage.*;
import oracle.cloud.storage.model.*;
import oracle.cloud.storage.exception.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Hello world!
 *
 */
public class App
{
  public static Boolean canRead(CloudStorage connection, String container_name){
    Boolean can_read = true;

    try {
      List<Key> objects = connection.listObjects(container_name, null);
    } catch (AccessDeniedException e) {
      can_read = false;
    }

    return can_read;
  }

  public static Boolean canWrite(CloudStorage connection, String container_name){
    Boolean can_write = true;
    InputStream test_stream = new ByteArrayInputStream(new byte[1]);

    try {
      connection.storeObject(container_name, "test_object", "text/plain", test_stream);
    } catch (AccessDeniedException e) {
      can_write = false;
    }

    return can_write;
  }

  public static void main(String[] args) {

    CommandLine cl = new Cli(args).parse();

    CloudStorageConfig admin_config = new CloudStorageConfig();
    CloudStorage admin_connection;

    String user = cl.getOptionValue("u");
    String password = cl.getOptionValue("p");
    String service = cl.getOptionValue("s");
    String container = cl.getOptionValue("c");
    String operation = cl.getOptionValue("o");
    System.out.println(container);
    admin_config.setServiceName("Storage-"+ service)
        .setUsername(user)
        .setPassword(password.toCharArray());

    try {

      admin_config.setServiceUrl("https://" + service + ".storage.oraclecloud.com");
      //admin_config.setServiceUrl("https://storage.us2.oraclecloud.com/auth/v1.0");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    admin_connection = CloudStorageFactory.getStorage(admin_config);


    CloudStorageConfig jack_config = new CloudStorageConfig();
    CloudStorage jack_connection;


    jack_config.setServiceName("Storage-"+service)
        .setUsername(user)
        .setPassword(password.toCharArray());

    try {
      jack_config.setServiceUrl("https://"+service+".storage.oraclecloud.com");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    jack_connection = CloudStorageFactory.getStorage(jack_config);

    if (operation.equals("listContainers")) {
      List<Container> list = jack_connection.listContainers();
      for (Container cont : list) {
        System.out.println("Name:" + cont.getName() + " Size:" + cont.getSize() +
            " Count:" + cont.getSize());
      }
    } else if (operation.equals("createObject")) {

      Container c = jack_connection.describeContainer(container);
      if (c == null) {
        System.out.println("Container is not valid");
        return;
      }

      String key = RandomStringUtils.randomAlphanumeric(10);
      String value= RandomStringUtils.randomAlphanumeric(100);
      System.out.println("key: " + key + " value: " + value);
      InputStream in = new ByteArrayInputStream(value.getBytes());

      Key ret = jack_connection.storeObject(container, key, "text/plain", in);
      if (ret == null) {
        System.out.println("ret: " + ret + " not equal to key: " + key);
        return;
      }
      System.out.println("Key is " + ret.getKey() + " size: " + ret.getSize());

    } else if (operation.equals("createContainers")) {

      admin_connection.createContainer(container+"1");
      admin_connection.createContainer(container);

      admin_connection.setContainerAcl(container+"1", AclType.READ, service+".Macaroni");
      admin_connection.setContainerAcl(container, AclType.WRITE, service +".Macaroni");

      System.out.println("Can Jack read from the Hello Container? " +
          (canRead(jack_connection, container+"1") ? "Yes" : "No"));
      System.out.println("Can Jack write to the Hello Container? " +
          (canWrite(jack_connection, container+"1") ? "Yes" : "No"));

      System.out.println("Can Jack read from the World Container? " +
          (canRead(jack_connection,container) ? "Yes" : "No"));
      System.out.println("Can Jack write to the World Container? " +
          (canWrite(jack_connection,container) ? "Yes" : "No"));
    }
  }
}
