package com.protify.Protify.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.hateoas.mediatype.hal.forms.HalFormsLinkDiscoverer;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FormsTraverson {
    private Traverson traverson;
    private  List<MediaType> mediaTypes;

    public FormsTraverson(URI uri, HypermediaRestTemplateConfigurer configurer) {
        this. mediaTypes = Arrays.asList(MediaTypes.HAL_FORMS_JSON);
        this.traverson = new Traverson(uri, mediaTypes);


        setLinkDiscoverers(
                List.of(new HalFormsLinkDiscoverer())
        );
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        configurer.registerHypermediaTypes(template);
        setRestOperations(template);
    }

    public static List<HttpMessageConverter<?>> getDefaultMessageConverters(MediaType... mediaTypes) {
        return Traverson.getDefaultMessageConverters(mediaTypes);
    }

    private  RestOperations operations ;

    public Traverson setRestOperations(RestOperations operations) {
        this.operations = operations;
        return traverson.setRestOperations(operations);
    }

    public Traverson setLinkDiscoverers(List<? extends LinkDiscoverer> discoverer) {
        return traverson.setLinkDiscoverers(discoverer);
    }

    public ExtendedTraversalBuilder follow(String... rels) {
        return new ExtendedTraversalBuilder(traverson.follow(rels), operations, mediaTypes);
    }

    public ExtendedTraversalBuilder follow(Hop hop) {
        return new ExtendedTraversalBuilder(traverson.follow(hop), operations, mediaTypes);
    }

    public static  class ExtendedTraversalBuilder{
        private Traverson.TraversalBuilder traversalBuilder;

        private final RestOperations operations ;
        private  final List<MediaType> mediaTypes;
        public ExtendedTraversalBuilder(Traverson.TraversalBuilder traversalBuilder, RestOperations operations, List<MediaType> mediaTypes) {
            this.traversalBuilder = traversalBuilder;
            this.operations = operations;
            this.mediaTypes = mediaTypes;
        }

        public ExtendedTraversalBuilder follow(String... rels) {
            return new ExtendedTraversalBuilder(traversalBuilder.follow(rels), operations,mediaTypes);
        }

        public ExtendedTraversalBuilder follow(Hop hop) {
            return new ExtendedTraversalBuilder(traversalBuilder.follow(hop), operations, mediaTypes);
        }

        private <T> HttpEntity<T> prepareRequest(T data, HttpHeaders headers) {
            if (headers.getAccept().isEmpty()) {
                headers.setAccept(this.mediaTypes);
            }
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new HttpEntity<>(data, headers);
        }

        public <T> T post(Object data, ParameterizedTypeReference<T> type) throws JsonProcessingException {
          return  operations.exchange( traversalBuilder.asLink().toUri(),  HttpMethod.POST, prepareRequest(data, new HttpHeaders())


             , type).getBody()
          ;



        }

        public <T> T put(Object data, ParameterizedTypeReference<T> type){
            return  operations.exchange( traversalBuilder.asLink().toUri(),  HttpMethod.PUT, prepareRequest(data, new HttpHeaders())


                    , type).getBody()
                    ;
        }

        public <T> T patch(Object data, ParameterizedTypeReference<T> type){
            return  operations.exchange( traversalBuilder.asLink().toUri(),  HttpMethod.PATCH, prepareRequest(data, new HttpHeaders())


                    , type).getBody()
                    ;
        }

        public <T> T delete( ParameterizedTypeReference<T> type){
            return  operations.exchange( traversalBuilder.asLink().toUri(),  HttpMethod.DELETE, prepareRequest(null, new HttpHeaders())


                    , type).getBody()
                    ;
        }

        public ExtendedTraversalBuilder withTemplateParameters(Map<String, Object> parameters) {
            return new ExtendedTraversalBuilder(traversalBuilder.withTemplateParameters(parameters), operations, mediaTypes);
        }

        public ExtendedTraversalBuilder withHeaders(HttpHeaders headers) {
            return new ExtendedTraversalBuilder(traversalBuilder.withHeaders(headers), operations, mediaTypes);
        }

        @Nullable
        public <T> T toObject(Class<T> type) {
            return traversalBuilder.toObject(type);
        }

        @Nullable
        public <T> T toObject(ParameterizedTypeReference<T> type) {
            return traversalBuilder.toObject(type);
        }

        public <T> T toObject(String jsonPath) {
            return traversalBuilder.toObject(jsonPath);
        }

        public <T> ResponseEntity<T> toEntity(Class<T> type) {
            return traversalBuilder.toEntity(type);
        }

        public Link asLink() {
            return traversalBuilder.asLink();
        }

        public Link asTemplatedLink() {
            return traversalBuilder.asTemplatedLink();
        }


    }
}
