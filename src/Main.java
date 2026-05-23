/*
 * Nicolas Nº74517 and Mahir Nº70217
 * FCT LEI OOP 2026
 */
import youVideo.*;
import youVideo.Exceptions.*;

import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

/**
 * Entry point for the YouVideo application.
 * Responsible only for reading user input and printing output.
 * All domain logic is delegated to {@link VideoNetwork}.
 */
public class Main {

    // -----------------------------------------------------------------------
    //  Command constants
    // -----------------------------------------------------------------------
    private static final String CMD_CREATE_PUBLISHABLE  = "createpublishable";
    private static final String CMD_CREATE_PREMIUM      = "createpremium";
    private static final String CMD_ADD_SUB             = "addsubtitle";
    private static final String CMD_GET_VIDEO           = "getvideo";
    private static final String CMD_SUBTITLE            = "subtitles";
    private static final String CMD_CREATE_PODCAST      = "createpodcast";
    private static final String CMD_ADD_EPISODE         = "addepisode";
    private static final String CMD_GET_PODCAST         = "getpodcast";
    private static final String CMD_EPISODES            = "episodes";
    private static final String CMD_AUTHOR_PODCAST      = "authorpodcasts";
    private static final String CMD_REMOVE_PODCAST      = "removepodcast";
    private static final String CMD_CREATE_SHOW         = "createshow";
    private static final String CMD_GET_SHOW            = "getshow";
    private static final String CMD_AUTHOR_SHOWS        = "authorshows";
    private static final String CMD_REMOVE_SHOW         = "removeshow";
    private static final String CMD_REMOVE_VIDEO        = "removevideo";
    private static final String CMD_AUTHOR_PRODUCTIVITY = "authorsproductivity";
    private static final String CMD_ADD_TAG             = "addtag";
    private static final String CMD_REMOVE_TAG          = "removetag";
    private static final String CMD_TAGGED              = "tagged";
    private static final String CMD_HELP                = "help";
    private static final String CMD_EXIT                = "exit";

    // -----------------------------------------------------------------------
    //  Output / error message constants
    // -----------------------------------------------------------------------
    private static final String UNKNOWN_COMMAND           = "Unknown command. Type help to see available commands.";
    private static final String INVALID_LANGUAGE          = "Invalid language type.";
    private static final String INVALID_DURATION          = "Invalid value.";
    private static final String ID_ALREADY_EXISTS         = "Video with this ID already exists.";
    private static final String INVALID_LANGUAGE_SUBTITLE = "Invalid language type in subtitle.";
    private static final String VIDEO_DOES_NOT_EXIST      = "Video does not exist.";
    private static final String NOT_A_PREMIUM_VIDEO       = "This operation requires a Premium video.";
    private static final String CREATED_SUB               = "Subtitle added successfully.";
    private static final String NO_PREMIUM_VIDEO          = "No Premium Video with ID.";
    private static final String TITLE_ALREADY_USED        = "Podcast with this title already exists.";
    private static final String PODCAST_CREATED           = "Podcast created successfully.";
    private static final String EPISODE_CREATED           = "Episode added successfully.";
    private static final String PODCAST_DOES_NOT_EXIST    = "Podcast does not exist.";
    private static final String EPISODE_ID_EXIST          = "Episode ID already exists in the system.";
    private static final String WRONG_DATE_EPISODE        = "Episode date must be >= than latest episode date.";
    private static final String HAS_NO_EPISODE            = "No episodes available for this podcast.";
    private static final String NO_PODCAST_AUTHOR         = "No podcasts found for this author.";
    private static final String REMOVE_PODCAST            = "Podcast removed successfully.";
    private static final String SHOW_VIDEO_DOES_NOT_EXIST = "Video for show does not exist.";
    private static final String SHOW_ALREADY_EXISTS       = "Show with this title already exists.";
    private static final String SHOW_CREATED              = "Show created successfully.";
    private static final String SHOW_DOES_NOT_EXIST       = "Show does not exist.";
    private static final String SHOW_REMOVED              = "Show removed successfully.";
    private static final String NO_SHOWS_AUTHOR           = "No shows found for this author.";
    private static final String CANNOT_REMOVE_EPISODE     = "Cannot remove: video is an episode of a podcast.";
    private static final String CANNOT_REMOVE_SHOW_VIDEO  = "Cannot remove: video is used in a show.";
    private static final String VIDEO_REMOVED             = "Video removed successfully.";
    private static final String VIDEO_CREATED_SUCCESS     = " created successfully.";
    private static final String SUBTITLE_LIST_HEADER      = "Subtitles for video ";
    private static final String TAGGED_SUCCESS            = "Tag added successfully.";
    private static final String TITLE_DOES_NOT_EXIST      = "Title does not exist.";
    private static final String TITLE_ALREADY_TAGGED      = "Title is already tagged with ";
    private static final String TAG_REMOVED               = "Tag removed successfully.";
    private static final String TITLE_NOT_TAGGED          = "Title is not tagged with ";
    private static final String NOTHING_TAGGED            = "No content tagged with ";
    private static final String INVALID_TAG_PARAMETERS    = "Invalid tagged parameters.";
    private static final String END_PROGRAM               = "Bye!";

    /** Full help text shown by the help command. */
    private static final String HELP_MENU_MESSAGE = """
            createpublishable - creates a new publishable video
            createpremium - creates a new publishable Premium video
            addsubtitle - adds subtitle to Premium video
            getvideo - presents publishable video data from its id
            subtitles - Lists Premium video subtitles
            createpodcast - creates a new podcast with no episodes
            addepisode - adds an episode to a podcast
            getpodcast - presents podcast data from its title
            episodes - List podcast episodes
            authorpodcasts - List all podcasts of an author
            removepodcast - removes a podcast
            createshow - creates show using an existing publishable video
            getshow - presents show data from its title
            authorshows - List all shows of an author
            removeshow - removes a show
            removevideo - removes a publishable video
            authorsproductivity - List authors by their productivity
            addtag - adds a tag to a show or podcast
            removetag - removes a tag from a show or podcast
            tagged - List content tagged with a given tag
            help - shows the available commands
            exit - terminates the execution of the program""";

    // -----------------------------------------------------------------------
    //  Entry point
    // -----------------------------------------------------------------------

    /**
     * Application entry point.
     * Sets the default locale to British English to ensure consistent language display names,
     * then starts the command interpreter.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.of("en", "GB"));
        Scanner sc = new Scanner(System.in);
        VideoNetwork platform = new VideoNetworkClass();
        commandInterpreter(sc, platform);
    }

    // -----------------------------------------------------------------------
    //  Command interpreter
    // -----------------------------------------------------------------------

    /**
     * Main command loop. Reads one token at a time as a command name (case-insensitive)
     * and dispatches to the matching handler method. Runs until the exit command is read.
     *
     * @param sc       scanner reading from standard input
     * @param platform the video network that handles all domain logic
     */
    private static void commandInterpreter(Scanner sc, VideoNetwork platform) {
        String command = sc.next().toLowerCase();
        while (!command.equals(CMD_EXIT)) {
            switch (command) {
                case CMD_CREATE_PUBLISHABLE  -> addPublishable(sc, platform);
                case CMD_CREATE_PREMIUM      -> addPremium(sc, platform);
                case CMD_ADD_SUB             -> addSub(sc, platform);
                case CMD_GET_VIDEO           -> getVideo(sc, platform);
                case CMD_SUBTITLE            -> subtitleList(sc, platform);
                case CMD_CREATE_PODCAST      -> addPodcast(sc, platform);
                case CMD_ADD_EPISODE         -> addEpisode(sc, platform);
                case CMD_GET_PODCAST         -> getPodcast(sc, platform);
                case CMD_EPISODES            -> episodesList(sc, platform);
                case CMD_AUTHOR_PODCAST      -> podcastList(sc, platform);
                case CMD_REMOVE_PODCAST      -> removePodcast(sc, platform);
                case CMD_CREATE_SHOW         -> createShow(sc, platform);
                case CMD_GET_SHOW            -> getShow(sc, platform);
                case CMD_AUTHOR_SHOWS        -> authorShows(sc, platform);
                case CMD_REMOVE_SHOW         -> removeShow(sc, platform);
                case CMD_REMOVE_VIDEO        -> removeVideo(sc, platform);
                case CMD_AUTHOR_PRODUCTIVITY -> authorProductivity(platform);
                case CMD_ADD_TAG             -> addTag(sc, platform);
                case CMD_REMOVE_TAG          -> removeTag(sc, platform);
                case CMD_TAGGED              -> contentTagged(sc, platform);
                case CMD_HELP                -> help();
                default                      -> System.out.println(UNKNOWN_COMMAND);
            }
            command = sc.next().toLowerCase();
        }
        System.out.println(END_PROGRAM);
        sc.close();
    }

    // -----------------------------------------------------------------------
    //  Command handler methods
    // -----------------------------------------------------------------------

    /**
     * Handles the createpublishable command.
     * Reads id, duration, url (same line), then publisher, title, and language (separate lines).
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void addPublishable(Scanner sc, VideoNetwork platform) {
        String id       = sc.next();
        int    duration = sc.nextInt();
        String url      = sc.next();
        sc.nextLine();
        String publisher     = sc.nextLine();
        String title         = sc.nextLine();
        String languageCode  = sc.nextLine().trim();
        try {
            platform.createPublishable(id, duration, url, publisher, title, languageCode);
            System.out.println("Video " + id + VIDEO_CREATED_SUCCESS);
        } catch (InvalidLanguageException  e) { System.out.println(INVALID_LANGUAGE);   }
        catch  (InvalidDurationException   e) { System.out.println(INVALID_DURATION);   }
        catch  (IdAlreadyExistsException   e) { System.out.println(ID_ALREADY_EXISTS);  }
    }

    /**
     * Handles the createpremium command.
     * Reads id, duration, url (same line), then publisher, title, language,
     * subtitle URL, and subtitle language (each on a separate line).
     * Note: the createpremium success message uses "PREMIUM Video" (one space).
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void addPremium(Scanner sc, VideoNetwork platform) {
        String id       = sc.next();
        int    duration = sc.nextInt();
        String url      = sc.next();
        sc.nextLine();
        String publisher        = sc.nextLine();
        String title            = sc.nextLine();
        String languageCode     = sc.nextLine();
        // FIXED: subtitle URL and language are on separate lines per the assignment spec
        String initSubUrl       = sc.nextLine();
        String initLanguageCode = sc.nextLine().trim();
        try {
            platform.createPremium(id, duration, url, publisher, title,
                    languageCode, initLanguageCode, initSubUrl);
            // FIXED: removed extra space between "PREMIUM" and "Video"
            System.out.println("PREMIUM Video " + id + VIDEO_CREATED_SUCCESS);
        } catch (InvalidLanguageException         e) { System.out.println(INVALID_LANGUAGE);           }
        catch  (InvalidLanguageSubtitleException  e) { System.out.println(INVALID_LANGUAGE_SUBTITLE);  }
        catch  (InvalidDurationException          e) { System.out.println(INVALID_DURATION);           }
        catch  (IdAlreadyExistsException          e) { System.out.println(ID_ALREADY_EXISTS);          }
    }

    /**
     * Handles the addsubtitle command.
     * Reads video id and subtitle URL (same line), then subtitle language code.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void addSub(Scanner sc, VideoNetwork platform) {
        String id          = sc.next();
        String subtitleUrl = sc.next();
        String languageCode = sc.next();
        sc.nextLine();
        try {
            platform.createSubtitle(id, subtitleUrl, languageCode);
            System.out.println(CREATED_SUB);
        } catch (InvalidLanguageSubtitleException e) { System.out.println(INVALID_LANGUAGE_SUBTITLE); }
        catch  (VideoDoesNotExistException        e) { System.out.println(VIDEO_DOES_NOT_EXIST);      }
        catch  (NotAPremiumVideoException         e) { System.out.println(NOT_A_PREMIUM_VIDEO);       }
    }

    /**
     * Handles the getvideo command.
     * Reads the video id and delegates display to the domain layer.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void getVideo(Scanner sc, VideoNetwork platform) {
        String id = sc.next();
        sc.nextLine();
        try {
            platform.getVideo(id);
        } catch (InvalidPublishableVideoException e) {
            System.out.println("Publishable Video " + id + " does not exist.");
        }
    }

    /**
     * Handles the subtitles command.
     * Retrieves a premium video via the domain interface and iterates its subtitles here
     * because printing is the responsibility of Main.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void subtitleList(Scanner sc, VideoNetwork platform) {
        String id = sc.next();
        sc.nextLine();
        try {
            PremiumVideos premiumVideo = platform.getPremiumVideo(id);
            System.out.println(SUBTITLE_LIST_HEADER + premiumVideo.getTitle() + ":");
            Iterator<Subtitles> it = premiumVideo.getSubtitlesIterator();
            while (it.hasNext()) {
                Subtitles sub = it.next();
                System.out.println("- " + sub.getUrl()
                        + " (" + sub.getLanguage().getDisplayLanguage(Locale.ENGLISH).toUpperCase() + ")");
            }
        } catch (NotAPremiumVideoException e) { System.out.println(NO_PREMIUM_VIDEO); }
    }

    /**
     * Handles the createpodcast command.
     * Reads title, author, and language on separate lines.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void addPodcast(Scanner sc, VideoNetwork platform) {
        String title    = sc.nextLine().trim();
        String author   = sc.nextLine();
        String language = sc.next();
        sc.nextLine();
        try {
            platform.createPodcast(title, author, language);
            System.out.println(PODCAST_CREATED);
        } catch (InvalidLanguageException    e) { System.out.println(INVALID_LANGUAGE);    }
        catch  (TitleAlreadyExistException   e) { System.out.println(TITLE_ALREADY_USED);  }
    }

    /**
     * Handles the addepisode command.
     * Reads podcast title (separate line), then id, duration, url (same line), then date.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void addEpisode(Scanner sc, VideoNetwork platform) {
        String title    = sc.nextLine().trim();
        String id       = sc.next();
        int    duration = sc.nextInt();
        String url      = sc.next();
        String date     = sc.next().trim();
        sc.nextLine();
        try {
            platform.addEpisode(title, id, duration, url, date);
            System.out.println(EPISODE_CREATED);
        } catch (InvalidDurationException     e) { System.out.println(INVALID_DURATION);     }
        catch  (TitleDoesNotExistsException   e) { System.out.println(PODCAST_DOES_NOT_EXIST);}
        catch  (IdAlreadyExistsException      e) { System.out.println(EPISODE_ID_EXIST);      }
        catch  (InvalidDateException          e) { System.out.println(WRONG_DATE_EPISODE);    }
    }

    /**
     * Handles the getpodcast command.
     * Prints podcast metadata, optional latest episode date, and optional tags.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void getPodcast(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        try {
            Podcast pod = platform.getPodcast(title);
            System.out.println("Podcast: " + pod.getTitle()
                    + " Author: " + pod.getAuthor()
                    + " Language: " + pod.getLanguage().getLanguage().toUpperCase());

            Iterator<Episode> epIt = pod.getEpisodesIterator();
            if (epIt.hasNext())
                System.out.println("Latest episode date: " + epIt.next().getReleaseDate());

            // Print tags in alphabetical order if any exist (new in phase 2)
            Iterator<String> tagIt = platform.getTagsForTitle(title);
            if (tagIt.hasNext()) {
                System.out.println("Tags:");
                while (tagIt.hasNext())
                    System.out.println(tagIt.next());
            }
        } catch (TitleDoesNotExistsException e) { System.out.println(PODCAST_DOES_NOT_EXIST); }
    }

    /**
     * Handles the episodes command.
     * Lists all episodes of a podcast in reverse chronological order.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void episodesList(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        try {
            Podcast pod = platform.getPodcast(title);
            Iterator<Episode> it = pod.getEpisodesIterator();
            if (!it.hasNext()) {
                System.out.println(HAS_NO_EPISODE);
                return;
            }
            System.out.println("Episodes for podcast " + pod.getTitle() + ":");
            while (it.hasNext()) {
                Episode ep = it.next();
                System.out.println("Episode " + ep.getId() + ": "
                        + ep.getDuration() + " min Date: " + ep.getReleaseDate());
                System.out.println("URL: " + ep.getUrl());
            }
        } catch (TitleDoesNotExistsException e) { System.out.println(PODCAST_DOES_NOT_EXIST); }
    }

    /**
     * Handles the authorpodcasts command.
     * Lists all podcasts by the given author in insertion order.
     * Always succeeds — prints a message if no podcasts are found.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void podcastList(Scanner sc, VideoNetwork platform) {
        String author = sc.nextLine().trim();
        // FIXED: now uses Iterator returned by the domain layer instead of old Array logic
        Iterator<Podcast> it = platform.authorPodcasts(author);
        if (!it.hasNext()) {
            System.out.println(NO_PODCAST_AUTHOR);
            return;
        }
        // Print header using the input name (as specified: "Podcasts by author <name>")
        System.out.println("Podcasts by author " + author + ":");
        while (it.hasNext()) {
            Podcast p = it.next();
            System.out.println("Podcast: " + p.getTitle()
                    + " Author: " + p.getAuthor()
                    + " Language: " + p.getLanguage().getLanguage().toUpperCase());
        }
    }

    /**
     * Handles the removepodcast command.
     * Removes a podcast and all its episodes from the system.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void removePodcast(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        try {
            platform.removePodcast(title);
            System.out.println(REMOVE_PODCAST);
        } catch (PodcastDoesNotExistsException e) { System.out.println(PODCAST_DOES_NOT_EXIST); }
    }

    /**
     * Handles the createshow command.
     * Reads author (separate line), then video id and transmission date (same line).
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void createShow(Scanner sc, VideoNetwork platform) {
        String author = sc.nextLine().trim();
        String videoId = sc.next();
        String date    = sc.next();
        sc.nextLine();
        try {
            platform.createShow(author, videoId, date);
            System.out.println(SHOW_CREATED);
        } catch (VideoForShowDoesNotExistException e) { System.out.println(SHOW_VIDEO_DOES_NOT_EXIST); }
        catch  (ShowAlreadyExistsException         e) { System.out.println(SHOW_ALREADY_EXISTS);       }
    }

    /**
     * Handles the getshow command.
     * Prints show metadata and optional tags. Display logic is in the domain layer.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void getShow(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        try {
            platform.getShow(title);
        } catch (ShowDoesNotExistException e) { System.out.println(SHOW_DOES_NOT_EXIST); }
    }

    /**
     * Handles the authorshows command.
     * Lists all shows by the given author ordered by date then title.
     * Always succeeds.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void authorShows(Scanner sc, VideoNetwork platform) {
        String author = sc.nextLine().trim();
        platform.authorShows(author);
    }

    /**
     * Handles the removeshow command.
     * Removes the show but leaves the underlying video intact.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void removeShow(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        try {
            platform.removeShow(title);
            System.out.println(SHOW_REMOVED);
        } catch (ShowDoesNotExistException e) { System.out.println(SHOW_DOES_NOT_EXIST); }
    }

    /**
     * Handles the removevideo command.
     * Episodes and videos used in shows cannot be removed.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void removeVideo(Scanner sc, VideoNetwork platform) {
        String id = sc.next();
        sc.nextLine();
        try {
            platform.removeVideo(id);
            System.out.println(VIDEO_REMOVED);
        } catch (VideoDoesNotExistException    e) { System.out.println(VIDEO_DOES_NOT_EXIST);    }
        catch  (CannotRemoveEpisodeException   e) { System.out.println(CANNOT_REMOVE_EPISODE);   }
        catch  (CannotRemoveShowVideoException e) { System.out.println(CANNOT_REMOVE_SHOW_VIDEO);}
    }

    /**
     * Handles the authorsproductivity command.
     * Delegates entirely to the domain layer which computes and prints the ranking.
     * Always succeeds.
     *
     * @param platform video network
     */
    private static void authorProductivity(VideoNetwork platform) {
        platform.authorsProductivity();
    }

    /**
     * Handles the addtag command.
     * Reads title and tag on separate lines.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void addTag(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        String tag   = sc.nextLine().trim();
        try {
            platform.addTag(title, tag);
            System.out.println(TAGGED_SUCCESS);
        } catch (TitleDoesNotExistsException e) { System.out.println(TITLE_DOES_NOT_EXIST);        }
        catch  (TaggedException              e) { System.out.println(TITLE_ALREADY_TAGGED + tag);  }
    }

    /**
     * Handles the removetag command.
     * Reads title and tag on separate lines.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void removeTag(Scanner sc, VideoNetwork platform) {
        String title = sc.nextLine().trim();
        String tag   = sc.nextLine().trim();
        try {
            platform.removeTag(title, tag);
            System.out.println(TAG_REMOVED);
        } catch (TitleDoesNotExistsException e) { System.out.println(TITLE_DOES_NOT_EXIST);     }
        catch  (TagNotPresentException       e) { System.out.println(TITLE_NOT_TAGGED + tag);   }
    }

    /**
     * Handles the tagged command.
     * Reads tag, content filter (SHOW/PODCAST/ALL), and order (ASC/DES) on the same line.
     *
     * @param sc       input scanner
     * @param platform video network
     */
    private static void contentTagged(Scanner sc, VideoNetwork platform) {
        String tag     = sc.next();
        String content = sc.next();
        String order   = sc.next();
        sc.nextLine();
        try {
            platform.tagged(tag, content, order);
        } catch (InvalidTagParametersException e) { System.out.println(INVALID_TAG_PARAMETERS);     }
        catch  (NoContentTaggedException       e) { System.out.println(NOTHING_TAGGED + tag + "."); }
    }

    /**
     * Handles the help command. Prints the full list of available commands.
     */
    private static void help() {
        System.out.println(HELP_MENU_MESSAGE);
    }
}