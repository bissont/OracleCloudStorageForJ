package com.ocs.OCSUser;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {
  private static final Logger log = Logger.getLogger(Cli.class.getName());
  private String[] args = null;
  private Options options = new Options();
  private String username;
  private String password;
  public Cli(String[] args) {

    this.args = args;

    options.addOption("h", "help", false, "show help.");
    options.addOption("s", "service", true, "service name");
    options.addOption("u", "username", true, "Username");
    options.addOption("p", "password", true, "Password");
    options.addOption("c", "container", true, "Container");
    options.addOption("o", "operation", true, "operation");

  }


  public CommandLine parse() {
    CommandLineParser parser = new BasicParser();

    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);

      if (cmd.hasOption("h"))
        help();

      if (!cmd.hasOption("u")) {
        log.log(Level.SEVERE, "Missing u option");
        help();
      }
      if (!cmd.hasOption("p")) {
        log.log(Level.SEVERE, "Missing p option");
        help();
      }
      if (!cmd.hasOption("s")) {
        log.log(Level.SEVERE, "Missing s option");
        help();
      }
      if (!cmd.hasOption("c") && !cmd.getOptionValue("o").equals("listContainers")) {
        log.log(Level.SEVERE, "Missing c option");
        help();
      }
      if (!cmd.hasOption("o")) {
        log.log(Level.SEVERE, "Missing o option");
        help();
      }
    } catch (ParseException e) {
      log.log(Level.SEVERE, "Failed to parse comand line properties", e);
      help();
    }

    return cmd;
  }

  private void help() {
    // This prints out some help
    HelpFormatter formater = new HelpFormatter();

    formater.printHelp("Main", options);
    System.exit(0);
  }
}
