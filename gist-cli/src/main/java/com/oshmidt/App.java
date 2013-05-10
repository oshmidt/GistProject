package com.oshmidt;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

/**
 * @author oshmidt
 *         <p>
 *         Main class. Application start point. After start parsed options and
 *         send them to GistManager.
 */
public final class App {

    /** private constructor. */
    private App() {
    }

    private static final String HELP_SHORT = Messages
            .getCommand("Commands.short.help");
    private static final String HELP_LONG = Messages
            .getCommand("Commands.long.help");
    private static final String HELP_DESCRIPTION = Messages
            .getCommand("Commands.description.help");

    private static final String ALL_KEY = Messages
            .getCommand("Commands.allKey");

    private static final String USERNAME_SHORT = Messages
            .getCommand("Commands.short.username");
    private static final String USERNAME_DESCRIPTION = Messages
            .getCommand("Commands.description.username");

    private static final String PASSWORD_SHORT = Messages
            .getCommand("Commands.short.password");
    private static final String PASSWORD_DESCRIPTION = Messages
            .getCommand("Commands.description.password");

    private static final String DOWNLOAD_GISTS_SHORT = Messages
            .getCommand("Commands.short.downloadGists");
    private static final String DOWNLOAD_GISTS_DESCRIPTION = Messages
            .getCommand("Commands.description.downloadGists");

    private static final String SHOW_LOCAL_GISTS_LONG = Messages
            .getCommand("Commands.long.showLocalGists");
    private static final String SHOW_LOCAL_GISTS_DESCRIPTION = Messages
            .getCommand("Commands.description.showLocalGists");
    
    private static final String USE_LOCAL_GISTS_SHORT = Messages
            .getCommand("Commands.short.useLocalData");
    private static final String USE_LOCAL_GISTS_DESCRIPTION = Messages
            .getCommand("Commands.description.useLocalData");
            
    private static final String DOWNLOAD_FILES_LONG = Messages
            .getCommand("Commands.long.downloadFiles");
    private static final String DOWNLOAD_FILES_SHORT = Messages
            .getCommand("Commands.short.downloadFiles");
    private static final String DOWNLOAD_FILES_DESCRIPTION = Messages
            .getCommand("Commands.description.downloadFiles");

    private static final String SHORT_DESCRIPTION = Messages
            .getCommand("Commands.short.Description");

    private static final String HELP_TITLE = Messages
            .getCommand("Commands.helpTitle");

    private static final String WRONG_COMMAND = Messages
            .getString("com.oshmidt.cli.wrongCommand");

    private static final String HELP_DEVELOPED_BY = Messages
            .getCommand("Commands.helpDevelopedBy");

    /**
     * GistManager instance.
     */
    private static GistManager gistManager = new GistManager();

    /**
     * Logger instance.
     */
    private static Logger logger = Logger.getLogger(App.class);

   
    private static CommandLine cmd;
    /**
     * Application start point.
     *
     * @param args
     *            - start arguments
     */
    public static void main(final String[] args) {
        logger.info(Messages.getString("com.oshmidt.cli.aplicationStartOption",
                StringUtils.convertToString(args, " ")));
        parsingCommands(args);
        runCommands();
    }
    
    private static void parsingCommands(String[] args) {
    	CommandLineParser parser = new PosixParser();
        cmd = null;
        try {
            cmd = parser.parse(initOptions(), args);
        } catch (ParseException e1) {
            logger.error(WRONG_COMMAND + e1);
            System.out.println(WRONG_COMMAND);
            return;
        }    	
    }
    
    private static void runCommands() {
        if (cmd.hasOption(USERNAME_SHORT) && cmd.hasOption(PASSWORD_SHORT)) {
            gistManager.initUser(cmd.getOptionValue(USERNAME_SHORT),
                    cmd.getOptionValue(PASSWORD_SHORT));
            gistManager.loadAndSaveRemoteGists();
        } else if (cmd.hasOption(DOWNLOAD_GISTS_SHORT)) {
            gistManager.importUser();
            gistManager.loadAndSaveRemoteGists();
        } else if (cmd.hasOption(USE_LOCAL_GISTS_SHORT)) {
            gistManager.readLocalGists();
        }

        if (cmd.hasOption(DOWNLOAD_FILES_LONG)) {
            gistManager.downloadGistFiles(cmd.getOptionValue(DOWNLOAD_FILES_LONG));
        } else if (cmd.hasOption(DOWNLOAD_FILES_SHORT)) {
            gistManager.downloadGistFiles(cmd.getOptionValue(DOWNLOAD_FILES_SHORT));
        }

        if (cmd.hasOption(HELP_SHORT) || (cmd.getOptions().length == 0)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(SHORT_DESCRIPTION, HELP_TITLE, initOptions(),
                    HELP_DEVELOPED_BY);
        }

        if (cmd.hasOption(SHOW_LOCAL_GISTS_LONG)) {
            if (cmd.getOptionValue(SHOW_LOCAL_GISTS_LONG).equals(ALL_KEY)) {
                gistManager.showGists();
            }
        } 	
    }

    /**
     * Method create and return options for parsing input parameters.
     *
     * @return Options - {@link org.apache.commons.cli.Option}
     */
    private static Options initOptions() {
        Options options = new Options();
        options.addOption(USERNAME_SHORT, true, USERNAME_DESCRIPTION);
        options.addOption(PASSWORD_SHORT, true, PASSWORD_DESCRIPTION);
        options.addOption(DOWNLOAD_GISTS_SHORT, false,
                DOWNLOAD_GISTS_DESCRIPTION);
        options.addOption(SHOW_LOCAL_GISTS_LONG, true,
                SHOW_LOCAL_GISTS_DESCRIPTION);
        options.addOption(DOWNLOAD_FILES_SHORT, DOWNLOAD_FILES_LONG, true, DOWNLOAD_FILES_DESCRIPTION);
        options.addOption(HELP_SHORT, HELP_LONG, false, HELP_DESCRIPTION);
        options.addOption(USE_LOCAL_GISTS_SHORT, false, USE_LOCAL_GISTS_DESCRIPTION);
        return options;

    }

}
