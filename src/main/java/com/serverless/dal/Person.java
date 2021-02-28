package com.serverless.dal;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.log4j.Logger;

@DynamoDBTable(tableName = "PLACEHOLDER_PERSON_TABLE_NAME")
public class Person {

    private static final String PERSON_TABLE_NAME = System.getenv("PERSON_TABLE_NAME");

    private String id;
    private String firstName;
    private String lastName;

    private static DynamoDBAdapter db_adapter;
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;

    private final Logger logger = Logger.getLogger(this.getClass());

    public Person() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PERSON_TABLE_NAME))
                .build();
        db_adapter = DynamoDBAdapter.getInstance();
        this.client = db_adapter.getDbClient();
        this.mapper = db_adapter.createDbMapper(mapperConfig);
    }

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBRangeKey(attributeName = "firstName")
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBRangeKey(attributeName = "lastName")
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Person> list() throws IOException {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Person> results = this.mapper.scan(Person.class, scanExp);
        for (Person p : results) {
            logger.info("Persons - list(): " + p.toString());
        }
        return results;
    }

    public Person get(String id) throws IOException {
        Person person = null;

        HashMap<String, AttributeValue> attributeValue = new HashMap<>();
        attributeValue.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Person> queryExp = new DynamoDBQueryExpression<Person>()
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(attributeValue);

        PaginatedQueryList<Person> result = this.mapper.query(Person.class, queryExp);
        if (result.size() > 0) {
            person = result.get(0);
            logger.info("Persons - get(): person - " + person.toString());
        } else {
            logger.info("Persons - get(): person - Not Found.");
        }
        return person;
    }

    public void save(Person person) throws IOException {
        logger.info("Persons - save(): " + person.toString());
        this.mapper.save(person);
    }

    public Boolean delete(String id) throws IOException {
        // get person if exists
        Person person = get(id);
        if (person != null) {
            logger.info("Persons - delete(): " + person.toString());
            this.mapper.delete(person);
        } else {
            logger.info("Persons - delete(): person - does not exist.");
            return false;
        }
        return true;
    }
}
