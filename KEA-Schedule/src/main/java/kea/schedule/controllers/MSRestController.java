package kea.schedule.controllers;

import kea.schedule.moduls.MicroService;
import kea.schedule.services.LangService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

@RestController
@RequestMapping("/servicerest/")
public class MSRestController {
    private MicroServiceService mss;
    private ResourceLoader rl;
    private LangService langservice;

    @Autowired
    public MSRestController(MicroServiceService mss, ResourceLoader rl, LangService langservice){
        this.mss = mss;
        this.rl = rl;
        this.langservice = langservice;
    }

    /*
    @GetMapping("{servicename}/{restpage}")
    @ResponseBody
    public String get_servicename_restpage(@PathVariable String servicename, @PathVariable String restpage) throws IOException {
        MicroService ms = mss.findMSByName(servicename);
        if(ms != null){
            System.out.println("Getting: " + "http://localhost:"+ms.getPort()+"/servicerest/"+servicename+"/"+restpage);
            Resource resource = rl.getResource("http://localhost:"+ms.getPort()+"/servicerest/"+servicename+"/"+restpage);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            String s= "";
            while(br.ready()){
                //System.out.println(br.readLine());
                s+=br.readLine();
            }
            return s;
        }
        System.out.println("Service: " + servicename);
        return "Unknown service";
    }
*/
    //https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy
 @RequestMapping(value = "{servicename}/{restpage}")
 @ResponseBody
public String /*ResponseEntity*/ get_servicename_restpage(@RequestBody(required = false) String body, @PathVariable String servicename, @PathVariable String restpage,
                                 HttpMethod method, HttpServletRequest request, HttpServletResponse response)
    throws URISyntaxException {
    String requestUrl = request.getRequestURI();
     MicroService ms = mss.findMSByName(servicename);
    if(ms == null) return null;
     URI uri = new URI("http", null, "localhost", ms.getPort(), "/servicerest/" + servicename + "/" + restpage, null, null);
    /*
     uri = UriComponentsBuilder.fromUri(uri)
                              .path(requestUrl)
                              .query("") // request.getQueryString()
                              .build(true).toUri();
*/
    HttpHeaders headers = new HttpHeaders();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        headers.set(headerName, request.getHeader(headerName));
    }

    HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    try {
        System.out.println("request mirrorRest: " + uri.toString() );
        Object o = (restTemplate.exchange(uri, method, httpEntity, String.class)).getBody();
        if(o != null){
            o.toString();
        }
        return "";
//        return (restTemplate.exchange(uri, method, httpEntity, String.class)).getBody().toString();
    } catch(HttpStatusCodeException e) {
        System.out.println(e.getStatusCode());
        return ResponseEntity.status(e.getRawStatusCode())
                             .headers(e.getResponseHeaders())
                             .body(e.getResponseBodyAsString()).toString();
    }
}

}
