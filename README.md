# Phần mềm thông tin Lịch sử Việt Nam
## Prerequisites
- Java 8 or higher
- Apache Maven

## Running the application
1. Clone the repository to your local machine (using SSH)
```shell
git clone git@github.com:pmatuan/ProjectOOP.git
```

2. Go to the project directory
```shell
cd ProjectOOP
```
3. Open the project with IntellJ IDEA or other IDE.
4. Run all files `*Crawler.java` in `crawlers` package to get raw data to files in `/data/*.json`. 
5. Run all files in `connectors` package to connect the between entities.
6. Run project via `Home.java` to view the UI.

## Data sources
The application uses data stored in the data directory in `JSON` format. These files can be updated to add or modify data used in the application.

## Architecture diagrams
UML diagrams for the application's architecture can be found in the astah_uml directory in the format of .asta files, which can be opened with Astah Community. The diagram for the services can also be found in img_report directory in the format of .png file.
