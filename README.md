# Club Manager Application
Sports clubs often encounter difficulties in effectively managing their members, finances and communications. In response to these needs, we have created a dedicated tool that would integrate these processes, allowing club owners, players and fans easy access to the necessary information.

## Description
The project is a **client-server** application whose backend was implemented in **Java**, using **multithreading**. The assumption is that the client connects to the server that is responsible for connecting to the **MySQL** management system that controls the operation of the database. Once retrieved from the database, the feedback is sent again by the server to the client.
Frontend was made in JavaFX and CSS.

### Subfolders structure
The `Server` workspace contains classes and subfolders responsible for starting the Server, connecting to the client, maintaining communication with it via Sockets and connecting to the database by endpoints to download resources from it.
* `ServerFunction`: the folder to maintain core backend of application.
* There are also two classes resposible for handling individual client threads.

The `ClubManagement` is responsible for handling individual interface elements and a single client. The classes included in them are controllers connecting to the graphical interface made in JavaFX and classes connecting to the server.
* `java`: the folder to maintain core backend of application.
* `resources`: the folder to maintain layout and view of application.

The `SQL Things` configures the database and loads it with initial data.

> [!IMPORTANT]
> During tests of the application, two classes should be launched: `main` from Server subfolder and `Client` form ClubManagement.

## Starting Server
Firstly you have to run a `Server` class.
 
## Starting Client
Client will only work if server is started.
To start client you have to run `Client` class.
