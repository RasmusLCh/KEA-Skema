package kea.schedule.controllers;

import kea.schedule.moduls.MicroService;
import kea.schedule.moduls.User;
import kea.schedule.services.LangService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
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

    //https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy
    @RequestMapping(value = "{servicename}/{page}")
    public String request_servicerest_page(@RequestBody(required = false) String body,
                                           @PathVariable String servicename,
                                           @PathVariable String page,
                                           HttpMethod method,
                                           HttpServletRequest request,
                                           HttpSession session,
                                           Model model) throws URISyntaxException, IOException {
        model.addAttribute("data", "");
        String query = "userid=0";
        if(session != null && session.getAttribute("user") != null){
            User user = (User)session.getAttribute("user");
            query = "userid=" + user.getId();
        }
        MicroService ms = mss.findMSByName(servicename);
        if(ms == null) return "";
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http", null, "localhost", ms.getPort(), "/servicerest/" + servicename + "/" + page, query, null);

        //POST Multipart, we take each part out and all parts are added to a new request!
        //Current issue1: For some bizar reason, the content_type changes from nothing (for those who hasnt gotten a content-type) to content-type application/octet-stream, overall this doesnt seem to have any effect though..
        //Current issue2: Only post works with multipart
        if(request.getMethod().equalsIgnoreCase("post") && request.getContentType().startsWith("multipart/form-data;")) {
            try {
                MultiValueMap<String, Object> newrequestbody = new LinkedMultiValueMap<>();
                //System.out.println("Method: " + request.getMethod());
                //System.out.println("ContentType: " + request.getContentType());
                java.util.Collection<javax.servlet.http.Part> parts = request.getParts();
                HttpEntity<byte[]> newpart = null;
                for(javax.servlet.http.Part p : parts){
                    MultiValueMap<String, String> newpartheader = new LinkedMultiValueMap<>();
                    //System.out.println("Part: " + p.toString() + " " + p.getContentType() + " " + p.getName());
                    //Add headers to partmap
                    for(String headername: p.getHeaderNames()){
                        //System.out.println("Headername: " + headername + " " + p.getHeader(headername));
                        newpartheader.add(headername, p.getHeader(headername));
                    }
                    if(p.getContentType() == null){
                        newpartheader.add(HttpHeaders.CONTENT_TYPE, null);
                        byte[] partbytes = null;
                        if(p.getInputStream() != null){
                            partbytes = new byte[(int)p.getSize()];
                            p.getInputStream().read(partbytes);
                            //System.out.println(new String(partbytes));
                        }
                        else{
                            //System.out.println("No input stream");
                        }
                        newpart = new HttpEntity<>(partbytes, newpartheader);
                    }
                    else{
                        //If a content type is set, we treat part as a file upload
                        byte[] partbytes = new byte[(int)p.getSize()];
                        p.getInputStream().read(partbytes);
                        newpart = new HttpEntity<>(partbytes, newpartheader);
                    }
                    newrequestbody.add(p.getName(), newpart);
                }
                HttpHeaders newrequestheader = new HttpHeaders();
                newrequestheader.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<MultiValueMap<String, Object>> uploade = new HttpEntity<>(newrequestbody, newrequestheader);
                Object o = (restTemplate.postForEntity(uri, uploade, String.class)).getBody();
                if(o != null){
                    return o.toString();
                }
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        else { //This is the default way we handle requests
            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.set(headerName, request.getHeader(headerName));
            }
            HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

            try {
                Object o = (restTemplate.exchange(uri, method, httpEntity, String.class)).getBody();

                if (o != null) {
                    return o.toString();
                }
            } catch (HttpStatusCodeException e) {
                System.out.println("HttpStatusCodeException: " + e.getStatusCode());
               return "Internal relay error!";
                /*
                return ResponseEntity.status(e.getRawStatusCode())
                        .headers(e.getResponseHeaders())
                        .body(e.getResponseBodyAsString()).toString();

                 */
            }
        }
        return "";
    }

    /*

    //https://stackoverflow.com/questions/14726082/spring-mvc-rest-service-redirect-forward-proxy
    @RequestMapping(value = "{servicename}/{restpage}")
    @ResponseBody
    public String get_servicename_restpage(@RequestBody(required = false) String body,
                                           @PathVariable String servicename,
                                           @PathVariable String restpage,
                                            HttpMethod method,
                                            HttpServletRequest request,
                                            HttpSession session) throws URISyntaxException {
         String query = null;
         if(session != null && session.getAttribute("user") != null){
             User user = (User)session.getAttribute("user");
             query = "userid=" + user.getId();
         }
         MicroService ms = mss.findMSByName(servicename);
        if(ms == null) return "";
         URI uri = new URI("http", null, "localhost", ms.getPort(), "/servicerest/" + servicename + "/" + restpage, query, null);
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            Object o = (restTemplate.exchange(uri, method, httpEntity, String.class)).getBody();
            if(o != null){
                return o.toString();
            }
            return "";
        } catch(HttpStatusCodeException e) {
            System.out.println(e.getStatusCode());
            return ResponseEntity.status(e.getRawStatusCode())
                                 .headers(e.getResponseHeaders())
                                 .body(e.getResponseBodyAsString()).toString();
        }
    }


     */
}
