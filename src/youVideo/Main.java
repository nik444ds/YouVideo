/*
** Nicolas Nº74517 and Mahir Nº


 */

package youVideo;
import dataStructures.*;

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
    public static final String UNKNOWN_COMMAND = "Unknown command";

    //Error message
    public static final String INVALID_LANGUAGE = "Invalid language type.";
    public static final String INVALID_DURATION = "Invalid value.";
    public static final String ID_ALREADY_EXISTS = "Video with this ID already exists.";
    public static final String INVALID_LANGUAGE_SUBTITLE = "Invalid language type in subtitle.";
    public static final String VIDEO_DOES_NOT_EXIST = "Video does not exist.";
    public static final String NOT_A_PREMIUM_VIDEO = "This operation requires a Premium video.";
    public static final String CREATED_SUB = "Subtitle added successfully.";
    public static final String INVALID_PUBLISHABLE_VIDEO = "Publishable Video videoId does not exist.";
    public static final String NO_PREMIUM_VIDEO = "No Premium Video with ID.";



    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Array<VideoStructure> videos = new ArrayClass<>();
        commandInterpreter(sc,videos);

    }
    private static void commandInterpreter(Scanner sc, Array<VideoStructure> videos){
        String commands = sc.next().toLowerCase();

        while(!commands.equals(CMD_EXIT)){
            switch(commands){
                case CMD_CREATE_PUBLISHABLE -> addPublishable(sc, videos);
               case CMD_CREATE_PREMIUM -> addPremium(sc, videos);
                 case CMD_ADD_SUB -> addSub(sc, videos);
                case CMD_GET_VIDEO -> getVideo(sc, videos);
                case CMD_SUBTITLE -> subtitleList(sc,videos);
                /*case CMD_CREATE_PODCAST ->
                case CMD_ADD_EPISODE ->
                case CMD_GET_PODCAST ->
                case CMD_EPISODES ->
                case CMD_AUTHOR_PODCAST ->
                case CMD_REMOVE_PODCAST ->
                case CMD_CREATE_SHOW ->
                case CMD_GET_SHOW ->
                case CMD_REMOVE_SHOW ->
                case CMD_REMOVE_VIDEO ->*/
                case CMD_HELP -> help();
                default -> System.out.println(UNKNOWN_COMMAND);
            }
            commands = sc.next().toLowerCase();
        }
        System.out.println("Bye!");
        sc.close();
    }

    // Commands methods =================


    /*
    Implement conditions for the command createpublishable
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


        System.out.println("Video " + id + " created successfully.");
    }
    /*
    Implements the commandpremium
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
        String initLanguageCode = sc.nextLine();
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
        PremiumVideos premiumVideos = new PremiumVideos(id,duration,url,publisher,title,languageCode,initSubUrl,initLanguageCode);
        video.insertLast(premiumVideos);

        System.out.println("PREMIUM Video " + id + " created successfully.");

    }
    /*
    Create the addsubtitle command
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


        //VERIFY IF WE CAN USE instanceOf!
        if(!(videoStructure instanceof PremiumVideos premiumVideos)){
            System.out.println(NOT_A_PREMIUM_VIDEO);
            return;
        }
        premiumVideos.addSubtitle(languageCode, languageUrl);

        System.out.println(CREATED_SUB);
    }


    //GetVideo command

    private static void getVideo(Scanner sc, Array<VideoStructure> videos){
        String id = sc.next();
        VideoStructure videoStructure = getVideoById(id,videos);
        //Increment a statement if the id is from podcasts episode
        if(videoStructure == null || videoStructure instanceof Episode){
            System.out.println(INVALID_PUBLISHABLE_VIDEO);
            return;
        }
        if(videoStructure instanceof PremiumVideos premiumVideos){
            System.out.println("PREMIUM Video " + premiumVideos.getId() + " " +  premiumVideos.getDuration() + " Title: " + premiumVideos.getTitle());
            System.out.println("File: " + premiumVideos.getUrl() + " Publisher: " + premiumVideos.getPublisher() + " Language: " + premiumVideos.getLanguages().getDisplayLanguage().toUpperCase());
            return;
        }
        if(videoStructure instanceof PublishableVideos pubVideos){
            System.out.println("Video " + pubVideos.getId() + " " + pubVideos.getDuration() + " Title: " + pubVideos.getTitle());
            System.out.println("File: " + pubVideos.getUrl() + " Publisher: " + pubVideos.getPublisher() + " Language: " + pubVideos.getLanguages().getDisplayLanguage().toUpperCase());
        }


    }
        //Subtitle Command
    private static void subtitleList(Scanner sc, Array<VideoStructure> videos){
        String id = sc.next();
        VideoStructure video = getVideoById(id, videos);

        if(!(video instanceof PremiumVideos premiumVideos)){
            System.out.println(NO_PREMIUM_VIDEO);
            return;
        }

        System.out.println("Subtitles for video " + premiumVideos.getTitle() + ":");

        Iterator<Subtitles> it = premiumVideos.getSubtitles().iterator();
        while(it.hasNext()){
            Subtitles sub = it.next();
            System.out.println("- " + sub.getUrl() + " (" + sub.getLanguage().getDisplayLanguage().toUpperCase() + ")");        }
    }





//Implement the command help
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
        System.out.println("removevideo - removes a publishable video");
        System.out.println("help - shows the available commands");
        System.out.println("exit - terminates the execution of the program");
    }

    // Auxiliary Methods
/*
    Verify if the id from VideoStructure exist
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
// Verify in String languages if the language inserted exist in the library ISOLanguages
    private static boolean isLanguageValid(String code) {
        String[] languages = Locale.getISOLanguages();

        for (int i = 0; i < Locale.getISOLanguages().length; i++) {
            if (languages[i].equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    // Can get the video from id
    private static VideoStructure getVideoById(String id,Array<VideoStructure> video){
        Iterator<VideoStructure> it = video.iterator();
        while(it.hasNext()){
            VideoStructure v = it.next();
            if(v.getId().equalsIgnoreCase(id))
                return v;
        }
        return null;

    }
}

/*
Classes and Interface
Podcasts - A class which implements a title, author and language. This class has a array of episodes
Shows -A class which takes publishable videos and give a transmission time(data) and one author


 */