/*
** Nicolas Nº74517 and Mahir Nº70217
* FCT LEI OOP 2026


 */
import youVideo.*;
import youVideo.Exceptions.*;
import youVideo.iterators.SubtitleListIterator;

import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
public class Main {
    //Commands
    private static final String CMD_CREATE_PUBLISHABLE = "createpublishable";
    private static final String CMD_CREATE_PREMIUM = "createpremium";
    private static final String CMD_ADD_SUB = "addsubtitle";
    private static final String CMD_GET_VIDEO = "getvideo";
    private static final String CMD_SUBTITLE = "subtitles";
    private static final String CMD_CREATE_PODCAST = "createpodcast";
    private static final String CMD_ADD_EPISODE = "addepisode";
    private static final String CMD_GET_PODCAST = "getpodcast";
    private static final String CMD_EPISODES = "episodes";
    private static final String CMD_AUTHOR_PODCAST = "authorpodcasts";
    private static final String CMD_REMOVE_PODCAST = "removepodcast";
    private static final String CMD_CREATE_SHOW = "createshow";
    private static final String CMD_GET_SHOW = "getshow";
    private static final String CMD_REMOVE_SHOW = "removeshow";
    private static final String CMD_REMOVE_VIDEO = "removevideo";
    private static final String CMD_ADD_TAG = "addtag";
    private static final String CMD_REMOVE_TAG = "removetag";
    private static final String CMD_TAGGED = "tagged";
    private static final String CMD_AUTHOR_PRODUCTIVITY = "authorsproductivity";
    private static final String CMD_HELP = "help";
    private static final String CMD_EXIT = "exit";
    private static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";


    //Error message
    private static final String INVALID_LANGUAGE = "Invalid language type.";
    private static final String INVALID_DURATION = "Invalid value.";
    private static final String ID_ALREADY_EXISTS = "Video with this ID already exists.";
    private static final String INVALID_LANGUAGE_SUBTITLE = "Invalid language type in subtitle.";
    private static final String VIDEO_DOES_NOT_EXIST = "Video does not exist.";
    private static final String NOT_A_PREMIUM_VIDEO = "This operation requires a Premium video.";
    private static final String CREATED_SUB = "Subtitle added successfully.";
    private static final String INVALID_PUBLISHABLE_VIDEO_1 = "Publishable Video ";
    private static final String INVALID_PUBLISHABLE_VIDEO_2 = " does not exist.";
    private static final String NO_PREMIUM_VIDEO = "No Premium Video with ID.";
    private static final String TITLE_ALREADY_USED = "Podcast with this title already exists.";
    private static final String PODCAST_CREATED = "Podcast created successfully.";
    private static final String EPISODE_CREATED = "Episode added successfully.";
    private static final String PODCAST_DOES_NOT_EXIST = "Podcast does not exist.";
    private static final String EPISODE_ID_EXIST = "Episode ID already exists in the system.";
    private static final String WRONG_DATE_EPISODE = "Episode date must be >= than latest episode date.";
    private static final String HAS_NO_EPISODE = "No episodes available for this podcast.";
    private static final String NO_PODCAST_AUTHOR = "No podcasts found for this author.";
    private static final String REMOVE_PODCAST = "Podcast removed successfully.";
    private static final String SHOW_VIDEO_DOES_NOT_EXIST = "Video for show does not exist.";
    private static final String SHOW_ALREADY_EXISTS = "Show with this title already exists.";
    private static final String SHOW_CREATED = "Show created successfully.";
    private static final String SHOW_DOES_NOT_EXIST = "Show does not exist.";
    private static final String SHOW_REMOVED = "Show removed successfully.";
    private static final String CANNOT_REMOVE_EPISODE_VIDEO = "Cannot remove: video is an episode of a podcast.";
    private static final String CANNOT_REMOVE_SHOW_VIDEO = "Cannot remove: video is used in a show.";
    private static final String VIDEO_REMOVED = "Video removed successfully.";
    public static final String SUBTITLE_LIST_HEADER = "Subtitles for video ";
    private static final String VIDEO_CREATED_SUCCESS = " created successfully.";
    private static final String TAGGED = "Tag added successfully.";
    private static final String TITLE_DOES_NOT_EXIST = "Title does not exist.";
    private static final String TITLE_ALREADY_TAGGED = "Title is already tagged with ";
    private static final String TAG_REMOVED = "Tag removed successfully.";
    private static final String TITLE_NOT_TAGGED = "Title is not tagged with ";
    private static final String END_PROGRAM = "Bye!";





    public static void main(String[] args){
        Locale.setDefault(Locale.of("en", "GB"));
        Scanner sc = new Scanner(System.in);
       VideoNetwork video = new VideoNetworkClass();
        commandInterpreter(sc,video);

    }

    /**
     * Main command loop that interprets and dispatches user instructions.
     * It remains active until the exit command is received, routing each
     * input to its respective handler method.
     * @param sc the scanner to read user commands
     * @param video the global list of commands
     * @pre sc != null && videos != null && podcast != null && show != null
     */
    private static void commandInterpreter(Scanner sc, VideoNetwork video){
        String commands = sc.next().toLowerCase();

        while(!commands.equals(CMD_EXIT)){
            switch(commands){
               case CMD_CREATE_PUBLISHABLE -> addPublishable(sc, video);
               case CMD_CREATE_PREMIUM -> addPremium(sc, video);
               case CMD_ADD_SUB -> addSub(sc, video);
               case CMD_GET_VIDEO -> getVideo(sc, video);
               case CMD_SUBTITLE -> subtitleList(sc,video);
               case CMD_CREATE_PODCAST -> addPodcast(sc, video);
               case CMD_ADD_EPISODE -> addEpisode(sc, video);
               case CMD_GET_PODCAST -> getPodcast(sc,video);
               case CMD_EPISODES -> episodesList(sc,video);
               case CMD_AUTHOR_PODCAST -> podcastList(sc,video);
               case CMD_REMOVE_PODCAST -> removePodcast(sc,video);
               case CMD_CREATE_SHOW -> createshow(sc,video);
               case CMD_GET_SHOW -> getShow(sc,video);
               case CMD_REMOVE_SHOW -> removeShow(sc,video);
               case CMD_REMOVE_VIDEO -> removeVideo(sc,video);
                case CMD_AUTHOR_PRODUCTIVITY -> authorProductivity(sc,video);
               case CMD_ADD_TAG -> createTag(sc,video);
               case CMD_REMOVE_TAG -> removeTag(sc,video);
                case CMD_TAGGED -> tagged(sc,video);
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
    private static void addPublishable(Scanner sc, VideoNetwork video){
        String id = sc.next();
        int duration = sc.nextInt();
        String url = sc.next();
        sc.nextLine();
        String publisher = sc.nextLine();
        String title = sc.nextLine();
        String languageCode = sc.nextLine();
      try{
          video.createPublishable(id,duration,url,publisher,title,languageCode);
          System.out.println("Video " + id + VIDEO_CREATED_SUCCESS);
      }
      catch (InvalidLanguageException e) { System.out.println(INVALID_LANGUAGE); }
      catch (InvalidDurationException e) { System.out.println(INVALID_DURATION); }
      catch (IdAlreadyExistsException e) { System.out.println(ID_ALREADY_EXISTS); }


    }
    /**
     * Executes the command to create and add a new premium video.
     * Validates ID uniqueness, duration, primary language code, and initial subtitle language.
     * @param sc the scanner to read premium video details (ID, duration, URL,etc.)
     * @param video the global list of videos to store the new premium record
     * @pre sc != null && video != null
     */
    private static void addPremium(Scanner sc, VideoNetwork video){
        String id = sc.next();
        int duration = sc.nextInt();
        String url  = sc.next();
        sc.nextLine();
        String publisher = sc.nextLine();
        String title = sc.nextLine();
        String languageCode = sc.nextLine();
        String initSubUrl = sc.nextLine();
        String initLanguageCode = sc.nextLine().trim();

        try{
            video.createPremium(id, duration,url,publisher,title, languageCode,initSubUrl,initLanguageCode);
            System.out.println("PREMIUM Video " + id + VIDEO_CREATED_SUCCESS);
        }
          catch (InvalidLanguageException e) {System.out.println(INVALID_LANGUAGE);}
          catch (InvalidLanguageSubtitleException e) {System.out.println(INVALID_LANGUAGE_SUBTITLE);}
          catch (InvalidDurationException e) {System.out.println(INVALID_DURATION);}
          catch (IdAlreadyExistsException e){System.out.println(ID_ALREADY_EXISTS);}
    }
    /**
     * Executes the command to add a new subtitle to an existing premium video.
     * Verifies if the language code is valid, if the video exists, and if it is a premium type.
     * @param sc the scanner to read subtitle details (video ID, subtitle URL, and language code)
     * @param video the global list of videos to search for the target video
     * @pre sc != null && videos != null
     */

    private static void addSub(Scanner sc, VideoNetwork video){
        String id = sc.next();
        String  languageUrl = sc.next();
        String languageCode = sc.next();
        sc.nextLine();

        try{
            video.createSubtitle(id,languageUrl,languageCode);
            System.out.println(CREATED_SUB);
        }
           catch(InvalidLanguageSubtitleException e) { System.out.println(INVALID_LANGUAGE_SUBTITLE);}
           catch(VideoDoesNotExistException e){System.out.println(VIDEO_DOES_NOT_EXIST);}
           catch(NotAPremiumVideoException e){System.out.println(NOT_A_PREMIUM_VIDEO);}
    }

    /**
     * Executes the command to display detailed information about a specific video.
     * Verifies if the video exists in the system before presentation.
     * @param sc the scanner to read the target video ID
     * @param video the global list of videos to search in
     * @pre sc != null && videos != null
     */
    private static void getVideo(Scanner sc, VideoNetwork video){
        String id = sc.next();
        sc.nextLine();

        try{
            video.getVideo(id);
        }
        catch(InvalidPublishableVideoException e) {System.out.println(INVALID_PUBLISHABLE_VIDEO_1 + id + INVALID_PUBLISHABLE_VIDEO_2);}


    }
    /**
     * Executes the command to list all subtitles of a specific premium video.
     * Verifies if the video exists and if it is of premium type before iterating through subtitles.
     * @param sc the scanner to read the video ID
     * @param video the global list of videos to search in
     * @pre sc != null && videos != null
     */
    private static void subtitleList(Scanner sc, VideoNetwork video){
        String id = sc.next();
        try{
            PremiumVideos premiumVideo = video.getPremiumVideo(id);
            System.out.println(SUBTITLE_LIST_HEADER + premiumVideo.getTitle() + ":");
            Iterator<Subtitles> it = premiumVideo.getSubtitlesIterator();
            while(it.hasNext()){
                Subtitles sub = it.next();
                System.out.println("- " + sub.getUrl() + " (" + sub.getLanguage().getDisplayLanguage(Locale.ENGLISH).toUpperCase() + ")");
        }
        }
        catch(NotAPremiumVideoException e){System.out.println(NO_PREMIUM_VIDEO);}
    }

    /**
     * Executes the command to register a new podcast in the system.
     * Validates that the podcast title is unique before creation.
     * @param sc the scanner to read podcast details (title, author, language)
     * @param video the global list of podcasts to store the new record
     * @pre sc != null && podcast != null
     */
    private static void addPodcast(Scanner sc, VideoNetwork video){
        String title = sc.nextLine().trim();
        String author = sc.nextLine();
        String language = sc.next();
        sc.nextLine();
        try{
            video.createPodcast(title,author,language);
            System.out.println(PODCAST_CREATED);
        }
        catch(InvalidLanguageException e){System.out.println(INVALID_LANGUAGE);}
        catch(TitleAlreadyExistException e){System.out.println(TITLE_ALREADY_USED);}


    }
    /**
     * Executes the command to add a new episode to an existing podcast.
     * Validates duration, podcast existence, ID uniqueness, and chronological order.
     * @param sc the scanner to read episode details (title, ID, duration, URL, date)
     * @param video the global list of videos to ensure ID uniqueness
     * @pre sc != null && video != null && podcast != null
     */
    private static void addEpisode(Scanner sc,VideoNetwork video){
        String title = sc.nextLine().trim();
        String id = sc.next();
        int duration = sc.nextInt();
        String url = sc.next();
        String date = sc.next().trim();
        sc.nextLine();
        try{
            video.addEpisode(title,id,duration,url,date);
            System.out.println(EPISODE_CREATED);
        }
        catch(InvalidDurationException e) {System.out.println(INVALID_DURATION);}
        catch(TitleDoesNotExistsException e) {System.out.println(PODCAST_DOES_NOT_EXIST);}
       catch(IdAlreadyExistsException e) { System.out.println(EPISODE_ID_EXIST);}
       catch(InvalidDateException e) {System.out.println(WRONG_DATE_EPISODE);}
    }
    /**
     * Executes the command to display general information about a podcast.
     * Shows title, author, language, and the release date of the latest episode if available.
     * @param sc the scanner to read the podcast title
     * @param video the list of podcasts to search in
     * @pre sc != null && pod != null
     */
    private static void getPodcast(Scanner sc, VideoNetwork video){
        String title = sc.nextLine().trim();
        if(!titleAlreadyExist(title, pod)){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        PodcastClass podcast = getPodcastByTitle(title,pod);
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
    private static void episodesList(Scanner sc, Array<PodcastClass> podcast){
        String title = sc.nextLine().trim();

        if(!titleAlreadyExist(title, podcast)){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        PodcastClass pod = getPodcastByTitle(title,podcast);
        if(pod.getEpisode().size() <= 0){
            System.out.println(HAS_NO_EPISODE);
            return;
        }

        System.out.println("Episodes for podcast " + title + ":");
        Iterator<EpisodeClass> it = pod.getEpisode().iterator();
        while(it.hasNext()){
            EpisodeClass ep = it.next();
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
    private static void podcastList(Scanner sc, Array<PodcastClass> podcast){
        String author = sc.nextLine().trim();
        Array<PodcastClass> authorPod = getPodcastByAuthor(author, podcast);
        if(authorPod.size() == 0){
            System.out.println(NO_PODCAST_AUTHOR);
            return;
        }
        System.out.println("Podcasts by author " + author + ":");
        Iterator<PodcastClass> it = authorPod.iterator();
        while(it.hasNext()){
            PodcastClass p = it.next();
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
    private static void removePodcast(Scanner sc, Array<VideoStructure> video , Array<PodcastClass> podcast){
        String title = sc.nextLine().trim();
        PodcastClass pod = getPodcastByTitle(title, podcast);
        if(pod == null){
            System.out.println(PODCAST_DOES_NOT_EXIST);
            return;
        }
        //remove all the episodes from the podcast
        Iterator<EpisodeClass> it = pod.getEpisode().iterator();
        //Remove from global data the episodes
        while(it.hasNext()){
            EpisodeClass ep = it.next();
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
    private static void createshow(Scanner sc , Array <VideoStructure> videos, Array<ShowClass> showStructure, Array<PodcastClass> podcast) {
        String author = sc.nextLine().trim();
        author = getExistingAuthorName(author, podcast);
        author = getExistingAuthorName(author, showStructure);
        String videoId = sc.next();
        String transmissionDate = sc.next();
        sc.nextLine();
        VideoStructure videoStructure = getVideoById(videoId, videos);
        if (videoStructure == null || videoStructure instanceof EpisodeClass) {
            System.out.println(SHOW_VIDEO_DOES_NOT_EXIST);
            return;
        }
        PublishableVideosClass pubVideo = (PublishableVideosClass) videoStructure;
        String showTitle = pubVideo.getTitle();
        Iterator<ShowClass> it = showStructure.iterator();
        while(it.hasNext()){
            ShowClass show = it.next();
            if(show.getTitle().equalsIgnoreCase(showTitle)){
                System.out.println(SHOW_ALREADY_EXISTS);
                return;
            }
        }
        ShowClass newShow = new ShowClass(author, pubVideo, transmissionDate);
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
    private static void getShow(Scanner sc, Array<ShowClass> showStructure){
        String title = sc.nextLine().trim();
        Iterator<ShowClass> it = showStructure.iterator();
        while(it.hasNext()){
            ShowClass show = it.next();
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
    private static void removeShow(Scanner sc, Array<ShowClass> showStructure){
        String title = sc.nextLine().trim();
        Iterator<ShowClass> it = showStructure.iterator();
        int position = 0;
        while(it.hasNext()){
            ShowClass currentShow = it.next();
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
    private static void removeVideo(Scanner sc, Array<VideoStructure> videos, Array<ShowClass> showStructure){
        String videoId = sc.next();
        VideoStructure video = getVideoById(videoId,videos);
        if(video == null) {
            System.out.println(VIDEO_DOES_NOT_EXIST);
            return;
        }
        if(video instanceof EpisodeClass){
            System.out.println(CANNOT_REMOVE_EPISODE_VIDEO);
            return;
        }
        Iterator<ShowClass> it = showStructure.iterator();
        while(it.hasNext()){
            ShowClass show = it.next();
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
    private static void authorProductivity(Scanner sc, VideoNetwork video){

    }

    private static void createTag(Scanner sc, VideoNetwork video){
        String title = sc.nextLine();
        String tag = sc.nextLine();
        try{
            video.addTag(title,tag);
            System.out.println(TAGGED);
        }
        catch(TitleDoesNotExistsException e){System.out.println(TITLE_DOES_NOT_EXIST);}
        catch(TaggedException e) {System.out.println(TITLE_ALREADY_TAGGED + tag);}

    }
    private static void removeTag(Scanner sc, VideoNetwork video){
        String title = sc.nextLine();
        String tag = sc.nextLine();;
        try{
            video.removeTag(title,tag);
            System.out.println(TAG_REMOVED);
        }
        catch(TitleDoesNotExistsException  e){System.out.println(TITLE_DOES_NOT_EXIST);}
        catch(TaggedException e){System.out.println(TITLE_NOT_TAGGED + tag);}
    }
    private static void tagged(Scanner sc, VideoNetwork video){

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



}

