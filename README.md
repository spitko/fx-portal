# Exchange Rate Portal

## Backend

### Built with:

- Spring Data JPA
- Spring Web Services
- H2 Database
- jaxws-maven-plugin for generating SOAP client for lb.lt API
  - Saved the WSDL file into resources and edited types to match the xsd
- Quartz Job Scheduler

### Endpoints

- `/api/rates/current`
  - Returns most recent rates for all currencies
- `/api/rates/historical/{currency}`
  - Returns rates starting from 1 year ago for given currency

### Notes

- All rates are against EUR (1 EUR = x other currency)
- H2 database is used to cache rates for subsequent requests

### Usage

Use maven to run backend on port 8080 using:

`mvn spring-boot:run`

## Frontend

Can be found under `app/`

Built with:

- Angular 17
- chart.js

### Usage

To install dependencies:

`npm install`

To run application locally at port 4200:

`npm start`

