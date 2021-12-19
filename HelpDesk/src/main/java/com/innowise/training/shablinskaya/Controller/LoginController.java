package com.innowise.training.shablinskaya.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller// используй @RestController
@RequestMapping(value = {"", "login"}) //value = "/login", produces = MediaType.APPLICATION_JSON_VALUE
public class LoginController {

    @RequestMapping(method = RequestMethod.GET) //зачем тебе GET в логине?
    //может тебе нужен метод POST? используй @PostMapping или @GetMapping("/{адрес}") + ResponseEntity<T>
    public String index(){
        return "login/index";
    }
    // у тебя есть логин контроллер, но еще нет настройки секьюрити

    //пример
//    @RestController
//    @RequestMapping(value = "/example", produces = MediaType.APPLICATION_JSON_VALUE)
//    public class ExampleController {
//
//        @GetMapping("/{id}")
//        public ResponseEntity<Example> getExampleById(@PathVariable("id") Long id) {
//            return ResponseEntity.ok(example.getById(id));
//        }
//
//    }
}
