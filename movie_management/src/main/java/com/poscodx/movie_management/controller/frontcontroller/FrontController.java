package com.poscodx.movie_management.controller.frontcontroller;

import com.poscodx.movie_management.controller.controlleradapter.BasicControllerAdapter;
import com.poscodx.movie_management.controller.controlleradapter.ControllerAdapter;
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

@WebServlet(name = "frontController", urlPatterns = "/*")
public class FrontController extends HttpServlet {

    private final Map<String, Object> controllerMap = new HashMap<>();
    private final List<ControllerAdapter> adapterList = new ArrayList<>();

    public FrontController() {
        initControllerMap();
        initAdapterList();
    }

    private void initControllerMap() {
        // 컨트롤러 추가
    }

    private void initAdapterList() {
        // 어댑터 추가
        adapterList.add(new BasicControllerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object controller = getController(request);
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ControllerAdapter adapter = getAdapter(controller);
        ModelView mv = adapter.handle(request, response, controller);

        View view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private Object getController(HttpServletRequest request){
        return controllerMap.get(request.getRequestURI());
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
        return new View("/" + viewName + ".jsp");
    }
}
