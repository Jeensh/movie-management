package com.poscodx.movie_management.controller.frontcontroller;

import com.poscodx.movie_management.controller.basiccontroller.LoginController;
import com.poscodx.movie_management.controller.basiccontroller.SignUpController;
import com.poscodx.movie_management.controller.controlleradapter.BasicControllerAdapter;
import com.poscodx.movie_management.controller.controlleradapter.ControllerAdapter;
import com.poscodx.movie_management.controller.controlleradapter.RestControllerAdapter;
import com.poscodx.movie_management.controller.restcontroller.AuthRestController;
import com.poscodx.movie_management.controller.restcontroller.SignUpRestController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// basic 컨트롤러는 get형식으로 들어온다고 가정
// rest 컨트롤러는 post형식으로 들어온다고 가정
@WebServlet(name = "frontController", urlPatterns = "/pm/*")
public class FrontController extends HttpServlet {

    private final Map<String, Object> controllerMap = new HashMap<>();
    private final List<ControllerAdapter> adapterList = new ArrayList<>();

    public FrontController() {
        initControllerMap();
        initAdapterList();
    }

    private void initControllerMap() {
        // 컨트롤러 추가
        controllerMap.put("/", new LoginController());
        controllerMap.put("/rest/auth", new AuthRestController());
        controllerMap.put("/signup", new SignUpController());
        controllerMap.put("/rest/signup", new SignUpRestController());
    }

    private void initAdapterList() {
        // 어댑터 추가
        adapterList.add(new BasicControllerAdapter());
        adapterList.add(new RestControllerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object controller = getController(request);
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 사용자 인증 실패시 로그인 컨트롤러로 이동
        if(!auth(request)){
            System.out.println("--" + controller.getClass());
            controller = controllerMap.get("/");
        }

        System.out.println(controller.getClass());

        ControllerAdapter adapter = getAdapter(controller);
        ModelView mv = adapter.handle(request, response, controller);

        View view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private Object getController(HttpServletRequest request){
        String uri = request.getRequestURI();
        uri = uri.substring(3);

        // 요청uri 확인
        System.out.println(uri);
        return controllerMap.get(uri);
    }

    private ControllerAdapter getAdapter(Object controller){
        for(ControllerAdapter adapter : adapterList){
            if(adapter.supports(controller)){
                return adapter;
            }
        }

        throw new RuntimeException("controller adapter를 찾을 수 없음. controller = " + controller);
    }

    private View viewResolver(String viewName){
        return new View("/views/" + viewName + ".jsp");
    }

    private boolean auth(HttpServletRequest request){
        String uri = request.getRequestURI();
        if(uri.contains("auth") || (uri.contains("sign"))) return true;
        else if(request.getSession().getAttribute("auth") == null) return false;
        else return true;
    }
}
