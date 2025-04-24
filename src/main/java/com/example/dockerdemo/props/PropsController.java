package com.example.dockerdemo.props;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/props")
public class PropsController {

    private final Props props;

    public PropsController(Props props) {
        this.props = props;
    }

    @GetMapping
    public Props getValues() {
        return props;
    }
}
