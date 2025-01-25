# Advanced Hibernate Project with Validation, Locking, and Relationships

## Overview
This project demonstrates the use of Hibernate for managing entity persistence, along with custom validation, validation groups, and locking mechanisms (optimistic and pessimistic). It includes the following features:

- Custom validation using `@NotEmpty` annotation.
- Validation groups for conditional validation.
- Optimistic and pessimistic locking for concurrent data access.
- Relationship annotations like `@OneToOne`, `@OneToMany`, and `@ManyToMany`.
- Query types like basic queries, HQL (Hibernate Query Language), and native SQL queries.

## Features

### Custom Validation
- Implements a custom annotation `@NotEmpty` to ensure that string fields are not empty or null.
- Provides support for validation groups (`BasicValidation` and `AdvancedValidation`) for conditional validation.

### Locking Mechanisms
- **Optimistic Locking**: Uses the `@Version` annotation to manage concurrent updates to entities.
- **Pessimistic Locking**: Uses `LockModeType.PESSIMISTIC_WRITE` to prevent concurrent updates.

## Prerequisites
To run this project, ensure you have the following installed:

- **Java 11+**
- **Maven**
- **Hibernate dependencies** (configured in `pom.xml`)
- **H2 Database**

## Setup and Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/AlexejDumka/MavenWithHibernate.git
   cd MavenWithHibernate
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Configure the database connection in `src/main/resources/hibernate.cfg.xml`.

## Running the Application
1. Run the `ApplicationRunner` class to initialize the Hibernate session and demonstrate the functionality:

   ```bash
   mvn exec:java -Dexec.mainClass="com.example.ApplicationRunner"
   ```

2. The following operations are performed:
    - Custom validation is applied to entities.
    - Entities are persisted in the H2 in-memory database.
    - Examples of optimistic and pessimistic locking are executed.

### Relationship Annotations Demonstrated
This project includes examples of common JPA relationship annotations:
- **@OneToOne**: Demonstrated in `TestEntity` for linking one-to-one relationships like a `Jobseeker` and `Resume`.
- **@OneToMany** and **@ManyToOne**: Used to map collections and their parent entities, such as `Job` ↔ `Application` relationships.
- **@ManyToMany**: Demonstrated with skills and jobs, showcasing a many-to-many mapping with a join table.



### Query Types Demonstrated
This project includes examples of the following query types:
- **Basic Queries**: Retrieving entities by ID or filtering by attributes.
- **HQL (Hibernate Query Language)**: Projection queries, criteria-based filtering, and custom conditions.
- **Native SQL Queries**: Using raw SQL for database-specific operations.
- **Aggregate Queries**: Calculating sums, averages, counts, etc.
- **Join Queries**: Inner join, left outer join, and full join examples.
- **Subqueries**: Both correlated and uncorrelated subqueries are demonstrated.


## File Structure
```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com/example/entity/   # Entity classes
│   │   ├── com/example/service/  # Service classes
│   │   └── com/example/          # Main application
│   ├── resources/                # Configuration files (e.g., hibernate.cfg.xml)
│   └── ApplicationRunner.java    # Main entry point
├── test/                         # Unit tests
pom.xml                           # Maven configuration file
```

## Technologies Used
- **Java 11+**
- **Hibernate ORM**
- **H2 Database**
- **Validation API (JSR 380)**

## License
This project is licensed under the MIT License.
