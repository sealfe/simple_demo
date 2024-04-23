package com.learn.simple_demo;

import com.learn.simple_demo.dto.ApiInputPutDto;
import com.learn.simple_demo.dto.ApiOutPutDto;
import com.learn.simple_demo.usecase.DeleteUseCase;
import com.learn.simple_demo.usecase.SaveExceptionUseCase;
import com.learn.simple_demo.usecase.SaveUseCase;
import com.learn.simple_demo.usecase.SelectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/db")
public class DbApi {


    @Autowired
    private SaveUseCase saveUseCase;

    @Autowired
    private SelectUseCase selectUseCase;

    @Autowired
    private DeleteUseCase deleteUseCase;


    @Autowired
    private SaveExceptionUseCase saveExceptionUseCase;


    @GetMapping("/{id}")
    public ApiOutPutDto getEntities(@PathVariable String id) {
        return selectUseCase.select(id);
    }


    @PostMapping("")
    public Boolean save(@RequestBody ApiInputPutDto apiInputPutDto) {
        saveUseCase.execute(apiInputPutDto.getEntity(), apiInputPutDto.getMongoRecord());
        return true;
    }


    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable String id) {
        deleteUseCase.execute(id);
        return true;
    }


    @PostMapping("exception")
    public Boolean saveException(@RequestBody ApiInputPutDto inputPutDto) {
        saveExceptionUseCase.execute(inputPutDto.getEntity(), inputPutDto.getMongoRecord());
        return true;
    }


    @PutMapping("refresh")
    public Boolean refresh() {
        MongoTemplateFactory.clean();
        MysqlFactory.clean();
        return true;
    }

}
