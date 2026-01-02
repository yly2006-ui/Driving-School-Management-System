package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.service.student.IDrivingContentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/drivingContent")
public class DrivingContentController {

    @Autowired
    private IDrivingContentService drivingContentService;

}
