Weather Application backend
A comprehensive Spring Boot application designed to process and analyze 20 years of massive weather CSV data. Built with an optimized MySQL schema, Spring Data JPA mapping, and a fully interactive Swagger/OpenAPI dashboard.

Features
Data Ingestion Endpoint (/api/v1/weather/upload): Seamlessly streams and batches multi-megabyte CSV files into a relational MySQL structure.
High-Performance Lookups (/api/v1/weather/by-date): Efficiently queries native DateTime conditions based on a massive 20-year span.
Dynamic Analytics (/api/v1/weather/stats): Dynamically traverses records within any given year to aggregate specific metrics and mathematically compute the exact median, high, and minimum temperatures per month via Java Streams.
OpenAPI 3.0 Integration: A beautiful interface deployed directly on http://localhost:8081/swagger-ui/index.html to easily execute backend contracts visually.
Technology Stack
Java 21: Leveraging the newest LTS release.
Spring Boot 4 / Spring Web / Spring Data JPA: The robust application scaffolding and transactional persistence layer.
MySQL 8.0: Advanced relational datastore for querying chronological datasets.
OpenCSV: A blazing-fast streaming CSV processor.
Lombok: Boilerplate stripping.
Requirements
Java 21 JDK installed & configured in JAVA_HOME
Maven 3.x+ installed
MySQL 8.x Server active locally on root port 3306


REST API Contracts
Method	Route	Description	Parameters
POST	/api/v1/weather/upload	Stream massive files into the database.	MultipartFile body
GET	/api/v1/weather/by-date	Extrapolate weather context by date.	date (YYYY-MM-DD)
GET	/api/v1/weather/by-month	Look up precise monthly metrics.	year, month
GET	/api/v1/weather/stats	Execute aggregate statistical math.	year
