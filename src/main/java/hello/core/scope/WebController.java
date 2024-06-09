package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final LogService logService;
    /*private final ObjectProvider<MyLogger> myLoggersProvider;*/
    private final MyLogger myLogger;

    @GetMapping("/request-scope")
    @ResponseBody
    public String requestScope(HttpServletRequest request) {
        /*MyLogger myLogger = myLoggersProvider.getObject();*/
        System.out.println("proxy mylogger = "+myLogger.getClass());
        myLogger.setRequsetUrl(request.getRequestURI().toString());

        myLogger.log("controller test");
        logService.logic("testId");
        return "OK";
    }
}
