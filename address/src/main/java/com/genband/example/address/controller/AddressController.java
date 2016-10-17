package com.genband.example.address.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.genband.example.address.model.Address;
import com.genband.example.address.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final static String CONTACT_SERVICE = "http://localhost:8080/contact";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AddressService addressService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    /**
     * Example HELLO
     * 
     * URL: localhost:8090/address/hello
     * 
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "hello world";
    }

    /**
     * Example one:
     * 
     * Request: client => address => contact Response: client <= address <=
     * contact
     * 
     * URL: localhost:8090/address/example?name= Goal: Make sure User is in both
     * address and contact database.
     * 
     * @param name
     * @return if found, return HTTPStatus.OK. Otherwise, return
     *         HttpStatus.BAD_REQUEST.
     */
    @RequestMapping(value = "/example1", method = RequestMethod.GET)
    public ResponseEntity<?> example1(@RequestParam(value = "name") String contactName) {
        // check the contactName is in the address list
        List<Address> result = addressService.findByContactName(contactName);
        if (result != null && result.size() > 0) {
            // check the contactName is still available in the contact list
            RestTemplate restTemplate = restTemplateBuilder.build();

            ResponseEntity<Void> response = restTemplate.getForEntity(CONTACT_SERVICE + "/{name}", Void.class,
                    contactName);
            logger.info("AddressController => example1 => HTTP Response :" + response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        logger.info("AddressController => example1 => HTTP Response :" + HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * use jsonNode to represent result
     * 
     * @return
     */
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public JsonNode test1() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        JsonNode jsonNode = restTemplate.getForObject(CONTACT_SERVICE + "/all", JsonNode.class);

        for (int index = 0; jsonNode.get(index) != null; index++) {
            logger.info(jsonNode.get(index).get("id").toString() + " " + jsonNode.get(index).get("name").toString());
        }

        return jsonNode;
    }

    /**
     * Get Headers
     * 
     * @return
     */
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders headForHeaders = restTemplate.headForHeaders(CONTACT_SERVICE + "/all");

        return headForHeaders.toString();
    }

    /**
     * Get OPTIONS
     *
     * @return
     */
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public Set<?> test3() {
        Set<HttpMethod> optionsForAllow = restTemplateBuilder.build().optionsForAllow(CONTACT_SERVICE);

        return optionsForAllow;
    }

    /**
     * test ArrayNode and restTemplate.exchange
     *
     * @return
     */
    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public ResponseEntity<?> test4() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ArrayNode arrayNode = nodeFactory.arrayNode();

        ObjectNode objectNode1 = nodeFactory.objectNode();
        objectNode1.put("name", "chris2");

        ObjectNode objectNode2 = nodeFactory.objectNode();
        objectNode2.put("name", "Sen2");

        ObjectNode objectNode3 = nodeFactory.objectNode();
        objectNode3.put("name", "Hao2");

        arrayNode.add(objectNode1).add(objectNode2).add(objectNode3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ArrayNode> entity = new HttpEntity<>(arrayNode, headers);

        ResponseEntity<?> exchange = restTemplate.exchange(CONTACT_SERVICE + "/add-list", HttpMethod.POST, entity,
                ResponseEntity.class);

        logger.info(exchange.toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * test single object node. if requestBody is list, we use ArrayNode if
     * requestBody is single object, we use ObjectNode
     */
    @RequestMapping(value = "/test5", method = RequestMethod.GET)
    public ResponseEntity<?> test5() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode objectNode1 = nodeFactory.objectNode();
        objectNode1.put("id", "1");
        objectNode1.put("name", "helloworld");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ObjectNode> entity = new HttpEntity<>(objectNode1, headers);

        ResponseEntity<?> response = restTemplate.exchange(CONTACT_SERVICE + "/update", HttpMethod.PUT, entity,
                ResponseEntity.class);

        logger.info(response.toString());

        return response;
    }

    /**
     * test findByAddressNameEndsWith in AddressRepository
     */
    @RequestMapping(value = "/test6", method = RequestMethod.GET)
    public List<Address> test6() {
        return addressService.findByAddressNameEndsWith("a");
    }

}
