# Serverless REST API application in Java/Maven using DynamoDB build with 'The Serverless Framework'

This simple serverless service will create 4 functions: 
- Create Person
- Get person 
- Get list of all persons 
- Delete person

It will be deployed in AWS and the data will be stored in DynamoDB. 

This project is part my experiments in serverless architecture. 

## Environment Pre-requisites
1) The serverless framework: https://www.serverless.com/framework/docs/providers/aws/guide/installation/
2) Java/Maven

## Build the Java project
From the project folder execute:
```
$ mvn clean install
```

## Deploy the serverless app
```
$ sls deploy
```

## Test the API
In order to test the functions either user Postman locally and invoke accordingly the APIs or go in AWS console and test
directly from the console. 

## View Logs and Metrics
1) Logs
    - Serverless commands:
        ```
        $ sls logs --function getProduct
        ```

2) Metrics
    - Serverless commands: 
        ```
        $ sls metrics
        ```
        or for a single function:
        ```
        $ sls metrics --function createPerson
        
        ```
Or reach CloudWatch in AWS and enjoy their dashboards. 

## Removing the service
```
$ sls remove
```

