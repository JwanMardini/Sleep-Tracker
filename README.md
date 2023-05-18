

# Sleep-Tracker 🛌

<p align="center">
<img width="172" alt="Skärmbild 2023-05-18 011247" src="https://github.com/JwanMardini/Sleep-Tracker/assets/125370944/7fc022a4-3bca-4ad9-b296-4a768421c944" />
</p>

## Introduction <img src="https://github.com/Alloush95/test/assets/125370944/adeebf6c-9099-4b74-a346-a482f0b1061c" width="40" />

Sleep-Tracker is a JavaFX application that allows users to track their sleep patterns and records in a database. This README file provides instructions on how to set up the database and run the application.



## Setting up the Database <img src="https://github.com/JwanMardini/Sleep-Tracker/assets/125370944/680abd4c-c840-4bf8-a4df-149db89181da" width="40" />


Open the MySQL dump file, which includes the table structure and data for a database named "sleeptrackerlogin". To import this database into your MySQL server, follow these steps:

1. Open MySQL Workbench or any other MySQL client tool.
2. Create a new database with the same name as the one in the dump file ("sleeptrackerlogin").
3. In the MySQL client tool, go to the "Server" menu and select "Data Import".
4. In the "Import Options" section, select "Import from Self-Contained File".
5. Click on the "..." button to browse and select the dump file.
6. In the "Default Target Schema" field, select the database you created in step 2 ("sleeptrackerlogin").
7. Click on the "Start Import" button to begin the import process.
8. Once the import is completed, you can view the database structure and data in the MySQL client tool.

Note: Make sure that the MySQL server you are importing the database into has the same version or a newer version than the one used to create the dump file.



## Running the Application
### Prerequirements:
You have to have the following things installed on your computer:
1. Java Development Kit (JDK): To develop and run JavaFX applications, you need to have JDK 8 or later versions installed on your computer.
2. JavaFX SDK: You need to download and install the JavaFX SDK, which contains the necessary libraries and tools to build JavaFX applications.
3. MySQL database: You need to have MySQL installed on your computer or server. You can download and install MySQL from the official website.
4. MySQL Connector/J: You need to download and add the MySQL Connector/J library to your project's classpath. This library is needed to connect to the MySQL database from your JavaFX application.
5. IDE: You need an Integrated Development Environment (IDE) to write, test, and run your JavaFX application. Some popular IDEs for JavaFX development include IntelliJ IDEA, Eclipse, and NetBeans.


### Connect  JavaFX application to MySQL database:
Steps to connect JavaFX application to MySQL database are:
1. Open the project in IntelliJ IDEA.
2. Click on File in the top menu and select Project Structure (or press Ctrl+Alt+Shift+S on Windows/Linux, or Cmd+; on macOS).
3. In the Project Structure dialog, click on Libraries in the left pane.
4. Click on the + button at the top to add a new library.
5. Select Java from the dropdown menu and click on Next.
6. Browse and select the mysql-connector JAR file from your file system and click on OK.
7. In the Add Library dialog, make sure that the mysql-connector checkbox is selected and click on OK.
8. Click on Apply and then OK to close the dialog and save the changes.
After following these steps, the mysql-connector library should be added to the project's classpath and the JavaFX application should be able to connect to the MySQL database.

### Setting up the running configuration:
To set up the run configuration for VM options in IntelliJ IDEA:

1. Click on "Run" from the main menu.
2. Select "Edit Configurations" from the dropdown.
3. In the "Run/Debug Configurations" window, click on the "+" icon in the top left corner and select "Application" from the dropdown menu.
4. In the "Configuration" tab, give a name to the configuration, select the main class and set the working directory.
5. In the "VM Options" field, add the necessary options. For example, if you need to specify the module path and add the required modules for JavaFX, you can add the following options:

--module-path "/path/to/javafx-sdk-17.0.1/lib" --add-modules javafx.controls,javafx.fxml

Make sure to replace "/path/to/javafx-sdk-17.0.1" with the actual path to your JavaFX SDK.

6. Click on the "Apply" button to save the configuration.

After that, you can run the application with the configured VM options by clicking on the "Run" button.

