package com.protify.Protify.adapter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.hal.forms.Jackson2HalFormsModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class FormsTraverson {

    private final Builder builder;
    private MockMvc mvc;
private final  ObjectMapper mapper;

    public FormsTraverson(MockMvc mvc) {
        this.mvc = mvc;
        this.builder =  new Builder(()->mvc .perform(MockMvcRequestBuilders.get("/").accept(MediaTypes.HAL_FORMS_JSON)));
        this.mapper = new ObjectMapper();
        this.mapper.registerModules(

                new Jackson2HalFormsModule()

        );

       this. mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public Builder follow(String... rels) {
        return builder.follow(rels);
    }

    public <T> T toObject(TypeReference<T> typeReference) throws Exception {
        return builder.toObject(typeReference);
    }

    public Builder post(String rel, Object body) {
        return builder.post(rel, body);
    }

    public Builder put(String rel, Object body) {
        return builder.put(rel, body);
    }

    public Builder patch(String rel, Object body) {
        return builder.patch(rel, body);
    }

    public Builder delete(String rel) {
        return builder.delete(rel);
    }




    public Builder follow(String rel){
        return builder.follow(rel) ;
    }


    public <T> T toObject() throws Exception {
        return builder.toObject();
    }

    public <T> T toObject(String path) throws Exception {
        return builder.toObject(path);
    }

    public Builder follow(Hop hop) {

        return builder.follow(hop);
    }




    public static class Hop {
        private  final String rel;

        public  static  Hop rel(String rel){
            return new Hop(rel);
        }

        private Hop(String rel) {
            this(rel, new HashMap<>());
        }

        private Hop(String rel, Map<String, Object> parameters) {
            this.rel = rel;
            this.parameters = parameters;
        }

        private final Map<String ,Object > parameters;


        public Hop withParameter(String key, Object value){
            HashMap<String, Object> parameters = new HashMap<>(this.parameters);
            parameters.put(key,value);
            return new Hop(rel, parameters);
        }
    }
    public class Builder {

        private final Callable<ResultActions> previousResult;



        public  Builder follow(String ... rels ){
            Builder acc = this;
            for(String rel : (rels)){
                acc = acc.follow(rel);
            }
            return     acc;
        }

        public <T> T toObject(TypeReference<T> typeReference) throws Exception {
            return  mapper.readValue(toContent(), typeReference);
        }

        public <T> T toObject() throws Exception {
            return  mapper.readValue(toContent(), new TypeReference<>() {
            });
        }


        public <T> T toObject(String path) throws Exception {
            return  JsonPath.read(toContent(), path);
        }


        public Builder(Callable<ResultActions> previousResult) {
            this.previousResult = previousResult;
        }

        public Builder follow(String rel){
            return  follow(new Hop(rel));
        }

        public  Builder follow(Hop hop){
            return  follow(HttpMethod.GET, hop, null);
        }
        public Builder post( String rel, Object body){
            return  follow(HttpMethod.POST, rel, body);
        }

        public Builder put( String rel, Object body){
            return  follow(HttpMethod.PUT, rel, body);
        }

        public Builder patch( String rel, Object body){
            return  follow(HttpMethod.PATCH, rel, body);
        }


        public Builder delete( String rel){
            return  follow(HttpMethod.DELETE, rel, null);
        }

        private
        Builder follow(HttpMethod method, String rel, Object body ){
            return follow(method, new Hop(rel), body);
        }

        public ResultActions  toResult() throws Exception {
            return previousResult.call();
        }

        private  String toContent() throws Exception {
            return  toResult().andExpect((status().is2xxSuccessful())).andReturn().getResponse().getContentAsString();
        }

        private
        Builder follow(HttpMethod method, Hop  hop, Object body ){
            return new Builder(()-> {
                String response = toContent();

                URI uri;




                if(hop.rel.startsWith("$")){
                    uri = new URI(JsonPath.<String>read(response, hop.rel));
                }else {



                    EntityModel<?> entity = mapper.readValue(response, new TypeReference<EntityModel<Object>>() {
                    });

                    System.out.println(entity);

                    uri = entity
                            .getRequiredLink(hop.rel).expand(hop.parameters).toUri();
                }



                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(method, uri)

                        .accept(
                                MediaTypes.HAL_FORMS_JSON
                        );


                if(body != null){
                    request=request .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(body));
                }


                return                mvc.perform(request);

            }

            );
        }
    }
}
