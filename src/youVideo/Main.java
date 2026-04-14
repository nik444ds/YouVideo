/*
** Nicolas Nº74517 and Mahir Nº


 */

package youVideo;
import dataStructures.*;
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


    public static void main(String[] args){
        commandInterpreter();
    }
    private static void commandInterpreter(){
        Scanner sc = new Scanner(System.in);
        String commands = sc.nextLine();

        while(!commands.equals(CMD_EXIT)){
            switch(commands){
                case CMD_CREATE_PUBLISHABLE ->
                case CMD_CREATE_PREMIUM ->
                case CMD_ADD_SUB ->
                case CMD_GET_VIDEO ->
                case CMD_SUBTITLE ->
                case CMD_CREATE_PODCAST ->
                case CMD_ADD_EPISODE ->
                case CMD_GET_PODCAST ->
                case CMD_EPISODES ->
                case CMD_AUTHOR_PODCAST ->
                case CMD_REMOVE_PODCAST ->
                case CMD_CREATE_SHOW ->
                case CMD_GET_SHOW ->
                case CMD_REMOVE_SHOW ->
                case CMD_REMOVE_VIDEO ->
                case CMD_HELP -> help();
                default -> System.out.println(UNKNOWN_COMMAND);
            }
        }









        sc.close();
    }


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


}

/*
Classes and Interface
Podcasts - A class which implements a title, author and language. This class has a array of episodes
Shows -A class which takes publishable videos and give a transmission time(data) and one author



 */