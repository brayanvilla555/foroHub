package com.app.appforo.services.impl;

import com.app.appforo.exception.ApiException;
import com.app.appforo.persistence.entity.Response;
import com.app.appforo.persistence.entity.Topico;
import com.app.appforo.persistence.entity.User;
import com.app.appforo.persistence.repository.ResponseRepository;
import com.app.appforo.persistence.repository.TopicoRepository;
import com.app.appforo.persistence.repository.UserRepository;
import com.app.appforo.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public Page<Response> findAll(Pageable pageable) {
        return responseRepository.findAll(pageable);
    }

    @Override
    public Response add(Response response) {
        response.setId(null);
        User user = response.getUser();
        Topico topico = response.getTopico();
        if (user != null && user.getId() != null && topico != null && topico.getId() != null) {
            topico = topicoRepository.findById(topico.getId())
                    .orElseThrow(()-> new ApiException("Tópico not found"));

            user = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ApiException("User not found"));
            response.setTopico(topico);
            response.setUser(user);
        } else {
            throw new RuntimeException("User must be provided with a valid ID");
        }

        return responseRepository.save(response);
    }

    @Override
    public Response findById(String responseId) {

        Integer id = Integer.parseInt(responseId);

        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found"));

        return response;
    }

    @Override
    public Map<String, String> delete(String responseId) {
        Map<String, String> map = new HashMap<String, String>();
        try{
            Integer id = Integer.parseInt(responseId);
            Optional<Response> response = responseRepository.findById(id);
            if(!response.isPresent()){
                map.put("statusCode", "ok");
                map.put("message", "Response not Found");
                throw new ApiException("Response not found: 404");
            }else{
                responseRepository.deleteById(id);
                map.put("statusCode", "ok");
                map.put("message", "Fue eliminado");
            }

        }catch (NumberFormatException ex ){
            throw new IllegalStateException("Not found" + ex.getMessage());
        }

        return map;
    }

    @Override
    public Response update(Response response,String responseId) {
        if(response == null){
            throw new ApiException("La petición no contiene un cuerpo");
        }
        Integer id = Integer.parseInt(responseId);

        if(id != response.getId()){
            throw new ApiException("Los id no son iguales");
        }

        Optional<Response> findResponse = responseRepository.findById(id);

        if(findResponse.isPresent()){

            topicoRepository.findById(response.getTopico().getId())
                    .orElseThrow(()-> new ApiException("Tópico not found"));

            userRepository.findById(response.getUser().getId())
                    .orElseThrow(() ->new ApiException("User not found: 404"));

            response.setDateCreate(findResponse.get().getDateCreate());
            return responseRepository.save(response);
        }else{
            throw new ApiException("Tópico not found: 404");
        }
    }

}
