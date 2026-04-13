# YouVideo 
![original-20752b0eaec2cc2a2e6eef7839a77daf](https://github.com/user-attachments/assets/3ad93389-f51a-4e16-9cce-deafc3723daa)


A simplified **video streaming platform** built in Java, inspired by services like YouTube and Netflix. Developed as an Object-Oriented Programming university project at FCT NOVA Lisbon.

##  About

YouVideo manages different types of video content — publishable videos, premium videos with subtitles, podcasts with episodes, and scheduled shows — while enforcing real-world business rules and constraints through a command-line interface.

##  Features

- **Publishable videos** — create and manage videos with title, publisher, language and file URL
- **Premium videos** — extend publishable videos with multi-language subtitle support
- **Podcasts** — manage podcast series with episodes ordered in reverse chronological order
- **Shows** — schedule transmissions of existing videos with author and broadcast date
- **Full validation** — language code validation (ISO 639-1), unique IDs across the entire system, cascading removal of podcast episodes

##  Commands

```
createpublishable   - creates a new publishable video
createpremium       - creates a new premium video with subtitle support
addsubtitle         - adds a subtitle to a premium video
getvideo            - displays video data by ID
subtitles           - lists all subtitles of a premium video
createpodcast       - creates a new podcast with no episodes
addepisode          - adds an episode to a podcast
getpodcast          - displays podcast data by title
episodes            - lists all episodes of a podcast
authorpodcasts      - lists all podcasts by a given author
removepodcast       - removes a podcast and all its episodes
createshow          - creates a show using an existing publishable video
getshow             - displays show data by title
removeshow          - removes a show
removevideo         - removes a publishable video
help                - shows available commands
exit                - terminates the program
```

##  Usage example

```
createpublishable vid440 250 http://example.com/video.mp4
CourseMaster
PHP Course
EN
Video vid440 created successfully.

createpremium vid456 180 http://example.com/vid456.mp4
EduChannel
Advanced Java
EN
http://example.com/subtitles-en.vtt
EN
PREMIUM Video vid456 created successfully.

createpodcast Tech Talk Daily
John Smith
EN
Podcast created successfully.
```

## OOP Concepts Applied

- Inheritance and polymorphism across video types
- Interfaces and abstract classes for extensibility
- Encapsulation and package structure
- Generic types and custom collections (no Java Collections Framework)
- Javadoc documentation throughout

## Tech

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

**Course:** Object-Oriented Programming — FCT NOVA Lisbon
