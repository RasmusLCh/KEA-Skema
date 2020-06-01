package proof.concept.microservice1.controllers;

import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/**
 * If our program calls for a page in the microservice, this controller handles it.
 * */
@Controller
@RequestMapping("/servicepages/MS1/")
public class MSServicePagesController {
    @GetMapping({"test"})
    public String get_test(){
        return "test_eng";
    }

    @GetMapping("test.dk")
    public String get_action_dk(@RequestParam(value="user_id", required=false, defaultValue = "0") int user_id){
        System.out.println("dk " + user_id);
        return "test_dk";
    }

    @GetMapping("test.eng")
    public String get_action_eng(@RequestParam(value="user_id", required=false, defaultValue = "0") int user_id){
        System.out.println("uh " + user_id);
        return "test_eng";
    }

    @GetMapping("action")
    public String get_action(){
        System.out.println("action");
        return "test_eng";
    }



    @PostMapping("action")
    public ResponseEntity post_action(@RequestBody JSONObject json, HttpServletRequest hsr){

        for (Enumeration e = hsr.getParameterNames() ; e.hasMoreElements() ;) {
            System.out.println(e.nextElement());

        }
        System.out.println("JSON: " + json.toJSONString());


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("upload")
    public ResponseEntity post_addfileresource(@RequestParam(value="multipartfile", required=false) MultipartFile multipartfile, @RequestParam(value="text1", required=false) String text1, HttpServletRequest request) throws IOException, ServletException {
        if(text1 != null){
            System.out.println("text1 is NOT NULL");
        }
        else{
            System.out.println("text1 is NULL!!!!!!");
        }
        String feedback = "";
        java.util.Collection<javax.servlet.http.Part> parts = request.getParts();
        HttpEntity<byte[]> newpart = null;
        for(javax.servlet.http.Part p : parts){
            for(String headername: p.getHeaderNames()){
                System.out.println("Headername: " + headername + " " + p.getHeader(headername));
                feedback += "Headername: " + headername + " " + p.getHeader(headername) + "<br/>";
            }
            if(p.getContentType() == null || p.getContentType().equalsIgnoreCase("application/octet-stream")){
                byte[] partbytes = null;
                if(p.getInputStream() != null){
                    partbytes = new byte[(int)p.getSize()];
                    p.getInputStream().read(partbytes);
                    System.out.println("data: " + new String(partbytes));
                    feedback += "Value: " + new String(partbytes) + "<br/>";
                }
                else{
                    System.out.println("No input stream");
                    feedback += "No input stream" + "<br/>";
                }
            }

        }
            Set keys = request.getParameterMap().keySet();
            Iterator k = keys.iterator();
            while(k.hasNext()){
                System.out.println(k.next().toString());
            }
            if(multipartfile != null && !multipartfile.isEmpty() && multipartfile.getOriginalFilename() != null && multipartfile.getOriginalFilename() != ""){

                System.out.println(multipartfile);
                System.out.println(multipartfile.isEmpty());
                System.out.println(multipartfile.getOriginalFilename());
                return new ResponseEntity<>("Thanks for the file with the name " + multipartfile.getOriginalFilename() + " with size of " + multipartfile.getSize(), HttpStatus.OK);
            }

            return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}
