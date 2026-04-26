/*
** Nicolas Nº74517 and Mahir Nº70217
* FCT LEI OOP 2026


 */

import dataStructures.*;
import youVideo.*;

import java.util.Locale;
import java.util.Scanner;
public class Main {
    //Commands
    public static final String CMD_CREATE_PUBLISHABLE = "createpublishable";
    public static final String CMD_CREATE_PREMIUM = "createpremium";
    public static final String CMD_ADD_SUB = "addsubtitle";
    public static final String CMD_GET_VIDEO = "getvideo";
    public static final String CMD_SUBTITLE = "subtitles";
    public static final String CMD_CREATE_PODCAST = "createpodcast";
    public static final String CMD_ADD_EPISODE = "addepisode";
    public static final String CMD_GET_PODCAST = "getpodcast";
    public static final String CMD_EPISODES = "episodes";
    public static final String CMD_AUTHOR_PODCAST = "authorpodcasts";
    public static final String CMD_REMOVE_PODCAST = "removepodcast";
    public static final String CMD_CREATE_SHOW = "createshow";
    public static final String CMD_GET_SHOW = "getshow";
    public static final String CMD_REMOVE_SHOW = "removeshow";
    public static final String CMD_REMOVE_VIDEO = "removevideo";
    public static final String CMD_HELP = "help";
    public static final String CMD_EXIT = "exit";
    public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";


    //Error message
    public static final String INVALID_LANGUAGE = "Invalid language type.";
    public static final String INVALID_DURATION = "Invalid value.";
    public static final String ID_ALREADY_EXISTS = "Video with this ID already exists.";
    public static final String INVALID_LANGUAGE_SUBTITLE = "Invalid language type in subtitle.";
    public static final String VIDEO_DOES_NOT_EXIST = "Video does not exist.";
    public static final String NOT_A_PREMIUM_VIDEO = "This operation requires a Premium video.";
    public static final String CREATED_SUB = "Subtitle added successfully.";
    public static final String INVALID_PUBLISHABLE_VIDEO_1 = "Publishable Video ";
    public static final String INVALID_PUBLISHABLE_VIDEO_2 = " does not exist.";
    public static final String NO_PREMIUM_VIDEO = "No Premium Video with ID.";
    public static final String TITLE_ALREADY_USED = "Podcast with this title already exists.";
    public static final String PODCAST_CREATED = "Podcast created successfully.";
    public static final String EPISODE_CREATED = "Episode added successfully.";
    public static final String PODCAST_DOES_NOT_EXIST = "Podcast does not exist.";
    public static final String EPISODE_ID_EXIST = "Episode ID already exists in the system.";
    public static final String WRONG_DATE_EPISODE = "Episode date must be >= than latest episode date.";
    public static final String HAS_NO_EPISODE = "No episodes available for this podcast.";
    public static final String NO_PODCAST_AUTHOR = "No podcasts found for this author.";
    public static final String REMOVE_PODCAST = "Podcast removed successfully.";
    public static final String SHOW_VIDEO_DOES_NOT_EXIST = "Video for show does not exist.";
    public static final String SHOW_ALREADY_EXISTS = "Show with this title already exists.";
    public static final String SHOW_CREATED = "Show created successfully.";
    public static final String SHOW_DOES_NOT_EXIST = "Show does not exist.";
    public static final String SHOW_REMOVED = "Show removed successfully.";
    public static final String CANNOT_REMOVE_EPISODE_VIDEO = "Cannot remove: video is an episode of a podcast.";
    public static final String CANNOT_REMOVE_SHOW_VIDEO = "Cannot remove: video is used in a show.";
    public static final String VIDEO_REMOVED = "Video removed successfully.";
    public static final String SUBTITLE_LIST_HEADER = "Subtitles for video ";
    public static final String VIDEO_CREATED_SUCCESS = " created successfully.";
    public static final String END_PROGRAM = "Bye!";





    public static void main(String[] args){
        Locale.setDefault(Locale.of("en", "GB"));
        Scanner sc = new Scanner(System.in);
        Array<VideoStructure> videos = new ArrayClass<>();
        Array<Podcasts> podcast = new ArrayClass<>();
        Array<Shows> show = new ArrayClass<>();
        commandInterpreter(sc,videos, podcast, show);

    }

    /**
     * Main command loop that interprets and dispatches user instructions.
     * It remains active until the exit command is received, routing each
     * input to its respective handler method.
     * @param sc the scanner to read user commands
     * @param videos the global list of videos
     * @param podcast the global list of podcasts
     * @param show the global list of shows
     * @pre sc != null && videos != null && podcast != null && show != null
     */
    private static void commandInterpreter(Scanner sc, Array<VideoStructure> videos, Array<Podcasts> podcast, Array<Shows> show){
        String commands = sc.next().toLowerCase();

        while(!commands.equals(CMD_EXIT)){
            switch(commands){
               case CMD_CREATE_PUBLISHABLE -> addPublishable(sc, videos);
               case CMD_CREATE_PREMIUM -> addPremium(sc, videos);
               case CMD_ADD_SUB -> addSub(sc, videos);
               case CMD_GET_VIDEO -> getVideo(sc, videos);
               case CMD_SUBTITLE -> subtitleList(sc,videos);
               case CMD_CREATE_PODCAST -> addPodcast(sc, podcast);
               case CMD_ADD_EPISODE -> addEpisode(sc, videos, podcast);
               case CMD_GET_PODCAST -> getPodcast(sc,podcast);
               case CMD_EPISODES -> episodesList(sc,podcast);
               case CMD_AUTHOR_PODCAST -> podcastList(sc,podcast);
               case CMD_REMOVE_PODCAST -> removePodcast(sc,videos,podcast);
               case CMD_CREATE_SHOW -> createshow(sc,videos,show,podcast);
               case CMD_GET_SHOW -> getShow(sc,show);
               case CMD_REMOVE_SHOW -> removeShow(sc,show);
               case CMD_REMOVE_VIDEO -> removeVideo(sc,videos,show);
               case CMD_HELP -> help();
               default -> System.out.println(UNKNOWN_COMMAND);
            }
            commands = sc.next().toLowerCase();

        }
        System.out.println(END_PROGRAM);
        sc.close();
    }

    /* =================================================================
                             COMMANDS METHODS
       =================================================================
    */

    /**
     * Executes the command to create and add a new publishable video.
     * Validates ID uniqueness, duration, and language code existence.
     * @param sc the scanner to read video details (ID, duration, URL, publisher, title, language)
     * @param video the global list of videos to store the new record
     * @pre sc != null && videos != null
     */
    private static void addPublishable(Scanner sc, Array<VideoStructure> video){
        String id = sc.next();
        int duration = sc.nextInt();
        String url = sc.next();
        sc.nextLine();
        String publisher = sc.nextLine();
        String title = sc.nextLine();
        String languageCode = sc.nextLine();
        if(!isLanguageValid(languageCode)){
            System.out.println(INVALID_LANGUAGE);
            return;
        }
        if(duration <= 0) {
            System.out.println(INVALID_DURATION);
            return;
        }
        if(idAlreadyExists(id, video)){
            System.out.println(ID_ALREADY_EXISTS);
            return;
        }

        PublishableVideos pubVideos = new PublishableVideos(id,duration,url,publisher,title,languageCode);
        video.insertLast(pubVideos);


        System.out.println("Video " + id + VIDEO_CREATED_SUCCESS);
    }
    /**
     * Executes the command to create and add a new premium video.
     * Validates ID uniqueness, duration, primary language code, and initial subtitle language.
     * @param sc the scanner to read premium video details (ID, duration, URL,etc.)
     * @param video the global list of videos to store the new premium record
     * @pre sc != null && video != null
     */
    private static void addPremium(Scanner sc, Array<VideoStructure> video){
        String id = sc.next();
        int duration = sc.nextInt();
        String url  = sc.next();
        sc.nextLine();
        String publisher = sc.nextLine();
        String title = sc.nextLine();
        String languageCode = sc.nextLine();
        String initSubUrl = sc.nextLine();
        String initLanguageCode = sc.nextLine().trim();
        if(!isLanguageValid(languageCode)){
            System.out.println(INVALID_LANGUAGE);
            return;
        }
        if(!isLanguageValid(initLanguageCode)){
            System.out.println(INVALID_LANGUAGE_SUBTITLE);
            return;
        }
        if(duration <= 0) {
            System.out.println(INVALID_DURATION);
            return;
        }
        if(idAlreadyExists(id, video)){
            System.out.println(ID_ALREADY_EXISTS);
            return;
        }
        PremiumVideos premiumVideos = new PremiumVideos(id,duration,url,publisher,title,languageCode,initLanguageCode,initSubUrl);
        video.insertLast(premiumVideos);

        System.out.println("PREMIUM Video " + id + VIDEO_CREATED_SUCCESS);

    }
    /**
     * Executes the command to add a new subtitle to an existing premium video.
     * Verifies if the language code is valid, if the video exists, and if it is a premium type.
     * @param sc the scanner to read subtitle details (video ID, subtitle URL, and language code)
     * @param videos the global list of videos to search for the target video
     * @pre sc != null && videos != null
     */

    private static void addSub(Scanner sc,Array<VideoStructure> videos){
        String id = sc.next();
        String  languageUrl = sc.next();
        String languageCode = sc.next();
        sc.nextLine();
        VideoStructure videoStructure = getVideoById(id,videos);
        if(!isLanguageValid(languageCode)){
            System.out.println(INVALID_LANGUAGE_SUBTITLE);
            return;
        }
        if(videoStructure == null){
            System.out.println(VIDEO_DOES_NOT_EXIST);
            return;
        }

        if(!(videoStructure instanceof PremiumVideos premiumVideos)){
            System.out.println(NOT_A_PREMIUM_VIDEO);
            return;
        }
        premiumVideos.addSubtitle(languageCode, languageUrl);

        System.out.println(CREATED_SUB);
    }

    /**
     * Executes the command to display detailed information about a specific video.
     * Verifies if the video exists in the system before presentation.
     * @param sc the scanner to read the target video ID
     * @param videos the global list of videos to search in
     * @pre sc != null && videos != null
     */
    private static void getVideo(Scanner sc, Array<VideoStructure> videos){
        String id = sc.next();
        sc.nextLine();
        VideoStructure videoStructure = getVideoById(id,videos);
        //Increment a statement if the id is from podcasts episode
        if(videoStructure == null || videoStructure instanceof Episode){
            System.out.println(INVALID_PUBLISHABLE_VIDEO_1 + id + INVALID_PUBLISHABLE_VIDEO_2);
            return;
        }
        //executed by polymorphism
       videoStructure.display();


    }
    /**
     * Executes the command to list all subtitles of a specific premium video.
     * Verifies if the video exists and if it is of premium type before iterating through subtitles.
     * @param sc the scanner to read the video ID
     * @param videos the global list of videos to search in
     * @pre sc != null && videos != null
     */
    private static void subtitleList(Scanner sc, Array<VideoStructure> videos){
        String id = sc.next();
        VideoStructure video = getVideoById(id, videos);

        if(!(video instanceof PremiumVideos premiumVideos)){
            System.out.println(NO_PREMIUM_VIDEO);
            return;
        }

        System.out.println(SUBTITLE_LIST_HEADER + premiumVideos.getTitle() + ":");

        Iterator<Subtitles> it = premiumVideos.getSubtitles().iterator();
        while(it.hasNext()){
            Subtitles sub = it.next();
            System.out.println("- " + sub.getUrl() + " (" + sub.getLanguage().getDisplayLanguage(Locale.ENGLISH).toUpperCase() + ")");        }
    }

    /**
     * Executes the command to register a new podcast in the system.
     * Validates that the podcast title is unique before creation.
     * @param sc the scanner to read podcast details (title, author, language)
     * @param podcast the global list of podcasts to store the new record
     * @pre sc != null && podcast != null
     */
    private static void addPodcast(Scanner sc, Array<Podcasts> podcast){
        String title = sc.nextLine().trim();
        String author = getExistingAuthorName(sc.nextLine(), podcast);
        String language = sc.next();
        sc.nextLine();
        if(!isLanguageValid(language)){
            System.out.println(INVALID_LANGUAGE);
            return;
        }
        if(titleAlreadyExist(title, podcast)){
            System.out.println(TITLE_ALREADY_USED);
            return;
        }
        Podcasts pod = new Podcasts(title, author, language);
        podcast.insertLast(pod);
        System.out.println(PODCAST_CREATED);
    }
    /**
     * Executes the command to add a new episode to an existing podcast.
     * Validates duration, podcast existence, ID uniqueness, and chronological order.
     * * @param sc the scanner to read episode details (title, ID, duration, URL, date)
     * @param video the global list of videos to ensure ID uniqueness
     * @param podcast the list of podcasts to find the target podcast
     * @pre sc != null && video != null && podcast != null
     */
    private static void addEpisode(Scanner sc, Array<VideoStructure> video, Array<Podcasts> podcast){
        String title = sc.nextLine().trim();
        String id = sc.next();
        int duration = sc.nextInt();
        String url = sc.next();
        String date = sc.next().trim();
        sc.nextLine();
        if(duration <= 0){
            System.out.println(INVALID_DURATION);
            return;
        }
        if(!titleAlreadyExist(title,podcast)){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        if(idAlreadyExists(id,video)){
            System.out.println(EPISODE_ID_EXIST);
            return;
        }

        Podcasts pod = getPodcastByTitle(title,podcast);

        //The recent episode are in position 0
        if(pod.getEpisode().size() > 0){
            String lastestDate = pod.getEpisode().get(0).getReleaseDate();
            if(date.compareTo(lastestDate) < 0){
                System.out.println(WRONG_DATE_EPISODE);
                return;
            }

        }
        Episode episode = new Episode(id,duration,url,date);
        pod.addEpisode(episode);
        video.insertLast(episode);
        System.out.println(EPISODE_CREATED);
    }
    /**
     * Executes the command to display general information about a podcast.
     * Shows title, author, language, and the release date of the latest episode if available.
     * @param sc the scanner to read the podcast title
     * @param pod the list of podcasts to search in
     * @pre sc != null && pod != null
     */
    private static void getPodcast(Scanner sc, Array<Podcasts> pod){
        String title = sc.nextLine().trim();
        if(!titleAlreadyExist(title, pod)){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        Podcasts podcast = getPodcastByTitle(title,pod);
        System.out.println("Podcast: " + podcast.getTitle() + " Author: "+ podcast.getAuthor() + " Language: " + podcast.getLanguage().getLanguage().toUpperCase());
        if(podcast.getEpisode().size() > 0)
        System.out.println("Latest episode date: " + podcast.getEpisode().get(0).getReleaseDate());

    }
    /**
     * Executes the command to list all episodes associated with a specific podcast.
     * Validates the existence of the podcast and checks if it contains any episodes.
     * @param sc the scanner to read the podcast title
     * @param podcast the list of podcasts to search in
     * @pre sc != null && podcast != null
     */
    private static void episodesList(Scanner sc, Array<Podcasts> podcast){
        String title = sc.nextLine().trim();

        if(!titleAlreadyExist(title, podcast)){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        Podcasts pod = getPodcastByTitle(title,podcast);
        if(pod.getEpisode().size() <= 0){
            System.out.println(HAS_NO_EPISODE);
            return;
        }

        System.out.println("Episodes for podcast " + title + ":");
        Iterator<Episode> it = pod.getEpisode().iterator();
        while(it.hasNext()){
            Episode ep = it.next();
            System.out.println("Episode " + ep.getId() + ": " + ep.getDuration() + " min Date: " + ep.getReleaseDate());
            System.out.println("URL: " + ep.getUrl());
        }

    }
    /**
     * Executes the command to list all podcasts created by a specific author.
     * Filters the global podcast list and displays details for each match found.
     * @param sc the scanner to read the author's name
     * @param podcast the global list of podcasts to filter
     * @pre sc != null && podcast != null
     */
    private static void podcastList(Scanner sc, Array<Podcasts> podcast){
        String author = sc.nextLine().trim();
        Array<Podcasts> authorPod = getPodcastByAuthor(author, podcast);
        if(authorPod.size() == 0){
            System.out.println(NO_PODCAST_AUTHOR);
            return;
        }
        System.out.println("Podcasts by author " + author + ":");
        Iterator<Podcasts> it = authorPod.iterator();
        while(it.hasNext()){
            Podcasts p = it.next();
            System.out.println("Podcast: " + p.getTitle() + " Author: " + p.getAuthor() + " Language: " + p.getLanguage().getLanguage().toUpperCase());
        }

    }
    /**
     * Executes the command to remove a podcast and all its associated episodes from the system.
     * Ensures that episodes are removed from the global video registry as well as the podcast list.
     * @param sc the scanner to read the podcast title
    * @param video the global list of videos (to remove associated episodes)
    * @param podcast the global list of podcasts to remove the podcast from
    * @pre sc != null && video != null && podcast != null
            */
    private static void removePodcast(Scanner sc,Array<VideoStructure> video ,Array<Podcasts> podcast){
        String title = sc.nextLine().trim();
        Podcasts pod = getPodcastByTitle(title, podcast);
        if(pod == null){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        //remove all the episodes from the podcast
        Iterator<Episode> it = pod.getEpisode().iterator();
        //Remove from global data the episodes
        while(it.hasNext()){
            Episode ep = it.next();
            int pos = video.searchIndexOf(ep);
            if(pos != -1)
            video.removeAt(pos);
        }
        //Remove the podcast
        int podPosition = podcast.searchIndexOf(pod);
        podcast.removeAt(podPosition);
        System.out.println(REMOVE_PODCAST);

    }
    /**
     * Executes the command to create a show from an existing publishable video.
     * Validates show title uniqueness and ensures the base video exists.
     * @param sc the scanner to read show details (author, video ID, transmission date)
     * @param videos the global list of videos to find the base video
     * @param showStructure the global list of shows to store the new record
     * @pre sc != null && videos != null && showStructure != null
     */
    private static void createshow(Scanner sc , Array <VideoStructure> videos, Array<Shows> showStructure, Array<Podcasts> podcast) {
        String author = sc.nextLine().trim();
        author = getExistingAuthorName(author, podcast);
        author = getExistingAuthorName(author, showStructure);
        String videoId = sc.next();
        String transmissionDate = sc.next();
        sc.nextLine();
        VideoStructure videoStructure = getVideoById(videoId, videos);
        if (videoStructure == null || videoStructure instanceof Episode) {
            System.out.println(SHOW_VIDEO_DOES_NOT_EXIST);
            return;
        }
        PublishableVideos pubVideo = (PublishableVideos) videoStructure;
        String showTitle = pubVideo.getTitle();
        Iterator<Shows> it = showStructure.iterator();
        while(it.hasNext()){
            Shows show = it.next();
            if(show.getTitle().equalsIgnoreCase(showTitle)){
                System.out.println(SHOW_ALREADY_EXISTS);
                return;
            }
        }
        Shows newShow = new Shows(author, pubVideo, transmissionDate);
        showStructure.insertLast(newShow);
        System.out.println(SHOW_CREATED);
    }
    /**
     * Executes the command to display detailed information about a specific show.
     * Searches for the show by title and presents its transmission date and author.
     * @param sc the scanner to read the show title
     * @param showStructure the list of shows to search in
     * @pre sc != null && showStructure != null
     */
    private static void getShow(Scanner sc, Array<Shows> showStructure){
        String title = sc.nextLine().trim();
        Iterator<Shows> it = showStructure.iterator();
        while(it.hasNext()){
            Shows show = it.next();
            if(show.getTitle().equalsIgnoreCase(title)){
                System.out.println("Show Date: " + show.getTransmissionDate() + " Author: " + show.getAuthor().trim());
                System.out.println("Video: " + show.getTitle());
                return;
            }
        }
        System.out.println(SHOW_DOES_NOT_EXIST);
    }

    /**
     * Executes the command to remove a show from the system.
     * Iterates through the list to find the show by title and removes it from its specific position.
     * @param sc the scanner to read the show title to be removed
     * @param showStructure the list of shows to modify
     * @pre sc != null && showStructure != null
     */
    private static void removeShow(Scanner sc, Array<Shows> showStructure){
        String title = sc.nextLine().trim();
        Iterator<Shows> it = showStructure.iterator();
        int position = 0;
        while(it.hasNext()){
            Shows currentShow = it.next();
            if(currentShow.getTitle().equalsIgnoreCase(title)){
                showStructure.removeAt(position);
                System.out.println(SHOW_REMOVED);
                return;
            }
            position++;
        }
        System.out.println(SHOW_DOES_NOT_EXIST);
    }

    /**
     * Executes the command to remove a video from the system if it's not in use
     * @param sc the scanner to read the video ID
     * @param videos the global list of videos
     * @param showStructure the list of shows to check for video usage
     */
    private static void removeVideo(Scanner sc, Array<VideoStructure> videos, Array<Shows> showStructure){
        String videoId = sc.next();
        VideoStructure video = getVideoById(videoId,videos);
        if(video == null) {
            System.out.println(VIDEO_DOES_NOT_EXIST);
            return;
        }
        if(video instanceof Episode){
            System.out.println(CANNOT_REMOVE_EPISODE_VIDEO);
            return;
        }
        Iterator<Shows> it = showStructure.iterator();
        while(it.hasNext()){
            Shows show = it.next();
            if(show.getvideo().getId().equalsIgnoreCase(videoId)) {
                System.out.println(CANNOT_REMOVE_SHOW_VIDEO);
                return;
            }
        }
        int position =-1;
        for(int i = 0; i < videos.size();i++){
            VideoStructure v = videos.get(i);
            if(v.getId().equalsIgnoreCase(videoId)){
                position = i;
                break;
            }
        }
        if(position!=-1){
            videos.removeAt(position);
            System.out.println(VIDEO_REMOVED);
        }
    }


    /**
     * Displays the list of all available commands and their descriptions
     */
    private static void help(){
        System.out.println("createpublishable - creates a new publishable video");
        System.out.println("createpremium - creates a new publishable Premium video");
        System.out.println("addsubtitle - adds subtitle to Premium video");
        System.out.println("getvideo - presents publishable video data from its id");
        System.out.println("subtitles - Lists Premium video subtitles");
        System.out.println("createpodcast - creates a new podcast with no episodes");
        System.out.println("addepisode - adds an episode to a podcast");
        System.out.println("getpodcast - presents podcast data from its title");
        System.out.println("episodes - List podcast episodes");
        System.out.println("authorpodcasts - List all podcasts of an author");
        System.out.println("removepodcast - removes a podcast");
        System.out.println("createshow - creates show using an existing publishable video");
        System.out.println("getshow - presents show data from its title");
        System.out.println("removeshow - removes a show");
        System.out.println("removevideo - removes a publishable video");
        System.out.println("help - shows the available commands");
        System.out.println("exit - terminates the execution of the program");
    }

    /*
         ----------------------------------------------------------------------------
                                      AUXILIARY METHODS
         ----------------------------------------------------------------------------
    */

    /**
     * Checks if a video ID is already in use within the global list
     * @param id the unique identifier to verify
     * @param videos the global list of videos
     * @return {@code true} if the ID exists (case-insensitive), {@code false} otherwise
     * @pre id != null && videos != null
     */
    private static boolean idAlreadyExists(String id, Array<VideoStructure> videos) {
        Iterator<VideoStructure> it = videos.iterator();
        while (it.hasNext()) {
            VideoStructure v = it.next();
            if (v.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Verifies if the provided language code exists in the ISO 639 standard library
     * @param code the language code to validate
     * @return true if the language exists, false otherwise
     * @pre code != null
     */
    private static boolean isLanguageValid(String code) {
        String[] languages = Locale.getISOLanguages();

        for (String lang : languages) {
            if (lang.equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a video from the list based on its unique identifier
     * @param id the unique ID of the video to search for
     * @param video the global list of videos
     * @return the video object if found, or  null if the ID does not exist
     * @pre id != null && video != null
     */
    private static VideoStructure getVideoById(String id,Array<VideoStructure> video){
        Iterator<VideoStructure> it = video.iterator();
        while(it.hasNext()){
            VideoStructure v = it.next();
            if(v.getId().equalsIgnoreCase(id))
                return v;
        }
        return null;

    }

    /**
     * Checks if a podcast title is already in use within the system
     * @param title the title to verify
     * @param podcast the global list of podcasts
     * @return true if the title already exists (case-insensitive), false otherwise
     * @pre title != null && podcast != null
     */
    private static boolean titleAlreadyExist(String title, Array<Podcasts> podcast){
        Iterator<Podcasts> it = podcast.iterator();
        while(it.hasNext()){
            Podcasts pod = it.next();
            if(pod.getTitle().equalsIgnoreCase(title))
                return true;

        }
        return false;
    }

    /**
     * Searches for a podcast by its title
     * @param title the title of the podcast to search for (case-insensitive)
     * @param pod the global list of podcasts to search in
     * @return the podcast object if found, or null if no podcast exists with that title
     * @pre title != null && pod != null
     */
    private static Podcasts getPodcastByTitle(String title,Array<Podcasts> pod){
        Iterator<Podcasts> it = pod.iterator();
        while(it.hasNext()){
            Podcasts p = it.next();
            if(p.getTitle().equalsIgnoreCase(title)){
                return p;
            }
        }
        return null;
    }

    /**
     * Search for all podcasts associated with a specific author.
     * @param author  name of author to search (case-sensitive)
     * @param pod The global list of podcasts where the research will be carried out
     * @return An array containing all podcasts found by the author;
     * return an empty array if no array is found.
     * @pre author != null && pod != null
     */
    private static Array<Podcasts> getPodcastByAuthor(String author, Array<Podcasts> pod){
        Array<Podcasts> result = new ArrayClass<>();
        Iterator<Podcasts> it = pod.iterator();
        while(it.hasNext()){
            Podcasts p = it.next();
            if(p.getAuthor().equalsIgnoreCase(author)){
                result.insertLast(p);
            }

        }
        return result;

    }

    /**
     * Searches for the original author name in the collection to maintain data consistency.
     * @param name the author name to validate
     * @param collection List of Authored objects to search
     * @return the name as it was first stored, or the input name if not found
     */
    private static <T extends Authored> String getExistingAuthorName(String name, Array<T> collection) {
        Iterator<T> it = collection.iterator();
        while(it.hasNext()){
            T item = it.next();
            if(item.getAuthor().equalsIgnoreCase(name)) {
                return item.getAuthor();
            }
        }
        return name;
    }



}

