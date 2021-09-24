import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManagerFactory;

@Controller
public class Hello {


    @GetMapping("hello")
    public String hello(){

        return "/hello";
    }

}
