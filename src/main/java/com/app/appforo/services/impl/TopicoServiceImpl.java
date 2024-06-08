package com.app.appforo.services.impl;

import com.app.appforo.exception.ApiException;
import com.app.appforo.persistence.entity.Topico;
import com.app.appforo.persistence.entity.User;
import com.app.appforo.persistence.repository.TopicoRepository;
import com.app.appforo.persistence.repository.UserRepository;
import com.app.appforo.services.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicoServiceImpl implements TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Topico> findAll(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    @Override
    public Topico add(Topico topico) {
        topico.setId(null);
        User user = topico.getUser();
        if (user != null && user.getId() != null) {
            user = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ApiException("User not found"));
            topico.setUser(user);
        } else {
            throw new RuntimeException("User must be provided with a valid ID");
        }
        return topicoRepository.save(topico);
    }

    @Override
    public Topico findById(String topicoId) {

        Integer id = Integer.parseInt(topicoId);

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found"));

        return topico;
    }

    @Override
    public Map<String, String> delete(String topicoId) {
        Map<String, String> map = new HashMap<String, String>();
        try{
            Integer id = Integer.parseInt(topicoId);
            Optional<Topico> topico = topicoRepository.findById(id);
            if(!topico.isPresent()){
                map.put("statusCode", "ok");
                map.put("message", "Fue eliminado");
                throw new ApiException("Tópico not found: 404");
            }else{
                topicoRepository.deleteById(id);
                map.put("statusCode", "ok");
                map.put("message", "Fue eliminado");
            }

        }catch (NumberFormatException ex ){
            throw new IllegalStateException("Not found" + ex.getMessage());
        }

        return map;
    }

    @Override
    public Topico update(Topico topico,String topicoId) {
        if(topico == null){
            throw new ApiException("La petición no contiene un cuerpo");
        }
        Integer id = Integer.parseInt(topicoId);

        if(id != topico.getId()){
            throw new ApiException("Los id no son iguales");
        }

        Optional<Topico> findTopico = topicoRepository.findById(id);

        if(findTopico.isPresent()){
            userRepository.findById(topico.getUser().getId())
                    .orElseThrow(() ->new ApiException("User not found: 404"));

            topico.setDateCreate(findTopico.get().getDateCreate());
            return topicoRepository.save(topico);
        }else{
            throw new ApiException("Tópico not found: 404");
        }
    }
}
