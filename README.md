
# Meteofrance Java SDK

Java library to interact with the Meteofrance API, enabling seamless download of forecasts, observations, and fine-grained weather data.

--- 

## ☔︎︎ API Overview

Meteofrance provides open data via their API portals:
- [Meteofrance Open Data Portal](https://portail-api.meteofrance.fr/web/fr/)
- [Confluence Documentation](https://confluence-meteofrance.atlassian.net/wiki/spaces/OpenDataMeteoFrance/overview?homepageId=222265642)

The Meteofrance API offers the following main features:

| **Feature**               | **Description**                                                                                     | **Details**                                                     |
|---------------------------|-----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------|
| **Observation Records**   | Access historical and real-time weather observations.                                              | Direct access to observational data.                            |
| **Forecast Package**      | Aggregated GRIB2 files for weather forecasts.                                                     | Supports models like `Arome`, `Arpege`, `Arome-PE`, and `Wave`. |
| **WCS**                   | [Web Coverage Service (WCS)](https://www.ogc.org/fr/standards/wcs/) for fine-grained data extraction. | Standardized for European Union data extraction.                |

> All features use the **same authentication mechanism**.

--- 

## 🌤️ Why Use `meteofrance-java-sdk`?

The SDK simplifies interactions with the Meteofrance API by providing:
- **Authentication Handling**: Streamlined management of API keys and tokens.
- **Response & Error Handling**: Reduces boilerplate code for listing, downloading, and processing data.

--- 

## ☀️️ Modules

The SDK is organized into the following modules:

| **Module**               | **Description**                                                                                     |
|--------------------------|-----------------------------------------------------------------------------------------------------|
| `meteofrance-core`       | Core functionalities for authentication and request handling.                                       |
| `meteofrance-paquet`    | Covers forecast models: `Wave`, `Arpege`, `Arome`.                                                  |
| `meteofrance-wcs`        | Implements Web Coverage Service (WCS) for fine-grained data extraction.                              |
| `meteofrance-observation`| Handles observation data retrieval.                                                                |
| `opengis.wcs`            | Dedicated package for WCS standard (European Union compliant). Not specific to Meteofrance.         |

--- 

## 🌿 Getting Started

### Prerequisites
1. Retrieve your **`ApplicationId`** and **API Key** from the [Meteofrance Open Data Portal](https://portail-api.meteofrance.fr/web/fr/).

--- 

### Step-by-Step Setup

#### 1. **Instantiate the Core Client**
```java
MeteoFranceClientConfig clientConfig = 
    MeteoFranceClientConfig.builder(config.getApplicationId()).build();

MeteoFranceClient client = MeteoFranceClientFactory.create(clientConfig);
```

#### 2. **Instantiate the Desired Service**
```java
StationService service = StationServiceFactory.create(client);
```

#### 3. **Extract Data**
```java
service.downloadObservationsByDepartmentId(988, "csv");
```

--- 

## Examples

### Fetching Forecast Data
```java
// Example: Fetching Arome forecast data
ForecastService forecastService = ForecastServiceFactory.create(client);
ForecastData data = forecastService.getForecast("Arome", "0.01", "t+24");
```

### Using WCS for Fine-Grained Extraction
```java
// Example: Extracting data using WCS
WCSService wcsService = WCSServiceFactory.create(client);
CoverageData coverage = wcsService.getCoverage("temperature", "2026-09-04");
```

--- 

##  Cite and Share ✨
If you find this framework or its documentation useful, consider starring the repository to support its development! 