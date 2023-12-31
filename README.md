This project implements Java CLI app for managing souvenirs and producers. Storing data in Json files

Here are some of the main features of the app:
* CRUD operations for souvenirs and producers
* Print souvenirs of given producer
* Print souvenirs of given country
* Print souvenirs by producers
* Print souvenirs by years
* Print producers by souvenir(name, year)
* Print producers whose prices are less than given

This project uses the following patterns:
* Facade pattern (for simplified interaction with dao)
* State pattern (for menu)
* Singletone (for dao)

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)

## Installation

To run the project locally, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/souvenire_TASK.git
   ```

2. Open the project in your preferred IDE.

3. Run the application

The application should now be running on your terminal

## Configuration
You can change name and location of the json storage files.\
Here's how to make these changes:

1. **Producers File:**
   - In `ProducerDAO`, change the value of this variable `private static final String PRODUCERS_FILE = "producers.json";` to the new file name.

2. **Souvenirs File:**
   - In `SouvenirDAO`, change the value of this variable `private static final String SOUVENIRS_FILE = "souvenirs.json";` to the new file name.

Ensure the new file names and paths are correctly set and accessible by application.
