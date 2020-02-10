package proof.concept.modules.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicerest/")
public class MSRestController {

    /*
    https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy
 @RequestMapping("/**")
public ResponseEntity mirrorRest(@RequestBody(required = false) String body,
    HttpMethod method, HttpServletRequest request, HttpServletResponse response)
    throws URISyntaxException {
    String requestUrl = request.getRequestURI();

    URI uri = new URI("http", null, server, port, null, null, null);
    uri = UriComponentsBuilder.fromUri(uri)
                              .path(requestUrl)
                              .query(request.getQueryString())
                              .build(true).toUri();

    HttpHeaders headers = new HttpHeaders();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        headers.set(headerName, request.getHeader(headerName));
    }

    HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    try {
        return restTemplate.exchange(uri, method, httpEntity, String.class);
    } catch(HttpStatusCodeException e) {
        return ResponseEntity.status(e.getRawStatusCode())
                             .headers(e.getResponseHeaders())
                             .body(e.getResponseBodyAsString());
    }
}
    * */
}
