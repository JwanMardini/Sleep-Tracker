# Sleep-Tracker

How to set up the DB:
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
