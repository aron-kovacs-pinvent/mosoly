package live.mosoly.portalbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {

    @RequestMapping({
            "/",
            "/login/{hash:\\w+}",
            "/login",
            "/menu",
            "/applicant-qr",
            "/apply",
            "/verification",
            "/verification-qr",
            "/verify-by"
    })
    public String index() {
        return "forward:/index.html";
    }

}

