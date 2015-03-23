/**
 * ClientCommunicatorUnitTest.java
 * JRE v1.8.040
 * 
 * Created by William Myers on Mar 22, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package client.communication;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import client.ClientException;

import server.Server;
import server.database.Database;
import server.database.dao.*;

import shared.communication.*;
import shared.model.*;

public class ClientCommunicatorUnitTest {
    private Database           db;
    private Server             server = new Server();
    private ClientCommunicator communicator;

    private UserDAO            userDAOTest;
    private ProjectDAO         projectDAOTest;
    private BatchDAO           batchDAOTest;

    User                       user;

    Project                    project1;
    Field                      field1;
    Field                      field2;

    Project                    project2;
    Field                      field3;
    Field                      field4;

    Batch                      batch1;
    Batch                      batch2;
    Batch                      batch3;

    Record                     record1;
    Record                     record2;
    Record                     record3;
    Record                     record4;
    Value                      value1;
    Value                      value2;
    Value                      value3;
    Value                      value4;
    Value                      value5;
    Value                      value6;
    Value                      value7;
    Value                      value8;

    @Before
    public void setup() {
        // add some data to the database

        user = new User("Joe", "password");

        project1 = new Project("project 1", 2, 0, 0);
        field1 = new Field("name", 0, 0, "", null);
        field2 = new Field("age", 0, 0, "", null);
        project1.addField(1, field1);
        project1.addField(2, field2);

        project2 = new Project("project 2", 2, 0, 0);
        field3 = new Field("height", 0, 0, "", null);
        field4 = new Field("weight", 0, 0, "", null);
        project2.addField(1, field3);
        project2.addField(2, field4);

        value1 = new Value("Joe");
        value2 = new Value("42");
        value3 = new Value("Billy");
        value4 = new Value("13");
        value5 = new Value("John");
        value6 = new Value("60");
        value7 = new Value("Welma");
        value8 = new Value("32");

        try {
            // Prepare database for test case
            db = new Database();
            db.startTransaction();
            db.initTables();

            userDAOTest = db.getUserDAO();
            projectDAOTest = db.getProjectDAO();
            batchDAOTest = db.getBatchDAO();

            // add stuff to database and update model

            user = userDAOTest.create(user);

            project1 = projectDAOTest.create(project1);
            project2 = projectDAOTest.create(project2);

            field1 = project1.getFields().get(1);
            field2 = project1.getFields().get(2);
            field3 = project2.getFields().get(1);
            field4 = project2.getFields().get(2);

            record1 = new Record();
            record1.addValue(field1, value1);
            record1.addValue(field2, value2);
            record2 = new Record();
            record2.addValue(field1, value3);
            record2.addValue(field2, value4);
            record3 = new Record();
            record4 = new Record();

            batch1 = new Batch(new BatchImage("file1"), true, project1);
            batch1.getRecords()[0] = record1;
            batch1.getRecords()[1] = record2;
            batch2 = new Batch(new BatchImage("file 2"), false, project1);
            batch3 = new Batch(new BatchImage("file 3"), false, project2);

            batch1 = batchDAOTest.create(batch1);
            batch2 = batchDAOTest.create(batch2);
            batch3 = batchDAOTest.create(batch3);

            value1 = batch1.getRecords()[0].getValues().get(field1);
            value2 = batch1.getRecords()[0].getValues().get(field2);
            value3 = batch1.getRecords()[1].getValues().get(field1);
            value4 = batch1.getRecords()[1].getValues().get(field2);

            // server setup
            String host = "127.0.0.1";
            int port = 36942;

            // prepend url prefix to file names
            String UrlPrefix = String.format("http://%s:%d/", host, port);

            //user.filesToURLs(UrlPrefix);
            //project1.filesToURLs(UrlPrefix);
            //project2.filesToURLs(UrlPrefix);
            //batch1.filesToURLs(UrlPrefix);
            //batch2.filesToURLs(UrlPrefix);
            //batch3.filesToURLs(UrlPrefix);
            //field1.filesToURLs(UrlPrefix);
            //field2.filesToURLs(UrlPrefix);
            //field3.filesToURLs(UrlPrefix);
            //field4.filesToURLs(UrlPrefix);

            // start server
            server.bootstrap(port);

            // initialize communicator
            communicator = new ClientCommunicator();
            communicator.setHost(host);
            communicator.setPort(port);
        } catch (Exception ex) {
            fail("Error initializing database: " + ex.getMessage());
        }
    }

    @After
    public void teardown() {
        // stop server
        server.stop();

        // Roll back this transaction so changes are undone
        db.endTransaction(false);
        db = null;
        testUserDAO = null;
    }

    @Test
    public void testValidateUser() {
        ValidateUserRequest request = new ValidateUserRequest();
        ValidateUserResponse response;

        try {
            request.user = user;
            response = communicator.validateUser(request);
            assertNotNull(response.user);

            request.user = new User("invalid", "user");
            response = communicator.validateUser(request);
            assertNull(response.user);
        } catch (ClientException ex) {
            fail("Error validating user: " + ex.getMessage());
        }
    }

    @Test
    public void testGetProjects() {
        GetProjectsRequest request = new GetProjectsRequest();
        GetProjectsResponse response;

        request.user = user;

        try {
            response = communicator.getProjects(request);
            List<Project> expected = new ArrayList<Project>();
            expected.add(project1);
            expected.add(project2);
            List<Project> actual = response.projects;

            assertEquals(expected, actual);
        } catch (Exception ex) {
            fail("Error getting projects: " + ex.getMessage());
        }
    }

    @Test
    public void testGetFields() {
        GetFieldsRequest request = new GetFieldsRequest();
        GetFieldsResponse response;

        request.user = user;

        try {
            // expected returns
            List<Field> project1Fields = new ArrayList<Field>();
            project1Fields.add(field1);
            project1Fields.add(field2);

            List<Field> project2Fields = new ArrayList<Field>();
            project2Fields.add(field3);
            project2Fields.add(field4);

            Map<Integer, List<Field>> expected;
            Map<Integer, List<Field>> actual;

            // tests
            request.project = project1;
            response = communicator.getFields(request);
            expected = new TreeMap<Integer, List<Field>>();
            expected.put(project1.getId(), project1Fields);
            actual = response.fields;
            assertEquals(expected, actual);

            request.project = null;
            response = communicator.getFields(request);
            expected = new TreeMap<Integer, List<Field>>();
            expected.put(project1.getId(), project1Fields);
            expected.put(project2.getId(), project2Fields);
            actual = response.fields;
            assertEquals(expected, actual);
        } catch (Exception ex) {
            fail("Error getting fields: " + ex.getMessage());
        }
    }

    @Test
    public void testDownloadAndSubmit() {
        // test download (we should get batch 2 for project 1)
        DownloadBatchRequest downloadRequest = new DownloadBatchRequest();
        DownloadBatchResponse downloadResponse;

        downloadRequest.user = user;
        downloadRequest.project = project1;

        Batch batch;

        try {
            downloadResponse = communicator.downloadBatch(downloadRequest);
            batch = downloadResponse.batch;
            assertEquals(batch2, batch);

            // make sure we can't download another batch
            downloadResponse = communicator.downloadBatch(downloadRequest);
            assertNull(downloadResponse.batch);
        } catch (Exception ex) {
            fail("Error downloading batch: " + ex.getMessage());
            return;
        }

        // add records to the batch
        batch.getRecords()[0] = record3;
        batch.getRecords()[1] = record4;

        // try submitting
        SubmitBatchRequest submitRequest = new SubmitBatchRequest();
        SubmitBatchResponse submitResponse;
        submitRequest.user = user;
        try {
            // make sure we can't submit wrong batch
            submitRequest.batch = batch1;
            submitResponse = communicator.submitBatch(submitRequest);
            assertEquals(false, submitResponse.succeeded);

            // make sure we can submit the right batch
            submitRequest.batch = batch;
            submitResponse = communicator.submitBatch(submitRequest);
            assertEquals(true, submitResponse.succeeded);

            // make sure we can't submit again
            submitResponse = communicator.submitBatch(submitRequest);
            assertEquals(false, submitResponse.succeeded);
        } catch (Exception ex) {
            fail("Error submitting batch: " + ex.getMessage());
        }
    }

    @Test
    public void testSearch() {
        // searches and expected results
        List<Field> fields1 = new ArrayList<Field>();
        fields1.add(field1);
        List<String> terms1 = new ArrayList<String>();
        terms1.add(value1.getData());
        List<SearchResponse> expected1 = new ArrayList<SearchResponse>();
        expected1.add(new SearchResponse(value1, field1, batch1, 1));

        List<Field> fields2 = new ArrayList<Field>();
        fields2.add(field1);
        fields2.add(field2);
        List<String> terms2 = new ArrayList<String>();
        terms2.add(value4.getData());
        terms2.add(value5.getData());
        List<SearchResponse> expected2 = new ArrayList<SearchResponse>();
        expected2.add(new SearchResponse(value4, field2, batch1, 2));

        List<Field> fields3 = new ArrayList<Field>();
        fields3.add(field1);
        fields3.add(field2);
        List<String> terms3 = new ArrayList<String>();
        terms3.add(value1.getData());
        terms3.add(value4.getData());
        List<SearchResponse> expected3 = new ArrayList<SearchResponse>();
        expected3.add(new SearchResponse(value1, field1, batch1, 1));
        expected3.add(new SearchResponse(value4, field2, batch1, 2));

        List<Field> fields4 = new ArrayList<Field>();
        fields4.add(field1);
        List<String> terms4 = new ArrayList<String>();
        terms4.add(value5.getData());
        List<SearchResponse> expected4 = new ArrayList<SearchResponse>();

        List<Field> fields5 = new ArrayList<Field>();
        fields5.add(field1);
        fields5.add(field2);
        List<String> terms5 = new ArrayList<String>();
        terms5.add(value1.getData());
        terms5.add(value2.getData());
        terms5.add(value3.getData());
        terms5.add(value4.getData());
        List<SearchResponse> expected5 = new ArrayList<SearchResponse>();
        expected5.add(new SearchResponse(value1, field1, batch1, 1));
        expected5.add(new SearchResponse(value3, field1, batch1, 2));
        expected5.add(new SearchResponse(value2, field2, batch1, 1));
        expected5.add(new SearchResponse(value4, field2, batch1, 2));

        // execute searches
        try {
            SearchRequest searchRequest = new SearchRequest();
            SearchResponse searchResponse;

            searchRequest.user = user;

            searchRequest.fields = fields1;
            searchRequest.terms = terms1;
            searchResponse = communicator.search(searchRequest);
            List<SearchResponse> actual1 = searchResponse.searchResults;

            searchRequest.fields = fields2;
            searchRequest.terms = terms2;
            searchResponse = communicator.search(searchRequest);
            List<SearchResponse> actual2 = searchResponse.searchResults;

            searchRequest.fields = fields3;
            searchRequest.terms = terms3;
            searchResponse = communicator.search(searchRequest);
            List<SearchResponse> actual3 = searchResponse.searchResults;

            searchRequest.fields = fields4;
            searchRequest.terms = terms4;
            searchResponse = communicator.search(searchRequest);
            List<SearchResponse> actual4 = searchResponse.searchResults;

            searchRequest.fields = fields5;
            searchRequest.terms = terms5;
            searchResponse = communicator.search(searchRequest);
            List<SearchResponse> actual5 = searchResponse.searchResults;

            // check that they match
            assertEquals(expected1, actual1);
            assertEquals(expected2, actual2);
            assertEquals(expected3, actual3);
            assertEquals(expected4, actual4);
            assertEquals(expected5, actual5);
        } catch (Exception ex) {
            fail("Error searching");
        }
    }
}
