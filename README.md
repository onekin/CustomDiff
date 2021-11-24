# CustomDiff
CustomDIFF is a web-based Visual Analytics tool for Software Product Lines Customization Analysis. 
CustomDIFF uses pure::variants and Git as the  variability manager and code repository, respectively.\
The application is implemented in two different modules: (1) the visualization module, available in this repository and 
(2) the data mining module, available at https://github.com/onekin/git2spldb

There is a video demo available at: https://vimeo.com/577936099 \
Online demo available at: http://customdiff.onekin.org

CustomDIFF has been accepted to be published in EMSE Journal in the work "Visualizing the Customization Effort in Product-Based-Evolving SPLs: A Case of Action Design Research"

## Acknowledgements

This work uses the following librariers:
* [Highcharts (c) - 2009-2021 Torstein Honsi](https://www.highcharts.com/blog/download/?=noncomm)
  * Highcharts is a commercial library. CustomDIFF uses its non-commercial license that is released under Creative Commons Attribution-NonCommercial 3.0.
   If you want to use CustomDIFF commercially you must purchase a license of HighCharts.
* [Bootstrap](http://getbootstrap.com)
* [jQuery](http://jquery.com)
* [Spring Boot](https://github.com/spring-projects/spring-boot)

## Installation & Usage

### With Docker
The easiest way of running CustomDIFF with the replication data is using Docker: 

1. [Download](https://www.docker.com/products/docker-desktop) and install docker on your computer 
2. Open a terminal in the root of the project
3. run docker-compose up

In this way the web application will be launched in localhost (by default the app will be running on port 9091)
and the customization data available for visualization will be that of the WeatherStationSPL.

### Manually
#### Importing into IDE
CustomDIFF visualization module is a Maven project. You can import it into your favorite IDE as you do with any other Maven project.\
Here you can find some guides on how to import a Maven project into the two most popular Java IDEs:
* [Eclipse](https://www.vogella.com/tutorials/EclipseMaven/article.html)
* [IntelliJ IDEA](https://www.jetbrains.com/help/idea/maven-support.html#maven_import_project_start)
#### Setting up the DataBase
1. Download MySQL database
2. Create the DB schema used in CustomDIFF, to this end use the schema creation script available at [the inserts folder](src/main/resources/META-INF/SQL-statements/create-db.sql)
3. After creating the schema you have two options, import the data about the customizations of the [WeatherStationSPL](src/main/resources/META-INF/SQL-statements/inserts-statements.sql)
or generate your own data using [Git2SPLDB mining module](https://github.com/onekin/git2spldb)

#### Running
If you want to build and run CustomDIFF on your own, you have to follow these steps:

1. Set up your database connection and, your HTTP port in the [application.properties](src/main/resources/application.properties) file. 
2. Package the jar using Maven:

     ``` $ mvn package  ```
3. Run the jar with Java:

     ``` $ java -jar target/CustomDiff-[VERSION].jar  ```
4. Open your browser and enjoy CustomDIFF :)   

## Contributing
T.B.D
     
## Contact
If you have any troubles setting up CustomDIFF [create an issue](https://github.com/onekin/customdiff/issues/new) or contact raul.medeiros[at]ehu.eus     
